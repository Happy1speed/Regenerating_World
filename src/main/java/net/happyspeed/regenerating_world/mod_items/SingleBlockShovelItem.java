package net.happyspeed.regenerating_world.mod_items;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class SingleBlockShovelItem extends ShovelItem {
    public SingleBlockShovelItem(Tier tier, Item.Properties properties) {
        super(tier, properties);
    }
}

