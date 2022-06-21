package dev.xkmc.l2artifacts.init.registrate;

import dev.xkmc.l2artifacts.content.core.ArtifactSet;
import dev.xkmc.l2artifacts.content.core.ArtifactSlot;
import dev.xkmc.l2artifacts.content.core.ArtifactStatType;
import dev.xkmc.l2artifacts.content.effects.SetEffect;
import dev.xkmc.l2library.base.L2Registrate;
import dev.xkmc.l2library.repack.registrate.util.entry.RegistryEntry;
import dev.xkmc.l2library.repack.registrate.util.nullness.NonNullSupplier;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

import static dev.xkmc.l2artifacts.init.L2Artifacts.REGISTRATE;
import static net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation.ADDITION;
import static net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation.MULTIPLY_TOTAL;

public class ArtifactTypeRegistry {

	public static L2Registrate.RegistryInstance<ArtifactSlot> SLOT = REGISTRATE.newRegistry("slot", ArtifactSlot.class);
	public static L2Registrate.RegistryInstance<ArtifactStatType> STAT_TYPE = REGISTRATE.newRegistry("stat_type", ArtifactStatType.class);
	public static L2Registrate.RegistryInstance<ArtifactSet> SET = REGISTRATE.newRegistry("set", ArtifactSet.class);
	public static L2Registrate.RegistryInstance<SetEffect> SET_EFFECT = REGISTRATE.newRegistry("set_effect", SetEffect.class);

	public static RegistryEntry<ArtifactSlot> SLOT_HEAD = regSlot("head", ArtifactSlot::new);
	public static RegistryEntry<ArtifactSlot> SLOT_NECKLACE = regSlot("necklace", ArtifactSlot::new);
	public static RegistryEntry<ArtifactSlot> SLOT_BRACELET = regSlot("bracelet", ArtifactSlot::new);
	public static RegistryEntry<ArtifactSlot> SLOT_BODY = regSlot("body", ArtifactSlot::new);
	public static RegistryEntry<ArtifactSlot> SLOT_BELT = regSlot("belt", ArtifactSlot::new);

	public static RegistryEntry<Attribute> CRIT_RATE = REGISTRATE.simple("crit_rate", ForgeRegistries.ATTRIBUTES.getRegistryKey(), () -> new RangedAttribute("attribute.name.crit_rate", 0, 0, 1).setSyncable(true));
	public static RegistryEntry<Attribute> CRIT_DMG = REGISTRATE.simple("crit_damage", ForgeRegistries.ATTRIBUTES.getRegistryKey(), () -> new RangedAttribute("attribute.name.crit_damage", 0, 0, 1000).setSyncable(true));

	public static RegistryEntry<ArtifactStatType> HEALTH_ADD = regStat("health_add", () -> Attributes.MAX_HEALTH, ADDITION, false);
	public static RegistryEntry<ArtifactStatType> ARMOR_ADD = regStat("armor_add", () -> Attributes.ARMOR, ADDITION, false);
	public static RegistryEntry<ArtifactStatType> TOUGH_ADD = regStat("tough_add", () -> Attributes.ARMOR_TOUGHNESS, ADDITION, false);
	public static RegistryEntry<ArtifactStatType> ATK_ADD = regStat("attack_add", () -> Attributes.ATTACK_DAMAGE, ADDITION, false);
	public static RegistryEntry<ArtifactStatType> REACH_ADD = regStat("reach_add", ForgeMod.ATTACK_RANGE, ADDITION, false);
	public static RegistryEntry<ArtifactStatType> CR_ADD = regStat("crit_rate_add", CRIT_RATE, ADDITION, true);
	public static RegistryEntry<ArtifactStatType> CD_ADD = regStat("crit_damage_add", CRIT_DMG, ADDITION, true);
	public static RegistryEntry<ArtifactStatType> ATK_MULT = regStat("attack_mult", () -> Attributes.ATTACK_DAMAGE, MULTIPLY_TOTAL, true);
	public static RegistryEntry<ArtifactStatType> SPEED_MULT = regStat("speed_mult", () -> Attributes.MOVEMENT_SPEED, MULTIPLY_TOTAL, true);
	public static RegistryEntry<ArtifactStatType> ATK_SPEED_MULT = regStat("attack_speed_mult", () -> Attributes.ATTACK_SPEED, MULTIPLY_TOTAL, true);

	public static void register() {

	}

	private static RegistryEntry<ArtifactSlot> regSlot(String id, NonNullSupplier<ArtifactSlot> slot) {
		return REGISTRATE.generic(SLOT, id, slot).defaultLang().register();
	}

	private static RegistryEntry<ArtifactStatType> regStat(String id, Supplier<Attribute> attr, AttributeModifier.Operation op, boolean usePercent) {
		return REGISTRATE.generic(STAT_TYPE, id, () -> new ArtifactStatType(attr, op, usePercent)).defaultLang().register();
	}

}
