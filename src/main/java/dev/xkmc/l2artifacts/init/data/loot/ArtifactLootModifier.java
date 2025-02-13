package dev.xkmc.l2artifacts.init.data.loot;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.LootModifier;
import org.jetbrains.annotations.NotNull;

public class ArtifactLootModifier extends LootModifier {

	public static final Codec<ArtifactLootModifier> CODEC = RecordCodecBuilder.create(i -> codecStart(i).and(i.group(
					Codec.INT.fieldOf("healthMin").forGetter(e -> e.healthMin),
					Codec.INT.fieldOf("healthMax").forGetter(e -> e.healthMax),
					Codec.DOUBLE.fieldOf("chance").forGetter(e -> e.chance),
					ItemStack.CODEC.fieldOf("result").forGetter(e -> e.result)))
			.apply(i, ArtifactLootModifier::new));

	private final int healthMin, healthMax;
	private final double chance;
	private final ItemStack result;

	public ArtifactLootModifier(int healthMin, int healthMax, double chance, ItemStack result, LootItemCondition... conditionsIn) {
		super(conditionsIn);
		this.healthMin = healthMin;
		this.healthMax = healthMax;
		this.chance = chance;
		this.result = result;
	}

	private ArtifactLootModifier(LootItemCondition[] conditionsIn, int healthMin, int healthMax, double chance, ItemStack result) {
		super(conditionsIn);
		this.healthMin = healthMin;
		this.healthMax = healthMax;
		this.chance = chance;
		this.result = result;
	}

	@Override
	protected @NotNull ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> list, LootContext context) {
		if (!context.hasParam(LootContextParams.THIS_ENTITY)) return list;
		Entity entity = context.getParam(LootContextParams.THIS_ENTITY);
		if (entity instanceof LivingEntity le && entity instanceof Enemy) {
			float health = le.getMaxHealth();
			if (chance > context.getRandom().nextDouble() && health + 1e-3 > healthMin &&
					(healthMax <= 0 || health + 1e-3 < healthMax)) {
				list.add(result.copy());
			}
		}
		return list;
	}

	@Override
	public Codec<ArtifactLootModifier> codec() {
		return CODEC;
	}

}
