package dev.xkmc.l2artifacts.content.core;

import com.google.common.collect.ImmutableMultimap;
import dev.xkmc.l2artifacts.init.registrate.ArtifactRegistry;
import dev.xkmc.l2library.base.NamedEntry;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;

public class ArtifactStatType extends NamedEntry<ArtifactStatType> {

	private final Attribute attr;
	private final AttributeModifier.Operation op;

	public ArtifactStatType(Attribute attr, AttributeModifier.Operation op) {
		super(() -> ArtifactRegistry.STAT_TYPE);
		this.attr = attr;
		this.op = op;
	}

	public void getModifier(ImmutableMultimap.Builder<Attribute, AttributeModifier> builder, StatEntry entry) {
		builder.put(attr, new AttributeModifier(entry.id, entry.name, entry.value, op));
	}

}
