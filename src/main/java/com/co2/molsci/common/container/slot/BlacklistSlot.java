package com.co2.molsci.common.container.slot;

import com.co2.molsci.util.MSUtils;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class BlacklistSlot extends Slot
{
	private ItemStack[] blacklist;
	
	public BlacklistSlot(IInventory inv, int i, int x, int y, ItemStack[] black)
	{
		super(inv, i, x, y);
		this.blacklist = black;
	}
	
	@Override
	public boolean isItemValid(ItemStack stack)
	{
		return !(MSUtils.itemStackArrayContains(blacklist, stack));
	}
}