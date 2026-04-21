package com.yifei.ec;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;

import java.util.ArrayList;
import java.util.List;

public class MobExterminationConfig {
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    private static final ForgeConfigSpec.ConfigValue<List<String>> WHITELISTED_MOBS = BUILDER
            .comment("List of mobs that can still spawn even after being killed")
            .define("whitelistedMobs", new ArrayList<>());
    public static final ForgeConfigSpec SPEC = BUILDER.build();
    
    public static void loadConfig() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, SPEC, "ec/ec-whitelist.toml");
    }
    
    public static List<String> getWhitelistedMobs() {
        return WHITELISTED_MOBS.get();
    }
    
    public static void addToWhitelist(String mobId) {
        List<String> whitelist = WHITELISTED_MOBS.get();
        if (!whitelist.contains(mobId)) {
            whitelist.add(mobId);
            WHITELISTED_MOBS.set(whitelist);
        }
    }
}
