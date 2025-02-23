package dev.sygii.attributes.mixin;

import dev.sygii.attributes.util.ReachUtil;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.screen.ForgingScreenHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(ForgingScreenHandler.class)
public abstract class ForgingScreenHandlerMixin {

    @ModifyConstant(
            method = "method_24924(Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;)Ljava/lang/Boolean;",
            require = 1, allow = 1, constant = @Constant(doubleValue = 64.0))
    private double getActualReachDistance(final double reachDistance, final PlayerEntity player) {
        return ReachUtil.getSquaredReachDistanceBonus2(player, reachDistance);
    }
}
