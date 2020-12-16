package de.danielxs01.stellwand.content.blocks.others;

import de.danielxs01.stellwand.Constants;
import de.danielxs01.stellwand.content.gui.GuiBlockSender;
import de.danielxs01.stellwand.content.items.ItemTool;
import de.danielxs01.stellwand.content.tileentities.TEBlockSender;
import de.danielxs01.stellwand.network.PacketDispatcher;
import de.danielxs01.stellwand.network.client.OpenGUI;
import de.danielxs01.stellwand.network.server.RequestTEStorageChange;
import de.danielxs01.stellwand.proxy.client.ClientProxy;
import de.danielxs01.stellwand.utils.BlockPos;
import de.danielxs01.stellwand.utils.BlockTileEntity;
import de.danielxs01.stellwand.utils.EStellwandSignal;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
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

	// Called on: Client & Server
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX,
			float hitY, float hitZ) {

		if (player.getCurrentEquippedItem() != null && player.getCurrentEquippedItem().getItem() instanceof ItemTool) {

			if (world.isRemote) {

				NBTTagCompound nbt = ItemTool.getPreparedNBT(player.getCurrentEquippedItem(), true);
				int frequency = nbt.getInteger("frequency");
				String signalName = nbt.getString("signal");
				EStellwandSignal signal = EStellwandSignal.valueOf(signalName);
				BlockPos pos = new BlockPos(x, y, z);

				String msg = "(" + x + ", " + y + ", " + z + ") Frequency: " + frequency + ", Signal: " + signal.name();
				player.addChatMessage(new ChatComponentText(msg));
				PacketDispatcher.sendToServer(new RequestTEStorageChange(pos, frequency, signal));

			}

			return true;

		} else if (player.isSneaking() && player.getCurrentEquippedItem() == null) {

			if (!world.isRemote)
				return true;

			TileEntity te = world.getTileEntity(x, y, z);
			if (te instanceof TEBlockSender) {
				TEBlockSender blockSender = (TEBlockSender) te;
				int frequency = blockSender.getFrequency();
				EStellwandSignal signal = blockSender.getSignal();
				String msg = "Frequency: " + frequency + "; Signal: " + signal.name();
				player.addChatMessage(new ChatComponentText(msg));

				return true;
			}

		} else if (!world.isRemote && player.getCurrentEquippedItem() == null) {

			TileEntity te = world.getTileEntity(x, y, z);
			if (te instanceof TEBlockSender) {
				TEBlockSender blockSender = (TEBlockSender) te;

				int guiId = GuiBlockSender.GUIID;
				BlockPos pos = new BlockPos(x, y, z);
				int frequency = blockSender.getFrequency();
				EStellwandSignal signal = blockSender.getSignal();

				PacketDispatcher.sendTo(new OpenGUI(guiId, pos, frequency, signal), (EntityPlayerMP) player);
			}

			return true;
		}

		return false;

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

				TEBlockSender sender = (TEBlockSender) te;
				ClientProxy.signalHandler.change(sender.getSenderID(), sender.getFrequency(), EStellwandSignal.OFF);

			}
		}

	}

	@Override
	public boolean canConnectRedstone(IBlockAccess world, int x, int y, int z, int side) {
		return true;
	}

}
