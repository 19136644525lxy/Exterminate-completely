package com.yifei.ec;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.locale.Language;

import java.util.concurrent.CompletableFuture;

public class CommandManager {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        // 主指令 /ec
        dispatcher.register(net.minecraft.commands.Commands.literal("ec")
            // 子指令 /ec whitelist
            .then(net.minecraft.commands.Commands.literal("whitelist")
                .executes(CommandManager::executeWhitelist)
                .then(net.minecraft.commands.Commands.literal("add")
                    .then(net.minecraft.commands.Commands.argument("mobId", com.mojang.brigadier.arguments.StringArgumentType.greedyString())
                        .executes(CommandManager::executeWhitelistAdd)
                    )
                )
            )
            // 子指令 /ec blacklist
            .then(net.minecraft.commands.Commands.literal("blacklist")
                .executes(CommandManager::executeBlacklist)
            )
            // 子指令 /ec language
            .then(net.minecraft.commands.Commands.literal("language")
                .executes(CommandManager::executeLanguageShow)
                .then(net.minecraft.commands.Commands.argument("langCode", com.mojang.brigadier.arguments.StringArgumentType.word())
                    .suggests((context, builder) -> {
                        builder.suggest("en_us");
                        builder.suggest("zh_cn");
                        return builder.buildFuture();
                    })
                    .executes(CommandManager::executeLanguageSet)
                )
            )
        );
    }

    private static int executeWhitelist(CommandContext<CommandSourceStack> context) {
        CommandSourceStack source = context.getSource();
        
        // 显示白名单中的生物ID
        ExterminateCompletely.getInstance().getMobManager().displayWhitelist(source);
        
        return 1;
    }

    private static int executeWhitelistAdd(CommandContext<CommandSourceStack> context) {
        CommandSourceStack source = context.getSource();
        String input = com.mojang.brigadier.arguments.StringArgumentType.getString(context, "mobId");
        
        // 先加载映射表（如果玩家在线）
        if (source.getEntity() instanceof net.minecraft.server.level.ServerPlayer) {
            net.minecraft.server.level.ServerPlayer player = (net.minecraft.server.level.ServerPlayer) source.getEntity();
            ExterminateCompletely.getInstance().getMobManager().getMappingManager().loadMappings(player);
        }
        
        // 通过中文名称查找英文ID
        String mobId = ExterminateCompletely.getInstance().getMobManager().getMappingManager().getMobIdByName(input);
        
        // 添加生物到白名单
        ExterminateCompletely.getInstance().getMobManager().addToWhitelist(mobId);
        String message = String.format(Language.getInstance().getOrDefault("commands.ec.whitelist.add.success"), mobId);
        source.sendSystemMessage(net.minecraft.network.chat.Component.literal(message));
        
        return 1;
    }
    
    private static int executeBlacklist(CommandContext<CommandSourceStack> context) {
        CommandSourceStack source = context.getSource();
        
        // 显示黑名单中的生物ID
        if (source.getEntity() instanceof net.minecraft.server.level.ServerPlayer) {
            net.minecraft.server.level.ServerPlayer player = (net.minecraft.server.level.ServerPlayer) source.getEntity();
            if (player.level instanceof net.minecraft.server.level.ServerLevel) {
                net.minecraft.server.level.ServerLevel serverLevel = (net.minecraft.server.level.ServerLevel) player.level;
                ExterminateCompletely.getInstance().getMobManager().displayBlacklist(source, serverLevel);
            } else {
                String message = Language.getInstance().getOrDefault("commands.ec.blacklist.not_server_level");
                source.sendSystemMessage(net.minecraft.network.chat.Component.literal(message));
            }
        } else {
            String message = Language.getInstance().getOrDefault("commands.ec.blacklist.not_player");
            source.sendSystemMessage(net.minecraft.network.chat.Component.literal(message));
        }
        
        return 1;
    }
    
    private static int executeLanguageShow(CommandContext<CommandSourceStack> context) {
        CommandSourceStack source = context.getSource();
        
        String currentLang = ExterminateCompletely.getInstance().getMobManager().getMappingManager().getCurrentLanguage();
        String currentLangName = Language.getInstance().getOrDefault("commands.ec.language." + currentLang);
        String enUsName = Language.getInstance().getOrDefault("commands.ec.language.en_us");
        String zhCnName = Language.getInstance().getOrDefault("commands.ec.language.zh_cn");
        String currentMsg = String.format(Language.getInstance().getOrDefault("commands.ec.language.current"), currentLangName);
        String availableMsg = String.format(Language.getInstance().getOrDefault("commands.ec.language.available"), enUsName + ", " + zhCnName);
        source.sendSystemMessage(net.minecraft.network.chat.Component.literal(currentMsg));
        source.sendSystemMessage(net.minecraft.network.chat.Component.literal(availableMsg));
        
        return 1;
    }
    
    private static int executeLanguageSet(CommandContext<CommandSourceStack> context) {
        CommandSourceStack source = context.getSource();
        String langCode = com.mojang.brigadier.arguments.StringArgumentType.getString(context, "langCode");
        
        // 设置语言
        ExterminateCompletely.getInstance().getMobManager().getMappingManager().setLanguage(langCode);
        String message = String.format(Language.getInstance().getOrDefault("commands.ec.language.set.success"), langCode);
        source.sendSystemMessage(net.minecraft.network.chat.Component.literal(message));
        
        // 如果执行者是玩家，重新加载映射表
        if (source.getEntity() instanceof net.minecraft.server.level.ServerPlayer) {
            net.minecraft.server.level.ServerPlayer player = (net.minecraft.server.level.ServerPlayer) source.getEntity();
            ExterminateCompletely.getInstance().getMobManager().reloadMappings(player);
        }
        
        return 1;
    }
}