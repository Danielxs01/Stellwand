package de.danielxs01.stellwand.proxy.client;

import java.util.HashMap;
import java.util.Map;

import de.danielxs01.stellwand.utils.EStellwandSignal;

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

}
