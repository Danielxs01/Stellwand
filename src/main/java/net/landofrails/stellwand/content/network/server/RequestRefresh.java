package net.landofrails.stellwand.content.network.server;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.landofrails.stellwand.content.blocks.stellwand.tileentity.TEBlockSender;
import net.landofrails.stellwand.content.blocks.stellwand.tileentity.TEBlockSignal;
import net.landofrails.stellwand.content.blocks.stellwand.utils.EStellwandSignal;
import net.landofrails.stellwand.content.network.PacketDispatcher;
import net.landofrails.stellwand.content.network.client.ResponseRefresh;
import net.landofrails.stellwand.content.util.BlockPos;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.tileentity.TileEntity;

public class RequestRefresh implements IMessage {

	private BlockPos blockPos;

	public RequestRefresh() {

	}

	public RequestRefresh(BlockPos blockPos) {
		this.blockPos = blockPos;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		this.blockPos = BlockPos.fromBytes(buf);
	}

	@Override
	public void toBytes(ByteBuf buf) {
		blockPos.toBytes(buf);
	}

	public static class Handler extends AbstractServerMessageHandler<RequestRefresh> {
		@Override
		public IMessage handleServerMessage(EntityPlayer player, RequestRefresh message, MessageContext ctx) {

			BlockPos pos = message.blockPos;
			// Give Player fresh TileEntity Data for requested block

			EntityPlayerMP multiPlayer = (EntityPlayerMP) player;

			TileEntity te = multiPlayer.getEntityWorld().getTileEntity(pos.getX(), pos.getY(), pos.getZ());
			if (te instanceof TEBlockSignal) {
				TEBlockSignal blockSignal = (TEBlockSignal) te;
				int frequency = blockSignal.getFrequency();
				EStellwandSignal signal = blockSignal.getSignal();

				// Send to player in dimension
				int dimensionId = player.worldObj.provider.dimensionId;
				PacketDispatcher.sendToDimension(new ResponseRefresh(pos, frequency, signal), dimensionId);

			} else if (te instanceof TEBlockSender) {
				TEBlockSignal blockSender = (TEBlockSignal) te;
				int frequency = blockSender.getFrequency();
				EStellwandSignal signal = blockSender.getSignal();

				// Send to player in dimension
				int dimensionId = player.worldObj.provider.dimensionId;
				PacketDispatcher.sendToDimension(new ResponseRefresh(pos, frequency, signal), dimensionId);
			}

			return null;
		}
	}

}