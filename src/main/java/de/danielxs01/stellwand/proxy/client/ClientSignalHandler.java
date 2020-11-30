package de.danielxs01.stellwand.proxy.client;

import java.util.HashMap;
import java.util.Map;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import de.danielxs01.stellwand.network.PacketDispatcher;
import de.danielxs01.stellwand.network.server.RequestSignals;
import de.danielxs01.stellwand.utils.EStellwandSignal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;

public class ClientSignalHandler {

	private Map<Integer, EStellwandSignal> signale = new HashMap<>();

	public void change(int frequency, EStellwandSignal signal) {
		if (signal != EStellwandSignal.OFF)
			signale.put(frequency, signal);
		else if (signale.containsKey(frequency))
			signale.remove(frequency);
	}

	public EStellwandSignal getSignal(int frequency) {
		if (signale.containsKey(frequency))
			return signale.get(frequency);
		return EStellwandSignal.OFF;
	}

	@SubscribeEvent
	public void onEvent(EntityJoinWorldEvent event) {
		if ((event.entity instanceof EntityPlayer)) {
			signale.clear();
			PacketDispatcher.sendToServer(new RequestSignals());
		}
	}

}
