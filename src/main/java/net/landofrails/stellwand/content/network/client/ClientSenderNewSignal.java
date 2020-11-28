package net.landofrails.stellwand.content.network.client;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.landofrails.stellwand.LandOfRails;
import net.landofrails.stellwand.content.blocks.stellwand.utils.EStellwandSignal;
import net.landofrails.stellwand.proxy.ClientProxy;
import net.minecraft.entity.player.EntityPlayer;

public class ClientSenderNewSignal implements IMessage {

	private int frequency;
	private EStellwandSignal signal;

	public ClientSenderNewSignal() {

	}

	public ClientSenderNewSignal(int frequency, EStellwandSignal signal) {
		this.frequency = frequency;
		this.signal = signal;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		frequency = buf.readInt();
		signal = EStellwandSignal.fromID(buf.readInt());
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(frequency);
		buf.writeInt(signal.getID());
	}

	public static class Handler extends AbstractClientMessageHandler<ClientSenderNewSignal> {

		@Override
		public IMessage handleClientMessage(EntityPlayer player, ClientSenderNewSignal message, MessageContext ctx) {

			LandOfRails.logger.info("ClientSenderNewSignal handleClientMessage: {} {}", message.frequency,
					message.signal.name());

			ClientProxy.signalHandler.update(message.frequency, message.signal);
			return null;
		}

	}

}
