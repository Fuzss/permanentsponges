package fuzs.permanentsponges.data;

import fuzs.permanentsponges.init.ModRegistry;
import fuzs.puzzleslib.api.data.v1.AbstractRecipeProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;

import java.util.function.Consumer;

public class ModRecipeProvider extends AbstractRecipeProvider {

    public ModRecipeProvider(PackOutput packOutput) {
        super(packOutput);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> p_176532_) {
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModRegistry.AQUEOUS_SPONGE_BLOCK.get())
                .define('W', ItemTags.WOOL)
                .define('#', Items.SLIME_BALL)
                .pattern(" # ")
                .pattern("#W#")
                .pattern(" # ")
                .unlockedBy("has_slime_ball", has(Items.SLIME_BALL))
                .save(p_176532_);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModRegistry.MAGMATIC_SPONGE_BLOCK.get())
                .define('W', ItemTags.WOOL)
                .define('#', Items.MAGMA_CREAM)
                .pattern(" # ")
                .pattern("#W#")
                .pattern(" # ")
                .unlockedBy("has_magma_cream", has(Items.MAGMA_CREAM))
                .save(p_176532_);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.TOOLS, ModRegistry.AQUEOUS_SPONGE_ON_A_STICK_ITEM.get())
                .requires(Items.STICK)
                .requires(ModRegistry.AQUEOUS_SPONGE_BLOCK.get())
                .unlockedBy("has_aqueous_sponge", has(ModRegistry.AQUEOUS_SPONGE_BLOCK.get()))
                .save(p_176532_);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.TOOLS, ModRegistry.MAGMATIC_SPONGE_ON_A_STICK_ITEM.get())
                .requires(Items.STICK)
                .requires(ModRegistry.MAGMATIC_SPONGE_BLOCK.get())
                .unlockedBy("has_magmatic_sponge", has(ModRegistry.MAGMATIC_SPONGE_BLOCK.get()))
                .save(p_176532_);
    }
}
