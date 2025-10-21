package net.happyspeed.regenerating_world.mod_blocks;

import net.happyspeed.regenerating_world.RegeneratingWorld;
import net.happyspeed.regenerating_world.callbacks.TemporaryTickingAreaManager;
import net.happyspeed.regenerating_world.faces.LivingEntityClassAccess;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.NetherPortalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;

public class ArchivePortalBlock extends NetherPortalBlock {
    public ArchivePortalBlock(Properties props) {
        super(props);
    }

    @Override
    public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        if (!level.isClientSide && entity instanceof ServerPlayer player) {

            ResourceKey<Level> targetDim = ResourceKey.create(Registries.DIMENSION, ResourceLocation.fromNamespaceAndPath(RegeneratingWorld.MODID, "the_archive"));
            MinecraftServer server = level.getServer();
            ServerLevel targetLevel = server.getLevel(targetDim);

            if (((LivingEntityClassAccess) player).getPortalWarmUpTicks() < 40) {
                ((LivingEntityClassAccess) player).setPortalWarmUpTicks(((LivingEntityClassAccess) player).getPortalWarmUpTicks() + 1);
            }

            if (targetLevel != null && !player.isPassenger()) {
                if (((LivingEntityClassAccess) player).getPortalWarmUpTicks() == 40) {
                    ((LivingEntityClassAccess) player).setPortalWarmUpTicks(0);
                    TemporaryTickingAreaManager.loadArea(targetLevel, new BlockPos(pos.getX(), pos.getY(), pos.getZ()), 1, 40);
                    teleportPlayer(player, targetLevel, new BlockPos(pos.getX() ,targetLevel.getHeight(Heightmap.Types.MOTION_BLOCKING, pos.getX(), pos.getZ()), pos.getZ()));
                }
            }
        }
    }


    public static void teleportPlayer(ServerPlayer player, ServerLevel targetLevel, BlockPos targetPos) {
        player.teleportTo(targetLevel, targetPos.getX() + 0.5, targetPos.getY(), targetPos.getZ() + 0.5, player.getYRot(), player.getXRot());
    }



