package dev.sygii.attributes.mixin;

import dev.sygii.attributes.AttributeBackport;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntity2Mixin extends LivingEntity {

    protected PlayerEntity2Mixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "getBlockBreakingSpeed", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/effect/StatusEffectUtil;hasHaste(Lnet/minecraft/entity/LivingEntity;)Z", shift = At.Shift.BEFORE), locals = LocalCapture.CAPTURE_FAILSOFT, cancellable = true)
    private void getBlockBreakingSpeed(BlockState block, CallbackInfoReturnable<Float> cir, float f) {
        System.out.println(f);
        f *= (float)this.getAttributeValue(AttributeBackport.PLAYER_BLOCK_BREAK_SPEED);
        System.out.println(f);
        cir.setReturnValue(f);
    }

}
