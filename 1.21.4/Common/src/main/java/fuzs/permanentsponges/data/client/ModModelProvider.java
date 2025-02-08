package fuzs.permanentsponges.data.client;

import fuzs.permanentsponges.init.ModRegistry;
import fuzs.puzzleslib.api.client.data.v2.AbstractModelProvider;
import fuzs.puzzleslib.api.data.v2.core.DataProviderContext;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.model.ModelTemplates;

public class ModModelProvider extends AbstractModelProvider {

    public ModModelProvider(DataProviderContext context) {
        super(context);
    }

    @Override
    public void addBlockModels(BlockModelGenerators blockModelGenerators) {
        blockModelGenerators.createTrivialCube(ModRegistry.AQUEOUS_SPONGE_BLOCK.value());
        blockModelGenerators.createTrivialCube(ModRegistry.MAGMATIC_SPONGE_BLOCK.value());
    }

    @Override
    public void addItemModels(ItemModelGenerators itemModelGenerators) {
        itemModelGenerators.generateFlatItem(ModRegistry.AQUEOUS_SPONGE_ON_A_STICK_ITEM.value(),
                ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModelGenerators.generateFlatItem(ModRegistry.MAGMATIC_SPONGE_ON_A_STICK_ITEM.value(),
                ModelTemplates.FLAT_HANDHELD_ITEM);
    }
}
