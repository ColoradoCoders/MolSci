package com.co2.molsci.item.food;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemCoffeeBean extends MSItemFood
{
	public static final String[] itemNames = { "raw", "lightRoasted", "darkRoasted" };
	public static final String[] iconNames = { "food/coffeeBeanRaw", "food/coffeeBeanLightRoasted", "food/coffeeBeanDarkRoasted" };
	public static final int[] hunger = { 1, 1, 1 };
	public static final float[] saturation = { 0.2f, 0.2f, 0.2f };
	
	public ItemCoffeeBean()
	{
		super(hunger, saturation, itemNames, iconNames);
		this.setHasSubtypes(true);
		this.setUnlocalizedName("coffeeBean");
	}
	
	@Override
	public void onFoodEaten(ItemStack stack, World world, EntityPlayer player)
	{
		
	}
}