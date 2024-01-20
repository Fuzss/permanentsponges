package fuzs.permanentsponges.world.level.block;

import fuzs.permanentsponges.util.LiquidAbsorptionHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import org.jetbrains.annotations.Nullable;

import java.util.List;

@SuppressWarnings("deprecation")
public class PermanentSpongeBlock extends Block {
    private final SpongeMaterial spongeMaterial;

    public PermanentSpongeBlock(Properties properties, SpongeMaterial spongeMaterial) {
        super(properties);
        this.spongeMaterial = spongeMaterial;
    }

    @Override
    public void onPlace(BlockState newState, Level level, BlockPos pos, BlockState oldState, boolean p_56815_) {
        if (level instanceof ServerLevel serverLevel && !oldState.is(newState.getBlock())) {
            removeAllLiquid(this.spongeMaterial, serverLevel, pos, false, this.spongeMaterial.getPoiType());
        }
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        removeAllLiquid(this.spongeMaterial, level, pos, false);
    }

    public static boolean removeAllLiquid(SpongeMaterial spongeMaterial, ServerLevel level, BlockPos pos, boolean fromStick) {
        return removeAllLiquid(spongeMaterial, level, pos, fromStick, null);
    }

    public static boolean removeAllLiquid(SpongeMaterial spongeMaterial, ServerLevel level, BlockPos pos, boolean fromStick, @Nullable Holder.Reference<PoiType> poiType) {
        int spongeRadius = fromStick ? spongeMaterial.getStickDistance() : spongeMaterial.getBlockDistance();
        boolean destroySource = !fromStick && spongeMaterial.shouldDestroyTouchingHot();
        return LiquidAbsorptionHelper.removeAllLiquid(level, pos, spongeRadius, destroySource, poiType);
    }

    @Override
    public void onRemove(BlockState oldState, Level level, BlockPos pos, BlockState newState, boolean movedByPiston) {
        super.onRemove(oldState, level, pos, newState, movedByPiston);
        if (!oldState.is(newState.getBlock())) {
            int spongeRadius = this.spongeMaterial.getBlockDistance() + 1;
            List<BlockPos> positions = LiquidAbsorptionHelper.getSpongeRadius(spongeRadius);
            for (int i = positions.size() - 1, j = 0; i >= 0; i--, j++) {
                BlockPos blockPos = positions.get(i);
                if (Math.abs(blockPos.getX()) == spongeRadius || Math.abs(blockPos.getY()) == spongeRadius || Math.abs(blockPos.getZ()) == spongeRadius) {
                    blockPos = blockPos.offset(pos);
                    level.scheduleTick(blockPos, level.getFluidState(blockPos).getType(), 1);
                } else {
                    // we are able to break early as everything is sorted via maximum norm
                    break;
                }
            }
        }
    }
}