package net.happyspeed.regenerating_world.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LeavesBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LeavesBlock.class)
public abstract class LeavesBlockMixin extends Block {

    public LeavesBlockMixin(Properties properties) {
        super(properties);
    }

    @ModifyExpressionValue(method = "<init>", at = @At(value = "CONSTANT", args = "intValue=7", ordinal = 0))
    private int modifyConstantLeafDistance2(int original) {
        return 15;
    }

    @ModifyExpressionValue(method = "updateDistance", at = @At(value = "CONSTANT", args = "intValue=7", ordinal = 0))
    private static int modifyConstantLeafDistance(int original) {
        return 15;
    }

    @ModifyExpressionValue(method = "isRandomlyTicking", at = @At(value = "CONSTANT", args = "intValue=7", ordinal = 0))
    private int modifyConstantLeafDistance3(int original) {
        return 15;
    }

    @ModifyExpressionValue(method = "decaying", at = @At(value = "CONSTANT", args = "intValue=7", ordinal = 0))
    private int modifyConstantLeafDistance4(int original) {
        return 15;
    }

    @ModifyExpressionValue(method = "getDistanceAt", at = @At(value = "CONSTANT", args = "intValue=7", ordinal = 0))
    private static int modifyConstantLeafDistance5(int original) {
        return 15;
    }
}
