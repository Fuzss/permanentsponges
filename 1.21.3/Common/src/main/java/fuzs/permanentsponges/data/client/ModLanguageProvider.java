package fuzs.permanentsponges.data.client;

import fuzs.permanentsponges.PermanentSponges;
import fuzs.permanentsponges.init.ModRegistry;
import fuzs.puzzleslib.api.client.data.v2.AbstractLanguageProvider;
import fuzs.puzzleslib.api.data.v2.core.DataProviderContext;

public class ModLanguageProvider extends AbstractLanguageProvider {

    public ModLanguageProvider(DataProviderContext context) {
        super(context);
    }

    @Override
    public void addTranslations(TranslationBuilder builder) {
        builder.addCreativeModeTab(PermanentSponges.MOD_ID, PermanentSponges.MOD_NAME);
        builder.add(ModRegistry.AQUEOUS_SPONGE_BLOCK.value(), "Aqueous Sponge");
        builder.add(ModRegistry.MAGMATIC_SPONGE_BLOCK.value(), "Magmatic Sponge");
        builder.add(ModRegistry.AQUEOUS_SPONGE_ON_A_STICK_ITEM.value(), "Aqueous Sponge On A Stick");
        builder.add(ModRegistry.MAGMATIC_SPONGE_ON_A_STICK_ITEM.value(), "Magmatic Sponge On A Stick");
    }
}
