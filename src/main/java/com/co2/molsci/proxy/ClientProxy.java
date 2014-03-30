package com.co2.molsci.proxy;

import com.co2.molsci.client.render.CoffeeMachineRenderer;

import cpw.mods.fml.client.registry.RenderingRegistry;

public class ClientProxy extends CommonProxy 
{
	@Override
	public void registerRenderers()
	{
		//Get rendering ids
		CoffeeMachineRenderer.renderId = RenderingRegistry.getNextAvailableRenderId();
		
		//Register renderers
		RenderingRegistry.registerBlockHandler(new CoffeeMachineRenderer());
	}
}