package de.danielxs01.stellwand.network.client;

import javax.annotation.Nullable;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import de.danielxs01.stellwand.content.tileentities.TEBlockSender;
import de.danielxs01.stellwand.content.tileentities.TEBlockSignal;
import de.danielxs01.stellwand.utils.BlockPos;
import de.danielxs01.stellwand.utils.EStellwandSignal;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;

public class ResponseTEStorageChange implements IMessage {

	// Type 1: Frequency, Type 2: + Signal
	private int type;
	private BlockPos pos;
	private int frequency;
	private String name;
	private EStellwandSignal signal;

	public ResponseTEStorageChange() {

	}

	public ResponseTEStorageChange(BlockPos pos, int frequency, String name, @Nullable EStellwandSignal signal) {
		this.pos = pos;
		this.type = signal == null ? 1 : 2;
		this.frequency = frequency;
		this.name = name;
		this.signal = signal;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		this.type = buf.readInt();
		this.pos = BlockPos.fromBytes(buf);
		this.frequency = buf.readInt();
		this.name = ByteBufUtils.readUTF8String(buf);
		if (type == 2)
			this.signal = EStellwandSignal.fromID(buf.readInt());
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(this.type);
		pos.toBytes(buf);
		buf.writeInt(this.frequency);
		ByteBufUtils.writeUTF8String(buf, this.name);
		if (this.type == 2)
			buf.writeInt(signal.getID());
	}

	public static class Handler extends AbstractClientMessageHandler<ResponseTEStorageChange> {

		@Override
		public IMessage handleClientMessage(EntityPlayer player, ResponseTEStorageChange message, MessageContext ctx) {

			BlockPos pos = message.pos;
			TileEntity te = player.worldObj.getTileEntity(pos.getX(), pos.getY(), pos.getZ());
			if (te instanceof TEBlockSender) {

				TEBlockSender blockSender = (TEBlockSender) te;
				blockSender.setFrequency(message.frequency);
				blockSender.setName(message.name);
				if (message.type == 2)
					blockSender.setSignal(message.signal);
				if (blockSender.isInitialUpdate())
					blockSender.update();
			} else if (te instanceof TEBlockSignal) {
				TEBlockSignal blockSignal = (TEBlockSignal) te;
				blockSignal.setFrequency(message.frequency);
				blockSignal.setName(message.name);
				if (message.type == 2)
					blockSignal.setSignal(message.signal);
			}

			return null;
		}

	}

}