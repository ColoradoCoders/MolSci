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
	
	public UpdatePacket(int id)
	{
		this(id, null);
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
		data.writeInt(id);
		super.writeData(data);
		
		if (payload != null)
		{
			data.writeByte(payload.getType().ordinal());
			payload.writeData(data);
		}
		else
			data.writeByte(0);
	}
	
	@Override
	public void readData(ByteBuf data)
	{
		id = data.readInt();
		super.readData(data);
		
		byte type = data.readByte();
		
		payload = PacketPayload.makePayload(type);
		
		if (payload != null)
			payload.readData(data);
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