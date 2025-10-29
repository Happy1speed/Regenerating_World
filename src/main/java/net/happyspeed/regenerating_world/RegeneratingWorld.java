package net.happyspeed.regenerating_world;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.happyspeed.regenerating_world.entity.ModEntities;
import net.happyspeed.regenerating_world.features.ModFeatures;
import net.happyspeed.regenerating_world.features.ModTrunkPlacers;
import net.happyspeed.regenerating_world.features.SuspiciousSandVeinFeature;
import net.happyspeed.regenerating_world.mod_blocks.*;
import net.happyspeed.regenerating_world.mod_items.*;
import net.happyspeed.regenerating_world.particles.ModParticles;
import net.happyspeed.regenerating_world.sounds.ModSounds;
import net.happyspeed.regenerating_world.util.*;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.features.TreeFeatures;
import net.minecraft.data.worldgen.placement.TreePlacements;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.ColorRGBA;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.horse.SkeletonHorse;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.TickingBlockEntity;
import net.minecraft.world.level.block.grower.TreeGrower;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.material.*;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.common.ItemAbilities;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.common.NeoForgeMod;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.level.BlockEvent;
import net.neoforged.neoforge.fluids.BaseFlowingFluid;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.registries.*;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;

import java.util.*;
import java.util.function.Supplier;


@Mod(RegeneratingWorld.MODID)
public class RegeneratingWorld {

