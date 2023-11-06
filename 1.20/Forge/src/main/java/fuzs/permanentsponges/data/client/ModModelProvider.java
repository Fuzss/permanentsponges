package fuzs.permanentsponges.data.client;

import fuzs.permanentsponges.init.ModRegistry;
import fuzs.puzzleslib.api.data.v1.AbstractModelProvider;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.data.event.GatherDataEvent;

public class ModModelProvider extends AbstractModelProvider {

    public ModModelProvider(GatherDataEvent evt, String modId) {
        super(evt, modId);
    }

    @Override
    protected void registerStatesAndModels() {
        this.simpleBlock(ModRegistry.AQUEOUS_SPONGE_BLOCK.get());
        this.simpleBlock(ModRegistry.MAGMATIC_SPONGE_BLOCK.get());
        this.simpleBlockItem(ModRegistry.AQUEOUS_SPONGE_BLOCK.get());
        this.simpleBlockItem(ModRegistry.MAGMATIC_SPONGE_BLOCK.get());
        this.itemModels().basicItem(ModRegistry.AQUEOUS_SPONGE_ON_A_STICK_ITEM.get());
        this.itemModels().basicItem(ModRegistry.MAGMATIC_SPONGE_ON_A_STICK_ITEM.get());
    }

    private void emptyBlock(Block block) {
        this.getVariantBuilder(block).partialState().setModels(new ConfiguredModel(this.models().getBuilder(this.name(block))));
    }

    private void simpleBlockItem(Block block) {
        this.simpleBlockItem(block, this.models().getExistingFile(this.key(block)));
    }
}
