package net.happyspeed.regenerating_world.callbacks;


import net.happyspeed.regenerating_world.RegeneratingWorld;
import net.happyspeed.regenerating_world.faces.PlayerClassAccess;
import net.happyspeed.regenerating_world.mod_items.ModItems;
import net.happyspeed.regenerating_world.network.BoundingBoxSyncPayload;
import net.happyspeed.regenerating_world.network.SuperStructureSpawnDataClient;
import net.happyspeed.regenerating_world.saved_data.SuperStructureSpawnData;
import net.happyspeed.regenerating_world.sounds.ModSounds;
import net.happyspeed.regenerating_world.util.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.FlintAndSteelItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityAttributeModificationEvent;
import net.neoforged.neoforge.event.entity.player.BonemealEvent;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.level.BlockEvent;
import net.neoforged.neoforge.event.level.LevelEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.neoforge.event.tick.ServerTickEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

import java.util.List;

import static net.happyspeed.regenerating_world.network.BoundingBoxSyncPayload.syncBoundingBoxesTo;
import static net.happyspeed.regenerating_world.util.BoundingBoxUtils.getRandomPosInBox;
import static net.happyspeed.regenerating_world.util.StructurePlacerUtils.placeConfiguredFeature;


@EventBusSubscriber(modid = RegeneratingWorld.MODID)
public class RegeneratingWorldEvents {
    @SubscribeEvent
    public static void onServerAboutToStart(ServerStartingEvent event) {
        MinecraftServer server = event.getServer();
        server.getGameRules()
                .getRule(GameRules.RULE_SPAWN_RADIUS)
                .set(0, server);
        server.getGameRules()
                .getRule(GameRules.RULE_DO_TRADER_SPAWNING)
                .set(false, server);
        server.getGameRules()
                .getRule(GameRules.RULE_DOINSOMNIA)
                .set(false, server);

        archivePortalForcer = new ArchivePortalForcer(event.getServer().overworld());
    }

    public static ArchivePortalForcer archivePortalForcer;

    @SubscribeEvent
    public static void onWorldCreated(LevelEvent.CreateSpawnPosition event) {
        LevelAccessor level = event.getLevel();


        if (level instanceof ServerLevel serverLevel) {
            TemporaryTickingAreaManager.loadArea(serverLevel, new BlockPos(-1024, 70, -1024), 4, 100);

            serverLevel.getServer().execute(() -> {

                serverLevel.getChunk(-992, -992);

                placeConfiguredFeature(serverLevel,
                        ResourceLocation.fromNamespaceAndPath(RegeneratingWorld.MODID, "leg_farx_farz"),
                        new BlockPos(-992, 48, -992));
            });

            serverLevel.getServer().execute(() -> {

                serverLevel.getChunk(-1104, -1104);

                placeConfiguredFeature(serverLevel,
                        ResourceLocation.fromNamespaceAndPath(RegeneratingWorld.MODID, "example_struct_stack"),
                        new BlockPos(-1104, 48, -1104));
            });

            serverLevel.getServer().execute(() -> {

                serverLevel.getChunk(-992, -1104);

                placeConfiguredFeature(serverLevel,
                        ResourceLocation.fromNamespaceAndPath(RegeneratingWorld.MODID, "example_struct_stack"),
                        new BlockPos(-992, 48, -1104));
            });

            serverLevel.getServer().execute(() -> {

                serverLevel.getChunk(-1104, -992);

                placeConfiguredFeature(serverLevel,
                        ResourceLocation.fromNamespaceAndPath(RegeneratingWorld.MODID, "example_struct_stack"),
                        new BlockPos(-1104, 48, -992));
            });
        }

        if (level instanceof ServerLevel serverLevel) {

            BlockPos spawn = findSafeSpawn(serverLevel, new BlockPos(serverLevel.getSharedSpawnPos().getX(), 60, serverLevel.getSharedSpawnPos().getZ()));
            serverLevel.setDefaultSpawnPos(spawn, serverLevel.getSharedSpawnAngle());
        }

    }

    private static BlockPos findSafeSpawn(ServerLevel level, BlockPos startPos) {
        int maxY = level.getMaxBuildHeight();
        BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos(startPos.getX(), startPos.getY(), startPos.getZ());

        while (pos.getY() <= maxY) {
            BlockState blockBelow = level.getBlockState(pos.below());
            BlockState blockAt = level.getBlockState(pos);
            BlockState blockAbove = level.getBlockState(pos.above());

            boolean isSafe = blockBelow.is(ModTags.Blocks.VALID_SPAWN_RELOCATION) && blockAt.isAir() && blockAbove.isAir();
            if (isSafe) return pos.immutable();

            pos.move(Direction.UP);
        }

        return startPos;
    }



