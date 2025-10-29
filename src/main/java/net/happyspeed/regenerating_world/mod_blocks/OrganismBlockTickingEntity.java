package net.happyspeed.regenerating_world.mod_blocks;


import net.happyspeed.regenerating_world.mod_items.ModItems;
import net.happyspeed.regenerating_world.util.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;

import static net.happyspeed.regenerating_world.mod_blocks.OrganismBlock.LEVEL;

public class OrganismBlockTickingEntity extends BlockEntity {

    public OrganismBlockTickingEntity(BlockPos pos, BlockState state) {
        super(ModItems.REPLICATOR_BLOCK_ENTITY.get(), pos, state);

    }

    private int timesSplit = 0;


    public static void placeBlockAllAround(Level level, BlockPos pos, BlockState state, OrganismBlockTickingEntity entity, ArrayList<Block> baseBlockList, ArrayList<Block> surfaceBlockList, ArrayList<Block> supportBlockList) {
        if (!level.isClientSide) {
            if (level instanceof ServerLevel serverLevel) {

                Block baseBlock1;

                if (!supportBlockList.isEmpty() && level.getBlockState(pos.below()).isAir()) {
                    baseBlock1 = supportBlockList.get(level.random.nextInt(supportBlockList.size()));
                }

                else if (!surfaceBlockList.isEmpty() && level.getBlockState(pos.above()).isAir()) {
                    baseBlock1 = surfaceBlockList.get(level.random.nextInt(surfaceBlockList.size()));
                }

                else {
                    baseBlock1 = baseBlockList.get(level.random.nextInt(baseBlockList.size()));
                }

                level.setBlockAndUpdate(pos, baseBlock1.defaultBlockState());


                if (!level.isAreaLoaded(pos, 2)) {
                    return;
                }
                for (int x = -1; x <= 1; x++) {
                    for (int y = -1; y <= 1; y++) {
                        for (int z = -1; z <= 1; z++) {
                            BlockPos adjacentPos = new BlockPos(pos.getX() + x, pos.getY() + y, pos.getZ() + z);
                            if (level.getBlockState(adjacentPos).is(ModTags.Blocks.REGENERATING_SURFACE_BLOCK_REPLACABLES)) {
                                Block baseBlock2;

                                if (!supportBlockList.isEmpty() && level.getBlockState(adjacentPos.below()).isAir()) {
                                    baseBlock2 = supportBlockList.get(level.random.nextInt(supportBlockList.size()));
                                }

                                else if (!surfaceBlockList.isEmpty() && level.getBlockState(adjacentPos.above()).isAir()) {
                                    baseBlock2 = surfaceBlockList.get(level.random.nextInt(surfaceBlockList.size()));
                                }

                                else {
                                    baseBlock2 = baseBlockList.get(level.random.nextInt(baseBlockList.size()));
                                }

                                level.setBlockAndUpdate(adjacentPos, baseBlock2.defaultBlockState());


                                if (level.random.nextInt(10) == 1) {
                                    serverLevel.playSound(null, adjacentPos.getX(), adjacentPos.getY(), adjacentPos.getZ(),
                                            SoundEvents.BONE_MEAL_USE, SoundSource.BLOCKS, 0.7F, 1.0F);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public static void tick(Level level, BlockPos pos, BlockState state, OrganismBlockTickingEntity entity, ArrayList<Block> baseBlockList, ArrayList<Block> surfaceBlockList, ArrayList<Block> supportBlockList) {
        if (!level.isClientSide) {
            if (level instanceof ServerLevel serverLevel) {
                if (level.getGameTime() % (5 + level.random.nextInt(3)) == 0) {


                    if (!level.isAreaLoaded(pos, 2)) {
                        return;
                    }

                    Direction randDirection = Direction.getRandom(level.random);
                    BlockPos adjacentPos = pos.relative(randDirection);

                    if (state.getValue(LEVEL) > 0) {
                        if (level.getBlockState(adjacentPos).is(ModTags.Blocks.REGENERATING_SURFACE_BLOCK_REPLACABLES)) {

                            level.setBlock(adjacentPos, state, 3);


                            level.setBlockAndUpdate(adjacentPos, state.setValue(LEVEL, state.getValue(LEVEL) - 1));

                            if (level.random.nextInt(10) == 1) {
                                serverLevel.playSound(null, adjacentPos.getX(), adjacentPos.getY(), adjacentPos.getZ(),
                                        SoundEvents.BONE_MEAL_USE, SoundSource.BLOCKS, 0.7F, 1.0F);
                            }
                        }
                        entity.timesSplit ++;
                        if (entity.timesSplit > 12 && level.random.nextInt(5) == 1) {
                            OrganismBlockTickingEntity.placeBlockAllAround(level, pos, state, entity, baseBlockList, surfaceBlockList, supportBlockList);
                        }
                    }
                    else {
                        OrganismBlockTickingEntity.placeBlockAllAround(level, pos, state, entity, baseBlockList, surfaceBlockList, supportBlockList);
                    }
                }
            }
        }
    }
}

