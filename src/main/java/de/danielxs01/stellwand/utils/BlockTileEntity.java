package de.danielxs01.stellwand.utils;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public abstract class BlockTileEntity<T extends TileEntity> extends Block {

	public BlockTileEntity(Material materialIn) {
		super(materialIn);
	}

	public abstract Class<T> getTileEntityClass();

	@SuppressWarnings("unchecked")
	public T getTileEntity(IBlockAccess world, int x, int y, int z) {
		return (T) world.getTileEntity(x, y, z);
	}

	@Override
	public boolean hasTileEntity(int metadata) {
		return true;
	}

	@Override
	public boolean hasTileEntity() {
		return true;
	}

	@Nullable
	@Override
	public abstract T createTileEntity(World world, int metadata);

}