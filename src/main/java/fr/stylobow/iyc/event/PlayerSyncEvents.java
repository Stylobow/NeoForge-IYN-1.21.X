package fr.stylobow.iyc.event;

import fr.stylobow.iyc.ImagineYourCraft;
import fr.stylobow.iyc.attachment.ModAttachmentTypes;
import fr.stylobow.iyc.network.SyncCosmeticPayload;
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

            if (!cosmetic.isEmpty()) {
                if (event.getEntity() instanceof ServerPlayer trackingPlayer) {
                    PacketDistributor.sendToPlayer(trackingPlayer, new SyncCosmeticPayload(trackedPlayer.getId(), cosmetic));
                }
            }
        }
    }
}