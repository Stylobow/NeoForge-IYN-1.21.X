package fr.stylobow.iyc.network;

import fr.stylobow.iyc.attachment.ModAttachmentTypes;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record SyncCosmeticPayload(int playerId, String cosmeticId) implements CustomPacketPayload {

    public static final Type<SyncCosmeticPayload> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath("iyc", "sync_cosmetic"));

    public static final StreamCodec<ByteBuf, SyncCosmeticPayload> CODEC = StreamCodec.composite(
            ByteBufCodecs.INT, SyncCosmeticPayload::playerId,
            ByteBufCodecs.STRING_UTF8, SyncCosmeticPayload::cosmeticId,
            SyncCosmeticPayload::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(SyncCosmeticPayload payload, IPayloadContext context) {
        context.enqueueWork(() -> {
            Level level = Minecraft.getInstance().level;
            if (level != null) {
                Entity entity = level.getEntity(payload.playerId());
                if (entity instanceof Player player) {
                    player.setData(ModAttachmentTypes.ACTIVE_COSMETIC, payload.cosmeticId());
                }
            }
        });
    }
}