package fuzs.permanentsponges.neoforge;

import fuzs.permanentsponges.PermanentSponges;
import fuzs.permanentsponges.data.ModBlockLootProvider;
import fuzs.permanentsponges.data.ModBlockTagProvider;
import fuzs.permanentsponges.data.ModRecipeProvider;
import fuzs.puzzleslib.api.core.v1.ModConstructor;
import fuzs.puzzleslib.neoforge.api.data.v2.core.DataProviderHelper;
import net.neoforged.fml.common.Mod;

@Mod(PermanentSponges.MOD_ID)
public class PermanentSpongesNeoForge {

    public PermanentSpongesNeoForge() {
        ModConstructor.construct(PermanentSponges.MOD_ID, PermanentSponges::new);
        DataProviderHelper.registerDataProviders(PermanentSponges.MOD_ID,
                ModBlockLootProvider::new,
                ModBlockTagProvider::new,
                ModRecipeProvider::new
        );
    }
}
