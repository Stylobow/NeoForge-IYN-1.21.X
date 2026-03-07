package fr.stylobow.iyc.client;

import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderPlayerEvent;

import java.util.UUID;

@EventBusSubscriber(modid = "iyc", bus = EventBusSubscriber.Bus.GAME, value = Dist.CLIENT)
public class DevPlayerRenderer {

    private static final UUID DEV_UUID = UUID.fromString("ef3a9cb5-3544-4ba3-b0f5-2fe19d2363be");
    private static final float SCALE_FACTOR = 1.4F / 1.8F;

    @SubscribeEvent
    public static void onPlayerRenderPre(RenderPlayerEvent.Pre event) {
        Player player = event.getEntity();
        if (player.getUUID().equals(DEV_UUID)) {
            event.getPoseStack().pushPose();
            event.getPoseStack().scale(SCALE_FACTOR, SCALE_FACTOR, SCALE_FACTOR);
        }
    }

    @SubscribeEvent
    public static void onPlayerRenderPost(RenderPlayerEvent.Post event) {
        Player player = event.getEntity();
        if (player.getUUID().equals(DEV_UUID)) {
            event.getPoseStack().popPose();
        }
    }
}