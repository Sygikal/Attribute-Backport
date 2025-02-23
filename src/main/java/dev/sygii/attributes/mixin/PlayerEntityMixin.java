package dev.sygii.attributes.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import dev.sygii.attributes.AttributeBackport;
import dev.sygii.attributes.util.ReachUtil;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.ViewerCountManager;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Debug(export=true)
@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity {

    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(
            method = "createPlayerAttributes",
            require = 1, allow = 1, at = @At("RETURN"))
    private static void addAttributes(final CallbackInfoReturnable<DefaultAttributeContainer.Builder> info) {
        info.getReturnValue().add(AttributeBackport.PLAYER_BLOCK_BREAK_SPEED)
                .add(AttributeBackport.PLAYER_BLOCK_INTERACTION_RANGE)
                .add(AttributeBackport.PLAYER_ENTITY_INTERACTION_RANGE)
                .add(AttributeBackport.PLAYER_SUBMERGED_MINING_SPEED)
                .add(AttributeBackport.PLAYER_MINING_EFFICIENCY)
                .add(AttributeBackport.PLAYER_SNEAKING_SPEED)
                .add(AttributeBackport.PLAYER_SWEEPING_DAMAGE_RATIO);
    }

    /*@Inject(method = "getBlockBreakingSpeed", at = @At(value = "INVOKE", target = "Lnet/minecraft/enchantment/EnchantmentHelper;hasAquaAffinity(Lnet/minecraft/entity/LivingEntity;)Z", shift = At.Shift.AFTER), locals = LocalCapture.CAPTURE_FAILSOFT, cancellable = true)
    private void getSubmergedSpeed(BlockState block, CallbackInfoReturnable<Float> cir, float f) {
        f *= 5;
        f *= (float)this.getAttributeValue(AttributeBackport.PLAYER_SUBMERGED_MINING_SPEED);
        System.out.println(f);
    }*/

    @WrapOperation(method = "attack", at = @At(value = "INVOKE", target = "Lnet/minecraft/enchantment/EnchantmentHelper;getSweepingMultiplier(Lnet/minecraft/entity/LivingEntity;)F"))
    private float getSweepingDamage(LivingEntity entity, Operation<Float> original) {
        return EnchantmentHelper.getSweepingMultiplier(entity) + (float)this.getAttributeValue(AttributeBackport.PLAYER_SWEEPING_DAMAGE_RATIO);
    }

    @ModifyVariable(method = "getBlockBreakingSpeed", at = @At(value = "INVOKE", target = "Lnet/minecraft/enchantment/EnchantmentHelper;getEfficiency(Lnet/minecraft/entity/LivingEntity;)I"))
    private float getEfficiency(float f) {
        f += (float)this.getAttributeValue(AttributeBackport.PLAYER_MINING_EFFICIENCY);
        return f;
    }

    @ModifyVariable(method = "getBlockBreakingSpeed", at = @At(value = "INVOKE", target = "Lnet/minecraft/enchantment/EnchantmentHelper;hasAquaAffinity(Lnet/minecraft/entity/LivingEntity;)Z", shift = At.Shift.AFTER))
    private float getSubmergedSpeed(float f) {
        f *= 5;
        f *= (float)this.getAttributeValue(AttributeBackport.PLAYER_SUBMERGED_MINING_SPEED);
        return f;
    }

    @ModifyVariable(method = "getBlockBreakingSpeed", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;isSubmergedIn(Lnet/minecraft/registry/tag/TagKey;)Z"))
    private float getBlockBreakingSpeed(float f) {
        f *= (float)this.getAttributeValue(AttributeBackport.PLAYER_BLOCK_BREAK_SPEED);
        return f;
    }

    /*@ModifyExpressionValue(method = "attack(Lnet/minecraft/entity/Entity;)V", at = @At(value = "CONSTANT", args = "floatValue=1.5F"))
    public float additionalEntityAttributes$applyCriticalBonusDamage(float original) {
        /*EntityAttributeInstance criticalDamageMultiplier = ((LivingEntity) (Object) this).getAttributeInstance(AdditionalEntityAttributes.CRITICAL_BONUS_DAMAGE);
        if (criticalDamageMultiplier == null) {
            return original;
        } else {
            return 1 + (float) criticalDamageMultiplier.getValue();
        }
        return 10;
    }*/

    /*@Inject(method = "getBlockBreakingSpeed", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;isSubmergedIn(Lnet/minecraft/registry/tag/TagKey;)Z", shift = At.Shift.BEFORE), locals = LocalCapture.CAPTURE_FAILSOFT, cancellable = true)
    private void getBlockBreakingSpeed(BlockState block, CallbackInfoReturnable<Float> cir, float f) {
        System.out.println(f);
        f *= (float)this.getAttributeValue(AttributeBackport.PLAYER_BLOCK_BREAK_SPEED);
        System.out.println(f);
        //cir.setReturnValue(f);
    }*/

    /*@ModifyReturnValue(method = "getBlockBreakingSpeed", at = @At(value = "STORE", ordinal = 0), locals = LocalCapture.CAPTURE_FAILSOFT, cancellable = true)
    private void getBlockBreakingSpeed3(BlockState block, CallbackInfoReturnable<Float> cir, float f) {
        /*f *= (float)this.getAttributeValue(AttributeBackport.PLAYER_SUBMERGED_MINING_SPEED);
        cir.setReturnValue(f);
        System.out.println(f);
    }*/

    /*@Redirect(
            method = "getBlockBreakingSpeed",
            constant = @Constant(floatValue = 5.0F, ordinal = 0))
    private int getReachAccountingOpenCount(final ViewerCountManager manager, final World world, final BlockPos pos) {
        return ReachUtil.getPlayersWithinReach(this::isPlayerViewing, world, pos.getX(), pos.getY(), pos.getZ(), 5.0).size();
    }*/


    @ModifyConstant(method = "attack(Lnet/minecraft/entity/Entity;)V", constant = @Constant(doubleValue = 9.0))
    private double getActualAttackRange(final double attackRange) {
        return ReachUtil.getSquaredAttackRange(this, attackRange);
    }

}
