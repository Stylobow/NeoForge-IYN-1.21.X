package fr.stylobow.iyc.event;

import fr.stylobow.iyc.ImagineYourCraft;
import fr.stylobow.iyc.attachment.ModAttachmentTypes;
import fr.stylobow.iyc.network.SkinSyncPayload;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.network.PacketDistributor;

@EventBusSubscriber(modid = ImagineYourCraft.MOD_ID, bus = EventBusSubscriber.Bus.GAME)
public class PlayerSyncEvents {

    @SubscribeEvent
    public static void onStartTracking(PlayerEvent.StartTracking event) {
        if (event.getTarget() instanceof Player trackedPlayer) {

            if (event.getEntity() instanceof ServerPlayer trackingPlayer) {

                if (trackedPlayer instanceof ServerPlayer targetServerPlayer) {
                    byte[] skin = targetServerPlayer.getData(ModAttachmentTypes.SKIN_DATA);
                    if (skin != null && skin.length > 0) {
                        PacketDistributor.sendToPlayer(trackingPlayer, new SkinSyncPayload(targetServerPlayer.getUUID(), 0, skin));
                    }

                    byte[] cape = targetServerPlayer.getData(ModAttachmentTypes.CAPE_DATA);
                    if (cape != null && cape.length > 0) {
                        PacketDistributor.sendToPlayer(trackingPlayer, new SkinSyncPayload(targetServerPlayer.getUUID(), 1, cape));
                    }

                    byte[] hat = targetServerPlayer.getData(ModAttachmentTypes.HAT_DATA);
                    if (hat != null && hat.length > 0) {
                        PacketDistributor.sendToPlayer(trackingPlayer, new SkinSyncPayload(targetServerPlayer.getUUID(), 2, hat));
                    }
                }
            }
        }
    }
}