package fuzs.permanentsponges.mixin;

import fuzs.permanentsponges.util.LiquidAbsorptionHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FlowingFluid.class)
abstract class FlowingFluidMixin extends Fluid {

    @Inject(method = "canHoldSpecificFluid", at = @At("HEAD"), cancellable = true)
    private static void canHoldFluid(BlockGetter blockGetter, BlockPos blockPos, BlockState blockState, Fluid fluid, CallbackInfoReturnable<Boolean> callback) {
        if (blockGetter instanceof ServerLevel serverLevel &&
                LiquidAbsorptionHelper.tryPreventLiquidFromEntering(serverLevel, blockPos, fluid)) {
            callback.setReturnValue(false);
        }
    }
}
