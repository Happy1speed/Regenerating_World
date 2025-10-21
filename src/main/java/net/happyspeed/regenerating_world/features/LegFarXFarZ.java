package net.happyspeed.regenerating_world.features;

import com.mojang.serialization.Codec;
import net.happyspeed.regenerating_world.RegeneratingWorld;
import net.happyspeed.regenerating_world.saved_data.SuperStructureSpawnData;
import net.happyspeed.regenerating_world.util.RotationHelper;
import net.happyspeed.regenerating_world.util.StructureCache;
import net.happyspeed.regenerating_world.util.StructurePlacerUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;

import java.util.*;

public class LegFarXFarZ extends Feature<NoneFeatureConfiguration> {
    public LegFarXFarZ(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }


    //Advancement: Were those the days?

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        if (context.level().getBlockState(context.origin()).is(Blocks.IRON_BLOCK)) {

            return false;
        }
        WorldGenLevel worldGenLevel = context.level();

        ServerLevel level = worldGenLevel.getLevel();

        BlockPos origin = context.origin();

        StructureTemplateManager manager = level.getStructureManager();

        // Load base structure


        //Underhang main!!!!!!
        for (int x = origin.getX(); x >= origin.getX() - 112; x -= 16) {
            for (int z = origin.getZ(); z >= origin.getZ() - 112; z -= 16) {
                Rotation currentRot = Rotation.getRandom(level.getRandom());
                StructureTemplate seg = getRandomUnderhangPiece(level.getRandom(), manager);

                BlockPos curBlockPos = new BlockPos(x,128,z);


                int chunkX = ((origin.getX() + 8) >> 4) << 4;
                int chunkZ = ((origin.getZ() + 8) >> 4) << 4;
                level.getChunk(chunkX, chunkZ);

                seg.placeInWorld(level, RotationHelper.getCorrectAlignmentForFlip(curBlockPos, level, currentRot, 0), curBlockPos, new StructurePlaceSettings()
                                .setMirror(Mirror.NONE)
                                .setRotation(currentRot)
                                .setIgnoreEntities(false)
                                .setFinalizeEntities(true)
                        , level.random, 16);


                SuperStructureSpawnData.get(level).addBox(BoundingBox.fromCorners(
                        new BlockPos(curBlockPos.getX(), curBlockPos.getY() - 64, curBlockPos.getZ()),
                        new BlockPos(curBlockPos.getX() + 16, curBlockPos.getY() + 8, curBlockPos.getZ() + 16))
                );
            }
        }



        StructureTemplate south_east_corner = manager.getOrCreate(ResourceLocation.fromNamespaceAndPath(RegeneratingWorld.MODID, "iter_corners/body_corner_south_east"));
        StructureTemplate south_west_corner = manager.getOrCreate(ResourceLocation.fromNamespaceAndPath(RegeneratingWorld.MODID, "iter_corners/body_corner_south_west"));
        StructureTemplate north_east_corner = manager.getOrCreate(ResourceLocation.fromNamespaceAndPath(RegeneratingWorld.MODID, "iter_corners/body_corner_north_east"));
        StructureTemplate north_west_corner = manager.getOrCreate(ResourceLocation.fromNamespaceAndPath(RegeneratingWorld.MODID, "iter_corners/body_corner_north_west"));

        StructureTemplate south_east_lid = manager.getOrCreate(ResourceLocation.fromNamespaceAndPath(RegeneratingWorld.MODID, "iter_corners/body_lid_south_east"));
        StructureTemplate south_west_lid = manager.getOrCreate(ResourceLocation.fromNamespaceAndPath(RegeneratingWorld.MODID, "iter_corners/body_lid_south_west"));
        StructureTemplate north_east_lid = manager.getOrCreate(ResourceLocation.fromNamespaceAndPath(RegeneratingWorld.MODID, "iter_corners/body_lid_north_east"));
        StructureTemplate north_west_lid = manager.getOrCreate(ResourceLocation.fromNamespaceAndPath(RegeneratingWorld.MODID, "iter_corners/body_lid_north_west"));

