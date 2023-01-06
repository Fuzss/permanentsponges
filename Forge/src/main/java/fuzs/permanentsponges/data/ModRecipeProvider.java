package fuzs.permanentsponges.data;

import fuzs.permanentsponges.init.ModRegistry;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;

import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider {

    public ModRecipeProvider(DataGenerator p_125973_) {
        super(p_125973_);
    }

    @Override
    protected void buildCraftingRecipes(Consumer<FinishedRecipe> p_176532_) {
        ShapedRecipeBuilder.shaped(ModRegistry.AQUEOUS_SPONGE_BLOCK.get())
                .define('W', ItemTags.WOOL)
                .define('#', Items.SLIME_BALL)
                .pattern(" # ")
                .pattern("#W#")
                .pattern(" # ")
                .unlockedBy("has_slime_ball", has(Items.SLIME_BALL))
                .save(p_176532_);
        ShapedRecipeBuilder.shaped(ModRegistry.MAGMATIC_SPONGE_BLOCK.get())
                .define('W', ItemTags.WOOL)
                .define('#', Items.MAGMA_CREAM)
                .pattern(" # ")
                .pattern("#W#")
                .pattern(" # ")
                .unlockedBy("has_magma_cream", has(Items.MAGMA_CREAM))
                .save(p_176532_);
        ShapelessRecipeBuilder.shapeless(ModRegistry.AQUEOUS_SPONGE_ON_A_STICK.get())
                .requires(Items.STICK)
                .requires(ModRegistry.AQUEOUS_SPONGE_BLOCK.get())
                .unlockedBy("has_aqueous_sponge", has(ModRegistry.AQUEOUS_SPONGE_BLOCK.get()))
                .save(p_176532_);
        ShapelessRecipeBuilder.shapeless(ModRegistry.MAGMATIC_SPONGE_ON_A_STICK_ITEM.get())
                .requires(Items.STICK)
                .requires(ModRegistry.MAGMATIC_SPONGE_BLOCK.get())
                .unlockedBy("has_magmatic_sponge", has(ModRegistry.MAGMATIC_SPONGE_BLOCK.get()))
                .save(p_176532_);
    }
}