    // Define mod id in a common place for everything to reference
    public static final String MODID = "regenerating_world";
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();

    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);


    // Creates a creative tab with the id "regenerating_world:example_tab" for the example item, that is placed after the combat tab
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> REGENERATING_WORLD_TAB = CREATIVE_MODE_TABS.register("regenerating_world_tab", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.regenerating_world")) //The language key for the title of your CreativeModeTab
            .withTabsBefore(CreativeModeTabs.COMBAT)
            .icon(() -> ModItems.OAK_SEED_ITEM.get().getDefaultInstance())
            .displayItems((parameters, output) -> {
                output.accept(ModItems.PARCHED_DIRT_BLOCK_ITEM.get());
                output.accept(ModItems.WEATHERED_SANDSTONE_BLOCK_ITEM.get());
                output.accept(ModItems.WEATHERED_SAND_BLOCK_ITEM.get());
                output.accept(ModItems.WEATHERED_SAND_BLOCK_ITEM.get());
                output.accept(ModItems.WEATHERED_STONE_BLOCK_ITEM.get());
                output.accept(ModItems.WEATHERED_DEEPSLATE_BLOCK_ITEM.get());
                output.accept(ModItems.WEATHERED_STONE_BRICKS_BLOCK_ITEM.get());
                output.accept(ModItems.CLAY_WATER_BUCKET.get());
                output.accept(ModItems.CLAY_BUCKET.get());
                output.accept(ModItems.OAK_SEED_ITEM.get());
                output.accept(ModItems.SPRUCE_SEED_ITEM.get());
                output.accept(ModItems.SAND_PILE_ITEM.get());
                output.accept(ModItems.COARSE_DIRT_PILE_ITEM.get());
                output.accept(ModItems.TUNGSTEN_ORE_BLOCK_ITEM.get());
                output.accept(ModItems.DEEPSLATE_TUNGSTEN_ORE_BLOCK_ITEM.get());
                output.accept(ModItems.TUNGSTEN_BLOCK_ITEM.get());
                output.accept(ModItems.RIBBED_TUNGSTEN_BLOCK_ITEM.get());
                output.accept(ModItems.TUNGSTEN_GLASS_BLOCK_ITEM.get());
                output.accept(ModItems.TUNGSTEN_INGOT_ITEM.get());
                output.accept(ModItems.RAW_TUNGSTEN_ITEM.get());
                output.accept(ModItems.ORIGIN_POINT_BLOCK_ITEM.get());
                output.accept(ModItems.SIMPLE_GROW_LIGHT_BLOCK_ITEM.get());
                output.accept(ModItems.HARDENED_AMBER_INGOT_ITEM.get());
                output.accept(ModItems.HARDENED_AMBER_HELMET_ITEM.get());
                output.accept(ModItems.HARDENED_AMBER_CHESTPLATE_ITEM.get());
                output.accept(ModItems.HARDENED_AMBER_LEGGINGS_ITEM.get());
                output.accept(ModItems.HARDENED_AMBER_BOOTS_ITEM.get());
                output.accept(ModItems.AMBER_CLUMP_ITEM.get());
                output.accept(ModItems.AMBER_PICKAXE.get());
                output.accept(ModItems.COPSTEN_BLEND_ITEM.get());
                output.accept(ModItems.DRY_WHEAT_SEEDS_ITEM.get());
                output.accept(ModItems.DRY_SUGAR_CANE_ITEM.get());
                output.accept(ModItems.COMPOSITE_MESH_ITEM.get());
                output.accept(ModItems.BONE_MASH_BUNDLE_ITEM.get());
                output.accept(ModItems.VAULT_POLE.get());
                output.accept(ModItems.EXACT_SHOVEL.get());
                output.accept(ModItems.FERN_LIGHT_BLOCK_ITEM.get());
                output.accept(ModItems.NUTRIENTS_DEPOSIT_BLOCK_ITEM.get());
                output.accept(ModItems.MINERAL_GRASS_BLOCK_ITEM.get());
                output.accept(ModItems.CLEAN_MINERAL_GRASS_BLOCK_ITEM.get());
                output.accept(ModItems.MINERAL_DIRT_BLOCK_ITEM.get());
                output.accept(ModItems.MINERAL_SAND_BLOCK_ITEM.get());
                output.accept(ModItems.MINERAL_MUD_BLOCK_ITEM.get());
                output.accept(ModItems.MINERAL_PACKED_MUD_BLOCK_ITEM.get());
                output.accept(ModItems.MINERAL_FULL_GRASS_BLOCK_ITEM.get());
                output.accept(ModItems.VINE_BOMB.get());
                output.accept(ModItems.VINE_BOOM.get());
                output.accept(ModItems.RESEED_STAFF.get());
                output.accept(ModItems.FANCY_OAK_SAPLING_ITEM.get());
                output.accept(ModItems.SWAMP_OAK_SAPLING_ITEM.get());
                output.accept(ModItems.OVERGROWTH_OAK_SAPLING_ITEM.get());
                output.accept(ModItems.BUSH_OAK_SAPLING_ITEM.get());
                output.accept(ModItems.COMPFUSION_MATRIX_BLOCK_ITEM.get());
                output.accept(ModItems.GLIDER_HELMET_ITEM.get());
                output.accept(ModItems.BIOME_PAINTBRUSH.get());
            }).build());

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> REGENERATING_WORLD_MIDGAME_TAB = CREATIVE_MODE_TABS.register("regenerating_world_midgame_tab", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.regenerating_world_midgame")) //The language key for the title of your CreativeModeTab
            .withTabsBefore(REGENERATING_WORLD_TAB.getId())
            .icon(() -> ModItems.BRITTLECRETE_BLOCK_ITEM.get().getDefaultInstance())
            .displayItems((parameters, output) -> {
                output.accept(ModItems.BRITTLECRETE_BLOCK_ITEM.get());
                output.accept(ModItems.BRITTLECRETE_BLOCK_SLAB_ITEM.get());
                output.accept(ModItems.RED_BRITTLECRETE_BLOCK_ITEM.get());
                output.accept(ModItems.SHEARMETAL_BLOCK_ITEM.get());
                output.accept(ModItems.CARVED_SHEARMETAL_BLOCK_ITEM.get());
                output.accept(ModItems.SMOOTH_SHEARMETAL_BLOCK_ITEM.get());
                output.accept(ModItems.WORN_SHEARMETAL_BLOCK_ITEM.get());
                output.accept(ModItems.SHEARMETAL_GRATE_BLOCK_ITEM.get());
                output.accept(ModItems.TORN_SHEARMETAL_BLOCK_ITEM.get());
                output.accept(ModItems.CABLE_BUNDLE_BLOCK_ITEM.get());
                output.accept(ModItems.ION_STATUS_BOARD_BLOCK_ITEM.get());
                output.accept(ModItems.GREEN_WARNLIGHT_BLOCK_ITEM.get());
            }).build());







    // The constructor for the mod class is the first code that is run when your mod is loaded.
    // FML will recognize some parameter types like IEventBus or ModContainer and pass them in automatically.
    public RegeneratingWorld(IEventBus modEventBus, ModContainer modContainer) {
        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);


        ModFluids.register(modEventBus);

        ModArmorMaterials.ARMOR_MATERIALS_REG.register(modEventBus);

        ModAttributes.ATTRIBUTES.register(modEventBus);

        ModSounds.register(modEventBus);

        ModItems.BLOCKS.register(modEventBus);

        ModEntities.ENTITY_TYPES.register(modEventBus);

        ModItems.BLOCK_ENTITY_TYPES.register(modEventBus);
        // Register the Deferred Register to the mod event bus so items get registered
        ModItems.ITEMS.register(modEventBus);

        ModTrunkPlacers.TRUNK_PLACERS.register(modEventBus);



        NeoForge.EVENT_BUS.register(this);

        modEventBus.addListener(ModFeatures::registerFeatures);




        // Register the Deferred Register to the mod event bus so tabs get registered
        CREATIVE_MODE_TABS.register(modEventBus);

        ModParticles.register(modEventBus);

        ModLootModifiers.register(modEventBus);


        // Register our mod's ModConfigSpec so that FML can create and load the config file for us
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        // Some common setup code
        LOGGER.info("Manual regeneration is suboptimal, but acceptable!");

        event.enqueueWork(() -> {
            ModGameRules.BONEMEAL_GRASS_ALLOWED = GameRules.register(
                    "bonemealGrassAllowed",
                    GameRules.Category.MISC,
                    GameRules.BooleanValue.create(false) // default value
            );
        });

    }


    @SubscribeEvent
    public void onBreak(BlockEvent.BreakEvent event) {
        Level world = (Level) event.getLevel();
        BlockPos pos = event.getPos();
        Player player = event.getPlayer();
        BlockState state = world.getBlockState(pos);

        if (world instanceof ServerLevel serverLevel) {

            // Only target Suspicious Sand for new break loot
            if (state.is(Blocks.SUSPICIOUS_SAND) && !world.isClientSide && !player.hasInfiniteMaterials()) {
                //ItemStack tool = player.getMainHandItem();
                // Make sure that you are on a server, otherwise the cast will fail.
                //LootParams.Builder builder = new LootParams.Builder(serverLevel);

                // Use whatever context parameters and values you need. Vanilla parameters can be found in LootContextParams.
                    // This variant can accept null as the value, in which case an existing value for that parameter will be removed.
                //builder.withOptionalParameter(LootContextParams.TOOL, tool);

                //builder.withParameter(LootContextParams.BLOCK_STATE, state);

                //builder.withParameter(LootContextParams.ORIGIN, pos.getCenter());
                    // Add a dynamic drop.
                //builder.withDynamicDrop(ResourceLocation.fromNamespaceAndPath("regenerating_world", "blocks/suspicious_sand"), stackAcceptor -> {
                    // some logic here
               // });
                // Set our luck value. Assumes that a player is available. Contexts without a player should use 0 here.
                //builder.withLuck(player.getLuck());

//                ResourceKey<LootTable> SusSandResourceKey = ResourceKey.create(
//                        net.minecraft.core.registries.Registries.LOOT_TABLE,
//                        ResourceLocation.fromNamespaceAndPath("regenerating_world", "blocks/sandy")
//                );
//                LootParams.Builder lootparams$builder = (new LootParams.Builder(serverLevel)).withParameter(LootContextParams.ORIGIN, Vec3.atCenterOf(pos)).withParameter(LootContextParams.TOOL, tool).withOptionalParameter(LootContextParams.THIS_ENTITY, player);
//                state.getDrops(lootparams$builder).forEach((drop) -> {
//                    Block.popResource(serverLevel, pos, drop);
//                });

                //Madness drove me to creating a hardcoded loot table. _Help.
                Random random = new Random();

                int tableItem = random.nextInt(3);

                if (tableItem == 1) {
                    int tableItem2 = random.nextInt(2);
                    if (tableItem2 == 1) {
                        Block.popResource(serverLevel, pos, new ItemStack(Items.STICK));
                        Block.popResource(serverLevel, pos, new ItemStack(Items.STICK));
                        Block.popResource(serverLevel, pos, new ItemStack(Items.STICK));
                    }
                    else {
                        Block.popResource(serverLevel, pos, new ItemStack(Items.CLAY_BALL));
                        Block.popResource(serverLevel, pos, new ItemStack(Items.CLAY_BALL));
                        Block.popResource(serverLevel, pos, new ItemStack(Items.CLAY_BALL));
                    }
                }
                else if (tableItem == 2) {

                    int tableItem2 = random.nextInt(3);
                    if (tableItem2 == 1) {
                        Block.popResource(serverLevel, pos, new ItemStack(Items.COBBLESTONE));
                        Block.popResource(serverLevel, pos, new ItemStack(Items.COBBLESTONE));
                    }
                    else {
                        Block.popResource(serverLevel, pos, new ItemStack(Items.BONE_BLOCK));
                    }
                }
                else {
                    int tableItem2 = random.nextInt(2);
                    if (tableItem2 == 1) {
                        Block.popResource(serverLevel, pos, new ItemStack(ModItems.SPRUCE_SEED_ITEM.asItem()));
                    }
                    else {
                        Block.popResource(serverLevel, pos, new ItemStack(ModItems.OAK_SEED_ITEM.asItem()));
                    }
                }
                // Specify a loot context param set here if you want.
                //LootParams params = builder.create(LootContextParamSets.EMPTY);
                // Get the loot table.
                //LootTable table = serverLevel.getServer().reloadableRegistries().getLootTable(SusSandResourceKey);
                // Actually roll the loot table.
                //List<ItemStack> list = table.getRandomItems(params);
                //LootParams lootparams = new LootParams.Builder(serverLevel).create(LootContextParamSets.EMPTY);
                //ObjectArrayList<ItemStack> objectarraylist = table.getRandomItems(lootparams);
                //if (!objectarraylist.isEmpty()) {
                //    for (ItemStack itemstack : objectarraylist) {
                //        DefaultDispenseItemBehavior.spawnItem(serverLevel, itemstack, 2, Direction.UP, Vec3.atBottomCenterOf(pos).relative(Direction.UP, 1.2));
                //    }
                //}
                //else {
                //    LOGGER.info("EMPTY");
                //}

//                for (ItemStack stack : list) {
//                    Block.popResource(serverLevel, pos, stack);
//                    LOGGER.info("AFTER DROP");
//                }
            }
        }
    }

    public static ResourceLocation rl(String path) {
        return ResourceLocation.fromNamespaceAndPath(MODID, path);
    }


}
