package dev.xkmc.l2artifacts.init.registrate;

import dev.xkmc.l2artifacts.content.core.ArtifactSet;
import dev.xkmc.l2artifacts.content.effects.AttrSetEntry;
import dev.xkmc.l2artifacts.content.effects.AttributeSetEffect;
import dev.xkmc.l2artifacts.content.effects.ConditionalAttributeSetEffect;
import dev.xkmc.l2artifacts.content.effects.v1.*;
import dev.xkmc.l2artifacts.content.effects.v2.*;
import dev.xkmc.l2artifacts.content.effects.v3.*;
import dev.xkmc.l2artifacts.content.misc.ExpItem;
import dev.xkmc.l2artifacts.content.upgrades.StatContainerItem;
import dev.xkmc.l2artifacts.content.upgrades.Upgrade;
import dev.xkmc.l2artifacts.content.upgrades.UpgradeBoostItem;
import dev.xkmc.l2artifacts.init.L2Artifacts;
import dev.xkmc.l2artifacts.init.registrate.entries.LinearFuncEntry;
import dev.xkmc.l2artifacts.init.registrate.entries.SetEntry;
import dev.xkmc.l2library.repack.registrate.util.entry.ItemEntry;
import dev.xkmc.l2library.repack.registrate.util.entry.RegistryEntry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.model.generators.ModelFile;

import static dev.xkmc.l2artifacts.init.L2Artifacts.REGISTRATE;
import static dev.xkmc.l2artifacts.init.registrate.ArtifactTypeRegistry.*;
import static net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation.ADDITION;
import static net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation.MULTIPLY_TOTAL;

@SuppressWarnings({"raw_type", "unchecked"})
public class ArtifactItemRegistry {

	public static final Tab TAB_ARTIFACT = new Tab("artifacts");

	static {
		REGISTRATE.creativeModeTab(() -> TAB_ARTIFACT);
	}

	public static final ItemEntry<ExpItem>[] ITEM_EXP;
	public static final ItemEntry<StatContainerItem>[] ITEM_STAT;
	public static final ItemEntry<UpgradeBoostItem>[] ITEM_BOOST_MAIN, ITEM_BOOST_SUB;

	static {
		int n = 5;
		ITEM_EXP = new ItemEntry[n];
		for (int i = 0; i < n; i++) {
			int r = i + 1;
			ITEM_EXP[i] = REGISTRATE.item("artifact_experience_" + r, p -> new ExpItem(p, r))
					.model((ctx, pvd) -> pvd.getBuilder(ctx.getName()).parent(new ModelFile.UncheckedModelFile("item/generated"))
							.texture("layer0", new ResourceLocation(L2Artifacts.MODID, "item/artifact_experience"))
							.texture("layer1", new ResourceLocation(L2Artifacts.MODID, "item/rank/" + r)))
					.defaultLang().register();
		}
		ITEM_STAT = new ItemEntry[n];
		for (int i = 0; i < n; i++) {
			int r = i + 1;
			ITEM_STAT[i] = REGISTRATE.item("stat_container_" + r, p -> new StatContainerItem(p, r))
					.model((ctx, pvd) -> pvd.getBuilder(ctx.getName()).parent(new ModelFile.UncheckedModelFile("item/generated"))
							.texture("layer0", new ResourceLocation(L2Artifacts.MODID, "item/stat_container"))
							.texture("layer1", new ResourceLocation(L2Artifacts.MODID, "item/rank/" + r)))
					.defaultLang().register();
		}
		ITEM_BOOST_MAIN = new ItemEntry[n];
		for (int i = 0; i < n; i++) {
			int r = i + 1;
			ITEM_BOOST_MAIN[i] = REGISTRATE.item("boost_main_" + r, p -> new UpgradeBoostItem(p, r, Upgrade.Type.BOOST_MAIN_STAT))
					.model((ctx, pvd) -> pvd.getBuilder(ctx.getName()).parent(new ModelFile.UncheckedModelFile("item/generated"))
							.texture("layer0", new ResourceLocation(L2Artifacts.MODID, "item/boost_main"))
							.texture("layer1", new ResourceLocation(L2Artifacts.MODID, "item/rank/" + r)))
					.defaultLang().register();
		}
		ITEM_BOOST_SUB = new ItemEntry[n];
		for (int i = 0; i < n; i++) {
			int r = i + 1;
			ITEM_BOOST_SUB[i] = REGISTRATE.item("boost_sub_" + r, p -> new UpgradeBoostItem(p, r, Upgrade.Type.BOOST_SUB_STAT))
					.model((ctx, pvd) -> pvd.getBuilder(ctx.getName()).parent(new ModelFile.UncheckedModelFile("item/generated"))
							.texture("layer0", new ResourceLocation(L2Artifacts.MODID, "item/boost_sub"))
							.texture("layer1", new ResourceLocation(L2Artifacts.MODID, "item/rank/" + r)))
					.defaultLang().register();
		}
	}

