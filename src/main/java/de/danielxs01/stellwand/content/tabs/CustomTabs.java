package de.danielxs01.stellwand.content.tabs;

import java.util.Optional;

import de.danielxs01.stellwand.content.items.CustomItems;
import de.danielxs01.stellwand.content.items.SmallLogo;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

public class CustomTabs {

	public static final CreativeTabs stellwandTab = new CustomStellwandTab();

	private CustomTabs() {

	}

}

class CustomStellwandTab extends CreativeTabs {

	public CustomStellwandTab() {
		super("stellwandtab");
	}

	@Override
	public Item getTabIconItem() {
		Item item = Items.redstone;
		Optional<Item> optional = CustomItems.getItems().values().stream().filter(i -> i instanceof SmallLogo)
				.findFirst();
		return optional.isPresent() ? optional.get() : item;
	}

}
