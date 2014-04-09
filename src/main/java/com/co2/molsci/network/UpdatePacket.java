package com.co2.molsci.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;

import com.co2.molsci.network.data.PacketPayload;

public class UpdatePacket extends PositionPacket
{
	private int id;
	
	public PacketPayload payload;
	
	public UpdatePacket()
	{ 
		super(0, 0, 0);
	}
	
	public UpdatePacket(int id, PacketPayload pl)
	{
		this(id, 0, 0, 0, pl);
	}
	
	public UpdatePacket(int id, int x, int y, int z, PacketPayload pl)
	{
		super(x, y, z);
		this.payload = pl;
		this.id = id;
	}
	
	@Override
	public void writeData(ByteBuf data)
	{
		
	}
	
	@Override
	public void readData(ByteBuf data)
	{
		
	}
	
	@Override
	public void executeClient(EntityPlayer player) 
	{ }

	@Override
	public void executeServer(EntityPlayer player)
	{ }

	@Override
	public int getId() 
	{
		return id;
	}
}