package net.happyspeed.regenerating_world.util;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

public class TagSearcher {

    public static Block getRandomBlockFromTag(Level level, String namespace, String tagName, Block fallback) {

        final TagKey<Block> tag =
                TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(namespace, tagName));


        var registry = level.registryAccess().registryOrThrow(Registries.BLOCK);

        Block returnBlock = registry.getTag(tag)
                .flatMap(set -> set.getRandomElement(level.random))
                .map(Holder::value)
                .orElse(fallback);
        return returnBlock;
    }

    public static Item getRandomItemFromTag(Level level, String namespace, String tagName, Item fallback) {

        final TagKey<Item> tag =
                TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(namespace, tagName));


        var registry = level.registryAccess().registryOrThrow(Registries.ITEM);

        Item returnBlock = registry.getTag(tag)
                .flatMap(set -> set.getRandomElement(level.random))
                .map(Holder::value)
                .orElse(fallback);
        return returnBlock;
    }
}
