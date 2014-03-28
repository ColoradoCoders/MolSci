package com.co2.molsci;

import java.io.File;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.co2.molsci.common.MSContent;
import com.co2.molsci.config.ConfigHandler;
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
	
	//Logger object for this mod
	public static final Logger logger = LogManager.getLogger("MolSci");
	
	//Content manager
	public static MSContent content;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent e)
	{
		//Set the mod version
		e.getModMetadata().version = Reference.MOD_VERSION;
		
		//Load the configs
		ConfigHandler.init(e.getModConfigurationDirectory() + File.separator + Reference.MOD_ID.toLowerCase() 
				+ File.separator);
		
		//Initialize content
		content = new MSContent();
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