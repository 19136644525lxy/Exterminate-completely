package com.yifei.ec;

import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraft.locale.Language;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

public class MobManager {
    // 为每个世界维护单独的黑名单
    private Map<Path, Set<String>> perWorldBlacklists = new ConcurrentHashMap<>();
    private final Set<String> whitelistedMobs = new CopyOnWriteArraySet<>();
    private boolean whitelistLoaded = false;
    private Path currentWorldPath = null;
    private final MappingManager mappingManager = new MappingManager();
    
    public MobManager() {
        // 不在构造函数中加载配置，避免在配置加载之前获取值
        System.out.println("[EC] MobManager initialized");
    }
    
    public MappingManager getMappingManager() {
        return mappingManager;
    }
    
    private void ensureWhitelistLoaded() {
        if (!whitelistLoaded) {
            whitelistedMobs.clear();
            whitelistedMobs.addAll(MobExterminationConfig.getWhitelistedMobs());
            whitelistLoaded = true;
            System.out.println("[EC] Whitelist loaded: " + whitelistedMobs.size() + " mobs");
        }
    }
    
    public void addMobToBlacklist(Mob mob) {
        ensureWhitelistLoaded();
        String mobId = ForgeRegistries.ENTITY_TYPES.getKey(mob.getType()).toString();
        if (!whitelistedMobs.contains(mobId)) {
            Path worldPath = getWorldPathFromMob(mob);
            if (worldPath != null) {
                Set<String> blacklist = perWorldBlacklists.computeIfAbsent(worldPath, k -> new CopyOnWriteArraySet<>());
                blacklist.add(mobId);
                System.out.println("[EC] Added mob to blacklist: " + mobId + " for world: " + worldPath);
                saveBlacklist(worldPath);
            }
        }
    }
    
    public boolean canMobSpawn(Mob mob) {
        ensureWhitelistLoaded();
        String mobId = ForgeRegistries.ENTITY_TYPES.getKey(mob.getType()).toString();
        
        // 白名单优先
        if (whitelistedMobs.contains(mobId)) {
            return true;
        }
        
        // 检查该世界的黑名单
        Path worldPath = getWorldPathFromMob(mob);
        if (worldPath != null) {
            Set<String> blacklist = perWorldBlacklists.computeIfAbsent(worldPath, k -> loadBlacklistForWorld(worldPath));
            boolean canSpawn = !blacklist.contains(mobId);
            if (!canSpawn) {
                System.out.println("[EC] Blocked mob spawn: " + mobId + " for world: " + worldPath);
            }
            return canSpawn;
        }
        
        // 如果无法获取世界路径，默认允许生成
        return true;
    }
    
    public void loadBlacklist() {
        // 加载当前服务器所有世界的黑名单
        MinecraftServer server = net.minecraftforge.server.ServerLifecycleHooks.getCurrentServer();
        if (server != null) {
            for (ServerLevel level : server.getAllLevels()) {
                Path worldPath = getWorldPath(level);
                if (worldPath != null) {
                    loadBlacklistForWorld(worldPath);
                }
            }
        }
        // 加载黑名单后也加载白名单
        ensureWhitelistLoaded();
    }
    
    private Set<String> loadBlacklistForWorld(Path worldPath) {
        Set<String> blacklist = new CopyOnWriteArraySet<>();
        try {
            Path blacklistPath = getBlacklistPathForWorld(worldPath);
            System.out.println("[EC] Loading blacklist from: " + blacklistPath);
            
            if (Files.exists(blacklistPath)) {
                String content = Files.readString(blacklistPath);
                String[] mobs = content.split("\\n");
                for (String mob : mobs) {
                    if (!mob.trim().isEmpty()) {
                        blacklist.add(mob.trim());
                        System.out.println("[EC] Loaded mob from blacklist: " + mob.trim());
                    }
                }
                System.out.println("[EC] Blacklist loaded for world " + worldPath + ": " + blacklist.size() + " mobs");
            } else {
                System.out.println("[EC] Blacklist file not found for world " + worldPath + ", starting with empty blacklist");
            }
        } catch (IOException e) {
            System.out.println("[EC] Error loading blacklist for world " + worldPath + ":");
            e.printStackTrace();
        }
        perWorldBlacklists.put(worldPath, blacklist);
        return blacklist;
    }
    
    public void saveBlacklist() {
        // 保存所有世界的黑名单
        for (Path worldPath : perWorldBlacklists.keySet()) {
            saveBlacklist(worldPath);
        }
    }
    
    private void saveBlacklist(Path worldPath) {
        try {
            Path blacklistPath = getBlacklistPathForWorld(worldPath);
            System.out.println("[EC] Saving blacklist to: " + blacklistPath);
            Files.createDirectories(blacklistPath.getParent());
            
            Set<String> blacklist = perWorldBlacklists.get(worldPath);
            if (blacklist != null) {
                StringBuilder content = new StringBuilder();
                for (String mob : blacklist) {
                    content.append(mob).append("\n");
                }
                Files.writeString(blacklistPath, content.toString());
                System.out.println("[EC] Blacklist saved for world " + worldPath + ": " + blacklist.size() + " mobs");
            }
        } catch (IOException e) {
            System.out.println("[EC] Error saving blacklist for world " + worldPath + ":");
            e.printStackTrace();
        }
    }
    
    private Path getWorldPathFromMob(Mob mob) {
        if (mob.level instanceof ServerLevel) {
            ServerLevel serverLevel = (ServerLevel) mob.level;
            return getWorldPath(serverLevel);
        }
        System.out.println("[EC] Mob is not in a ServerLevel");
        return null;
    }
    
