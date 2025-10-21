package net.happyspeed.regenerating_world.mixin;

import net.happyspeed.regenerating_world.faces.LivingEntityClassAccess;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity implements LivingEntityClassAccess {
    public LivingEntityMixin(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }

    @Unique
    public int portalWarmUpTicks = 0;


    @Unique
    public int getPortalWarmUpTicks() {
        return this.portalWarmUpTicks;
    }

    @Unique
    public void setPortalWarmUpTicks(int p) {
        this.portalWarmUpTicks = p;
    }

    @Inject(method = "tick", at = @At("HEAD"))
    public void managePortalWarmUpTicks(CallbackInfo ci) {
        if (level().getGameTime() % 5 == 0 && this.portalWarmUpTicks > 0) {
            this.portalWarmUpTicks--;
        }
    }
}
