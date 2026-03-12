package fr.stylobow.iyc.attachment;

import com.mojang.serialization.Codec;
import fr.stylobow.iyc.ImagineYourCraft;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public class ModAttachmentTypes {

    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES =
            DeferredRegister.create(NeoForgeRegistries.Keys.ATTACHMENT_TYPES, "iyc");

    public static final Supplier<AttachmentType<String>> ACTIVE_COSMETIC = ATTACHMENT_TYPES.register("active_cosmetic",
            () -> AttachmentType.builder(() -> "")
                    .serialize(Codec.STRING)
                    .copyOnDeath()
                    .build());
}