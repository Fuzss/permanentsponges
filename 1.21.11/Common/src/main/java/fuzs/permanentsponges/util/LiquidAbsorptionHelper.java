package fuzs.permanentsponges.util;

import com.google.common.base.Predicates;
import fuzs.permanentsponges.core.CommonAbstractions;
import fuzs.permanentsponges.init.ModRegistry;
import fuzs.permanentsponges.world.level.block.SpongeMaterial;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.ai.village.poi.PoiManager;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import org.jspecify.annotations.Nullable;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class LiquidAbsorptionHelper {
    private static final Int2ObjectMap<List<BlockPos>> SPONGE_RADIUS = new Int2ObjectOpenHashMap<>();

    public static List<BlockPos> getSpongeRadius(int depth) {
        return SPONGE_RADIUS.computeIfAbsent(depth, LiquidAbsorptionHelper::getSortedSpongeRadius);
    }

    private static List<BlockPos> getSortedSpongeRadius(int depth) {
        return BlockPos.betweenClosedStream(-depth, -depth, -depth, depth, depth, depth)
                .map(BlockPos::immutable)
                .sorted(Comparator.<BlockPos>comparingInt(blockPos -> Math.max(Math.max(Math.abs(blockPos.getX()),
                                Math.abs(blockPos.getY())), Math.abs(blockPos.getZ())))
                        .thenComparingInt(BlockPos.ZERO::distManhattan))
                .toList();
    }

    public static boolean removeAllLiquid(ServerLevel level, BlockPos blockPos, int spongeRadius, boolean destroySource) {
        List<BlockPos> positions = getSpongeRadius(spongeRadius);
        BoundingBox boundingBox = new BoundingBox(-spongeRadius,
                -spongeRadius,
                -spongeRadius,
                spongeRadius,
                spongeRadius,
                spongeRadius);
        level.getFluidTicks().clearArea(boundingBox.move(blockPos));
        boolean hasDestroyedSource = false;
        for (BlockPos currentBlockPos : positions) {
            currentBlockPos = blockPos.offset(currentBlockPos);
            BlockState blockState = level.getBlockState(currentBlockPos);
            FluidState fluidState = level.getFluidState(currentBlockPos);
            if (!fluidState.isEmpty() || blockState.isAir()) {
                if (!hasDestroyedSource && CommonAbstractions.INSTANCE.isHotFluid(fluidState.getType())) {
                    if (destroySource) {
                        destroySpongeBlock(level, blockPos);
                    }
                    hasDestroyedSource = true;
                }
                removeLiquid(level, currentBlockPos, blockState, fluidState);
            }
        }
        return hasDestroyedSource;
    }

    private static void destroySpongeBlock(ServerLevel level, BlockPos blockPos) {
        if (level.getBlockState(blockPos).is(ModRegistry.PERMANENT_SPONGES_BLOCK_TAG)) {
            level.setBlock(blockPos, Blocks.AIR.defaultBlockState(), 3);
            level.levelEvent(LevelEvent.PARTICLES_WATER_EVAPORATING, blockPos, 0);
            level.playSound(null,
                    blockPos,
                    SoundEvents.FIRE_EXTINGUISH,
                    SoundSource.BLOCKS,
                    1.0F,
                    (1.0F + level.getRandom().nextFloat() * 0.2F) * 0.7F);
        }
    }

    private static void removeLiquid(Level level, BlockPos blockPos, BlockState blockState, FluidState fluidState) {
        if (!(blockState.getBlock() instanceof BucketPickup) || ((BucketPickup) blockState.getBlock()).pickupBlock(null,
                level,
                blockPos,
                blockState).isEmpty()) {
            boolean setToAir = false;
            if (blockState.getBlock() instanceof LiquidBlock || blockState.isAir()) {
                setToAir = true;
            } else if (blockState.is(Blocks.KELP) || blockState.is(Blocks.KELP_PLANT) || blockState.is(Blocks.SEAGRASS)
                    || blockState.is(Blocks.TALL_SEAGRASS)) {
                BlockEntity blockEntity = blockState.hasBlockEntity() ? level.getBlockEntity(blockPos) : null;
                Block.dropResources(blockState, level, blockPos, blockEntity);
                setToAir = true;
            }
            if (setToAir && !blockState.isAir()) {
                level.setBlock(blockPos, Blocks.AIR.defaultBlockState(), 3);
                if (!fluidState.isEmpty() && level.getRandom().nextInt(5) == 0) {
                    removeLiquidEffects(level, blockPos, fluidState.getType());
                }
            }
        }
    }

    public static void removeLiquidEffects(Level level, BlockPos blockPos, Fluid fluid) {
        if (CommonAbstractions.INSTANCE.isHotFluid(fluid)) {
            level.levelEvent(LevelEvent.LAVA_FIZZ, blockPos, 0);
        } else {
            level.levelEvent(LevelEvent.PARTICLES_WATER_EVAPORATING, blockPos, 0);
            level.playSound(null,
                    blockPos,
                    SoundEvents.FIRE_EXTINGUISH,
                    SoundSource.BLOCKS,
                    1.0F,
                    (1.0F + level.getRandom().nextFloat() * 0.2F) * 0.7F);
        }
    }

    public static boolean tryPreventLiquidFromEntering(ServerLevel serverLevel, BlockPos pos, Fluid fluid) {
        Map.Entry<SpongeMaterial, BlockPos> entry = keepPositionFreeFromLiquid(serverLevel, pos);
        if (entry != null) {
            if (entry.getKey().shouldDestroyTouchingHot() && CommonAbstractions.INSTANCE.isHotFluid(fluid)) {
                destroySpongeBlock(serverLevel, entry.getValue());
            }
            return true;
        } else {
            return false;
        }
    }

    private static Map.@Nullable Entry<SpongeMaterial, BlockPos> keepPositionFreeFromLiquid(ServerLevel serverLevel, BlockPos blockPos) {
        // Lithium passes null in here
        if (blockPos != null) {
            PoiManager poiManager = serverLevel.getPoiManager();
            for (SpongeMaterial spongeMaterial : SpongeMaterial.values()) {
                ResourceKey<PoiType> resourceKey = spongeMaterial.getPoiType().key();
                // 2**0.5
                int distance = (int) Math.ceil((spongeMaterial.getBlockDistance() + 1) * 1.42);
                Optional<BlockPos> optional = poiManager.findAll(holder -> holder.is(resourceKey),
                        Predicates.alwaysTrue(),
                        blockPos,
                        distance,
                        PoiManager.Occupancy.ANY).filter((BlockPos currentBlockPos) -> {
                    currentBlockPos = currentBlockPos.subtract(blockPos);
                    return Math.abs(currentBlockPos.getX()) <= spongeMaterial.getBlockDistance()
                            && Math.abs(currentBlockPos.getY()) <= spongeMaterial.getBlockDistance()
                            && Math.abs(currentBlockPos.getZ()) <= spongeMaterial.getBlockDistance();
                }).min(Comparator.comparingInt(blockPos::distManhattan));
                if (optional.isPresent()) {
                    return Map.entry(spongeMaterial, optional.get());
                }
            }
        }

        return null;
    }
}
