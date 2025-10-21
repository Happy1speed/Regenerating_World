package net.happyspeed.regenerating_world.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.happyspeed.regenerating_world.faces.ModeClassAccess;
import net.happyspeed.regenerating_world.mod_items.SingleBlockShovelItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MultiPlayerGameMode.class)
public abstract class MixinMultiPlayerGameMode implements ModeClassAccess {

    @Shadow private int destroyDelay;

    @ModifyExpressionValue(method = "startDestroyBlock", at = @At(value = "CONSTANT", args = "intValue=5"))
    private int injectCustomDelay(int original) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player != null) {
            ItemStack held = mc.player.getMainHandItem();
            if (held.getItem() instanceof SingleBlockShovelItem) {
                // Force a delay before the next block can be broken
                return this.destroyDelay = 100;
            }
        }
        return original;
    }

    @ModifyExpressionValue(method = "continueDestroyBlock", at = @At(value = "CONSTANT", args = "intValue=5", ordinal = 0))
    private int injectCustomDelay2(int original) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player != null) {
            ItemStack held = mc.player.getMainHandItem();
            if (held.getItem() instanceof SingleBlockShovelItem) {
                // Force a delay before the next block can be broken
                return this.destroyDelay = 100;
            }
        }
        return original;
    }

    @ModifyExpressionValue(method = "continueDestroyBlock", at = @At(value = "CONSTANT", args = "intValue=5", ordinal = 1))
    private int injectCustomDelay3(int original) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player != null) {
            ItemStack held = mc.player.getMainHandItem();
            if (held.getItem() instanceof SingleBlockShovelItem) {
                // Force a delay before the next block can be broken
                return this.destroyDelay = 100;
            }
        }
        return original;
    }

    @Inject(method = "startDestroyBlock", at = @At("HEAD"))
    private void startDestroy(BlockPos loc, Direction face, CallbackInfoReturnable<Boolean> cir) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player != null) {
            ItemStack held = mc.player.getMainHandItem();
            if (held.getItem() instanceof SingleBlockShovelItem) {
                // Force a delay before the next block can be broken
                this.destroyDelay = 100;
            }
        }
    }

    @Unique
    public int getDestroyDelay() {
        return this.destroyDelay;
    }

    @Unique
    public void setDestroyDelay(int _destroyDelay) {
        this.destroyDelay = _destroyDelay;
    }
}
