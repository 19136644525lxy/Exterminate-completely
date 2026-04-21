package com.yifei.ec;

import net.minecraft.server.MinecraftServer;
import net.minecraft.world.entity.player.Player;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class MappingManager {
    private Map<String, String> currentMappings = new HashMap<>();
    private Path mappingsDirectory;
    private String currentLanguage = "en_us";
    
    public MappingManager() {
        System.out.println("[EC] MappingManager initialized");
    }
    
    public void setLanguage(String language) {
        this.currentLanguage = language;
        System.out.println("[EC] Language set to: " + language);
    }
    
    public String getCurrentLanguage() {
        return currentLanguage;
    }
    
    public void loadMappings(Player player) {
        MinecraftServer server = player.getServer();
        if (server == null) {
            System.out.println("[EC] Server is null, cannot load mappings");
            return;
        }
        
        // 获取映射表目录
        mappingsDirectory = server.getServerDirectory().toPath().resolve("config").resolve("ec").resolve("mapp");
        
        Path langDirectory = mappingsDirectory.resolve(currentLanguage);
        Path mappingFile = langDirectory.resolve("mappings.txt");
        
        try {
            // 创建目录结构
            Files.createDirectories(langDirectory);
            
            // 如果映射表文件不存在，创建默认映射表
            if (!Files.exists(mappingFile)) {
                createDefaultMappings(mappingFile);
            }
            
            // 加载映射表
            currentMappings.clear();
            for (String line : Files.readAllLines(mappingFile)) {
                if (!line.trim().isEmpty() && line.contains("=")) {
                    String[] parts = line.split("=", 2);
                    if (parts.length == 2) {
                        currentMappings.put(parts[0].trim(), parts[1].trim());
                    }
                }
            }
            
            System.out.println("[EC] Loaded mappings for language: " + currentLanguage + ", " + currentMappings.size() + " entries");
        } catch (IOException e) {
            System.out.println("[EC] Error loading mappings:");
            e.printStackTrace();
        }
    }
    
    private void createDefaultMappings(Path mappingFile) throws IOException {
        StringBuilder defaultMappings = new StringBuilder();
        
        // 添加默认映射
        if (currentLanguage.equals("zh_cn")) {
            // 中文默认映射
            defaultMappings.append("minecraft:creeper=苦力怕\n");
            defaultMappings.append("minecraft:zombie=僵尸\n");
            defaultMappings.append("minecraft:skeleton=骷髅\n");
            defaultMappings.append("minecraft:spider=蜘蛛\n");
            defaultMappings.append("minecraft:enderman=末影人\n");
            defaultMappings.append("minecraft:slime=史莱姆\n");
            defaultMappings.append("minecraft:witch=女巫\n");
            defaultMappings.append("minecraft:phantom=幻翼\n");
            defaultMappings.append("minecraft:drowned=溺尸\n");
            defaultMappings.append("minecraft:husk=尸壳\n");
            defaultMappings.append("minecraft:stray=流浪者\n");
            defaultMappings.append("minecraft:cave_spider=洞穴蜘蛛\n");
            defaultMappings.append("minecraft:silverfish=蠹虫\n");
            defaultMappings.append("minecraft:blaze=烈焰人\n");
            defaultMappings.append("minecraft:ghast=恶魂\n");
            defaultMappings.append("minecraft:magma_cube=岩浆怪\n");
            defaultMappings.append("minecraft:zombified_piglin=僵尸猪灵\n");
            defaultMappings.append("minecraft:piglin=猪灵\n");
            defaultMappings.append("minecraft:piglin_brute=猪灵蛮兵\n");
            defaultMappings.append("minecraft:hoglin=疣猪兽\n");
            defaultMappings.append("minecraft:zoglin=僵尸疣猪兽\n");
            defaultMappings.append("minecraft:wither_skeleton=凋灵骷髅\n");
            defaultMappings.append("minecraft:guardian=守卫者\n");
            defaultMappings.append("minecraft:elder_guardian=远古守卫者\n");
            defaultMappings.append("minecraft:shulker=潜影贝\n");
            defaultMappings.append("minecraft:endermite=末影螨\n");
            defaultMappings.append("minecraft:evoker=唤魔者\n");
            defaultMappings.append("minecraft:vindicator=卫道士\n");
            defaultMappings.append("minecraft:vex=恼鬼\n");
            defaultMappings.append("minecraft:pillager=掠夺者\n");
            defaultMappings.append("minecraft:ravager=劫掠兽\n");
            defaultMappings.append("minecraft:warden=监守者\n");
        } else {
            // 英文默认映射
            defaultMappings.append("minecraft:creeper=Creeper\n");
            defaultMappings.append("minecraft:zombie=Zombie\n");
            defaultMappings.append("minecraft:skeleton=Skeleton\n");
            defaultMappings.append("minecraft:spider=Spider\n");
            defaultMappings.append("minecraft:enderman=Enderman\n");
            defaultMappings.append("minecraft:slime=Slime\n");
            defaultMappings.append("minecraft:witch=Witch\n");
            defaultMappings.append("minecraft:phantom=Phantom\n");
            defaultMappings.append("minecraft:drowned=Drowned\n");
            defaultMappings.append("minecraft:husk=Husk\n");
            defaultMappings.append("minecraft:stray=Stray\n");
            defaultMappings.append("minecraft:cave_spider=Cave Spider\n");
            defaultMappings.append("minecraft:silverfish=Silverfish\n");
            defaultMappings.append("minecraft:blaze=Blaze\n");
            defaultMappings.append("minecraft:ghast=Ghast\n");
            defaultMappings.append("minecraft:magma_cube=Magma Cube\n");
            defaultMappings.append("minecraft:zombified_piglin=Zombified Piglin\n");
            defaultMappings.append("minecraft:piglin=Piglin\n");
            defaultMappings.append("minecraft:piglin_brute=Piglin Brute\n");
            defaultMappings.append("minecraft:hoglin=Hoglin\n");
            defaultMappings.append("minecraft:zoglin=Zoglin\n");
            defaultMappings.append("minecraft:wither_skeleton=Wither Skeleton\n");
            defaultMappings.append("minecraft:guardian=Guardian\n");
            defaultMappings.append("minecraft:elder_guardian=Elder Guardian\n");
            defaultMappings.append("minecraft:shulker=Shulker\n");
            defaultMappings.append("minecraft:endermite=Endermite\n");
            defaultMappings.append("minecraft:evoker=Evoker\n");
            defaultMappings.append("minecraft:vindicator=Vindicator\n");
            defaultMappings.append("minecraft:vex=Vex\n");
            defaultMappings.append("minecraft:pillager=Pillager\n");
            defaultMappings.append("minecraft:ravager=Ravager\n");
            defaultMappings.append("minecraft:warden=Warden\n");
        }
        
        // 保存默认映射
        Files.writeString(mappingFile, defaultMappings.toString());
        System.out.println("[EC] Created default mappings for language: " + currentLanguage);
    }
    
    public String getMappedName(String mobId) {
        return currentMappings.getOrDefault(mobId, mobId);
    }
    
    // 通过中文名称查找英文ID
    public String getMobIdByName(String name) {
        // 如果输入已经是ID格式（包含冒号），直接返回
        if (name.contains(":")) {
            return name;
        }
        
        // 在映射表中查找对应的中文名称
        for (Map.Entry<String, String> entry : currentMappings.entrySet()) {
            if (entry.getValue().equals(name)) {
                return entry.getKey();
            }
        }
        
        // 如果没找到，返回原输入
        return name;
    }
    
    public void reloadMappings(Player player) {
        loadMappings(player);
    }
}