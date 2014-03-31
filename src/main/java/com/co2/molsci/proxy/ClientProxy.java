package com.co2.molsci.proxy;

import com.co2.molsci.client.render.CoffeeMachineRenderer;
import com.co2.molsci.entity.tile.TileEntityCoffeeMachine;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class ClientProxy extends CommonProxy 
{
	@Override
	public void registerRenderers()
	{
		CoffeeMachineRenderer.renderId = RenderingRegistry.getNextAvailableRenderId();
		
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCoffeeMachine.class, new CoffeeMachineRenderer());
	}
}