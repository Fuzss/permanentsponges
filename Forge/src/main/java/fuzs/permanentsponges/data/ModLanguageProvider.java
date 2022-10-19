package fuzs.permanentsponges.data;

import fuzs.permanentsponges.init.ModRegistry;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.LanguageProvider;

public class ModLanguageProvider extends LanguageProvider {

    public ModLanguageProvider(DataGenerator gen, String modId) {
        super(gen, modId, "en_us");
    }

    @Override
    protected void addTranslations() {
        this.add(ModRegistry.AQUEOUS_SPONGE_BLOCK.get(), "Aqueous Sponge");
        this.add(ModRegistry.MAGMATIC_SPONGE_BLOCK.get(), "Magmatic Sponge");
    }
}
