package net.happyspeed.regenerating_world.util;

import net.happyspeed.regenerating_world.RegeneratingWorld;
import net.happyspeed.regenerating_world.mod_items.ModItems;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;

public class ExactTier implements Tier {
    @Override public int getUses() {
        return 1250;
    }
    @Override public float getSpeed() {
        return 20.0f;
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
