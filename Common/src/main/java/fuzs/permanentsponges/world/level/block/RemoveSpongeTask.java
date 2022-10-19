package fuzs.permanentsponges.world.level.block;

import fuzs.permanentsponges.init.ModRegistry;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.village.poi.PoiManager;
import net.minecraft.world.level.block.Blocks;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RemoveSpongeTask extends AbstractSpongeTask {
    private static final Int2ObjectMap<List<BlockPos>> SPONGE_RADIUS = new Int2ObjectOpenHashMap<>();

    private final Queue<BlockPos> blocks;

    private RemoveSpongeTask(ServerLevel level, BlockPos source, Queue<BlockPos> blocks) {
        super(level, source, ModRegistry.SPONGE_AIR_BLOCK.get());
        this.blocks = blocks;
    }

    public static AbstractSpongeTask createRemoveTask(ServerLevel level, BlockPos pos, int depth) {
        depth++;
        Set<BlockPos> occupiedPositions = findOccupiedPositions(level, pos, depth);
        LinkedList<BlockPos> positions = getCachedSpongeRadius(depth).stream().map(pos::offset).filter(Predicate.not(occupiedPositions::contains)).collect(Collectors.toCollection(LinkedList::new));
        return new RemoveSpongeTask(level, pos, positions);
    }

    private static Set<BlockPos> findOccupiedPositions(ServerLevel level, BlockPos pos, int depth) {
        PoiManager poiManager = level.getPoiManager();
        Stream<BlockPos> all = poiManager.findAll(poiType -> poiType.is(ModRegistry.PERMANENT_SPONGE_POI_TYPE.getResourceKey()), pos1 -> !pos1.equals(pos), pos, depth * 2, PoiManager.Occupancy.ANY);
        return all.flatMap(pos1 -> getCachedSpongeRadius(depth).stream().map(pos1::offset)).collect(Collectors.toSet());
    }

    private static List<BlockPos> getCachedSpongeRadius(int depth) {
        return SPONGE_RADIUS.computeIfAbsent(depth, RemoveSpongeTask::getSpongeRadius);
    }

    private static List<BlockPos> getSpongeRadius(int depth) {
        return BlockPos.betweenClosedStream(-depth, -depth, -depth, depth, depth, depth).filter((pos) -> {
            int distance = pos.distManhattan(BlockPos.ZERO);
            return distance > 0 && distance <= depth;
        }).map(BlockPos::immutable).sorted(Comparator.<BlockPos>comparingInt(o -> o.distManhattan(BlockPos.ZERO)).reversed()).toList();
    }

    @Override
    public boolean advance(int amount) {
        for (int i = 0; (i < amount || amount == -1) && !this.blocks.isEmpty(); i++) {
            BlockPos pos = this.blocks.poll();
            if (this.level.getBlockState(pos).is(this.replacement)) {
                this.level.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
            }
        }
        return this.blocks.isEmpty();
    }

    @Override
    public boolean containsBlocksAtChunkPos(int x, int z) {
        for (BlockPos pos : this.blocks) {
            if (SectionPos.blockToSection(pos.getX()) == x && SectionPos.blockToSection(pos.getZ()) == z) {
                return true;
            }
        }
        return false;
    }
}
