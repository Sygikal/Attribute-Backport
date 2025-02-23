package dev.sygii.attributes.mixin;

import dev.sygii.attributes.AttributeBackport;
import dev.sygii.attributes.EntityAccess;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public abstract class EntityMixin implements EntityAccess {

    @Shadow
    private int fireTicks;

    @Shadow
    public abstract void setFireTicks(int fireTicks);

    @Inject(method="setOnFireFor", at = @At(value = "FIELD", target = "Lnet/minecraft/entity/Entity;fireTicks:I", shift = At.Shift.BEFORE), cancellable = true)
    public final void setOnFireFor(int seconds, CallbackInfo ci) {
        this.setOnFireForTicks(MathHelper.floor(seconds * 20.0F));
        ci.cancel();
    }

    public void setOnFireForTicks(int ticks) {
        if (((Entity)(Object)this) instanceof LivingEntity asd) {
            ticks = MathHelper.ceil((double)ticks * asd.getAttributeValue(AttributeBackport.GENERIC_BURNING_TIME));
        }
        if (this.fireTicks < ticks) {
            this.setFireTicks(ticks);
        }
    }
}
