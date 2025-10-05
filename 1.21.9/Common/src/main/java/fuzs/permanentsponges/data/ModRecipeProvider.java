package fuzs.permanentsponges.data;

import fuzs.permanentsponges.init.ModRegistry;
import fuzs.puzzleslib.api.data.v2.AbstractRecipeProvider;
import fuzs.puzzleslib.api.data.v2.core.DataProviderContext;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;

public class ModRecipeProvider extends AbstractRecipeProvider {

    public ModRecipeProvider(DataProviderContext context) {
        super(context);
    }

    @Override
    public void addRecipes(RecipeOutput recipeOutput) {
        ShapedRecipeBuilder.shaped(this.items(),
                        RecipeCategory.BUILDING_BLOCKS,
                        ModRegistry.AQUEOUS_SPONGE_BLOCK.value())
                .define('@', ItemTags.WOOL)
                .define('#', Items.SLIME_BALL)
                .pattern(" # ")
                .pattern("#@#")
                .pattern(" # ")
                .unlockedBy(getHasName(Items.SLIME_BALL), this.has(Items.SLIME_BALL))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(this.items(),
                        RecipeCategory.BUILDING_BLOCKS,
                        ModRegistry.MAGMATIC_SPONGE_BLOCK.value())
                .define('@', ItemTags.WOOL)
                .define('#', Items.MAGMA_CREAM)
                .pattern(" # ")
                .pattern("#@#")
                .pattern(" # ")
                .unlockedBy(getHasName(Items.MAGMA_CREAM), this.has(Items.MAGMA_CREAM))
                .save(recipeOutput);
        ShapelessRecipeBuilder.shapeless(this.items(),
                        RecipeCategory.TOOLS,
                        ModRegistry.HANDHELD_AQUEOUS_SPONGE_ITEM.value())
                .requires(Items.STICK)
                .requires(ModRegistry.AQUEOUS_SPONGE_BLOCK.value())
                .unlockedBy(getHasName(ModRegistry.AQUEOUS_SPONGE_BLOCK.value()),
                        this.has(ModRegistry.AQUEOUS_SPONGE_BLOCK.value()))
                .save(recipeOutput);
        ShapelessRecipeBuilder.shapeless(this.items(),
                        RecipeCategory.TOOLS,
                        ModRegistry.HANDHELD_MAGMATIC_SPONGE_ITEM.value())
                .requires(Items.STICK)
                .requires(ModRegistry.MAGMATIC_SPONGE_BLOCK.value())
                .unlockedBy(getHasName(ModRegistry.MAGMATIC_SPONGE_BLOCK.value()),
                        this.has(ModRegistry.MAGMATIC_SPONGE_BLOCK.value()))
                .save(recipeOutput);
    }
}
