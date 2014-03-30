package com.co2.molsci.client;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.co2.molsci.client.container.ContainerCoffeeMachine;
import com.co2.molsci.client.gui.GuiCoffeeMachine;
import com.co2.molsci.entity.tile.TileEntityCoffeeMachine;
import com.co2.molsci.lib.GuiIds;

import cpw.mods.fml.common.network.IGuiHandler;

public class MSGuiHandler implements IGuiHandler
{
	@Override
	public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) 
	{
		TileEntity entity = world.getTileEntity(x, y, z);
		
		switch (id)
		{
		case GuiIds.COFFEE_MACHINE:
			if (entity instanceof TileEntityCoffeeMachine)
				return new ContainerCoffeeMachine(player.inventory, (TileEntityCoffeeMachine)entity);
			else
				return null;
		default:
			return null;
		}
	}

	@Override
	public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) 
	{
		TileEntity entity = world.getTileEntity(x, y, z);
		
		switch (id)
		{
		case GuiIds.COFFEE_MACHINE:
			if (entity instanceof TileEntityCoffeeMachine)
				return new GuiCoffeeMachine(player.inventory, (TileEntityCoffeeMachine)entity);
			else
				return null;
		default:
			return null;
		}
	}
}