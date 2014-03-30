package com.co2.molsci.proxy;

import com.co2.molsci.entity.tile.TileEntityCoffeeMachine;

import cpw.mods.fml.common.registry.GameRegistry;

public abstract class CommonProxy implements IProxy 
{
	@Override
	public void registerTileEntities()
	{
		GameRegistry.registerTileEntity(TileEntityCoffeeMachine.class, "tile.coffeeMachine");
	}
}