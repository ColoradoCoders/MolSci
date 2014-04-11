package com.co2.molsci.util;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagList;

public class ResearchManager
{
	NBTTagList test = new NBTTagList();
	
	public void addBuff(EntityPlayer player, String category, byte choice) {
		player.getEntityData().getByteArray(category)[getLevel(player, category)] = choice;
	}
	
	public void addRP(EntityPlayer player, String category, int amount)
	{
		player.getEntityData().setInteger(category, amount);
	}
	
	public byte getBuff(EntityPlayer player, String category)
	{
		return player.getEntityData().getByteArray(category)[getLevel(player, category)];
	}
	
	public int getLevel(EntityPlayer player, String category)
	{
		int total = player.getEntityData().getInteger(category);
		if(total < 500)
			return 0;
		else if(total < 1500)
			return 1;
		else if(total < 3000)
			return 2;
		else if(total < 5000)
			return 3;
		else if(total < 10000)
			return 4;
		else
			return 5;
	}
	
	public int getSpecificLevel(EntityPlayer player, String category) {
		return player.getEntityData().getInteger(category);
	}
}
