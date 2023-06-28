package fuzs.permanentsponges.world.level.block.sponge;

import com.google.common.collect.Lists;
import fuzs.permanentsponges.core.CommonAbstractions;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BucketPickup;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;

import java.util.Queue;
import java.util.stream.Collectors;

public class SetSpongeTask extends AbstractSpongeTask {
    private final boolean vanish;
    private boolean hasDestroyedSource;

    public SetSpongeTask(ServerLevel level, BlockPos pos, int distance, Block replacement, boolean vanish) {
        super(level, pos, distance, replacement);
        this.vanish = vanish;
    }

    public static boolean instant(ServerLevel level, Block replacement, BlockPos source, int distance, boolean vanish) {
        SetSpongeTask task = new SetSpongeTask(level, source, distance, replacement, false);
        task.finishQuickly();
        return vanish && task.hasDestroyedSource;
    }

    @Override
    protected Queue<BlockPos> getSpongeRadiusAtPos(ServerLevel level, int distance, BlockPos pos) {
        return getSpongeRadius(distance).stream().map(pos::offset).collect(Collectors.toCollection(Lists::newLinkedList));
    }

    @Override
    public boolean containsBlocksAtChunkPos(int x, int z) {
        return Math.abs(SectionPos.blockToSection(this.pos.getX()) - x) <= 1 && Math.abs(SectionPos.blockToSection(this.pos.getZ()) - z) <= 1;
    }

    @Override
    public boolean advance(int amount) {
        for (int i = 0; (i < amount || amount == -1) && !this.blocks.isEmpty(); i++) {
            BlockPos blockpos1 = this.blocks.poll();
            BlockState blockstate = this.level.getBlockState(blockpos1);
            FluidState fluidstate = this.level.getFluidState(blockpos1);
            if (!fluidstate.isEmpty() || blockstate.isAir()) {
                if (this.shouldDestroySource(fluidstate)) {
                    this.destroySource();
                }
                this.removeLiquid(blockpos1, blockstate);
            }
        }
        return this.blocks.isEmpty();
    }

    private void removeLiquid(BlockPos pos, BlockState state) {
        if (state.getBlock() instanceof BucketPickup && !((BucketPickup) state.getBlock()).pickupBlock(this.level, pos, state).isEmpty()) {
            if (this.level.getBlockState(pos).is(Blocks.AIR)) {
                this.level.setBlock(pos, this.replacement.defaultBlockState(), 3);
            }
        } else if (state.getBlock() instanceof LiquidBlock || state.isAir()) {
            this.level.setBlock(pos, this.replacement.defaultBlockState(), 3);
        } else if (state.is(Blocks.KELP) || state.is(Blocks.KELP_PLANT) || state.is(Blocks.SEAGRASS) || state.is(Blocks.TALL_SEAGRASS)) {
            BlockEntity blockentity = state.hasBlockEntity() ? this.level.getBlockEntity(pos) : null;
            Block.dropResources(state, this.level, pos, blockentity);
            this.level.setBlock(pos, this.replacement.defaultBlockState(), 3);
        }
    }

    private boolean shouldDestroySource(FluidState fluidstate) {
        return CommonAbstractions.INSTANCE.getFluidTemperature(fluidstate) >= 1000;
    }

    private void destroySource() {
        if (!this.hasDestroyedSource) {
            if (this.vanish) this.level.setBlock(this.pos, this.replacement.defaultBlockState(), 3);
            this.hasDestroyedSource = true;
        }
    }
}
