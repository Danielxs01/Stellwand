package de.danielxs01.stellwand.content.blocks.others;

import de.danielxs01.stellwand.Constants;
import de.danielxs01.stellwand.content.tileentities.TEBlockSender;
import de.danielxs01.stellwand.utils.BlockTileEntity;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockSender extends BlockTileEntity<TEBlockSender> {

	private IIcon side = null;

	public static final String STANDARDTEXTURE = Constants.MODID + ":" + "others/sender";

	public BlockSender() {
		super(Material.iron);
	}

	@Override
	public void registerBlockIcons(IIconRegister reg) {
		side = reg.registerIcon(STANDARDTEXTURE);
	}

	@Override
	public IIcon getIcon(int side, int meta) {
		return this.side;
	}

	@Override
	public Class<TEBlockSender> getTileEntityClass() {
		return TEBlockSender.class;
	}

	@Override
	public TEBlockSender createTileEntity(World world, int metadata) {
		return new TEBlockSender();
	}

	// Methods

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX,
			float hitY, float hitZ) {

		// Called: Client, Server
		// Side: Client
		if (world.isRemote) {
			// Request Gui for TileEntity Data

			// TODO: Request GUI and refresh Data
		}

		return true;

	}

	@Override
	public void onBlockDestroyedByPlayer(World world, int x, int y, int z, int meta) {
		this.onBlockDestroyed(world, x, y, z);
	}

	@Override
	public void onBlockDestroyedByExplosion(World world, int x, int y, int z, Explosion explosion) {
		this.onBlockDestroyed(world, x, y, z);
	}

	public void onBlockDestroyed(World world, int x, int y, int z) {

		if (world.isRemote) {
			TileEntity te = world.getTileEntity(x, y, z);
			if (te instanceof TEBlockSender) {

				// TODO: Zurücksetzen des Signals

			}
		}

	}

	@Override
	public boolean canConnectRedstone(IBlockAccess world, int x, int y, int z, int side) {
		return true;
	}

}
