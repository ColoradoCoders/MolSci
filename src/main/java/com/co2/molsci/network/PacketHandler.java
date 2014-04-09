package com.co2.molsci.network;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

import java.util.EnumMap;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.INetHandler;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.network.Packet;
import net.minecraft.world.World;

import org.apache.logging.log4j.Level;

import com.co2.molsci.MolecularScience;
import com.co2.molsci.lib.Reference;

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
		
		switch (FMLCommonHandler.instance().getEffectiveSide())
		{
		case CLIENT:
			msg.executeClient(Minecraft.getMinecraft().thePlayer);
			break;
		case SERVER:
			INetHandler net = ctx.channel().attr(NetworkRegistry.NET_HANDLER).get();
			msg.executeServer(((NetHandlerPlayServer)net).playerEntity);
			break;
		default:
			break;
		}
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