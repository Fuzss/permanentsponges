package fuzs.permanentsponges.neoforge;

import fuzs.permanentsponges.PermanentSponges;
import fuzs.permanentsponges.data.ModBlockTagsProvider;
import fuzs.permanentsponges.data.ModRecipeProvider;
import fuzs.permanentsponges.data.client.ModLanguageProvider;
import fuzs.permanentsponges.data.client.ModModelProvider;
import fuzs.puzzleslib.api.core.v1.ModConstructor;
import fuzs.puzzleslib.neoforge.api.data.v2.core.DataProviderHelper;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLConstructModEvent;

@Mod(PermanentSponges.MOD_ID)
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class PermanentSpongesNeoForge {

    @SubscribeEvent
    public static void onConstructMod(final FMLConstructModEvent evt) {
        ModConstructor.construct(PermanentSponges.MOD_ID, PermanentSponges::new);
        DataProviderHelper.registerDataProviders(PermanentSponges.MOD_ID, ModModelProvider::new, ModLanguageProvider::new, ModBlockTagsProvider::new, ModRecipeProvider::new);
    }
}
