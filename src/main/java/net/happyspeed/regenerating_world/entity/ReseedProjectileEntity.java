package net.happyspeed.regenerating_world.entity;

import net.happyspeed.regenerating_world.RegeneratingWorld;
import net.happyspeed.regenerating_world.mod_items.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class ReseedProjectileEntity extends ThrowableItemProjectile {
    public ReseedProjectileEntity(EntityType<? extends ThrowableItemProjectile> type, Level level) {
        super(type, level);
    }

    @Override
    protected Item getDefaultItem() {
        return ModItems.RESEED_THROWABLE_ITEM.get();
    }

    @Override
    protected void onHitBlock(BlockHitResult result) {
        super.onHitBlock(result);
        int radiusX = 3;
        int radiusY = 3;
        int radiusZ = 3;

        BlockPos origin = result.getBlockPos();
        Level level = this.level();

        for (int dx = -radiusX; dx <= radiusX; dx++) {
            for (int dy = -radiusY; dy <= radiusY; dy++) {
                for (int dz = -radiusZ; dz <= radiusZ; dz++) {
                    double normX = dx / (double) radiusX;
                    double normY = dy / (double) radiusY;
                    double normZ = dz / (double) radiusZ;

                    // Inside an ellipsoid with some fuzz
                    if (normX * normX + normY * normY + normZ * normZ <= 1.0 + random.nextFloat() * 0.2f) {
                        BlockPos pos = origin.offset(dx, dy, dz);

                        if (!level.getBlockState(pos).isAir()) continue;

                        if (!(level.random.nextInt(2) == 0)) continue;


                        BlockState grassState = Blocks.SHORT_GRASS.defaultBlockState();
                        if (grassState.canSurvive(level, pos)) {
                            level.setBlock(pos, grassState, 2);
                        }
                    }
                }
            }
        }
        level.playSound(null, this.getX(), this.getY(), this.getZ(),
                SoundEvents.BONE_MEAL_USE, SoundSource.PLAYERS, 0.6F, 1.0F);
        this.discard();
    }


}
