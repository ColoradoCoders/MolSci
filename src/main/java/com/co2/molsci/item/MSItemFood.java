package com.co2.molsci.item;

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

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class MSItemFood extends ItemFood
{
	int[] hunger;
	float[] saturation;
	String[] unlocalizedNames;
	String[] iconNames;
	IIcon[] icons;
	
	public MSItemFood(int[] hunger, float[] saturation, String[] unlocalizedNames, String[] iconNames)
	{
		super(0, 0, false);
		this.hunger = hunger;
		this.saturation = saturation;
		this.unlocalizedNames = unlocalizedNames;
		this.iconNames = iconNames;
	}
	
	@Override
	public ItemStack onEaten(ItemStack stack, World world, EntityPlayer player)
	{
		--stack.stackSize;
		int dam = stack.getItemDamage();
		player.getFoodStats().addStats(hunger[dam], saturation[dam]);
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