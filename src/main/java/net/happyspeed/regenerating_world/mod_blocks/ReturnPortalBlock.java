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
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.NetherPortalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;

public class ReturnPortalBlock extends NetherPortalBlock {
    public ReturnPortalBlock(Properties props) {
        super(props);
    }

    @Override
    public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        if (!level.isClientSide && entity instanceof ServerPlayer player) {

            MinecraftServer server = level.getServer();
            ServerLevel targetLevel = server.getLevel(Level.OVERWORLD);

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
        if (targetLevel != null && !player.isPassenger()) {
            BlockPos returnPos = targetLevel.getSharedSpawnPos(); //currently this is just world spawn
            player.teleportTo(targetLevel, returnPos.getX() + 0.5, returnPos.getY(), returnPos.getZ() + 0.5, player.getYRot(), player.getXRot());
        }
    }




}
