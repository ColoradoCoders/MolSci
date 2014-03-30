package com.co2.molsci.client.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;

public class ContainerMS extends Container
{
	protected static final int INVENTORY_OFFSET = 36;
	
	public ContainerMS()
	{
		
	}
	
	protected void bindPlayerInventory(InventoryPlayer player)
	{
		//Add the hotbar
		for (int i = 0; i < 9; ++i)
			this.addSlotToContainer(new Slot(player, i, 8 + (i * 18), 142));

		//Add the rest of the player inventory
		for (int y = 0; y < 3; ++y)
			for (int x = 0; x < 9; ++x)
				this.addSlotToContainer(new Slot(player, 9 + x + (y * 9), 8 + (x * 18), 84 + (y * 18)));
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer var1) 
	{
		return true;
	}
}
