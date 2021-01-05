package de.danielxs01.stellwand.network.client;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import de.danielxs01.stellwand.Stellwand;
import de.danielxs01.stellwand.content.tileentities.TEBlockSender;
import de.danielxs01.stellwand.content.tileentities.TEBlockSignal;
import de.danielxs01.stellwand.utils.BlockPos;
import de.danielxs01.stellwand.utils.EStellwandSignal;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;

public class OpenGUI implements IMessage {

	private int guiId;
	private BlockPos pos;
	private int frequency;
	private String name;
	private EStellwandSignal signal;

	public OpenGUI() {

	}

	public OpenGUI(int guiId, BlockPos pos, int frequency, String name, EStellwandSignal signal) {
		this.guiId = guiId;
		this.pos = pos;
		this.frequency = frequency;
		this.name = name;
		this.signal = signal;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		this.guiId = buf.readInt();
		pos = BlockPos.fromBytes(buf);
		frequency = buf.readInt();
		signal = EStellwandSignal.fromID(buf.readInt());
		name = ByteBufUtils.readUTF8String(buf);

	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(this.guiId);
		pos.toBytes(buf);
		buf.writeInt(frequency);
		buf.writeInt(signal.getID());
		ByteBufUtils.writeUTF8String(buf, name);
	}

	public static class Handler extends AbstractClientMessageHandler<OpenGUI> {

		@Override
		public IMessage handleClientMessage(EntityPlayer player, OpenGUI message, MessageContext ctx) {

			BlockPos pos = message.pos;

			// Refresh TileEntity Data
			TileEntity te = player.worldObj.getTileEntity(pos.getX(), pos.getY(), pos.getZ());
			if (te instanceof TEBlockSignal) {
				TEBlockSignal blockSignal = (TEBlockSignal) te;
				blockSignal.setFrequency(message.frequency);
				blockSignal.setSignal(message.signal);
				blockSignal.setName(message.name);
			} else if (te instanceof TEBlockSender) {
				TEBlockSender blockSender = (TEBlockSender) te;
				blockSender.setFrequency(message.frequency);
				blockSender.setSignal(message.signal);
				blockSender.setName(message.name);
			}

			player.openGui(Stellwand.instance, message.guiId, player.worldObj, pos.getX(), pos.getY(), pos.getZ());
			return null;
		}

	}

}
