package net.happyspeed.regenerating_world.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.world.food.FoodData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FoodData.class)
public class ReduceExhaustionMixin {


    @Shadow private float exhaustionLevel;

    @Inject(method = "addExhaustion", at = @At("HEAD"), cancellable = true)
    private void lessEx(float exhaustion, CallbackInfo ci) {
        this.exhaustionLevel = Math.min(this.exhaustionLevel + (exhaustion * 0.5f), 40.0f);
        ci.cancel();
    }
}
