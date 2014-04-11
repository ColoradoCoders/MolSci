package com.co2.molsci.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import com.co2.molsci.MolecularScience;
import com.co2.molsci.client.render.CoffeeMachineRenderer;
import com.co2.molsci.entity.tile.TileEntityCoffeeMachine;
import com.co2.molsci.lib.GuiIds;
import com.co2.molsci.util.MSUtils;

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
	public void breakBlock(World world, int x, int y, int z, Block block, int i)
	{
		TileEntity entity = world.getTileEntity(x, y, z);
		
		if (entity instanceof IInventory && !world.isRemote)
		{
			MSUtils.dropItems(world, (IInventory) entity, x, y, z);
			MSUtils.clearInventory((IInventory) entity);
		}
		
		super.breakBlock(world, x, y, z, block, i);
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
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack)
	{
		int dir = MathHelper.floor_double((double)(entity.rotationYaw * 4.0f / 360.0f) + 0.5) & 3;
		
		world.setBlockMetadataWithNotify(x, y, z, dir, 2);
	}
	
	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess access, int x, int y, int z)
	{
		int meta = access.getBlockMetadata(x, y, z);
		
		switch (meta)
		{
		case 1:
			this.setBlockBounds(0.375f, 0.0f, 0.375f, 0.84375f, 0.625f, 0.625f);
			return;
		case 2:
			this.setBlockBounds(0.375f, 0.0f, 0.375f, 0.625f, 0.625f, 0.84375f);
			return;
		case 3:
			this.setBlockBounds(0.15625f, 0.0f, 0.375f, 0.625f, 0.625f, 0.625f);
			return;
		case 0:
		default:
			this.setBlockBounds(0.375f, 0.0f, 0.15625f, 0.625f, 0.625f, 0.625f);
			return;
		}
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