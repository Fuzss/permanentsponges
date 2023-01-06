package fuzs.permanentsponges.world.level.block;

import fuzs.permanentsponges.init.ModRegistry;
import fuzs.permanentsponges.core.sponge.AbstractSpongeTask;
import fuzs.permanentsponges.core.sponge.RemoveSpongeTask;
import fuzs.permanentsponges.core.sponge.SetSpongeTask;
import fuzs.permanentsponges.core.sponge.SpongeScheduler;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

@SuppressWarnings("deprecation")
public class PermanentSpongeBlock extends Block {
    private final boolean vanish;

    public PermanentSpongeBlock(Properties properties, boolean vanish) {
        super(properties);
        this.vanish = vanish;
    }

    @Override
    public void onPlace(BlockState newState, Level level, BlockPos pos, BlockState oldState, boolean p_56815_) {
        if (!oldState.is(newState.getBlock())) {
            AbstractSpongeTask task = SetSpongeTask.createSetTask((ServerLevel) level, ModRegistry.SPONGE_AIR_BLOCK.get(), pos, 6, this.vanish);
            SpongeScheduler.INSTANCE.scheduleTask(task);
        }
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        AbstractSpongeTask task = SetSpongeTask.createSetTask(level, ModRegistry.SPONGE_AIR_BLOCK.get(), pos, 6, this.vanish);
        SpongeScheduler.INSTANCE.scheduleTask(task);
    }

    @Override
    public void onRemove(BlockState oldState, Level level, BlockPos pos, BlockState newState, boolean p_50941_) {
        if (!oldState.is(newState.getBlock())) {
            AbstractSpongeTask task = RemoveSpongeTask.createRemoveTask((ServerLevel) level, pos, 6);
            SpongeScheduler.INSTANCE.scheduleTask(task);
            super.onRemove(oldState, level, pos, newState, p_50941_);
        }
    }
}