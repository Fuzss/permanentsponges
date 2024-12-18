package fuzs.permanentsponges.handler;

import fuzs.permanentsponges.PermanentSponges;
import fuzs.permanentsponges.config.ServerConfig;
import fuzs.permanentsponges.mixin.accessor.BucketItemAccessor;
import fuzs.permanentsponges.mixin.accessor.ItemAccessor;
import fuzs.permanentsponges.util.LiquidAbsorptionHelper;
import fuzs.puzzleslib.api.event.v1.core.EventResultHolder;
import fuzs.puzzleslib.api.util.v1.InteractionResultHelper;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LiquidBlockContainer;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.Nullable;

public class TryEmptyBucketHandler {

    public static EventResultHolder<InteractionResult> onUseItem(Player player, Level level, InteractionHand interactionHand) {
        if (!PermanentSponges.CONFIG.get(ServerConfig.class).preventEmptyingBuckets) return EventResultHolder.pass();
        // implementation is mostly copied from BucketItem
        ItemStack itemInHand = player.getItemInHand(interactionHand);
        if (itemInHand.getItem() instanceof BucketItem item) {
            Fluid fluid = ((BucketItemAccessor) item).permanentsponges$getContent();
            if (fluid != Fluids.EMPTY) {
                BlockHitResult blockHitResult = ItemAccessor.permanentsponges$callGetPlayerPOVHitResult(level,
                        player,
                        ClipContext.Fluid.NONE);
                if (blockHitResult.getType() == HitResult.Type.BLOCK) {
                    BlockPos blockPos = blockHitResult.getBlockPos();
                    Direction direction = blockHitResult.getDirection();
                    BlockPos blockPos2 = blockPos.relative(direction);
                    if (level.mayInteract(player, blockPos) && player.mayUseItemAt(blockPos2, direction, itemInHand)) {
                        BlockState blockState = level.getBlockState(blockPos);
                        BlockPos blockPos3 =
                                blockState.getBlock() instanceof LiquidBlockContainer && fluid == Fluids.WATER ?
                                        blockPos : blockPos2;
                        if (emptyContents(player, level, blockPos3, blockHitResult, fluid)) {
                            item.checkExtraContent(player, level, itemInHand, blockPos3);
                            if (player instanceof ServerPlayer) {
                                CriteriaTriggers.PLACED_BLOCK.trigger((ServerPlayer) player, blockPos3, itemInHand);
                            }
                            player.awardStat(Stats.ITEM_USED.get(item));
                            InteractionResult result = InteractionResultHelper.sidedSuccess(BucketItem.getEmptySuccessItem(
                                    itemInHand,
                                    player), level.isClientSide());
                            if (itemInHand != getObject(result)) {
                                player.setItemInHand(interactionHand, getObject(result));
                            }
                            return EventResultHolder.interrupt(result);
                        }
                    }
                }
            }
        }

        return EventResultHolder.pass();
    }

    /**
     * @deprecated replace with method in {@link InteractionResultHelper}
     */
    @Deprecated(forRemoval = true)
    public static ItemStack getObject(InteractionResult interactionResult) {
        return interactionResult instanceof InteractionResult.Success success &&
                success.heldItemTransformedTo() != null ? success.heldItemTransformedTo() : ItemStack.EMPTY;
    }

    private static boolean emptyContents(@Nullable Player player, Level level, BlockPos blockPos, @Nullable BlockHitResult hitResult, Fluid fluid) {
        if (!(fluid instanceof FlowingFluid)) {
            return false;
        } else {
            BlockState blockState = level.getBlockState(blockPos);
            Block block = blockState.getBlock();
            boolean bl = blockState.canBeReplaced(fluid);
            boolean bl2 = blockState.isAir() || bl || block instanceof LiquidBlockContainer &&
                    ((LiquidBlockContainer) block).canPlaceLiquid(player, level, blockPos, blockState, fluid);
            if (!bl2) {
                return hitResult != null && emptyContents(player,
                        level,
                        hitResult.getBlockPos().relative(hitResult.getDirection()),
                        null,
                        fluid);
            } else if (level instanceof ServerLevel serverLevel &&
                    LiquidAbsorptionHelper.tryPreventLiquidFromEntering(serverLevel, blockPos, fluid)) {
                LiquidAbsorptionHelper.removeLiquidEffects(level, blockPos, fluid);
                return true;
            }
        }

        return false;
    }
}
