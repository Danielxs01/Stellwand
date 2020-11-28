package net.landofrails.stellwand.content.network.server;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.landofrails.stellwand.LandOfRails;
import net.landofrails.stellwand.content.blocks.stellwand.tileentity.TEBlockSender;
import net.landofrails.stellwand.content.blocks.stellwand.tileentity.TEBlockSignal;
import net.landofrails.stellwand.content.blocks.stellwand.utils.EStellwandSignal;
import net.landofrails.stellwand.content.network.PacketDispatcher;
import net.landofrails.stellwand.content.network.client.ResponseRefresh;
import net.landofrails.stellwand.content.util.BlockPos;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.tileentity.TileEntity;

public class RequestChange implements IMessage {

	private int type;
	private BlockPos blockPos;
	private int frequency;
	private EStellwandSignal signal = null;

	public RequestChange() {

	}

	public RequestChange(BlockPos blockPos, int frequency) {
		this.type = 1;
		this.blockPos = blockPos;
		this.frequency = frequency;
	}

	public RequestChange(BlockPos blockPos, int frequency, EStellwandSignal signal) {
		this.type = 2;
		this.blockPos = blockPos;
		this.frequency = frequency;
		this.signal = signal;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		this.type = buf.readInt();
		this.blockPos = BlockPos.fromBytes(buf);
		this.frequency = buf.readInt();
		if (this.type == 2)
			this.signal = EStellwandSignal.fromID(buf.readInt());
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(type);
		blockPos.toBytes(buf);
		buf.writeInt(frequency);
		if (type == 2)
			buf.writeInt(signal.getID());
	}

	public static class Handler extends AbstractServerMessageHandler<RequestChange> {
		@Override
		public IMessage handleServerMessage(EntityPlayer player, RequestChange message, MessageContext ctx) {

			BlockPos pos = message.blockPos;

			EntityPlayerMP multiPlayer = (EntityPlayerMP) player;

			LandOfRails.logger.info("Receiving RequestChange");
			// Refresh TileEntity Data
			TileEntity te = multiPlayer.getEntityWorld().getTileEntity(pos.getX(), pos.getY(), pos.getZ());
			if (te instanceof TEBlockSignal) {

				LandOfRails.logger.info("Success");

				TEBlockSignal blockSignal = (TEBlockSignal) te;
				blockSignal.setFrequency(message.frequency);
				if (message.signal != null)
					blockSignal.setSignal(message.signal);
			} else if (te instanceof TEBlockSender) {

				LandOfRails.logger.info("Success");

				TEBlockSender blockSender = (TEBlockSender) te;
				blockSender.setFrequency(message.frequency);
				if (message.signal != null)
					blockSender.setSignal(message.signal);
			}

			for (Object op : multiPlayer.getEntityWorld().playerEntities) {
				EntityPlayerMP multiP = (EntityPlayerMP) op;
				PacketDispatcher.sendTo(new ResponseRefresh(pos, message.frequency, EStellwandSignal.OFF), multiP);
			}

			return null;
		}
	}

}