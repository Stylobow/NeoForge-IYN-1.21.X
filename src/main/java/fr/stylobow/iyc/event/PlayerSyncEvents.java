package fr.stylobow.iyc.event;

import fr.stylobow.iyc.ImagineYourCraft;
import fr.stylobow.iyc.attachment.ModAttachmentTypes;
import fr.stylobow.iyc.network.SyncCosmeticPayload;
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
    public static void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event) {
        syncPlayerSelf(event.getEntity());
    }

    @SubscribeEvent
    public static void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event) {
        syncPlayerSelf(event.getEntity());
    }

    @SubscribeEvent
    public static void onPlayerChangeDimension(PlayerEvent.PlayerChangedDimensionEvent event) {
        syncPlayerSelf(event.getEntity());
    }

    private static void syncPlayerSelf(Player entity) {
        if (entity instanceof ServerPlayer player) {
            String cosmetic = player.getData(ModAttachmentTypes.ACTIVE_COSMETIC);
            if (!cosmetic.isEmpty()) {
                PacketDistributor.sendToPlayer(player, new SyncCosmeticPayload(player.getId(), cosmetic));
            }
        }
    }

    @SubscribeEvent
    public static void onStartTracking(PlayerEvent.StartTracking event) {
        if (event.getTarget() instanceof Player trackedPlayer) {
            String cosmetic = trackedPlayer.getData(ModAttachmentTypes.ACTIVE_COSMETIC);

            if (event.getEntity() instanceof ServerPlayer trackingPlayer) {
                if (!cosmetic.isEmpty()) {
                    PacketDistributor.sendToPlayer(trackingPlayer, new SyncCosmeticPayload(trackedPlayer.getId(), cosmetic));
                }

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