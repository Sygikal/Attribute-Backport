package dev.sygii.attributes.mixin.reach;

import dev.sygii.attributes.util.ReachUtil;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(Inventory.class)
public interface InventoryMixin {

    @Inject(
            method = "canPlayerUse(Lnet/minecraft/block/entity/BlockEntity;Lnet/minecraft/entity/player/PlayerEntity;I)Z",
            require = 1, allow = 1,
            at = @At(shift = At.Shift.BEFORE, value = "INVOKE", target = "Lnet/minecraft/util/math/BlockPos;getX()I"),
            locals = LocalCapture.CAPTURE_FAILHARD, cancellable = true)
    private static void checkWithinActualReach(final BlockEntity blockEntity, final PlayerEntity player, final int reachDistance, final CallbackInfoReturnable<Boolean> cir, final World world, final BlockPos pos) {
        System.out.println(reachDistance);
        if (player.squaredDistanceTo(pos.toCenterPos()) <= ReachUtil.getSquaredReachDistanceBonus2(player, reachDistance * reachDistance)) {
            cir.setReturnValue(true);
        }
    }
}
