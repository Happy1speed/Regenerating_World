package net.happyspeed.regenerating_world.features;

import com.mojang.serialization.Codec;
import net.happyspeed.regenerating_world.RegeneratingWorld;
import net.happyspeed.regenerating_world.mod_items.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class DustLayerFeature extends Feature<NoneFeatureConfiguration> {
    public DustLayerFeature(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> ctx) {
        WorldGenLevel level = ctx.level();
        BlockPos origin = ctx.origin();

        int radiusX = 16;
        int radiusZ = 16;

        boolean placed = false;

        for (int dx = 0; dx <= radiusX; dx++) {
            for (int dz = 0; dz <= radiusZ; dz++) {

                BlockPos pos = origin.offset(dx, 0, dz);

                BlockState current = level.getBlockState(pos);

                if (level.getBlockState(new BlockPos(pos.getX(), pos.getY() - 1, pos.getZ())).isAir() || level.getBlockState(new BlockPos(pos.getX(), pos.getY() - 1, pos.getZ())).is(ModItems.SHEARMETAL_GRATE_BLOCK.get())) {
                    continue;
                }
                else {
                    level.setBlock(pos, ModItems.PARCHED_DIRT_BLOCK.get().defaultBlockState(), Block.UPDATE_NONE);
                }

                placed = true;

            }
        }
        return placed;
    }

}
