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
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModRegistry.AQUEOUS_SPONGE_BLOCK.value())
                .define('W', ItemTags.WOOL)
                .define('#', Items.SLIME_BALL)
                .pattern(" # ")
                .pattern("#W#")
                .pattern(" # ")
                .unlockedBy("has_slime_ball", has(Items.SLIME_BALL))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModRegistry.MAGMATIC_SPONGE_BLOCK.value())
                .define('W', ItemTags.WOOL)
                .define('#', Items.MAGMA_CREAM)
                .pattern(" # ")
                .pattern("#W#")
                .pattern(" # ")
                .unlockedBy("has_magma_cream", has(Items.MAGMA_CREAM))
                .save(recipeOutput);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.TOOLS, ModRegistry.AQUEOUS_SPONGE_ON_A_STICK_ITEM.value())
                .requires(Items.STICK)
                .requires(ModRegistry.AQUEOUS_SPONGE_BLOCK.value())
                .unlockedBy("has_aqueous_sponge", has(ModRegistry.AQUEOUS_SPONGE_BLOCK.value()))
                .save(recipeOutput);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.TOOLS, ModRegistry.MAGMATIC_SPONGE_ON_A_STICK_ITEM.value())
                .requires(Items.STICK)
                .requires(ModRegistry.MAGMATIC_SPONGE_BLOCK.value())
                .unlockedBy("has_magmatic_sponge", has(ModRegistry.MAGMATIC_SPONGE_BLOCK.value()))
                .save(recipeOutput);
    }
}
