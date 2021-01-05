package de.danielxs01.stellwand.network.server;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import de.danielxs01.stellwand.content.tileentities.TEBlockSender;
import de.danielxs01.stellwand.content.tileentities.TEBlockSignal;
import de.danielxs01.stellwand.network.PacketDispatcher;
import de.danielxs01.stellwand.network.client.OpenGUI;
import de.danielxs01.stellwand.utils.BlockPos;
import de.danielxs01.stellwand.utils.EStellwandSignal;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.tileentity.TileEntity;

public class RequestGUI implements IMessage {

	private int guiId;
	private BlockPos blockPos;

	public RequestGUI() {

	}

	public RequestGUI(int guiId, BlockPos blockPos) {
		this.guiId = guiId;
		this.blockPos = blockPos;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		this.guiId = buf.readInt();
		this.blockPos = BlockPos.fromBytes(buf);
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(this.guiId);
		blockPos.toBytes(buf);
	}

	public static class Handler extends AbstractServerMessageHandler<RequestGUI> {
		@Override
		public IMessage handleServerMessage(EntityPlayer player, RequestGUI message, MessageContext ctx) {

			BlockPos pos = message.blockPos;
			// Give Player fresh TileEntity Data for requested block

			EntityPlayerMP multiPlayer = (EntityPlayerMP) player;

			TileEntity te = multiPlayer.getEntityWorld().getTileEntity(pos.getX(), pos.getY(), pos.getZ());
			if (te instanceof TEBlockSignal) {
				TEBlockSignal blockSignal = (TEBlockSignal) te;
				int frequency = blockSignal.getFrequency();
				String name = blockSignal.getName();
				EStellwandSignal signal = blockSignal.getSignal();
				PacketDispatcher.sendTo(new OpenGUI(message.guiId, message.blockPos, frequency, name, signal),
						(EntityPlayerMP) player);
			} else if (te instanceof TEBlockSender) {
				TEBlockSender blockSender = (TEBlockSender) te;
				int frequency = blockSender.getFrequency();
				String name = blockSender.getName();
				EStellwandSignal signal = blockSender.getSignal();
				PacketDispatcher.sendTo(new OpenGUI(message.guiId, message.blockPos, frequency, name, signal),
						(EntityPlayerMP) player);
			}

			return null;
		}
	}

}
