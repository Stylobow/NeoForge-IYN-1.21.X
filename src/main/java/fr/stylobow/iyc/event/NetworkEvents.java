package fr.stylobow.iyc.event;

import fr.stylobow.iyc.attachment.ModAttachmentTypes;
import fr.stylobow.iyc.network.SkinSyncPayload;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

@EventBusSubscriber(modid = "iyc", bus = EventBusSubscriber.Bus.MOD)
public class NetworkEvents {

    @SubscribeEvent
    public static void register(final RegisterPayloadHandlersEvent event) {
        final PayloadRegistrar registrar = event.registrar("1.0");

        registrar.playBidirectional(
                SkinSyncPayload.TYPE,
                SkinSyncPayload.CODEC,
                NetworkEvents::onSkinPacketReceived
        );
    }

    private static void onSkinPacketReceived(final SkinSyncPayload payload, final IPayloadContext context) {
        if (context.flow().isServerbound()) {
            context.enqueueWork(() -> {
                if (context.player() instanceof ServerPlayer serverPlayer) {
                    byte[] data = payload.imageData();
                    int type = payload.cosmeticType();

                    if (type == 0) serverPlayer.setData(ModAttachmentTypes.SKIN_DATA, data);
                    else if (type == 1) serverPlayer.setData(ModAttachmentTypes.CAPE_DATA, data);
                    else if (type == 2) serverPlayer.setData(ModAttachmentTypes.HAT_DATA, data);

                    PacketDistributor.sendToPlayersTrackingEntity(serverPlayer, payload);
                }
            });
        } else {
            if (FMLEnvironment.dist == Dist.CLIENT) {
                ClientInnerRunner.process(payload, context);
            }
        }
    }

    private static class ClientInnerRunner {
        private static void process(SkinSyncPayload payload, IPayloadContext context) {
            ClientHandler.handleSkinPacket(payload, context);
        }
    }
}