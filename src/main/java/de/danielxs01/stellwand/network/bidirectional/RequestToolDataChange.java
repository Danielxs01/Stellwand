package de.danielxs01.stellwand.network.bidirectional;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import de.danielxs01.stellwand.content.items.ItemTool;
import de.danielxs01.stellwand.network.PacketDispatcher;
import de.danielxs01.stellwand.network.client.AbstractClientMessageHandler;
import de.danielxs01.stellwand.network.server.AbstractServerMessageHandler;
import de.danielxs01.stellwand.utils.EStellwandSignal;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;

public class RequestToolDataChange implements IMessage {

	private static final Charset CHARSET = StandardCharsets.UTF_8;

	private int frequency = 0;
	private EStellwandSignal signal = EStellwandSignal.OFF;

	public RequestToolDataChange() {

	}

	public RequestToolDataChange(int frequency, EStellwandSignal signal) {
		this.frequency = frequency;
		this.signal = signal;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		frequency = buf.readInt();
		int length = buf.readInt();
		byte[] bytes = new byte[length];
		for (int i = 0; i < length; i++) {
			bytes[i] = buf.readByte();
		}
		signal = EStellwandSignal.valueOf(new String(bytes, CHARSET));
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(frequency);
		byte[] bytes = signal.name().getBytes(CHARSET);
		buf.writeInt(bytes.length);
		for (byte b : bytes)
			buf.writeByte(b);

	}

	public static class ServerHandler extends AbstractServerMessageHandler<RequestToolDataChange> {

		@Override
		public IMessage handleServerMessage(EntityPlayer player, RequestToolDataChange message, MessageContext ctx) {

			if (player.getCurrentEquippedItem() != null
					&& player.getCurrentEquippedItem().getItem() instanceof ItemTool) {
				NBTTagCompound nbt = ItemTool.getPreparedNBT(player.getCurrentEquippedItem(), false);
				nbt.setInteger("frequency", message.frequency);
				nbt.setString("signal", message.signal.name());
				player.getCurrentEquippedItem().setTagCompound(nbt);

				PacketDispatcher.sendTo(message, (EntityPlayerMP) player);
			}

			return null;
		}

	}

	public static class ClientHandler extends AbstractClientMessageHandler<RequestToolDataChange> {

		@Override
		public IMessage handleClientMessage(EntityPlayer player, RequestToolDataChange message, MessageContext ctx) {
			if (player.getCurrentEquippedItem() != null
					&& player.getCurrentEquippedItem().getItem() instanceof ItemTool) {
				NBTTagCompound nbt = ItemTool.getPreparedNBT(player.getCurrentEquippedItem(), false);
				nbt.setInteger("frequency", message.frequency);
				nbt.setString("signal", message.signal.name());
				player.getCurrentEquippedItem().setTagCompound(nbt);
			}

			return null;
		}

	}

}
