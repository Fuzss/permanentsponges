package fuzs.permanentsponges;

import fuzs.permanentsponges.world.level.block.sponge.SpongeScheduler;
import fuzs.puzzleslib.core.CommonFactories;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerChunkEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerWorldEvents;

public class PermanentSpongesFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        CommonFactories.INSTANCE.modConstructor(PermanentSponges.MOD_ID).accept(new PermanentSponges());
        registerHandlers();
    }

    private static void registerHandlers() {
        ServerTickEvents.END_WORLD_TICK.register(SpongeScheduler.INSTANCE::onServerWorld$Tick);
        ServerChunkEvents.CHUNK_UNLOAD.register(SpongeScheduler.INSTANCE::onChunk$Unload);
        ServerWorldEvents.UNLOAD.register(SpongeScheduler.INSTANCE::onServerWorld$Unload);
    }
}
