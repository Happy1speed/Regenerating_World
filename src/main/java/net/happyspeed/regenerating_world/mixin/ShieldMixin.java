package net.happyspeed.regenerating_world.mixin;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Player.class)
public abstract class ShieldMixin extends LivingEntity {
    @Shadow public abstract void disableShield();

    protected ShieldMixin(EntityType<? extends LivingEntity> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(method = "blockUsingShield", at = @At("HEAD"), cancellable = true)
    public void changeBlockDisable(LivingEntity entity, CallbackInfo ci) {
        super.blockUsingShield(entity);
        entity.knockback(0.8, this.getX() - entity.getX(), this.getZ() - entity.getZ());
        this.disableShield();
    }

    @Redirect(method = "hurtCurrentlyUsedShield", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;playSound(Lnet/minecraft/sounds/SoundEvent;FF)V"))
    public void redirectSoundPlay(Player instance, SoundEvent sound, float volume, float pitch) {
        this.playSound(SoundEvents.ARMOR_EQUIP_NETHERITE.value(), 0.6F, 0.8F + this.level().random.nextFloat() * 0.4F);
    }
}