        StructureTemplate underhang_south_east_corner = manager.getOrCreate(ResourceLocation.fromNamespaceAndPath(RegeneratingWorld.MODID, "underhang/underhang_corner_south_east"));
        StructureTemplate underhang_south_west_corner = manager.getOrCreate(ResourceLocation.fromNamespaceAndPath(RegeneratingWorld.MODID, "underhang/underhang_corner_south_west"));
        StructureTemplate underhang_north_east_corner = manager.getOrCreate(ResourceLocation.fromNamespaceAndPath(RegeneratingWorld.MODID, "underhang/underhang_corner_north_east"));
        StructureTemplate underhang_north_west_corner = manager.getOrCreate(ResourceLocation.fromNamespaceAndPath(RegeneratingWorld.MODID, "underhang/underhang_corner_north_west"));

        Rotation currentRot;


        StructureTemplate dust_layer = manager.getOrCreate(ResourceLocation.fromNamespaceAndPath(RegeneratingWorld.MODID, "city_top/dust_layer"));

        //CORNERS

        //originX + 16
        //originZ + 16
        //originX - 128
        //originZ - 128

        BlockPos underCornerCurBlockPos;

        //Positive Extreme
        for (int y = 144; y <= 192; y += 16) {
            BlockPos curBlockPos = new BlockPos((origin.getX() + 16), y, origin.getZ() + 16);
            int chunkX = (((origin.getX() + 16) + 8) >> 4) << 4;
            int chunkZ = (((origin.getZ() + 16) + 8) >> 4) << 4;
            level.getChunk(chunkX, chunkZ);
            south_east_corner.placeInWorld(level, curBlockPos,curBlockPos, new StructurePlaceSettings()
                            .setMirror(Mirror.NONE)
                            .setRotation(Rotation.NONE)
                            .setIgnoreEntities(false)
                            .setFinalizeEntities(true)
                    , level.random, 16);


            BlockPos curBlockPosLid = new BlockPos(curBlockPos.getX(), y + 16, curBlockPos.getZ());
            south_east_lid.placeInWorld(level, curBlockPosLid,curBlockPosLid, new StructurePlaceSettings()
                            .setMirror(Mirror.NONE)
                            .setRotation(Rotation.NONE)
                            .setIgnoreEntities(false)
                            .setFinalizeEntities(true)
                    , level.random, 16);
            StructurePlacerUtils.placeConfiguredFeature(level, ResourceLocation.fromNamespaceAndPath(RegeneratingWorld.MODID, "dust_layer"), new BlockPos(curBlockPosLid.getX(),  curBlockPosLid.getY() + 1, curBlockPosLid.getZ()));

        }
        underCornerCurBlockPos = new BlockPos((origin.getX() + 16), 128, origin.getZ() + 16);
        underhang_south_east_corner.placeInWorld(level, underCornerCurBlockPos, underCornerCurBlockPos, new StructurePlaceSettings()
                        .setMirror(Mirror.NONE)
                        .setRotation(Rotation.NONE)
                        .setIgnoreEntities(false)
                        .setFinalizeEntities(true)
                , level.random, 16);


        //Negative Extreme
        for (int y = 144; y <= 192; y += 16) {
            BlockPos curBlockPos = new BlockPos((origin.getX() - 128), y, origin.getZ() - 128);
            int chunkX = (((origin.getX() + 16) + 8) >> 4) << 4;
            int chunkZ = (((origin.getZ() + 16) + 8) >> 4) << 4;
            level.getChunk(chunkX, chunkZ);
            north_west_corner.placeInWorld(level, curBlockPos,curBlockPos, new StructurePlaceSettings()
                            .setMirror(Mirror.NONE)
                            .setRotation(Rotation.NONE)
                            .setIgnoreEntities(false)
                            .setFinalizeEntities(true)
                    , level.random, 16);

            BlockPos curBlockPosLid = new BlockPos(curBlockPos.getX(), y + 16, curBlockPos.getZ());
            north_west_lid.placeInWorld(level, curBlockPosLid,curBlockPosLid, new StructurePlaceSettings()
                            .setMirror(Mirror.NONE)
                            .setRotation(Rotation.NONE)
                            .setIgnoreEntities(false)
                            .setFinalizeEntities(true)
                    , level.random, 16);

            StructurePlacerUtils.placeConfiguredFeature(level, ResourceLocation.fromNamespaceAndPath(RegeneratingWorld.MODID, "dust_layer"), new BlockPos(curBlockPosLid.getX(),  curBlockPosLid.getY() + 1, curBlockPosLid.getZ()));
        }
        underCornerCurBlockPos = new BlockPos((origin.getX() - 128), 128, origin.getZ() - 128);
        underhang_north_west_corner.placeInWorld(level, underCornerCurBlockPos,underCornerCurBlockPos, new StructurePlaceSettings()
                        .setMirror(Mirror.NONE)
                        .setRotation(Rotation.NONE)
                        .setIgnoreEntities(false)
                        .setFinalizeEntities(true)
                , level.random, 16);

