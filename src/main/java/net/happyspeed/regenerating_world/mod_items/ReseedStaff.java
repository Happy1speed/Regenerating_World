package net.happyspeed.regenerating_world.mod_items;

import net.happyspeed.regenerating_world.RegeneratingWorld;
import net.happyspeed.regenerating_world.entity.ModEntities;
import net.happyspeed.regenerating_world.entity.ReseedProjectileEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Unique;

public class ReseedStaff extends Item {

    public ReseedStaff(Properties properties) {
        super(properties);
    }


    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        if (!level.isClientSide && level instanceof ServerLevel serverLevel) {
            Vec3 look = player.getLookAngle();

            ReseedProjectileEntity projectile = new ReseedProjectileEntity(ModEntities.RESEED_PROJECTILE.get(), level);
            projectile.setOwner(player);

            projectile.setPos(player.getX(), player.getEyeY(), player.getZ());
            projectile.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 1.5F, 1.0F);

            level.addFreshEntity(projectile);

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
                    SoundEvents.TRIDENT_THROW, SoundSource.PLAYERS, 0.6F, 1.7F);


            player.swing(hand, true);
            player.getCooldowns().addCooldown(this, 5);

            return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
        }
        return InteractionResultHolder.fail(stack);
    }
}
