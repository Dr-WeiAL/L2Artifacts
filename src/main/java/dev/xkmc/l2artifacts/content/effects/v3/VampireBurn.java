package dev.xkmc.l2artifacts.content.effects.v3;

import dev.xkmc.l2artifacts.content.config.ArtifactSetConfig;
import dev.xkmc.l2artifacts.content.effects.SetEffect;
import dev.xkmc.l2artifacts.init.registrate.entries.LinearFuncEntry;
import dev.xkmc.l2library.base.effects.EffectUtil;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import java.util.List;

public class VampireBurn extends SetEffect {

	private final LinearFuncEntry light;

	public VampireBurn(LinearFuncEntry lightLow) {
		super(0);
		this.light = lightLow;
	}

	@Override
	public List<MutableComponent> getDetailedDescription(int rank) {
		int lo = (int) Math.round(light.getFromRank(rank));
		return List.of(Component.translatable(getDescriptionId() + ".desc", lo));
	}

	@Override
	public void tick(Player player, ArtifactSetConfig.Entry ent, int rank, boolean enabled) {
		if (!enabled) return;
		if (player.getLevel().isClientSide()) return;
		int light = PlayerLight.playerUnderSun(player);
		Level level = player.getLevel();
		if (light > this.light.getFromRank(rank) && level.canSeeSky(player.getOnPos()) && level.isDay() && !fireImmune(player)) {
			if (player.getRemainingFireTicks() < 40) {
				player.setRemainingFireTicks(60);
			}
		}
		if (light <= this.light.getFromRank(rank)) {
			EffectUtil.refreshEffect(player, new MobEffectInstance(MobEffects.NIGHT_VISION, 40), EffectUtil.AddReason.SELF, player);
		}
	}

	private static boolean fireImmune(LivingEntity entity) {
		return entity.isInWaterRainOrBubble() ||
				entity.isInPowderSnow ||
				entity.wasInPowderSnow ||
				entity.fireImmune() ||
				entity.hasEffect(MobEffects.FIRE_RESISTANCE);
	}

}
