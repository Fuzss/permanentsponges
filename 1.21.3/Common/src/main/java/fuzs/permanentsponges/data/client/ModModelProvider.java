package fuzs.permanentsponges.data.client;

import fuzs.permanentsponges.init.ModRegistry;
import fuzs.puzzleslib.api.client.data.v2.AbstractModelProvider;
import fuzs.puzzleslib.api.data.v2.core.DataProviderContext;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.ItemModelGenerators;
import net.minecraft.data.models.model.ModelTemplates;

public class ModModelProvider extends AbstractModelProvider {

    public ModModelProvider(DataProviderContext context) {
        super(context);
    }

    @Override
    public void addBlockModels(BlockModelGenerators builder) {
        builder.createTrivialCube(ModRegistry.AQUEOUS_SPONGE_BLOCK.value());
        builder.createTrivialCube(ModRegistry.MAGMATIC_SPONGE_BLOCK.value());
    }

    @Override
    public void addItemModels(ItemModelGenerators builder) {
        builder.generateFlatItem(ModRegistry.AQUEOUS_SPONGE_ON_A_STICK_ITEM.value(), ModelTemplates.FLAT_HANDHELD_ITEM);
        builder.generateFlatItem(ModRegistry.MAGMATIC_SPONGE_ON_A_STICK_ITEM.value(), ModelTemplates.FLAT_HANDHELD_ITEM);
    }
}
