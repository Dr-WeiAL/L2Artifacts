package dev.xkmc.l2artifacts.content.effects.v4;

import dev.xkmc.l2artifacts.content.effects.attribute.AttributeSetData;
import dev.xkmc.l2serial.serialization.SerialClass;

@SerialClass
public class LuckAttackData extends AttributeSetData {

	@SerialClass.SerialField
	public int count;

}