	// bland
	public static final SetEntry<ArtifactSet> SET_GAMBLER = REGISTRATE.regSet("gambler", ArtifactSet::new, 1, 5, "Gambler Set", SLOT_HEAD, SLOT_NECKLACE, SLOT_BODY, SLOT_BRACELET, SLOT_BELT);
	public static final SetEntry<ArtifactSet> SET_BERSERKER = REGISTRATE.regSet("berserker", ArtifactSet::new, 1, 5, "Berserker Set", SLOT_HEAD, SLOT_NECKLACE, SLOT_BODY, SLOT_BRACELET, SLOT_BELT);
	public static final SetEntry<ArtifactSet> SET_ARCHER = REGISTRATE.regSet("archer", ArtifactSet::new, 1, 5, "Archer Set", SLOT_HEAD, SLOT_NECKLACE, SLOT_BODY, SLOT_BRACELET, SLOT_BELT);

	// v1
	public static final SetEntry<ArtifactSet> SET_SAINT = REGISTRATE.regSet("saint", ArtifactSet::new, 1, 5, "Saint Set", SLOT_HEAD, SLOT_NECKLACE, SLOT_BODY, SLOT_BRACELET, SLOT_BELT);
	public static final SetEntry<ArtifactSet> SET_PERFECTION = REGISTRATE.regSet("perfection", ArtifactSet::new, 1, 5, "Perfection Set", SLOT_HEAD, SLOT_NECKLACE, SLOT_BODY, SLOT_BRACELET, SLOT_BELT);
	public static final SetEntry<ArtifactSet> SET_DAMOCLES = REGISTRATE.regSet("damocles", ArtifactSet::new, 1, 5, "Sword of Damocles", SLOT_HEAD);
	public static final SetEntry<ArtifactSet> SET_PROTECTION = REGISTRATE.regSet("protection", ArtifactSet::new, 1, 5, "Never Falling Crown", SLOT_HEAD);

	// v2
	public static final SetEntry<ArtifactSet> SET_FROZE = REGISTRATE.regSet("froze", ArtifactSet::new, 1, 5, "Frozen Set", SLOT_HEAD, SLOT_NECKLACE, SLOT_BODY, SLOT_BRACELET, SLOT_BELT);
	public static final SetEntry<ArtifactSet> SET_EXECUTOR = REGISTRATE.regSet("executor", ArtifactSet::new, 1, 5, "Executor Set", SLOT_HEAD, SLOT_NECKLACE, SLOT_BODY, SLOT_BRACELET, SLOT_BELT);
	public static final SetEntry<ArtifactSet> SET_PHYSICAL = REGISTRATE.regSet("physical", ArtifactSet::new, 1, 5, "Courage Set", SLOT_HEAD, SLOT_NECKLACE, SLOT_BODY, SLOT_BRACELET, SLOT_BELT);
	public static final SetEntry<ArtifactSet> SET_WRATH = REGISTRATE.regSet("wrath", ArtifactSet::new, 1, 5, "Curse of Bad Luck", SLOT_HEAD, SLOT_NECKLACE, SLOT_BODY, SLOT_BRACELET, SLOT_BELT);

