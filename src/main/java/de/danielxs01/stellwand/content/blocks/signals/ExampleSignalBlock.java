package de.danielxs01.stellwand.content.blocks.signals;

import de.danielxs01.stellwand.content.blocks.signals.ExampleSignalBlock.ExampleTileEntity;
import de.danielxs01.stellwand.utils.BlockTileEntity;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class ExampleSignalBlock extends BlockTileEntity<ExampleTileEntity> {

	public ExampleSignalBlock(Material materialIn) {
		super(materialIn);
	}

	public class ExampleTileEntity extends TileEntity {

	}

	public static class ExampleTESR extends TileEntitySpecialRenderer {

		@Override
		public void renderTileEntityAt(TileEntity entity, double x, double y, double z, float arg) {

		}

	}

	@Override
	public Class<ExampleTileEntity> getTileEntityClass() {
		return ExampleTileEntity.class;
	}

	@Override
	public ExampleTileEntity createTileEntity(World world, int metadata) {
		return new ExampleTileEntity();
	}

	public static void register() {

	}

}
