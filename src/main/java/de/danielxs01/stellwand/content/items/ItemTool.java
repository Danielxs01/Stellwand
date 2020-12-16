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

	private static ItemTool instance;

	public ItemTool() {
		if (instance == null)
			instance = this;
	}

	// Client
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List par3List, boolean par4) {

		if (!stack.hasTagCompound() || !stack.getTagCompound().hasKey("frequency")
				|| !stack.getTagCompound().hasKey("signal"))
			return;
		NBTTagCompound nbt = stack.getTagCompound();
		par3List.add("Frequency: " + nbt.getInteger("frequency"));
		par3List.add("Signal: " + nbt.getString("signal"));
	}

	// Client & Server
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {

		if (!world.isRemote)
			return stack;

		double x = player.posX;
		double y = player.posY;
		double z = player.posZ;
		player.openGui(Constants.MODID, GuiToolData.GUIID, world, (int) x, (int) y, (int) z);

		return stack;
	}

	@Override
	public boolean getShareTag() {
		return true;
	}

	@Override
	public boolean isItemTool(ItemStack stack) {
		return stack.getItem() instanceof ItemTool;
	}

	public static NBTTagCompound getPreparedNBT(ItemStack itemTool, boolean override) {
		if (itemTool.getItem() instanceof ItemTool) {
			NBTTagCompound nbt = new NBTTagCompound();
			if (itemTool.hasTagCompound())
				nbt = itemTool.getTagCompound();
			if (!nbt.hasKey("frequency"))
				nbt.setInteger("frequency", 0);
			if (!nbt.hasKey("signal"))
				nbt.setString("signal", EStellwandSignal.OFF.name());
			if (override)
				itemTool.setTagCompound(nbt);
			return nbt;
		}
		return null;
	}

	public static ItemStack getItemStack(NBTTagCompound nbt) {
		ItemStack is = new ItemStack(instance);
		is.setTagCompound(nbt);
		return is;
	}

}
