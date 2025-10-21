package net.happyspeed.regenerating_world.mod_blocks;

import net.happyspeed.regenerating_world.RegeneratingWorld;
import net.happyspeed.regenerating_world.mod_items.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Container;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentEffectComponents;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Unique;

public class CompfusionMatrixTickingEntity extends BlockEntity {


    private boolean wasPowered = false;

    public CompfusionMatrixTickingEntity(BlockPos pos, BlockState state) {
        super(ModItems.COMPFUSION_MATRIX_BLOCK_ENTITY.get(), pos, state);
    }


    public void onNeighborSignalChange(boolean isPowered) {
        if (isPowered && !wasPowered) {
            onRedstonePulse();
        }
        wasPowered = isPowered;
    }


    @Unique
    private void handleItems(Item[] items, int[] inputs, Item result, Container container, BlockPos containerPosition) {
        boolean rightItemAmountFlag = true;
        for (int i = 0; i < items.length; i++) {
            Item curItem = items[i];
            if (!(container.countItem(curItem) >= inputs[i])) {
                rightItemAmountFlag = false;
            }
        }

        if (rightItemAmountFlag) {
            for (int j = 0; j < items.length; j++) {
                Item curItem = items[j];
                CompfusionMatrixTickingEntity.clearInventoryOfItem(container, curItem, inputs[j]);
            }

            int trackedSlot = CompfusionMatrixTickingEntity.isContainerNotFullSlot(container, result);
            if (trackedSlot != -1) {
                ItemStack newItem = new ItemStack(result, container.getItem(trackedSlot).getCount() + 1);
                container.setItem(trackedSlot, newItem);
            }
            else {
                //There is pretty much no universe where this could ever happen, but it is good logic.
                Block.popResource(level, containerPosition, new ItemStack(result, 1));
            }
            if (level instanceof ServerLevel serverLevel) {
                level.playSound(null, containerPosition, SoundEvents.PLAYER_LEVELUP, SoundSource.BLOCKS, 1.0f, 1.0f);
                level.playSound(null, containerPosition, SoundEvents.PISTON_EXTEND, SoundSource.BLOCKS, 1.0f, 0.85f);
                for (int i = 0; i < 20; i++) {
                    serverLevel.sendParticles(
                            ParticleTypes.TOTEM_OF_UNDYING,
                            (double) containerPosition.getX() + serverLevel.random.nextDouble(),
                            (double) (containerPosition.getY() + 0.5),
                            (double) containerPosition.getZ() + serverLevel.random.nextDouble(),
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

    public void onRedstonePulse() {
        if (this.level != null) {
            BlockPos containerPosition = new BlockPos(this.getBlockPos().getX(), this.getBlockPos().getY() + 1, this.getBlockPos().getZ());

            if (level.getBlockEntity(containerPosition) instanceof Container container) {
                //Manual Recipe
                //Compfusing? I find it quite easy to understand.
                BlockState modeBlock = level.getBlockState(new BlockPos(this.getBlockPos().getX(), this.getBlockPos().getY() - 1, this.getBlockPos().getZ()));
                if (modeBlock.is(Blocks.SAND)) {
                    handleItems(new Item[]{Items.SAND.asItem(), Items.AMETHYST_SHARD.asItem()}, new int[]{128, 64}, ModItems.MINERAL_SAND_BLOCK_ITEM.get(), container, containerPosition);
                }
                if (modeBlock.is(Blocks.PACKED_MUD)) {
                    handleItems(new Item[]{Items.PACKED_MUD.asItem(), Items.AMETHYST_SHARD.asItem()}, new int[]{128, 64}, ModItems.MINERAL_PACKED_MUD_BLOCK_ITEM.get(), container, containerPosition);
                }
                if (modeBlock.is(Blocks.MUD)) {

                    handleItems(new Item[]{Items.MUD.asItem(), Items.AMETHYST_SHARD.asItem()}, new int[]{128, 64}, ModItems.MINERAL_MUD_BLOCK_ITEM.get(), container, containerPosition);
                }
                if (modeBlock.is(Blocks.COARSE_DIRT)) {
                    handleItems(new Item[]{Items.DIRT.asItem(), Items.AMETHYST_SHARD.asItem()}, new int[]{128, 64}, ModItems.MINERAL_DIRT_BLOCK_ITEM.get(), container, containerPosition);
                }
                if (modeBlock.is(Blocks.DIRT)) {
                    handleItems(new Item[]{Items.GRASS_BLOCK.asItem(), Items.AMETHYST_SHARD.asItem()}, new int[]{128, 64}, ModItems.MINERAL_GRASS_BLOCK_ITEM.get(), container, containerPosition);
                }
                if (modeBlock.is(Blocks.ROOTED_DIRT)) {
                    handleItems(new Item[]{Items.GRASS_BLOCK.asItem(), Items.AMETHYST_SHARD.asItem()}, new int[]{128, 64}, ModItems.CLEAN_MINERAL_GRASS_BLOCK_ITEM.get(), container, containerPosition);
                }
                if (modeBlock.is(Blocks.BONE_BLOCK)) {
                    handleItems(new Item[]{Items.BONE_MEAL.asItem()}, new int[]{512}, ModItems.BONE_MASH_BUNDLE_ITEM.get(), container, containerPosition);
                }
            }
        }
    }

    public static void clearInventoryOfItem(Container container, Item item, int max) {
        int itemsToDelete = max;
        for (int i = 0; i < container.getContainerSize(); i++) {
            ItemStack itemstack = container.getItem(i);
            if (itemsToDelete == 0) {
                break;
            }
            if (itemstack.is(item)) {
                itemsToDelete -= itemstack.getCount();
                container.removeItemNoUpdate(i);
            }
        }
    }

    public static int isContainerNotFullSlot(Container container, Item item) {
        int openSlot = -1;
        for (int i = 0; i < container.getContainerSize(); i++) {
            ItemStack itemstack = container.getItem(i);
            if (itemstack.isEmpty() || (itemstack.is(item) && itemstack.getCount() < itemstack.getMaxStackSize())) {
                openSlot = i;
                break;
            }
        }
        return openSlot;
    }

}
