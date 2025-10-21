package net.happyspeed.regenerating_world.util;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

public class StructurePlacerUtils {
    public static boolean placeConfiguredFeature(ServerLevel level, ResourceLocation featureId, BlockPos pos) {
        // Look up the configured feature
        Holder<ConfiguredFeature<?, ?>> holder = level.registryAccess()
                .registryOrThrow(Registries.CONFIGURED_FEATURE)
                .getHolder(ResourceKey.create(Registries.CONFIGURED_FEATURE, featureId))
                .orElseThrow(() -> new IllegalStateException("ConfiguredFeature not found: " + featureId));

        ConfiguredFeature<?, ?> feature = holder.value();

        // Run placement at the exact position
        return feature.place(
                level,
                level.getChunkSource().getGenerator(),
                level.getRandom(),
                pos
        );
    }
}
