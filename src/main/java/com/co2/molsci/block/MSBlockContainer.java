package com.co2.molsci.block;

import java.util.List;

import com.co2.molsci.lib.Reference;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public abstract class MSBlockContainer extends BlockContainer
{
	String[] textureNames;
	IIcon[] icons;
	
	public MSBlockContainer(Material mat, float hard, String[] textures)
	{
		super(mat);
		this.textureNames = textures;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister register)
	{
		icons = new IIcon[textureNames.length];
		
		for (int i = 0; i < icons.length; ++i)
			icons[i] = register.registerIcon(Reference.toResourceString(textureNames[i]));
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta)
	{
		return (meta < icons.length) ? icons[meta] : icons[0];
	}
	
	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void getSubBlocks(Item block, CreativeTabs tab, List list)
	{
		for (int i = 0; i < icons.length; ++i)
			list.add(new ItemStack(block, 1, i));
	}
}