        //Negative Lead
        for (int y = 144; y <= 192; y += 16) {
            BlockPos curBlockPos = new BlockPos((origin.getX() - 128), y, origin.getZ() + 16);
            int chunkX = (((origin.getX() + 16) + 8) >> 4) << 4;
            int chunkZ = (((origin.getZ() + 16) + 8) >> 4) << 4;
            level.getChunk(chunkX, chunkZ);
            south_west_corner.placeInWorld(level, curBlockPos,curBlockPos, new StructurePlaceSettings()
                            .setMirror(Mirror.NONE)
                            .setRotation(Rotation.NONE)
                            .setIgnoreEntities(false)
                            .setFinalizeEntities(true)
                    , level.random, 16);

            BlockPos curBlockPosLid = new BlockPos(curBlockPos.getX(), y + 16, curBlockPos.getZ());
            south_west_lid.placeInWorld(level, curBlockPosLid,curBlockPosLid, new StructurePlaceSettings()
                            .setMirror(Mirror.NONE)
                            .setRotation(Rotation.NONE)
                            .setIgnoreEntities(false)
                            .setFinalizeEntities(true)
                    , level.random, 16);

            StructurePlacerUtils.placeConfiguredFeature(level, ResourceLocation.fromNamespaceAndPath(RegeneratingWorld.MODID, "dust_layer"), new BlockPos(curBlockPosLid.getX(),  curBlockPosLid.getY() + 1, curBlockPosLid.getZ()));
        }

        underCornerCurBlockPos = new BlockPos((origin.getX() - 128), 128, origin.getZ() + 16);

        underhang_south_west_corner.placeInWorld(level, underCornerCurBlockPos,underCornerCurBlockPos, new StructurePlaceSettings()
                        .setMirror(Mirror.NONE)
                        .setRotation(Rotation.NONE)
                        .setIgnoreEntities(false)
                        .setFinalizeEntities(true)
                , level.random, 16);


        //Positive Lead
        for (int y = 144; y <= 192; y += 16) {
            BlockPos curBlockPos = new BlockPos((origin.getX() + 16), y, origin.getZ() - 128);
            int chunkX = (((origin.getX() + 16) + 8) >> 4) << 4;
            int chunkZ = (((origin.getZ() + 16) + 8) >> 4) << 4;
            level.getChunk(chunkX, chunkZ);
            north_east_corner.placeInWorld(level, curBlockPos,curBlockPos, new StructurePlaceSettings()
                            .setMirror(Mirror.NONE)
                            .setRotation(Rotation.NONE)
                            .setIgnoreEntities(false)
                            .setFinalizeEntities(true)
                    , level.random, 16);

            BlockPos curBlockPosLid = new BlockPos(curBlockPos.getX(), y + 16, curBlockPos.getZ());
            north_east_lid.placeInWorld(level, curBlockPosLid,curBlockPosLid, new StructurePlaceSettings()
                            .setMirror(Mirror.NONE)
                            .setRotation(Rotation.NONE)
                            .setIgnoreEntities(false)
                            .setFinalizeEntities(true)
                    , level.random, 16);

            StructurePlacerUtils.placeConfiguredFeature(level, ResourceLocation.fromNamespaceAndPath(RegeneratingWorld.MODID, "dust_layer"), new BlockPos(curBlockPosLid.getX(),  curBlockPosLid.getY() + 1, curBlockPosLid.getZ()));
        }

