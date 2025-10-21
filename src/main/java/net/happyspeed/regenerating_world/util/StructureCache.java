package net.happyspeed.regenerating_world.util;

import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;

import java.util.*;

public class StructureCache {
    private static final Map<String, StructureTemplate[]> POOL_CACHE = new HashMap<>();
    private static final Map<String, StructureTemplate> SINGLE_CACHE = new HashMap<>();

    public static void registerPool(String id, StructureTemplate[] template) {
        POOL_CACHE.put(id, template);
    }

    public static StructureTemplate[] getPool(String id) {
        return POOL_CACHE.get(id);
    }

    public static void registerSingle(String id, StructureTemplate template) {
        SINGLE_CACHE.put(id, template);
    }

    public static StructureTemplate getSingle(String id) {
        return SINGLE_CACHE.get(id);
    }
}

