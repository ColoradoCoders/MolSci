package com.co2.molsci.util;

import net.minecraft.item.ItemStack;

public class MSUtils 
{
	public static boolean arrayContains(Object[] arr, Object obj)
	{
		for (Object o : arr)
			if (o.equals(obj))
				return true;
		
		return false;
	}
	
	public static boolean itemStackMatch(ItemStack s1, ItemStack s2)
	{
		return (s1.getItem() == s1.getItem()) && (s1.getItemDamage() == s2.getItemDamage());
	}
	
	public static boolean itemStackArrayContains(ItemStack[] arr, ItemStack stack)
	{
		for (ItemStack is : arr)
			if (itemStackMatch(is, stack))
				return true;
		
		return false;
	}
}