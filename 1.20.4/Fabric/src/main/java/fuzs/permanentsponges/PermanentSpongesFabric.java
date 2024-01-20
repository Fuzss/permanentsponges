package fuzs.permanentsponges;

import fuzs.puzzleslib.api.core.v1.ModConstructor;
import net.fabricmc.api.ModInitializer;

public class PermanentSpongesFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        ModConstructor.construct(PermanentSponges.MOD_ID, PermanentSponges::new);
    }
}
