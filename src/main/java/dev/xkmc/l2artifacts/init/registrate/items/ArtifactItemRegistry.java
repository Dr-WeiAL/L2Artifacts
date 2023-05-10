package dev.xkmc.l2artifacts.init.registrate.items;

import com.tterrag.registrate.util.entry.ItemEntry;
import dev.xkmc.l2artifacts.content.misc.ArtifactChestItem;
import dev.xkmc.l2artifacts.content.misc.ExpItem;
import dev.xkmc.l2artifacts.content.misc.RandomArtifactItem;
import dev.xkmc.l2artifacts.content.misc.SelectArtifactItem;
import dev.xkmc.l2artifacts.content.swap.ArtifactSwapItem;
import dev.xkmc.l2artifacts.content.upgrades.StatContainerItem;
import dev.xkmc.l2artifacts.content.upgrades.Upgrade;
import dev.xkmc.l2artifacts.content.upgrades.UpgradeBoostItem;
import dev.xkmc.l2artifacts.init.L2Artifacts;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.tags.ITagManager;

import java.util.Objects;

import static dev.xkmc.l2artifacts.init.L2Artifacts.REGISTRATE;

@SuppressWarnings({"raw_type", "unchecked"})
public class ArtifactItemRegistry {

	public static final String[] RANK_NAME = {" -Common-", " =Rare=", " >Epic<", " »Legendary«", " -»Godly«-"};

	static {
		REGISTRATE.creativeModeTab("artifacts", b -> b
				.icon(ArtifactItemRegistry.SELECT::asStack)
				.title(Component.translatable("itemGroup.l2artifacts.artifacts")));
	}

	public static final ItemEntry<SelectArtifactItem> SELECT;
	public static final ItemEntry<ArtifactChestItem> FILTER, UPGRADED_POCKET;
	public static final ItemEntry<ArtifactSwapItem> SWAP;
	public static final ItemEntry<RandomArtifactItem>[] RANDOM;
	public static final ItemEntry<ExpItem>[] ITEM_EXP;
	public static final ItemEntry<StatContainerItem>[] ITEM_STAT;
	public static final ItemEntry<UpgradeBoostItem>[] ITEM_BOOST_MAIN, ITEM_BOOST_SUB;

	static {
		ITagManager<Item> manager = Objects.requireNonNull(ForgeRegistries.ITEMS.tags());
		SELECT = REGISTRATE.item("select", SelectArtifactItem::new)
				.defaultModel().lang("Artifact Selector (Creative)").register();
		FILTER = REGISTRATE.item("filter", ArtifactChestItem::new)
				.defaultModel().lang("Artifact Pocket").register();
		SWAP = REGISTRATE.item("swap", ArtifactSwapItem::new)
				.defaultModel().lang("Artifact Quick Swap").register();
		UPGRADED_POCKET = REGISTRATE.item("upgraded_pocket", ArtifactChestItem::new)
				.defaultModel().lang("Upgraded Artifact Pocket").register();
		int n = 5;
		RANDOM = new ItemEntry[n];
		for (int i = 0; i < n; i++) {
			int r = i + 1;
			TagKey<Item> artifact = manager.createTagKey(new ResourceLocation(L2Artifacts.MODID, "artifact"));
			TagKey<Item> rank_tag = manager.createTagKey(new ResourceLocation(L2Artifacts.MODID, "rank_" + r));
			RANDOM[i] = REGISTRATE.item("random_" + r, p -> new RandomArtifactItem(p, r))
					.model((ctx, pvd) -> pvd.getBuilder(ctx.getName()).parent(new ModelFile.UncheckedModelFile("item/generated"))
							.texture("layer0", new ResourceLocation(L2Artifacts.MODID, "item/rank/" + r))
							.texture("layer1", new ResourceLocation(L2Artifacts.MODID, "item/random")))
					.tag(rank_tag, artifact)
					.lang("Random Artifact" + RANK_NAME[i]).register();
		}
		ITEM_EXP = new ItemEntry[n];
		for (int i = 0; i < n; i++) {
			int r = i + 1;
			ITEM_EXP[i] = REGISTRATE.item("artifact_experience_" + r, p -> new ExpItem(p, r))
					.model((ctx, pvd) -> pvd.getBuilder(ctx.getName()).parent(new ModelFile.UncheckedModelFile("item/generated"))
							.texture("layer0", new ResourceLocation(L2Artifacts.MODID, "item/rank/" + r))
							.texture("layer1", new ResourceLocation(L2Artifacts.MODID, "item/artifact_experience")))
					.lang("Artifact Experience" + RANK_NAME[i]).register();
		}
		ITEM_STAT = new ItemEntry[n];
		for (int i = 0; i < n; i++) {
			int r = i + 1;
			ITEM_STAT[i] = REGISTRATE.item("stat_container_" + r, p -> new StatContainerItem(p, r))
					.model((ctx, pvd) -> pvd.getBuilder(ctx.getName()).parent(new ModelFile.UncheckedModelFile("item/generated"))
							.texture("layer0", new ResourceLocation(L2Artifacts.MODID, "item/rank/" + r))
							.texture("layer1", new ResourceLocation(L2Artifacts.MODID, "item/stat_container")))
					.lang("Stat Container" + RANK_NAME[i]).register();
		}
		ITEM_BOOST_MAIN = new ItemEntry[n];
		for (int i = 0; i < n; i++) {
			int r = i + 1;
			ITEM_BOOST_MAIN[i] = REGISTRATE.item("boost_main_" + r, p -> new UpgradeBoostItem(p, r, Upgrade.Type.BOOST_MAIN_STAT))
					.model((ctx, pvd) -> pvd.getBuilder(ctx.getName()).parent(new ModelFile.UncheckedModelFile("item/generated"))
							.texture("layer0", new ResourceLocation(L2Artifacts.MODID, "item/rank/" + r))
							.texture("layer1", new ResourceLocation(L2Artifacts.MODID, "item/boost_main")))
					.lang("Main Stat Booster" + RANK_NAME[i]).register();
		}
		ITEM_BOOST_SUB = new ItemEntry[n];
		for (int i = 0; i < n; i++) {
			int r = i + 1;
			ITEM_BOOST_SUB[i] = REGISTRATE.item("boost_sub_" + r, p -> new UpgradeBoostItem(p, r, Upgrade.Type.BOOST_SUB_STAT))
					.model((ctx, pvd) -> pvd.getBuilder(ctx.getName()).parent(new ModelFile.UncheckedModelFile("item/generated"))
							.texture("layer0", new ResourceLocation(L2Artifacts.MODID, "item/rank/" + r))
							.texture("layer1", new ResourceLocation(L2Artifacts.MODID, "item/boost_sub")))
					.lang("Sub Stat Booster" + RANK_NAME[i]).register();
		}
	}

	static {
		LAItem0.register();
		LAItem1.register();
		LAItem2.register();
		LAItem3.register();
		LAItem4.register();
	}

	public static void register() {

	}

}