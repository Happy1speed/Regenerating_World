package net.happyspeed.regenerating_world.mod_items;

import com.mojang.brigadier.ParseResults;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.happyspeed.regenerating_world.util.BiomeRegistryCapture;
import net.minecraft.client.gui.components.CommandSuggestions;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Unique;

public class BiomePaintBrushItem extends Item {

    public BiomePaintBrushItem(Properties properties) {
        super(properties);
    }

    @Unique
    String currentBiome = Biomes.PLAINS.location().toString();
    @Unique
    int currentBiomeIndex = 0;

    @Override
    public InteractionResult useOn(UseOnContext context) {
        ItemStack stack = context.getItemInHand();
        Level level = context.getLevel();
        Player player = context.getPlayer();
        InteractionHand hand = context.getHand();
        BlockPos interactionBlockPos = context.getClickedPos();
        MinecraftServer server = level.getServer();

        if (level instanceof ServerLevel serverLevel && !player.isCrouching()) {


            CommandSourceStack source = new CommandSourceStack(
                    player,
                    new Vec3(interactionBlockPos.getX(), interactionBlockPos.getY(), interactionBlockPos.getZ()),
                    player.getRotationVector(),
                    serverLevel,
                    4, // permission level
                    "biome_paintbrush",
                    Component.literal("biome_paintbrush"),
                    server,
                    player
            ).withSuppressedOutput();

            ParseResults<CommandSourceStack> parseResults = level.getServer().getCommands().getDispatcher().parse("fillbiome ~-3 ~-3 ~-3 ~3 ~3 ~3 " + currentBiome, source);

            try {
                server.getCommands().getDispatcher().execute(parseResults);
            } catch (CommandSyntaxException e) {
                source.sendFailure(Component.literal("Command failed: " + e.getMessage()));
            }

            ((ServerLevel) level).sendParticles(
                    ParticleTypes.ENCHANT,
                    interactionBlockPos.getX(),
                    interactionBlockPos.getY(),
                    interactionBlockPos.getZ(),
                    20,
                    0.8, 0.8, 0.8,
                    0.0
            );


            level.playSound(null, player.getX(), player.getY(), player.getZ(),
                    SoundEvents.SALMON_FLOP, SoundSource.PLAYERS, 0.5F, 1.4F);


            player.swing(hand, true);
            player.getCooldowns().addCooldown(this, 5);
            return InteractionResult.SUCCESS;
        }
        else if (player.isCrouching()) {
            player.getCooldowns().addCooldown(this, 5);
            if (currentBiomeIndex < BiomeRegistryCapture.orderedBiomes.size() - 1) {
                currentBiomeIndex += 1;
            }
            else {
                currentBiomeIndex = 0;
            }
            currentBiome = BiomeRegistryCapture.orderedBiomes.get(currentBiomeIndex).location().toString();
            player.displayClientMessage(Component.literal(currentBiome), true);
            return InteractionResult.SUCCESS;
        }

        return InteractionResult.FAIL;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (player.isCrouching()) {
            player.getCooldowns().addCooldown(this, 5);
            if (currentBiomeIndex < BiomeRegistryCapture.orderedBiomes.size() - 1) {
                currentBiomeIndex += 1;
            }
            else {
                currentBiomeIndex = 0;
            }
            currentBiome = BiomeRegistryCapture.orderedBiomes.get(currentBiomeIndex).location().toString();
            player.displayClientMessage(Component.literal(currentBiome), true);
            return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
        }
        return InteractionResultHolder.fail(stack);
    }

}
