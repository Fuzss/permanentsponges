package fuzs.permanentsponges.data;

import fuzs.permanentsponges.PermanentSponges;
import fuzs.permanentsponges.init.ModRegistry;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.LanguageProvider;

public class ModLanguageProvider extends LanguageProvider {

    public ModLanguageProvider(DataGenerator gen, String modId) {
        super(gen, modId, "en_us");
    }

    @Override
    protected void addTranslations() {
        this.add("itemGroup.permanentsponges.main", PermanentSponges.MOD_NAME);
        this.add(ModRegistry.SPONGE_AIR_BLOCK.get(), "Sponge Air");
        this.add(ModRegistry.AQUEOUS_SPONGE_BLOCK.get(), "Aqueous Sponge");
        this.add(ModRegistry.MAGMATIC_SPONGE_BLOCK.get(), "Magmatic Sponge");
        this.add(ModRegistry.AQUEOUS_SPONGE_ON_A_STICK.get(), "Aqueous Sponge On A Stick");
        this.add(ModRegistry.MAGMATIC_SPONGE_ON_A_STICK_ITEM.get(), "Magmatic Sponge On A Stick");
    }
}
