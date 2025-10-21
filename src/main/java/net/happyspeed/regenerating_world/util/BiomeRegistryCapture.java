package net.happyspeed.regenerating_world.util;

import net.happyspeed.regenerating_world.RegeneratingWorld;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.server.ServerStartedEvent;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@EventBusSubscriber(modid = RegeneratingWorld.MODID)
public class BiomeRegistryCapture {

    public static final Set<ResourceKey<Biome>> DISALLOWED_BIOMES = Set.of(
            ResourceKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath("minecraft", "the_void")),
            ResourceKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath("ae2", "spatial_storage"))
    );

    public static boolean isAllowed(ResourceKey<Biome> key) {
        return !DISALLOWED_BIOMES.contains(key);
    }

    public static List<ResourceKey<Biome>> orderedBiomes;

    @SubscribeEvent
    public static void onServerStarted(ServerStartedEvent event) {
        MinecraftServer server = event.getServer();
        Registry<Biome> biomeRegistry = server.registryAccess().registryOrThrow(Registries.BIOME);

        orderedBiomes = biomeRegistry.registryKeySet().stream().filter(BiomeRegistryCapture::isAllowed)
            .sorted((a, b) -> a.location().toString().compareTo(b.location().toString()))
            .collect(Collectors.toList());

        //System.out.println("Biome list initialized with " + orderedBiomes.size() + " entries.");
//        for (ResourceKey<Biome> key : orderedBiomes) {
//            System.out.println(key.location());
//        }

    }
}
