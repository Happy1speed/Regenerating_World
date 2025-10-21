package net.happyspeed.regenerating_world.mod_blocks;

import net.happyspeed.regenerating_world.RegeneratingWorld;
import net.happyspeed.regenerating_world.mod_items.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Clearable;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.TickingBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.CommonHooks;

public class SimpleGrowLightTickingEntity extends BlockEntity {


    public SimpleGrowLightTickingEntity(BlockPos pos, BlockState state) {
        super(ModItems.SIMPLE_GROW_LIGHT_BLOCK_ENTITY.get(), pos, state);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, SimpleGrowLightTickingEntity entity) {
        if (!level.isClientSide) {
            if (level.getGameTime() % (600 + level.random.nextInt(20)) == 0) {
                BlockPos underPos = new BlockPos(pos.getX(), pos.getY() - 2, pos.getZ());
                if (level.getBlockState(underPos).getBlock() instanceof BonemealableBlock bonemealableblock && bonemealableblock.isValidBonemealTarget(level, underPos, level.getBlockState(underPos))) {
                    if (level instanceof ServerLevel serverLevel) {
                        if (bonemealableblock.isBonemealSuccess(level, level.random, underPos, level.getBlockState(underPos))) {
                            bonemealableblock.performBonemeal((ServerLevel) level, level.random, underPos, level.getBlockState(underPos));
                            level.levelEvent(1505, underPos, 15);
                            for (int i = 0; i < 8; i++) {
                                serverLevel.sendParticles(
                                        ParticleTypes.COMPOSTER,
                                        (double) underPos.getX() + serverLevel.random.nextDouble(),
                                        (double) (underPos.getY() + 0.5),
                                        (double) underPos.getZ() + serverLevel.random.nextDouble(),
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
            }
        }
    }
}
