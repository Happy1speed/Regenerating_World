package net.happyspeed.regenerating_world.util;

import net.happyspeed.regenerating_world.RegeneratingWorld;
import net.happyspeed.regenerating_world.mod_items.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;

import java.util.List;

public class ArchivePortalShapeDetector {
    public static boolean isValidFrame(Level level, BlockPos pos) {
        if (level.getBlockState(new BlockPos(pos.getX(), pos.getY(), pos.getZ())).is(Blocks.CHISELED_STONE_BRICKS)) {
            if (level.getBlockState(new BlockPos(pos.getX() + 1, pos.getY(), pos.getZ())).is(Blocks.GOLD_BLOCK)) {
                if (level.getBlockState(new BlockPos(pos.getX() - 1, pos.getY(), pos.getZ())).is(Blocks.GOLD_BLOCK)) {
                    if (level.getBlockState(new BlockPos(pos.getX(), pos.getY(), pos.getZ() + 1)).is(Blocks.GOLD_BLOCK)) {
                        if (level.getBlockState(new BlockPos(pos.getX(), pos.getY(), pos.getZ() - 1)).is(Blocks.GOLD_BLOCK)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public static void spawnPortal(Level level, BlockPos pos) {
        for (BlockPos portalPos : getPortalArea(pos)) {
            level.setBlock(portalPos, ModItems.ARCHIVE_PORTAL_BLOCK.get().defaultBlockState(), 3);
        }
    }

    private static List<BlockPos> getPortalArea(BlockPos origin) {
        // Return list of portal block positions
        return List.of(origin); // Stub
    }
}
