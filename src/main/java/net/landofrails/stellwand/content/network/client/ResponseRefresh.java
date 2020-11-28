package net.landofrails.stellwand.content.network.client;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.landofrails.stellwand.content.blocks.stellwand.tileentity.TEBlockSender;
import net.landofrails.stellwand.content.blocks.stellwand.tileentity.TEBlockSignal;
import net.landofrails.stellwand.content.blocks.stellwand.utils.EStellwandSignal;
import net.landofrails.stellwand.content.util.BlockPos;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;

public class ResponseRefresh implements IMessage {

	private BlockPos pos;
	private int frequency;
	private EStellwandSignal signal;

	public ResponseRefresh() {

	}

	public ResponseRefresh(BlockPos pos, int frequency, EStellwandSignal signal) {
		this.pos = pos;
		this.frequency = frequency;
		this.signal = signal;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		pos = BlockPos.fromBytes(buf);
		frequency = buf.readInt();
		signal = EStellwandSignal.fromID(buf.readInt());
	}

	@Override
	public void toBytes(ByteBuf buf) {
		pos.toBytes(buf);
		buf.writeInt(frequency);
		buf.writeInt(signal.getID());
	}

	public static class Handler extends AbstractClientMessageHandler<ResponseRefresh> {

		@Override
		public IMessage handleClientMessage(EntityPlayer player, ResponseRefresh message, MessageContext ctx) {

			BlockPos pos = message.pos;

			// Refresh TileEntity Data
			TileEntity te = player.worldObj.getTileEntity(pos.getX(), pos.getY(), pos.getZ());
			if (te instanceof TEBlockSignal) {
				TEBlockSignal blockSignal = (TEBlockSignal) te;
				blockSignal.setFrequency(message.frequency);
				blockSignal.setSignal(message.signal);
			} else if (te instanceof TEBlockSender) {
				TEBlockSender blockSender = (TEBlockSender) te;
				blockSender.setFrequency(message.frequency);
				blockSender.setSignal(message.signal);
			}

			return null;
		}

	}

}