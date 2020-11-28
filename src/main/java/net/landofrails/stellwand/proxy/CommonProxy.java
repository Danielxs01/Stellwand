package net.landofrails.stellwand.proxy;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.common.registry.GameRegistry;
import net.landofrails.stellwand.Constants;
import net.landofrails.stellwand.LandOfRails;
import net.landofrails.stellwand.content.blocks.CustomBlocks;
import net.landofrails.stellwand.content.blocks.stellwand.tileentity.TEBlockSender;
import net.landofrails.stellwand.content.blocks.stellwand.tileentity.TEBlockSignal;
import net.landofrails.stellwand.content.gui.GuiHandler;
import net.landofrails.stellwand.content.items.CustomItems;
import net.landofrails.stellwand.content.network.PacketDispatcher;
import net.landofrails.stellwand.content.util.ServerSignalHandler;
import net.minecraft.entity.player.EntityPlayer;

public class CommonProxy {

	public static ServerSignalHandler signalHandler = new ServerSignalHandler();

	public void preInit(FMLPreInitializationEvent event) {
		LandOfRails.logger.info("preInit");

		CustomItems.init();
		CustomItems.register();
		CustomBlocks.init();
		CustomBlocks.register();

		NetworkRegistry.INSTANCE.registerGuiHandler(LandOfRails.instance, new GuiHandler());

		GameRegistry.registerTileEntity(TEBlockSignal.class, Constants.MODID + "_BlockSignal");
		GameRegistry.registerTileEntity(TEBlockSender.class, Constants.MODID + "_BlockSender");

		PacketDispatcher.registerPackets();
	}

	public void init(FMLInitializationEvent event) {
		LandOfRails.logger.info("init");
	}

	public void postInit(FMLPostInitializationEvent event) {
		LandOfRails.logger.info("postInit");
	}

	public EntityPlayer getPlayerEntity(MessageContext ctx) {
		return ctx.getServerHandler().playerEntity;
	}

}
