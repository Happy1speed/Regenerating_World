package net.happyspeed.regenerating_world.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ComposterBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.annotation.Nullable;

import static net.minecraft.world.level.block.ComposterBlock.LEVEL;
import static net.minecraft.world.level.block.ComposterBlock.getValue;
import static net.minecraft.world.level.block.entity.HopperBlockEntity.addItem;

@Mixin(ComposterBlock.class)
public abstract class ComposterBlockMixin extends Block {
    public ComposterBlockMixin(Properties properties) {
        super(properties);
    }

    @Inject(method = "addItem", at = @At(value = "HEAD"), cancellable = true)
    private static void alwaysCompost(Entity entity, BlockState state, LevelAccessor level, BlockPos pos, ItemStack stack, CallbackInfoReturnable<BlockState> cir) {
        int i = (Integer)state.getValue(LEVEL);
        float f = getValue(stack);
        int j = i + 1;
        BlockState blockstate = (BlockState)state.setValue(LEVEL, j);
        level.setBlock(pos, blockstate, 3);
        level.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(entity, blockstate));
        if (j == 7) {
            level.scheduleTick(pos, state.getBlock(), 5);
        }
        cir.setReturnValue(state);
        cir.cancel();
    }



}