        underCornerCurBlockPos = new BlockPos((origin.getX() + 16), 128, origin.getZ() - 128);

        underhang_north_east_corner.placeInWorld(level, underCornerCurBlockPos,underCornerCurBlockPos, new StructurePlaceSettings()
                        .setMirror(Mirror.NONE)
                        .setRotation(Rotation.NONE)
                        .setIgnoreEntities(false)
                        .setFinalizeEntities(true)
                , level.random, 16);




        //FACES

        //South Face

        //Lid
        StructureTemplate body_lid = manager.getOrCreate(ResourceLocation.fromNamespaceAndPath(RegeneratingWorld.MODID, "iter_main/body_lid"));

        currentRot = Rotation.CLOCKWISE_180;
        for (int x = origin.getX(); x >= origin.getX() - 112; x -= 16) {

            Rotation currentUnderhangRot = Rotation.getRandom(level.getRandom());
            StructureTemplate seg = getRandomUnderhangPiece(level.getRandom(), manager);

            BlockPos curUnderhangBlockPos = new BlockPos(x,128, origin.getZ() + 16);

            seg.placeInWorld(level, RotationHelper.getCorrectAlignmentForFlip(curUnderhangBlockPos, level, currentUnderhangRot, 0), curUnderhangBlockPos, new StructurePlaceSettings()
                            .setMirror(Mirror.NONE)
                            .setRotation(currentUnderhangRot)
                            .setIgnoreEntities(false)
                            .setFinalizeEntities(true)
                    , level.random, 16);

            for (int y = 144; y <= 192; y += 16) {
                BlockPos curBlockPos = new BlockPos(x, y, origin.getZ() + 16);
                int chunkX = ((x + 8) >> 4) << 4;
                int chunkZ = (((origin.getZ() + 16) + 8) >> 4) << 4;
                level.getChunk(chunkX, chunkZ);
                getRandomOuterShellPiece(level.getRandom(), manager).placeInWorld(level, RotationHelper.getCorrectAlignmentForFlip(curBlockPos,level, currentRot, 0),curBlockPos, new StructurePlaceSettings()
                                .setMirror(Mirror.NONE)
                                .setRotation(Rotation.CLOCKWISE_180)
                                .setIgnoreEntities(false)
                                .setFinalizeEntities(true)
                        , level.random, 16);


            }
            body_lid.placeInWorld(level, new BlockPos(x, 208, origin.getZ() + 16), BlockPos.ZERO, new StructurePlaceSettings(), level.random, 16);
            dust_layer.placeInWorld(
                    level, new BlockPos(x, 209, origin.getZ() + 16), new BlockPos(x, 209, origin.getZ() + 16),
                    new StructurePlaceSettings()
                            .setMirror(Mirror.NONE)
                            .setRotation(Rotation.NONE)
                            .setIgnoreEntities(false)
                            .setFinalizeEntities(true)
                    , level.random, 16);
        }

        //East Face
        currentRot = Rotation.CLOCKWISE_90;
        for (int z = origin.getZ(); z >= origin.getX() - 112; z -= 16) {

            Rotation currentUnderhangRot = Rotation.getRandom(level.getRandom());
            StructureTemplate seg = getRandomUnderhangPiece(level.getRandom(), manager);

            BlockPos curUnderhangBlockPos = new BlockPos(origin.getX() + 16,128, z);

            seg.placeInWorld(level, RotationHelper.getCorrectAlignmentForFlip(curUnderhangBlockPos, level, currentUnderhangRot, 0), curUnderhangBlockPos, new StructurePlaceSettings()
                            .setMirror(Mirror.NONE)
                            .setRotation(currentUnderhangRot)
                            .setIgnoreEntities(false)
                            .setFinalizeEntities(true)
                    , level.random, 16);

            for (int y = 144; y <= 192; y += 16) {
                BlockPos curBlockPos = new BlockPos(origin.getX() + 16, y, z);
                int chunkX = (((origin.getX() + 16) + 8) >> 4) << 4;
                int chunkZ = ((z + 8) >> 4) << 4;
                level.getChunk(chunkX, chunkZ);
                getRandomOuterShellPiece(level.getRandom(), manager).placeInWorld(level, RotationHelper.getCorrectAlignmentForFlip(curBlockPos,level, currentRot, 0),curBlockPos, new StructurePlaceSettings()
                                .setMirror(Mirror.NONE)
                                .setRotation(Rotation.CLOCKWISE_90)
                                .setIgnoreEntities(false)
                                .setFinalizeEntities(true)
                        , level.random, 16);
            }
            body_lid.placeInWorld(level, new BlockPos(origin.getX() + 16, 208, z), BlockPos.ZERO, new StructurePlaceSettings(), level.random, 16);
            dust_layer.placeInWorld(
                    level, new BlockPos(origin.getX() + 16, 209, z), new BlockPos(origin.getX() + 16, 209, z),
                    new StructurePlaceSettings()
                            .setMirror(Mirror.NONE)
                            .setRotation(Rotation.NONE)
                            .setIgnoreEntities(false)
                            .setFinalizeEntities(true)
                    , level.random, 16);
        }

