package fuzs.permanentsponges.world.level.block;

import fuzs.permanentsponges.util.LiquidAbsorptionHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.server.TickTask;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

public class PermanentSpongeBlock extends Block {
    private final SpongeMaterial spongeMaterial;

    public PermanentSpongeBlock(SpongeMaterial spongeMaterial, Properties properties) {
        super(properties);
        this.spongeMaterial = spongeMaterial;
    }

    @Override
    public void onPlace(BlockState newState, Level level, BlockPos pos, BlockState oldState, boolean movedByPiston) {
        if (level instanceof ServerLevel serverLevel && !oldState.is(newState.getBlock())) {
            // the poi type is added after this is called which will log an error when trying to remove the record where there was none added yet
            serverLevel.getServer().schedule(new TickTask(serverLevel.getServer().getTickCount(), () -> {
                removeAllLiquid(this.spongeMaterial, serverLevel, pos, false);
            }));
        }
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        removeAllLiquid(this.spongeMaterial, level, pos, false);
    }

    public static boolean removeAllLiquid(SpongeMaterial spongeMaterial, ServerLevel level, BlockPos pos, boolean fromStick) {
        int spongeRadius = fromStick ? spongeMaterial.getStickDistance() : spongeMaterial.getBlockDistance();
        boolean destroySource = !fromStick && spongeMaterial.shouldDestroyTouchingHot();
        return LiquidAbsorptionHelper.removeAllLiquid(level, pos, spongeRadius, destroySource);
    }

    @Override
    protected void affectNeighborsAfterRemoval(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, boolean movedByPiston) {
        super.affectNeighborsAfterRemoval(blockState, serverLevel, blockPos, movedByPiston);
        int spongeRadius = this.spongeMaterial.getBlockDistance() + 1;
        List<BlockPos> positions = LiquidAbsorptionHelper.getSpongeRadius(spongeRadius);
        for (int i = positions.size() - 1, j = 0; i >= 0; i--, j++) {
            BlockPos blockPosInRadius = positions.get(i);
            if (Math.abs(blockPosInRadius.getX()) == spongeRadius ||
                    Math.abs(blockPosInRadius.getY()) == spongeRadius ||
                    Math.abs(blockPosInRadius.getZ()) == spongeRadius) {
                blockPosInRadius = blockPosInRadius.offset(blockPos);
                serverLevel.scheduleTick(blockPosInRadius, serverLevel.getFluidState(blockPosInRadius).getType(), 1);
            } else {
                // we are able to break early as everything is sorted via maximum norm
                break;
            }
        }
    }
}