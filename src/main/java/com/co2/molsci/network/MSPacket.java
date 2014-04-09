package com.co2.molsci.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;

public abstract class MSPacket 
{
	public abstract int getId();
	
	public abstract void readData(ByteBuf data);
	
	public abstract void writeData(ByteBuf data);
	
	public abstract void executeClient(EntityPlayer player);
	
	public abstract void executeServer(EntityPlayer player);
}