	// v3
	public static final SetEntry<ArtifactSet> SET_PHOTOSYN = REGISTRATE.regSet("photosynthesis", ArtifactSet::new, 1, 5, "Photosynthesis Hat", SLOT_HEAD);
	public static final SetEntry<ArtifactSet> SET_VAMPIRE = REGISTRATE.regSet("vampire", ArtifactSet::new, 1, 5, "Vampire Set", SLOT_HEAD, SLOT_NECKLACE, SLOT_BODY, SLOT_BRACELET, SLOT_BELT);
	public static final SetEntry<ArtifactSet> SET_SUN_BLOCK = REGISTRATE.regSet("sun_block", ArtifactSet::new, 1, 5, "Umbrella", SLOT_HEAD);
	public static final SetEntry<ArtifactSet> SET_GLUTTONY = REGISTRATE.regSet("gluttony", ArtifactSet::new, 1, 5, "Gluttony Set", SLOT_HEAD, SLOT_NECKLACE, SLOT_BODY, SLOT_BRACELET, SLOT_BELT);
	public static final SetEntry<ArtifactSet> SET_FALLEN = REGISTRATE.regSet("fury_of_fallen", ArtifactSet::new, 1, 5, "Fury of Fallen", SLOT_HEAD, SLOT_NECKLACE, SLOT_BODY, SLOT_BRACELET, SLOT_BELT);


	// v1
	public static final RegistryEntry<AttributeSetEffect> EFF_GAMBLER_3, EFF_GAMBLER_5,
			EFF_BERSERKER_3, EFF_BERSERKER_5,
			EFF_ARCHER_3, EFF_ARCHER_5;

	static {

		// gambler set
		{
			LinearFuncEntry cr3 = REGISTRATE.regLinear("gambler_3_crit_rate", -0.2, 0);
			LinearFuncEntry cd3 = REGISTRATE.regLinear("gambler_3_crit_damage", 0.2, 0.1);
			LinearFuncEntry cr5 = REGISTRATE.regLinear("gambler_5_crit_rate", 0.04, 0.02);
			LinearFuncEntry cd5 = REGISTRATE.regLinear("gambler_5_crit_damage", 0.08, 0.04);
			LinearFuncEntry luck = REGISTRATE.regLinear("gambler_5_luck", 1, 0.5);

			EFF_GAMBLER_3 = REGISTRATE.setEffect("gambler_3", () -> new AttributeSetEffect(
					new AttrSetEntry(CRIT_RATE, ADDITION, cr3, true),
					new AttrSetEntry(CRIT_DMG, ADDITION, cd3, true)
			)).lang("Pursuit of Bets").register();

			EFF_GAMBLER_5 = REGISTRATE.setEffect("gambler_5", () -> new AttributeSetEffect(
					new AttrSetEntry(CRIT_RATE, ADDITION, cr5, true),
					new AttrSetEntry(CRIT_DMG, ADDITION, cd5, true),
					new AttrSetEntry(() -> Attributes.LUCK, ADDITION, luck, false)
			)).lang("Bless of Luck").register();
		}

		// berserker set
		{
			LinearFuncEntry ar3 = REGISTRATE.regLinear("berserker_3_armor", -10, 0);
			LinearFuncEntry atk3 = REGISTRATE.regLinear("berserker_3_attack", 0.2, 0.1);
			LinearFuncEntry speed = REGISTRATE.regLinear("berserker_5_speed", 0.04, 0.02);
			LinearFuncEntry haste = REGISTRATE.regLinear("berserker_5_attack_speed", 0.04, 0.02);
			LinearFuncEntry cd5 = REGISTRATE.regLinear("berserker_5_crit_damage", 0.04, 0.02);

			EFF_BERSERKER_3 = REGISTRATE.setEffect("berserker_3", () -> new AttributeSetEffect(
					new AttrSetEntry(() -> Attributes.ARMOR, ADDITION, ar3, false),
					new AttrSetEntry(() -> Attributes.ATTACK_DAMAGE, MULTIPLY_TOTAL, atk3, true)
			)).lang("Unpolished Bruteforce").register();

			EFF_BERSERKER_5 = REGISTRATE.setEffect("berserker_5", () -> new AttributeSetEffect(
					new AttrSetEntry(() -> Attributes.MOVEMENT_SPEED, MULTIPLY_TOTAL, speed, true),
					new AttrSetEntry(() -> Attributes.ATTACK_SPEED, MULTIPLY_TOTAL, haste, true),
					new AttrSetEntry(CRIT_DMG, ADDITION, cd5, true)
			)).lang("Subconscious Fight").register();
		}

		// archer set
		{

			LinearFuncEntry atk3 = REGISTRATE.regLinear("archer_3_attack", -0.4, 0);
			LinearFuncEntry bow3 = REGISTRATE.regLinear("archer_3_bow", 0.4, 0.2);
			LinearFuncEntry haste = REGISTRATE.regLinear("archer_5_attack_speed", -0.4, 0);
			LinearFuncEntry cr5 = REGISTRATE.regLinear("archer_5_crit_rate", 0.4, 0.2);

			EFF_ARCHER_3 = REGISTRATE.setEffect("archer_3", () -> new AttributeSetEffect(
					new AttrSetEntry(() -> Attributes.ATTACK_DAMAGE, MULTIPLY_TOTAL, atk3, true),
					new AttrSetEntry(BOW_STRENGTH, ADDITION, bow3, true)
			)).lang("Specialty of Archer").register();

			EFF_ARCHER_5 = REGISTRATE.setEffect("archer_5", () -> new AttributeSetEffect(
					new AttrSetEntry(() -> Attributes.ATTACK_SPEED, MULTIPLY_TOTAL, haste, true),
					new AttrSetEntry(CRIT_RATE, ADDITION, cr5, true)
			)).lang("Focus of Archer").register();
		}

	}

