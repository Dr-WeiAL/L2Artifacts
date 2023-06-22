package dev.xkmc.l2artifacts.init.data;

import dev.xkmc.l2artifacts.content.config.ArtifactSetConfig;
import dev.xkmc.l2artifacts.content.config.LinearFuncConfig;
import dev.xkmc.l2artifacts.content.config.SlotStatConfig;
import dev.xkmc.l2artifacts.content.config.StatTypeConfig;
import dev.xkmc.l2artifacts.content.core.ArtifactSet;
import dev.xkmc.l2artifacts.content.core.ArtifactSlot;
import dev.xkmc.l2artifacts.content.core.ArtifactStatType;
import dev.xkmc.l2artifacts.init.L2Artifacts;
import dev.xkmc.l2artifacts.init.registrate.ArtifactTypeRegistry;
import dev.xkmc.l2artifacts.init.registrate.entries.SetEntry;
import dev.xkmc.l2artifacts.network.NetworkManager;
import dev.xkmc.l2library.serial.config.ConfigDataProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.Objects;
import java.util.function.Consumer;

public class ConfigGen extends ConfigDataProvider {

	public ConfigGen(DataGenerator generator) {
		super(generator, "Artifact Config");
	}

	@Override
	public void add(Collector map) {
		// Slot Stat Config
		{
			ArrayList<ArtifactStatType> all = new ArrayList<>();
			{
				all.add(ArtifactTypeRegistry.HEALTH_ADD.get());
				all.add(ArtifactTypeRegistry.ARMOR_ADD.get());
				all.add(ArtifactTypeRegistry.TOUGH_ADD.get());
				all.add(ArtifactTypeRegistry.ATK_ADD.get());
				all.add(ArtifactTypeRegistry.ATK_MULT.get());
				all.add(ArtifactTypeRegistry.CR_ADD.get());
				all.add(ArtifactTypeRegistry.CD_ADD.get());
				all.add(ArtifactTypeRegistry.REACH_ADD.get());
				all.add(ArtifactTypeRegistry.ATK_SPEED_MULT.get());
				all.add(ArtifactTypeRegistry.SPEED_MULT.get());
				all.add(ArtifactTypeRegistry.BOW_ADD.get());
			}
			{
				ArrayList<ArtifactStatType> list = new ArrayList<>();
				list.add(ArtifactTypeRegistry.HEALTH_ADD.get());
				list.add(ArtifactTypeRegistry.ARMOR_ADD.get());
				list.add(ArtifactTypeRegistry.SPEED_MULT.get());
				list.add(ArtifactTypeRegistry.CR_ADD.get());
				list.add(ArtifactTypeRegistry.TOUGH_ADD.get());
				addSlotStat(map, ArtifactTypeRegistry.SLOT_BODY.get(), list, all);
			}
			{
				ArrayList<ArtifactStatType> list = new ArrayList<>();
				list.add(ArtifactTypeRegistry.ATK_ADD.get());
				list.add(ArtifactTypeRegistry.ATK_MULT.get());
				list.add(ArtifactTypeRegistry.BOW_ADD.get());
				list.add(ArtifactTypeRegistry.CD_ADD.get());
				list.add(ArtifactTypeRegistry.REACH_ADD.get());
				addSlotStat(map, ArtifactTypeRegistry.SLOT_BRACELET.get(), list, all);
			}
			{
				ArrayList<ArtifactStatType> list = new ArrayList<>();
				list.add(ArtifactTypeRegistry.HEALTH_ADD.get());
				list.add(ArtifactTypeRegistry.ARMOR_ADD.get());
				list.add(ArtifactTypeRegistry.ATK_ADD.get());
				list.add(ArtifactTypeRegistry.ATK_MULT.get());
				list.add(ArtifactTypeRegistry.BOW_ADD.get());

				list.add(ArtifactTypeRegistry.CR_ADD.get());
				list.add(ArtifactTypeRegistry.CD_ADD.get());
				addSlotStat(map, ArtifactTypeRegistry.SLOT_NECKLACE.get(), list, all);
			}
			{
				ArrayList<ArtifactStatType> list = new ArrayList<>();
				list.add(ArtifactTypeRegistry.HEALTH_ADD.get());
				list.add(ArtifactTypeRegistry.ARMOR_ADD.get());
				list.add(ArtifactTypeRegistry.ATK_ADD.get());
				list.add(ArtifactTypeRegistry.ATK_MULT.get());
				list.add(ArtifactTypeRegistry.BOW_ADD.get());

				list.add(ArtifactTypeRegistry.ATK_SPEED_MULT.get());
				list.add(ArtifactTypeRegistry.SPEED_MULT.get());
				addSlotStat(map, ArtifactTypeRegistry.SLOT_BELT.get(), list, all);
			}
			{
				ArrayList<ArtifactStatType> list = new ArrayList<>();
				list.add(ArtifactTypeRegistry.HEALTH_ADD.get());
				list.add(ArtifactTypeRegistry.ARMOR_ADD.get());
				list.add(ArtifactTypeRegistry.ATK_ADD.get());
				list.add(ArtifactTypeRegistry.ATK_MULT.get());
				list.add(ArtifactTypeRegistry.BOW_ADD.get());

				list.add(ArtifactTypeRegistry.TOUGH_ADD.get());
				list.add(ArtifactTypeRegistry.REACH_ADD.get());

				list.add(ArtifactTypeRegistry.CR_ADD.get());
				list.add(ArtifactTypeRegistry.CD_ADD.get());
				list.add(ArtifactTypeRegistry.ATK_SPEED_MULT.get());
				list.add(ArtifactTypeRegistry.SPEED_MULT.get());

				addSlotStat(map, ArtifactTypeRegistry.SLOT_HEAD.get(), list, all);
			}
		}

		// Stat Type Config

		addStatType(map, ArtifactTypeRegistry.HEALTH_ADD.get(), 0.4);
		addStatType(map, ArtifactTypeRegistry.ARMOR_ADD.get(), 0.4);
		addStatType(map, ArtifactTypeRegistry.TOUGH_ADD.get(), 0.2);
		addStatType(map, ArtifactTypeRegistry.ATK_ADD.get(), 0.4);
		addStatType(map, ArtifactTypeRegistry.ATK_MULT.get(), 0.02);
		addStatType(map, ArtifactTypeRegistry.CR_ADD.get(), 0.01);
		addStatType(map, ArtifactTypeRegistry.CD_ADD.get(), 0.02);
		addStatType(map, ArtifactTypeRegistry.REACH_ADD.get(), 0.02);
		addStatType(map, ArtifactTypeRegistry.SPEED_MULT.get(), 0.01);
		addStatType(map, ArtifactTypeRegistry.ATK_SPEED_MULT.get(), 0.01);
		addStatType(map, ArtifactTypeRegistry.BOW_ADD.get(), 0.02);

		// Set Effect Config

		for (SetEntry<?> set : L2Artifacts.REGISTRATE.SET_LIST) {
			addArtifactSet(map, set.get(), set.builder);
		}

		// linear function handle

		for (var key : L2Artifacts.REGISTRATE.LINEAR_LIST.keySet()) {
			LinearFuncConfig config = new LinearFuncConfig();
			var list = L2Artifacts.REGISTRATE.LINEAR_LIST.get(key);
			for (var entry : list) {
				config.map.put(entry.get(), new LinearFuncConfig.Entry(entry.base, entry.slope));
			}
			map.add(NetworkManager.LINEAR, key, config);
		}
	}

