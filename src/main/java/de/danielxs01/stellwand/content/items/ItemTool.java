package de.danielxs01.stellwand.content.items;

import java.util.List;

import de.danielxs01.stellwand.Constants;
import de.danielxs01.stellwand.content.gui.GuiToolData;
import de.danielxs01.stellwand.utils.EStellwandSignal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

@SuppressWarnings({ "java:S3740", "java:S1192" })
public class ItemTool extends Item {

	public ItemTool() {
		setMaxStackSize(1);
	}

	// Client
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List par3List, boolean par4) {

		NBTTagCompound nbt = getNBT(stack);

		par3List.add("Frequency: " + nbt.getInteger("frequency"));
		par3List.add("Signal: " + nbt.getString("signal"));
	}

	// Client & Server
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {

		if (!world.isRemote)
			return stack;

		double x = player.posX, y = player.posY, z = player.posZ;
		player.openGui(Constants.MODID, GuiToolData.GUIID, world, (int) x, (int) y, (int) z);

		return stack;
	}

	public static NBTTagCompound getNBT(ItemStack stack) {
		NBTTagCompound nbt;

		if (stack.hasTagCompound())
			nbt = stack.getTagCompound();
		else
			nbt = new NBTTagCompound();

		if (!nbt.hasKey("frequency"))
			nbt.setInteger("frequency", 0);
		if (!nbt.hasKey("signal"))
			nbt.setString("signal", EStellwandSignal.OFF.name());

		return nbt;
	}

}
