package net.landofrails.stellwand.content.blocks.stellwand.utils;

import java.util.ArrayList;

import net.landofrails.stellwand.content.blocks.stellwand.tileentity.TEBlockSignal;
import net.landofrails.stellwand.content.gui.GuiBlockTrackSignal;
import net.landofrails.stellwand.content.network.PacketDispatcher;
import net.landofrails.stellwand.content.network.server.RequestGUI;
import net.landofrails.stellwand.content.util.BlockPos;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
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

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX,
			float hitY, float hitZ) {

		// Called: Client, Server; Side: Client
		if (world.isRemote) {
			// Request Gui for TileEntity Data
			PacketDispatcher.sendToServer(new RequestGUI(GuiBlockTrackSignal.GUIID, new BlockPos(x, y, z)));
		}

		return true;

	}

	@Override
	public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune) {
		return new ArrayList<>();
	}

}