	public static void addSlotStat(Collector map, ArtifactSlot slot, ArrayList<ArtifactStatType> main, ArrayList<ArtifactStatType> sub) {
		SlotStatConfig config = new SlotStatConfig();
		ResourceLocation rl = Objects.requireNonNull(slot.getRegistryName());
		config.available_main_stats.put(slot, main);
		config.available_sub_stats.put(slot, sub);
		map.add(NetworkManager.SLOT_STATS, rl, config);
	}

	private static void addStatType(Collector map, ArtifactStatType type, double base) {
		StatTypeConfig config = new StatTypeConfig();
		ResourceLocation rl = Objects.requireNonNull(type.getRegistryName());
		config.stats.put(type, genEntry(base, 0.2, 2));
		map.add(NetworkManager.STAT_TYPES, rl, config);
	}

	private static void addArtifactSet(Collector map, ArtifactSet set, Consumer<ArtifactSetConfig.SetBuilder> builder) {
		ResourceLocation rl = Objects.requireNonNull(set.getRegistryName());
		map.add(NetworkManager.ARTIFACT_SETS, rl, ArtifactSetConfig.construct(set, builder));
	}

	private static StatTypeConfig.Entry genEntry(double base, double sub, double factor) {
		StatTypeConfig.Entry entry = new StatTypeConfig.Entry();
		entry.base = base;
		entry.base_low = 1;
		entry.base_high = factor;
		entry.main_low = sub;
		entry.main_high = sub * factor;
		entry.sub_low = sub;
		entry.sub_high = sub * factor;
		return entry;
	}


}