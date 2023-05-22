package fuzs.permanentsponges;

import fuzs.permanentsponges.data.ModLanguageProvider;
import fuzs.permanentsponges.data.ModModelProvider;
import fuzs.permanentsponges.data.ModRecipeProvider;
import fuzs.permanentsponges.world.level.block.sponge.SpongeScheduler;
import fuzs.puzzleslib.api.core.v1.ModConstructor;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.level.ChunkEvent;
import net.minecraftforge.event.level.LevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLConstructModEvent;

import java.util.concurrent.CompletableFuture;

@Mod(PermanentSponges.MOD_ID)
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class PermanentSpongesForge {

    @SubscribeEvent
    public static void onConstructMod(final FMLConstructModEvent evt) {
        ModConstructor.construct(PermanentSponges.MOD_ID, PermanentSponges::new);
        registerHandlers();
    }

    private static void registerHandlers() {
        // TODO replace with common implementation in 1.19.4
        MinecraftForge.EVENT_BUS.addListener((final TickEvent.LevelTickEvent evt) -> {
            if (evt.phase == TickEvent.Phase.END) {
                if (evt.level instanceof ServerLevel level) {
                    SpongeScheduler.INSTANCE.onServerWorld$Tick(level);
                }
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
        final DataGenerator dataGenerator = evt.getGenerator();
        final PackOutput packOutput = dataGenerator.getPackOutput();
        final CompletableFuture<HolderLookup.Provider> lookupProvider = evt.getLookupProvider();
        final ExistingFileHelper fileHelper = evt.getExistingFileHelper();
        dataGenerator.addProvider(true, new ModModelProvider(packOutput, PermanentSponges.MOD_ID, fileHelper));
        dataGenerator.addProvider(true, new ModLanguageProvider(packOutput, PermanentSponges.MOD_ID));
        dataGenerator.addProvider(true, new ModRecipeProvider(packOutput));
    }
}
