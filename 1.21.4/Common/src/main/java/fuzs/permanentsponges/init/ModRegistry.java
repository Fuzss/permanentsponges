package fuzs.permanentsponges.init;

import fuzs.permanentsponges.PermanentSponges;
import fuzs.permanentsponges.world.item.SpongeOnAStickItem;
import fuzs.permanentsponges.world.level.block.PermanentSpongeBlock;
import fuzs.permanentsponges.world.level.block.SpongeMaterial;
import fuzs.puzzleslib.api.init.v3.registry.RegistryManager;
import fuzs.puzzleslib.api.init.v3.tags.TagFactory;
import net.minecraft.core.Holder;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;

public class ModRegistry {
    static final RegistryManager REGISTRIES = RegistryManager.from(PermanentSponges.MOD_ID);
    public static final Holder.Reference<Block> AQUEOUS_SPONGE_BLOCK = REGISTRIES.registerBlock("aqueous_sponge",
            (BlockBehaviour.Properties properties) -> new PermanentSpongeBlock(SpongeMaterial.AQUATIC, properties),
            () -> BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_YELLOW)
                    .strength(0.6F)
                    .sound(SoundType.GRASS)
                    .randomTicks());
    public static final Holder.Reference<Block> MAGMATIC_SPONGE_BLOCK = REGISTRIES.registerBlock("magmatic_sponge",
            (BlockBehaviour.Properties properties) -> new PermanentSpongeBlock(SpongeMaterial.MAGMATIC, properties),
            () -> BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_YELLOW)
                    .strength(0.6F)
                    .sound(SoundType.GRASS)
                    .randomTicks());
    public static final Holder.Reference<Item> AQUEOUS_SPONGE_ITEM = REGISTRIES.registerBlockItem(AQUEOUS_SPONGE_BLOCK);
    public static final Holder.Reference<Item> MAGMATIC_SPONGE_ITEM = REGISTRIES.registerBlockItem(MAGMATIC_SPONGE_BLOCK);
    public static final Holder.Reference<Item> AQUEOUS_SPONGE_ON_A_STICK_ITEM = REGISTRIES.registerItem(
            "aqueous_sponge_on_a_stick",
            (Item.Properties properties) -> new SpongeOnAStickItem(SpongeMaterial.AQUATIC, properties),
            () -> new Item.Properties().durability(65));
    public static final Holder.Reference<Item> MAGMATIC_SPONGE_ON_A_STICK_ITEM = REGISTRIES.registerItem(
            "magmatic_sponge_on_a_stick",
            (Item.Properties properties) -> new SpongeOnAStickItem(SpongeMaterial.MAGMATIC, properties),
            () -> new Item.Properties().durability(129));
    public static final Holder.Reference<PoiType> AQUEOUS_SPONGE_POI_TYPE = REGISTRIES.registerPoiType("aqueous_sponge",
            AQUEOUS_SPONGE_BLOCK);
    public static final Holder.Reference<PoiType> MAGMATIC_SPONGE_POI_TYPE = REGISTRIES.registerPoiType(
            "magmatic_sponge",
            MAGMATIC_SPONGE_BLOCK);

    static final TagFactory TAGS = TagFactory.make(PermanentSponges.MOD_ID);
    public static final TagKey<Block> PERMANENT_SPONGES_BLOCK_TAG = TAGS.registerBlockTag("permanent_sponges");

    public static void bootstrap() {
        // NO-OP
    }
}
