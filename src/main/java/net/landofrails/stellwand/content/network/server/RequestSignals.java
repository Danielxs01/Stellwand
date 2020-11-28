package net.landofrails.stellwand.content.network.server;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.landofrails.stellwand.content.blocks.stellwand.utils.EStellwandSignal;
import net.landofrails.stellwand.content.network.PacketDispatcher;
import net.landofrails.stellwand.content.network.client.ClientSenderNewSignal;
import net.landofrails.stellwand.proxy.CommonProxy;
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

			for (int frequency : CommonProxy.signalHandler.getFrequencys()) {
				EStellwandSignal signal = CommonProxy.signalHandler.getHighestPrio(frequency);
				PacketDispatcher.sendTo(new ClientSenderNewSignal(frequency, signal), (EntityPlayerMP) player);
			}

			return null;
		}

	}

}
