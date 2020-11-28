package net.landofrails.stellwand.content.network.server;

import java.util.UUID;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.landofrails.stellwand.content.blocks.stellwand.utils.EStellwandSignal;
import net.landofrails.stellwand.content.network.PacketDispatcher;
import net.landofrails.stellwand.content.network.client.ClientSenderNewSignal;
import net.landofrails.stellwand.proxy.CommonProxy;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;

public class ServerSenderNewSignal implements IMessage {

	private UUID senderID;
	private int frequency;
	private EStellwandSignal signal;

	public ServerSenderNewSignal() {

	}

	public ServerSenderNewSignal(UUID senderID, int frequency, EStellwandSignal signal) {
		this.senderID = senderID;
		this.frequency = frequency;
		this.signal = signal;
	}

	@Override
	public void fromBytes(ByteBuf buf) {

		this.senderID = new UUID(buf.readLong(), buf.readLong());
		frequency = buf.readInt();
		signal = EStellwandSignal.fromID(buf.readInt());
	}

	@Override
	public void toBytes(ByteBuf buf) {
		// UUID:
		buf.writeLong(senderID.getMostSignificantBits());
		buf.writeLong(senderID.getLeastSignificantBits());

		buf.writeInt(frequency);
		buf.writeInt(signal.getID());
	}

	public static class Handler extends AbstractServerMessageHandler<ServerSenderNewSignal> {

		@Override
		public IMessage handleServerMessage(EntityPlayer player, ServerSenderNewSignal message, MessageContext ctx) {

			CommonProxy.signalHandler.change(message.senderID, message.frequency, message.signal);
			EStellwandSignal signal = CommonProxy.signalHandler.getHighestPrio(message.frequency);

			for (Object op : player.getEntityWorld().playerEntities) {
				EntityPlayerMP p = (EntityPlayerMP) op;
				PacketDispatcher.sendTo(new ClientSenderNewSignal(message.frequency, signal), p);
			}

			return null;
		}

	}

}
