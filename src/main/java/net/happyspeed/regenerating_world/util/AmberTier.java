package net.happyspeed.regenerating_world.util;

import net.happyspeed.regenerating_world.RegeneratingWorld;
import net.happyspeed.regenerating_world.mod_items.ModItems;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public class AmberTier implements Tier {
    @Override public int getUses() {
        return 750;
    }
    @Override public float getSpeed() {
        return 5.0f;
    }

    @Override
    public float getAttackDamageBonus() {
        return 2;
    }

    @Override
    public TagKey<Block> getIncorrectBlocksForDrops() {
        return ModTags.Blocks.INCORRECT_FOR_AMBER_TOOL;
    }

    @Override public int getEnchantmentValue() {
        return 14;
    }

    @Override public Ingredient getRepairIngredient() {
        return Ingredient.of(new ItemStack(ModItems.AMBER_CLUMP_ITEM.asItem()));
    }
}
