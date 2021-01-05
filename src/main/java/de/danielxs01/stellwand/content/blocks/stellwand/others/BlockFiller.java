package de.danielxs01.stellwand.content.blocks.stellwand.others;

import de.danielxs01.stellwand.Constants;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

public class BlockFiller extends Block {

	private IIcon side = null;

	public static final String STANDARDTEXTURE = Constants.MODID + ":" + "others/filler";

	public BlockFiller() {
		super(Material.rock);
	}

	@Override
	public void registerBlockIcons(IIconRegister reg) {
		side = reg.registerIcon(STANDARDTEXTURE);
	}

	@Override
	public IIcon getIcon(int side, int meta) {
		return this.side;
	}

}
