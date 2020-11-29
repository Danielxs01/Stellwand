package de.danielxs01.stellwand.content.items;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import cpw.mods.fml.common.registry.GameRegistry;
import de.danielxs01.stellwand.Constants;
import de.danielxs01.stellwand.content.tabs.CustomTabs;
import net.minecraft.item.Item;

public class CustomItems {

	private CustomItems() {

	}

	private static Map<String, Item> items = new HashMap<>();

	public static void init() {
		items.put("SmallLogo", new SmallLogo());

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
		if (!(item instanceof SmallLogo))
			item.setCreativeTab(CustomTabs.stellwandTab);
	}

	public static Map<String, Item> getItems() {
		return items;
	}

}
