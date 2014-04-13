package com.co2.molsci.block.food;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockCrops;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import com.co2.molsci.common.MSRepo;
import com.co2.molsci.config.WorldGenSettings;
import com.co2.molsci.lib.Reference;
import com.co2.molsci.world.CoffeePlantGen;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockCoffeeSapling extends BlockCrops
{
	public static final int GROWTH_STAGES = 5;
	public static final String[] textureNames = { "coffeeSapling" };
	
	@SideOnly(Side.CLIENT)
	public IIcon[] icons;
	
	public BlockCoffeeSapling()
	{
		super();
		setTickRandomly(true);
	}
	
	@Override
	public void updateTick(World world, int x, int y, int z, Random random)
	{
		super.updateTick(world, x, y, z, random);
		
		if (world.getBlockLightValue(x, y, z) >= 4)
		{
			if (random.nextFloat() < WorldGenSettings.COFFEE_PLANT_GROWTH_RATE)
			{
				CoffeePlantGen gen = new CoffeePlantGen(true);
				gen.generateTree(world, x, y, z);
			}
		}
	}
	
	@Override
	public boolean canPlaceBlockAt(World world, int x, int y, int z)
	{
		Block b = world.getBlock(x, y - 1, z);
		
		return (b == Blocks.dirt);
	}
	
	@Override
	public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int meta, int fortune)
	{
		ArrayList<ItemStack> retval = new ArrayList<ItemStack>();
		
		retval.add(new ItemStack(MSRepo.coffeeBean));
		
		return retval;
	}
	
	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z)
	{
		int meta = world.getBlockMetadata(x, y, z);
		
		switch (meta)
		{
		case 0:
			return AxisAlignedBB.getBoundingBox(x + 0.3, y, z + 0.3, x + 0.7, y + 0.3, z + 0.7);
		case 1:
			return AxisAlignedBB.getBoundingBox(x + 0.2, y, z + 0.2, x + 0.8, y + 0.7, z + 0.8);
		case 2:
			return AxisAlignedBB.getBoundingBox(x + 0.2, y, z + 0.2, x + 0.8, y + 0.8, z + 0.8);
		case 3:
			return AxisAlignedBB.getBoundingBox(x, y, z, x + 1, y + 1, z + 1);
		case 4:
		default:
			return AxisAlignedBB.getBoundingBox(x, y, z, x + 1, y + 1, z + 1);
		}
	}
	
	//TODO bet block bounds
	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess access, int x, int y, int z)
	{
		super.setBlockBoundsBasedOnState(access, x, y, z);
	}
	
	@Override
	public int getRenderType()
	{
		return 1;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister register)
	{
		icons = new IIcon[textureNames.length];
		
		for (int i = 0; i < icons.length; ++i)
			icons[i] = register.registerIcon(Reference.toResourceString(textureNames[i]));
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta)
	{
		if (meta < 0 || meta > (GROWTH_STAGES - 1))
			meta = (GROWTH_STAGES - 1);
		
		return icons[meta];
	}
	
	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void getSubBlocks(Item block, CreativeTabs tab, List list)
	{
		for (int i = 0; i < icons.length; ++i)
			list.add(new ItemStack(block, 1, i));
	}
}