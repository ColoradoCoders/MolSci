package com.co2.molsci;

import com.co2.molsci.lib.Reference;
import com.co2.molsci.proxy.IProxy;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.MOD_VERSION)
public class MolecularScience
{
	@Instance(value = Reference.MOD_ID)
	public static MolecularScience instance;
	
	@SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.SERVER_PROXY_CLASS)
	public static IProxy proxy;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent e)
	{
		//Set the mod version
		e.getModMetadata().version = Reference.MOD_VERSION;
	}
	
	@EventHandler
	public void init(FMLInitializationEvent e)
	{
		//Register custom renderers
		proxy.registerRenderers();
		
		//Register the tile entities
		proxy.registerTileEntities();
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent e)
	{
		
	}
}