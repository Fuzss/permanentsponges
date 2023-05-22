package fuzs.permanentsponges.data;

import fuzs.permanentsponges.init.ModRegistry;
import fuzs.puzzleslib.api.data.v1.AbstractModelProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModModelProvider extends AbstractModelProvider {

    public ModModelProvider(PackOutput packOutput, String modId, ExistingFileHelper fileHelper) {
        super(packOutput, modId, fileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        this.emptyBlock(ModRegistry.SPONGE_AIR_BLOCK.get());
        this.simpleBlock(ModRegistry.AQUEOUS_SPONGE_BLOCK.get());
        this.simpleBlock(ModRegistry.MAGMATIC_SPONGE_BLOCK.get());
        this.simpleBlockItem(ModRegistry.SPONGE_AIR_BLOCK.get());
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
