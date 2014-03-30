package com.co2.molsci.client.render;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.world.IBlockAccess;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public class CoffeeMachineRenderer implements ISimpleBlockRenderingHandler
{
	public static int renderId;
	
	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) 
	{
		
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer)
	{
		if (modelId != renderId)
			return true;
		
		return true;
	}

	@Override
	public boolean shouldRender3DInInventory(int modelId) 
	{
		return true;
	}

	@Override
	public int getRenderId() 
	{
		return renderId;
	}
}