	public static final RegistryEntry<PerfectionAbsorption> EFF_PERFECTION_ABSORPTION;
	public static final RegistryEntry<PerfectionProtection> EFF_PERFECTION_PROTECTION;
	public static final RegistryEntry<SaintReduction> EFF_SAINT_REDUCTION;
	public static final RegistryEntry<SaintRestoration> EFF_SAINT_RESTORATION;
	public static final RegistryEntry<DamoclesSword> EFF_DAMOCLES;
	public static final RegistryEntry<ProtectionResistance> EFF_PROTECTION_RESISTANCE;

	// v2
	public static final RegistryEntry<FrozeSlowEffect> EFF_FROZE_SLOW;
	public static final RegistryEntry<FrozeBreakEffect> EFF_FROZE_BREAK;
	public static final RegistryEntry<ExecutorSelfHurtEffect> EFF_EXECUTOR_SELF_HURT;
	public static final RegistryEntry<ExecutorLimitEffect> EFF_EXECUTOR_LIMIT;
	public static final RegistryEntry<PhysicalDamageEffect> EFF_PHYSICAL_DAMAGE;
	public static final RegistryEntry<AttributeSetEffect> EFF_PHYSICAL_ARMOR;
	public static final RegistryEntry<WrathEffect> EFF_WRATH_POISON, EFF_WRATH_SLOW, EFF_WRATH_FIRE;

	// v3
	public static final RegistryEntry<Photosynthesisffect> EFF_PHOTOSYN;
	public static final RegistryEntry<VampireBurn> EFF_VAMPIRE_BURN;
	public static final RegistryEntry<VampireHeal> EFF_VAMPIRE_HEAL;
	public static final RegistryEntry<SunBlockMask> EFF_SUN_BLOCK;
	public static final RegistryEntry<ConditionalAttributeSetEffect> EFF_GLUTTONY_FAST;
	public static final RegistryEntry<GluttonyHeal> EFF_GLUTTONY_HEAL;
	public static final RegistryEntry<ConditionalAttributeSetEffect> EFF_FALLEN_1, EFF_FALLEN_2, EFF_FALLEN_3, EFF_FALLEN_4, EFF_FALLEN_5;

