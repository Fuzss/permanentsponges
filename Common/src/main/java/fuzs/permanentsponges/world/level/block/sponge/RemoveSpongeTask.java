package fuzs.permanentsponges.world.level.block.sponge;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import fuzs.permanentsponges.init.ModRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.village.poi.PoiManager;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import java.util.Collections;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RemoveSpongeTask extends AbstractSpongeTask {

    public RemoveSpongeTask(ServerLevel level, BlockPos pos, int distance, Block replacement) {
        super(level, pos, distance, replacement);
    }

    @Override
    protected Queue<BlockPos> getSpongeRadiusAtPos(ServerLevel level, int distance, BlockPos pos) {
        Set<BlockPos> occupiedPositions = findOccupiedPositions(level, pos, distance);
        List<BlockPos> spongeRadius = getSpongeRadius(distance);
        spongeRadius = Lists.newArrayList(spongeRadius);
        Collections.reverse(spongeRadius);
        return spongeRadius.stream()
                .map(pos::offset)
                .filter(Predicate.not(occupiedPositions::contains))
                .collect(Collectors.toCollection(Lists::newLinkedList));
    }

    private static Set<BlockPos> findOccupiedPositions(ServerLevel level, BlockPos pos, int depth) {
        PoiManager poiManager = level.getPoiManager();
        Stream<BlockPos> all = poiManager.findAll(poiType -> poiType.is(ModRegistry.PERMANENT_SPONGE_POI_TYPE.getResourceKey()), pos1 -> !pos1.equals(pos), pos, depth * 2, PoiManager.Occupancy.ANY);
        return all.flatMap(pos1 -> getSpongeRadius(depth).stream()
                .map(pos1::offset))
                .collect(ImmutableSet.toImmutableSet());
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
