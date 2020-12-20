package de.danielxs01.stellwand.content.gui;

import cpw.mods.fml.common.network.IGuiHandler;
import de.danielxs01.stellwand.content.gui.stellwand.GuiBlockSender;
import de.danielxs01.stellwand.content.gui.stellwand.GuiBlockSignal;
import de.danielxs01.stellwand.content.gui.stellwand.GuiToolData;
import de.danielxs01.stellwand.utils.BlockPos;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class GuiHandler implements IGuiHandler {

	@Override
	public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
		return false;
	}

	@Override
	public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
		if (id == GuiBlockSignal.GUIID) {
			return new GuiBlockSignal(player, new BlockPos(x, y, z));
		} else if (id == GuiBlockSender.GUIID) {
			return new GuiBlockSender(player, new BlockPos(x, y, z));
		} else if (id == GuiToolData.GUIID) {
			return new GuiToolData(player);
		}
		return null;
	}

}
