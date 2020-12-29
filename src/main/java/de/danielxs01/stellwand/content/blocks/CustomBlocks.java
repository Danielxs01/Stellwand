package de.danielxs01.stellwand.content.blocks;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import cpw.mods.fml.common.registry.GameRegistry;
import de.danielxs01.stellwand.Constants;
import de.danielxs01.stellwand.content.blocks.stellwand.others.BlockFiller;
import de.danielxs01.stellwand.content.blocks.stellwand.others.BlockSender;
import de.danielxs01.stellwand.content.blocks.stellwand.trackDiag.BlockTrackDiagDL;
import de.danielxs01.stellwand.content.blocks.stellwand.trackDiag.BlockTrackDiagDR;
import de.danielxs01.stellwand.content.blocks.stellwand.trackDiag.BlockTrackDiagEmpty;
import de.danielxs01.stellwand.content.blocks.stellwand.trackDiag.BlockTrackDiagUL;
import de.danielxs01.stellwand.content.blocks.stellwand.trackDiag.BlockTrackDiagUR;
import de.danielxs01.stellwand.content.blocks.stellwand.trackMainSignal.BlockTrackMainSignalLeft;
import de.danielxs01.stellwand.content.blocks.stellwand.trackMainSignal.BlockTrackMainSignalRight;
import de.danielxs01.stellwand.content.blocks.stellwand.trackStraight.BlockTrackEmpty;
import de.danielxs01.stellwand.content.blocks.stellwand.trackStraight.BlockTrackEmptyColored;
import de.danielxs01.stellwand.content.blocks.stellwand.trackStraight.BlockTrackEmptyUnisolated;
import de.danielxs01.stellwand.content.blocks.stellwand.trackSwitch.BlockTrackSwitchLRD;
import de.danielxs01.stellwand.content.blocks.stellwand.trackSwitch.BlockTrackSwitchLRU;
import de.danielxs01.stellwand.content.blocks.stellwand.trackSwitch.BlockTrackSwitchRLD;
import de.danielxs01.stellwand.content.blocks.stellwand.trackSwitch.BlockTrackSwitchRLU;
import de.danielxs01.stellwand.content.tabs.CustomTabs;
import net.minecraft.block.Block;

public class CustomBlocks {

	private CustomBlocks() {

	}

	private static Map<String, Block> blocks = new HashMap<>();

	public static void init() {
		// Add blocks here:
		blocks.put("blockSender", new BlockSender());
		blocks.put("blockFiller", new BlockFiller());
		blocks.put("blockTrackEmpty", new BlockTrackEmpty());

		blocks.put("blockTrackEmptyColored", new BlockTrackEmptyColored());
		blocks.put("blockTrackEmptyUnisolated", new BlockTrackEmptyUnisolated());

		blocks.put("blockTrackDiagDL", new BlockTrackDiagDL());
		blocks.put("blockTrackDiagDR", new BlockTrackDiagDR());
		blocks.put("blockTrackDiagUL", new BlockTrackDiagUL());
		blocks.put("blockTrackDiagUR", new BlockTrackDiagUR());
		blocks.put("blockTrackDiagEmpty", new BlockTrackDiagEmpty());

		blocks.put("blockTrackSwitchLRD", new BlockTrackSwitchLRD());
		blocks.put("blockTrackSwitchLRU", new BlockTrackSwitchLRU());
		blocks.put("blockTrackSwitchRLD", new BlockTrackSwitchRLD());
		blocks.put("blockTrackSwitchRLU", new BlockTrackSwitchRLU());

		blocks.put("blockTrackMainSignalLeft", new BlockTrackMainSignalLeft());
		blocks.put("blockTrackMainSignalRight", new BlockTrackMainSignalRight());

		for (Entry<String, Block> entry : blocks.entrySet())
			setName(entry.getValue(), entry.getKey());
	}

	public static void register() {

		for (Entry<String, Block> block : blocks.entrySet()) {
			GameRegistry.registerBlock(block.getValue(), block.getKey());
		}

	}

	public static void setName(Block block, String name) {
		block.setBlockName(name);
		block.setBlockTextureName(Constants.MODID + ":" + name);
		block.setCreativeTab(CustomTabs.stellwandTab);
	}

	public static Map<String, Block> getBlocks() {
		return blocks;
	}

}
