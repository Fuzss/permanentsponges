package fuzs.permanentsponges.core;

import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariantAttributeHandler;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariantAttributes;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;

public class FabricAbstractions implements CommonAbstractions {

    @SuppressWarnings("UnstableApiUsage")
    @Override
    public int getFluidTemperature(FluidState fluidState) {
        Fluid fluid = fluidState.getType();
        FluidVariantAttributeHandler attributeHandler = FluidVariantAttributes.getHandlerOrDefault(fluid);
        return attributeHandler.getTemperature(FluidVariant.of(fluid));
    }
}
