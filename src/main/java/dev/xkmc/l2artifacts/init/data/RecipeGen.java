package dev.xkmc.l2artifacts.init.data;

import com.tterrag.registrate.providers.RegistrateRecipeProvider;
import com.tterrag.registrate.util.DataIngredient;
import com.tterrag.registrate.util.entry.ItemEntry;
import dev.xkmc.l2artifacts.content.core.BaseArtifact;
import dev.xkmc.l2artifacts.init.L2Artifacts;
import dev.xkmc.l2artifacts.init.registrate.entries.SetEntry;
import dev.xkmc.l2artifacts.init.registrate.items.ArtifactItemRegistry;
import dev.xkmc.l2complements.init.L2Complements;
import dev.xkmc.l2complements.init.registrate.LCItems;
import dev.xkmc.l2library.base.recipe.ConditionalRecipeWrapper;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.data.recipes.LegacyUpgradeRecipeBuilder;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.SmithingTransformRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.tags.ITagManager;

import java.util.Objects;
import java.util.function.BiFunction;

@SuppressWarnings("removal")
public class RecipeGen {

	private static void craftCombine(RegistrateRecipeProvider pvd, ItemEntry<?>[] arr) {
		for (int i = 1; i < 5; i++) {
			ItemEntry<?> input = arr[i - 1];
			ItemEntry<?> output = arr[i];
			pvd.singleItemUnfinished(DataIngredient.items(input.get()), RecipeCategory.MISC, output, 2, 1)
					.save(pvd, new ResourceLocation(L2Artifacts.MODID, "rank_up_" + output.getId().getPath()));
			pvd.singleItemUnfinished(DataIngredient.items(output.get()), RecipeCategory.MISC, input, 1, 2)
					.save(pvd, new ResourceLocation(L2Artifacts.MODID, "rank_down_" + output.getId().getPath()));
		}
	}

	public static void genRecipe(RegistrateRecipeProvider pvd) {
		ITagManager<Item> manager = Objects.requireNonNull(ForgeRegistries.ITEMS.tags());
		for (int i = 0; i < 5; i++) {
			int rank = i + 1;
			TagKey<Item> rank_tag = manager.createTagKey(new ResourceLocation(L2Artifacts.MODID, "rank_" + rank));
			ItemEntry<?> output = ArtifactItemRegistry.ITEM_EXP[i];
			pvd.singleItem(DataIngredient.tag(rank_tag), RecipeCategory.MISC, output, 1, 1);
		}

		craftCombine(pvd, ArtifactItemRegistry.ITEM_EXP);
		craftCombine(pvd, ArtifactItemRegistry.ITEM_STAT);
		craftCombine(pvd, ArtifactItemRegistry.ITEM_BOOST_MAIN);
		craftCombine(pvd, ArtifactItemRegistry.ITEM_BOOST_SUB);

		TagKey<Item> artifact = manager.createTagKey(new ResourceLocation(L2Artifacts.MODID, "artifact"));
		unlock(pvd, new ShapedRecipeBuilder(RecipeCategory.MISC, ArtifactItemRegistry.FILTER.get(), 1)::unlockedBy, Items.ENDER_PEARL)
				.pattern(" A ").pattern("LEL").pattern(" L ")
				.define('E', Items.ENDER_PEARL).define('L', Items.LEATHER).define('A', artifact)
				.save(pvd);

		//TODO mapping
		unlock(pvd, SmithingTransformRecipeBuilder.smithing(Ingredient.of(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE), Ingredient.of(ArtifactItemRegistry.FILTER.get()),
						Ingredient.of(Items.NETHERITE_INGOT),
						RecipeCategory.MISC,
						ArtifactItemRegistry.UPGRADED_POCKET.get())::unlocks,
				Items.NETHERITE_INGOT)
				.save(pvd, L2Artifacts.MODID + ":upgraded_pocket");

		//TODO mapping, removal
		unlock(pvd, LegacyUpgradeRecipeBuilder.smithing(Ingredient.of(ArtifactItemRegistry.FILTER.get()),
						Ingredient.of(Items.NETHERITE_INGOT),
						RecipeCategory.MISC,
						ArtifactItemRegistry.UPGRADED_POCKET.get())::unlocks,
				Items.NETHERITE_INGOT)
				.save(pvd, L2Artifacts.MODID + ":upgraded_pocket_old");

		// rank up recipes
		for (SetEntry<?> set : L2Artifacts.REGISTRATE.SET_LIST) {
			ItemEntry<BaseArtifact>[][] items = set.items;
			for (ItemEntry<BaseArtifact>[] slot : items) {
				int n = slot.length;
				for (int i = 1; i < n; i++) {
					BaseArtifact input = slot[i - 1].get();
					Item corner = ArtifactItemRegistry.ITEM_EXP[input.rank - 1].get();
					BaseArtifact output = slot[i].get();
					Item center = ArtifactItemRegistry.ITEM_EXP[output.rank - 1].get();
					craft(pvd, output, center, input, corner);
				}
			}
		}

		// conditionals
		{
			unlock(pvd, new ShapedRecipeBuilder(RecipeCategory.MISC, ArtifactItemRegistry.ITEM_STAT[0].get(), 4)::unlockedBy, LCItems.RESONANT_FEATHER.get())
					.pattern("AAA").pattern("BCB").pattern("AAA")
					.define('A', Items.GOLD_INGOT)
					.define('B', LCItems.SOUL_FLAME.get())
					.define('C', LCItems.RESONANT_FEATHER.get())
					.save(ConditionalRecipeWrapper.mod(pvd, L2Complements.MODID));

			unlock(pvd, new ShapedRecipeBuilder(RecipeCategory.MISC, ArtifactItemRegistry.ITEM_BOOST_MAIN[0].get(), 4)::unlockedBy, LCItems.FORCE_FIELD.get())
					.pattern("ABA").pattern("BCB").pattern("ABA")
					.define('A', Items.GOLD_INGOT)
					.define('B', LCItems.STORM_CORE.get())
					.define('C', LCItems.FORCE_FIELD.get())
					.save(ConditionalRecipeWrapper.mod(pvd, L2Complements.MODID));

			unlock(pvd, new ShapedRecipeBuilder(RecipeCategory.MISC, ArtifactItemRegistry.ITEM_BOOST_SUB[0].get(), 4)::unlockedBy, LCItems.EMERALD.get())
					.pattern("ABA").pattern("BCB").pattern("ABA")
					.define('A', Items.GOLD_INGOT)
					.define('B', LCItems.CAPTURED_WIND.get())
					.define('C', LCItems.EMERALD.get())
					.save(ConditionalRecipeWrapper.mod(pvd, L2Complements.MODID));
		}
	}

	public static void craft(RegistrateRecipeProvider pvd, Item output, Item center, Item input, Item corner) {
		unlock(pvd, new ShapedRecipeBuilder(RecipeCategory.MISC, output, 1)::unlockedBy, input)
				.pattern("CCC").pattern("BAB").pattern("CCC")
				.define('A', center)
				.define('B', input)
				.define('C', corner)
				.save(pvd);
	}

	public static <T> T unlock(RegistrateRecipeProvider pvd, BiFunction<String, InventoryChangeTrigger.TriggerInstance, T> func, Item item) {
		return func.apply("has_" + pvd.safeName(item), DataIngredient.items(item).getCritereon(pvd));
	}

}
