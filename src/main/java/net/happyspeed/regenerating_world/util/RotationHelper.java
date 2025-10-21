package net.happyspeed.regenerating_world.util;


import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;

public class RotationHelper {

    public static BlockPos getCorrectAlignmentForFlip(BlockPos blockPos, Level level, Rotation rotation, int offset) {
        BlockPos adjustedPos = blockPos;

        if (rotation == Rotation.CLOCKWISE_90) {
            adjustedPos = blockPos.offset(15 + offset, 0, 0);
        }
        else if (rotation == Rotation.CLOCKWISE_180) {
            adjustedPos = blockPos.offset(15 + offset, 0, 15 + offset);
        }
        else if (rotation == Rotation.COUNTERCLOCKWISE_90) {
            adjustedPos = blockPos.offset(0, 0, 15 + offset);
        }
        return adjustedPos;
    }
}
