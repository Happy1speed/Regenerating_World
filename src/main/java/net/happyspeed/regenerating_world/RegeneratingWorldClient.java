package net.happyspeed.regenerating_world;

import net.happyspeed.regenerating_world.entity.ModEntities;
import net.happyspeed.regenerating_world.entity.renderers.ReseedProjectileRenderer;
import net.happyspeed.regenerating_world.faces.ModeClassAccess;
import net.happyspeed.regenerating_world.mod_items.ModItems;
import net.happyspeed.regenerating_world.network.SuperStructureSpawnDataClient;
import net.happyspeed.regenerating_world.particles.AuraParticleProvider;
import net.happyspeed.regenerating_world.particles.ModParticles;
import net.happyspeed.regenerating_world.sounds.AmbientWindSoundHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.*;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

import java.util.Random;

// This class will not load on dedicated servers. Accessing client side code from here is safe.
@Mod(value = RegeneratingWorld.MODID, dist = Dist.CLIENT)
// You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
@EventBusSubscriber(modid = RegeneratingWorld.MODID, value = Dist.CLIENT)
public class RegeneratingWorldClient {
    public RegeneratingWorldClient(ModContainer container) {
        // Allows NeoForge to create a config screen for this mod's configs.
        // The config screen is accessed by going to the Mods screen > clicking on your mod > clicking on config.
        // Do not forget to add translations for your config options to the en_us.json file.

        container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
    }

    @SubscribeEvent
    static void onClientSetup(FMLClientSetupEvent event) {
        // Some client setup code
        RegeneratingWorld.LOGGER.info("HELLO FROM CLIENT SETUP");
        RegeneratingWorld.LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());

        ItemBlockRenderTypes.setRenderLayer(ModItems.TUNGSTEN_GLASS_BLOCK.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModItems.FANCY_OAK_SAPLING.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModItems.SWAMP_OAK_SAPLING.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModItems.OVERGROWTH_OAK_SAPLING.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModItems.BUSH_OAK_SAPLING.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModItems.SHEARMETAL_GRATE_BLOCK.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModItems.TORN_SHEARMETAL_BLOCK.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModItems.ARCHIVE_PORTAL_BLOCK.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(ModItems.RETURN_PORTAL_BLOCK.get(), RenderType.translucent());

    }

    @SubscribeEvent
    public static void onClientTick(ClientTickEvent.Post event) {
        Minecraft mc = Minecraft.getInstance();
        if (!Minecraft.getInstance().isPaused()) {
            if (mc.level != null && mc.level.getGameTime() % 2 == 0 && mc.player != null) {
                BlockPos spawnPos = mc.level.getSharedSpawnPos();
                Random random = new Random();
                mc.level.addParticle(ParticleTypes.ENCHANT,
                        spawnPos.getX() + random.nextDouble(1.0),
                        spawnPos.getY() + random.nextDouble(1.0) + 0.5,
                        spawnPos.getZ() + random.nextDouble(1.0),
                        0.0, 0.0, 0.0);
            }

            if (mc.gameMode != null) {
                if (((ModeClassAccess) mc.gameMode).getDestroyDelay() > 5 && !mc.options.keyAttack.isDown()) {
                    ((ModeClassAccess) mc.gameMode).setDestroyDelay(0);
                }
            }
            LocalPlayer player = mc.player;
            Level level = mc.level;

            if (player == null || level == null) return;

            BlockPos playerPos = player.blockPosition();


            for (BoundingBox box : SuperStructureSpawnDataClient.get().getBoxes()) {
                //Ambient Sounds for Superstructure
                if (box.isInside(playerPos)) {
                    if (level.random.nextInt(40) == 0) {
                        AmbientWindSoundHandler.playAmbientIfNotPlaying();
                    }
                }

                if (box.getCenter().distManhattan(playerPos) < 100) {
                    if (level.random.nextInt(7) == 0) {
                        int minX = box.minX() - 20;
                        int minY = box.minY();
                        int minZ = box.minZ() - 20;
                        int maxX = box.maxX() - 20;
                        int maxY = box.maxY();
                        int maxZ = box.maxZ() - 20;

                        double x = level.random.nextInt(minX, maxX);
                        double y = level.random.nextInt(minY, maxY);
                        double z = level.random.nextInt(minZ, maxZ);

                        level.addParticle(ModParticles.AURA_PARTICLE.get(), x, y, z, 0.4, 0.2, 0.4);
                    }
                }
            }
        }
    }


    @SubscribeEvent
    public static void onCustomizeOverlay(CustomizeGuiOverlayEvent.Chat event) {
        GuiGraphics guiGraphics = event.getGuiGraphics();
        Minecraft mc = Minecraft.getInstance();

        if (mc.player != null) {

            if (mc.player.isCrouching()) {
                guiGraphics.renderItem(new ItemStack(Items.COMPASS), 10, 10);
                guiGraphics.renderItem(new ItemStack(Items.CLOCK), 10, 30);
            }
        }
    }


    @SubscribeEvent
    public static void registerFactories(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(ModParticles.AURA_PARTICLE.get(), AuraParticleProvider::new);
    }

    @SubscribeEvent
    public static void onRegisterRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ModEntities.RESEED_PROJECTILE.get(), ReseedProjectileRenderer::new);
    }

}
