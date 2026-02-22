package fr.stylobow.iyc.client;

import fr.stylobow.iyc.screen.CustomScreen;
import fr.stylobow.iyc.client.gui.MusicScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.PauseScreen;
import net.minecraft.network.chat.Component;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ScreenEvent;

@EventBusSubscriber(modid = "iyc", value = Dist.CLIENT)
public class ClientEvents {

    @SubscribeEvent
    public static void onScreenInit(ScreenEvent.Init.Post event) {
        if (event.getScreen() instanceof PauseScreen screen) {

            int buttonHeight = 20;
            int padding = 50;
            int targetY = (screen.height / 4 + 48) + 96;

            for (GuiEventListener listener : event.getListenersList()) {
                if (listener instanceof AbstractWidget widget) {
                    if (widget.getY() >= targetY) {
                        widget.setY(widget.getY() + padding);
                    }
                }
            }

            int buttonWidth = 100;
            int leftX = screen.width / 2 - 102;
            int rightX = screen.width / 2 + 2;

            Component IYN_Settings = Component.translatable("iyc.menu.title").withColor(0x189E00);

            Button customButton = Button.builder(IYN_Settings, (button) -> {
                        Minecraft.getInstance().setScreen(new CustomScreen(Component.translatable("iyc.menu.open")));
                    })
                    .bounds(leftX, targetY, buttonWidth, buttonHeight)
                    .build();

            Button musicButton = Button.builder(Component.translatable("iyc.music.title"), (button) -> {
                        Minecraft.getInstance().setScreen(new MusicScreen());
                    })
                    .bounds(rightX, targetY, buttonWidth, buttonHeight)
                    .build();

            event.addListener(customButton);
            event.addListener(musicButton);
        }
    }
}