package de.danielxs01.stellwand.network.server;

import java.util.UUID;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import de.danielxs01.stellwand.Stellwand;
import de.danielxs01.stellwand.network.PacketDispatcher;
import de.danielxs01.stellwand.network.client.ResponseSignalChange;
import de.danielxs01.stellwand.proxy.server.CommonProxy;
import de.danielxs01.stellwand.utils.EStellwandSignal;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;

public class RequestSignalChange implements IMessage {

	private UUID senderID;
	private int frequency;
	private EStellwandSignal signal;

	public RequestSignalChange() {

	}

	public RequestSignalChange(UUID senderID, int frequency, EStellwandSignal signal) {
		this.senderID = senderID;
		this.frequency = frequency;
		this.signal = signal;
	}

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
			CommonProxy.signalHandler.change(message.senderID, message.frequency, message.signal);

			Stellwand.logger.info("RequestSignalChange | Frequency: {}, Signal: {}", message.frequency,
					message.signal.name());

			for (Object o : player.worldObj.playerEntities) {
				EntityPlayerMP playerMP = (EntityPlayerMP) o;
				EStellwandSignal signal = CommonProxy.signalHandler.getHighestPrio(message.frequency);
				PacketDispatcher.sendTo(new ResponseSignalChange(message.frequency, signal), playerMP);
			}

			return null;
		}

	}

}
