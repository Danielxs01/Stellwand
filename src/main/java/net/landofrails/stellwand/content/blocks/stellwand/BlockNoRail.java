package net.landofrails.stellwand.content.blocks.stellwand;

import net.landofrails.stellwand.Constants;
import net.landofrails.stellwand.content.blocks.stellwand.utils.IStellwandBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

public class BlockNoRail extends Block implements IStellwandBlock {

	private IIcon side = null;

	public static final String STANDARDTEXTURE = Constants.MODID + ":" + "stellwand/filler";

	public BlockNoRail() {
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
