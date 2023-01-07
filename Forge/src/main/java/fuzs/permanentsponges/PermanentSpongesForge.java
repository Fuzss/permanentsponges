package fuzs.permanentsponges;

import fuzs.permanentsponges.world.level.block.sponge.SpongeScheduler;
import fuzs.permanentsponges.data.ModBlockStateProvider;
import fuzs.permanentsponges.data.ModBlockTagsProvider;
import fuzs.permanentsponges.data.ModLanguageProvider;
import fuzs.permanentsponges.data.ModRecipeProvider;
import fuzs.puzzleslib.core.CommonFactories;
import net.minecraft.data.DataGenerator;
import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.level.ChunkEvent;
import net.minecraftforge.event.level.LevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLConstructModEvent;

@Mod(PermanentSponges.MOD_ID)
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class PermanentSpongesForge {

    @SubscribeEvent
    public static void onConstructMod(final FMLConstructModEvent evt) {
        CommonFactories.INSTANCE.modConstructor(PermanentSponges.MOD_ID).accept(new PermanentSponges());
        registerHandlers();
    }

    private static void registerHandlers() {
        MinecraftForge.EVENT_BUS.addListener((final TickEvent.LevelTickEvent evt) -> {
            if (evt.side == LogicalSide.SERVER && evt.phase == TickEvent.Phase.END) {
                SpongeScheduler.INSTANCE.onServerWorld$Tick((ServerLevel) evt.level);
            }
        });
        MinecraftForge.EVENT_BUS.addListener((final ChunkEvent.Unload evt) -> {
            if (evt.getLevel() instanceof ServerLevel level) {
                SpongeScheduler.INSTANCE.onChunk$Unload(level, evt.getChunk());
            }
        });
        MinecraftForge.EVENT_BUS.addListener((final LevelEvent.Unload evt) -> {
            if (evt.getLevel() instanceof ServerLevel level) {
                SpongeScheduler.INSTANCE.onServerWorld$Unload(level.getServer(), level);
            }
        });
    }

    @SubscribeEvent
    public static void onGatherData(final GatherDataEvent evt) {
        DataGenerator generator = evt.getGenerator();
        final ExistingFileHelper existingFileHelper = evt.getExistingFileHelper();
        generator.addProvider(true, new ModRecipeProvider(generator));
        generator.addProvider(true, new ModLanguageProvider(generator, PermanentSponges.MOD_ID));
        generator.addProvider(true, new ModBlockStateProvider(generator, PermanentSponges.MOD_ID, existingFileHelper));
        generator.addProvider(true, new ModBlockTagsProvider(generator, PermanentSponges.MOD_ID, existingFileHelper));
    }
}