        //North Face
        currentRot = Rotation.NONE;
        for (int x = origin.getX(); x >= origin.getX() - 112; x -= 16) {

            Rotation currentUnderhangRot = Rotation.getRandom(level.getRandom());
            StructureTemplate seg = getRandomUnderhangPiece(level.getRandom(), manager);

            BlockPos curUnderhangBlockPos = new BlockPos(x,128, origin.getZ() - 128);

            seg.placeInWorld(level, RotationHelper.getCorrectAlignmentForFlip(curUnderhangBlockPos, level, currentUnderhangRot, 0), curUnderhangBlockPos, new StructurePlaceSettings()
                            .setMirror(Mirror.NONE)
                            .setRotation(currentUnderhangRot)
                            .setIgnoreEntities(false)
                            .setFinalizeEntities(true)
                    , level.random, 16);

            for (int y = 144; y <= 192; y += 16) {
                BlockPos curBlockPos = new BlockPos(x, y, origin.getZ() - 128);
                int chunkX = ((x + 8) >> 4) << 4;
                int chunkZ = (((origin.getZ() - 128) + 8) >> 4) << 4;
                level.getChunk(chunkX, chunkZ);
                getRandomOuterShellPiece(level.getRandom(), manager).placeInWorld(level, RotationHelper.getCorrectAlignmentForFlip(curBlockPos,level, currentRot, 0),curBlockPos, new StructurePlaceSettings()
                                .setMirror(Mirror.NONE)
                                .setRotation(Rotation.NONE)
                                .setIgnoreEntities(false)
                                .setFinalizeEntities(true)
                        , level.random, 16);
            }
            body_lid.placeInWorld(level, new BlockPos(x, 208, origin.getZ() - 128), BlockPos.ZERO, new StructurePlaceSettings(), level.random, 16);
            dust_layer.placeInWorld(
                    level, new BlockPos(x, 209, origin.getZ() - 128), new BlockPos(x, 209, origin.getZ() - 128),
                    new StructurePlaceSettings()
                            .setMirror(Mirror.NONE)
                            .setRotation(Rotation.NONE)
                            .setIgnoreEntities(false)
                            .setFinalizeEntities(true)
                    , level.random, 16);
        }

