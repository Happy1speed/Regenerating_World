package net.happyspeed.regenerating_world.mixin;

import net.happyspeed.regenerating_world.RegeneratingWorld;
import net.happyspeed.regenerating_world.mod_items.ModItems;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(FallingBlockEntity.class)
public abstract class FallingBlockEntityMixin extends Entity {

    @Shadow public abstract BlockState getBlockState();

    public FallingBlockEntityMixin(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }

    @Redirect(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/item/FallingBlockEntity;spawnAtLocation(Lnet/minecraft/world/level/ItemLike;)Lnet/minecraft/world/entity/item/ItemEntity;", ordinal = 0))
    public ItemEntity editingTick(FallingBlockEntity instance, ItemLike itemLike) {
        if (this.getBlockState().is(ModItems.WEATHERED_SAND_BLOCK.get())) {
            return null;
        }
        return this.spawnAtLocation(itemLike);
    }

    @Redirect(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/item/FallingBlockEntity;spawnAtLocation(Lnet/minecraft/world/level/ItemLike;)Lnet/minecraft/world/entity/item/ItemEntity;", ordinal = 1))
    public ItemEntity editingTick2(FallingBlockEntity instance, ItemLike itemLike) {
        if (this.getBlockState().is(ModItems.WEATHERED_SAND_BLOCK.get())) {
            return null;
        }
        return this.spawnAtLocation(itemLike);
    }

    @Redirect(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/item/FallingBlockEntity;spawnAtLocation(Lnet/minecraft/world/level/ItemLike;)Lnet/minecraft/world/entity/item/ItemEntity;", ordinal = 2))
    public ItemEntity editingTick3(FallingBlockEntity instance, ItemLike itemLike) {
        if (this.getBlockState().is(ModItems.WEATHERED_SAND_BLOCK.get())) {
            return null;
        }
        return this.spawnAtLocation(itemLike);
    }
}
