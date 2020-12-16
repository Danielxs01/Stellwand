package de.danielxs01.stellwand.content.items;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class SmallLogo extends Item {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List lore, boolean par4) {
		lore.add("Useless Item :D");
	}

}
