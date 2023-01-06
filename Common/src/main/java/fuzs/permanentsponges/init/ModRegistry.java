package fuzs.permanentsponges.init;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import fuzs.permanentsponges.PermanentSponges;
import fuzs.permanentsponges.world.item.SpongeOnAStickItem;
import fuzs.permanentsponges.world.level.block.PermanentSpongeBlock;
import fuzs.puzzleslib.core.CoreServices;
import fuzs.puzzleslib.init.RegistryManager;
import fuzs.puzzleslib.init.RegistryReference;
import fuzs.puzzleslib.init.builder.ModPoiTypeBuilder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.AirBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;

import java.util.Set;

public class ModRegistry {
    private static final RegistryManager REGISTRY = CoreServices.FACTORIES.registration(PermanentSponges.MOD_ID);
    public static final RegistryReference<Block> SPONGE_AIR_BLOCK = REGISTRY.registerBlock("sponge_air", () -> new AirBlock(BlockBehaviour.Properties.of(Material.STRUCTURAL_AIR).noCollission().noLootTable().air()) {});
    public static final RegistryReference<Block> AQUEOUS_SPONGE_BLOCK = REGISTRY.registerBlockWithItem("aqueous_sponge", () -> new PermanentSpongeBlock(BlockBehaviour.Properties.of(Material.SPONGE).strength(0.6F).sound(SoundType.GRASS).randomTicks(), true), CreativeModeTab.TAB_BUILDING_BLOCKS);
    public static final RegistryReference<Block> MAGMATIC_SPONGE_BLOCK = REGISTRY.registerBlockWithItem("magmatic_sponge", () -> new PermanentSpongeBlock(BlockBehaviour.Properties.of(Material.SPONGE).strength(0.6F).sound(SoundType.GRASS).randomTicks(), false), CreativeModeTab.TAB_BUILDING_BLOCKS);
    public static final RegistryReference<Item> AQUEOUS_SPONGE_ON_A_STICK = REGISTRY.registerItem("aqueous_sponge_on_a_stick", () -> new SpongeOnAStickItem(new Item.Properties().tab(CreativeModeTab.TAB_TOOLS).durability(17), true));
    public static final RegistryReference<Item> MAGMATIC_SPONGE_ON_A_STICK_ITEM = REGISTRY.registerItem("magmatic_sponge_on_a_stick", () -> new SpongeOnAStickItem(new Item.Properties().tab(CreativeModeTab.TAB_TOOLS).durability(33), false));
    private static final Set<BlockState> ALL_SPONGE_STATES = ImmutableList.of(AQUEOUS_SPONGE_BLOCK.get(), MAGMATIC_SPONGE_BLOCK.get()).stream()
            .flatMap(block -> block.getStateDefinition().getPossibleStates().stream())
            .collect(ImmutableSet.toImmutableSet());
    public static final RegistryReference<PoiType> PERMANENT_SPONGE_POI_TYPE = REGISTRY.registerPoiTypeBuilder("permanent_sponge", () -> ModPoiTypeBuilder.of(0, 1, ALL_SPONGE_STATES));

    public static final TagKey<Block> SPONGE_TAG = TagKey.create(Registry.BLOCK_REGISTRY, new ResourceLocation(PermanentSponges.MOD_ID, "sponge"));

    public static void touch() {}
}