        //West Face
        currentRot = Rotation.COUNTERCLOCKWISE_90;
        for (int z = origin.getZ(); z >= origin.getX() - 112; z -= 16) {

            Rotation currentUnderhangRot = Rotation.getRandom(level.getRandom());
            StructureTemplate seg = getRandomUnderhangPiece(level.getRandom(), manager);

            BlockPos curUnderhangBlockPos = new BlockPos(origin.getX() - 128,128, z);

            seg.placeInWorld(level, RotationHelper.getCorrectAlignmentForFlip(curUnderhangBlockPos, level, currentUnderhangRot, 0), curUnderhangBlockPos, new StructurePlaceSettings()
                            .setMirror(Mirror.NONE)
                            .setRotation(currentUnderhangRot)
                            .setIgnoreEntities(false)
                            .setFinalizeEntities(true)
                    , level.random, 16);

            for (int y = 144; y <= 192; y += 16) {
                BlockPos curBlockPos = new BlockPos(origin.getX() - 128, y, z);
                int chunkX = (((origin.getX() - 128) + 8) >> 4) << 4;
                int chunkZ = ((z + 8) >> 4) << 4;
                level.getChunk(chunkX, chunkZ);
                getRandomOuterShellPiece(level.getRandom(), manager).placeInWorld(level, RotationHelper.getCorrectAlignmentForFlip(curBlockPos,level, currentRot, 0),curBlockPos, new StructurePlaceSettings()
                                .setMirror(Mirror.NONE)
                                .setRotation(Rotation.COUNTERCLOCKWISE_90)
                                .setIgnoreEntities(false)
                                .setFinalizeEntities(true)
                        , level.random, 16);
            }
            body_lid.placeInWorld(level, new BlockPos(origin.getX() - 128, 208, z), BlockPos.ZERO, new StructurePlaceSettings(), level.random, 16);
            dust_layer.placeInWorld(
                    level, new BlockPos(origin.getX() - 128, 209, z), new BlockPos(origin.getX() - 128, 209, z),
                    new StructurePlaceSettings()
                            .setMirror(Mirror.NONE)
                            .setRotation(Rotation.NONE)
                            .setIgnoreEntities(false)
                            .setFinalizeEntities(true)
                    , level.random, 16);
        }

        ArrayList<BlockPos> placeLocations = new ArrayList<>();


        for (int x = origin.getX(); x >= origin.getX() - 112; x -= 16) {
            for (int z = origin.getZ(); z >= origin.getZ() - 112; z -= 16) {
                BlockPos curBlockPos = new BlockPos(x,209,z);
                placeLocations.add(curBlockPos);
            }
        }

        Collections.shuffle(placeLocations, new Random());
        Set<BlockPos> deduplicated = new LinkedHashSet<>(placeLocations);
        placeLocations = new ArrayList<>(deduplicated);




        //There is no list length test, just keep this under 64
        int buildingAmount = 32 + level.random.nextInt(6);


        //Solid Dust Layer (Inner)
        for (int x = origin.getX(); x >= origin.getX() - 112; x -= 16) {
            for (int z = origin.getZ(); z >= origin.getZ() - 112; z -= 16) {
                int chunkXd = ((origin.getX() + 8) >> 4) << 4;
                int chunkZd = ((origin.getZ() + 8) >> 4) << 4;
                level.getChunk(chunkXd, chunkZd);
                BlockPos curBlockPos = new BlockPos(x,209,z);

                boolean placeDust = true;

                for (int i = 0; i < buildingAmount; i++) {
                    if (curBlockPos == placeLocations.get(i)) {
                        placeDust = false;
                        break;
                    }
                }

                if (placeDust) {
                    dust_layer.placeInWorld(
                            level, curBlockPos, curBlockPos,
                            new StructurePlaceSettings()
                                    .setMirror(Mirror.NONE)
                                    .setRotation(Rotation.NONE)
                                    .setIgnoreEntities(false)
                                    .setFinalizeEntities(true)
                            , level.random, 16);
                }

                body_lid.placeInWorld(level, new BlockPos(x, 208, z), BlockPos.ZERO, new StructurePlaceSettings(), level.random, 16);

            }
        }

        //Molding Dust Layer (Outer)
//        for (int x = origin.getX() + 16; x >= origin.getX() - 128; x -= 16) {
//            for (int z = origin.getZ() + 16; z >= origin.getZ() - 128; z -= 16) {
//                if (x == origin.getX() || x == origin.getX() - 112 || z == origin.getZ() || z == origin.getZ() - 112) {
//
//                    int chunkXd = ((origin.getX() + 8) >> 4) << 4;
//                    int chunkZd = ((origin.getZ() + 8) >> 4) << 4;
//                    level.getChunk(chunkXd, chunkZd);
//                    BlockPos curBlockPos = new BlockPos(x, 208, z);
//
//                    StructurePlacerUtils.placeConfiguredFeature(level, ResourceLocation.fromNamespaceAndPath(RegeneratingWorld.MODID, "dust_layer"), curBlockPos);
//                }
//            }
//        }





