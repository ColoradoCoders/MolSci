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
			setBlockAndNotifyAdequately(world, i, j + 1, k, MSRepo.coffeePlant, 0);
			
			i += (random.nextInt(3) - 1);
			k += (random.nextInt(3) - 1);
			j = MSUtils.getMaxHeightOfBlockWithSpaceAbove(world, i, k, new Block[] { Blocks.dirt, Blocks.grass });
		}
		
		return true;
	}
}
