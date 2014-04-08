package com.co2.molsci.proxy;

import net.minecraft.item.Item;
import net.minecraftforge.client.MinecraftForgeClient;

import com.co2.molsci.client.render.CoffeeMachineRenderer;
import com.co2.molsci.common.MSRepo;
import com.co2.molsci.entity.tile.TileEntityCoffeeMachine;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class ClientProxy extends CommonProxy 
{
	@Override
	public void registerRenderers()
	{
		//Register the Coffee Machine renderer
		CoffeeMachineRenderer.renderId = RenderingRegistry.getNextAvailableRenderId();
		CoffeeMachineRenderer coffeeMachineRenderer = new CoffeeMachineRenderer();
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCoffeeMachine.class, coffeeMachineRenderer);
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(MSRepo.coffeeMachine), coffeeMachineRenderer);
	}
}