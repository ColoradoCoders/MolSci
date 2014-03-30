package com.co2.molsci.entity.tile;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.Constants;

public class TileEntityCoffeeMachine extends TileEntity implements IInventory
{
	//Inventory slots: 0 = liquid input, 1 = coffee beans, 2 = cup input, 3 = cup output
	private ItemStack[] inventory;
	
	public TileEntityCoffeeMachine()
	{
		super();
		inventory = new ItemStack[4];
	}
	
	@Override
	public void updateEntity()
	{
		super.updateEntity();
		
		//TODO: Finish update code
	}
	
	@Override
	public int getSizeInventory()
	{
		return inventory.length;
	}

	@Override
	public ItemStack getStackInSlot(int slot) 
	{
		return inventory[slot];
	}

	@Override
	public ItemStack decrStackSize(int slot, int count) 
	{
		ItemStack stack = getStackInSlot(slot);
		
		if (stack != null)
		{
			if (stack.stackSize <= count)
				setInventorySlotContents(slot, null);
			else
			{
				stack = stack.splitStack(count);
				markDirty();
			}
		}
		
		return stack;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int slot) 
	{
		ItemStack stack = getStackInSlot(slot);
		setInventorySlotContents(slot, null);
		return stack;
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack stack) 
	{
		inventory[slot] = stack;
		
		if (stack != null && stack.stackSize > getInventoryStackLimit())
			stack.stackSize = getInventoryStackLimit();
		
		markDirty();
	}

	@Override
	public String getInventoryName() 
	{
		return "CoffeeMachine";
	}

	@Override
	public boolean hasCustomInventoryName() 
	{
		//TODO: look at this
		return true;
	}

	@Override
	public int getInventoryStackLimit() 
	{
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) 
	{
		return player.getDistanceSq(this.xCoord + 0.5, this.yCoord + 0.5, this.zCoord + 0.5) <= 64.0;
	}

	@Override
	public void openInventory() 
	{ }

	@Override
	public void closeInventory() 
	{ }

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack) 
	{
		return false; //Used in auto-insertion and the like
	}
	
	@Override
	public void writeToNBT(NBTTagCompound tag)
	{
		super.writeToNBT(tag);
		
		NBTTagList list = new NBTTagList();
		for (int i = 0; i < inventory.length; ++i)
		{
			if (getStackInSlot(i) != null)
			{
				NBTTagCompound stack = new NBTTagCompound();
				stack.setByte("Slot", (byte)i);
				inventory[i].writeToNBT(stack);
				list.appendTag(stack);
			}
		}
		tag.setTag("Items", list);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound tag)
	{
		super.readFromNBT(tag);
		
		NBTTagList list = tag.getTagList("Items", Constants.NBT.TAG_COMPOUND);
		inventory = new ItemStack[4];
		for (int i = 0; i < inventory.length && i < list.tagCount(); ++i)
		{
			NBTTagCompound stack = (NBTTagCompound) list.getCompoundTagAt(i);
			byte index = stack.getByte("Slot");
			if (index > 0 && index < inventory.length)
				inventory[index] = ItemStack.loadItemStackFromNBT(stack);
		}
	}
	
	@Override
	public Packet getDescriptionPacket()
	{
		NBTTagCompound tag = new NBTTagCompound();
		this.writeToNBT(tag);
		return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 1, tag);
	}
	
	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity packet)
	{
		this.readFromNBT(packet.func_148857_g());
	}
}