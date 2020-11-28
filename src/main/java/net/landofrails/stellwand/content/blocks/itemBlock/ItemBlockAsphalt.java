package net.landofrails.stellwand.content.blocks.itemBlock;

import java.util.List;

import net.landofrails.stellwand.content.blocks.BlockAsphaltLBtoRC;
import net.landofrails.stellwand.content.blocks.BlockAsphaltLTtoRC;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlockAsphalt extends ItemBlock {

	public ItemBlockAsphalt(Block block) {
		super(block);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
		// field_150939_a: The block that has been set via the constructor
		if (field_150939_a != null) {
			if (field_150939_a instanceof BlockAsphaltLBtoRC)
				par3List.add("Middle to Left");
			else if (field_150939_a instanceof BlockAsphaltLTtoRC)
				par3List.add("Middle to Right");
		}
	}

}
