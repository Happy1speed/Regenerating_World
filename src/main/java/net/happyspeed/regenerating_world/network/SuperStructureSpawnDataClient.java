package net.happyspeed.regenerating_world.network;

import net.minecraft.world.level.levelgen.structure.BoundingBox;

import java.util.ArrayList;
import java.util.List;

public class SuperStructureSpawnDataClient {
    private static final SuperStructureSpawnDataClient INSTANCE = new SuperStructureSpawnDataClient();
    private List<BoundingBox> boxes = new ArrayList<>();

    public static SuperStructureSpawnDataClient get() {
        return INSTANCE;
    }

    public void setBoxes(List<BoundingBox> newBoxes) {
        this.boxes = newBoxes;
    }

    public List<BoundingBox> getBoxes() {
        return boxes;
    }
}
