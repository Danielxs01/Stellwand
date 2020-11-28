package net.landofrails.stellwand.content.blocks.stellwand.utils;

public enum EStellwandSignal {
	// @formatter:off
	OFF(10, 0), 
	SINGLE_WHITE(20, 1), DOUBLE_WHITE(21, 1), BRANCH_WHITE(22, 1),
	SINGLE_RED(30, 2),   DOUBLE_RED(31, 2),   BRANCH_RED(32, 2);
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
}
