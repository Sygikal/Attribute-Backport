package dev.sygii.attributes.util;

import dev.sygii.attributes.AttributeBackport;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class ReachUtil {

    public static double getReachDistance(final LivingEntity entity, final double baseReachDistance) {
        @Nullable final var reachDistance = entity.getAttributeInstance(AttributeBackport.PLAYER_BLOCK_INTERACTION_RANGE);
        return (reachDistance != null) ? (reachDistance.getValue()) : baseReachDistance;
    }

    public static double getSquaredReachDistance(final LivingEntity entity, final double sqBaseReachDistance) {
        final var reachDistance = getReachDistance(entity, Math.sqrt(sqBaseReachDistance));
        return reachDistance * reachDistance;
    }

    public static double getSquaredReachDistanceBonus(final LivingEntity entity, final double sqBaseReachDistance) {
        final var reachDistance = getReachDistance(entity, Math.sqrt(sqBaseReachDistance)) + 1.5d;
        return reachDistance * reachDistance;
    }

    public static double getSquaredReachDistanceBonus2(final LivingEntity entity, final double sqBaseReachDistance) {
        final var reachDistance = getReachDistance(entity, Math.sqrt(sqBaseReachDistance)) + 3.5d;
        return reachDistance * reachDistance;
    }

    public static double getAttackRange(final LivingEntity entity, final double baseAttackRange) {
        @Nullable final var attackRange = entity.getAttributeInstance(AttributeBackport.PLAYER_ENTITY_INTERACTION_RANGE);
        return (attackRange != null) ? (attackRange.getValue()) : baseAttackRange;
    }

    public static double getSquaredAttackRange(final LivingEntity entity, final double sqBaseAttackRange) {
        final var attackRange = getAttackRange(entity, Math.sqrt(sqBaseAttackRange));
        return attackRange * attackRange;
    }

    public static List<PlayerEntity> getPlayersWithinReach(final World world, final int x, final int y, final int z, final double baseReachDistance) {
        return getPlayersWithinReach(player -> true, world, x, y, z, baseReachDistance);
    }

    public static List<PlayerEntity> getPlayersWithinReach(final Predicate<PlayerEntity> viewerPredicate, final World world, final int x, final int y, final int z, final double baseReachDistance) {
        final List<PlayerEntity> playersWithinReach = new ArrayList<>(0);
        for (final PlayerEntity player : world.getPlayers()) {
            if (viewerPredicate.test(player)) {
                final var reach = getReachDistance(player, baseReachDistance);
                final var dx = (x + (reach/9)) - player.getX();
                final var dy = (y + (reach/9)) - player.getEyeY();
                final var dz = (z + (reach/9)) - player.getZ();
                if (((dx * dx) + (dy * dy) + (dz * dz)) <= (reach * reach)) {
                    playersWithinReach.add(player);
                }
            }
        }
        return playersWithinReach;
    }

    public static boolean isWithinAttackRange(final PlayerEntity player, final Entity entity) {
        return player.squaredDistanceTo(entity) <= getSquaredAttackRange(player, 64.0);
    }
}
