package dev.sygii.attributes.mixin;

import dev.sygii.attributes.util.ReachUtil;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.network.ServerPlayerInteractionManager;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ServerPlayerInteractionManager.class)
public abstract class ServerPlayerInteractionManagerMixin{
    @Shadow
    @Final
    protected ServerPlayerEntity player;

    /*@ModifyExpressionValue(method = "processBlockBreakingAction", at = @At(value = "FIELD", target = "Lnet/minecraft/server/network/ServerPlayNetworkHandler;MAX_BREAK_SQUARED_DISTANCE:D"))
    private double onPlayerInteractBlock(double original) {
        return ((ServerPlayerEntityAccess)this.player).getBlockInteractionRange() + 1;
    }*/

    @Redirect(
            method = "processBlockBreakingAction",
            at = @At(value = "FIELD", target = "Lnet/minecraft/server/network/ServerPlayNetworkHandler;MAX_BREAK_SQUARED_DISTANCE:D", opcode = Opcodes.GETSTATIC))
    private double getActualReachDistance() {
        return ReachUtil.getSquaredReachDistanceBonus(this.player, ServerPlayNetworkHandler.MAX_BREAK_SQUARED_DISTANCE);
    }
}
