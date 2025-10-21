package net.happyspeed.regenerating_world.util;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.structure.BoundingBox;

public class BoundingBoxUtils {

    public static BlockPos getRandomPosInBox(BoundingBox box, RandomSource random) {
        int x = random.nextInt(box.maxX() - box.minX() + 1) + box.minX();
        int y = random.nextInt(box.maxY() - box.minY() + 1) + box.minY();
        int z = random.nextInt(box.maxZ() - box.minZ() + 1) + box.minZ();
        return new BlockPos(x, y, z);
    }

}
