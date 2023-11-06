package fuzs.permanentsponges.core;

import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariantAttributes;
import net.minecraft.world.level.material.Fluid;

public class FabricAbstractions implements CommonAbstractions {

    @SuppressWarnings("UnstableApiUsage")
    @Override
    public int getFluidTemperature(Fluid fluid) {
        return FluidVariantAttributes.getTemperature(FluidVariant.of(fluid));
    }
}
