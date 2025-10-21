package net.happyspeed.regenerating_world.features;

import com.mojang.serialization.Codec;
import net.happyspeed.regenerating_world.RegeneratingWorld;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class DisruptiveSquareFeature extends Feature<NoneFeatureConfiguration> {
    public DisruptiveSquareFeature(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> ctx) {
        WorldGenLevel level = ctx.level();
        RandomSource random = ctx.random();
        BlockPos origin = ctx.origin();

        int width = 8;

        boolean placed = false;

        for (int dx = -width; dx <= width; dx++) {
            for (int dy = -width; dy <= width; dy++) {
                for (int dz = -width; dz <= width; dz++) {
                    BlockPos pos = origin.offset(dx, dy, dz);

                    BlockState current = level.getBlockState(pos);

                    if (current.is(Blocks.BEDROCK) || current.isAir()) continue;

                    if (!current.is(BlockTags.MINEABLE_WITH_PICKAXE) && !current.is(BlockTags.MINEABLE_WITH_SHOVEL)) continue;

                    level.setBlock(pos, Blocks.AIR.defaultBlockState(), Block.UPDATE_NONE);

                    placed = true;

                }
            }
        }
        return placed;
    }

}
