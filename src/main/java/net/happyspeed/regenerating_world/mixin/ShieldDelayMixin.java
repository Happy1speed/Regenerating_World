package net.happyspeed.regenerating_world.mixin;

import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(LivingEntity.class)
public abstract class ShieldDelayMixin {

    @ModifyConstant(
        method = "isBlocking",
        constant = @Constant(intValue = 5)
    )
    private int modifyShieldDelay(int original) {
        return 0; // Change this to your desired delay in ticks
    }
}
