package com.co2.molsci.entity.tile;

import com.co2.molsci.common.MSRepo;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
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
	public static final int MAX_MILK_LEVEL = 1000;
	public static final int MAX_WATER_LEVEL = 5000;
	
	//Inventory slots: 0 = liquid input, 1 = coffee beans, 2 = cup input, 3 = cup output, 4 = bucket output
	private ItemStack[] inventory;
	
	//The liquid levels in the machine
	private int waterLevel;
	private int milkLevel;
	
	//If it should use cream
	private boolean useCream;
	
	public TileEntityCoffeeMachine()
	{
		super();
		inventory = new ItemStack[5];
		addWaterIfPossible(5000);
		addMilkIfPossible(100);
		useCream = true;
	}
	
	@Override
	public void updateEntity()
	{
		super.updateEntity();
		
		if (inventory[0] != null && inventory[0].getItem() == Items.milk_bucket)
		{
			while (addMilkIfPossible(1000) && inventory[0] != null && canTakeBucket())
			{
				decrStackSize(0, 1);
				if (inventory[4] != null)
					++(inventory[4].stackSize);
				else
					setInventorySlotContents(4, new ItemStack(Items.bucket));
			}
		}
		else if (inventory[0] != null && inventory[0].getItem() == Items.water_bucket)
		{
			while (addWaterIfPossible(1000) && inventory[0] != null && canTakeBucket())
			{
				decrStackSize(0, 1);
				if (inventory[4] != null)
					++(inventory[4].stackSize);
				else
					setInventorySlotContents(4, new ItemStack(Items.bucket));
			}
		}
			
		if (!canRemoveWater(500) || inventory[3] != null)
			return;
		
		if (inventory[1] != null && inventory[2] != null && inventory[3] == null)
		{
			boolean useCreamer = useCream && removeMilkIfPossible(100);
			removeWaterIfPossible(500);
			
			if (inventory[1].getItemDamage() == 1)
			{
				if (useCreamer)
					setInventorySlotContents(3, new ItemStack(MSRepo.coffeeCup, 1, 2));
				else
					setInventorySlotContents(3, new ItemStack(MSRepo.coffeeCup, 1, 1));
			}
			else
			{
				if (useCreamer)
					setInventorySlotContents(3, new ItemStack(MSRepo.coffeeCup, 1, 4));
				else
					setInventorySlotContents(3, new ItemStack(MSRepo.coffeeCup, 1, 3));
			}
			
			decrStackSize(1, 1);
			decrStackSize(2, 1);
		}
	}
	
	public boolean canTakeBucket()
	{
		return (inventory[4] == null) || (inventory[4].stackSize < 16);
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
		
		tag.setInteger("Water", waterLevel);
		tag.setInteger("Milk", milkLevel);
		tag.setBoolean("UseCream", useCream);
		
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
		
		waterLevel = tag.getInteger("Water");
		milkLevel = tag.getInteger("Milk");
		useCream = tag.getBoolean("UseCream");
		
		NBTTagList list = tag.getTagList("Items", Constants.NBT.TAG_COMPOUND);
		inventory = new ItemStack[5];
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
	
	public boolean shouldUseCream()
	{
		return useCream;
	}
	
	public void setUseCream(boolean b)
	{
		useCream = b;
	}
	
	public int getWaterLevel()
	{
		return waterLevel;
	}
	
	public int getMilkLevel()
	{
		return milkLevel;
	}
	
	public boolean addWaterIfPossible(int amt)
	{
		if (canAddWater(amt))
		{
			waterLevel += amt;
			return true;
		}
		
		return false;
	}
	
	public boolean addMilkIfPossible(int amt)
	{
		if (canAddMilk(amt))
		{
			milkLevel += amt;
			return true;
		}
		
		return false;
	}
	
	public boolean removeWaterIfPossible(int amt)
	{
		if (canRemoveWater(amt))
		{
			waterLevel -= amt;
			return true;
		}
		
		return false;
	}
	
	public boolean removeMilkIfPossible(int amt)
	{
		if (canRemoveMilk(amt))
		{
			milkLevel -= amt;
			return true;
		}
		
		return false;
	}
	
	public boolean canAddWater(int amt)
	{
		return (waterLevel + amt) <= MAX_WATER_LEVEL;
	}
	
	public boolean canAddMilk(int amt)
	{
		return (milkLevel + amt) <= MAX_MILK_LEVEL;
	}
	
	public boolean canRemoveWater(int amt)
	{
		return (waterLevel - amt) >= 0;
	}
	
	public boolean canRemoveMilk(int amt)
	{
		return (milkLevel - amt) >= 0;
	}
}