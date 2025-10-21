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

public class FernLightBlock extends Block implements EntityBlock {

    public FernLightBlock(Properties properties) {
        super(properties);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new FernLightTickingEntity(pos, state);
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return type == ModItems.FERN_LIGHT_BLOCK_ENTITY.get() && !level.isClientSide
            ? (lvl, pos, st, be) -> FernLightTickingEntity.tick(lvl, pos, st, (FernLightTickingEntity) be)
            : null;
    }


}
