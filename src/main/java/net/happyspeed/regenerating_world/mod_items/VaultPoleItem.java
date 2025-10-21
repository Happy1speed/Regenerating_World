package net.happyspeed.regenerating_world.mod_items;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Unique;

public class VaultPoleItem extends Item {

    public VaultPoleItem(Properties properties) {
        super(properties);
    }

    @Unique
    public static double getDistanceFromGround(Entity entity) {
        Level level = entity.level();

        BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos(entity.getX(), entity.getY(), entity.getZ());

        double distance = 0;

        while (pos.getY() > level.getMinBuildHeight()) {
            pos.move(0, -1, 0);
            if (!level.getBlockState(pos).isAir()) {
                break;
            }
            distance++;
        }
        return distance;
    }


    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        if (!level.isClientSide) {

            if (player.onGround() || getDistanceFromGround(player) < 1.3) {
                Vec3 look = player.getLookAngle();

                double boostStrength = 1.5;
                Vec3 boost = new Vec3(look.x * boostStrength, look.y * boostStrength, look.z * boostStrength);

                // Apply velocity
                player.push(boost.x, boost.y, boost.z);
                player.hurtMarked = true;

                ((ServerLevel) level).sendParticles(
                        ParticleTypes.POOF,
                        player.getX(),
                        player.getY() + 0.1,
                        player.getZ(),
                        20,
                        0.3, 0.1, 0.3,
                        0.05
                );


                level.playSound(null, player.getX(), player.getY(), player.getZ(),
                        SoundEvents.ENDER_DRAGON_FLAP, SoundSource.PLAYERS, 1.0F, 1.2F);


                player.swing(hand, true);
                player.getCooldowns().addCooldown(this, 40);
            }

            return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
        }
        return InteractionResultHolder.fail(stack);
    }
}
