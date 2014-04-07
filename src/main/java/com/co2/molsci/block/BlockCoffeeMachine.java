package com.co2.molsci.block;

import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.co2.molsci.MolecularScience;
import com.co2.molsci.client.render.CoffeeMachineRenderer;
import com.co2.molsci.entity.tile.TileEntityCoffeeMachine;
import com.co2.molsci.lib.GuiIds;

import cpw.mods.fml.common.network.internal.FMLNetworkHandler;

public class BlockCoffeeMachine extends MSBlockContainer
{
	public BlockCoffeeMachine()
	{
		super(Material.ground, 1.0f, new String[]{ "food/coffeeMachine" });
		this.setBlockName("coffeeMachine");
		this.setBlockBounds(0.375f, 0.0f, 0.15625f, 0.625f, 0.625f, 0.625f);
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ)
	{
		if (!world.isRemote)
			FMLNetworkHandler.openGui(player, MolecularScience.instance, GuiIds.COFFEE_MACHINE, world, x, y, z);
		
		return true;
	}
	
	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}
	
	@Override
	public boolean renderAsNormalBlock()
	{
		return false;
	}
	
	@Override
	public boolean canPlaceBlockAt(World world, int x, int y, int z)
	{
		return World.doesBlockHaveSolidTopSurface(world, x, y - 1, z);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta)
	{
		return new TileEntityCoffeeMachine();
	}
	
	@Override
	public int getRenderType()
	{
		return CoffeeMachineRenderer.renderId;
	}
}