package fr.stylobow.iyc.worldgen.feature;

import com.mojang.serialization.Codec;
import fr.stylobow.iyc.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class SteelFeature extends Feature<NoneFeatureConfiguration> {
    public SteelFeature(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        WorldGenLevel worldgenlevel = context.level();
        BlockPos blockpos = context.origin();
        RandomSource randomsource = context.random();

        BlockState steelState = ModBlocks.STEEL_ORE.get().defaultBlockState();

        if (!worldgenlevel.isEmptyBlock(blockpos)) {
            return false;
        } else {
            BlockState blockstate = worldgenlevel.getBlockState(blockpos.above());
            if (!blockstate.is(Blocks.NETHERRACK) && !blockstate.is(Blocks.BASALT) && !blockstate.is(Blocks.BLACKSTONE)) {
                return false;
            } else {
                worldgenlevel.setBlock(blockpos, steelState, 2);
                int blocksPlaced = 1;

                for(int i = 0; i < 750; ++i) {
                    if (blocksPlaced >= 50) {
                        break;
                    }

                    BlockPos blockpos1 = blockpos.offset(
                            randomsource.nextInt(8) - randomsource.nextInt(8),
                            -randomsource.nextInt(12),
                            randomsource.nextInt(8) - randomsource.nextInt(8)
                    );

                    if (worldgenlevel.getBlockState(blockpos1).isAir()) {
                        int neighbors = 0;
                        for(Direction direction : Direction.values()) {
                            if (worldgenlevel.getBlockState(blockpos1.relative(direction)).is(steelState.getBlock())) {
                                ++neighbors;
                            }
                            if (neighbors > 1) break;
                        }

                        if (neighbors == 1) {
                            worldgenlevel.setBlock(blockpos1, steelState, 2);
                            blocksPlaced++;
                        }
                    }
                }
                return true;
            }
        }
    }
}