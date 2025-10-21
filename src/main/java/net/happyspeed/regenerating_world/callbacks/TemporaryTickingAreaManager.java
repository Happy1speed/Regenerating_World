package net.happyspeed.regenerating_world.callbacks;

import net.happyspeed.regenerating_world.RegeneratingWorld;
import net.minecraft.core.BlockPos;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.server.ServerStartedEvent;
import net.neoforged.neoforge.event.tick.ServerTickEvent;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@EventBusSubscriber(modid = RegeneratingWorld.MODID)
public class TemporaryTickingAreaManager {

    // Track forced chunks and their remaining lifetime
    private static final Map<ChunkKey, Integer> tickingAreas = new HashMap<>();

    /**
     * Force‑loads a square of chunks around a center for a given duration.
     *
     * @param level   The world
     * @param center  Center position
     * @param radius  Radius in chunks (1 = 3x3 area)
     * @param ticks   Duration in ticks (20 = 1 second, 20*60*5 = 5 minutes)
     */
    public static void loadArea(ServerLevel level, BlockPos center, int radius, int ticks) {
        int centerX = center.getX() >> 4;
        int centerZ = center.getZ() >> 4;

        for (int dx = -radius; dx <= radius; dx++) {
            for (int dz = -radius; dz <= radius; dz++) {
                int cx = centerX + dx;
                int cz = centerZ + dz;

                level.setChunkForced(cx, cz, true);
                tickingAreas.put(new ChunkKey(level, cx, cz), ticks);
            }
        }
    }

    @SubscribeEvent
    public static void onServerTick(ServerTickEvent.Post event) {




        Iterator<Map.Entry<ChunkKey, Integer>> it = tickingAreas.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<ChunkKey, Integer> entry = it.next();
            int remaining = entry.getValue() - 1;

            if (remaining <= 0) {
                entry.getKey().level.setChunkForced(entry.getKey().x, entry.getKey().z, false);
                it.remove();
            } else {
                entry.setValue(remaining);
            }
        }
    }

    // Helper record to track which world + chunk
    private record ChunkKey(ServerLevel level, int x, int z) {}

    @SubscribeEvent
    public static void onServerStarted(ServerStartedEvent event) {
        MinecraftServer server = event.getServer();
        for (ServerLevel level : server.getAllLevels()) {
            for (long key : level.getForcedChunks()) {
                ChunkPos pos = new ChunkPos(key);
                level.setChunkForced(pos.x, pos.z, false);
            }
        }
    }


}
