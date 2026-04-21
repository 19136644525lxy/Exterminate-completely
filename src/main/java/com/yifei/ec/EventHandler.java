package com.yifei.ec;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class EventHandler {
    @SubscribeEvent
    public void onLivingDeath(LivingDeathEvent event) {
        LivingEntity entity = event.getEntity();
        Entity source = event.getSource().getEntity();
        
        if (entity instanceof Mob && source instanceof Player) {
            ExterminateCompletely.getInstance().getMobManager().addMobToBlacklist((Mob) entity);
        }
    }
    
    @SubscribeEvent
    public void onCheckSpawn(LivingSpawnEvent.CheckSpawn event) {
        if (event.getEntity() instanceof Mob) {
            Mob mob = (Mob) event.getEntity();
            if (!ExterminateCompletely.getInstance().getMobManager().canMobSpawn(mob)) {
                event.setCanceled(true);
            }
        }
    }
    
    @SubscribeEvent
    public void onSpecialSpawn(LivingSpawnEvent.SpecialSpawn event) {
        if (event.getEntity() instanceof Mob) {
            Mob mob = (Mob) event.getEntity();
            if (!ExterminateCompletely.getInstance().getMobManager().canMobSpawn(mob)) {
                event.setCanceled(true);
            }
        }
    }
    
    @SubscribeEvent
    public void onEntityJoinLevel(EntityJoinLevelEvent event) {
        if (event.getEntity() instanceof Mob) {
            Mob mob = (Mob) event.getEntity();
            if (!ExterminateCompletely.getInstance().getMobManager().canMobSpawn(mob)) {
                event.setCanceled(true);
            }
        }
    }
}
