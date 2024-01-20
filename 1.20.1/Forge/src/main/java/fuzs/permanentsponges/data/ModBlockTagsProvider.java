package fuzs.permanentsponges.data;

import fuzs.permanentsponges.init.ModRegistry;
import fuzs.puzzleslib.api.data.v1.AbstractTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraftforge.data.event.GatherDataEvent;

public class ModBlockTagsProvider extends AbstractTagProvider.Blocks {

    public ModBlockTagsProvider(GatherDataEvent evt, String modId) {
        super(evt, modId);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        this.tag(ModRegistry.PERMANENT_SPONGES_BLOCK_TAG).add(ModRegistry.AQUEOUS_SPONGE_BLOCK.value(), ModRegistry.MAGMATIC_SPONGE_BLOCK.value());
    }
}
