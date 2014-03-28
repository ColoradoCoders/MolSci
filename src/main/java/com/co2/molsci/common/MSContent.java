package com.co2.molsci.common;

import com.co2.molsci.item.ItemCoffeeBean;

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
	}
	
	private void initBlocks()
	{
		
	}
}