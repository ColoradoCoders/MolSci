package com.co2.molsci.block.food;

import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.util.FakePlayer;

import com.co2.molsci.block.MSBlock;
import com.co2.molsci.common.MSRepo;
import com.co2.molsci.config.WorldGenSettings;

public class BlockCoffeeLeaves extends MSBlock
{
	public BlockCoffeeLeaves()
	{
		super(Material.leaves, 1.0f, new String[] { "coffeeLeaves", "coffeeLeavesFull" });
		this.setTickRandomly(true);
	}
	
	@Override
	public void updateTick(World world, int x, int y, int z, Random random)
	{
		super.updateTick(world, x, y, z, random);
		
		int meta = world.getBlockMetadata(x, y, z);
		
		if (world.getBlockLightValue(x, y, z) > 0)
			if (meta == 0 && random.nextFloat() < WorldGenSettings.COFFEE_LEAVES_GROWTH_RATE)
				world.setBlockMetadataWithNotify(x, y, z, 1, 3);
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int i, float cx, float cy, float cz)
	{
		int meta = world.getBlockMetadata(x, y, z);
		
		if (meta == 0)
			return false;
		
		if (world.isRemote)
			return false;
		
		world.setBlockMetadataWithNotify(x, y, z, 0, 3);
			
		EntityItem entityitem = new EntityItem(world, player.posX, player.posY - 1.0D, player.posZ, new ItemStack(MSRepo.coffeeBean));
		world.spawnEntityInWorld(entityitem);
		if (!(player instanceof FakePlayer))
			entityitem.onCollideWithPlayer(player);
		world.markBlockForUpdate(x, y, z);
		return true;
	}
	
	@Override
	public int getRenderBlockPass()
	{
		return 1;
	}
	
	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}
}