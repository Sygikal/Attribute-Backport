package dev.sygii.attributes.mixin.reach;

import dev.sygii.attributes.AttributeBackport;
import dev.sygii.attributes.util.ReachUtil;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Debug(export=true)
@Mixin(PlayerEntity.class)
public abstract class ReachPlayerEntityMixin extends LivingEntity {

    protected ReachPlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(
            method = "createPlayerAttributes",
            require = 1, allow = 1, at = @At("RETURN"))
    private static void addAttributes(final CallbackInfoReturnable<DefaultAttributeContainer.Builder> info) {
        info.getReturnValue().add(AttributeBackport.PLAYER_BLOCK_INTERACTION_RANGE)
                .add(AttributeBackport.PLAYER_ENTITY_INTERACTION_RANGE);
    }

    @ModifyConstant(method = "attack(Lnet/minecraft/entity/Entity;)V", constant = @Constant(doubleValue = 9.0))
    private double getActualAttackRange(final double attackRange) {
        return ReachUtil.getSquaredAttackRange(this, attackRange);
    }

}
