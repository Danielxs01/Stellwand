package net.landofrails.stellwand.content.blocks;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import cpw.mods.fml.common.registry.GameRegistry;
import net.landofrails.stellwand.Constants;
import net.landofrails.stellwand.content.blocks.itemBlock.ItemBlockAsphalt;
import net.landofrails.stellwand.content.blocks.stellwand.BlockNoRail;
import net.landofrails.stellwand.content.blocks.stellwand.BlockSender;
import net.landofrails.stellwand.content.blocks.stellwand.diagonal.BlockTrackDiagDL;
import net.landofrails.stellwand.content.blocks.stellwand.diagonal.BlockTrackDiagDR;
import net.landofrails.stellwand.content.blocks.stellwand.diagonal.BlockTrackDiagUL;
import net.landofrails.stellwand.content.blocks.stellwand.diagonal.BlockTrackDiagUR;
import net.landofrails.stellwand.content.blocks.stellwand.straight.BlockTrackEmpty;
import net.landofrails.stellwand.content.blocks.stellwand.straight.BlockTrackEmptyColored;
import net.landofrails.stellwand.content.blocks.stellwand.switches.BlockTrackSwitchLRD;
import net.landofrails.stellwand.content.blocks.stellwand.switches.BlockTrackSwitchLRU;
import net.landofrails.stellwand.content.blocks.stellwand.switches.BlockTrackSwitchRLD;
import net.landofrails.stellwand.content.blocks.stellwand.switches.BlockTrackSwitchRLU;
import net.landofrails.stellwand.content.blocks.stellwand.utils.IStellwandBlock;
import net.landofrails.stellwand.content.tabs.CustomTabs;
import net.minecraft.block.Block;

public class CustomBlocks {

	private CustomBlocks() {

	}

	private static Map<String, Block> blocks = new HashMap<>();

	public static void init() {
		// Add blocks here:
		blocks.put("asphaltLBtoRC", new BlockAsphaltLBtoRC());
		blocks.put("asphaltLTtoRC", new BlockAsphaltLTtoRC());

		blocks.put("blockSender", new BlockSender());
		blocks.put("blockNoRail", new BlockNoRail());

		blocks.put("blockTrackEmpty", new BlockTrackEmpty());
		blocks.put("blockTrackEmptyColored", new BlockTrackEmptyColored());

		blocks.put("blockTrackDiagDL", new BlockTrackDiagDL());
		blocks.put("blockTrackDiagDR", new BlockTrackDiagDR());
		blocks.put("blockTrackDiagUL", new BlockTrackDiagUL());
		blocks.put("blockTrackDiagUR", new BlockTrackDiagUR());

		blocks.put("blockTrackSwitchLRD", new BlockTrackSwitchLRD());
		blocks.put("blockTrackSwitchLRU", new BlockTrackSwitchLRU());
		blocks.put("blockTrackSwitchRLD", new BlockTrackSwitchRLD());
		blocks.put("blockTrackSwitchRLU", new BlockTrackSwitchRLU());

		for (Entry<String, Block> entry : blocks.entrySet())
			setName(entry.getValue(), entry.getKey());
	}

	public static void register() {

		for (Entry<String, Block> block : blocks.entrySet()) {
			if (block.getValue() instanceof BlockAsphaltLTtoRC || block.getValue() instanceof BlockAsphaltLBtoRC) {
				GameRegistry.registerBlock(block.getValue(), ItemBlockAsphalt.class, block.getKey());
			} else {
				GameRegistry.registerBlock(block.getValue(), block.getKey());
			}
		}

	}

	public static void setName(Block block, String name) {
		block.setBlockName(name);
		block.setBlockTextureName(Constants.MODID + ":" + name);
		block.setCreativeTab(block instanceof IStellwandBlock ? CustomTabs.stellwandTab : CustomTabs.blockTab);
	}

	public static Map<String, Block> getBlocks() {
		return blocks;
	}

}
