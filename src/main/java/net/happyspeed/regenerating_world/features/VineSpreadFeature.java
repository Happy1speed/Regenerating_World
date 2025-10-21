package net.happyspeed.regenerating_world.features;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.VineBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

import java.util.List;

public class VineSpreadFeature extends Feature<NoneFeatureConfiguration> {
    public VineSpreadFeature(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }


    public static BooleanProperty getVineFacing(Direction dir) {
        return switch (dir) {
            case NORTH -> VineBlock.NORTH;
            case SOUTH -> VineBlock.SOUTH;
            case EAST  -> VineBlock.EAST;
            case WEST  -> VineBlock.WEST;
            default -> throw new IllegalArgumentException("Vines can't face " + dir);
        };
    }



    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> ctx) {
        WorldGenLevel level = ctx.level();
        RandomSource random = ctx.random();
        BlockPos origin = ctx.origin();

        int radiusX = 5;
        int radiusY = 5;
        int radiusZ = 5;

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

                        if (!level.getBlockState(pos).isAir()) continue;

                        List<Direction> horizontal = List.of(Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST);
                        Direction chosen = horizontal.get(random.nextInt(horizontal.size()));

                        BooleanProperty facing = getVineFacing(chosen);

                        BlockState vineState = Blocks.VINE.defaultBlockState().setValue(facing, true);
                        if (vineState.canSurvive(level, pos)) {
                            level.setBlock(pos, vineState, 2);
                        }
                    }
                }
            }
        }
        return placed;
    }

}
