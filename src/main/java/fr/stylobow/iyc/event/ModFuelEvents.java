package fr.stylobow.iyc.event;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.furnace.FurnaceFuelBurnTimeEvent;

@EventBusSubscriber(modid = "iyc", bus = EventBusSubscriber.Bus.GAME)
public class ModFuelEvents {

    @SubscribeEvent
    public static void onFurnaceFuelBurnTime(FurnaceFuelBurnTimeEvent event) {
        if (event.getItemStack().is(TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("iyc", "fuel")))) {
            event.setBurnTime(600);
        }
    }
}