    @SubscribeEvent
    public static void onTooltip(ItemTooltipEvent event) {
        ItemStack stack = event.getItemStack();

        if (stack.getItem() == ModItems.ORIGIN_POINT_BLOCK_ITEM.get()) {
            event.getToolTip().add(Component.translatable("item.tooltip.origin_block"));
        }

        if (stack.getItem() == ModItems.DRY_WHEAT_SEEDS_ITEM.get()) {
            event.getToolTip().add(Component.translatable("item.tooltip.dry_item"));
        }

        if (stack.getItem() == ModItems.DRY_SUGAR_CANE_ITEM.get()) {
            event.getToolTip().add(Component.translatable("item.tooltip.dry_item"));
        }

        if (stack.getItem() == Items.DRIED_KELP.asItem()) {
            event.getToolTip().add(Component.translatable("item.tooltip.dry_item"));
        }

        if (stack.getItem() == ModItems.VAULT_POLE.get()) {
            event.getToolTip().add(Component.translatable("item.tooltip.vault_pole"));
        }

        if (stack.getItem() == ModItems.SIMPLE_GROW_LIGHT_BLOCK_ITEM.get()) {
            event.getToolTip().add(Component.translatable("item.tooltip.grow_light"));
        }

        if (stack.getItem() == ModItems.EXACT_SHOVEL.get()) {
            event.getToolTip().add(Component.translatable("item.tooltip.exact_shovel"));
        }

        if (stack.getItem() == ModItems.BIOME_PAINTBRUSH.get()) {
            event.getToolTip().add(Component.translatable("item.tooltip.biome_paintbrush"));
        }

        if (stack.getItem() == ModItems.BIOME_PAINTBUCKET.get()) {
            event.getToolTip().add(Component.translatable("item.tooltip.biome_paintbucket"));
        }

        if (stack.getItem() == ModItems.FERN_LIGHT_BLOCK_ITEM.get()) {
            event.getToolTip().add(Component.translatable("item.tooltip.fern_light"));
        }
    }

