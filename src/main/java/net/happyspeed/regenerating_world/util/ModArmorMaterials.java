package net.happyspeed.regenerating_world.util;

import net.happyspeed.regenerating_world.RegeneratingWorld;
import net.happyspeed.regenerating_world.mod_items.ModItems;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class ModArmorMaterials {

    public static final DeferredRegister<ArmorMaterial> ARMOR_MATERIALS_REG =
            DeferredRegister.create(Registries.ARMOR_MATERIAL, RegeneratingWorld.MODID);

    public static final DeferredHolder<ArmorMaterial, ArmorMaterial> HARDENED_AMBER =
            ARMOR_MATERIALS_REG.register("hardened_amber", () ->
                    new ArmorMaterial(
                            Map.of(
                                    ArmorItem.Type.BOOTS, 3,
                                    ArmorItem.Type.LEGGINGS, 3,
                                    ArmorItem.Type.CHESTPLATE, 3,
                                    ArmorItem.Type.HELMET, 3
                            ),
                            22,
                            SoundEvents.ARMOR_EQUIP_DIAMOND,
                            () -> Ingredient.of(ModItems.HARDENED_AMBER_INGOT_ITEM.get()),
                            List.of(new ArmorMaterial.Layer(ResourceLocation.fromNamespaceAndPath(RegeneratingWorld.MODID, "hardened_amber"))),
                            0.0F,
                            0.0F
                    )
            );

    public static final DeferredHolder<ArmorMaterial, ArmorMaterial> GLIDER =
            ARMOR_MATERIALS_REG.register("glider", () ->
                    new ArmorMaterial(
                            Map.of(
                                    ArmorItem.Type.BOOTS, 3,
                                    ArmorItem.Type.LEGGINGS, 3,
                                    ArmorItem.Type.CHESTPLATE, 3,
                                    ArmorItem.Type.HELMET, 3
                            ),
                            22,
                            SoundEvents.ARMOR_EQUIP_DIAMOND,
                            () -> Ingredient.of(Items.PAPER.asItem()),
                            List.of(new ArmorMaterial.Layer(ResourceLocation.fromNamespaceAndPath(RegeneratingWorld.MODID, "glider"))),
                            0.0F,
                            0.0F
                    )
            );




}
