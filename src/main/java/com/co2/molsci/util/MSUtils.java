package com.co2.molsci.util;

import io.netty.buffer.ByteBuf;

import java.util.Arrays;

import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class MSUtils 
{
	public static boolean arrayContains(Object[] arr, Object obj)
	{
		for (Object o : arr)
			if (o.equals(obj))
				return true;
		
		return false;
	}
	
	// matches itemStacks ignoring metadata
	public static boolean itemStackMatch(ItemStack s1, ItemStack s2)
	{
		return (s1.getItem() == s2.getItem()) && (s1.getItemDamage() == s2.getItemDamage());
	}
	
	public static boolean itemStackArrayContains(ItemStack[] arr, ItemStack stack)
	{
		for (ItemStack is : arr)
			if (itemStackMatch(is, stack))
				return true;
		
		return false;
	}
	
	public static <T> T[] concat(T[] t1, T[] t2)
	{
		T[] result = Arrays.copyOf(t1, t1.length + t2.length);
		System.arraycopy(t2, 0, result, t1.length, t2.length);
		return result;
	}
	
	public static int[] concat(int[] i1, int[] i2)
	{
		int[] result = Arrays.copyOf(i1, i1.length + i2.length);
		System.arraycopy(i2, 0, result, i1.length, i2.length);
		return result;
	}
	
	public static float[] concat(float[] f1, float[] f2)
	{
		float[] result = Arrays.copyOf(f1, f1.length + f2.length);
		System.arraycopy(f2, 0, result, f1.length, f2.length);
		return result;
	}
	
	public static void writeString(ByteBuf data, String s)
	{
		try
		{
			byte[] b = s.getBytes("UTF-8");
			data.writeInt(b.length);
			data.writeBytes(b);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			data.writeInt(0);
		}
	}
	
	public static String readString(ByteBuf data)
	{
		try
		{
			int len = data.readInt();
			byte[] b = new byte[len];
			data.readBytes(b);
			return new String(b, "UTF-8");
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}
	
	public static boolean isNullOrEmpty(String s)
	{
		return (s == null) || (s.length() < 1);
	}
	
	public static void dropItems(World world, ItemStack stack, int x, int y, int z)
	{
		if (stack == null || stack.stackSize < 1)
			return;
		
		float f = 0.7f;
		double d = (world.rand.nextFloat() * f) + (1.0f - f) * 0.5;
		double d2 = (world.rand.nextFloat() * f) + (1.0f - f) * 0.5;
		double d3 = (world.rand.nextFloat() * f) + (1.0f - f) * 0.5;
		
		EntityItem item = new EntityItem(world, x + d, y + d2, z + d3, stack);
		item.delayBeforeCanPickup = 10;
		
		world.spawnEntityInWorld(item);
	}
	
	public static void dropItems(World world, IInventory inv, int x, int y, int z)
	{
		for (int i = 0; i < inv.getSizeInventory(); ++i)
		{
			ItemStack items = inv.getStackInSlot(i);
			
			if (items != null && items.stackSize > 0)
				dropItems(world, items, x, y, z);
		}
	}
	
	public static void clearInventory(IInventory inv)
	{
		for (int i = 0; i < inv.getSizeInventory(); ++i)
			inv.setInventorySlotContents(i, null);
	}
	
	public static int getMaxHeightOfBlockWithSpaceAbove(World world, int x, int z, Block[] blocks)
	{
		int highest = -1;
		
		for (int y = 254; y >= 0; --y)
		{
			if (arrayContains(blocks, world.getBlock(x, y, z)) && world.isAirBlock(x, y + 1, z))
			{
				highest = y;
				break;
			}
		}
		
		return highest;
	}
}