package com.co2.molsci.client.container.slot;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class OutputSlot extends Slot
{
	public OutputSlot(IInventory inv, int i, int x, int y)
	{
		super(inv, i, x, y);
	}
	
	@Override
	public boolean isItemValid(ItemStack stack)
	{
		return false;
	}
}