package dev.sygii.attributes.mixin;

import com.mojang.authlib.GameProfile;
import dev.sygii.attributes.AttributeBackport;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(ClientPlayerEntity.class)
public abstract class ClientPlayerEntityMixin extends AbstractClientPlayerEntity {

    public ClientPlayerEntityMixin(ClientWorld world, GameProfile profile) {
        super(world, profile);
    }

    @ModifyConstant(
            method = "tickMovement",
            require = 1, allow = 1, constant = @Constant(floatValue = 0.3f))
    public float modifySneakSpeed(float constant) {
        return (float)this.getAttributeValue(AttributeBackport.PLAYER_SNEAKING_SPEED);
    }

}
