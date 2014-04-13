package com.co2.molsci.world;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import com.co2.molsci.common.MSRepo;
import com.co2.molsci.config.WorldGenSettings;
import com.co2.molsci.util.MSUtils;

public class CoffeePlantGen extends WorldGenerator
{
	public CoffeePlantGen(boolean notify)
	{
		super(notify);
	}
	
	@Override
	public boolean generate(World world, Random random, int i, int j, int k)
	{
		for (int count = 0; count < WorldGenSettings.COFFEE_PLANT_GEN_SIZE; ++count)
		{
			generateTree(world, i, j, k);
			
			i += (random.nextInt(9) - 4);
			k += (random.nextInt(9) - 4);
			j = MSUtils.getMaxHeightOfBlockWithSpaceAbove(world, i, k, new Block[] { Blocks.dirt, Blocks.grass });
		}
		
		return true;
	}
	
	public boolean generateTree(World world, int x, int y, int z)
	{
		world.setBlock(x, y, z, Blocks.log, 3, 3);
		world.setBlock(x, y + 1, z, Blocks.log, 3, 3);
		
		for (int y1 = y + 1; y1 < (y + 3); ++y1)
		{
			for (int x1 = x - 1; x1 < (x + 2); ++x1)
			{
				for (int z1 = z - 1; z1 < (z + 2); ++z1)
				{
					Block block = world.getBlock(x1, y1, z1);
					if (block.canBeReplacedByLeaves(world, x1, y1, z1) && block != Blocks.log)
						world.setBlock(x1, y1, z1, MSRepo.coffeeLeaves, 0, 3);
				}
			}
		}
		
		return true;
	}
}
