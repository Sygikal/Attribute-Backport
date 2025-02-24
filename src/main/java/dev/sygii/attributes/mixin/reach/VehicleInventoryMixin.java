package dev.sygii.attributes.mixin.reach;

import dev.sygii.attributes.util.ReachUtil;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.vehicle.VehicleInventory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(VehicleInventory.class)
public interface VehicleInventoryMixin {

    @ModifyConstant(
            method = "canPlayerAccess(Lnet/minecraft/entity/player/PlayerEntity;)Z",
            require = 1, allow = 1, constant = @Constant(doubleValue = 8.0))
    private double getActualReachDistance(final double reachDistance, final PlayerEntity player) {
        return ReachUtil.getReachDistance(player, reachDistance);
    }
}
