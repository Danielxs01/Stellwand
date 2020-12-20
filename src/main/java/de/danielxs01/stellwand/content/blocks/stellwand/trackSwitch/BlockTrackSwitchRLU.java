package de.danielxs01.stellwand.content.blocks.stellwand.trackSwitch;

public class BlockTrackSwitchRLU extends BlockTrackSwitch {

	public static BlockTrackSwitchRLU instance;

	public BlockTrackSwitchRLU() {
		super("RLU");
		if (instance == null)
			instance = this;
	}

}
