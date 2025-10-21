package net.happyspeed.regenerating_world.util;

import net.happyspeed.regenerating_world.RegeneratingWorld;
import net.happyspeed.regenerating_world.mod_items.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

import java.util.List;

public class ReturnPortalShapeDetector extends ArchivePortalShapeDetector {

    public static void spawnPortal(Level level, BlockPos pos) {
        for (BlockPos portalPos : getPortalArea(pos)) {
            level.setBlock(portalPos, ModItems.RETURN_PORTAL_BLOCK.get().defaultBlockState(), 3);
        }
    }

    private static List<BlockPos> getPortalArea(BlockPos origin) {
        // Return list of portal block positions
        return List.of(origin); // Stub
    }
}
