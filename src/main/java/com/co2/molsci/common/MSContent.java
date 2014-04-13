package com.co2.molsci.common;

import com.co2.molsci.block.food.BlockCoffeeLeaves;
import com.co2.molsci.block.food.BlockCoffeeMachine;
import com.co2.molsci.block.food.BlockCoffeeSapling;
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
		//Food Blocks
		MSRepo.coffeeMachine = new BlockCoffeeMachine();
		MSRepo.coffeeSapling = new BlockCoffeeSapling();
		MSRepo.coffeeLeaves = new BlockCoffeeLeaves();
		
		GameRegistry.registerBlock(MSRepo.coffeeMachine, "coffeeMachine");
		GameRegistry.registerBlock(MSRepo.coffeeSapling, "coffeePlant");
		GameRegistry.registerBlock(MSRepo.coffeeLeaves, "coffeeLeaves");
	}
}