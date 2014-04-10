package com.co2.molsci.network.data;

import io.netty.buffer.ByteBuf;

public abstract class PacketPayload 
{
	public static enum Type
	{
		NULL, ARRAY
	}
	
	public static PacketPayload makePayload(byte type)
	{
		switch (type)
		{
		case 1:
			return new PacketPayloadArrays();
		default:
			return null;
		}
	}
	
	public abstract void writeData(ByteBuf data);
	
	public abstract void readData(ByteBuf data);
	
	public abstract Type getType();
}