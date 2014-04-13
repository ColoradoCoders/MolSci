package com.co2.molsci.world;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;

import com.co2.molsci.config.WorldGenSettings;
import com.co2.molsci.util.MSUtils;

import cpw.mods.fml.common.IWorldGenerator;

public class MSWorldGenerator implements IWorldGenerator
{
	CoffeePlantGen coffeePlantGen;
	
	public MSWorldGenerator()
	{
		coffeePlantGen = new CoffeePlantGen(false);
	}
	
	@Override
	public void generate(Random random, int cx, int cz, World world, IChunkProvider generator, IChunkProvider provider)
	{
		switch (world.provider.dimensionId)
		{
		case -1:
			generateNether(world, random, cx * 16, cz * 16);
			return;
		case 0:
			generateOverworldNature(world, random, cx * 16, cz * 16);
			return;
		}
	}
	
	private void generateOverworldNature(World world, Random random, int cx, int cz)
	{
		int bx = cx + random.nextInt(16);
		int bz = cz + random.nextInt(16);
		int by = MSUtils.getMaxHeightOfBlockWithSpaceAbove(world, bx, bz, new Block[] { Blocks.dirt, Blocks.grass });
		
		int bid = world.getWorldChunkManager().getBiomeGenAt(bx, bz).biomeID;
		
		boolean canCoffee = (bid == 21) || (bid == 22) || (bid == 23) || (bid == 149) || (bid == 151);
		
		if (canCoffee && random.nextFloat() < WorldGenSettings.COFFEE_PLANT_SPAWN_CHANCE && by < 255 && by > 60)
			coffeePlantGen.generate(world, random, bx, by, bz);
	}
	
	private void generateNether(World world, Random random, int cx, int cz)
	{
		
	}
}