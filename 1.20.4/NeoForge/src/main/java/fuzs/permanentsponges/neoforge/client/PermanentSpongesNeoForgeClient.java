package fuzs.permanentsponges.neoforge.client;

import fuzs.permanentsponges.PermanentSponges;
import fuzs.permanentsponges.client.PermanentSpongesClient;
import fuzs.permanentsponges.data.client.ModLanguageProvider;
import fuzs.permanentsponges.data.client.ModModelProvider;
import fuzs.puzzleslib.api.client.core.v1.ClientModConstructor;
import fuzs.puzzleslib.neoforge.api.data.v2.core.DataProviderHelper;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLConstructModEvent;

@Mod.EventBusSubscriber(modid = PermanentSponges.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class PermanentSpongesNeoForgeClient {

    @SubscribeEvent
    public static void onConstructMod(final FMLConstructModEvent evt) {
        ClientModConstructor.construct(PermanentSponges.MOD_ID, PermanentSpongesClient::new);
        DataProviderHelper.registerDataProviders(PermanentSponges.MOD_ID,
                ModModelProvider::new,
                ModLanguageProvider::new
        );
    }
}
