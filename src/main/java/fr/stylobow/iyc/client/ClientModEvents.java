package fr.stylobow.iyc.client;

import fr.stylobow.iyc.block.entity.ModBlockEntities;
import fr.stylobow.iyc.client.renderer.CustomCapeLayer;
import fr.stylobow.iyc.client.renderer.RainbowBlockRenderer;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.resources.PlayerSkin;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

@EventBusSubscriber(modid = "iyc", bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientModEvents {

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(ModBlockEntities.RAINBOW_BE.get(), RainbowBlockRenderer::new);
    }

    @SubscribeEvent
    public static void registerLayers(EntityRenderersEvent.AddLayers event) {

        for (PlayerSkin.Model skinName : event.getSkins()) {
            var renderer = event.getSkin(skinName);

            if (renderer instanceof LivingEntityRenderer livingRenderer) {

                livingRenderer.addLayer(new CustomCapeLayer(
                        (RenderLayerParent<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>>) livingRenderer
                ));
            }
        }
    }
}