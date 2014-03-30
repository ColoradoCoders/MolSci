package com.co2.molsci.client.container.slot;

import com.co2.molsci.util.MSUtils;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class WhitelistSlot extends Slot
{
	private ItemStack[] whitelist;
	
	public WhitelistSlot(IInventory inv, int i, int x, int y, ItemStack[] white)
	{
		super(inv, i, x, y);
		this.whitelist = white;
	}
	
	@Override
	public boolean isItemValid(ItemStack stack)
	{
		return MSUtils.itemStackArrayContains(whitelist, stack);
	}
}