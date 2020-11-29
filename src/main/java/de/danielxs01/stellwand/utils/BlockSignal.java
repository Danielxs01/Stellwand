package de.danielxs01.stellwand.utils;

import java.util.ArrayList;

import de.danielxs01.stellwand.content.tileentities.TEBlockSignal;
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

			// TODO: Refresh data and request gui
		}

		return true;

	}

	@Override
	public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune) {
		return new ArrayList<>();
	}

}
