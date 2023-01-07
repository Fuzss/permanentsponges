package fuzs.permanentsponges.world.level.block.sponge;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

import java.util.Comparator;
import java.util.List;
import java.util.Queue;

public abstract class AbstractSpongeTask {
    private static final Int2ObjectMap<List<BlockPos>> SPONGE_RADIUS = new Int2ObjectOpenHashMap<>();

    protected final ServerLevel level;
    protected final BlockPos pos;
    protected final Block replacement;
    protected final Queue<BlockPos> blocks;

    protected AbstractSpongeTask(ServerLevel level, BlockPos pos, int distance, Block replacement) {
        this.level = level;
        this.pos = pos;
        this.replacement = replacement;
        this.blocks = this.getSpongeRadiusAtPos(level, distance, pos);
    }

    protected static List<BlockPos> getSpongeRadius(int depth) {
        return SPONGE_RADIUS.computeIfAbsent(depth, AbstractSpongeTask::getSortedSpongeRadius);
    }

    private static List<BlockPos> getSortedSpongeRadius(int depth) {
        return BlockPos.betweenClosedStream(-depth, -depth, -depth, depth, depth, depth)
                .map(BlockPos::immutable)
                .sorted(Comparator.<BlockPos>comparingInt(o -> o.distManhattan(BlockPos.ZERO)))
                .toList();
    }

    protected abstract Queue<BlockPos> getSpongeRadiusAtPos(ServerLevel level, int distance, BlockPos pos);

    public final boolean mayAdvance(Level level) {
        return this.level == level;
    }

    public final void finishQuickly() {
        this.advance(-1);
    }

    public final long position() {
        return this.pos.asLong();
    }

    public abstract boolean containsBlocksAtChunkPos(int x, int z);

    public abstract boolean advance(int amount);
}
