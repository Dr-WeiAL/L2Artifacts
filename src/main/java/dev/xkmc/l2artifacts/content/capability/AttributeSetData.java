package dev.xkmc.l2artifacts.content.capability;

import dev.xkmc.l2library.serial.SerialClass;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.player.Player;

import java.util.ArrayList;
import java.util.UUID;

@SerialClass
public class AttributeSetData extends SetEffectData {

	public record AttributePair(Attribute attr, UUID id) {

	}

	@SerialClass.SerialField
	public ArrayList<AttributePair> list = new ArrayList<>();

	@Override
	protected void remove(Player player) {
		for (var attr : list) {
			var ins = player.getAttribute(attr.attr());
			if (ins != null) {
				ins.removeModifier(attr.id());
			}
		}
	}
}
