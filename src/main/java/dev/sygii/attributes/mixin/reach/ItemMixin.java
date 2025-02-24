package dev.sygii.attributes.mixin.reach;

import dev.sygii.attributes.util.ReachUtil;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(Item.class)
public class ItemMixin {

    @ModifyConstant(
            method = "raycast(Lnet/minecraft/world/World;Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/world/RaycastContext$FluidHandling;)Lnet/minecraft/util/hit/BlockHitResult;",
            require = 4, allow = 4, constant = @Constant(doubleValue = 5.0))
    private static double getActualReachDistance(final double reachDistance, final World world, final PlayerEntity player) {
        return ReachUtil.getReachDistance(player, reachDistance + (reachDistance/9));
    }
}
