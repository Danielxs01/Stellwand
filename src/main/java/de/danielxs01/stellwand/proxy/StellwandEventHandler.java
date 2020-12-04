package de.danielxs01.stellwand.proxy;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import de.danielxs01.stellwand.Stellwand;
import de.danielxs01.stellwand.proxy.client.ClientProxy;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;

public class StellwandEventHandler {

	@SubscribeEvent
	public void onEvent(EntityJoinWorldEvent event) {
		if ((event.entity instanceof EntityPlayer) && event.world.isRemote) {
			Stellwand.logger.info("EntityJoinWorldEvent");
			ClientProxy.signalHandler.clear();
		}
	}

}
