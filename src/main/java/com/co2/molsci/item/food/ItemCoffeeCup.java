package com.co2.molsci.item.food;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import com.co2.molsci.common.MSRepo;

public class ItemCoffeeCup extends MSItemFood
{
	public static final String[] itemNames = { "empty", "lightRoast", "lightRoastCream", "darkRoast", "darkRoastCream" };
	public static final String[] textureNames = { "food/coffeeCup", "food/coffeeCupLight", "food/coffeeCupLightCream", "food/coffeeCupDark", "food/coffeeCupDarkCream" };
	public static final int[] hunger = { 0, 4, 4, 6, 6 };
	public static final float[] saturation = { 0.0f, 1.0f, 1.2f, 0.5f, 0.8f };
	public static final Integer[] blackList = { 0 };
	
	public ItemCoffeeCup()
	{
		super(hunger, saturation, itemNames, textureNames, blackList);
		this.setHasSubtypes(true);
		this.setUnlocalizedName("coffeeCup");
	}
	
	@Override
	public ItemStack onEaten(ItemStack stack, World world, EntityPlayer player)
	{
		ItemStack retStack = super.onEaten(stack, world, player);
		
		if (!player.capabilities.isCreativeMode)
			player.inventory.addItemStackToInventory(new ItemStack(MSRepo.coffeeCup, 1, 0));
		
		return retStack;
	}
	
	@Override
	public int getItemStackLimit(ItemStack stack)
	{
		return (stack.getItemDamage() == 0) ? 64 : 1;
	}
	
	@Override
	public void onFoodEaten(ItemStack stack, World world, EntityPlayer player)
	{
		
	}
}