//    @Override
//    @Nullable
//    public DimensionTransition getPortalDestination(ServerLevel level, Entity entity, BlockPos pos) {
//        ResourceKey<Level> the_archive = ResourceKey.create(Registries.DIMENSION, ResourceLocation.fromNamespaceAndPath(RegeneratingWorld.MODID, "the_archive"));
//        ResourceKey<Level> resourcekey = level.dimension() == the_archive ? Level.OVERWORLD : the_archive;
//        ServerLevel serverlevel = level.getServer().getLevel(resourcekey);
//        if (serverlevel == null) {
//            return null;
//        } else {
//            boolean flag = serverlevel.dimension() == the_archive;
//            WorldBorder worldborder = serverlevel.getWorldBorder();
//            double d0 = DimensionType.getTeleportationScale(level.dimensionType(), serverlevel.dimensionType());
//            BlockPos blockpos = worldborder.clampToBounds(entity.getX() * d0, entity.getY(), entity.getZ() * d0);
//            return this.getExitPortal(serverlevel, entity, pos, blockpos, flag, worldborder);
//        }
//    }
//
//    @Nullable
//    private DimensionTransition getExitPortal(ServerLevel level, Entity entity, BlockPos pos, BlockPos exitPos, boolean isNether, WorldBorder worldBorder) {
//        Optional<BlockPos> optional = SpawnChunkRuleHandler.archivePortalForcer.findClosestPortalPosition(exitPos, isNether, worldBorder);
//        BlockUtil.FoundRectangle blockutil$foundrectangle;
//        DimensionTransition.PostDimensionTransition dimensiontransition$postdimensiontransition;
//        if (optional.isPresent()) {
//            BlockPos blockpos = (BlockPos)optional.get();
//            BlockState blockstate = level.getBlockState(blockpos);
//            blockutil$foundrectangle = BlockUtil.getLargestRectangleAround(blockpos, (Direction.Axis)blockstate.getValue(BlockStateProperties.HORIZONTAL_AXIS), 21, Direction.Axis.Y, 21, (p_351970_) -> {
//                return level.getBlockState(p_351970_) == blockstate;
//            });
//            dimensiontransition$postdimensiontransition = DimensionTransition.PLAY_PORTAL_SOUND.then((p_351967_) -> {
//                p_351967_.placePortalTicket(blockpos);
//            });
//        } else {
//            Direction.Axis direction$axis = (Direction.Axis)entity.level().getBlockState(pos).getOptionalValue(AXIS).orElse(Direction.Axis.X);
//            Optional<BlockUtil.FoundRectangle> optional1 = SpawnChunkRuleHandler.archivePortalForcer.createPortal(exitPos, direction$axis);
//            if (optional1.isEmpty()) {
//                RegeneratingWorld.LOGGER.error("Unable to create a portal, likely target out of worldborder");
//                return null;
//            }
//
//            blockutil$foundrectangle = (BlockUtil.FoundRectangle)optional1.get();
//            dimensiontransition$postdimensiontransition = DimensionTransition.PLAY_PORTAL_SOUND.then(DimensionTransition.PLACE_PORTAL_TICKET);
//        }
//
//        return getDimensionTransitionFromExit(entity, pos, blockutil$foundrectangle, level, dimensiontransition$postdimensiontransition);
//    }
//
//    private static DimensionTransition getDimensionTransitionFromExit(Entity entity, BlockPos pos, BlockUtil.FoundRectangle rectangle, ServerLevel level, DimensionTransition.PostDimensionTransition postDimensionTransition) {
//        BlockState blockstate = entity.level().getBlockState(pos);
//        Direction.Axis direction$axis;
//        Vec3 vec3;
//        if (blockstate.hasProperty(BlockStateProperties.HORIZONTAL_AXIS)) {
//            direction$axis = (Direction.Axis)blockstate.getValue(BlockStateProperties.HORIZONTAL_AXIS);
//            BlockUtil.FoundRectangle blockutil$foundrectangle = BlockUtil.getLargestRectangleAround(pos, direction$axis, 21, Direction.Axis.Y, 21, (p_351016_) -> {
//                return entity.level().getBlockState(p_351016_) == blockstate;
//            });
//            vec3 = entity.getRelativePortalPosition(direction$axis, blockutil$foundrectangle);
//        } else {
//            direction$axis = Direction.Axis.X;
//            vec3 = new Vec3(0.5, 0.0, 0.0);
//        }
//
//        return createDimensionTransition(level, rectangle, direction$axis, vec3, entity, entity.getDeltaMovement(), entity.getYRot(), entity.getXRot(), postDimensionTransition);
//    }
//
//    private static DimensionTransition createDimensionTransition(ServerLevel level, BlockUtil.FoundRectangle rectangle, Direction.Axis axis, Vec3 offset, Entity entity, Vec3 speed, float yRot, float xRot, DimensionTransition.PostDimensionTransition postDimensionTransition) {
//        BlockPos blockpos = rectangle.minCorner;
//        BlockState blockstate = level.getBlockState(blockpos);
//        Direction.Axis direction$axis = (Direction.Axis)blockstate.getOptionalValue(BlockStateProperties.HORIZONTAL_AXIS).orElse(Direction.Axis.X);
//        double d0 = (double)rectangle.axis1Size;
//        double d1 = (double)rectangle.axis2Size;
//        EntityDimensions entitydimensions = entity.getDimensions(entity.getPose());
//        int i = axis == direction$axis ? 0 : 90;
//        Vec3 vec3 = axis == direction$axis ? speed : new Vec3(speed.z, speed.y, -speed.x);
//        double d2 = (double)entitydimensions.width() / 2.0 + (d0 - (double)entitydimensions.width()) * offset.x();
//        double d3 = (d1 - (double)entitydimensions.height()) * offset.y();
//        double d4 = 0.5 + offset.z();
//        boolean flag = direction$axis == Direction.Axis.X;
//        Vec3 vec31 = new Vec3((double)blockpos.getX() + (flag ? d2 : d4), (double)blockpos.getY() + d3, (double)blockpos.getZ() + (flag ? d4 : d2));
//        Vec3 vec32 = PortalShape.findCollisionFreePosition(vec31, level, entity, entitydimensions);
//        return new DimensionTransition(level, vec32, vec3, yRot + (float)i, xRot, postDimensionTransition);
//    }


}
