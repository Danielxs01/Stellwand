package net.landofrails.stellwand.content.blocks.stellwand;

import net.landofrails.stellwand.Constants;
import net.landofrails.stellwand.LandOfRails;
import net.landofrails.stellwand.content.blocks.stellwand.tileentity.TEBlockSender;
import net.landofrails.stellwand.content.blocks.stellwand.utils.BlockTileEntity;
import net.landofrails.stellwand.content.blocks.stellwand.utils.EStellwandSignal;
import net.landofrails.stellwand.content.blocks.stellwand.utils.IStellwandBlock;
import net.landofrails.stellwand.content.gui.GuiBlockSender;
import net.landofrails.stellwand.content.network.PacketDispatcher;
import net.landofrails.stellwand.content.network.server.RequestGUI;
import net.landofrails.stellwand.content.network.server.ServerSenderNewSignal;
import net.landofrails.stellwand.content.util.BlockPos;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockSender extends BlockTileEntity<TEBlockSender> implements IStellwandBlock {

	private IIcon side = null;

	public static final String STANDARDTEXTURE = Constants.MODID + ":" + "stellwand/sender";

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
			LandOfRails.logger.info("RequestGui");
			PacketDispatcher.sendToServer(new RequestGUI(GuiBlockSender.GUIID, new BlockPos(x, y, z)));
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

				// Zurücksetzen des Signals

				LandOfRails.logger.info("Test");

				TEBlockSender blockSender = (TEBlockSender) te;
				PacketDispatcher.sendToServer(new ServerSenderNewSignal(blockSender.getSenderID(),
						blockSender.getFrequency(), EStellwandSignal.OFF));
			}
		}

	}

	@Override
	public boolean canConnectRedstone(IBlockAccess world, int x, int y, int z, int side) {
		return true;
	}

}
