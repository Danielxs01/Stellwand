package de.danielxs01.stellwand.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.danielxs01.stellwand.Stellwand;
import net.minecraft.entity.player.EntityPlayer;

public abstract class AbstractMessageHandler<T extends IMessage> implements IMessageHandler<T, IMessage> {

	@SideOnly(Side.CLIENT)
	public abstract IMessage handleClientMessage(EntityPlayer player, T message, MessageContext ctx);

	public abstract IMessage handleServerMessage(EntityPlayer player, T message, MessageContext ctx);

	@Override
	public IMessage onMessage(T message, MessageContext ctx) {
		// due to compile-time issues, FML will crash if you try to use
		// Minecraft.getMinecraft() here,
		// even when you restrict this code to the client side and before the code is
		// ever accessed;
		// a solution is to use your proxy classes to get the player (see below).
		if (ctx.side.isClient()) {
			// the only reason to check side here is to use our more aptly named handling
			// methods
			// client side proxy will return the client side EntityPlayer
			return handleClientMessage(Stellwand.commonProxy.getPlayerEntity(ctx), message, ctx);
		} else {
			// server side proxy will return the server side EntityPlayer
			return handleServerMessage(Stellwand.commonProxy.getPlayerEntity(ctx), message, ctx);
		}
	}

}
