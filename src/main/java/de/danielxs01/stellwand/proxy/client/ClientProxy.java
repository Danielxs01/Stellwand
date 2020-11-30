package de.danielxs01.stellwand.proxy.client;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import de.danielxs01.stellwand.Stellwand;
import de.danielxs01.stellwand.proxy.server.CommonProxy;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;

public class ClientProxy extends CommonProxy {

	// TODO: Use it
	public static ClientSignalHandler signalHandler = new ClientSignalHandler();

	@Override
	public void preInit(FMLPreInitializationEvent event) {
		Stellwand.logger.info("preInit");
		super.preInit(event);
	}

	@Override
	public void init(FMLInitializationEvent event) {
		Stellwand.logger.info("init");
		super.init(event);

	}

	@Override
	public void postInit(FMLPostInitializationEvent event) {
		Stellwand.logger.info("postInit");
		super.postInit(event);
	}

	@Override
	public EntityPlayer getPlayerEntity(MessageContext ctx) {
		return (ctx.side.isClient() ? Minecraft.getMinecraft().thePlayer : super.getPlayerEntity(ctx));
	}

}
