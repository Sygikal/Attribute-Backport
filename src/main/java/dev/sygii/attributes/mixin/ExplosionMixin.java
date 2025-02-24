package dev.sygii.attributes.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import dev.sygii.attributes.AttributeBackport;
import net.minecraft.enchantment.ProtectionEnchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Debug(export = true)
@Mixin(Explosion.class)
public class ExplosionMixin {

    @WrapOperation(method = "collectBlocksAndDamageEntities", at = @At(value = "INVOKE", target = "Lnet/minecraft/enchantment/ProtectionEnchantment;transformExplosionKnockback(Lnet/minecraft/entity/LivingEntity;D)D"))
    private double test(LivingEntity entity, double velocity, Operation<Double> original) {
        //ab = aa * (1.0 - livingEntity.getAttributeValue(EntityAttributes.GENERIC_EXPLOSION_KNOCKBACK_RESISTANCE));
        return original.call(entity, velocity) * (1.0 - entity.getAttributeValue(AttributeBackport.GENERIC_EXPLOSION_KNOCKBACK_RESISTANCE));
        //return original.call(entity, velocity);
        //original.call(instance, entity, x, y, z, power, explosionSourceType);
    }

}
