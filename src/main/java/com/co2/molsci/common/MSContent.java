package com.co2.molsci.common;

import com.co2.molsci.block.BlockCoffeeMachine;
import com.co2.molsci.block.BlockCoffeePlant;
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
		MSRepo.coffeePlant = new BlockCoffeePlant();
		
		GameRegistry.registerBlock(MSRepo.coffeeMachine, "coffeeMachine");
		GameRegistry.registerBlock(MSRepo.coffeePlant, "coffeePlant");
	}
}