package com.co2.molsci.network;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.co2.molsci.lib.PacketIds;
import com.co2.molsci.network.data.INetworkTile;

public class TileUpdatePacket extends UpdatePacket
{
	public TileUpdatePacket()
	{
		super(PacketIds.TILE_UPDATE);
	}
	
	public TileUpdatePacket(INetworkTile tile)
	{
		super(PacketIds.TILE_UPDATE);
		
		payload = tile.getPacketPayload();
		
		TileEntity entity = (TileEntity) tile;
		xCoord = entity.xCoord;
		yCoord = entity.yCoord;
		zCoord = entity.zCoord;
	}
	
	public boolean targetExists(World world)
	{
		return world.blockExists(xCoord, yCoord, zCoord);
	}
	
	public TileEntity getTarget(World world)
	{
		return world.getTileEntity(xCoord, yCoord, zCoord);
	}
}