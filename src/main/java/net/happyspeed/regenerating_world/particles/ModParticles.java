package net.happyspeed.regenerating_world.particles;

import net.happyspeed.regenerating_world.RegeneratingWorld;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModParticles {
    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES =
            DeferredRegister.create(BuiltInRegistries.PARTICLE_TYPE, RegeneratingWorld.MODID);

    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> AURA_PARTICLE =
            PARTICLE_TYPES.register("aura", () -> new SimpleParticleType(true));


    public static void register(IEventBus bus) {
        PARTICLE_TYPES.register(bus);
    }
}

