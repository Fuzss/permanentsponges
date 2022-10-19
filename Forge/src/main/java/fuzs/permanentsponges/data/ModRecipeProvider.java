package fuzs.permanentsponges.data;

import fuzs.permanentsponges.init.ModRegistry;
import fuzs.wateringcan.init.ModRegistry;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.Tags;

import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider {
    public ModRecipeProvider(DataGenerator p_125973_) {
        super(p_125973_);
    }

    @Override
    protected void buildCraftingRecipes(Consumer<FinishedRecipe> p_176532_) {
        ShapedRecipeBuilder.shaped(ModRegistry.WATERING_CAN_ITEM.get())
                .group("watering_can")
                .define('S', ModRegistry.SPONGE_TAG)
                .define('B', Items.BUCKET)
                .define('M', Items.BONE_MEAL)
                .pattern(" # ")
                .pattern("#S#")
                .pattern(" # ")
                .unlockedBy("has_bucket", has(Items.BUCKET))
                .save(p_176532_);
    }
}
