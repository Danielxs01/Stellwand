package net.landofrails.stellwand.content.items;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import cpw.mods.fml.common.registry.GameRegistry;
import net.landofrails.stellwand.Constants;
import net.landofrails.stellwand.content.items.stellwand.IStellwandItem;
import net.landofrails.stellwand.content.items.stellwand.MagnifyingGlass;
import net.landofrails.stellwand.content.tabs.CustomTabs;
import net.minecraft.item.Item;

public class CustomItems {

	private CustomItems() {

	}

	private static Map<String, Item> items = new HashMap<>();

	public static void init() {
		items.put("SmallLogo", new SmallLogo());
		items.put("MagnifyingGlass", new MagnifyingGlass());

		for (Entry<String, Item> entry : items.entrySet())
			setName(entry.getValue(), entry.getKey());
	}

	public static void register() {

		for (Item item : items.values())
			GameRegistry.registerItem(item, item.getUnlocalizedName());

	}

	public static void setName(Item item, String name) {
		item.setUnlocalizedName(name);
		item.setTextureName(Constants.MODID + ":" + name);
		item.setCreativeTab(item instanceof IStellwandItem ? CustomTabs.stellwandTab : CustomTabs.itemTab);
	}

	public static Map<String, Item> getItems() {
		return items;
	}

}
