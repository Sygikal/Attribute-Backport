package dev.sygii.attributes.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import dev.sygii.attributes.AttributeBackport;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Debug(export = true)
@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {

    public LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Inject(
            method = "createLivingAttributes()Lnet/minecraft/entity/attribute/DefaultAttributeContainer$Builder;",
            require = 1, allow = 1, at = @At("RETURN"))
    private static void addAttributes(final CallbackInfoReturnable<DefaultAttributeContainer.Builder> info) {
        info.getReturnValue().add(AttributeBackport.GENERIC_BURNING_TIME)
                .add(AttributeBackport.GENERIC_OXYGEN_BONUS);
    }


    /*@ModifyVariable(method = "getNextAirUnderwater", at = @At(value = "INVOKE", target = "Lnet/minecraft/enchantment/EnchantmentHelper;getRespiration(Lnet/minecraft/entity/LivingEntity;)I"))
    private int getEfficiency(int f) {
        f += ((LivingEntity)(Object)this).getAttributeValue(AttributeBackport.GENERIC_OXYGEN_BONUS);
        return f;
    }*/

    @Inject(method="getNextAirUnderwater", at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/enchantment/EnchantmentHelper;getRespiration(Lnet/minecraft/entity/LivingEntity;)I", shift = At.Shift.AFTER), cancellable = true, locals = LocalCapture.CAPTURE_FAILHARD)
    private void getEfficiency(int air, CallbackInfoReturnable<Integer> cir, int i) {
        i += ((LivingEntity)(Object)this).getAttributeValue(AttributeBackport.GENERIC_OXYGEN_BONUS);
        if (i > 0.0 && this.random.nextDouble() >= 1.0 / (i + 1.0)) {
            cir.setReturnValue(air);
        } else {
            cir.setReturnValue(air - 1);
        }
    }


}
