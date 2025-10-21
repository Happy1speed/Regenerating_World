package net.happyspeed.regenerating_world.util;

import net.happyspeed.regenerating_world.RegeneratingWorld;
import net.happyspeed.regenerating_world.mod_items.ModItems;
import net.minecraft.BlockUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.Vec3i;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.ai.village.poi.PoiManager;
import net.minecraft.world.entity.ai.village.poi.PoiRecord;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.ai.village.poi.PoiTypes;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.NetherPortalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.border.WorldBorder;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.portal.PortalForcer;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class ArchivePortalForcer extends PortalForcer {

    public ArchivePortalForcer(ServerLevel level) {
        super(level);
    }

//    @Override
//    public Optional<BlockPos> findClosestPortalPosition(BlockPos exitPos, boolean isNether, WorldBorder worldBorder) {
//        PoiManager poimanager = this.level.getPoiManager();
//        int i = isNether ? 16 : 128;
//        poimanager.ensureLoadedAndValid(this.level, exitPos, i);
//        Stream<BlockPos> var10000 = poimanager.getInSquare((p_230634_) -> {
//            return p_230634_.is(PoiTypes.NETHER_PORTAL);
//        }, exitPos, i, PoiManager.Occupancy.ANY).map(PoiRecord::getPos);
//        Objects.requireNonNull(worldBorder);
//        return var10000.filter(worldBorder::isWithinBounds).filter((p_352047_) -> {
//            return this.level.getBlockState(p_352047_).hasProperty(BlockStateProperties.HORIZONTAL_AXIS);
//        }).min(Comparator.comparingDouble((p_352046_) -> {
//            return p_352046_.distSqr(exitPos);
//        }).thenComparingInt(Vec3i::getY));
//    }


    @Override
    public Optional<BlockPos> findClosestPortalPosition(BlockPos exitPos, boolean isNether, WorldBorder worldBorder) {
        PoiManager poiManager = this.level.getPoiManager();
        int searchRadius = isNether ? 16 : 128;

        // Ensure POIs are loaded around the exit position
        poiManager.ensureLoadedAndValid(this.level, exitPos, searchRadius);

        // Reference your custom portal POI key
        ResourceKey<PoiType> CUSTOM_PORTAL_KEY = ResourceKey.create(
                Registries.POINT_OF_INTEREST_TYPE,
                ResourceLocation.fromNamespaceAndPath(RegeneratingWorld.MODID, "archive_portal")
        );

        // Filter POIs by your custom portal type
        Predicate<Holder<PoiType>> portalPredicate = holder -> holder.is(CUSTOM_PORTAL_KEY);

        List<PoiRecord> portalRecords = poiManager.getInSquare(
                portalPredicate,
                exitPos,
                searchRadius,
                PoiManager.Occupancy.ANY
        ).toList();

        BlockPos closestPortal = null;
        double closestDistance = Double.MAX_VALUE;
        int lowestY = Integer.MAX_VALUE;

        for (PoiRecord record : portalRecords) {
            BlockPos pos = record.getPos();

            if (!worldBorder.isWithinBounds(pos)) continue;

            BlockState state = this.level.getBlockState(pos);
            if (!state.hasProperty(BlockStateProperties.HORIZONTAL_AXIS)) continue;

            double distance = pos.distSqr(new Vec3i(exitPos.getX(), exitPos.getY(), exitPos.getZ()));
            int y = pos.getY();

            if (distance < closestDistance || (distance == closestDistance && y < lowestY)) {
                closestPortal = pos;
                closestDistance = distance;
                lowestY = y;
            }
        }

        return Optional.ofNullable(closestPortal);
    }




    public Optional<BlockUtil.FoundRectangle> createPortal(BlockPos pos, Direction.Axis axis) {
        Direction direction = Direction.get(Direction.AxisDirection.POSITIVE, axis);
        double d0 = -1.0;
        BlockPos blockpos = null;
        double d1 = -1.0;
        BlockPos blockpos1 = null;
        WorldBorder worldborder = this.level.getWorldBorder();
        int i = Math.min(this.level.getMaxBuildHeight(), this.level.getMinBuildHeight() + this.level.getLogicalHeight()) - 1;
        BlockPos.MutableBlockPos blockpos$mutableblockpos = pos.mutable();
        Iterator var14 = BlockPos.spiralAround(pos, 16, Direction.EAST, Direction.SOUTH).iterator();

        while(true) {
            BlockPos.MutableBlockPos blockpos$mutableblockpos1;
            int l2;
            int l;
            int j3;
            int j1;
            do {
                do {
                    if (!var14.hasNext()) {
                        if (d0 == -1.0 && d1 != -1.0) {
                            blockpos = blockpos1;
                            d0 = d1;
                        }

                        int l1;
                        int k2;
                        if (d0 == -1.0) {
                            l1 = Math.max(this.level.getMinBuildHeight() - -1, 70);
                            k2 = i - 9;
                            if (k2 < l1) {
                                return Optional.empty();
                            }

                            blockpos = (new BlockPos(pos.getX() - direction.getStepX() * 1, Mth.clamp(pos.getY(), l1, k2), pos.getZ() - direction.getStepZ() * 1)).immutable();
                            blockpos = worldborder.clampToBounds(blockpos);
                            Direction direction1 = direction.getClockWise();

                            for(l = -1; l < 2; ++l) {
                                for(j3 = 0; j3 < 2; ++j3) {
                                    for(j1 = -1; j1 < 3; ++j1) {
                                        BlockState blockstate1 = j1 < 0 ? Blocks.GOLD_BLOCK.defaultBlockState() : Blocks.AIR.defaultBlockState();
                                        blockpos$mutableblockpos.setWithOffset(blockpos, j3 * direction.getStepX() + l * direction1.getStepX(), j1, j3 * direction.getStepZ() + l * direction1.getStepZ());
                                        this.level.setBlockAndUpdate(blockpos$mutableblockpos, blockstate1);
                                    }
                                }
                            }
                        }

                        for(l1 = -1; l1 < 3; ++l1) {
                            for(k2 = -1; k2 < 4; ++k2) {
                                if (l1 == -1 || l1 == 2 || k2 == -1 || k2 == 3) {
                                    blockpos$mutableblockpos.setWithOffset(blockpos, l1 * direction.getStepX(), k2, l1 * direction.getStepZ());
                                    this.level.setBlock(blockpos$mutableblockpos, Blocks.GOLD_BLOCK.defaultBlockState(), 3);
                                }
                            }
                        }

                        BlockState blockstate = (BlockState) ModItems.ARCHIVE_PORTAL_BLOCK.get().defaultBlockState().setValue(NetherPortalBlock.AXIS, axis);

                        for(k2 = 0; k2 < 2; ++k2) {
                            for(l2 = 0; l2 < 3; ++l2) {
                                blockpos$mutableblockpos.setWithOffset(blockpos, k2 * direction.getStepX(), l2, k2 * direction.getStepZ());
                                this.level.setBlock(blockpos$mutableblockpos, blockstate, 18);
                            }
                        }

                        return Optional.of(new BlockUtil.FoundRectangle(blockpos.immutable(), 2, 3));
                    }

                    blockpos$mutableblockpos1 = (BlockPos.MutableBlockPos)var14.next();
                    l2 = Math.min(i, this.level.getHeight(Heightmap.Types.MOTION_BLOCKING, blockpos$mutableblockpos1.getX(), blockpos$mutableblockpos1.getZ()));
                } while(!worldborder.isWithinBounds(blockpos$mutableblockpos1));
            } while(!worldborder.isWithinBounds(blockpos$mutableblockpos1.move(direction, 1)));

            blockpos$mutableblockpos1.move(direction.getOpposite(), 1);

            for(l = l2; l >= this.level.getMinBuildHeight(); --l) {
                blockpos$mutableblockpos1.setY(l);
                if (this.canPortalReplaceBlock(blockpos$mutableblockpos1)) {
                    for(j3 = l; l > this.level.getMinBuildHeight() && this.canPortalReplaceBlock(blockpos$mutableblockpos1.move(Direction.DOWN)); --l) {
                    }

                    if (l + 4 <= i) {
                        j1 = j3 - l;
                        if (j1 <= 0 || j1 >= 3) {
                            blockpos$mutableblockpos1.setY(l);
                            if (this.canHostFrame(blockpos$mutableblockpos1, blockpos$mutableblockpos, direction, 0)) {
                                double d2 = pos.distSqr(blockpos$mutableblockpos1);
                                if (this.canHostFrame(blockpos$mutableblockpos1, blockpos$mutableblockpos, direction, -1) && this.canHostFrame(blockpos$mutableblockpos1, blockpos$mutableblockpos, direction, 1) && (d0 == -1.0 || d0 > d2)) {
                                    d0 = d2;
                                    blockpos = blockpos$mutableblockpos1.immutable();
                                }

                                if (d0 == -1.0 && (d1 == -1.0 || d1 > d2)) {
                                    d1 = d2;
                                    blockpos1 = blockpos$mutableblockpos1.immutable();
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    private boolean canPortalReplaceBlock(BlockPos.MutableBlockPos pos) {
        BlockState blockstate = this.level.getBlockState(pos);
        return blockstate.canBeReplaced() && blockstate.getFluidState().isEmpty();
    }

    private boolean canHostFrame(BlockPos originalPos, BlockPos.MutableBlockPos offsetPos, Direction p_direction, int offsetScale) {
        Direction direction = p_direction.getClockWise();

        for(int i = -1; i < 3; ++i) {
            for(int j = -1; j < 4; ++j) {
                offsetPos.setWithOffset(originalPos, p_direction.getStepX() * i + direction.getStepX() * offsetScale, j, p_direction.getStepZ() * i + direction.getStepZ() * offsetScale);
                if (j < 0 && !this.level.getBlockState(offsetPos).isSolid()) {
                    return false;
                }

                if (j >= 0 && !this.canPortalReplaceBlock(offsetPos)) {
                    return false;
                }
            }
        }

        return true;
    }
}
