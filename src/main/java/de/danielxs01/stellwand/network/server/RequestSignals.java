package de.danielxs01.stellwand.network.server;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import de.danielxs01.stellwand.network.PacketDispatcher;
import de.danielxs01.stellwand.network.client.ResponseSignalChange;
import de.danielxs01.stellwand.proxy.server.CommonProxy;
import de.danielxs01.stellwand.utils.EStellwandSignal;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;

@SuppressWarnings("java:S1186")
public class RequestSignals implements IMessage {

	@Override
	public void fromBytes(ByteBuf buf) {

	}

	@Override
	public void toBytes(ByteBuf buf) {

	}

	public static class Handler extends AbstractServerMessageHandler<RequestSignals> {

		@Override
		public IMessage handleServerMessage(EntityPlayer player, RequestSignals message, MessageContext ctx) {

			for (int frequency : CommonProxy.signalHandler.uuidFrequency.values()) {
				EStellwandSignal signal = CommonProxy.signalHandler.getHighestPrio(frequency);
				PacketDispatcher.sendTo(new ResponseSignalChange(frequency, signal), (EntityPlayerMP) player);
			}

			return null;
		}

	}

}
