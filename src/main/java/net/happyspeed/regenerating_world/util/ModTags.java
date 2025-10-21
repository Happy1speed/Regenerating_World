package net.happyspeed.regenerating_world.util;

import net.happyspeed.regenerating_world.RegeneratingWorld;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;

public class ModTags {
    public static class Blocks {
        public static final TagKey<Block> DECENT_GROWING_SOIL = TagKey.create(
                Registries.BLOCK,
                ResourceLocation.fromNamespaceAndPath(RegeneratingWorld.MODID, "decent_growing_soil")
        );

        public static final TagKey<Block> GREAT_GROWING_SOIL = TagKey.create(
                Registries.BLOCK,
                ResourceLocation.fromNamespaceAndPath(RegeneratingWorld.MODID, "great_growing_soil")
        );

        public static final TagKey<Block> FERTILE_GROWING_SOIL = TagKey.create(
                Registries.BLOCK,
                ResourceLocation.fromNamespaceAndPath(RegeneratingWorld.MODID, "fertile_growing_soil")
        );

        public static final TagKey<Block> CONIFER_GROWING_SOIL = TagKey.create(
                Registries.BLOCK,
                ResourceLocation.fromNamespaceAndPath(RegeneratingWorld.MODID, "conifer_growing_soil")
        );

        public static final TagKey<Block> CONIFER_SAPLING = TagKey.create(
                Registries.BLOCK,
                ResourceLocation.fromNamespaceAndPath(RegeneratingWorld.MODID, "conifer_sapling")
        );

        public static final TagKey<Block> PITCH_BLACK_GROWABLE_SAPLING = TagKey.create(
                Registries.BLOCK,
                ResourceLocation.fromNamespaceAndPath(RegeneratingWorld.MODID, "pitch_black_growable_sapling")
        );

        public static final TagKey<Block> INCORRECT_FOR_AMBER_TOOL = TagKey.create(
                Registries.BLOCK,
                ResourceLocation.fromNamespaceAndPath(RegeneratingWorld.MODID, "incorrect_for_amber_tool")
        );

        public static final TagKey<Block> REGENERATING_SURFACE_BLOCK_REPLACABLES = TagKey.create(
                Registries.BLOCK,
                ResourceLocation.fromNamespaceAndPath(RegeneratingWorld.MODID, "regenerating_surface_block_replacables")
        );

        public static final TagKey<Block> VALID_SPAWN_RELOCATION = TagKey.create(
                Registries.BLOCK,
                ResourceLocation.fromNamespaceAndPath(RegeneratingWorld.MODID, "valid_spawn_relocation")
        );

    }
    public static class Fluids {
        public static final TagKey<Fluid> SPRUCE_SAP_FLUID = TagKey.create(
                Registries.FLUID,
                ResourceLocation.fromNamespaceAndPath(RegeneratingWorld.MODID, "spruce_sap_fluid")
        );
    }
}


