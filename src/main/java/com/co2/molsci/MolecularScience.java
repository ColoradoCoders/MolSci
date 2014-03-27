package com.co2.molsci;

import com.co2.molsci.lib.Reference;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.MOD_VERSION)
public class MolecularScience
{
	@Instance(value = Reference.MOD_ID)
	public static MolecularScience instance;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent e)
	{
		
	}
	
	@EventHandler
	public void init(FMLInitializationEvent e)
	{
		
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent e)
	{
		
	}
}