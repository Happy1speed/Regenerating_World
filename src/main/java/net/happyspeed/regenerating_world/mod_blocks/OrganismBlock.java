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

import java.util.ArrayList;

public class OrganismBlock extends Block implements EntityBlock {


    public static final IntegerProperty LEVEL = IntegerProperty.create("level", 0, 32);

    ArrayList<Block> baseBlockList;
    ArrayList<Block> surfaceBlockList;
    ArrayList<Block> supportBlockList;

    public OrganismBlock(ArrayList<Block> baseBlockList, ArrayList<Block> surfaceBlockList, ArrayList<Block> supportBlockList, Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(LEVEL, 0));
        this.baseBlockList = baseBlockList;
        this.supportBlockList = supportBlockList;
        this.surfaceBlockList = surfaceBlockList;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(LEVEL);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new OrganismBlockTickingEntity(pos, state);
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return type == ModItems.REPLICATOR_BLOCK_ENTITY.get() && !level.isClientSide
                ? (lvl, pos, st, be) -> OrganismBlockTickingEntity.tick(lvl, pos, st, (OrganismBlockTickingEntity) be, baseBlockList, surfaceBlockList, supportBlockList)
                : null;
    }

}

