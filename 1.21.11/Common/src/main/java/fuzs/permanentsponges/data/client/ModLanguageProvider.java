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
        builder.add(ModRegistry.CREATIVE_MODE_TAB.value(), PermanentSponges.MOD_NAME);
        builder.add(ModRegistry.AQUEOUS_SPONGE_BLOCK.value(), "Aqueous Sponge");
        builder.add(ModRegistry.MAGMATIC_SPONGE_BLOCK.value(), "Magmatic Sponge");
        builder.add(ModRegistry.HANDHELD_AQUEOUS_SPONGE_ITEM.value(), "Handheld Aqueous Sponge");
        builder.add(ModRegistry.HANDHELD_MAGMATIC_SPONGE_ITEM.value(), "Handheld Magmatic Sponge");
    }
}
