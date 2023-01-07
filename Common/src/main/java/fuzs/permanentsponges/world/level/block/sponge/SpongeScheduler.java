package fuzs.permanentsponges.world.level.block.sponge;

import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.LevelChunk;

import java.util.Iterator;

public class SpongeScheduler {
    public static final SpongeScheduler INSTANCE = new SpongeScheduler();

    private final Long2ObjectMap<AbstractSpongeTask> tasks = new Long2ObjectOpenHashMap<>();

    public void scheduleTask(AbstractSpongeTask task) {
        // use map so that a task already running for a position can be 'aborted' and replaced
        this.tasks.put(task.position(), task);
    }

    public void onServerWorld$Tick(ServerLevel level) {
        Iterator<AbstractSpongeTask> iterator = this.tasks.values().iterator();
        while (iterator.hasNext()) {
            AbstractSpongeTask task = iterator.next();
            if (task.mayAdvance(level)) {
                if (task.advance(8)) {
                    iterator.remove();
                }
            }
        }
    }

    public void onServerWorld$Unload(MinecraftServer server, ServerLevel level) {
        Iterator<AbstractSpongeTask> iterator = this.tasks.values().iterator();
        while (iterator.hasNext()) {
            AbstractSpongeTask task = iterator.next();
            task.finishQuickly();
            iterator.remove();
        }
    }

    public void onChunk$Unload(ServerLevel level, ChunkAccess chunk) {
        if (chunk instanceof LevelChunk levelChunk) {
            Iterator<AbstractSpongeTask> iterator = this.tasks.values().iterator();
            while (iterator.hasNext()) {
                AbstractSpongeTask task = iterator.next();
                if (task.mayAdvance(levelChunk.getLevel())) {
                    ChunkPos chunkPos = levelChunk.getPos();
                    if (task.containsBlocksAtChunkPos(chunkPos.x, chunkPos.z)) {
                        task.finishQuickly();
                        iterator.remove();
                    }
                }
            }
        }
    }
}
