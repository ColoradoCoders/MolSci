package com.co2.molsci.common;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;

public class MSRecipes 
{
	public static void initItemRecipes()
	{
		//Furnace recipes
		FurnaceRecipes.smelting().func_151394_a(new ItemStack(MSRepo.coffeeBean, 1, 0), new ItemStack(MSRepo.coffeeBean, 1, 1), 0.1f);
		FurnaceRecipes.smelting().func_151394_a(new ItemStack(MSRepo.coffeeBean, 1, 1), new ItemStack(MSRepo.coffeeBean, 1, 2), 0.1f);
	}
	
	public static void initBlockRecipes()
	{
		
	}
}