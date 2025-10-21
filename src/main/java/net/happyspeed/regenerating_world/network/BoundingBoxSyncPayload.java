package net.happyspeed.regenerating_world.network;

import net.happyspeed.regenerating_world.RegeneratingWorld;
import net.happyspeed.regenerating_world.saved_data.SuperStructureSpawnData;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.levelgen.structure.BoundingBox;

import java.util.ArrayList;
import java.util.List;

public record BoundingBoxSyncPayload(List<BoundingBox> boxes) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<BoundingBoxSyncPayload> TYPE =
            new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(RegeneratingWorld.MODID, "bounding_box_sync"));

    public static final StreamCodec<FriendlyByteBuf, BoundingBoxSyncPayload> STREAM_CODEC =
            new StreamCodec<>() {
                @Override
                public BoundingBoxSyncPayload decode(FriendlyByteBuf buf) {
                    int count = buf.readVarInt();
                    List<BoundingBox> boxes = new ArrayList<>();
                    for (int i = 0; i < count; i++) {
                        boxes.add(new BoundingBox(
                                buf.readInt(), buf.readInt(), buf.readInt(),
                                buf.readInt(), buf.readInt(), buf.readInt()
                        ));
                    }
                    return new BoundingBoxSyncPayload(boxes);
                }

                @Override
                public void encode(FriendlyByteBuf buf, BoundingBoxSyncPayload payload) {
                    buf.writeVarInt(payload.boxes().size());
                    for (BoundingBox box : payload.boxes()) {
                        buf.writeInt(box.minX());
                        buf.writeInt(box.minY());
                        buf.writeInt(box.minZ());
                        buf.writeInt(box.maxX());
                        buf.writeInt(box.maxY());
                        buf.writeInt(box.maxZ());
                    }
                }
            };



    @Override
    public CustomPacketPayload.Type<BoundingBoxSyncPayload> type() {
        return TYPE;
    }

    public static void syncBoundingBoxesTo(ServerPlayer player) {
        List<BoundingBox> boxes = SuperStructureSpawnData.get(player.serverLevel()).getBoxes();
        player.connection.send(new BoundingBoxSyncPayload(boxes));
    }

}

