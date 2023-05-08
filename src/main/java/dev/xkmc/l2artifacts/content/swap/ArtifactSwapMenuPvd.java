package dev.xkmc.l2artifacts.content.swap;

import dev.xkmc.l2artifacts.init.registrate.ArtifactMenuRegistry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkHooks;

public record ArtifactSwapMenuPvd(ServerPlayer player,
								  InteractionHand hand,
								  ItemStack stack) implements MenuProvider {

	@Override
	public Component getDisplayName() {
		return stack.getHoverName();
	}

	@Override
	public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
		return new ArtifactSwapMenu(ArtifactMenuRegistry.MT_SWAP.get(), id, inventory, hand);
	}

	public void writeBuffer(FriendlyByteBuf buf) {
		buf.writeInt(hand == InteractionHand.MAIN_HAND ? 0 : 1);
	}

	public void open() {
		NetworkHooks.openScreen(player, this, this::writeBuffer);
	}

}
