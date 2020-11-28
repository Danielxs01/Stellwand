package net.landofrails.stellwand.content.tabs;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import net.landofrails.stellwand.content.items.CustomItems;
import net.landofrails.stellwand.content.items.SmallLogo;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class CustomTabs {

	public static final CreativeTabs blockTab = new CustomBlockTab();
	public static final CreativeTabs itemTab = new CustomItemTab();
	public static final CreativeTabs stellwandTab = new CustomStellwandTab();

	private CustomTabs() {

	}

}

class CustomBlockTab extends CreativeTabs {

	public CustomBlockTab() {
		super("blocktab");
	}

	@Override
	public Item getTabIconItem() {
		Item item = Items.redstone;
		Optional<Item> optional = CustomItems.getItems().values().stream().filter(i -> i instanceof SmallLogo)
				.findFirst();
		return optional.isPresent() ? optional.get() : item;
	}

}

class CustomItemTab extends CreativeTabs {

	public CustomItemTab() {
		super("itemtab");
	}

	@Override
	public Item getTabIconItem() {
		Item item = Items.redstone;
		Optional<Item> optional = CustomItems.getItems().values().stream().filter(i -> i instanceof SmallLogo)
				.findFirst();
		return optional.isPresent() ? optional.get() : item;
	}

}

class CustomStellwandTab extends CreativeTabs {

	public CustomStellwandTab() {
		super("stellwandtab");
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void displayAllReleventItems(List list) {
		super.displayAllReleventItems(list);
		Collections.sort(list, new Comparator<ItemStack>() {

			@Override
			public int compare(ItemStack o1, ItemStack o2) {
				String name1 = o1.hasDisplayName() ? o1.getDisplayName() : o1.getUnlocalizedName();
				String name2 = o2.hasDisplayName() ? o2.getDisplayName() : o2.getUnlocalizedName();
				return name1.compareTo(name2);
			}

		});
	}

	@Override
	public Item getTabIconItem() {
		Item item = Items.redstone;
		Optional<Item> optional = CustomItems.getItems().values().stream().filter(i -> i instanceof SmallLogo)
				.findFirst();
		return optional.isPresent() ? optional.get() : item;
	}

}
