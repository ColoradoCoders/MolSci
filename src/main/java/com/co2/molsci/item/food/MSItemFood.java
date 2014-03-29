package com.co2.molsci.item.food;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import com.co2.molsci.lib.Reference;
import com.co2.molsci.util.MSUtils;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class MSItemFood extends ItemFood
{
	int[] hunger;
	Integer[] blackList;
	float[] saturation;
	String[] unlocalizedNames;
	String[] iconNames;
	IIcon[] icons;
	boolean isLiquid;
	
	public MSItemFood(int[] hunger, float[] saturation, String[] unlocalizedNames, String[] iconNames)
	{
		super(0, 0, false);
		this.hunger = hunger;
		this.blackList = new Integer[]{ -1 };
		this.saturation = saturation;
		this.unlocalizedNames = unlocalizedNames;
		this.iconNames = iconNames;
		this.isLiquid = false;
	}
	
	public MSItemFood(int[] hunger, float[] saturation, String[] unlocalizedNames, String[] iconNames, Integer[] blackList)
	{
		super(0, 0, false);
		this.hunger = hunger;
		this.blackList = blackList;
		this.saturation = saturation;
		this.unlocalizedNames = unlocalizedNames;
		this.iconNames = iconNames;
		this.isLiquid = false;
	}
	
	public boolean isLiquid()
	{
		return isLiquid;
	}
	
	public MSItemFood setIsLiquid(boolean b)
	{
		isLiquid = b;
		return this;
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
	{
		if (!MSUtils.arrayContains(blackList, stack.getItemDamage()) && player.canEat(false))
			player.setItemInUse(stack, stack.getMaxItemUseDuration());
		
		return stack;
	}
	
	@Override
	public ItemStack onEaten(ItemStack stack, World world, EntityPlayer player)
	{
		if (!player.capabilities.isCreativeMode)
			--stack.stackSize;
		
		int dam = stack.getItemDamage();
		player.getFoodStats().addStats(hunger[dam], saturation[dam]);
		if (this.isLiquid)
			//TODO: Check if random.slurp is the proper drinking sound
			world.playSoundAtEntity(player, "random.slurp", 0.5f, world.rand.nextFloat() * 0.1f + 0.9f);
		else
			world.playSoundAtEntity(player, "random.burp", 0.5f, world.rand.nextFloat() * 0.1f + 0.9f);
		this.onFoodEaten(stack, world, player);
		return stack;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIconFromDamage(int meta)
	{
		int dam = MathHelper.clamp_int(meta, 0, iconNames.length);
		return icons[dam];
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister register)
	{
		this.icons = new IIcon[iconNames.length];
		
		for (int i = 0; i < icons.length; ++i)
			this.icons[i] = register.registerIcon(Reference.toResourceString(iconNames[i]));
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack)
	{
		int pos = MathHelper.clamp_int(stack.getItemDamage(), 0, unlocalizedNames.length);
		return "item.molsci." + super.getUnlocalizedName().substring(5) + "." + unlocalizedNames[pos];
	}
	
	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void getSubItems(Item i, CreativeTabs tab, List list)
	{
		for (int j = 0; j < unlocalizedNames.length; ++j)
			list.add(new ItemStack(i, 1, j));
	}
}