package de.danielxs01.stellwand.network;

import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;
import de.danielxs01.stellwand.Constants;
import de.danielxs01.stellwand.network.client.AbstractClientMessageHandler;
import de.danielxs01.stellwand.network.client.OpenGUI;
import de.danielxs01.stellwand.network.client.ResponseSignalChange;
import de.danielxs01.stellwand.network.client.ResponseTEStorageChange;
import de.danielxs01.stellwand.network.server.RequestGUI;
import de.danielxs01.stellwand.network.server.RequestSignalChange;
import de.danielxs01.stellwand.network.server.RequestTEStorage;
import de.danielxs01.stellwand.network.server.RequestTEStorageChange;
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
		registerMessage(RequestTEStorage.Handler.class, RequestTEStorage.class);
		registerMessage(RequestTEStorageChange.Handler.class, RequestTEStorageChange.class);
		registerMessage(RequestSignalChange.Handler.class, RequestSignalChange.class);

		// Client
		registerMessage(OpenGUI.Handler.class, OpenGUI.class, Side.CLIENT);
		registerMessage(ResponseTEStorageChange.Handler.class, ResponseTEStorageChange.class);
		registerMessage(ResponseSignalChange.Handler.class, ResponseSignalChange.class);
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
