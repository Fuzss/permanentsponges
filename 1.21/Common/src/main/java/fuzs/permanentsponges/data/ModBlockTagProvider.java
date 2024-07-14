package fuzs.permanentsponges.data;

import fuzs.permanentsponges.init.ModRegistry;
import fuzs.puzzleslib.api.data.v2.core.DataProviderContext;
import fuzs.puzzleslib.api.data.v2.tags.AbstractTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;

public class ModBlockTagProvider extends AbstractTagProvider<Block> {

    public ModBlockTagProvider(DataProviderContext context) {
        super(Registries.BLOCK, context);
    }

    @Override
    public void addTags(HolderLookup.Provider provider) {
        this.add(BlockTags.MINEABLE_WITH_HOE)
                .add(ModRegistry.AQUEOUS_SPONGE_BLOCK.value(), ModRegistry.MAGMATIC_SPONGE_BLOCK.value());
        this.add(ModRegistry.PERMANENT_SPONGES_BLOCK_TAG)
                .add(ModRegistry.AQUEOUS_SPONGE_BLOCK.value(), ModRegistry.MAGMATIC_SPONGE_BLOCK.value());
    }
}
