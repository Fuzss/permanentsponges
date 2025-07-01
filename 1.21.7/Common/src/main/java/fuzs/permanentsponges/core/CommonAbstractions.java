package fuzs.permanentsponges.core;

import fuzs.puzzleslib.api.core.v1.ServiceProviderHelper;
import net.minecraft.world.level.material.Fluid;

public interface CommonAbstractions {
    CommonAbstractions INSTANCE = ServiceProviderHelper.load(CommonAbstractions.class);

    int getFluidTemperature(Fluid fluid);

    default boolean isHotFluid(Fluid fluid) {
        return this.getFluidTemperature(fluid) >= 1000;
    }
}
