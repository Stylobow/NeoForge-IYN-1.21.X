package fr.stylobow.iyc.mixin;

import net.minecraft.client.gui.components.SplashRenderer;
import net.minecraft.client.gui.screens.TitleScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

@Mixin(TitleScreen.class)
public class TitleScreenMixin {

    @Shadow private SplashRenderer splash;

    @Inject(method = "init", at = @At("RETURN"))
    private void onInit(CallbackInfo ci) {
        Calendar calendar = Calendar.getInstance();
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        Map<String, String> specialEvents = new HashMap<>();

        // "MONTH-DAY" (JANUARY = 0 ; DECEMBER = 11)
        specialEvents.put("0-1", "Bonne année !");
        specialEvents.put("9-24", "Joyeux anniversaire Stylobow !");
        specialEvents.put("9-31", "Bouh ! Joyeux Halloween !");
        specialEvents.put("11-25", "Joyeux Noël à tous !");

        String dateKey = month + "-" + day;

        if (specialEvents.containsKey(dateKey)) {
            this.splash = new SplashRenderer(specialEvents.get(dateKey));
        }
    }
}