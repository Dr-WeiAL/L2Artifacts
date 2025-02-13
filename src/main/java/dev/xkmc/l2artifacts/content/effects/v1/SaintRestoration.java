package dev.xkmc.l2artifacts.content.effects.v1;

import dev.xkmc.l2artifacts.content.config.ArtifactSetConfig;
import dev.xkmc.l2artifacts.content.effects.core.PlayerOnlySetEffect;
import dev.xkmc.l2artifacts.init.registrate.entries.LinearFuncEntry;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.player.Player;

import java.util.List;

import static net.minecraft.world.item.ItemStack.ATTRIBUTE_MODIFIER_FORMAT;

public class SaintRestoration extends PlayerOnlySetEffect {

	private final LinearFuncEntry val;

	public SaintRestoration(LinearFuncEntry val) {
		super(0);
		this.val = val;
	}

	@Override
	public void tick(Player player, ArtifactSetConfig.Entry ent, int rank, boolean enabled) {
		if (!enabled)
			return;
		int period = (int) val.getFromRank(rank);
		if (player.tickCount % period == 0 && player.getMainHandItem().isEmpty()) {
			if (player.getHealth() < player.getMaxHealth()) {
				player.heal(1);
			} else {
				List<Player> list = player.level().getEntitiesOfClass(Player.class, player.getBoundingBox().inflate(32));
				Player min = null;
				double health = 1e6;
				for (Player p : list) {
					if (p.isAlive() && p.getHealth() < health) {
						min = p;
						health = p.getHealth();
					}
				}
				if (min != null) {
					min.heal(1);
				}
			}
		}
	}

	@Override
	public List<MutableComponent> getDetailedDescription(int rank) {
		double period = val.getFromRank(rank) / 20d;
		return List.of(Component.translatable(getDescriptionId() + ".desc", ATTRIBUTE_MODIFIER_FORMAT.format(period)));
	}

}