	static {

		// perfection
		{
			LinearFuncEntry period = REGISTRATE.regLinear("perfection_absorption_period", 100, 0);
			LinearFuncEntry max = REGISTRATE.regLinear("perfection_absorption_max", 4, 2);
			LinearFuncEntry def = REGISTRATE.regLinear("perfection_protection", 0.2, 0.1);
			EFF_PERFECTION_ABSORPTION = REGISTRATE.setEffect("perfection_absorption", () -> new PerfectionAbsorption(period, max))
					.desc("Heart of Perfection",
							"When at full health, every %s seconds gain 1 point of absorption, maximum %s points."
					).register();
			EFF_PERFECTION_PROTECTION = REGISTRATE.setEffect("perfection_protection", () -> new PerfectionProtection(def))
					.desc("Eternity of Perfection",
							"When at full health, reduce damage by %s%%"
					).register();
		}

		// saint
		{
			LinearFuncEntry atk = REGISTRATE.regLinear("saint_reduction_atk", 0.25, 0);
			LinearFuncEntry def = REGISTRATE.regLinear("saint_reduction_def", 0.25, 0.05);
			LinearFuncEntry period = REGISTRATE.regLinear("saint_restoration", 100, -10);

			EFF_SAINT_REDUCTION = REGISTRATE.setEffect("saint_reduction", () -> new SaintReduction(atk, def))
					.desc("Sympathy of Saint",
							"Direct damage dealt reduce to %s%%, damage taken reduce to %s%%"
					).register();
			EFF_SAINT_RESTORATION = REGISTRATE.setEffect("saint_restoration", () -> new SaintRestoration(period))
					.desc("Bless of Holiness",
							"When have empty main hand, restore health to oneself or allies every %s seconds."
					).register();
		}

		//froze
		{
			LinearFuncEntry damage = REGISTRATE.regLinear("froze_slow_fire_damage", 1.2, 0);
			LinearFuncEntry period = REGISTRATE.regLinear("froze_slow_period", 80, 40);
			LinearFuncEntry level = REGISTRATE.regLinear("froze_slow_level", 0, 1);
			LinearFuncEntry factor = REGISTRATE.regLinear("froze_break", 0.2, 0.1);
			EFF_FROZE_SLOW = REGISTRATE.setEffect("froze_slow", () -> new FrozeSlowEffect(damage, period, level))
					.desc("Frozen Blade",
							"Take %s%% fire damage. When not on fire, apply level %s slow effect on attack target for %s seconds"
					).register();
			EFF_FROZE_BREAK = REGISTRATE.setEffect("froze_break", () -> new FrozeBreakEffect(factor))
					.desc("Ice Breaker",
							"Attacks targetting slowed enemy will have %s%% more damage"
					).register();
		}

		//executor
		{
			LinearFuncEntry atk = REGISTRATE.regLinear("executor_attack", 0.3, 0.15);
			LinearFuncEntry hurt = REGISTRATE.regLinear("executor_self_hurt", 0.2, 0);
			LinearFuncEntry factor = REGISTRATE.regLinear("executor_limit", 0.3, -0.05);

			EFF_EXECUTOR_SELF_HURT = REGISTRATE.setEffect("executor_self_hurt", () -> new ExecutorSelfHurtEffect(
					new AttrSetEntry(() -> Attributes.ATTACK_DAMAGE,
							AttributeModifier.Operation.MULTIPLY_TOTAL, atk, true),
					hurt)).desc("Brutal Execution",
					"When kill enemies, deal real damage to oneself equal to %s%% of enemies' max health."
			).register();
			EFF_EXECUTOR_LIMIT = REGISTRATE.setEffect("executor_limit", () -> new ExecutorLimitEffect(factor))
					.desc("Cold Hearted",
							"The damage dealt to oneself will be capped to one's max health, and then reduced to %s%%"
					).register();
		}

		// physical
		{
			LinearFuncEntry atk = REGISTRATE.regLinear("physical_attack", 0.2, 0.1);
			LinearFuncEntry armor = REGISTRATE.regLinear("physical_armor", 4, 2);
			LinearFuncEntry factor = REGISTRATE.regLinear("physical_reduce_magic", 0.5, 0);

			EFF_PHYSICAL_DAMAGE = REGISTRATE.setEffect("physical_damage", () -> new PhysicalDamageEffect(
					new AttrSetEntry(() -> Attributes.ATTACK_DAMAGE,
							AttributeModifier.Operation.MULTIPLY_TOTAL, atk, true),
					factor)).desc("Barbaric Attack",
					"Magical damage dealt will be reduced to %s%%"
			).register();
			EFF_PHYSICAL_ARMOR = REGISTRATE.setEffect("physical_armor", () -> new AttributeSetEffect(
					new AttrSetEntry(() -> Attributes.ARMOR,
							ADDITION, armor, false)
			)).lang("Survival Instinct").register();
		}

		// wrath
		{
			LinearFuncEntry dec = REGISTRATE.regLinear("wrath_decrease", 0.8, 0);
			LinearFuncEntry inc = REGISTRATE.regLinear("wrath_increase", 1.2, 0.1);
			EFF_WRATH_POISON = REGISTRATE.setEffect("wrath_poison", () -> new WrathEffect(e -> e.hasEffect(MobEffects.POISON), dec, inc))
					.desc("Bad Day Encounters Bad Luck",
							"When target is poisoned, increase damage to %s%%. Otherwise, decrease damage to %s%%."
					).register();
			EFF_WRATH_SLOW = REGISTRATE.setEffect("wrath_slow", () -> new WrathEffect(e -> e.hasEffect(MobEffects.MOVEMENT_SLOWDOWN), dec, inc))
					.desc("Snow Storm Encounters Blitz Winter",
							"When target is slowed, increase damage to %s%%. Otherwise, decrease damage to %s%%."
					).register();
			EFF_WRATH_FIRE = REGISTRATE.setEffect("wrath_fire", () -> new WrathEffect(Entity::isOnFire, dec, inc))
					.desc("Emergency Encounters Unwanted Fight",
							"When target is on fire, increase damage to %s%%. Otherwise, decrease damage to %s%%."
					).register();

		}

		// vampire
		{
			LinearFuncEntry lo = REGISTRATE.regLinear("vampire_burn_low", 3, 1);
			LinearFuncEntry light = REGISTRATE.regLinear("vampire_heal_light", 4, 1);
			LinearFuncEntry amplify = REGISTRATE.regLinear("vampire_heal_ratio", 0.2, 0.1);
			EFF_VAMPIRE_BURN = REGISTRATE.setEffect("vampire_burn", () -> new VampireBurn(lo))
					.desc("Photophobic",
							"When under direct sunlight, burn. " +
									"Whe sunlight light level received is not higher than %s, get Night Vision."
					).register();
			EFF_VAMPIRE_HEAL = REGISTRATE.setEffect("vampire_heal", () -> new VampireHeal(light, amplify))
					.desc("Blood Thurst",
							"When sunlight light level received is not higher than %s, " +
									"when dealing damage, heal %s%% of that damage."
					).register();
		}

		// gluttony
		{
			LinearFuncEntry atk = REGISTRATE.regLinear("gluttony_attack", 0.2, 0.1);
			LinearFuncEntry swi = REGISTRATE.regLinear("gluttony_swing", 0.04, 0.02);
			LinearFuncEntry spe = REGISTRATE.regLinear("gluttony_speed", 0.1, 0.05);
			EFF_GLUTTONY_FAST = REGISTRATE.setEffect("gluttony_fast", () -> new ConditionalAttributeSetEffect(
					player -> player.hasEffect(MobEffects.HUNGER),
					new AttrSetEntry(() -> Attributes.ATTACK_DAMAGE, MULTIPLY_TOTAL, atk, true),
					new AttrSetEntry(() -> Attributes.ATTACK_SPEED, MULTIPLY_TOTAL, swi, true),
					new AttrSetEntry(() -> Attributes.MOVEMENT_SPEED, MULTIPLY_TOTAL, spe, true)
			)).desc("Hunger Strike", "When having Hunger effect:").register();

			LinearFuncEntry eat = REGISTRATE.regLinear("gluttony_eat", 2, 1);
			EFF_GLUTTONY_HEAL = REGISTRATE.setEffect("gluttony_eat", () -> new GluttonyHeal(eat))
					.desc("Flesh Eater", "When kill entities, restore food level by %s and saturation by %s")
					.register();
		}

		// fallen
		{
			LinearFuncEntry atk = REGISTRATE.regLinear("fury_of_fallen", 0.2, 0.1);
			EFF_FALLEN_1 = REGISTRATE.setEffect("fury_of_fallen_1", () -> new ConditionalAttributeSetEffect(
					player -> player.getHealth() <= player.getMaxHealth() * 0.5,
					new AttrSetEntry(() -> Attributes.ATTACK_DAMAGE, MULTIPLY_TOTAL, atk, true)
			)).desc("Furry of Fallen Lv.1", "When health is less than 50%:").register();
			EFF_FALLEN_2 = REGISTRATE.setEffect("fury_of_fallen_2", () -> new ConditionalAttributeSetEffect(
					player -> player.getHealth() <= player.getMaxHealth() * 0.4,
					new AttrSetEntry(() -> Attributes.ATTACK_DAMAGE, MULTIPLY_TOTAL, atk, true)
			)).desc("Furry of Fallen Lv.2", "When health is less than 40%:").register();
			EFF_FALLEN_3 = REGISTRATE.setEffect("fury_of_fallen_3", () -> new ConditionalAttributeSetEffect(
					player -> player.getHealth() <= player.getMaxHealth() * 0.3,
					new AttrSetEntry(() -> Attributes.ATTACK_DAMAGE, MULTIPLY_TOTAL, atk, true)
			)).desc("Furry of Fallen Lv.3", "When health is less than 30%:").register();
			EFF_FALLEN_4 = REGISTRATE.setEffect("fury_of_fallen_4", () -> new ConditionalAttributeSetEffect(
					player -> player.getHealth() <= player.getMaxHealth() * 0.2,
					new AttrSetEntry(() -> Attributes.ATTACK_DAMAGE, MULTIPLY_TOTAL, atk, true)
			)).desc("Furry of Fallen Lv.4", "When health is less than 20%:").register();
			EFF_FALLEN_5 = REGISTRATE.setEffect("fury_of_fallen_5", () -> new ConditionalAttributeSetEffect(
					player -> player.getHealth() <= player.getMaxHealth() * 0.1,
					new AttrSetEntry(() -> Attributes.ATTACK_DAMAGE, MULTIPLY_TOTAL, atk, true)
			)).desc("Furry of Fallen Lv.5", "When health is less than 10%:").register();
		}

		// singles
		{
			LinearFuncEntry amplify = REGISTRATE.regLinear("damocles", 1, 0.5);
			EFF_DAMOCLES = REGISTRATE.setEffect("damocles", () -> new DamoclesSword(amplify))
					.desc("Sword of Damocles",
							"When at full health, direct attack damage increase by %s%%. When below half health, die immediately."
					).register();
			EFF_PROTECTION_RESISTANCE = REGISTRATE.setEffect("protection_resistance", ProtectionResistance::new)
					.desc("Crown of Never Falling Soldier",
							"Damage taken reduced when health is low."
					).register();

			LinearFuncEntry period = REGISTRATE.regLinear("photosynthesis_period", 5, -1);
			LinearFuncEntry lo = REGISTRATE.regLinear("photosynthesis_low", 5, -1);
			LinearFuncEntry hi = REGISTRATE.regLinear("photosynthesis_high", 12, -1);
			EFF_PHOTOSYN = REGISTRATE.setEffect("photosynthesis", () -> new Photosynthesisffect(period, lo, hi))
					.desc("Flourishing Ring",
							"Every %s seconds, when under sun with light level of %s or higher, restore food level. When in light level lower than %s, increase exhaustion"
					).register();

			EFF_SUN_BLOCK = REGISTRATE.setEffect("sun_block", SunBlockMask::new)
					.desc("Sunlight Hat", "Block sunlight for the player").register();

		}
	}

	public static void register() {

	}

	public static class Tab extends CreativeModeTab {

		public Tab(String label) {
			super(L2Artifacts.MODID + "." + label);
		}

		@Override
		public ItemStack makeIcon() {
			return SET_GAMBLER.items[0][0].asStack();
		}
	}

}
