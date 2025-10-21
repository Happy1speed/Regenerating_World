package net.happyspeed.regenerating_world.features;

import com.mojang.serialization.Codec;
import net.happyspeed.regenerating_world.RegeneratingWorld;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class ClayVeinFeature extends Feature<NoneFeatureConfiguration> {
    public ClayVeinFeature(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> ctx) {
        WorldGenLevel level = ctx.level();
        RandomSource random = ctx.random();
        BlockPos origin = ctx.origin();

        int radiusX = 3 + random.nextInt(3); // blob radius in X
        int radiusY = 2 + random.nextInt(2); // blob radius in Y
        int radiusZ = 3 + random.nextInt(3); // blob radius in Z

        boolean placed = false;

        for (int dx = -radiusX; dx <= radiusX; dx++) {
            for (int dy = -radiusY; dy <= radiusY; dy++) {
                for (int dz = -radiusZ; dz <= radiusZ; dz++) {
                    double normX = dx / (double) radiusX;
                    double normY = dy / (double) radiusY;
                    double normZ = dz / (double) radiusZ;

                    // Inside an ellipsoid with some fuzz
                    if (normX * normX + normY * normY + normZ * normZ <= 1.0 + random.nextFloat() * 0.2f) {
                        BlockPos pos = origin.offset(dx, dy, dz);

                        BlockState current = level.getBlockState(pos);

                        if (current.isAir() || current.is(Blocks.WATER) || level.getBlockState(pos.above()).isAir() || level.getBlockState(pos.above(2)).isAir()) continue;
                        level.setBlock(pos, Blocks.CLAY.defaultBlockState(), Block.UPDATE_NONE);
                        placed = true;
                    }
                }
            }
        }
        return placed;
    }

}
