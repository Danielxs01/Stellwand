package de.danielxs01.stellwand.utils;

public enum EStellwandSignal {
	// @formatter:off
	OFF(10, 0), 
	SINGLE_WHITE(20, 1),  DOUBLE_WHITE(21, 1), BRANCH_WHITE(22, 1),
	SINGLE_GREEN(40, 2),
	SINGLE_ORANGE(50, 3),
	SINGLE_RED(30, 4),    DOUBLE_RED(31, 2),   BRANCH_RED(32, 2);
	// @formatter:on
	private int id;
	private int importance;

	private EStellwandSignal(int id, int importance) {
		this.id = id;
		this.importance = importance;
	}

	public int getID() {
		return id;
	}

	public int getImportance() {
		return importance;
	}

	public static EStellwandSignal fromID(int id) {
		for (EStellwandSignal signal : EStellwandSignal.values())
			if (signal.getID() == id)
				return signal;
		return null;
	}

	public EStellwandSignal getNextSignal() {
		EStellwandSignal[] signale = EStellwandSignal.values();
		boolean next = false;

		for (EStellwandSignal s : signale) {
			if (s.equals(this))
				next = true;
			else if (next)
				return s;
		}

		return signale[0];
	}

}
