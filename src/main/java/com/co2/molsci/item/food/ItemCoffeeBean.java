package com.co2.molsci.item.food;

import com.co2.molsci.common.MSRepo;

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
		super(hunger, saturation, itemNames, iconNames, new Integer[] { 0 });
		this.setHasSubtypes(true);
		this.setUnlocalizedName("coffeeBean");
	}
	
	@Override
	public void onFoodEaten(ItemStack stack, World world, EntityPlayer player)
	{
		// TODO: Add potion effects
	}
	
	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float xC, float yC, float zC)
	{
		int bid = world.getWorldChunkManager().getBiomeGenAt(x, z).biomeID;
		boolean canCoffee = (bid == 21) || (bid == 22) || (bid == 23) || (bid == 149) || (bid == 151);
		
		if (canCoffee && MSRepo.coffeeSapling.canPlaceBlockAt(world, x, y, z) && world.isAirBlock(x, y + 1, z))
		{
			world.setBlock(x, y + 1, z, MSRepo.coffeeSapling, 0, 3);
			--(stack.stackSize);
			return true;
		}
		
		return false;
	}
}