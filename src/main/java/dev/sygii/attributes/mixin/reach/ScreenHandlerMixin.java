package dev.sygii.attributes.mixin.reach;

import dev.sygii.attributes.util.ReachUtil;
import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.screen.ScreenHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(ScreenHandler.class)
public class ScreenHandlerMixin {

    @ModifyConstant(
            method = "method_17696(Lnet/minecraft/block/Block;Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;)Ljava/lang/Boolean;",
            require = 1, allow = 1, constant = @Constant(doubleValue = 64.0))
    private static double getActualReachDistance(final double reachDistance, final Block block, final PlayerEntity player) {
        return ReachUtil.getSquaredReachDistanceBonus2(player, reachDistance);
    }

}
