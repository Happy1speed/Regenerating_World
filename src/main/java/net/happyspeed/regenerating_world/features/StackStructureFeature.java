package net.happyspeed.regenerating_world.features;

import com.mojang.serialization.Codec;
import net.happyspeed.regenerating_world.RegeneratingWorld;
import net.happyspeed.regenerating_world.saved_data.SuperStructureSpawnData;
import net.happyspeed.regenerating_world.util.RotationHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.QuartPos;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeResolver;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.LevelChunkSection;
import net.minecraft.world.level.chunk.PalettedContainer;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;

import static net.happyspeed.regenerating_world.features.LegFarXFarZ.getRandomUnderhangPiece;

public class StackStructureFeature extends Feature<NoneFeatureConfiguration> {
    public StackStructureFeature(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        WorldGenLevel worldGenLevel = context.level();

        ServerLevel level = worldGenLevel.getLevel();

        BlockPos origin = context.origin();

        StructureTemplateManager manager = level.getStructureManager();

        // Load base structure
        StructureTemplate base = manager.getOrCreate(ResourceLocation.fromNamespaceAndPath(RegeneratingWorld.MODID, "leg_base/leg_base"));
        base.placeInWorld(level, origin, origin, new StructurePlaceSettings(), level.random, 16);


        BlockPos[] offsets = {
            new BlockPos(0, 16, 0),
            new BlockPos(0, 32, 0),
            new BlockPos(0, 48, 0),
            new BlockPos(0, 64, 0),
            new BlockPos(0, 80, 0)
        };

        // Place segment structures
        for (BlockPos offset : offsets) {
            StructureTemplate seg = manager.getOrCreate(ResourceLocation.fromNamespaceAndPath(RegeneratingWorld.MODID, "leg_segments/leg_seg_1"));
            BlockPos target = origin.offset(offset);
            seg.placeInWorld(level, target, target, new StructurePlaceSettings(), level.random, 16);
        }

        Rotation currentRot = Rotation.getRandom(level.getRandom());

        StructureTemplate mixUnderhang = getRandomUnderhangPiece(level.getRandom(), manager);
        mixUnderhang.placeInWorld(level, RotationHelper.getCorrectAlignmentForFlip(origin.offset(offsets[offsets.length - 1]), level, currentRot, 0), origin.offset(offsets[offsets.length - 1]), new StructurePlaceSettings()
                        .setMirror(Mirror.NONE)
                        .setRotation(currentRot)
                        .setIgnoreEntities(false)
                        .setFinalizeEntities(true)
                , level.random, 16);

        return true;
    }
}
