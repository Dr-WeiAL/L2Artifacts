package dev.xkmc.l2artifacts.content.effects.elemental;

import dev.xkmc.l2artifacts.content.config.ArtifactSetConfig;
import dev.xkmc.l2artifacts.content.effects.SetEffect;
import dev.xkmc.l2artifacts.init.registrate.entries.LinearFuncEntry;
import dev.xkmc.l2library.base.effects.EffectUtil;
import dev.xkmc.l2library.init.events.attack.AttackCache;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class FrozeSlowEffect extends SetEffect {

	private final LinearFuncEntry factor, period, level;

	public FrozeSlowEffect(LinearFuncEntry factor, LinearFuncEntry period, LinearFuncEntry level) {
		super(0);
		this.factor = factor;
		this.period = period;
		this.level = level;
	}

	@Override
	public void playerHurtOpponentEvent(Player player, ArtifactSetConfig.Entry ent, int rank, AttackCache event) {
		if (player.isOnFire()) return;
		EffectUtil.addEffect(event.getAttackTarget(), new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN,
				(int) period.getFromRank(rank), (int) level.getFromRank(rank)), EffectUtil.AddReason.NONE, player);
	}

	@Override
	public void playerHurtEvent(Player player, ArtifactSetConfig.Entry ent, int rank, LivingHurtEvent event) {
		if (event.getSource().isFire()) {
			event.setAmount((float) (event.getAmount() * factor.getFromRank(rank)));
		}
	}
}