    @SubscribeEvent
    public static void onBlockPlaced(BlockEvent.EntityPlaceEvent event) {
        BlockPos pos = event.getPos();
        Block block = event.getPlacedBlock().getBlock();

        BlockPos shiftUpOne = new BlockPos(pos.getX(), pos.getY() + 1, pos.getZ());

        if (block == ModItems.ORIGIN_POINT_BLOCK.get()) {
            if (event.getLevel() instanceof ServerLevel serverLevel) {

                serverLevel.setDefaultSpawnPos(shiftUpOne, serverLevel.getSharedSpawnAngle());
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event) {
        if (!event.isEndConquered()) {
            ServerPlayer player = (ServerPlayer) event.getEntity();
            ServerLevel level = ((ServerPlayer) event.getEntity()).serverLevel();

            BlockPos spawnPos = level.getSharedSpawnPos();
            player.teleportTo(level, spawnPos.getX() + 0.5, spawnPos.getY(), spawnPos.getZ() + 0.5, player.getYRot(), player.getXRot());
        }
    }

    @SubscribeEvent
    public static void onBonemeal(BonemealEvent event) {
        Level level = event.getLevel();
        BlockPos pos = event.getPos();
        BlockState state = level.getBlockState(pos);


        if (level.isClientSide()) return;

        // Check if target is a grass block
        if (level.getBlockState(event.getPos()).is(Blocks.GRASS_BLOCK)) {

            // Check the gamerule
            boolean allow = level.getGameRules().getBoolean(ModGameRules.BONEMEAL_GRASS_ALLOWED);

            if (!allow) {

                event.setCanceled(true);

                if (event.getPlayer() instanceof Player player && !player.getAbilities().instabuild) {
                    event.getStack().shrink(1);
                }
                level.levelEvent(2005, event.getPos(), 0);
            }
        }

        if (level.getBlockState(event.getPos()).is(Blocks.PODZOL)) {


            boolean allow = level.getGameRules().getBoolean(ModGameRules.BONEMEAL_GRASS_ALLOWED);

            if (allow) {

                BlockPos placePos = new BlockPos(pos.getX(), pos.getY() + 1, pos.getZ());

                if (level.getBlockState(placePos).isAir()) {

                    if (event.getPlayer() instanceof Player player && !player.getAbilities().instabuild) {
                        event.getStack().shrink(1);
                    }
                    if (level instanceof ServerLevel serverLevel) {
                        if (level.getBlockState(placePos).isAir()) {
                            level.setBlock(placePos, Blocks.FERN.defaultBlockState(), 2);
                            level.levelEvent(1505, placePos, 15);
                            for (int i = 0; i < 8; i++) {
                                serverLevel.sendParticles(
                                        ParticleTypes.COMPOSTER,
                                        (double) placePos.getX() + serverLevel.random.nextDouble(),
                                        (double) (placePos.getY() + 0.5),
                                        (double) placePos.getZ() + serverLevel.random.nextDouble(),
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

    @SubscribeEvent
    public static void onUseFlintAndSteel(PlayerInteractEvent.RightClickBlock event) {
        Level level = event.getLevel();
        BlockPos pos = event.getPos();
        ItemStack stack = event.getItemStack();
        ResourceKey<Level> dimension = level.dimension();
        if (dimension.location().toString().equals("regenerating_world:the_archive")) {
            if (stack.getItem() instanceof FlintAndSteelItem) {
                if (ReturnPortalShapeDetector.isValidFrame(level, pos)) {
                    ReturnPortalShapeDetector.spawnPortal(level, new BlockPos(pos.getX(), pos.getY() + 1, pos.getZ()));
                    event.setCanceled(true);
                }
            }
        } else if (dimension == Level.OVERWORLD) {
            if (stack.getItem() instanceof FlintAndSteelItem) {
                if (ArchivePortalShapeDetector.isValidFrame(level, pos)) {
                    ArchivePortalShapeDetector.spawnPortal(level, new BlockPos(pos.getX(), pos.getY() + 1, pos.getZ()));
                    event.setCanceled(true);
                }
            }
        }
    }

    @SubscribeEvent
    public static void onWorldLoad(LevelEvent.Load event) {
        if (!(event.getLevel() instanceof ServerLevel serverLevel)) return;

        StructureTemplateManager manager = serverLevel.getStructureManager();
        StructureTemplate buildingPlainBase1 = manager.getOrCreate(ResourceLocation.fromNamespaceAndPath(RegeneratingWorld.MODID, "city_top/city_base_plain_1"));
        StructureTemplate buildingPlainBase2 = manager.getOrCreate(ResourceLocation.fromNamespaceAndPath(RegeneratingWorld.MODID, "city_top/city_base_plain_2"));
        StructureTemplate buildingPlainBase3 = manager.getOrCreate(ResourceLocation.fromNamespaceAndPath(RegeneratingWorld.MODID, "city_top/city_base_plain_3"));
        StructureTemplate buildingPlainBase4 = manager.getOrCreate(ResourceLocation.fromNamespaceAndPath(RegeneratingWorld.MODID, "city_top/city_base_plain_4"));
        StructureTemplate buildingPlainBase5 = manager.getOrCreate(ResourceLocation.fromNamespaceAndPath(RegeneratingWorld.MODID, "city_top/city_base_plain_5"));

        StructureTemplate[] basePlainCityPool = new StructureTemplate[]{buildingPlainBase1, buildingPlainBase2, buildingPlainBase3, buildingPlainBase4, buildingPlainBase5};

        StructureCache.registerPool("base_city_plain_pool", basePlainCityPool);

        StructureTemplate buildingRedBase1 = manager.getOrCreate(ResourceLocation.fromNamespaceAndPath(RegeneratingWorld.MODID, "city_top/city_base_red_1"));
        StructureTemplate buildingRedBase2 = manager.getOrCreate(ResourceLocation.fromNamespaceAndPath(RegeneratingWorld.MODID, "city_top/city_base_red_2"));
        StructureTemplate buildingRedBase3 = manager.getOrCreate(ResourceLocation.fromNamespaceAndPath(RegeneratingWorld.MODID, "city_top/city_base_red_3"));
        StructureTemplate buildingRedBase4 = manager.getOrCreate(ResourceLocation.fromNamespaceAndPath(RegeneratingWorld.MODID, "city_top/city_base_red_4"));

        StructureTemplate[] baseRedCityPool = new StructureTemplate[]{buildingRedBase1, buildingRedBase2, buildingRedBase3, buildingRedBase4};

        StructureCache.registerPool("base_city_red_pool", baseRedCityPool);

        StructureTemplate buildingPlainOnlyBase1 = manager.getOrCreate(ResourceLocation.fromNamespaceAndPath(RegeneratingWorld.MODID, "city_top/city_only_base_plain_1"));

        StructureCache.registerSingle("only_base_city_plain_arch", buildingPlainOnlyBase1);

        StructureTemplate buildingPlainTop1 = manager.getOrCreate(ResourceLocation.fromNamespaceAndPath(RegeneratingWorld.MODID, "city_top/city_topper_plain_1"));
        StructureTemplate buildingPlainTop2 = manager.getOrCreate(ResourceLocation.fromNamespaceAndPath(RegeneratingWorld.MODID, "city_top/city_topper_plain_2"));
        StructureTemplate buildingPlainTop3 = manager.getOrCreate(ResourceLocation.fromNamespaceAndPath(RegeneratingWorld.MODID, "city_top/city_topper_plain_3"));
        StructureTemplate buildingPlainTop4 = manager.getOrCreate(ResourceLocation.fromNamespaceAndPath(RegeneratingWorld.MODID, "city_top/city_topper_plain_4"));

        StructureTemplate[] topPlainCityPool = new StructureTemplate[]{buildingPlainTop1, buildingPlainTop2, buildingPlainTop3, buildingPlainTop4};

        StructureCache.registerPool("top_city_plain_pool", topPlainCityPool);

    }

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {

        if (!(event.getEntity().level() instanceof ServerLevel level)) return;

        if (!(event.getEntity() instanceof Player player)) return;

        BlockPos pos = player.blockPosition();

        if (player.level().getGameTime() % 300 == 0) {
            for (ServerPlayer player_iter : level.players()) {
                syncBoundingBoxesTo(player_iter);
            }
        }



    }

    @SubscribeEvent
    public static void onLevelTick(ServerTickEvent.Post event) {
        ServerLevel level = event.getServer().overworld();

        if (level.getGameTime() % 40 == 0) {
            for (BoundingBox boundingBox : SuperStructureSpawnData.get(level).getBoxes()) {
                if (level.random.nextInt(199) == 0) {
                    boolean soundPlayed = false;
                    for (ServerPlayer player : level.players()) {
                        if (!soundPlayed) {
                            BlockPos boxPos = getRandomPosInBox(boundingBox, level.random);
                            if (player.position().distanceTo(boxPos.getCenter()) > 20 && player.position().distanceTo(boxPos.getCenter()) < 100 && player.getOnPos().getY() < 140) {
                                int randSound = level.random.nextInt(4);
                                if (randSound == 0) {
                                    player.level().playSound(
                                            null,
                                            player.blockPosition(),
                                            ModSounds.HUGE_IMPACT.get(),
                                            SoundSource.WEATHER,
                                            3.0F,
                                            Math.max(0.7f, level.random.nextFloat())
                                    );
                                }
                                else if (randSound == 1) {
                                    player.level().playSound(
                                            null,
                                            player.blockPosition(),
                                            ModSounds.METAL_REVERB_1.get(),
                                            SoundSource.WEATHER,
                                            3.0F,
                                            Math.max(0.7f, level.random.nextFloat())
                                    );
                                }
                                else if (randSound == 2) {
                                    player.level().playSound(
                                            null,
                                            player.blockPosition(),
                                            ModSounds.METAL_REVERB_2.get(),
                                            SoundSource.WEATHER,
                                            3.0F,
                                            Math.max(0.7f, level.random.nextFloat())
                                    );
                                }
                                else {
                                    player.level().playSound(
                                            null,
                                            player.blockPosition(),
                                            ModSounds.MYSTERY_ARP.get(),
                                            SoundSource.WEATHER,
                                            3.0F,
                                            Math.max(0.8f, level.random.nextFloat())
                                    );
                                }

                                soundPlayed = true;
                            }
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void onRegisterPayloads(RegisterPayloadHandlersEvent event) {
        PayloadRegistrar registrar = event.registrar("1.0.0"); // protocol version

        registrar.playToClient(
                BoundingBoxSyncPayload.TYPE,
                BoundingBoxSyncPayload.STREAM_CODEC,
                (payload, context) -> {
                    context.enqueueWork(() -> {
                        SuperStructureSpawnDataClient.get().setBoxes(payload.boxes());
                    });
                }
        );
    }

    @SubscribeEvent
    public static void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) return;

        ServerLevel level = player.serverLevel();
        List<BoundingBox> boxes = SuperStructureSpawnData.get(level).getBoxes();
        BoundingBoxSyncPayload payload = new BoundingBoxSyncPayload(boxes);

        player.connection.send(payload);
    }


    @SubscribeEvent
    public static void onLivingTick(EntityTickEvent.Post event) {
        Entity entity = event.getEntity();
        if (!(entity instanceof Player player)) return;

        // Only apply when airborne
        if (player.onGround() || player.isInWater() || player.isFallFlying()) return;

        double airMobility = player.getAttributeValue(ModAttributes.AIR_MOBILITY);
        if (airMobility <= 0) return;

        float strafe = player.xxa;
        float forward = player.zza;
        if (strafe == 0 && forward == 0) return;

        if (!(((PlayerClassAccess) player).getTicksOffGround() >= 10)) return;

        Vec3 motion = player.getDeltaMovement().multiply(1.0 + airMobility, 1.0, 1.0 + airMobility);

        double horizontalSpeed = Math.sqrt(
                motion.x * motion.x + motion.z * motion.z
        );

        double maxSpeed = 1.0;
        if (horizontalSpeed < maxSpeed) {
            player.setDeltaMovement(motion);
        }
    }

    @SubscribeEvent
    public static void onEntityAttributeModification(EntityAttributeModificationEvent event) {
        event.add(EntityType.PLAYER, ModAttributes.AIR_MOBILITY);
    }


}
