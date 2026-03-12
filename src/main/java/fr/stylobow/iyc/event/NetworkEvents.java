package fr.stylobow.iyc.event;

import fr.stylobow.iyc.network.SyncCosmeticPayload;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
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
    }
}