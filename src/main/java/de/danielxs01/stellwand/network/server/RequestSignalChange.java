package de.danielxs01.stellwand.network.server;

import java.util.UUID;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import de.danielxs01.stellwand.utils.EStellwandSignal;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;

public class RequestSignalChange implements IMessage {

	private UUID senderID;
	private int frequency;
	private EStellwandSignal signal;

	@Override
	public void fromBytes(ByteBuf buf) {
		senderID = new UUID(buf.readLong(), buf.readLong());
		frequency = buf.readInt();
		signal = EStellwandSignal.fromID(buf.readInt());
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeLong(senderID.getMostSignificantBits());
		buf.writeLong(senderID.getLeastSignificantBits());
		buf.writeInt(frequency);
		buf.writeInt(signal.getID());
	}

	public static class Handler extends AbstractServerMessageHandler<RequestSignalChange> {

		@Override
		public IMessage handleServerMessage(EntityPlayer player, RequestSignalChange message, MessageContext ctx) {

			// TODO: Save this on server

			return null;
		}

	}

}
