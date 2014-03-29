package com.co2.molsci.common;

import com.co2.molsci.item.food.ItemCoffeeBean;
import com.co2.molsci.item.food.ItemCoffeeCup;

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
		//Food Items
		MSRepo.coffeeBean = new ItemCoffeeBean();
		MSRepo.coffeeCup = (ItemCoffeeCup) new ItemCoffeeCup().setIsLiquid(true);
		
		GameRegistry.registerItem(MSRepo.coffeeBean, "coffeeBean");
		GameRegistry.registerItem(MSRepo.coffeeCup, "coffeeCup");
	}
	
	private void initBlocks()
	{
		
	}
}