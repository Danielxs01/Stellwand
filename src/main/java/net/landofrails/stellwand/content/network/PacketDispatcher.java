package net.landofrails.stellwand.content.network;

import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;
import net.landofrails.stellwand.Constants;
import net.landofrails.stellwand.content.network.client.AbstractClientMessageHandler;
import net.landofrails.stellwand.content.network.client.ClientSenderNewSignal;
import net.landofrails.stellwand.content.network.client.OpenGUI;
import net.landofrails.stellwand.content.network.client.ResponseRefresh;
import net.landofrails.stellwand.content.network.server.RequestChange;
import net.landofrails.stellwand.content.network.server.RequestGUI;
import net.landofrails.stellwand.content.network.server.RequestRefresh;
import net.landofrails.stellwand.content.network.server.RequestSignals;
import net.landofrails.stellwand.content.network.server.ServerSenderNewSignal;
import net.minecraft.entity.player.EntityPlayerMP;

@SuppressWarnings("java:S3740")
public class PacketDispatcher {

	private PacketDispatcher() {

	}

	private static byte packetId = 0;

	private static final SimpleNetworkWrapper dispatcher = NetworkRegistry.INSTANCE.newSimpleChannel(Constants.MODID);

	public static final void registerPackets() {
		// Server
		registerMessage(RequestGUI.Handler.class, RequestGUI.class, Side.SERVER);
		registerMessage(RequestChange.Handler.class, RequestChange.class);
		registerMessage(RequestRefresh.Handler.class, RequestRefresh.class);
		registerMessage(ServerSenderNewSignal.Handler.class, ServerSenderNewSignal.class);
		registerMessage(RequestSignals.Handler.class, RequestSignals.class);

		// Client
		registerMessage(OpenGUI.Handler.class, OpenGUI.class, Side.CLIENT);
		registerMessage(ResponseRefresh.Handler.class, ResponseRefresh.class);
		registerMessage(ClientSenderNewSignal.Handler.class, ClientSenderNewSignal.class);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static final void registerMessage(Class handlerClass, Class messageClass, Side side) {
		PacketDispatcher.dispatcher.registerMessage(handlerClass, messageClass, packetId++, side);
	}

	public static final void sendTo(IMessage message, EntityPlayerMP player) {
		PacketDispatcher.dispatcher.sendTo(message, player);
	}

	public static final void sendToAllAround(IMessage message, NetworkRegistry.TargetPoint point) {
		PacketDispatcher.dispatcher.sendToAllAround(message, point);
	}

	public static final void sendToAllAround(IMessage message, int dimension, double x, double y, double z,
			double range) {
		PacketDispatcher.sendToAllAround(message, new NetworkRegistry.TargetPoint(dimension, x, y, z, range));
	}

	public static final void sendToDimension(IMessage message, int dimensionId) {
		PacketDispatcher.dispatcher.sendToDimension(message, dimensionId);
	}

	public static final void sendToServer(IMessage message) {
		PacketDispatcher.dispatcher.sendToServer(message);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static final void registerMessage(Class handlerClass, Class messageClass) {
		Side side = AbstractClientMessageHandler.class.isAssignableFrom(handlerClass) ? Side.CLIENT : Side.SERVER;
		PacketDispatcher.dispatcher.registerMessage(handlerClass, messageClass, packetId++, side);
	}

}
