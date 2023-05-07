package dev.xkmc.l2artifacts.content.config;

import dev.xkmc.l2artifacts.content.core.LinearFuncHandle;
import dev.xkmc.l2library.serial.config.BaseConfig;
import dev.xkmc.l2library.serial.config.CollectType;
import dev.xkmc.l2library.serial.config.ConfigCollect;
import dev.xkmc.l2serial.serialization.SerialClass;

import java.util.HashMap;

@SerialClass
public class LinearFuncConfig extends BaseConfig {

	public record Entry(double base, double slope) {
	}

	@SerialClass.SerialField
	@ConfigCollect(CollectType.MAP_OVERWRITE)
	public HashMap<LinearFuncHandle, Entry> map = new HashMap<>();

}
