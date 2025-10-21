package net.happyspeed.regenerating_world.mod_blocks;

import net.happyspeed.regenerating_world.RegeneratingWorld;
import net.happyspeed.regenerating_world.mod_items.ModItems;
import net.happyspeed.regenerating_world.util.ModTags;
import net.happyspeed.regenerating_world.util.TagSearcher;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import static net.happyspeed.regenerating_world.mod_blocks.MineralGrassBlock.LEVEL;

public class MineralGrassTickingEntity extends BlockEntity {


    public MineralGrassTickingEntity(BlockPos pos, BlockState state) {
        super(ModItems.MINERAL_GRASS_BLOCK_ENTITY.get(), pos, state);
    }

    private int timesSplit = 0;


    public static void placeBlockAllAround(Level level, BlockPos pos, BlockState state, MineralGrassTickingEntity entity) {
        if (!level.isClientSide) {
            if (level instanceof ServerLevel serverLevel) {
                if (level.getBlockState(pos.above()).isAir()) {
                    level.setBlockAndUpdate(pos, Blocks.GRASS_BLOCK.defaultBlockState());
                } else {
                    level.setBlockAndUpdate(pos,  TagSearcher.getRandomBlockFromTag(level, RegeneratingWorld.MODID, "mineral_dirt_mix", Blocks.AIR).defaultBlockState());
                }
                if (!level.isAreaLoaded(pos, 2)) {
                    return;
                }
                for (int x = -1; x <= 1; x++) {
                    for (int y = -1; y <= 1; y++) {
                        for (int z = -1; z <= 1; z++) {
                            BlockPos adjacentPos = new BlockPos(pos.getX() + x, pos.getY() + y, pos.getZ() + z);
                            if (level.getBlockState(adjacentPos).is(ModTags.Blocks.REGENERATING_SURFACE_BLOCK_REPLACABLES)) {
                                if (level.getBlockState(adjacentPos.above()).isAir()) {
                                    level.setBlockAndUpdate(adjacentPos, Blocks.GRASS_BLOCK.defaultBlockState());
                                } else {
                                    level.setBlockAndUpdate(adjacentPos,  TagSearcher.getRandomBlockFromTag(level, RegeneratingWorld.MODID, "mineral_dirt_mix", Blocks.AIR).defaultBlockState());
                                }
                                if (level.random.nextInt(10) == 1) {
                                    serverLevel.playSound(null, adjacentPos.getX(), adjacentPos.getY(), adjacentPos.getZ(),
                                            SoundEvents.ROOTED_DIRT_PLACE, SoundSource.BLOCKS, 0.7F, 1.0F);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public static void tick(Level level, BlockPos pos, BlockState state, MineralGrassTickingEntity entity) {
        if (!level.isClientSide) {
            if (level instanceof ServerLevel serverLevel) {
                if (level.getGameTime() % (5 + level.random.nextInt(3)) == 0) {


                    if (!level.isAreaLoaded(pos, 2)) {
                        return;
                    }

                    Direction randDirection = Direction.getRandom(level.random);
                    BlockPos adjacentPos = pos.relative(randDirection);

                    if (state.getValue(LEVEL) < 32) {
                        if (level.getBlockState(adjacentPos).is(ModTags.Blocks.REGENERATING_SURFACE_BLOCK_REPLACABLES)) {
                            level.setBlock(adjacentPos, ModItems.MINERAL_GRASS_BLOCK.get().defaultBlockState(), 3);
                            level.setBlockAndUpdate(adjacentPos, state.setValue(LEVEL, state.getValue(LEVEL) + 1));
                            if (level.random.nextInt(10) == 1) {
                                serverLevel.playSound(null, adjacentPos.getX(), adjacentPos.getY(), adjacentPos.getZ(),
                                        SoundEvents.ROOTED_DIRT_PLACE, SoundSource.BLOCKS, 0.7F, 1.0F);
                            }
                        }
                        entity.timesSplit ++;
                        if (entity.timesSplit > 12 && level.random.nextInt(5) == 1) {
                            MineralGrassTickingEntity.placeBlockAllAround(level, pos, state, entity);
                        }
                    }
                    else {
                        MineralGrassTickingEntity.placeBlockAllAround(level, pos, state, entity);
                    }
                }
            }
        }
    }

}
