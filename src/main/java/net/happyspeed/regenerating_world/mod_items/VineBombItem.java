package net.happyspeed.regenerating_world.mod_items;

import net.happyspeed.regenerating_world.RegeneratingWorld;
import net.happyspeed.regenerating_world.util.StructurePlacerUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
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

public class VineBombItem extends Item {

    public VineBombItem(Properties properties) {
        super(properties);
    }



    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        if (!level.isClientSide && level instanceof ServerLevel serverLevel) {

            StructurePlacerUtils.placeConfiguredFeature(serverLevel, ResourceLocation.fromNamespaceAndPath(RegeneratingWorld.MODID, "vine_spread"), player.getOnPos());

            ((ServerLevel) level).sendParticles(
                    ParticleTypes.COMPOSTER,
                    player.getX(),
                    player.getY() + 0.1,
                    player.getZ(),
                    10,
                    0.3, 0.1, 0.3,
                    0.05
            );

            level.playSound(null, player.getX(), player.getY(), player.getZ(),
                    SoundEvents.BONE_MEAL_USE, SoundSource.PLAYERS, 0.8F, 1.0F);

            player.swing(hand, true);
            if (!player.hasInfiniteMaterials()) {
                stack.shrink(1);
            }

            return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
        }
        return InteractionResultHolder.fail(stack);
    }
}
