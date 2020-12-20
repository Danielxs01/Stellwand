package de.danielxs01.stellwand.proxy.client;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import de.danielxs01.stellwand.Constants;
import de.danielxs01.stellwand.Stellwand;
import de.danielxs01.stellwand.content.obj.ItemZwergsignal;
import de.danielxs01.stellwand.content.obj.TESRZwergsignal;
import de.danielxs01.stellwand.content.obj.TEZwergsignal;
import de.danielxs01.stellwand.proxy.server.CommonProxy;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;

public class ClientProxy extends CommonProxy {

	public static int[] displayList = new int[1];

	public static ClientSignalHandler signalHandler = new ClientSignalHandler();

	@Override
	public void preInit(FMLPreInitializationEvent event) {
		Stellwand.logger.info("preInit");
		ClientRegistry.bindTileEntitySpecialRenderer(TEZwergsignal.class, new TESRZwergsignal());

		super.preInit(event);
	}

	@Override
	public void init(FMLInitializationEvent event) {
		Stellwand.logger.info("init");
		super.init(event);

		final IModelCustom model = AdvancedModelLoader
				.loadModel(new ResourceLocation(Constants.MODID, "obj/ch_zwergsignal_head.obj"));
		displayList[0] = GLAllocation.generateDisplayLists(1);
		GL11.glNewList(displayList[0], GL11.GL_COMPILE);
		model.renderAll();
		GL11.glEndList();

		// Item
		MinecraftForgeClient.registerItemRenderer(ItemZwergsignal.instance,
				new ItemZwergsignal.ItemZwergsignalRenderer());

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
