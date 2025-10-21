package net.happyspeed.regenerating_world.mod_blocks;

import net.happyspeed.regenerating_world.RegeneratingWorld;
import net.happyspeed.regenerating_world.mod_items.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

public class CleanMineralGrassBlock extends Block implements EntityBlock {


    public static final IntegerProperty LEVEL = IntegerProperty.create("level", 0, 32);


    public CleanMineralGrassBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(LEVEL, 0));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(LEVEL);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new CleanMineralGrassTickingEntity(pos, state);
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return type == ModItems.CLEAN_MINERAL_GRASS_BLOCK_ENTITY.get() && !level.isClientSide
            ? (lvl, pos, st, be) -> CleanMineralGrassTickingEntity.tick(lvl, pos, st, (CleanMineralGrassTickingEntity) be)
            : null;
    }

}
