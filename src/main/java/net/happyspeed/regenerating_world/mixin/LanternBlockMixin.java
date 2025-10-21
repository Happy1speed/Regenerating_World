package net.happyspeed.regenerating_world.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LanternBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LanternBlock.class)
public abstract class LanternBlockMixin extends Block {
    public LanternBlockMixin(Properties properties) {
        super(properties);
    }

    //YIPPIE LANTERNS ON LEAVES

    @Inject(method = "canSurvive", at = @At("HEAD"), cancellable = true)
    private void allowLanternOnLeaves(BlockState state, LevelReader level, BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        Direction direction = state.getValue(LanternBlock.HANGING) ? Direction.UP : Direction.DOWN;
        BlockPos supportPos = pos.relative(direction);
        BlockState supportState = level.getBlockState(supportPos);

        if (supportState.is(BlockTags.LEAVES)) {
            cir.setReturnValue(true);
        }
    }
}
