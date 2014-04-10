package com.co2.molsci.network;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

import java.io.IOException;
import java.util.EnumMap;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.INetHandler;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.network.Packet;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import org.apache.logging.log4j.Level;

import com.co2.molsci.MolecularScience;
import com.co2.molsci.lib.PacketIds;
import com.co2.molsci.lib.Reference;
import com.co2.molsci.network.data.INetworkTile;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.FMLEmbeddedChannel;
import cpw.mods.fml.common.network.FMLIndexedMessageToMessageCodec;
import cpw.mods.fml.common.network.FMLOutboundHandler;
import cpw.mods.fml.common.network.FMLOutboundHandler.OutboundTarget;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.relauncher.Side;

public class PacketHandler extends FMLIndexedMessageToMessageCodec<MSPacket> 
{
	public static PacketHandler instance;
	
	public EnumMap<Side, FMLEmbeddedChannel> channels;
	
	public static void initialize()
	{
		MolecularScience.logger.log(Level.INFO, "Initializing MolSci PacketHandler.");
		
		instance = new PacketHandler();
		instance.channels = NetworkRegistry.INSTANCE.newChannel(Reference.MOD_CHANNEL, instance);
		
		instance.addDiscriminator(0, UpdatePacket.class);
		instance.addDiscriminator(1, TileUpdatePacket.class);
	}
	
	@Override
	public void encodeInto(ChannelHandlerContext ctx, MSPacket msg, ByteBuf target) throws Exception
	{
		msg.writeData(target);
	}

	@Override
	public void decodeInto(ChannelHandlerContext ctx, ByteBuf source, MSPacket msg)
	{
		msg.readData(source);
		
		try
		{
			INetHandler net = ctx.channel().attr(NetworkRegistry.NET_HANDLER).get();
			EntityPlayer player = null;
			if (FMLCommonHandler.instance().getEffectiveSide().isServer())
				player = ((NetHandlerPlayServer)net).playerEntity;
			else
				player = Minecraft.getMinecraft().thePlayer;
			
			if (player == null)
				return;
			
			switch (msg.getId())
			{
			case PacketIds.TILE_UPDATE:
				handleTileUpdate(player, (TileUpdatePacket)msg);
				break;
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	private void handleTileUpdate(EntityPlayer player, TileUpdatePacket packet) throws IOException
	{
		World world = player.worldObj;
		
		if (!packet.targetExists(world))
			return;
		
		TileEntity entity = packet.getTarget(world);
		
		if (!(entity instanceof INetworkTile))
			return;
		
		INetworkTile tile = (INetworkTile) entity;
		tile.handleUpdatePacket(packet);
	}
	
	public void sendToAllClients(Packet packet, World world, int x, int y, int z, int maxDistance) 
	{
		channels.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.ALL);
		channels.get(Side.SERVER).writeOutbound(packet);
	}

	public void sendToAllClients(MSPacket packet, World world, int x, int y, int z, int maxDistance) 
	{
		channels.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.ALL);
		channels.get(Side.SERVER).writeOutbound(packet);
	}

	public void sendToPlayer(EntityPlayer entityplayer, MSPacket packet) 
	{
		channels.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.PLAYER);
		channels.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGETARGS).set(entityplayer);
		channels.get(Side.SERVER).writeOutbound(packet);
	}

	public void sendToServer(MSPacket packet) 
	{
		channels.get(Side.CLIENT).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(OutboundTarget.TOSERVER);
		channels.get(Side.CLIENT).writeOutbound(packet);
	}
}