package net.happyspeed.regenerating_world.saved_data;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.saveddata.SavedData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class SuperStructureSpawnData extends SavedData {
    private final List<BoundingBox> boxes = new ArrayList<>();

    public static SuperStructureSpawnData get(ServerLevel level) {
        return level.getDataStorage().computeIfAbsent(
                new Factory<>(
                        SuperStructureSpawnData::new,
                        SuperStructureSpawnData::load,
                        null
                ),
                "superstructure_spawns"
        );
    }

    public SuperStructureSpawnData() {}

    public void addBox(BoundingBox box) {
        boxes.add(box);
        setDirty();
    }

    // --- SAVE ---
    @Override
    public CompoundTag save(CompoundTag tag, HolderLookup.Provider provider) {
        ListTag list = new ListTag();
        for (BoundingBox box : boxes) {
            CompoundTag bbTag = new CompoundTag();
            bbTag.putInt("minX", box.minX());
            bbTag.putInt("minY", box.minY());
            bbTag.putInt("minZ", box.minZ());
            bbTag.putInt("maxX", box.maxX());
            bbTag.putInt("maxY", box.maxY());
            bbTag.putInt("maxZ", box.maxZ());
            list.add(bbTag);
        }
        tag.put("Boxes", list);
        return tag;
    }

    // --- LOAD ---
    public static SuperStructureSpawnData load(CompoundTag tag, HolderLookup.Provider provider) {
        SuperStructureSpawnData data = new SuperStructureSpawnData();
        ListTag list = tag.getList("Boxes", Tag.TAG_COMPOUND);
        for (Tag t : list) {
            CompoundTag bbTag = (CompoundTag) t;
            data.boxes.add(new BoundingBox(
                    bbTag.getInt("minX"), bbTag.getInt("minY"), bbTag.getInt("minZ"),
                    bbTag.getInt("maxX"), bbTag.getInt("maxY"), bbTag.getInt("maxZ")
            ));
        }
        return data;
    }

    public List<BoundingBox> getBoxes() {
        return Collections.unmodifiableList(boxes);
    }

}

