package fuzs.permanentsponges.data;

import fuzs.permanentsponges.init.ModRegistry;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;

public class ModBlockStateProvider extends BlockStateProvider {

    public ModBlockStateProvider(DataGenerator gen, String modId, ExistingFileHelper exFileHelper) {
        super(gen, modId, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        this.emptyBlock(ModRegistry.SPONGE_AIR_BLOCK.get());
        this.simpleBlock(ModRegistry.AQUEOUS_SPONGE_BLOCK.get());
        this.simpleBlock(ModRegistry.MAGMATIC_SPONGE_BLOCK.get());
        this.simpleBlockItem(ModRegistry.SPONGE_AIR_BLOCK.get());
        this.simpleBlockItem(ModRegistry.AQUEOUS_SPONGE_BLOCK.get());
        this.simpleBlockItem(ModRegistry.MAGMATIC_SPONGE_BLOCK.get());
        this.itemModels().basicItem(ModRegistry.AQUEOUS_SPONGE_ON_A_STICK.get());
        this.itemModels().basicItem(ModRegistry.MAGMATIC_SPONGE_ON_A_STICK_ITEM.get());
    }

    private ResourceLocation key(Block block) {
        return ForgeRegistries.BLOCKS.getKey(block);
    }

    private String name(Block block) {
        return this.key(block).getPath();
    }

    private void emptyBlock(Block block) {
        this.getVariantBuilder(block).partialState().setModels(new ConfiguredModel(this.models().getBuilder(this.name(block))));
    }

    private void simpleBlockItem(Block block) {
        this.simpleBlockItem(block, this.models().getExistingFile(this.key(block)));
    }
}
