package dev.xkmc.l2artifacts.content.core;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import dev.xkmc.l2library.serial.SerialClass;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@SerialClass
public class ArtifactStats {

	@SerialClass.SerialField
	public ArtifactSlot slot;

	@SerialClass.SerialField
	public int rank;

	@SerialClass.SerialField
	public int level;

	@SerialClass.SerialField
	public int exp;

	@SerialClass.SerialField
	public StatEntry main_stat;

	@SerialClass.SerialField
	public ArrayList<StatEntry> sub_stats = new ArrayList<>();

	public final Map<ArtifactStatType, StatEntry> map = new HashMap<>();

	@Deprecated
	public ArtifactStats() {

	}

	public ArtifactStats(ArtifactSlot slot, int rank, Random random) {
		this.slot = slot;
		this.rank = rank;
		slot.generate(this, random);
	}

	@SerialClass.OnInject
	public void onInject() {
		main_stat.init(slot);
		map.put(main_stat.type, main_stat);
		for (StatEntry ent : sub_stats) {
			ent.init(slot);
			map.put(ent.type, ent);
		}
	}

	public void add(StatEntry entry) {
		if (map.containsKey(entry.type))
			return;
		if (main_stat == null) {
			main_stat = entry;
		} else {
			sub_stats.add(entry);
		}
		entry.init(slot);
		map.put(entry.type, entry);
	}

	public void add(ArtifactStatType type, double value) {
		if (map.containsKey(type)) {
			map.get(type).value += value;
		} else {
			add(new StatEntry(slot, type, value));
		}
	}

	public Multimap<Attribute, AttributeModifier> buildAttributes() {
		ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
		for (StatEntry ent : map.values()) {
			ent.type.getModifier(builder, ent);
		}
		return builder.build();
	}

	public void addExp(int exp) {
		this.exp += exp;
		int max_level = ArtifactUpgradeManager.getMaxLevel(rank);
		while (true) {
			if (this.level < max_level) {
				break;
			}
			int max_exp = ArtifactUpgradeManager.getExpForLevel(rank, level);
			if (this.exp < max_exp) {
				break;
			}
			this.exp -= max_exp;
			this.level++;
			ArtifactUpgradeManager.onUpgrade(this);
		}
		if (this.level == max_level) {
			this.exp = 0;
		}
	}

}
