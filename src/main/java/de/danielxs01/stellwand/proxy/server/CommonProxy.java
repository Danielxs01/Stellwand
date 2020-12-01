package de.danielxs01.stellwand.proxy.server;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.common.registry.GameRegistry;
import de.danielxs01.stellwand.Constants;
import de.danielxs01.stellwand.Stellwand;
import de.danielxs01.stellwand.content.blocks.CustomBlocks;
import de.danielxs01.stellwand.content.gui.GuiHandler;
import de.danielxs01.stellwand.content.items.CustomItems;
import de.danielxs01.stellwand.content.tileentities.TEBlockSender;
import de.danielxs01.stellwand.content.tileentities.TEBlockSignal;
import de.danielxs01.stellwand.network.PacketDispatcher;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.MinecraftForge;

public class CommonProxy {

	// TODO: Use signal handlers
	public static ServerSignalHandler signalHandler = new ServerSignalHandler();

	public void preInit(FMLPreInitializationEvent event) {

		Stellwand.logger.info("preInit");

		CustomItems.init();
		CustomItems.register();
		CustomBlocks.init();
		CustomBlocks.register();

		NetworkRegistry.INSTANCE.registerGuiHandler(Stellwand.instance, new GuiHandler());

		GameRegistry.registerTileEntity(TEBlockSignal.class, Constants.MODID + "_BlockSignal");
		GameRegistry.registerTileEntity(TEBlockSender.class, Constants.MODID + "_BlockSender");

		MinecraftForge.EVENT_BUS.register(Stellwand.eventHandler);

		PacketDispatcher.registerPackets();
	}

	public void init(FMLInitializationEvent event) {
		Stellwand.logger.info("init");
	}

	public void postInit(FMLPostInitializationEvent event) {
		Stellwand.logger.info("postInit");
	}

	public EntityPlayer getPlayerEntity(MessageContext ctx) {
		return ctx.getServerHandler().playerEntity;
	}

}
