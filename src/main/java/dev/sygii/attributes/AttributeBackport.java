package dev.sygii.attributes;

import net.fabricmc.api.ModInitializer;

import net.minecraft.entity.attribute.ClampedEntityAttribute;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AttributeBackport implements ModInitializer {
	public static final String MOD_ID = "attribute-backport";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static final EntityAttribute PLAYER_BLOCK_BREAK_SPEED = register(
			"player.block_break_speed", (new ClampedEntityAttribute("attribute.name.player.block_break_speed", 1.0, (double)0.0, (double)1024.0)).setTracked(true));

	public static final EntityAttribute PLAYER_BLOCK_INTERACTION_RANGE = register(
			"player.block_interaction_range", (new ClampedEntityAttribute("attribute.name.player.block_interaction_range", 4.5, 0.0, 64.0)).setTracked(true));

	public static final EntityAttribute PLAYER_ENTITY_INTERACTION_RANGE = register(
			"player.entity_interaction_range", (new ClampedEntityAttribute("attribute.name.player.entity_interaction_range", 3.0, 0.0, 64.0)).setTracked(true));

	public static final EntityAttribute PLAYER_SUBMERGED_MINING_SPEED = register(
			"player.submerged_mining_speed", (new ClampedEntityAttribute("attribute.name.player.submerged_mining_speed", 0.2, 0.0, 20.0)).setTracked(true));

	public static final EntityAttribute PLAYER_MINING_EFFICIENCY = register(
			"player.mining_efficiency", (new ClampedEntityAttribute("attribute.name.player.mining_efficiency", 0.0, 0.0, 1024.0)).setTracked(true));

	public static final EntityAttribute GENERIC_BURNING_TIME = register(
			"generic.burning_time", (new ClampedEntityAttribute("attribute.name.generic.burning_time", 1.0, 0.0, 1024.0)).setTracked(true));

	public static final EntityAttribute GENERIC_OXYGEN_BONUS = register(
			"generic.oxygen_bonus", (new ClampedEntityAttribute("attribute.name.generic.oxygen_bonus", 0.0, 0.0, 1024.0)).setTracked(true));

	public static final EntityAttribute PLAYER_SNEAKING_SPEED = register(
			"player.sneaking_speed", (new ClampedEntityAttribute("attribute.name.player.sneaking_speed", 0.3, 0.0, 1.0)).setTracked(true));

	public static final EntityAttribute PLAYER_SWEEPING_DAMAGE_RATIO = register(
			"player.sweeping_damage_ratio", (new ClampedEntityAttribute("attribute.name.player.sweeping_damage_ratio", 0.0, 0.0, 1.0)).setTracked(true));

	public static final EntityAttribute GENERIC_EXPLOSION_KNOCKBACK_RESISTANCE = register(
			"generic.explosion_knockback_resistance", (new ClampedEntityAttribute("attribute.name.generic.explosion_knockback_resistance", 0.0, 0.0, 1.0)).setTracked(true));

	public static final EntityAttribute GENERIC_WATER_MOVEMENT_EFFICIENCY = register(
			"generic.water_movement_efficiency", (new ClampedEntityAttribute("attribute.name.generic.water_movement_efficiency", 0.0, 0.0, 1.0)).setTracked(true));

	/*public static final EntityAttribute GENERIC_MAX_ABSORPTION = register(
			"generic.max_absorption", (new ClampedEntityAttribute("attribute.name.generic.max_absorption", (double)0.0F, (double)0.0F, (double)2048.0F)).setTracked(true));
*/
	@Override
	public void onInitialize() {
	}

	private static EntityAttribute register(String id, EntityAttribute attribute) {
		return Registry.register(Registries.ATTRIBUTE, Identifier.of(MOD_ID, id), attribute);
	}
}