package com.co2.molsci.entity.tile;

import com.co2.molsci.common.MSRepo;

import cpw.mods.fml.common.FMLCommonHandler;
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
	
	public static TileEntityCoffeeMachine last;
	
	//Inventory slots: 0 = liquid input, 1 = coffee beans, 2 = cup input, 3 = cup output, 4 = bucket output
	private ItemStack[] inventory;
	
	//The liquid levels in the machine
	private int waterLevel;
	private int milkLevel;
	
	//If it should use cream
	private boolean useCream;
	
	private int lastCraft;
	
	public TileEntityCoffeeMachine()
	{
		super();
		inventory = new ItemStack[5];
		waterLevel = 0;
		milkLevel = 0;
		lastCraft = 0;
		useCream = true;
	}
	
	@Override
	public void updateEntity()
	{
		super.updateEntity();
		++lastCraft;
	}
	
	private void updateInput()
	{	
		if (hasOutput() || inventory[1] == null || inventory[2] == null || !removeWaterIfPossible(250))
			return;
		
		int type = inventory[1].getItemDamage();
		System.out.println("Last craft: " + lastCraft);
		
		if (inventory[1].stackSize <= 1)
			inventory[1] = null;
		else
			--(inventory[1].stackSize);
		
		if (inventory[2].stackSize <= 1)
			inventory[2] = null;
		else
			--(inventory[2].stackSize);
		
		boolean cream = shouldUseCream() && removeMilkIfPossible(50);
		
		System.out.println("Update. hasOutput: " + hasOutput() + " inv[3]: " + (inventory[3] != null) + " cream: " + cream);
		
		switch (type)
		{
		case 1:
			inventory[3] = new ItemStack(MSRepo.coffeeCup, 1, 1);
			break;
		case 2:
			inventory[3] = new ItemStack(MSRepo.coffeeCup, 1, 3);
			break;
		}
		
		if (cream)
			inventory[3].setItemDamage(inventory[3].getItemDamage() + 1);
		
		lastCraft = 0;
		System.out.println("Reset lastCraft for entity " + this + " server: " + FMLCommonHandler.instance().getEffectiveSide().isServer());
	}
	
	private void tryAddLiquids()
	{
		if (inventory[0] != null && inventory[0].getItem() == Items.milk_bucket)
		{
			if (canTakeBucket() && addMilkIfPossible(1000))
			{
				inventory[0] = null;
				if (inventory[4] != null)
					++(inventory[4].stackSize);
				else
					inventory[4] = new ItemStack(Items.bucket);
			}
		}
		else if (inventory[0] != null && inventory[0].getItem() == Items.water_bucket)
		{
			if (canTakeBucket() && addWaterIfPossible(1000))
			{
				inventory[0] = null;
				if (inventory[4] != null)
					++(inventory[4].stackSize);
				else
					inventory[4] = new ItemStack(Items.bucket);
			}
		}
	}
	
	private void handleInventoryChange()
	{
		if (worldObj != null && worldObj.isRemote)
			return;
		
		tryAddLiquids();
		updateInput();
		
		if (worldObj != null)
			worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
	}
	
	public boolean hasOutput()
	{
		return inventory[3] != null;
	}
	
	public boolean canTakeBucket()
	{
		return (inventory[4] == null) || (inventory[4].stackSize < 64);
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

		handleInventoryChange();
		
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