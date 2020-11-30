package de.danielxs01.stellwand.network.client;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import de.danielxs01.stellwand.proxy.client.ClientProxy;
import de.danielxs01.stellwand.utils.EStellwandSignal;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;

public class ResponseSignalChange implements IMessage {

	private int frequency;
	private EStellwandSignal signal;

	public ResponseSignalChange() {

	}

	public ResponseSignalChange(int frequency, EStellwandSignal signal) {
		this.frequency = frequency;
		this.signal = signal;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		this.frequency = buf.readInt();
		this.signal = EStellwandSignal.fromID(buf.readInt());
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(frequency);
		buf.writeInt(signal.getID());

	}

	public static class Handler extends AbstractClientMessageHandler<ResponseSignalChange> {

		@Override
		public IMessage handleClientMessage(EntityPlayer player, ResponseSignalChange message, MessageContext ctx) {

			ClientProxy.signalHandler.change(message.frequency, message.signal);

			return null;
		}

	}

}
