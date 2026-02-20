package fr.stylobow.iyc.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class RainbowBlockEntity extends BlockEntity {

    public RainbowBlockEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntities.RAINBOW_BE.get(), pos, blockState);
    }
}