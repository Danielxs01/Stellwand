package de.danielxs01.stellwand.utils;

import java.util.ArrayList;

import de.danielxs01.stellwand.content.gui.stellwand.GuiBlockSignal;
import de.danielxs01.stellwand.content.items.ItemTool;
import de.danielxs01.stellwand.content.tileentities.TEBlockSignal;
import de.danielxs01.stellwand.network.PacketDispatcher;
import de.danielxs01.stellwand.network.client.OpenGUI;
import de.danielxs01.stellwand.network.server.RequestTEStorageChange;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
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

		if (player.getCurrentEquippedItem() != null && player.getCurrentEquippedItem().getItem() instanceof ItemTool) {

			if (world.isRemote) {

				NBTTagCompound nbt = ItemTool.getPreparedNBT(player.getCurrentEquippedItem(), true);

				int frequency = nbt.getInteger("frequency");
				String signalName = nbt.getString("signal");
				String name = nbt.getString("name");
				EStellwandSignal signal = EStellwandSignal.valueOf(signalName);
				BlockPos pos = new BlockPos(x, y, z);

				String msg = "(" + x + ", " + y + ", " + z + ") Frequency: " + frequency + ", Signal: " + signal.name();
				player.addChatMessage(new ChatComponentText(msg));
				PacketDispatcher.sendToServer(new RequestTEStorageChange(pos, frequency, name, signal));

			}

			return true;

		} else if (player.isSneaking() && player.getCurrentEquippedItem() == null) {

			if (!world.isRemote)
				return true;

			TileEntity te = world.getTileEntity(x, y, z);
			if (te instanceof TEBlockSignal) {
				TEBlockSignal blockSignal = (TEBlockSignal) te;
				int frequency = blockSignal.getFrequency();
				EStellwandSignal signal = blockSignal.getSignal();
				String msg = "Frequency: " + frequency + "; Signal: " + signal.name();
				player.addChatMessage(new ChatComponentText(msg));
			}

		} else if (!world.isRemote && player.getCurrentEquippedItem() == null) {

			TileEntity te = world.getTileEntity(x, y, z);
			if (te instanceof TEBlockSignal) {
				TEBlockSignal blockSignal = (TEBlockSignal) te;

				int guiId = GuiBlockSignal.GUIID;
				BlockPos pos = new BlockPos(x, y, z);
				int frequency = blockSignal.getFrequency();
				EStellwandSignal signal = blockSignal.getSignal();
				String name = blockSignal.getName();

				PacketDispatcher.sendTo(new OpenGUI(guiId, pos, frequency, name, signal), (EntityPlayerMP) player);
			}

		}

		return false;

	}

	@Override
	public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune) {
		return new ArrayList<>();
	}

	@Override
	public void onBlockPreDestroy(World world, int x, int y, int z, int oldMetadata) {
		world.setTileEntity(x, y, z, null);
	}

}
