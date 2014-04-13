package com.co2.molsci.item;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;

import com.co2.molsci.lib.Reference;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class MSItem extends Item
{
	String[] unlocalizedNames;
	String[] iconNames;
	
	@SideOnly(Side.CLIENT)
	IIcon[] icons;
	
	public MSItem(String[] unlocalizedNames, String[] iconNames)
	{
		super();
		this.unlocalizedNames = unlocalizedNames;
		this.iconNames = iconNames;
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