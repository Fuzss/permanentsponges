package fuzs.permanentsponges.data.client;

import fuzs.permanentsponges.PermanentSponges;
import fuzs.permanentsponges.init.ModRegistry;
import fuzs.puzzleslib.api.data.v1.AbstractLanguageProvider;
import net.minecraftforge.data.event.GatherDataEvent;

public class ModLanguageProvider extends AbstractLanguageProvider {

    public ModLanguageProvider(GatherDataEvent evt, String modId) {
        super(evt, modId);
    }

    @Override
    protected void addTranslations() {
        this.addCreativeModeTab(PermanentSponges.MOD_NAME);
        this.add(ModRegistry.AQUEOUS_SPONGE_BLOCK.get(), "Aqueous Sponge");
        this.add(ModRegistry.MAGMATIC_SPONGE_BLOCK.get(), "Magmatic Sponge");
        this.add(ModRegistry.AQUEOUS_SPONGE_ON_A_STICK_ITEM.get(), "Aqueous Sponge On A Stick");
        this.add(ModRegistry.MAGMATIC_SPONGE_ON_A_STICK_ITEM.get(), "Magmatic Sponge On A Stick");
    }
}
