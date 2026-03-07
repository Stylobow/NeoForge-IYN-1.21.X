package fr.stylobow.iyc.client;

import fr.stylobow.iyc.block.custom.CloudBlock;
import net.minecraft.client.Camera;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ViewportEvent;

@EventBusSubscriber(modid = "iyc", bus = EventBusSubscriber.Bus.GAME, value = Dist.CLIENT)
public class CloudFogHandler {

    @SubscribeEvent
    public static void onFogColor(ViewportEvent.ComputeFogColor event) {
        Camera camera = event.getCamera();
        BlockState state = camera.getEntity().level().getBlockState(BlockPos.containing(camera.getPosition()));

        if (state.getBlock() instanceof CloudBlock cloud) {
            event.setRed(cloud.getFogR());
            event.setGreen(cloud.getFogG());
            event.setBlue(cloud.getFogB());
        }
    }

    @SubscribeEvent
    public static void onFogRender(ViewportEvent.RenderFog event) {
        Camera camera = event.getCamera();
        BlockState state = camera.getEntity().level().getBlockState(BlockPos.containing(camera.getPosition()));

        if (state.getBlock() instanceof CloudBlock) {
            event.setNearPlaneDistance(0.0F);
            event.setFarPlaneDistance(5.0F);
            event.setCanceled(true);
        }
    }
}