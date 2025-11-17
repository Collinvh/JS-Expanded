package jp.jsexpanded.server.block.obj;

import jp.jurassicsaga.server.base.block.obj.plant.JSDoublePlantBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ReedPlant extends JSDoublePlantBlock implements LiquidBlockContainer, SimpleWaterloggedBlock {
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public ReedPlant(Properties p_52861_) {
        super(p_52861_);
    }

    @Override
    protected boolean mayPlaceOn(@NotNull BlockState pState, BlockGetter pLevel, BlockPos pPos) {
        if (pLevel.getFluidState(pPos.above(2)).is(FluidTags.WATER)) {
            return false;
        }

        return pState.isSolidRender(pLevel, pPos);
    }

    @Override
    public @NotNull BlockState updateShape(BlockState state, @NotNull Direction facing, @NotNull BlockState facingState, @NotNull LevelAccessor world,
                                           @NotNull BlockPos currentPos, @NotNull BlockPos facingPos) {
        if (state.getValue(WATERLOGGED)) {
            world.scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(world));
        }

        return super.updateShape(state, facing, facingState, world, currentPos, facingPos);
    }

    @Override
    public void neighborChanged(@NotNull BlockState state, @NotNull Level world, @NotNull BlockPos pos, @NotNull Block blockIn, @NotNull BlockPos fromPos, boolean isMoving) {
        if (!canSurvive(state, world, pos)) {
            if (state.getValue(WATERLOGGED)) {
                world.setBlockAndUpdate(pos, Blocks.WATER.defaultBlockState());
            } else {
                world.destroyBlock(pos, false);
            }
        }
        if (state.getValue(HALF) == DoubleBlockHalf.LOWER) {
            BlockState stateUpper = world.getBlockState(pos.above());
            if (stateUpper.getBlock() instanceof ReedPlant) {
                if (!canSurvive(stateUpper, world, pos.above())) {
                    world.destroyBlock(pos.above(), false);
                }
            }
        }
    }

    @Override
    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        FluidState FluidState = context.getLevel().getFluidState(context.getClickedPos());
        BlockState state = super.getStateForPlacement(context);
        if (state != null) {
            return state.setValue(WATERLOGGED, FluidState.getType() == Fluids.WATER);
        } else {
            return null;
        }
    }

    @Override
    public boolean canSurvive(BlockState state, @NotNull LevelReader world, @NotNull BlockPos pos) {
        if (state.getValue(HALF) == DoubleBlockHalf.UPPER && state.getValue(WATERLOGGED)) {
            return false;
        }
        if (state.getValue(HALF) != DoubleBlockHalf.UPPER) {
            if (world.getFluidState(pos).getType() == Fluids.WATER)
                return super.canSurvive(state, world, pos);
            return false;
        } else {

            BlockState blockstate = world.getBlockState(pos.below());
            if (state.getBlock() != this)
                return false;
            if (world.isWaterAt(pos.above(1))) {
                return false;
            }
            return blockstate.getBlock() == this && blockstate.getValue(HALF) == DoubleBlockHalf.LOWER;
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(HALF, WATERLOGGED);
    }

    @Override
    public @NotNull FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    public boolean canPlaceLiquid(@Nullable Player pPlayer, @NotNull BlockGetter pLevel, @NotNull BlockPos pPos, @NotNull BlockState pState, @NotNull Fluid pFluid) {
        return false;
    }

    @Override
    public boolean placeLiquid(@NotNull LevelAccessor pLevel, @NotNull BlockPos pPos, @NotNull BlockState pState, @NotNull FluidState pFluidState) {
        return false;
    }
}
