package fuzs.permanentsponges.core;

import net.minecraft.world.level.material.FluidState;

public class ForgeAbstractions implements CommonAbstractions {

    @Override
    public int getFluidTemperature(FluidState fluidState) {
        return fluidState.getType().getFluidType().getTemperature();
    }
}
