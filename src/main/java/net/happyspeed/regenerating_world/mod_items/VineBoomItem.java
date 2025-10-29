package net.happyspeed.regenerating_world.mod_items;

import net.happyspeed.regenerating_world.RegeneratingWorld;
import net.happyspeed.regenerating_world.sounds.ModSounds;
import net.happyspeed.regenerating_world.util.StructurePlacerUtils;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class VineBoomItem extends Item {

    public VineBoomItem(Properties properties) {
        super(properties);
    }



    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        if (!level.isClientSide && level instanceof ServerLevel serverLevel) {

            level.playSound(null, player.getX(), player.getY(), player.getZ(),
                    ModSounds.VINE_BOOM.get(), SoundSource.PLAYERS, 1.0F, 1.0F);

            player.swing(hand, true);

            return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
        }
        return InteractionResultHolder.fail(stack);
    }
}
