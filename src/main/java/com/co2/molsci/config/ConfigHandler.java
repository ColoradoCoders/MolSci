package com.co2.molsci.config;

import java.io.File;

import org.apache.logging.log4j.Level;

import com.co2.molsci.MolecularScience;

import net.minecraftforge.common.config.Configuration;

public class ConfigHandler 
{
	public static void init(String path)
	{
		Configuration con = new Configuration(new File(path + "molsci.properties"));
		
		initWorldSettings(con);
	}
	
	private static void initWorldSettings(Configuration con)
	{
		try
		{
			con.load();
			
			con.addCustomCategoryComment("WorldGen", "Settings on how things in the world generate.");
			WorldGenSettings.COFFEE_PLANT_GEN_SIZE = con.get("WorldGen", "coffeePlantGenSize", WorldGenSettings.COFFEE_PLANT_GEN_SIZE_DEFAULT).getInt(WorldGenSettings.COFFEE_PLANT_GEN_SIZE_DEFAULT);
			WorldGenSettings.COFFEE_PLANT_GROWTH_RATE = (float)con.get("WorldGen", "coffeePlantGrowthRate", WorldGenSettings.COFFEE_PLANT_GROWTH_RATE_DEFAULT).getDouble(WorldGenSettings.COFFEE_PLANT_GROWTH_RATE_DEFAULT);
			WorldGenSettings.COFFEE_PLANT_SPAWN_CHANCE = (float)con.get("WorldGen", "coffePlantGenChange", WorldGenSettings.COFFEE_PLANT_SPAWN_CHANCE_DEFAULT).getDouble(WorldGenSettings.COFFEE_PLANT_SPAWN_CHANCE_DEFAULT);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			MolecularScience.logger.log(Level.ERROR, "MolecularScience could not load it's configuration file.");
		}
		finally
		{
			con.save();
		}
	}
}
