package fr.stylobow.iyc.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import fr.stylobow.iyc.block.entity.RainbowBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import org.joml.Matrix4f;

import java.awt.Color;

public class RainbowBlockRenderer implements BlockEntityRenderer<RainbowBlockEntity> {

    public RainbowBlockRenderer(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(RainbowBlockEntity blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        BlockState blockState = blockEntity.getBlockState();
        BlockPos pos = blockEntity.getBlockPos();
        BlockRenderDispatcher blockRenderer = Minecraft.getInstance().getBlockRenderer();

        float timeHue = (System.currentTimeMillis() % 5000L) / 5000.0f;
        float posOffset = (pos.getX() + pos.getY() + pos.getZ()) * 0.05f;
        float finalHue = (timeHue + posOffset) % 1.0f;
        if (finalHue < 0) finalHue += 1.0f;

        int rgb = Color.HSBtoRGB(finalHue, 0.8f, 1.0f);
        float red = ((rgb >> 16) & 0xFF) / 255.0f;
        float green = ((rgb >> 8) & 0xFF) / 255.0f;
        float blue = (rgb & 0xFF) / 255.0f;

        poseStack.pushPose();

        VertexConsumer vertexConsumer = bufferSource.getBuffer(RenderType.solid());

        blockRenderer.getModelRenderer().renderModel(
                poseStack.last(),
                vertexConsumer,
                blockState,
                blockRenderer.getBlockModel(blockState),
                red, green, blue,
                packedLight,
                packedOverlay,
                net.neoforged.neoforge.client.model.data.ModelData.EMPTY,
                RenderType.solid()
        );

        poseStack.popPose();
    }
}