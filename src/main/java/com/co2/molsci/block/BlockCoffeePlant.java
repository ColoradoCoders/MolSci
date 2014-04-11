package com.co2.molsci.block;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.block.BlockCrops;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.common.util.FakePlayer;

import com.co2.molsci.common.MSRepo;
import com.co2.molsci.config.WorldGenSettings;
import com.co2.molsci.lib.Reference;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockCoffeePlant extends BlockCrops
{
	public static final int GROWTH_STAGES = 5;
	public static final String[] textureNames = { "coffee", "coffee", "coffee", "coffee", "coffee" };
	
	@SideOnly(Side.CLIENT)
	public IIcon[] icons;
	
	public BlockCoffeePlant()
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
			int meta = world.getBlockMetadata(x, y, z);
			
			if (meta < (GROWTH_STAGES - 1))
			{
				if (random.nextFloat() < WorldGenSettings.COFFEE_PLANT_GROWTH_RATE)
				{
					++meta;
					world.setBlockMetadataWithNotify(x, y, z, meta, 2);
				}
			}
		}
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int i, float f, float f2, float f3)
	{
		int meta = world.getBlockMetadata(x, y, z);
		if (meta >= (GROWTH_STAGES - 1))
		{
			if (world.isRemote)
				return true;
			
			world.setBlockMetadataWithNotify(x, y, z, (GROWTH_STAGES - 2), 2);
			EntityItem item = new EntityItem(world, player.posX, player.posY - 1.0, player.posZ, new ItemStack(MSRepo.coffeeBean));
			world.spawnEntityInWorld(item);
			if (!(player instanceof FakePlayer))
				item.onCollideWithPlayer(player);
			
			return true; 
		}
		
		return false;
	}
	
	@Override
	public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int meta, int fortune)
	{
		ArrayList<ItemStack> retval = new ArrayList<ItemStack>();
		
		if (meta < (GROWTH_STAGES - 1))
			retval.add(new ItemStack(MSRepo.coffeeBean));
		else
			retval.add(new ItemStack(MSRepo.coffeeBean, 2));
		
		return retval;
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