        for (int i = 0; i < buildingAmount; i++) {
            BlockPos placeTargetLocation = placeLocations.get(i);
            int chunkX = ((origin.getX() + 8) >> 4) << 4;
            int chunkZ = ((origin.getZ() + 8) >> 4) << 4;
            level.getChunk(chunkX, chunkZ);
            placeCityBuilding(context, placeTargetLocation);
        }


        StructureTemplate base = manager.getOrCreate(ResourceLocation.fromNamespaceAndPath(RegeneratingWorld.MODID, "leg_base/leg_base"));
        base.placeInWorld(level, origin, origin, new StructurePlaceSettings(), level.random, 16);


        BlockPos[] legOffsets = {
                new BlockPos(0, 16, 0),
                new BlockPos(0, 32, 0),
                new BlockPos(0, 48, 0),
                new BlockPos(0, 64, 0),
                new BlockPos(0, 80, 0)
        };

        // Place segment structures
        for (BlockPos offset : legOffsets) {
            StructureTemplate legSeg = manager.getOrCreate(ResourceLocation.fromNamespaceAndPath(RegeneratingWorld.MODID, "leg_segments/leg_seg_1"));
            BlockPos target = origin.offset(offset);
            legSeg.placeInWorld(level, target, target, new StructurePlaceSettings(), level.random, 16);
        }


        currentRot = Rotation.getRandom(level.getRandom());

