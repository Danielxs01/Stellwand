package net.landofrails.stellwand.content.util;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import net.landofrails.stellwand.content.blocks.stellwand.utils.EStellwandSignal;

public class ServerSignalHandler {

	private Map<UUID, Integer> uuidFrequenzen = new HashMap<>();
	private Map<UUID, EStellwandSignal> uuidSignale = new HashMap<>();

	public void change(UUID senderID, int frequency, EStellwandSignal signal) {

		if (signal == EStellwandSignal.OFF || frequency == 0) {
			// Remove
			if (uuidFrequenzen.containsKey(senderID))
				uuidFrequenzen.remove(senderID);
			if (uuidSignale.containsKey(senderID))
				uuidSignale.remove(senderID);
		} else {
			// Change
			uuidFrequenzen.put(senderID, frequency);
			uuidSignale.put(senderID, signal);
		}

	}

	public EStellwandSignal getHighestPrio(int frequency) {
		EStellwandSignal signal = EStellwandSignal.OFF;
		for (Entry<UUID, Integer> blockFrequenz : uuidFrequenzen.entrySet()) {
			if (blockFrequenz.getValue() == frequency) {
				EStellwandSignal s = uuidSignale.get(blockFrequenz.getKey());
				if (s.getImportance() > signal.getImportance())
					signal = s;
			}
		}

		return signal;
	}

	public Collection<Integer> getFrequencys() {
		return uuidFrequenzen.values();
	}

}
