package fuzs.permanentsponges.init;

import fuzs.permanentsponges.PermanentSponges;
import fuzs.permanentsponges.world.item.SpongeOnAStickItem;
import fuzs.permanentsponges.world.level.block.PermanentSpongeBlock;
import fuzs.permanentsponges.world.level.block.SpongeMaterial;
import fuzs.puzzleslib.api.init.v3.RegistryManager;
import fuzs.puzzleslib.api.init.v3.tags.BoundTagFactory;
import net.minecraft.core.Holder;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;

public class ModRegistry {
    static final RegistryManager REGISTRY = RegistryManager.from(PermanentSponges.MOD_ID);
    public static final Holder.Reference<Block> AQUEOUS_SPONGE_BLOCK = REGISTRY.registerBlock("aqueous_sponge", () -> new PermanentSpongeBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_YELLOW).strength(0.6F).sound(SoundType.GRASS).randomTicks(), SpongeMaterial.AQUATIC));
    public static final Holder.Reference<Block> MAGMATIC_SPONGE_BLOCK = REGISTRY.registerBlock("magmatic_sponge", () -> new PermanentSpongeBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_YELLOW).strength(0.6F).sound(SoundType.GRASS).randomTicks(), SpongeMaterial.MAGMATIC));
    public static final Holder.Reference<Item> AQUEOUS_SPONGE_ITEM = REGISTRY.registerBlockItem(AQUEOUS_SPONGE_BLOCK);
    public static final Holder.Reference<Item> MAGMATIC_SPONGE_ITEM = REGISTRY.registerBlockItem(MAGMATIC_SPONGE_BLOCK);
    public static final Holder.Reference<Item> AQUEOUS_SPONGE_ON_A_STICK_ITEM = REGISTRY.registerItem("aqueous_sponge_on_a_stick", () -> new SpongeOnAStickItem(new Item.Properties().durability(65), SpongeMaterial.AQUATIC));
    public static final Holder.Reference<Item> MAGMATIC_SPONGE_ON_A_STICK_ITEM = REGISTRY.registerItem("magmatic_sponge_on_a_stick", () -> new SpongeOnAStickItem(new Item.Properties().durability(129), SpongeMaterial.MAGMATIC));
    public static final Holder.Reference<PoiType> AQUEOUS_SPONGE_POI_TYPE = REGISTRY.registerPoiType("aqueous_sponge", () -> AQUEOUS_SPONGE_BLOCK.value());
    public static final Holder.Reference<PoiType> MAGMATIC_SPONGE_POI_TYPE = REGISTRY.registerPoiType("magmatic_sponge", () -> MAGMATIC_SPONGE_BLOCK.value());

    static final BoundTagFactory TAGS = BoundTagFactory.make(PermanentSponges.MOD_ID);
    public static final TagKey<Block> PERMANENT_SPONGES_BLOCK_TAG = TAGS.registerBlockTag("permanent_sponges");

    public static void touch() {

    }
}