        StructureTemplate mixUnderhang = getRandomUnderhangPiece(level.getRandom(), manager);
        mixUnderhang.placeInWorld(level, RotationHelper.getCorrectAlignmentForFlip(origin.offset(legOffsets[legOffsets.length - 1]), level, currentRot, 0), origin.offset(legOffsets[legOffsets.length - 1]), new StructurePlaceSettings()
                        .setMirror(Mirror.NONE)
                        .setRotation(currentRot)
                        .setIgnoreEntities(false)
                        .setFinalizeEntities(true)
                , level.random, 16);
        return true;
    }


    public void placeCityBuilding(FeaturePlaceContext<NoneFeatureConfiguration> context, BlockPos origin) {
        WorldGenLevel worldGenLevel = context.level();

        ServerLevel level = worldGenLevel.getLevel();

        Rotation randomRot = Rotation.getRandom(level.random);



        if (level.random.nextInt(5) != 0) {
            //Place Plain
            StructureTemplate currentPlacer;
            if (level.random.nextInt(5) == 0) {
                currentPlacer = StructureCache.getSingle("only_base_city_plain_arch");
            }
            else {
                currentPlacer = StructureCache.getPool("base_city_plain_pool")[level.random.nextInt(StructureCache.getPool("base_city_plain_pool").length - 1)];
            }
            currentPlacer.placeInWorld(
            level, RotationHelper.getCorrectAlignmentForFlip(origin,level, randomRot, level.random.nextInt(-1, 1)), origin,
            new StructurePlaceSettings()
                    .setMirror(Mirror.NONE)
                    .setRotation(randomRot)
                    .setIgnoreEntities(false)
                    .setFinalizeEntities(true)
            , level.random, 16);
        }
        else {
            //Place Painted

            StructureTemplate currentPlacer = StructureCache.getPool("base_city_red_pool")[level.random.nextInt(StructureCache.getPool("base_city_red_pool").length - 1)];

            currentPlacer.placeInWorld(
                    level, RotationHelper.getCorrectAlignmentForFlip(origin,level, randomRot, level.random.nextInt(-1, 1)), origin,
                    new StructurePlaceSettings()
                            .setMirror(Mirror.NONE)
                            .setRotation(randomRot)
                            .setIgnoreEntities(false)
                            .setFinalizeEntities(true)
                    , level.random, 16);
        }

        BlockPos[] offsets = {
                new BlockPos(0, 16, 0),
                new BlockPos(0, 32, 0),
                new BlockPos(0, 48, 0),
                new BlockPos(0, 64, 0),
                new BlockPos(0, 80, 0)
        };

        BlockPos trackedOffset = origin;

        int timesPlaced = 0;
        // Place segment structures
        for (BlockPos offset : offsets) {
            BlockPos target = origin.offset(offset);
            randomRot = Rotation.getRandom(level.random);
            if (level.random.nextInt(5) != 0) {
                //Place Plain
                StructureTemplate currentPlacer = StructureCache.getPool("base_city_plain_pool")[level.random.nextInt(StructureCache.getPool("base_city_plain_pool").length - 1)];

                currentPlacer.placeInWorld(
                        level, RotationHelper.getCorrectAlignmentForFlip(target,level, randomRot, level.random.nextInt(-1, 1)), target,
                        new StructurePlaceSettings()
                                .setMirror(Mirror.NONE)
                                .setRotation(randomRot)
                                .setIgnoreEntities(false)
                                .setFinalizeEntities(true)
                        , level.random, 16);
            }
            else {
                //Place Painted

                StructureTemplate currentPlacer = StructureCache.getPool("base_city_red_pool")[level.random.nextInt(StructureCache.getPool("base_city_red_pool").length - 1)];

                currentPlacer.placeInWorld(
                        level, RotationHelper.getCorrectAlignmentForFlip(target,level, randomRot, level.random.nextInt(-1, 1)), target,
                        new StructurePlaceSettings()
                                .setMirror(Mirror.NONE)
                                .setRotation(randomRot)
                                .setIgnoreEntities(false)
                                .setFinalizeEntities(true)
                        , level.random, 16);
            }
            trackedOffset = target;
            timesPlaced++;
            if (timesPlaced > 1) {
                if (level.random.nextInt(6 - timesPlaced) == 0) {
                    break;
                }
            }

        }

        //Skyscraper top
        randomRot = Rotation.getRandom(level.random);

        BlockPos topPos = new BlockPos(trackedOffset.getX(), trackedOffset.getY() + 16, trackedOffset.getZ());

        StructureTemplate currentPlacer = StructureCache.getPool("top_city_plain_pool")[level.random.nextInt(StructureCache.getPool("top_city_plain_pool").length - 1)];

        if (level.random.nextInt(5) != 0) {
            //chance for no topper
            currentPlacer.placeInWorld(
                    level, RotationHelper.getCorrectAlignmentForFlip(topPos, level, randomRot, 0), topPos,
                    new StructurePlaceSettings()
                            .setMirror(Mirror.NONE)
                            .setRotation(randomRot)
                            .setIgnoreEntities(false)
                            .setFinalizeEntities(true)
                    , level.random, 16);
        }
    }

    private static StructureTemplate getRandomOuterShellPiece(RandomSource source, StructureTemplateManager manager) {

        int randStruct = source.nextInt(6);

        if (randStruct == 0) {
            return manager.getOrCreate(ResourceLocation.fromNamespaceAndPath(RegeneratingWorld.MODID, "iter_main/body_outer_shell_1"));
        }
        else if (randStruct == 1) {
            return manager.getOrCreate(ResourceLocation.fromNamespaceAndPath(RegeneratingWorld.MODID, "iter_main/body_outer_shell_2"));
        }
        else if (randStruct == 2) {
            return manager.getOrCreate(ResourceLocation.fromNamespaceAndPath(RegeneratingWorld.MODID, "iter_main/body_outer_shell_3"));
        }
        else if (randStruct == 3) {
            return manager.getOrCreate(ResourceLocation.fromNamespaceAndPath(RegeneratingWorld.MODID, "iter_main/body_outer_shell_4"));
        }
        else if (randStruct == 4) {
            return manager.getOrCreate(ResourceLocation.fromNamespaceAndPath(RegeneratingWorld.MODID, "iter_main/body_outer_shell_5"));
        }
        else {
            return manager.getOrCreate(ResourceLocation.fromNamespaceAndPath(RegeneratingWorld.MODID, "iter_main/body_outer_shell_6"));
        }

    }


    public static StructureTemplate getRandomUnderhangPiece(RandomSource source, StructureTemplateManager manager) {

        int randStruct = source.nextInt(2);

        if (randStruct == 0) {
            return manager.getOrCreate(ResourceLocation.fromNamespaceAndPath(RegeneratingWorld.MODID, "underhang/underhang_solid_part_1"));
        }
        else {
            return manager.getOrCreate(ResourceLocation.fromNamespaceAndPath(RegeneratingWorld.MODID, "underhang/underhang_solid_part_2"));
        }
    }
}
