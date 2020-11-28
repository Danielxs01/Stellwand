package net.landofrails.stellwand.content.util;

import java.util.HashMap;
import java.util.Map;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.landofrails.stellwand.LandOfRails;
import net.landofrails.stellwand.content.blocks.stellwand.utils.EStellwandSignal;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;

@SideOnly(Side.CLIENT)
public class ClientSignalHandler {

	private Map<Integer, EStellwandSignal> frequenzSignale = new HashMap<>();

	public void update(int frequency, EStellwandSignal signal) {

		LandOfRails.logger.info("ClientSignalHandler update: {} {}", frequency, signal.name());

		if (signal == EStellwandSignal.OFF) {
			if (frequenzSignale.containsKey(frequency))
				frequenzSignale.remove(frequency);
		} else {
			frequenzSignale.put(frequency, signal);
		}
	}

	public EStellwandSignal getSignal(int frequency) {
		EStellwandSignal signal = EStellwandSignal.OFF;

		if (frequenzSignale.containsKey(frequency)) {
			signal = frequenzSignale.get(frequency);
		}

		return signal;
	}

	@SubscribeEvent
	public void onEvent(EntityJoinWorldEvent event) {
		LandOfRails.logger.info("EntityJoinWorldEvent!");
	}

}
