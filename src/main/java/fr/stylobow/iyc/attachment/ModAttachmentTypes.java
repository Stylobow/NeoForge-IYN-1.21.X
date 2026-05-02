package fr.stylobow.iyc.attachment;

import com.mojang.serialization.Codec;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.nio.ByteBuffer;
import java.util.function.Supplier;

public class ModAttachmentTypes {

    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES =
            DeferredRegister.create(NeoForgeRegistries.Keys.ATTACHMENT_TYPES, "iyc");

    public static final Supplier<AttachmentType<String>> ACTIVE_COSMETIC = ATTACHMENT_TYPES.register("active_cosmetic",
            () -> AttachmentType.builder(() -> "")
                    .serialize(Codec.STRING)
                    .copyOnDeath()
                    .build());

    public static final Supplier<AttachmentType<byte[]>> SKIN_DATA = ATTACHMENT_TYPES.register(
            "skin_data", () -> AttachmentType.builder(() -> new byte[0])
                    .serialize(Codec.BYTE_BUFFER.xmap(ByteBuffer::array, ByteBuffer::wrap))
                    .build());

    public static final Supplier<AttachmentType<byte[]>> CAPE_DATA = ATTACHMENT_TYPES.register(
            "cape_data", () -> AttachmentType.builder(() -> new byte[0])
                    .serialize(Codec.BYTE_BUFFER.xmap(ByteBuffer::array, ByteBuffer::wrap))
                    .build());

    public static final Supplier<AttachmentType<byte[]>> HAT_DATA = ATTACHMENT_TYPES.register(
            "hat_data", () -> AttachmentType.builder(() -> new byte[0])
                    .serialize(Codec.BYTE_BUFFER.xmap(ByteBuffer::array, ByteBuffer::wrap))
                    .build());
}