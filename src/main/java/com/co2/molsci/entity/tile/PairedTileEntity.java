package com.co2.molsci.entity.tile;

import java.io.IOException;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

import com.co2.molsci.network.UpdatePacket;
import com.co2.molsci.network.data.INetworkTile;
import com.co2.molsci.network.data.PacketPayload;

public class PairedTileEntity extends TileEntity implements INetworkTile
{
	private int pairedX, pairedY, pairedZ;
	
	//TODO: store paired particle
	
	public PairedTileEntity()
	{
		
	}
	
	public boolean isPairedEntityValid()
	{
		return (worldObj != null) && (worldObj.getTileEntity(pairedX, pairedY, pairedZ) instanceof PairedTileEntity);
	}
	
	public PairedTileEntity getPairedEntity()
	{
		if (!isPairedEntityValid())
			return null;
		
		return (PairedTileEntity) worldObj.getTileEntity(pairedX, pairedY, pairedZ);
	}

	@Override
	public void handleUpdatePacket(UpdatePacket packet) throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public UpdatePacket getUpdatePacket() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PacketPayload getPacketPayload() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void writeToNBT(NBTTagCompound tag)
	{
		
	}
	
	@Override
	public void readFromNBT(NBTTagCompound tag)
	{
		
	}
	
	@Override
	public Packet getDescriptionPacket()
	{
		NBTTagCompound tag = new NBTTagCompound();
		this.writeToNBT(tag);
		return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 1, tag);
	}
	
	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity packet)
	{
		this.readFromNBT(packet.func_148857_g());
	}
}
