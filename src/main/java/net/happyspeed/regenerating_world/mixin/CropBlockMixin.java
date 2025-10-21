package net.happyspeed.regenerating_world.mixin;

import net.happyspeed.regenerating_world.RegeneratingWorld;
import net.happyspeed.regenerating_world.util.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CropBlock.class)
public abstract class CropBlockMixin {

    @Inject(method = "randomTick", at = @At("HEAD"))
    private void boostGrowthOnTaggedSoil(BlockState state, ServerLevel level, BlockPos pos, RandomSource random, CallbackInfo ci) {
        // Check block below
        BlockState below = level.getBlockState(pos.below());
        int skyLight = level.getBrightness(LightLayer.SKY, pos);
        if (skyLight > 5) {
            if (below.is(ModTags.Blocks.DECENT_GROWING_SOIL)) {
                if (random.nextInt(5) == 0) {
                    CropBlock crop = (CropBlock) (Object) this;
                    int age = crop.getAge(state);
                    if (age < crop.getMaxAge()) {
                        level.setBlock(pos, crop.getStateForAge(age + 1), Block.UPDATE_CLIENTS);
                    }
                }
            }
            else if (below.is(ModTags.Blocks.GREAT_GROWING_SOIL)) {
                if (random.nextInt(2) == 0) {
                    CropBlock crop = (CropBlock) (Object) this;
                    int age = crop.getAge(state);
                    if (age < crop.getMaxAge()) {
                        level.setBlock(pos, crop.getStateForAge(age + 1), Block.UPDATE_CLIENTS);
                    }
                }
            }
            else if (below.is(ModTags.Blocks.FERTILE_GROWING_SOIL)) {
                CropBlock crop = (CropBlock) (Object) this;
                int age = crop.getAge(state);
                if (age < crop.getMaxAge()) {
                    level.setBlock(pos, crop.getStateForAge(age + 1), Block.UPDATE_CLIENTS);
                }
            }
        }
    }
}

