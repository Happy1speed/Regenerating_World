package net.happyspeed.regenerating_world.mod_blocks;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.TintedGlassBlock;
import net.minecraft.world.level.block.TransparentBlock;
import net.minecraft.world.level.block.state.BlockState;

public class TungstenGlassBlock extends TransparentBlock {

    public static final MapCodec<TungstenGlassBlock> CODEC = simpleCodec(TungstenGlassBlock::new);

    public MapCodec<TungstenGlassBlock> codec() {
        return CODEC;
    }


    public TungstenGlassBlock(Properties properties) {
        super(properties);
    }

    @Override
    public boolean propagatesSkylightDown(BlockState state, BlockGetter reader, BlockPos pos) {
        return true;                               // Let skylight through
    }

    @Override
    public boolean useShapeForLightOcclusion(BlockState state) {
        return false;
    }
}
