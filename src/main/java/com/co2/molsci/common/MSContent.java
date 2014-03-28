package com.co2.molsci.common;

import com.co2.molsci.item.ItemCoffeeBean;

import cpw.mods.fml.common.registry.GameRegistry;

public class MSContent 
{
	public MSContent()
	{
		initItems();
		MSRecipes.initItemRecipes();
		initBlocks();
		MSRecipes.initBlockRecipes();
	}
	
	private void initItems()
	{
		MSRepo.coffeeBean = new ItemCoffeeBean();
		
		GameRegistry.registerItem(MSRepo.coffeeBean, "coffeeBean");
	}
	
	private void initBlocks()
	{
		
	}
}