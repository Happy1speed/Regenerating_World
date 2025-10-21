package net.happyspeed.regenerating_world.mod_blocks;

import net.happyspeed.regenerating_world.RegeneratingWorld;
import net.happyspeed.regenerating_world.mod_items.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class FernLightTickingEntity extends BlockEntity {


    public FernLightTickingEntity(BlockPos pos, BlockState state) {
        super(ModItems.FERN_LIGHT_BLOCK_ENTITY.get(), pos, state);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, FernLightTickingEntity entity) {
        if (!level.isClientSide) {
            if (level.getGameTime() % 20 == 0) {
                BlockPos underPos = new BlockPos(pos.getX(), pos.getY() + 1, pos.getZ());
                if (level.getBlockState(underPos).is(Blocks.PODZOL)) {
                    if (level instanceof ServerLevel serverLevel) {
                        BlockPos placePos = new BlockPos(pos.getX(), pos.getY() + 2, pos.getZ());
                        if (level.getBlockState(placePos).isAir() && level.random.nextInt(12) == 1) {
                            level.setBlock(placePos, Blocks.FERN.defaultBlockState(), 2);
                            level.levelEvent(1505, underPos, 15);
                            for (int i = 0; i < 8; i++) {
                                serverLevel.sendParticles(
                                        ParticleTypes.COMPOSTER,
                                        (double) placePos.getX() + serverLevel.random.nextDouble(),
                                        (double) (placePos.getY() + 0.5),
                                        (double) placePos.getZ() + serverLevel.random.nextDouble(),
                                        1,
                                        0.0,
                                        0.0,
                                        0.0,
                                        1.0
                                );
                            }
                        }
                    }
                }

                BlockPos growPos = new BlockPos(pos.getX(), pos.getY() + 2, pos.getZ());
                if (level.getBlockState(growPos).getBlock() instanceof BonemealableBlock bonemealableblock && bonemealableblock.isValidBonemealTarget(level, growPos, level.getBlockState(growPos)) && level.getBlockState(growPos).is(Blocks.FERN) && level.random.nextInt(12) == 1) {
                    if (level instanceof ServerLevel serverLevel) {
                        if (bonemealableblock.isBonemealSuccess(level, level.random, growPos, level.getBlockState(growPos))) {
                            bonemealableblock.performBonemeal((ServerLevel) level, level.random, growPos, level.getBlockState(growPos));
                            level.levelEvent(1505, growPos, 15);
                            for (int i = 0; i < 8; i++) {
                                serverLevel.sendParticles(
                                        ParticleTypes.COMPOSTER,
                                        (double) growPos.getX() + serverLevel.random.nextDouble(),
                                        (double) (growPos.getY() + 0.5),
                                        (double) growPos.getZ() + serverLevel.random.nextDouble(),
                                        1,
                                        0.0,
                                        0.0,
                                        0.0,
                                        1.0
                                );
                            }
                        }
                    }
                }
            }
        }
    }
}
