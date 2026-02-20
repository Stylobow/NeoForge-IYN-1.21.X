package fr.stylobow.iyc.block.entity;

import fr.stylobow.iyc.ImagineYourCraft;
import fr.stylobow.iyc.block.ModBlocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.minecraft.core.registries.Registries;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, ImagineYourCraft.MOD_ID);

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<RainbowBlockEntity>> RAINBOW_BE =
            BLOCK_ENTITIES.register("rainbow_be", () ->
                    BlockEntityType.Builder.of(RainbowBlockEntity::new, ModBlocks.RAINBOW_IRON_BLOCK.get()).build(null));

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}