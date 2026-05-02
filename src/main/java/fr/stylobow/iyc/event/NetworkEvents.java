package fr.stylobow.iyc.event;

import fr.stylobow.iyc.attachment.ModAttachmentTypes;
import fr.stylobow.iyc.client.skin.CustomSkinManager;
import fr.stylobow.iyc.network.SkinSyncPayload;
import fr.stylobow.iyc.network.SyncCosmeticPayload;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

@EventBusSubscriber(modid = "iyc", bus = EventBusSubscriber.Bus.MOD)
public class NetworkEvents {

    @SubscribeEvent
    public static void register(RegisterPayloadHandlersEvent event) {
        PayloadRegistrar registrar = event.registrar("1.0");

        registrar.playToClient(
                SyncCosmeticPayload.TYPE,
                SyncCosmeticPayload.CODEC,
                SyncCosmeticPayload::handle
        );

        registrar.playBidirectional(
                SkinSyncPayload.TYPE,
                SkinSyncPayload.CODEC,
                (payload, context) -> {
                    if (context.flow().isServerbound()) {
                        context.enqueueWork(() -> {
                            if (context.player() instanceof ServerPlayer serverPlayer) {
                                if (payload.cosmeticType() == 0) {
                                    serverPlayer.setData(ModAttachmentTypes.SKIN_DATA, payload.imageData());
                                } else if (payload.cosmeticType() == 1) {
                                    serverPlayer.setData(ModAttachmentTypes.CAPE_DATA, payload.imageData());
                                } else if (payload.cosmeticType() == 2) {
                                    serverPlayer.setData(ModAttachmentTypes.HAT_DATA, payload.imageData());
                                }
                                PacketDistributor.sendToPlayersTrackingEntity(serverPlayer, payload);
                            }
                        });
                    } else {
                        context.enqueueWork(() -> {
                            CustomSkinManager.receiveSkinPacket(payload.playerId(), payload.cosmeticType(), payload.imageData());
                        });
                    }
                }
        );
    }
}