    private Path getWorldPath(ServerLevel level) {
        try {
            // 获取服务器实例
            MinecraftServer server = level.getServer();
            if (server != null) {
                // 方法1：使用服务器目录 + 世界名称（最可靠的方法）
                try {
                    Path serverDirectory = server.getServerDirectory().toPath();
                    System.out.println("[EC] Server directory: " + serverDirectory);
                    
                    String worldName = server.getWorldData().getLevelName();
                    if (worldName != null && !worldName.isEmpty()) {
                        Path worldPath = serverDirectory.resolve(worldName);
                        System.out.println("[EC] Using server directory + world name: " + worldPath);
                        
                        if (!Files.exists(worldPath)) {
                            Files.createDirectories(worldPath);
                            System.out.println("[EC] Created world directory: " + worldPath);
                        }
                        return worldPath;
                    } else {
                        System.out.println("[EC] World name is null or empty");
                    }
                } catch (Exception e) {
                    System.out.println("[EC] Error getting server directory + world name:");
                    e.printStackTrace();
                }
                
                // 方法2：使用服务器目录作为备选
                try {
                    Path serverDirectory = server.getServerDirectory().toPath();
                    System.out.println("[EC] Using server directory as fallback: " + serverDirectory);
                    
                    if (!Files.exists(serverDirectory)) {
                        Files.createDirectories(serverDirectory);
                    }
                    return serverDirectory;
                } catch (Exception e) {
                    System.out.println("[EC] Error getting server directory:");
                    e.printStackTrace();
                }
                
                // 方法3：使用当前工作目录（最后的备选方案）
                try {
                    Path currentDir = Path.of(".").toAbsolutePath().normalize();
                    System.out.println("[EC] Using current directory as final fallback: " + currentDir);
                    return currentDir;
                } catch (Exception e) {
                    System.out.println("[EC] Error getting current directory:");
                    e.printStackTrace();
                }
            } else {
                System.out.println("[EC] Server is null");
            }
        } catch (Exception e) {
            System.out.println("[EC] Error getting world path:");
            e.printStackTrace();
        }
        return null;
    }
    
    private Path getBlacklistPathForWorld(Path worldPath) {
        Path ecDir = worldPath.resolve("ec");
        Path blacklistPath = ecDir.resolve("blacklist.txt");
        System.out.println("[EC] Blacklist path for world: " + blacklistPath);
        return blacklistPath;
    }
    
    public void updateWhitelist() {
        whitelistedMobs.clear();
        whitelistedMobs.addAll(MobExterminationConfig.getWhitelistedMobs());
        whitelistLoaded = true;
        System.out.println("[EC] Whitelist updated: " + whitelistedMobs.size() + " mobs");
    }
    
    // 重置黑名单，用于测试
    public void resetBlacklist() {
        perWorldBlacklists.clear();
        currentWorldPath = null;
        System.out.println("[EC] Blacklist reset for all worlds");
    }
    
    // 显示白名单
    public void displayWhitelist(CommandSourceStack source) {
        ensureWhitelistLoaded();
        
        if (source.getEntity() instanceof Player) {
            Player player = (Player) source.getEntity();
            mappingManager.loadMappings(player);
        }
        
        String title = Language.getInstance().getOrDefault("commands.ec.whitelist.title");
        source.sendSystemMessage(Component.literal(title));
        if (whitelistedMobs.isEmpty()) {
            String empty = Language.getInstance().getOrDefault("commands.ec.whitelist.empty").replace(title + "\n", "");
            source.sendSystemMessage(Component.literal(empty));
        } else {
            StringBuilder result = new StringBuilder();
            boolean first = true;
            for (String mobId : whitelistedMobs) {
                String mappedName = mappingManager.getMappedName(mobId);
                // 只显示映射表中的名称，如果映射表中没有则显示原始ID
                String displayName = mappedName.equals(mobId) ? mobId : mappedName;
                if (!first) {
                    result.append(", ");
                }
                result.append(displayName);
                first = false;
            }
            source.sendSystemMessage(Component.literal(result.toString()));
        }
    }
    
    // 添加生物到白名单
    public void addToWhitelist(String mobId) {
        MobExterminationConfig.addToWhitelist(mobId);
        updateWhitelist();
    }
    
    // 显示黑名单
    public void displayBlacklist(CommandSourceStack source, ServerLevel level) {
        Path worldPath = getWorldPath(level);
        if (worldPath == null) {
            source.sendSystemMessage(Component.literal("Could not get world path"));
            return;
        }
        
        if (source.getEntity() instanceof Player) {
            Player player = (Player) source.getEntity();
            mappingManager.loadMappings(player);
        }
        
        Set<String> blacklist = perWorldBlacklists.computeIfAbsent(worldPath, k -> loadBlacklistForWorld(worldPath));
        String title = Language.getInstance().getOrDefault("commands.ec.blacklist.title");
        source.sendSystemMessage(Component.literal(title));
        if (blacklist.isEmpty()) {
            String empty = Language.getInstance().getOrDefault("commands.ec.blacklist.empty").replace(title + "\n", "");
            source.sendSystemMessage(Component.literal(empty));
        } else {
            StringBuilder result = new StringBuilder();
            boolean first = true;
            for (String mobId : blacklist) {
                String mappedName = mappingManager.getMappedName(mobId);
                // 只显示映射表中的名称，如果映射表中没有则显示原始ID
                String displayName = mappedName.equals(mobId) ? mobId : mappedName;
                if (!first) {
                    result.append(", ");
                }
                result.append(displayName);
                first = false;
            }
            source.sendSystemMessage(Component.literal(result.toString()));
        }
    }
    
    // 重载映射表
    public void reloadMappings(Player player) {
        mappingManager.reloadMappings(player);
    }
}
