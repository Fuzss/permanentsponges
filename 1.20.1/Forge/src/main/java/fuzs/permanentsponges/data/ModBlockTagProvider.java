package fuzs.permanentsponges.data;

import fuzs.permanentsponges.init.ModRegistry;
import fuzs.puzzleslib.api.data.v2.AbstractTagProvider;
import fuzs.puzzleslib.api.data.v2.core.DataProviderContext;
import net.minecraft.core.HolderLookup;
import net.minecraft.tags.BlockTags;

public class ModBlockTagProvider extends AbstractTagProvider.Blocks {

    public ModBlockTagProvider(DataProviderContext context) {
        super(context);
    }

    @Override
    public void addTags(HolderLookup.Provider provider) {
        this.tag(BlockTags.MINEABLE_WITH_HOE).add(ModRegistry.AQUEOUS_SPONGE_BLOCK.value(), ModRegistry.MAGMATIC_SPONGE_BLOCK.value());
        this.tag(ModRegistry.PERMANENT_SPONGES_BLOCK_TAG).add(ModRegistry.AQUEOUS_SPONGE_BLOCK.value(), ModRegistry.MAGMATIC_SPONGE_BLOCK.value());
    }
}
