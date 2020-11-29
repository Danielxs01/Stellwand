package de.danielxs01.stellwand.utils;

import java.util.ArrayList;

import de.danielxs01.stellwand.content.gui.GuiBlockSignal;
import de.danielxs01.stellwand.content.tileentities.TEBlockSignal;
import de.danielxs01.stellwand.network.PacketDispatcher;
import de.danielxs01.stellwand.network.client.OpenGUI;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockSignal extends BlockTileEntity<TEBlockSignal> {

	public BlockSignal() {
		super(Material.rock);
		setLightLevel(0.5F);

	}

	@Override
	public Class<TEBlockSignal> getTileEntityClass() {
		return TEBlockSignal.class;
	}

	@Override
	public TEBlockSignal createTileEntity(World world, int metadata) {
		return new TEBlockSignal();
	}

	public TEBlockSignal getBlockSignal(IBlockAccess access, int x, int y, int z) {
		TileEntity te = access.getTileEntity(x, y, z);
		if (te instanceof TEBlockSignal)
			return (TEBlockSignal) te;
		return null;
	}

	// Called on: Client & Server
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX,
			float hitY, float hitZ) {

		if (!world.isRemote) {

			// TODO: Check GUI

			TileEntity te = world.getTileEntity(x, y, z);
			if (te instanceof TEBlockSignal) {
				TEBlockSignal blockSignal = (TEBlockSignal) te;

				int guiId = GuiBlockSignal.GUIID;
				BlockPos pos = new BlockPos(x, y, z);
				int frequency = blockSignal.getFrequency();
				EStellwandSignal signal = blockSignal.getSignal();

				PacketDispatcher.sendTo(new OpenGUI(guiId, pos, frequency, signal), (EntityPlayerMP) player);
			}

		}
		return true;

	}

	@Override
	public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune) {
		return new ArrayList<>();
	}

}
