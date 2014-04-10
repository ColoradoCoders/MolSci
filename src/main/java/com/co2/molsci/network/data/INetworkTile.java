package com.co2.molsci.network.data;

import java.io.IOException;

import com.co2.molsci.network.UpdatePacket;

// Provides handler methods for TileEntities that are synchronized by custom packets
public interface INetworkTile 
{
	public void handleUpdatePacket(UpdatePacket packet) throws IOException;
	
	public UpdatePacket getUpdatePacket();
	
	public PacketPayload getPacketPayload();
}