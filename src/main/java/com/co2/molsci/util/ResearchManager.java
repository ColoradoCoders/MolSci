package com.co2.molsci.util;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;

public class ResearchManager
{	
	private final String KEY = "molsciResearch";
	
	public void addRP(EntityPlayer player, String category, int amount)
	{	
		int lvl = getLevel(player, category);
		int sum = getRP(player, category) + amount;
		player.getEntityData().getCompoundTag(KEY).setInteger(category, sum);
		if(lvl > getLevel(player, category)) {
			player.getEntityData().getCompoundTag(KEY).setBoolean("canUpgrade", true);
			ChatComponentText message = new ChatComponentText("Congrats you just leveled up! Press the P key to pick a new buff.");
			player.addChatMessage(message);
		}
	}
	
	public byte getBuff(EntityPlayer player, String category)
	{
		return player.getEntityData().getCompoundTag(KEY).getByteArray(category)[getLevel(player, category)];
	}
	
	public int getLevel(EntityPlayer player, String category)
	{
		int total = player.getEntityData().getCompoundTag(KEY).getInteger(category);
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
	
	public int getRP(EntityPlayer player, String category) {
		return player.getEntityData().getCompoundTag(KEY).getInteger(category);
	}
	
	public boolean canUpgrade(EntityPlayer player) {
		return player.getEntityData().getCompoundTag(KEY).getBoolean("canUpgrade");
	}
	
	// does not check if the player can upgrade
	public void setBuff(EntityPlayer player, String category, Byte choice) {
		player.getEntityData().getCompoundTag(KEY).getByteArray(category)[getLevel(player, category)] = choice;
	}
	
}
