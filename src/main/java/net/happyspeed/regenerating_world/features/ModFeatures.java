package net.happyspeed.regenerating_world.features;

import net.happyspeed.regenerating_world.RegeneratingWorld;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.carver.CarverConfiguration;
import net.minecraft.world.level.levelgen.carver.WorldCarver;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.RegisterEvent;

import static net.happyspeed.regenerating_world.RegeneratingWorld.MODID;

public final class ModFeatures {
    public static final ResourceKey<Feature<?>> SUS_SAND_VEIN_FEATURE =
        ResourceKey.create(Registries.FEATURE, ResourceLocation.fromNamespaceAndPath(MODID, "sus_sand_vein"));

    public static final ResourceKey<Feature<?>> SAND_VEIN_FEATURE =
            ResourceKey.create(Registries.FEATURE, ResourceLocation.fromNamespaceAndPath(MODID, "sand_vein"));

    public static final ResourceKey<Feature<?>> PARCHED_DIRT_VEIN_FEATURE =
            ResourceKey.create(Registries.FEATURE, ResourceLocation.fromNamespaceAndPath(MODID, "parched_dirt_vein"));

    public static final ResourceKey<Feature<?>> UNBOUND_SAND_VEIN_FEATURE =
            ResourceKey.create(Registries.FEATURE, ResourceLocation.fromNamespaceAndPath(MODID, "unbound_sand_vein"));

    public static final ResourceKey<Feature<?>> FLOOR_SAND_FEATURE =
            ResourceKey.create(Registries.FEATURE, ResourceLocation.fromNamespaceAndPath(MODID, "floor_sand"));

    public static final ResourceKey<Feature<?>> CLAY_PATCH_FEATURE =
            ResourceKey.create(Registries.FEATURE, ResourceLocation.fromNamespaceAndPath(MODID, "clay_patch_vein"));

    public static final ResourceKey<Feature<?>> DISRUPTIVE_SQUARE_FEATURE =
            ResourceKey.create(Registries.FEATURE, ResourceLocation.fromNamespaceAndPath(MODID, "disruptive_square"));

    public static final ResourceKey<Feature<?>> EXAMPLE_STRUCT_STACK_FEATURE =
            ResourceKey.create(Registries.FEATURE, ResourceLocation.fromNamespaceAndPath(MODID, "example_struct_stack"));

    public static final ResourceKey<Feature<?>> LEG_FARX_FARZ_FEATURE =
            ResourceKey.create(Registries.FEATURE, ResourceLocation.fromNamespaceAndPath(MODID, "leg_farx_farz"));

    public static final ResourceKey<Feature<?>> DUST_LAYER_FEATURE =
            ResourceKey.create(Registries.FEATURE, ResourceLocation.fromNamespaceAndPath(MODID, "dust_layer"));

    public static final ResourceKey<Feature<?>> VINE_SPREAD_FEATURE =
            ResourceKey.create(Registries.FEATURE, ResourceLocation.fromNamespaceAndPath(MODID, "vine_spread"));

    public static void registerFeatures(RegisterEvent event) {
        if (event.getRegistryKey().equals(Registries.FEATURE)) {
            event.register(Registries.FEATURE, helper -> {
                helper.register(
                    SUS_SAND_VEIN_FEATURE.location(),
                    new SuspiciousSandVeinFeature(NoneFeatureConfiguration.CODEC)
                );
            });
            event.register(Registries.FEATURE, helper -> {
                helper.register(
                        SAND_VEIN_FEATURE.location(),
                        new SandVeinFeature(NoneFeatureConfiguration.CODEC)
                );
            });
            event.register(Registries.FEATURE, helper -> {
                helper.register(
                        UNBOUND_SAND_VEIN_FEATURE.location(),
                        new UnboundSandVeinFeature(NoneFeatureConfiguration.CODEC)
                );
            });

            event.register(Registries.FEATURE, helper -> {
                helper.register(
                        FLOOR_SAND_FEATURE.location(),
                        new FloorSandFeature(NoneFeatureConfiguration.CODEC)
                );
            });

            event.register(Registries.FEATURE, helper -> {
                helper.register(
                        PARCHED_DIRT_VEIN_FEATURE.location(),
                        new ParchedDirtVeinFeature(NoneFeatureConfiguration.CODEC)
                );
            });

            event.register(Registries.FEATURE, helper -> {
                helper.register(
                        CLAY_PATCH_FEATURE.location(),
                        new ClayVeinFeature(NoneFeatureConfiguration.CODEC)
                );
            });

            event.register(Registries.FEATURE, helper -> {
                helper.register(
                        EXAMPLE_STRUCT_STACK_FEATURE.location(),
                        new StackStructureFeature(NoneFeatureConfiguration.CODEC)
                );
            });

            event.register(Registries.FEATURE, helper -> {
                helper.register(
                        LEG_FARX_FARZ_FEATURE.location(),
                        new LegFarXFarZ(NoneFeatureConfiguration.CODEC)
                );
            });

            event.register(Registries.FEATURE, helper -> {
                helper.register(
                        DISRUPTIVE_SQUARE_FEATURE.location(),
                        new DisruptiveSquareFeature(NoneFeatureConfiguration.CODEC)
                );
            });

            event.register(Registries.FEATURE, helper -> {
                helper.register(
                        DUST_LAYER_FEATURE.location(),
                        new DustLayerFeature(NoneFeatureConfiguration.CODEC)
                );
            });

            event.register(Registries.FEATURE, helper -> {
                helper.register(
                        VINE_SPREAD_FEATURE.location(),
                        new VineSpreadFeature(NoneFeatureConfiguration.CODEC)
                );
            });
        }
    }
}