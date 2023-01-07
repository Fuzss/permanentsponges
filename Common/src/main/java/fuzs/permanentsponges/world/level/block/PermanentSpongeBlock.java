package fuzs.permanentsponges.world.level.block;

import fuzs.permanentsponges.init.ModRegistry;
import fuzs.permanentsponges.world.level.block.sponge.*;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

@SuppressWarnings("deprecation")
public class PermanentSpongeBlock extends Block {
    private final SpongeMaterial spongeMaterial;

    public PermanentSpongeBlock(Properties properties, SpongeMaterial spongeMaterial) {
        super(properties);
        this.spongeMaterial = spongeMaterial;
    }

    @Override
    public void onPlace(BlockState newState, Level level, BlockPos pos, BlockState oldState, boolean p_56815_) {
        if (!oldState.is(newState.getBlock())) {
            int distance = this.spongeMaterial.getBlockDistance();
            boolean vanish = this.spongeMaterial.shouldDestroyTouchingHot();
            AbstractSpongeTask task = new SetSpongeTask((ServerLevel) level, pos, distance, this.getReplacementBlock(), vanish);
            SpongeScheduler.INSTANCE.scheduleTask(task);
        }
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        int distance = this.spongeMaterial.getBlockDistance();
        boolean vanish = this.spongeMaterial.shouldDestroyTouchingHot();
        AbstractSpongeTask task = new SetSpongeTask(level, pos, distance, this.getReplacementBlock(), vanish);
        SpongeScheduler.INSTANCE.scheduleTask(task);
    }

    @Override
    public void onRemove(BlockState oldState, Level level, BlockPos pos, BlockState newState, boolean p_50941_) {
        if (!oldState.is(newState.getBlock())) {
            int distance = this.spongeMaterial.getBlockDistance();
            AbstractSpongeTask task = new RemoveSpongeTask((ServerLevel) level, pos, distance, this.getReplacementBlock());
            SpongeScheduler.INSTANCE.scheduleTask(task);
            super.onRemove(oldState, level, pos, newState, p_50941_);
        }
    }

    protected Block getReplacementBlock() {
        return ModRegistry.SPONGE_AIR_BLOCK.get();
    }
}