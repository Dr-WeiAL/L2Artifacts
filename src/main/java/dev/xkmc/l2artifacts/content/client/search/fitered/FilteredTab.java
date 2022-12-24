package dev.xkmc.l2artifacts.content.client.search.fitered;

import dev.xkmc.l2artifacts.content.client.search.tabs.FilterTabBase;
import dev.xkmc.l2artifacts.content.client.search.tabs.FilterTabManager;
import dev.xkmc.l2artifacts.content.client.search.tabs.FilterTabToken;
import dev.xkmc.l2artifacts.network.NetworkManager;
import dev.xkmc.l2artifacts.network.SetFilterToServer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

public class FilteredTab extends FilterTabBase<FilteredTab> {

	public FilteredTab(FilterTabToken<FilteredTab> token, FilterTabManager manager, ItemStack stack, Component title) {
		super(token, manager, stack, title);
	}

	@Override
	public void onTabClicked() {
		NetworkManager.HANDLER.toServer(new SetFilterToServer(manager.token));
	}
}
