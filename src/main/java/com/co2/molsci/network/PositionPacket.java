package com.co2.molsci.network;

import io.netty.buffer.ByteBuf;

public abstract class PositionPacket extends MSPacket
{
	public int xCoord;
	public int yCoord;
	public int zCoord;
	
	public PositionPacket()
	{ }
	
	public PositionPacket(int x, int y, int z)
	{
		this.xCoord = x;
		this.yCoord = y;
		this.zCoord = z;
	}
	
	@Override
	public void writeData(ByteBuf data)
	{
		data.writeInt(xCoord);
		data.writeInt(yCoord);
		data.writeInt(zCoord);
	}
	
	@Override
	public void readData(ByteBuf data)
	{
		xCoord = data.readInt();
		yCoord = data.readInt();
		zCoord = data.readInt();
	}
}
