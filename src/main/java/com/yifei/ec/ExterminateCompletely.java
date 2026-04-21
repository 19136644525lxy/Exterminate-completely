package com.yifei.ec;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.event.server.ServerStoppingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod("ec")
public class ExterminateCompletely {
    public static final String MOD_ID = "ec";
    private static ExterminateCompletely instance;
    private MobManager mobManager;
    
    public ExterminateCompletely() {
        instance = this;
        mobManager = new MobManager();
        
        MinecraftForge.EVENT_BUS.register(new EventHandler());
        MinecraftForge.EVENT_BUS.register(this);
        
        MobExterminationConfig.loadConfig();
        System.out.println("[EC] ExterminateCompletely initialized");
    }
    
    public static ExterminateCompletely getInstance() {
        return instance;
    }
    
    public MobManager getMobManager() {
        return mobManager;
    }
    
    @net.minecraftforge.eventbus.api.SubscribeEvent
    public void onRegisterCommands(RegisterCommandsEvent event) {
        CommandManager.register(event.getDispatcher());
        System.out.println("[EC] Commands registered");
    }
    
    @net.minecraftforge.eventbus.api.SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        System.out.println("[EC] Server starting, reloading blacklist");
        // 在服务器启动时重新创建MobManager实例，确保黑名单被重置
        mobManager = new MobManager();
        mobManager.loadBlacklist();
        System.out.println("[EC] Server started, blacklist reloaded");
    }
    
    @net.minecraftforge.eventbus.api.SubscribeEvent
    public void onServerStopping(net.minecraftforge.event.server.ServerStoppingEvent event) {
        System.out.println("[EC] Server stopping, saving blacklist");
        if (mobManager != null) {
            mobManager.saveBlacklist();
        }
        System.out.println("[EC] Server stopped, blacklist saved");
    }
}
