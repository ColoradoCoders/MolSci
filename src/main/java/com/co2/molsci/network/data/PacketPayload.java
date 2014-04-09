package com.co2.molsci.network.data;

import io.netty.buffer.ByteBuf;

public abstract class PacketPayload 
{
	public static enum Type
	{
		NULL, ARRAY
	}
	
	public abstract void writeData(ByteBuf data);
	
	public abstract void readData(ByteBuf data);
	
	public abstract Type getType();
}