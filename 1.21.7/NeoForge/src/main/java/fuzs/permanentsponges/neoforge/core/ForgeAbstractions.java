package fuzs.permanentsponges.neoforge.core;

import fuzs.permanentsponges.core.CommonAbstractions;
import net.minecraft.world.level.material.Fluid;

public class ForgeAbstractions implements CommonAbstractions {

    @Override
    public int getFluidTemperature(Fluid fluid) {
        return fluid.getFluidType().getTemperature();
    }
}
