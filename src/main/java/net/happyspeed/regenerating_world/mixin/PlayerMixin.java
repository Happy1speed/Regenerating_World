package net.happyspeed.regenerating_world.mixin;

import net.happyspeed.regenerating_world.faces.PlayerClassAccess;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Player.class)
public abstract class PlayerMixin extends LivingEntity implements PlayerClassAccess {
    protected PlayerMixin(EntityType<? extends LivingEntity> entityType, Level level) {
        super(entityType, level);
    }

    @Unique
    int ticksOffGround = 0;

    @Inject(method = "tick", at = @At("HEAD"))
    public void playerHeadInject(CallbackInfo ci) {
        if (this.onGround()) {
            ticksOffGround = 0;
        }
        else {
            if (ticksOffGround < 21) {
                ticksOffGround++;
            }
        }
    }

    @Unique
    public int getTicksOffGround() {
        return this.ticksOffGround;
    }
}
