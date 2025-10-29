package net.happyspeed.regenerating_world.mod_items;

import net.happyspeed.regenerating_world.RegeneratingWorld;
import net.happyspeed.regenerating_world.mod_blocks.*;
import net.happyspeed.regenerating_world.util.*;
import net.happyspeed.thrivingblocks.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ColorRGBA;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.grower.TreeGrower;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import static net.happyspeed.regenerating_world.RegeneratingWorld.MODID;

public class ModItems {
    private static boolean never(BlockState state, BlockGetter blockGetter, BlockPos pos) {
        return false;
    }

    // Create a Deferred Register to hold Blocks which will all be registered under the "regenerating_world" namespace
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(MODID);

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES =
            DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, MODID);





    // Create a Deferred Register to hold Items which will all be registered under the "regenerating_world" namespace
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MODID);
    // Create a Deferred Register to hold CreativeModeTabs which will all be registered under the "regenerating_world" namespace


    // Creates a new Block with the id "regenerating_world:example_block", combining the namespace and path
    public static final DeferredBlock<Block> PARCHED_DIRT_BLOCK = BLOCKS.registerSimpleBlock("parched_dirt", BlockBehaviour.Properties.of().mapColor(MapColor.DIRT).sound(SoundType.GRAVEL).strength(0.5f));

    public static final DeferredItem<BlockItem> PARCHED_DIRT_BLOCK_ITEM = ITEMS.registerSimpleBlockItem("parched_dirt", PARCHED_DIRT_BLOCK);

    public static final DeferredBlock<Block> WEATHERED_SANDSTONE_BLOCK = BLOCKS.registerSimpleBlock("weathered_sandstone", BlockBehaviour.Properties.of().mapColor(MapColor.SAND).sound(SoundType.STONE).strength(2.0f).requiresCorrectToolForDrops());

    public static final DeferredItem<BlockItem> WEATHERED_SANDSTONE_BLOCK_ITEM = ITEMS.registerSimpleBlockItem("weathered_sandstone", WEATHERED_SANDSTONE_BLOCK);

    public static final DeferredBlock<Block> WEATHERED_SAND_BLOCK = BLOCKS.register("weathered_sand",
            name -> new ColoredFallingBlock(
                    new ColorRGBA(14406560),
                    BlockBehaviour.Properties.of().mapColor(MapColor.SAND).instrument(NoteBlockInstrument.SNARE).strength(0.5F).sound(SoundType.SAND)
            ));

    public static final DeferredItem<BlockItem> WEATHERED_SAND_BLOCK_ITEM = ITEMS.registerSimpleBlockItem("weathered_sand", WEATHERED_SAND_BLOCK);

    public static final DeferredBlock<Block> WEATHERED_STONE_BLOCK = BLOCKS.registerSimpleBlock("weathered_stone", BlockBehaviour.Properties.of().mapColor(MapColor.STONE).sound(SoundType.STONE).strength(1.0f).requiresCorrectToolForDrops());

    public static final DeferredItem<BlockItem> WEATHERED_STONE_BLOCK_ITEM = ITEMS.registerSimpleBlockItem("weathered_stone", WEATHERED_STONE_BLOCK);

    public static final DeferredBlock<Block> WEATHERED_DEEPSLATE_BLOCK = BLOCKS.register(
            "weathered_deepslate",
            name -> new RotatedPillarBlock(
                    BlockBehaviour.Properties.of()
                            .mapColor(MapColor.DEEPSLATE)
                            .instrument(NoteBlockInstrument.BASEDRUM)
                            .requiresCorrectToolForDrops()
                            .strength(8.0F, 2.0F)
                            .sound(SoundType.DEEPSLATE)
            )
    );

    public static final DeferredItem<BlockItem> WEATHERED_DEEPSLATE_BLOCK_ITEM = ITEMS.registerSimpleBlockItem("weathered_deepslate", WEATHERED_DEEPSLATE_BLOCK);


    public static final DeferredBlock<Block> ORIGIN_POINT_BLOCK = BLOCKS.registerSimpleBlock("origin_point", BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PURPLE).sound(SoundType.TRIAL_SPAWNER).strength(0.4f));

    public static final DeferredItem<BlockItem> ORIGIN_POINT_BLOCK_ITEM = ITEMS.registerSimpleBlockItem("origin_point", ORIGIN_POINT_BLOCK);


    public static final DeferredItem<Item> OAK_SEED_ITEM = ITEMS.registerSimpleItem("oak_seed");
    public static final DeferredItem<Item> SPRUCE_SEED_ITEM = ITEMS.registerSimpleItem("spruce_seed");

    public static final DeferredItem<Item> SAND_PILE_ITEM = ITEMS.registerSimpleItem("sand_pile");
    public static final DeferredItem<Item> COARSE_DIRT_PILE_ITEM = ITEMS.registerSimpleItem("coarse_dirt_pile");

    public static final DeferredItem<Item> AMBER_CLUMP_ITEM = ITEMS.registerSimpleItem("amber_clump");



    public static final DeferredItem<Item> CLAY_BUCKET = ITEMS.register("clay_bucket", () -> new ClayBucketItem(Fluids.EMPTY, new Item.Properties().stacksTo(1)));

    public static final DeferredItem<Item> CLAY_WATER_BUCKET = ITEMS.register("clay_water_bucket", () -> new ClayBucketItem(Fluids.WATER, new Item.Properties().craftRemainder(CLAY_BUCKET.asItem()).stacksTo(1)));





    public static final DeferredBlock<Block> TUNGSTEN_BLOCK = BLOCKS.registerSimpleBlock("tungsten", BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_BLUE).sound(ModSoundTypes.METALIC_BLOCK).strength(1.5f).requiresCorrectToolForDrops());

    public static final DeferredItem<BlockItem> TUNGSTEN_BLOCK_ITEM = ITEMS.registerSimpleBlockItem("tungsten", TUNGSTEN_BLOCK);

    public static final DeferredBlock<Block> RIBBED_TUNGSTEN_BLOCK = BLOCKS.registerSimpleBlock("ribbed_tungsten", BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLUE).sound(ModSoundTypes.METALIC_BLOCK).strength(1.5f).requiresCorrectToolForDrops());

    public static final DeferredItem<BlockItem> RIBBED_TUNGSTEN_BLOCK_ITEM = ITEMS.registerSimpleBlockItem("ribbed_tungsten", RIBBED_TUNGSTEN_BLOCK);


    public static final DeferredBlock<Block> TUNGSTEN_ORE_BLOCK = BLOCKS.registerSimpleBlock("tungsten_ore", BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_BLUE).sound(SoundType.STONE).strength(1.5f).requiresCorrectToolForDrops());

    public static final DeferredItem<BlockItem> TUNGSTEN_ORE_BLOCK_ITEM = ITEMS.registerSimpleBlockItem("tungsten_ore", TUNGSTEN_ORE_BLOCK);

    public static final DeferredBlock<Block> DEEPSLATE_TUNGSTEN_ORE_BLOCK = BLOCKS.registerSimpleBlock("deepslate_tungsten_ore", BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_BLUE).sound(SoundType.DEEPSLATE).strength(1.5f).requiresCorrectToolForDrops());

    public static final DeferredItem<BlockItem> DEEPSLATE_TUNGSTEN_ORE_BLOCK_ITEM = ITEMS.registerSimpleBlockItem("deepslate_tungsten_ore", DEEPSLATE_TUNGSTEN_ORE_BLOCK);


    public static final DeferredBlock<Block> TUNGSTEN_GLASS_BLOCK = BLOCKS.register("tungsten_glass",() -> new TungstenGlassBlock(BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_LIGHT_BLUE).sound(SoundType.GLASS)
            .strength(1.5f).requiresCorrectToolForDrops()
            .noOcclusion()
            .isViewBlocking(ModItems::never)
            .isValidSpawn(Blocks::never)
            .isRedstoneConductor(ModItems::never)
            .isSuffocating(ModItems::never)));

    public static final DeferredItem<BlockItem> TUNGSTEN_GLASS_BLOCK_ITEM = ITEMS.registerSimpleBlockItem("tungsten_glass", TUNGSTEN_GLASS_BLOCK);

    public static final DeferredItem<Item> TUNGSTEN_INGOT_ITEM = ITEMS.registerSimpleItem("tungsten_ingot");

    public static final DeferredItem<Item> RAW_TUNGSTEN_ITEM = ITEMS.registerSimpleItem("raw_tungsten");

    public static final DeferredItem<Item> DRY_WHEAT_SEEDS_ITEM = ITEMS.registerSimpleItem("dry_wheat_seeds");

    public static final DeferredItem<Item> DRY_SUGAR_CANE_ITEM = ITEMS.registerSimpleItem("dry_sugar_cane");

    public static final DeferredItem<Item> HARDENED_AMBER_INGOT_ITEM = ITEMS.registerSimpleItem("hardened_amber_ingot");

    public static final DeferredItem<Item> HARDENED_AMBER_HELMET_ITEM = ITEMS.register("hardened_amber_helmet", () -> new ArmorItem(
            ModArmorMaterials.HARDENED_AMBER,
            ArmorItem.Type.HELMET,
            new Item.Properties().stacksTo(1).component(DataComponents.ATTRIBUTE_MODIFIERS, ItemAttributeModifiers.builder()
                    .add(
                            Attributes.MOVEMENT_SPEED,
                            new AttributeModifier(
                                    ResourceLocation.fromNamespaceAndPath(MODID, "amber_helmet_speed"),
                                    0.10, // +10% speed
                                    AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
                            ),
                            EquipmentSlotGroup.HEAD
                    )
                    .add(
                            Attributes.ARMOR,
                            new AttributeModifier(
                                    ResourceLocation.fromNamespaceAndPath(MODID, "amber_helmet_armor"),
                                    1,
                                    AttributeModifier.Operation.ADD_VALUE
                            ),
                            EquipmentSlotGroup.HEAD
                    )
                    .build()
            )
    ));

    public static final DeferredItem<Item> HARDENED_AMBER_CHESTPLATE_ITEM = ITEMS.register("hardened_amber_chestplate", () -> new ArmorItem(
            ModArmorMaterials.HARDENED_AMBER,
            ArmorItem.Type.CHESTPLATE,
            new Item.Properties().stacksTo(1).component(DataComponents.ATTRIBUTE_MODIFIERS, ItemAttributeModifiers.builder()
                    .add(
                            Attributes.MOVEMENT_SPEED,
                            new AttributeModifier(
                                    ResourceLocation.fromNamespaceAndPath(MODID, "amber_chestplate_speed"),
                                    0.10, // +10% speed
                                    AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
                            ),
                            EquipmentSlotGroup.CHEST
                    )
                    .add(
                            Attributes.ARMOR,
                            new AttributeModifier(
                                    ResourceLocation.fromNamespaceAndPath(MODID, "amber_chestplate_armor"),
                                    1,
                                    AttributeModifier.Operation.ADD_VALUE
                            ),
                            EquipmentSlotGroup.CHEST
                    )
                    .build()
            )));

    public static final DeferredItem<Item> HARDENED_AMBER_LEGGINGS_ITEM = ITEMS.register("hardened_amber_leggings", () -> new ArmorItem(
            ModArmorMaterials.HARDENED_AMBER,
            ArmorItem.Type.LEGGINGS,
            new Item.Properties().stacksTo(1).component(DataComponents.ATTRIBUTE_MODIFIERS, ItemAttributeModifiers.builder()
                    .add(
                            Attributes.MOVEMENT_SPEED,
                            new AttributeModifier(
                                    ResourceLocation.fromNamespaceAndPath(MODID, "amber_leggings_speed"),
                                    0.10, // +10% speed
                                    AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
                            ),
                            EquipmentSlotGroup.LEGS
                    )
                    .add(
                            Attributes.ARMOR,
                            new AttributeModifier(
                                    ResourceLocation.fromNamespaceAndPath(MODID, "amber_leggings_armor"),
                                    1,
                                    AttributeModifier.Operation.ADD_VALUE
                            ),
                            EquipmentSlotGroup.LEGS
                    )
                    .build()
            )));

    public static final DeferredItem<Item> HARDENED_AMBER_BOOTS_ITEM = ITEMS.register("hardened_amber_boots", () -> new ArmorItem(
            ModArmorMaterials.HARDENED_AMBER,
            ArmorItem.Type.BOOTS,
            new Item.Properties().stacksTo(1).component(DataComponents.ATTRIBUTE_MODIFIERS, ItemAttributeModifiers.builder()
                    .add(
                            Attributes.MOVEMENT_SPEED,
                            new AttributeModifier(
                                    ResourceLocation.fromNamespaceAndPath(MODID, "amber_boots_speed"),
                                    0.10, // +10% speed
                                    AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
                            ),
                            EquipmentSlotGroup.FEET
                    )
                    .add(
                            Attributes.ARMOR,
                            new AttributeModifier(
                                    ResourceLocation.fromNamespaceAndPath(MODID, "amber_boots_armor"),
                                    1,
                                    AttributeModifier.Operation.ADD_VALUE
                            ),
                            EquipmentSlotGroup.FEET
                    )
                    .build()
            )
    ));


    public static final DeferredItem<PickaxeItem> AMBER_PICKAXE =
            ITEMS.register("amber_pickaxe",
                    () -> new PickaxeItem(
                            new AmberTier(),
                            new Item.Properties()
                                    .stacksTo(1).attributes(PickaxeItem.createAttributes(new AmberTier(),1.0F, -2.8F))
                    )
            );

    public static final DeferredItem<VaultPoleItem> VAULT_POLE =
            ITEMS.register("vault_pole",
                    () -> new VaultPoleItem(
                            new Item.Properties()
                                    .stacksTo(1).component(DataComponents.ATTRIBUTE_MODIFIERS, ItemAttributeModifiers.builder()
                                            .add(
                                                    Attributes.SAFE_FALL_DISTANCE,
                                                    new AttributeModifier(
                                                            ResourceLocation.fromNamespaceAndPath(MODID, "vault_pole_safe_fall"),
                                                            6, // more safe fall distance
                                                            AttributeModifier.Operation.ADD_VALUE
                                                    ),
                                                    EquipmentSlotGroup.HAND
                                            ).build())
                    )
            );



    public static final DeferredItem<Item> GLIDER_HELMET_ITEM = ITEMS.register("glider_helmet", () -> new ArmorItem(
            ModArmorMaterials.GLIDER,
            ArmorItem.Type.HELMET,
            new Item.Properties().stacksTo(1).component(DataComponents.ATTRIBUTE_MODIFIERS, ItemAttributeModifiers.builder()
                    .add(
                            ModAttributes.AIR_MOBILITY,
                            new AttributeModifier(
                                    ResourceLocation.fromNamespaceAndPath(MODID, "glider_helmet_air_speed"),
                                    0.15,
                                    AttributeModifier.Operation.ADD_VALUE
                            ),
                            EquipmentSlotGroup.HEAD
                    )
                    .build()
            )
    ));

    public static final DeferredItem<Item> COPSTEN_BLEND_ITEM = ITEMS.registerSimpleItem("copsten_blend");

    public static final DeferredItem<Item> COMPOSITE_MESH_ITEM = ITEMS.registerSimpleItem("composite_mesh");


    public static final DeferredBlock<Block> SIMPLE_GROW_LIGHT_BLOCK = BLOCKS.register("simple_grow_light", () -> new SimpleGrowLightBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_ORANGE).sound(ModSoundTypes.METALIC_BLOCK).strength(1.5f).requiresCorrectToolForDrops().lightLevel(p_50870_ -> 10)));

    public static final DeferredItem<BlockItem> SIMPLE_GROW_LIGHT_BLOCK_ITEM = ITEMS.registerSimpleBlockItem("simple_grow_light", SIMPLE_GROW_LIGHT_BLOCK);

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<SimpleGrowLightTickingEntity>> SIMPLE_GROW_LIGHT_BLOCK_ENTITY =
            BLOCK_ENTITY_TYPES.register("simple_grow_light_block_entity",
                    () -> BlockEntityType.Builder.of(SimpleGrowLightTickingEntity::new, SIMPLE_GROW_LIGHT_BLOCK.get()).build(null));



    public static final DeferredBlock<Block> COMPFUSION_MATRIX_BLOCK = BLOCKS.register("compfusion_matrix", () -> new CompfusionMatrixBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_LIGHT_GRAY).sound(SoundType.COPPER).strength(1.5f).requiresCorrectToolForDrops()));

    public static final DeferredItem<BlockItem> COMPFUSION_MATRIX_BLOCK_ITEM = ITEMS.registerSimpleBlockItem("compfusion_matrix", COMPFUSION_MATRIX_BLOCK);

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<CompfusionMatrixTickingEntity>> COMPFUSION_MATRIX_BLOCK_ENTITY =
            BLOCK_ENTITY_TYPES.register("compfusion_matrix_block_entity",
                    () -> BlockEntityType.Builder.of(CompfusionMatrixTickingEntity::new, COMPFUSION_MATRIX_BLOCK.get()).build(null));


    public static final DeferredBlock<Block> FERN_LIGHT_BLOCK = BLOCKS.register("fern_light", () -> new FernLightBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_ORANGE).sound(ModSoundTypes.METALIC_BLOCK).strength(1.5f).requiresCorrectToolForDrops().lightLevel(p_50870_ -> 10)));

    public static final DeferredItem<BlockItem> FERN_LIGHT_BLOCK_ITEM = ITEMS.registerSimpleBlockItem("fern_light", FERN_LIGHT_BLOCK);

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<FernLightTickingEntity>> FERN_LIGHT_BLOCK_ENTITY =
            BLOCK_ENTITY_TYPES.register("fern_light_block_entity",
                    () -> BlockEntityType.Builder.of(FernLightTickingEntity::new, FERN_LIGHT_BLOCK.get()).build(null));



    public static final DeferredBlock<Block> BRITTLECRETE_BLOCK = BLOCKS.registerSimpleBlock("brittlecrete", BlockBehaviour.Properties.of().mapColor(MapColor.RAW_IRON).sound(SoundType.CALCITE).strength(0.7f).requiresCorrectToolForDrops());

    public static final DeferredItem<BlockItem> BRITTLECRETE_BLOCK_ITEM = ITEMS.registerSimpleBlockItem("brittlecrete", BRITTLECRETE_BLOCK);

    public static final DeferredBlock<Block> RED_BRITTLECRETE_BLOCK = BLOCKS.registerSimpleBlock("red_brittlecrete", BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_RED).sound(SoundType.CALCITE).strength(0.7f).requiresCorrectToolForDrops());

    public static final DeferredItem<BlockItem> RED_BRITTLECRETE_BLOCK_ITEM = ITEMS.registerSimpleBlockItem("red_brittlecrete", RED_BRITTLECRETE_BLOCK);


    public static final DeferredBlock<Block> BRITTLECRETE_BLOCK_SLAB = BLOCKS.register("brittlecrete_slab",
            id -> new SlabBlock(BlockBehaviour.Properties.of().mapColor(MapColor.RAW_IRON)
                    .strength(0.7f)
                    .sound(SoundType.CALCITE)));

    public static final DeferredItem<Item> BRITTLECRETE_BLOCK_SLAB_ITEM = ITEMS.register("brittlecrete_slab",
            id -> new BlockItem(BRITTLECRETE_BLOCK_SLAB.get(), new Item.Properties()));






    public static final DeferredBlock<Block> ARCHIVE_PORTAL_BLOCK = BLOCKS.register("archive_portal",() -> new ArchivePortalBlock(BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_LIGHT_BLUE).sound(SoundType.GLASS)
            .strength(1.5f).requiresCorrectToolForDrops()
            .noOcclusion()
            .noCollission()
            .isViewBlocking(ModItems::never)
            .isValidSpawn(Blocks::never)
            .isRedstoneConductor(ModItems::never)
            .isSuffocating(ModItems::never)));

    public static final DeferredBlock<Block> RETURN_PORTAL_BLOCK = BLOCKS.register("return_portal",() -> new ReturnPortalBlock(BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_LIGHT_BLUE).sound(SoundType.GLASS)
            .strength(1.5f).requiresCorrectToolForDrops()
            .noOcclusion()
            .noCollission()
            .isViewBlocking(ModItems::never)
            .isValidSpawn(Blocks::never)
            .isRedstoneConductor(ModItems::never)
            .isSuffocating(ModItems::never)));



    public static final DeferredBlock<Block> MINERAL_GRASS_BLOCK = BLOCKS.register("mineral_grass", () -> new OrganismBlock(
            new ArrayList<Block>(Arrays.asList(Blocks.DIRT, Blocks.ROOTED_DIRT, Blocks.COARSE_DIRT)),
            new ArrayList<Block>(Arrays.asList(Blocks.GRASS_BLOCK)),
            new ArrayList<Block>(),
            BlockBehaviour.Properties.of().mapColor(MapColor.DIRT).sound(SoundType.GRAVEL).strength(1.5f).requiresCorrectToolForDrops()));

    public static final DeferredItem<BlockItem> MINERAL_GRASS_BLOCK_ITEM = ITEMS.registerSimpleBlockItem("mineral_grass", MINERAL_GRASS_BLOCK);

    public static final DeferredBlock<Block> CLEAN_MINERAL_GRASS_BLOCK = BLOCKS.register("clean_mineral_grass", () -> new OrganismBlock(
            new ArrayList<Block>(Arrays.asList(Blocks.DIRT)),
            new ArrayList<Block>(Arrays.asList(Blocks.GRASS_BLOCK)),
            new ArrayList<Block>(),
            BlockBehaviour.Properties.of().mapColor(MapColor.DIRT).sound(SoundType.GRAVEL).strength(1.5f).requiresCorrectToolForDrops()));

    public static final DeferredItem<BlockItem> CLEAN_MINERAL_GRASS_BLOCK_ITEM = ITEMS.registerSimpleBlockItem("clean_mineral_grass", CLEAN_MINERAL_GRASS_BLOCK);

    public static final DeferredBlock<Block> MINERAL_DIRT_BLOCK = BLOCKS.register("mineral_dirt", () -> new OrganismBlock(
            new ArrayList<Block>(Arrays.asList(Blocks.DIRT)),
            new ArrayList<Block>(),
            new ArrayList<Block>(),
            BlockBehaviour.Properties.of().mapColor(MapColor.DIRT).sound(SoundType.GRAVEL).strength(1.5f).requiresCorrectToolForDrops()));

    public static final DeferredItem<BlockItem> MINERAL_DIRT_BLOCK_ITEM = ITEMS.registerSimpleBlockItem("mineral_dirt", MINERAL_DIRT_BLOCK);

    public static final DeferredBlock<Block> MINERAL_SAND_BLOCK = BLOCKS.register("mineral_sand", () -> new OrganismBlock(
            new ArrayList<Block>(Arrays.asList(Blocks.SAND)),
            new ArrayList<Block>(),
            new ArrayList<Block>(Arrays.asList(Blocks.SANDSTONE)),
            BlockBehaviour.Properties.of().mapColor(MapColor.SAND).sound(SoundType.SAND).strength(1.5f).requiresCorrectToolForDrops()));

    public static final DeferredItem<BlockItem> MINERAL_SAND_BLOCK_ITEM = ITEMS.registerSimpleBlockItem("mineral_sand", MINERAL_SAND_BLOCK);


    public static final DeferredBlock<Block> MINERAL_MUD_BLOCK = BLOCKS.register("mineral_mud", () -> new OrganismBlock(
            new ArrayList<Block>(Arrays.asList(Blocks.MUD)),
            new ArrayList<Block>(),
            new ArrayList<Block>(),
            BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BROWN).sound(SoundType.MUD).strength(1.5f).requiresCorrectToolForDrops()));

    public static final DeferredItem<BlockItem> MINERAL_MUD_BLOCK_ITEM = ITEMS.registerSimpleBlockItem("mineral_mud", MINERAL_MUD_BLOCK);

    public static final DeferredBlock<Block> MINERAL_PACKED_MUD_BLOCK = BLOCKS.register("mineral_packed_mud", () -> new OrganismBlock(
            new ArrayList<Block>(Arrays.asList(Blocks.PACKED_MUD)),
            new ArrayList<Block>(),
            new ArrayList<Block>(),
            BlockBehaviour.Properties.of().mapColor(MapColor.RAW_IRON).sound(SoundType.PACKED_MUD).strength(1.5f).requiresCorrectToolForDrops()));

    public static final DeferredItem<BlockItem> MINERAL_PACKED_MUD_BLOCK_ITEM = ITEMS.registerSimpleBlockItem("mineral_packed_mud", MINERAL_PACKED_MUD_BLOCK);

    public static final DeferredBlock<Block> MINERAL_FULL_GRASS_BLOCK = BLOCKS.register("mineral_full_grass", () -> new OrganismBlock(
            new ArrayList<Block>(Arrays.asList(ModBlocks.FULL_GRASS_BLOCK.get())),
            new ArrayList<Block>(),
            new ArrayList<Block>(),
            BlockBehaviour.Properties.of().mapColor(MapColor.RAW_IRON).sound(SoundType.PACKED_MUD).strength(1.5f).requiresCorrectToolForDrops()));

    public static final DeferredItem<BlockItem> MINERAL_FULL_GRASS_BLOCK_ITEM = ITEMS.registerSimpleBlockItem("mineral_full_grass", MINERAL_FULL_GRASS_BLOCK);

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<OrganismBlockTickingEntity>> REPLICATOR_BLOCK_ENTITY =
            BLOCK_ENTITY_TYPES.register("replicator_block_entity",
                    () -> BlockEntityType.Builder.of(OrganismBlockTickingEntity::new,
                            MINERAL_GRASS_BLOCK.get(),
                            CLEAN_MINERAL_GRASS_BLOCK.get(),
                            MINERAL_DIRT_BLOCK.get(),
                            MINERAL_SAND_BLOCK.get(),
                            MINERAL_MUD_BLOCK.get(),
                            MINERAL_PACKED_MUD_BLOCK.get(),
                            MINERAL_FULL_GRASS_BLOCK.get()
                            //Place for more mineral blocks
                    ).build(null));





    public static final DeferredItem<SingleBlockShovelItem> EXACT_SHOVEL =
            ITEMS.register("exact_shovel",
                    () -> new SingleBlockShovelItem(
                            new ExactTier(),
                            new Item.Properties()
                                    .stacksTo(1).attributes(ShovelItem.createAttributes(new AmberTier(),1.0F, -2.8F))
                    )
            );


    static ResourceKey<ConfiguredFeature<?, ?>> FANCY_OAK_KEY =
            ResourceKey.create(Registries.CONFIGURED_FEATURE,ResourceLocation.withDefaultNamespace( "fancy_oak"));

    static TreeGrower fancyOakGrower = new TreeGrower(
            "fancy_oak",
            Optional.empty(), // megaTree
            Optional.of(FANCY_OAK_KEY), // tree
            Optional.empty() // flowers
    );


    public static final DeferredBlock<Block> FANCY_OAK_SAPLING =
            BLOCKS.register("fancy_oak_sapling",
                    () -> new SaplingBlock(
                            fancyOakGrower,
                            BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SAPLING)
                    ));

    public static final DeferredItem<BlockItem> FANCY_OAK_SAPLING_ITEM = ITEMS.registerSimpleBlockItem("fancy_oak_sapling", FANCY_OAK_SAPLING);




    static ResourceKey<ConfiguredFeature<?, ?>> SWAMP_OAK_KEY =
            ResourceKey.create(Registries.CONFIGURED_FEATURE,ResourceLocation.withDefaultNamespace( "swamp_oak"));

    static TreeGrower swampOakGrower = new TreeGrower(
            "swamp_oak",
            Optional.empty(), // megaTree
            Optional.of(SWAMP_OAK_KEY), // tree
            Optional.empty() // flowers
    );


    public static final DeferredBlock<Block> SWAMP_OAK_SAPLING =
            BLOCKS.register("swamp_oak_sapling",
                    () -> new SaplingBlock(
                            swampOakGrower,
                            BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SAPLING)
                    ));

    public static final DeferredItem<BlockItem> SWAMP_OAK_SAPLING_ITEM = ITEMS.registerSimpleBlockItem("swamp_oak_sapling", SWAMP_OAK_SAPLING);

    static ResourceKey<ConfiguredFeature<?, ?>> OVERGROWTH_MED_OAK_KEY =
            ResourceKey.create(Registries.CONFIGURED_FEATURE,ResourceLocation.fromNamespaceAndPath( MODID, "medium_oak"));

    static ResourceKey<ConfiguredFeature<?, ?>> OVERGROWTH_LAR_OAK_KEY =
            ResourceKey.create(Registries.CONFIGURED_FEATURE,ResourceLocation.fromNamespaceAndPath( MODID, "large_oak"));

    static TreeGrower overgrowthOakGrower = new TreeGrower(
            "overgrowth_oak",
            Optional.of(OVERGROWTH_LAR_OAK_KEY), // megaTree
            Optional.of(OVERGROWTH_MED_OAK_KEY), // tree
            Optional.empty() // flowers
    );

    public static final DeferredBlock<Block> OVERGROWTH_OAK_SAPLING =
            BLOCKS.register("overgrowth_oak_sapling",
                    () -> new SaplingBlock(
                            overgrowthOakGrower,
                            BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SAPLING)
                    ));

    public static final DeferredItem<BlockItem> OVERGROWTH_OAK_SAPLING_ITEM = ITEMS.registerSimpleBlockItem("overgrowth_oak_sapling", OVERGROWTH_OAK_SAPLING);


    static ResourceKey<ConfiguredFeature<?, ?>> BUSH_OAK_KEY =
            ResourceKey.create(Registries.CONFIGURED_FEATURE,ResourceLocation.fromNamespaceAndPath( MODID ,"oak_bush"));

    static TreeGrower bushOakGrower = new TreeGrower(
            "bush_oak",
            Optional.empty(), // megaTree
            Optional.of(BUSH_OAK_KEY), // tree
            Optional.empty() // flowers
    );


    public static final DeferredBlock<Block> BUSH_OAK_SAPLING =
            BLOCKS.register("bush_oak_sapling",
                    () -> new SaplingBlock(
                            bushOakGrower,
                            BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SAPLING)
                    ));

    public static final DeferredItem<BlockItem> BUSH_OAK_SAPLING_ITEM = ITEMS.registerSimpleBlockItem("bush_oak_sapling", BUSH_OAK_SAPLING);




    public static final DeferredItem<VineBombItem> VINE_BOMB =
            ITEMS.register("vine_bomb",
                    () -> new VineBombItem(
                            new Item.Properties()
                    )
            );

    //Different from Vine Bomb
    public static final DeferredItem<VineBoomItem> VINE_BOOM =
            ITEMS.register("vine_boom",
                    () -> new VineBoomItem(
                            new Item.Properties()
                    )
            );


    public static final DeferredItem<ReseedStaff> RESEED_STAFF =
            ITEMS.register("reseed_staff",
                    () -> new ReseedStaff(
                            new Item.Properties()
                                    .stacksTo(1)
                    )
            );


    public static final DeferredItem<Item> RESEED_THROWABLE_ITEM = ITEMS.registerSimpleItem("reseed_throwable");

    public static final DeferredItem<Item> BONE_MASH_BUNDLE_ITEM = ITEMS.registerSimpleItem("bone_mash_bundle");


    public static final DeferredItem<BiomePaintBrushItem> BIOME_PAINTBRUSH =
            ITEMS.register("biome_paintbrush",
                    () -> new BiomePaintBrushItem(
                            new Item.Properties()
                                    .stacksTo(1)
                    )
            );

    public static final DeferredBlock<Block> SHEARMETAL_BLOCK = BLOCKS.registerSimpleBlock("shearmetal_block", BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_GRAY).sound(ModSoundTypes.METALIC_BLOCK).strength(1.5f).requiresCorrectToolForDrops());

    public static final DeferredItem<BlockItem> SHEARMETAL_BLOCK_ITEM = ITEMS.registerSimpleBlockItem("shearmetal_block", SHEARMETAL_BLOCK);

    public static final DeferredBlock<Block> CARVED_SHEARMETAL_BLOCK = BLOCKS.registerSimpleBlock("carved_shearmetal_block", BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_GRAY).sound(ModSoundTypes.METALIC_BLOCK).strength(1.5f).requiresCorrectToolForDrops());

    public static final DeferredItem<BlockItem> CARVED_SHEARMETAL_BLOCK_ITEM = ITEMS.registerSimpleBlockItem("carved_shearmetal_block", CARVED_SHEARMETAL_BLOCK);

    public static final DeferredBlock<Block> WORN_SHEARMETAL_BLOCK = BLOCKS.registerSimpleBlock("worn_shearmetal_block", BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_GRAY).sound(ModSoundTypes.METALIC_BLOCK).strength(1.5f).requiresCorrectToolForDrops());

    public static final DeferredItem<BlockItem> WORN_SHEARMETAL_BLOCK_ITEM = ITEMS.registerSimpleBlockItem("worn_shearmetal_block", WORN_SHEARMETAL_BLOCK);

    public static final DeferredBlock<Block> SMOOTH_SHEARMETAL_BLOCK = BLOCKS.registerSimpleBlock("smooth_shearmetal_block", BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_GRAY).sound(ModSoundTypes.METALIC_BLOCK).strength(1.5f).requiresCorrectToolForDrops());

    public static final DeferredItem<BlockItem> SMOOTH_SHEARMETAL_BLOCK_ITEM = ITEMS.registerSimpleBlockItem("smooth_shearmetal_block", SMOOTH_SHEARMETAL_BLOCK);

    public static final DeferredBlock<Block> TORN_SHEARMETAL_BLOCK =
            BLOCKS.register("torn_shearmetal_block",
                    id -> new SixWayDirectionalBlock(BlockBehaviour.Properties.of()
                            .mapColor(MapColor.COLOR_GRAY).sound(ModSoundTypes.METALIC_BLOCK)
                            .strength(1.5f).requiresCorrectToolForDrops().noCollission().noOcclusion()));

    public static final DeferredItem<BlockItem> TORN_SHEARMETAL_BLOCK_ITEM = ITEMS.registerSimpleBlockItem("torn_shearmetal_block", TORN_SHEARMETAL_BLOCK);


    //Im Grate!
    public static final DeferredBlock<Block> SHEARMETAL_GRATE_BLOCK = BLOCKS.register("shearmetal_grate",() -> new TungstenGlassBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_GRAY).sound(ModSoundTypes.METALIC_BLOCK).strength(1.5f).requiresCorrectToolForDrops()
            .noOcclusion()
            .isViewBlocking(ModItems::never)
            .isValidSpawn(Blocks::never)
            .isRedstoneConductor(ModItems::never)
            .isSuffocating(ModItems::never)));


    public static final DeferredItem<BlockItem> SHEARMETAL_GRATE_BLOCK_ITEM = ITEMS.registerSimpleBlockItem("shearmetal_grate", SHEARMETAL_GRATE_BLOCK);


    public static final DeferredBlock<Block> CABLE_BUNDLE_BLOCK = BLOCKS.register("cable_bundle",() -> new RotatedPillarBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_GRAY).sound(ModSoundTypes.METALIC_BLOCK).strength(1.5f).requiresCorrectToolForDrops()));


    public static final DeferredItem<BlockItem> CABLE_BUNDLE_BLOCK_ITEM = ITEMS.registerSimpleBlockItem("cable_bundle", CABLE_BUNDLE_BLOCK);


    public static final DeferredBlock<Block> ION_STATUS_BOARD_BLOCK = BLOCKS.register("ion_status_board", () -> new IonMatrixDisplayPanel(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_GRAY).sound(ModSoundTypes.METALIC_BLOCK).strength(1.5f).requiresCorrectToolForDrops()));


    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<IonMatrixDisplayPanelTickingEntity>> ION_STATUS_BOARD_BLOCK_ENTITY =
            BLOCK_ENTITY_TYPES.register("ion_status_board_block_entity",
                    () -> BlockEntityType.Builder.of(IonMatrixDisplayPanelTickingEntity::new, ION_STATUS_BOARD_BLOCK.get()).build(null));

    public static final DeferredItem<BlockItem> ION_STATUS_BOARD_BLOCK_ITEM = ITEMS.registerSimpleBlockItem("ion_status_board", ION_STATUS_BOARD_BLOCK);

    public static final DeferredBlock<Block> GREEN_WARNLIGHT_BLOCK = BLOCKS.registerSimpleBlock("green_warnlight", BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_LIGHT_GREEN).sound(ModSoundTypes.METALIC_BLOCK).strength(1.5f).requiresCorrectToolForDrops().lightLevel(p_50870_ -> 4));

    public static final DeferredItem<BlockItem> GREEN_WARNLIGHT_BLOCK_ITEM = ITEMS.registerSimpleBlockItem("green_warnlight", GREEN_WARNLIGHT_BLOCK);

    public static final DeferredBlock<Block> WEATHERED_STONE_BRICKS_BLOCK = BLOCKS.registerSimpleBlock("weathered_stone_bricks", BlockBehaviour.Properties.of().mapColor(MapColor.STONE).sound(SoundType.STONE).strength(1.5f));

    public static final DeferredItem<BlockItem> WEATHERED_STONE_BRICKS_BLOCK_ITEM = ITEMS.registerSimpleBlockItem("weathered_stone_bricks", WEATHERED_STONE_BRICKS_BLOCK);

    public static final DeferredBlock<Block> NUTRIENTS_DEPOSIT_BLOCK = BLOCKS.registerSimpleBlock("nutrients_deposit_block", BlockBehaviour.Properties.of().mapColor(MapColor.DIRT).sound(SoundType.PACKED_MUD).strength(1.5f).requiresCorrectToolForDrops());

    public static final DeferredItem<BlockItem> NUTRIENTS_DEPOSIT_BLOCK_ITEM = ITEMS.registerSimpleBlockItem("nutrients_deposit_block", NUTRIENTS_DEPOSIT_BLOCK);

}
