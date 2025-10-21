package net.happyspeed.regenerating_world.features;

import net.happyspeed.regenerating_world.RegeneratingWorld;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModTrunkPlacers {
    public static final DeferredRegister<TrunkPlacerType<?>> TRUNK_PLACERS =
            DeferredRegister.create(Registries.TRUNK_PLACER_TYPE, RegeneratingWorld.MODID);

    public static final Supplier<TrunkPlacerType<MegaFancyTrunkPlacer>> MEGA_FANCY_TRUNK_PLACER =
            TRUNK_PLACERS.register("mega_fancy_trunk_placer", () -> new TrunkPlacerType<>(MegaFancyTrunkPlacer.CODEC));
}


