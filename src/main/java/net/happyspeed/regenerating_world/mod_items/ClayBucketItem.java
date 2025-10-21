package net.happyspeed.regenerating_world.mod_items;


import net.happyspeed.regenerating_world.RegeneratingWorld;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BucketPickup;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.NotNull;

public class ClayBucketItem extends BucketItem {
    public ClayBucketItem(Fluid content, Properties properties) {
        super(content, properties);
    }


    public static ItemStack getEmptySuccessItem2(ItemStack bucketStack, Player player) {
        return !player.hasInfiniteMaterials() ? new ItemStack(ModItems.CLAY_BUCKET.asItem()) : bucketStack;
    }

    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        BlockHitResult blockhitresult = getPlayerPOVHitResult(level, player, this.content == Fluids.EMPTY ? net.minecraft.world.level.ClipContext.Fluid.SOURCE_ONLY : net.minecraft.world.level.ClipContext.Fluid.NONE);
        BlockPos blockpos = blockhitresult.getBlockPos();
        Direction direction = blockhitresult.getDirection();
        BlockPos blockpos1 = blockpos.relative(direction);

        BlockState blockstate1;
        blockstate1 = level.getBlockState(blockpos);
        Block var14 = blockstate1.getBlock();

        if (blockhitresult.getType() == HitResult.Type.MISS) {
            return InteractionResultHolder.pass(itemstack);
        } else if (blockhitresult.getType() != HitResult.Type.BLOCK) {
            return InteractionResultHolder.pass(itemstack);
        } else {

            if (level.mayInteract(player, blockpos) && player.mayUseItemAt(blockpos1, direction, itemstack)) {
                ItemStack itemstack3;
                if (this.content == Fluids.EMPTY) {
                    if (!blockstate1.is(Blocks.WATER)) {
                        return InteractionResultHolder.pass(itemstack);
                    }
                    if (var14 instanceof BucketPickup) {
                        BucketPickup bucketpickup = (BucketPickup)var14;
                        itemstack3 = bucketpickup.pickupBlock(player, level, blockpos, blockstate1);
                        if (!itemstack3.isEmpty()) {
                            player.awardStat(Stats.ITEM_USED.get(this));
                            bucketpickup.getPickupSound(blockstate1).ifPresent((p_150709_) -> {
                                player.playSound(p_150709_, 1.0F, 1.0F);
                            });
                            level.gameEvent(player, GameEvent.FLUID_PICKUP, blockpos);
                            ItemStack itemstack2 = ItemUtils.createFilledResult(itemstack, player, ModItems.CLAY_WATER_BUCKET.toStack());
                            if (!level.isClientSide) {
                                CriteriaTriggers.FILLED_BUCKET.trigger((ServerPlayer) player, ModItems.CLAY_WATER_BUCKET.toStack());
                            }

                            return InteractionResultHolder.sidedSuccess(itemstack2, level.isClientSide());
                        }
                    }

                    return InteractionResultHolder.fail(itemstack);
                } else {
                    blockstate1 = level.getBlockState(blockpos);
                    BlockPos blockpos2 = this.canBlockContainFluid(player, level, blockpos, blockstate1) ? blockpos : blockpos1;
                    if (this.emptyContents(player, level, blockpos2, blockhitresult, itemstack)) {
                        this.checkExtraContent(player, level, itemstack, blockpos2);
                        if (player instanceof ServerPlayer) {
                            CriteriaTriggers.PLACED_BLOCK.trigger((ServerPlayer)player, blockpos2, itemstack);
                        }

                        player.awardStat(Stats.ITEM_USED.get(this));
                        itemstack3 = ItemUtils.createFilledResult(itemstack, player, getEmptySuccessItem2(itemstack, player));
                        return InteractionResultHolder.sidedSuccess(itemstack3, level.isClientSide());
                    } else {
                        return InteractionResultHolder.fail(itemstack);
                    }
                }
            } else {
                return InteractionResultHolder.fail(itemstack);
            }
        }
    }
}
