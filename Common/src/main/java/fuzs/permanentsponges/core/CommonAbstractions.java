package fuzs.permanentsponges.core;

import fuzs.puzzleslib.util.PuzzlesUtil;
import net.minecraft.world.level.material.FluidState;

public interface CommonAbstractions {
    CommonAbstractions INSTANCE = PuzzlesUtil.loadServiceProvider(CommonAbstractions.class);

    int getFluidTemperature(FluidState fluidState);
}
