package fuzs.permanentsponges;

import fuzs.permanentsponges.world.level.block.sponge.SpongeScheduler;
import fuzs.puzzleslib.api.core.v1.ModConstructor;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerChunkEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerWorldEvents;

public class PermanentSpongesFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        ModConstructor.construct(PermanentSponges.MOD_ID, PermanentSponges::new);
        registerHandlers();
    }

    private static void registerHandlers() {
        // TODO replace with common implementation in 1.19.4
        ServerTickEvents.END_WORLD_TICK.register(SpongeScheduler.INSTANCE::onServerWorld$Tick);
        ServerChunkEvents.CHUNK_UNLOAD.register(SpongeScheduler.INSTANCE::onChunk$Unload);
        ServerWorldEvents.UNLOAD.register(SpongeScheduler.INSTANCE::onServerWorld$Unload);
    }
}
