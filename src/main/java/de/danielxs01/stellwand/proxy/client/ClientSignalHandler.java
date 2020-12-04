package de.danielxs01.stellwand.proxy.client;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import de.danielxs01.stellwand.utils.EStellwandSignal;

public class ClientSignalHandler {

	public Map<UUID, Integer> uuidFrequency = new HashMap<>();
	public Map<UUID, EStellwandSignal> uuidSignal = new HashMap<>();

	public void change(UUID senderID, int frequency, EStellwandSignal signal) {

		if (signal == EStellwandSignal.OFF) {
			if (uuidFrequency.containsKey(senderID))
				uuidFrequency.remove(senderID);
			if (uuidSignal.containsKey(senderID))
				uuidSignal.remove(senderID);
		} else {
			uuidFrequency.put(senderID, frequency);
			uuidSignal.put(senderID, signal);
		}

	}

	public EStellwandSignal getHighestPrio(int frequency) {

		if (!uuidFrequency.containsValue(frequency))
			return EStellwandSignal.OFF;

		EStellwandSignal signal = EStellwandSignal.OFF;
		for (Entry<UUID, Integer> entry : uuidFrequency.entrySet()) {
			if (entry.getValue() == frequency) {
				EStellwandSignal s = uuidSignal.get(entry.getKey());
				if (s.getImportance() >= signal.getImportance()) {
					signal = s;
				}
			}
		}
		return signal;
	}

	public void clear() {
		uuidFrequency.clear();
		uuidSignal.clear();
	}

}
