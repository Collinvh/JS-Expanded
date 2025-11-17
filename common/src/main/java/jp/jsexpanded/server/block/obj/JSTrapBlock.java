package jp.jsexpanded.server.block.obj;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class JSTrapBlock extends HorizontalDirectionalBlock {
    public static final MapCodec<JSTrapBlock> CODEC = simpleCodec(JSTrapBlock::new);
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final BooleanProperty POWERED = BlockStateProperties.POWERED;

    public JSTrapBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(FACING, Direction.NORTH));
    }

    @Override
    protected MapCodec<? extends HorizontalDirectionalBlock> codec() {
        return CODEC;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, POWERED);
    }

    @Override
    protected @NotNull BlockState rotate(BlockState pState, Rotation pRot) {
        return pState.setValue(FACING, pRot.rotate(pState.getValue(FACING)));
    }

    @Override
    protected @NotNull BlockState mirror(BlockState pState, Mirror pMirror) {
        return pState.rotate(pMirror.getRotation(pState.getValue(FACING)));
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return this.defaultBlockState().setValue(FACING, pContext.getHorizontalDirection().getOpposite());
    }

    @Override
    public void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, BlockPos fromPos, boolean isMoving) {
        if (level.isClientSide) return;

        boolean powered = level.hasNeighborSignal(pos);

        if (powered && !state.getValue(POWERED)) {
            Direction facing = state.getValue(FACING);

            tryShoot(level, pos.relative(facing), facing);
            tryShoot(level, pos.above(), Direction.UP);
            tryShoot(level, pos.below(), Direction.DOWN);
        }

        level.setBlock(pos, state.setValue(POWERED, powered), 3);
    }

    private void tryShoot(Level level, BlockPos pos, Direction dir) {
        BlockPos outPos = pos;
        BlockState outState = level.getBlockState(outPos);

        if (outState.isSolid()) return;

        Arrow arrow = new Arrow(
                level,
                pos.getX() + 0.5,
                pos.getY() + 0.5 + (dir == Direction.DOWN ? -0.5F : 0),
                pos.getZ() + 0.5,
                Items.ARROW.getDefaultInstance(),
                null
        );

        arrow.shoot(
                dir.getStepX(),
                dir.getStepY(),
                dir.getStepZ(),
                1.5f,
                0.5f
        );

        arrow.pickup = AbstractArrow.Pickup.DISALLOWED;
        level.addFreshEntity(arrow);

        CompletableFuture
                .delayedExecutor(5, TimeUnit.SECONDS)
                .execute(() -> {
                    Objects.requireNonNull(level.getServer()).execute(() -> {
                        if (!arrow.isRemoved()) {
                            arrow.discard();
                        }
                    });
                });
    }
}