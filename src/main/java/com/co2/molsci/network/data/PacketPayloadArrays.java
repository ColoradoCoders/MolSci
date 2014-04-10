package com.co2.molsci.network.data;

import com.co2.molsci.util.MSUtils;

import io.netty.buffer.ByteBuf;

public class PacketPayloadArrays extends PacketPayload
{
	public int[] intPayload = new int[0];
	public float[] floatPayload = new float[0];
	public String[] stringPayload = new String[0];
	
	public PacketPayloadArrays()
	{ }
	
	public PacketPayloadArrays(int intLen, int floatLen, int stringLen)
	{
		intPayload = new int[intLen];
		floatPayload = new float[floatLen];
		stringPayload = new String[stringLen];
	}
	
	public void append(PacketPayloadArrays other)
	{
		if (other == null)
			return;
		
		if (other.intPayload.length > 0)
			intPayload = MSUtils.concat(intPayload, other.intPayload);
		if (other.floatPayload.length > 0)
			floatPayload = MSUtils.concat(floatPayload, other.floatPayload);
		if (other.stringPayload.length > 0)
			stringPayload = MSUtils.concat(stringPayload, other.stringPayload);
	}
	
	public PacketPayloadArrays append(int[] other)
	{
		if (other == null || other.length < 1)
			return this;
		
		intPayload = MSUtils.concat(intPayload, other);
		
		return this;
	}
	
	public PacketPayloadArrays append(int other)
	{
		intPayload = MSUtils.concat(intPayload, new int[]{ other });
		return this;
	}
	
	public PacketPayloadArrays append(float[] other)
	{
		if (other == null || other.length < 1)
			return this;
		
		floatPayload = MSUtils.concat(floatPayload, other);
		return this;
	}
	
	public PacketPayloadArrays append(float other)
	{
		floatPayload = MSUtils.concat(floatPayload, new float[]{ other });
		return this;
	}
	
	public PacketPayloadArrays append(String[] other)
	{
		if (other == null || other.length < 1)
			return this;
		
		stringPayload = MSUtils.concat(stringPayload, other);
		return this;
	}
	
	public PacketPayloadArrays append(String other)
	{
		if (MSUtils.isNullOrEmpty(other))
			return this;
		
		stringPayload = MSUtils.concat(stringPayload, new String[]{ other });
		return this;
	}
	
	@Override
	public void writeData(ByteBuf data) 
	{
		data.writeInt(intPayload.length);
		data.writeInt(floatPayload.length);
		data.writeInt(stringPayload.length);
		
		for (int i : intPayload)
			data.writeInt(i);
		for (float f : floatPayload)
			data.writeFloat(f);
		for (String s : stringPayload)
			MSUtils.writeString(data, s);
	}

	@Override
	public void readData(ByteBuf data) 
	{
		intPayload = new int[data.readInt()];
		floatPayload = new float[data.readInt()];
		stringPayload = new String[data.readInt()];
		
		for (int i = 0; i < intPayload.length; ++i)
			intPayload[i] = data.readInt();
		for (int i = 0; i < floatPayload.length; ++i)
			floatPayload[i] = data.readFloat();
		for (int i = 0; i < stringPayload.length; ++i)
			stringPayload[i] = MSUtils.readString(data);
	}

	@Override
	public Type getType() 
	{
		return Type.ARRAY;
	}
}