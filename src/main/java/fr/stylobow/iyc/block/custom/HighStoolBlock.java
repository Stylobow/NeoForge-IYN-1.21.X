package fr.stylobow.iyc.block.custom;

import com.mojang.serialization.MapCodec;
import fr.stylobow.iyc.block.entity.ModEntities;
import fr.stylobow.iyc.block.entity.custom.ChairEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class HighStoolBlock extends HorizontalDirectionalBlock {
    public static final MapCodec<HighStoolBlock> CODEC = simpleCodec(HighStoolBlock::new);

    protected static final VoxelShape SHAPE = Block.box(3, 0, 3, 13, 14, 13);

    public HighStoolBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if (!level.isClientSide() && !player.isShiftKeyDown()) {
            Entity entity = null;
            List<ChairEntity> entites = level.getEntities(ModEntities.CHAIR_ENTITY.get(), new AABB(pos), chair -> true);

            if(entites.isEmpty()) {
                entity = ModEntities.CHAIR_ENTITY.get().spawn(((ServerLevel) level), pos, MobSpawnType.TRIGGERED);

                if (entity != null) {
                    entity.setPos(entity.getX(), entity.getY() + 0.35, entity.getZ());
                }
            } else {
                entity = entites.get(0);
            }
            if (entity != null) {
                player.startRiding(entity);
            }
        }

        return InteractionResult.SUCCESS;
    }

    @Override
    protected MapCodec<? extends HorizontalDirectionalBlock> codec() {
        return CODEC;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean movedByPiston) {
        if (state.getBlock() != newState.getBlock()) {
            List<ChairEntity> entities = level.getEntities(ModEntities.CHAIR_ENTITY.get(), new AABB(pos), chair -> true);

            for (ChairEntity entity : entities) {
                entity.discard();
            }
        }

        super.onRemove(state, level, pos, newState, movedByPiston);
    }
}