package fr.stylobow.iyc.event;

import fr.stylobow.iyc.ImagineYourCraft;
import fr.stylobow.iyc.command.CosmeticCommand;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterCommandsEvent;

@EventBusSubscriber(modid = "iyc", bus = EventBusSubscriber.Bus.GAME)
public class ModEvents {

    @SubscribeEvent
    public static void onCommandsRegister(RegisterCommandsEvent event) {
        CosmeticCommand.register(event.getDispatcher());
    }
}