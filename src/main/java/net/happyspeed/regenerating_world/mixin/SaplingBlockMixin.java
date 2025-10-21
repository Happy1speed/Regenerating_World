package net.happyspeed.regenerating_world.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.happyspeed.regenerating_world.util.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SaplingBlock.class)
public abstract class SaplingBlockMixin {


    @Inject(
        method = "randomTick",
        at = @At("HEAD"),
        cancellable = true
    )
    private void regeneratingWorld$boostGrowth(BlockState state, ServerLevel level, BlockPos pos, RandomSource random, CallbackInfo ci) {
        BlockState below = level.getBlockState(pos.below());
        if (level.getBrightness(LightLayer.SKY, pos) > 5) {
            if (below.is(ModTags.Blocks.DECENT_GROWING_SOIL)) {
                if (random.nextInt(5) == 0) {
                    ((SaplingBlock) (Object) this).advanceTree(level, pos, state, random);
                    ci.cancel();
                }
            }
            else if (below.is(ModTags.Blocks.GREAT_GROWING_SOIL)) {
                if (random.nextInt(3) == 0) {
                    ((SaplingBlock) (Object) this).advanceTree(level, pos, state, random);
                    ci.cancel();
                }
            }
            else if (below.is(ModTags.Blocks.FERTILE_GROWING_SOIL)) {
                ((SaplingBlock) (Object) this).advanceTree(level, pos, state, random);
                ci.cancel();
            }
            else if (state.is(ModTags.Blocks.CONIFER_SAPLING) && below.is(ModTags.Blocks.CONIFER_GROWING_SOIL)) {
                ((SaplingBlock) (Object) this).advanceTree(level, pos, state, random);
                ci.cancel();
            }
        }
    }

    @ModifyExpressionValue(method = "randomTick", at = @At(value = "CONSTANT", args = "intValue=7"))
    private int modifyGrowth(int original, @Local(ordinal = 0, argsOnly = true) ServerLevel level, @Local(ordinal = 0, argsOnly = true) BlockPos pos, @Local(ordinal = 0, argsOnly = true) BlockState state) {
        if (level.getBrightness(LightLayer.SKY, pos) > 5 || state.is(ModTags.Blocks.PITCH_BLACK_GROWABLE_SAPLING)) {
            return original;
        }
        else {
            return 80;
        }
    }

    @Inject(method = "performBonemeal", at = @At(value = "HEAD"), cancellable = true)
    private void modifyBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state, CallbackInfo ci) {
        if (!(level.getBrightness(LightLayer.SKY, pos) > 5) && !state.is(ModTags.Blocks.PITCH_BLACK_GROWABLE_SAPLING)) {
            ci.cancel();
        }
    }
}
