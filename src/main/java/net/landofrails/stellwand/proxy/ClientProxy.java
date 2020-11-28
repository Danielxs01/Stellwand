package net.landofrails.stellwand.proxy;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import net.landofrails.stellwand.LandOfRails;
import net.landofrails.stellwand.content.util.ClientSignalHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;

public class ClientProxy extends CommonProxy {

	public static ClientSignalHandler signalHandler = new ClientSignalHandler();

	@Override
	public void preInit(FMLPreInitializationEvent event) {
		LandOfRails.logger.info("preInit");
		super.preInit(event);
	}

	@Override
	public void init(FMLInitializationEvent event) {
		LandOfRails.logger.info("init");
		super.init(event);

	}

	@Override
	public void postInit(FMLPostInitializationEvent event) {
		LandOfRails.logger.info("postInit");
		super.postInit(event);
	}

	@Override
	public EntityPlayer getPlayerEntity(MessageContext ctx) {
		return (ctx.side.isClient() ? Minecraft.getMinecraft().thePlayer : super.getPlayerEntity(ctx));
	}

}
