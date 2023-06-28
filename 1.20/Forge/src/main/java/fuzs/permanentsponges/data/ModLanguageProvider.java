package fuzs.permanentsponges.data;

import fuzs.permanentsponges.PermanentSponges;
import fuzs.permanentsponges.init.ModRegistry;
import fuzs.puzzleslib.api.data.v1.AbstractLanguageProvider;
import net.minecraft.data.PackOutput;

public class ModLanguageProvider extends AbstractLanguageProvider {

    public ModLanguageProvider(PackOutput packOutput, String modId) {
        super(packOutput, modId);
    }

    @Override
    protected void addTranslations() {
        this.addCreativeModeTab(PermanentSponges.MOD_NAME);
        this.add(ModRegistry.SPONGE_AIR_BLOCK.get(), "Sponge Air");
        this.add(ModRegistry.AQUEOUS_SPONGE_BLOCK.get(), "Aqueous Sponge");
        this.add(ModRegistry.MAGMATIC_SPONGE_BLOCK.get(), "Magmatic Sponge");
        this.add(ModRegistry.AQUEOUS_SPONGE_ON_A_STICK_ITEM.get(), "Aqueous Sponge On A Stick");
        this.add(ModRegistry.MAGMATIC_SPONGE_ON_A_STICK_ITEM.get(), "Magmatic Sponge On A Stick");
    }
}
