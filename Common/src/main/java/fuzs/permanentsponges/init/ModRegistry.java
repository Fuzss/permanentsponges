package fuzs.permanentsponges.init;

import com.google.common.collect.ImmutableSet;
import fuzs.permanentsponges.PermanentSponges;
import fuzs.permanentsponges.world.item.SpongeOnAStickItem;
import fuzs.permanentsponges.world.level.block.PermanentSpongeBlock;
import fuzs.permanentsponges.world.level.block.sponge.SpongeMaterial;
import fuzs.puzzleslib.api.init.v2.RegistryManager;
import fuzs.puzzleslib.api.init.v2.RegistryReference;
import fuzs.puzzleslib.api.init.v2.builder.PoiTypeBuilder;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.AirBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;

import java.util.Set;
import java.util.stream.Stream;

public class ModRegistry {
    static final RegistryManager REGISTRY = RegistryManager.instant(PermanentSponges.MOD_ID);
    public static final RegistryReference<Block> SPONGE_AIR_BLOCK = REGISTRY.registerBlock("sponge_air", () -> new AirBlock(BlockBehaviour.Properties.of().replaceable().noCollission().noLootTable().air()));
    public static final RegistryReference<Block> AQUEOUS_SPONGE_BLOCK = REGISTRY.registerBlock("aqueous_sponge", () -> new PermanentSpongeBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_YELLOW).strength(0.6F).sound(SoundType.GRASS).randomTicks(), SpongeMaterial.AQUATIC));
    public static final RegistryReference<Block> MAGMATIC_SPONGE_BLOCK = REGISTRY.registerBlock("magmatic_sponge", () -> new PermanentSpongeBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_YELLOW).strength(0.6F).sound(SoundType.GRASS).randomTicks(), SpongeMaterial.MAGMATIC));
    public static final RegistryReference<Item> AQUEOUS_SPONGE_ITEM = REGISTRY.registerBlockItem(AQUEOUS_SPONGE_BLOCK);
    public static final RegistryReference<Item> MAGMATIC_SPONGE_ITEM = REGISTRY.registerBlockItem(MAGMATIC_SPONGE_BLOCK);
    public static final RegistryReference<Item> AQUEOUS_SPONGE_ON_A_STICK_ITEM = REGISTRY.registerItem("aqueous_sponge_on_a_stick", () -> new SpongeOnAStickItem(new Item.Properties().durability(65), SpongeMaterial.AQUATIC));
    public static final RegistryReference<Item> MAGMATIC_SPONGE_ON_A_STICK_ITEM = REGISTRY.registerItem("magmatic_sponge_on_a_stick", () -> new SpongeOnAStickItem(new Item.Properties().durability(129), SpongeMaterial.MAGMATIC));
    public static final RegistryReference<PoiType> PERMANENT_SPONGE_POI_TYPE = REGISTRY.registerPoiTypeBuilder("permanent_sponge", () -> {
        Set<BlockState> allSpongeStates = Stream.of(AQUEOUS_SPONGE_BLOCK.get(), MAGMATIC_SPONGE_BLOCK.get())
                .flatMap(block -> block.getStateDefinition().getPossibleStates().stream())
                .collect(ImmutableSet.toImmutableSet());
        return PoiTypeBuilder.of(0, 1, allSpongeStates);
    });

    public static void touch() {

    }
}
