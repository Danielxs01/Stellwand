package de.danielxs01.stellwand.network.server;

import javax.annotation.Nullable;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import de.danielxs01.stellwand.content.tileentities.TEBlockSender;
import de.danielxs01.stellwand.content.tileentities.TEBlockSignal;
import de.danielxs01.stellwand.network.PacketDispatcher;
import de.danielxs01.stellwand.network.client.ResponseTEStorageChange;
import de.danielxs01.stellwand.utils.BlockPos;
import de.danielxs01.stellwand.utils.EStellwandSignal;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.tileentity.TileEntity;

public class RequestTEStorageChange implements IMessage {

	// Type 1: Frequency, Type 2: + Signal
	private int type;
	private BlockPos pos;
	private int frequency;
	private EStellwandSignal signal;

	public RequestTEStorageChange() {

	}

	public RequestTEStorageChange(BlockPos pos, int frequency, @Nullable EStellwandSignal signal) {
		this.type = signal == null ? 1 : 2;
		this.pos = pos;
		this.frequency = frequency;
		this.signal = signal;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		this.type = buf.readInt();
		this.pos = BlockPos.fromBytes(buf);
		this.frequency = buf.readInt();
		if (type == 2)
			this.signal = EStellwandSignal.fromID(buf.readInt());
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(this.type);
		pos.toBytes(buf);
		buf.writeInt(this.frequency);
		if (this.type == 2)
			buf.writeInt(signal.getID());
	}

	public static class Handler extends AbstractServerMessageHandler<RequestTEStorageChange> {

		@Override
		public IMessage handleServerMessage(EntityPlayer player, RequestTEStorageChange message, MessageContext ctx) {

			BlockPos pos = message.pos;
			TileEntity te = player.worldObj.getTileEntity(pos.getX(), pos.getY(), pos.getZ());
			if (te instanceof TEBlockSender) {
				TEBlockSender blockSender = (TEBlockSender) te;
				blockSender.setFrequency(message.frequency);
				if (message.type == 2)
					blockSender.setSignal(message.signal);
			} else if (te instanceof TEBlockSignal) {
				TEBlockSignal blockSignal = (TEBlockSignal) te;
				blockSignal.setFrequency(message.frequency);
				if (message.type == 2)
					blockSignal.setSignal(message.signal);
			}

			for (Object o : player.worldObj.playerEntities) {
				EntityPlayerMP p = (EntityPlayerMP) o;
				PacketDispatcher.sendTo(new ResponseTEStorageChange(pos, message.frequency, message.signal), p);
			}

			return null;
		}

	}

}
