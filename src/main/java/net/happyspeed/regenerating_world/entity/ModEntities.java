package net.happyspeed.regenerating_world.entity;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

import static net.happyspeed.regenerating_world.RegeneratingWorld.MODID;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
        DeferredRegister.create(BuiltInRegistries.ENTITY_TYPE, MODID);

    public static final Supplier<EntityType<ReseedProjectileEntity>> RESEED_PROJECTILE =
        ENTITY_TYPES.register("reseed_projectile", () ->
            EntityType.Builder.<ReseedProjectileEntity>of(ReseedProjectileEntity::new, MobCategory.MISC)
                .sized(0.25F, 0.25F)
                .clientTrackingRange(4)
                .updateInterval(10)
                .build("reseed_projectile"));
}
