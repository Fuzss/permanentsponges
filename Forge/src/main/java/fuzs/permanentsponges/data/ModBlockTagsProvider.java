package fuzs.permanentsponges.data;

import fuzs.permanentsponges.init.ModRegistry;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

public class ModBlockTagsProvider extends BlockTagsProvider {

    public ModBlockTagsProvider(DataGenerator dataGenerator, String modId, @Nullable ExistingFileHelper existingFileHelper) {
        super(dataGenerator, modId, existingFileHelper);
    }

    @Override
    protected void addTags() {
        this.tag(ModRegistry.SPONGE_TAG).add(ModRegistry.AQUEOUS_SPONGE_BLOCK.get(), ModRegistry.MAGMATIC_SPONGE_BLOCK.get());
    }
}