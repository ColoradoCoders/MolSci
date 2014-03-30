package com.co2.molsci.client.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import com.co2.molsci.client.container.slot.OutputSlot;
import com.co2.molsci.client.container.slot.WhitelistSlot;
import com.co2.molsci.common.MSRepo;
import com.co2.molsci.entity.tile.TileEntityCoffeeMachine;

public class ContainerCoffeeMachine extends ContainerMS
{
	private static final ItemStack[] liquids = { new ItemStack(Items.water_bucket), new ItemStack(Items.milk_bucket) };
	private static final ItemStack[] beans = { new ItemStack(MSRepo.coffeeBean, 1, 1), new ItemStack(MSRepo.coffeeBean, 1, 2) };
	
	private TileEntityCoffeeMachine entity;
	
	public ContainerCoffeeMachine(InventoryPlayer player, TileEntityCoffeeMachine tileEntity)
	{
		this.entity = tileEntity;
		
		this.bindPlayerInventory(player);
		
		//Add the 4 container slots
		this.addSlotToContainer(new WhitelistSlot(entity, 0, 107, 8, liquids));
		this.addSlotToContainer(new WhitelistSlot(entity, 1, 53, 31, beans));
		this.addSlotToContainer(new WhitelistSlot(entity, 2, 53, 58, new ItemStack[]{ new ItemStack(MSRepo.coffeeCup, 1, 0) }));
		this.addSlotToContainer(new OutputSlot(entity, 3, 107, 58));
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer player)
	{
		return entity.isUseableByPlayer(player);
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slot)
	{
		ItemStack stack = null;
		Slot invSlot = (Slot) this.inventorySlots.get(slot);
		
		if (invSlot != null && invSlot.getHasStack())
		{
			ItemStack slotStack = invSlot.getStack();
			stack = slotStack.copy();
			
			//If the origin slot is in the player inventory
			if (slot < INVENTORY_OFFSET)
			{
				if (slotStack.getItem() == MSRepo.coffeeBean)
				{
					//Try to merge a coffee bean
					if (!this.mergeItemStack(slotStack, 37, 38, false))
						return null;
				}
				else if (slotStack.getItem() == MSRepo.coffeeCup)
				{
					if (!this.mergeItemStack(slotStack, 38, 39, false))
						return null;
				}
				else if ((slotStack.getItem() == Items.milk_bucket) || slotStack.getItem() == Items.water_bucket)
				{
					if (!this.mergeItemStack(slotStack, 36, 37, false))
						return null;
				}
				else
					return null;
			}
			else if (!this.mergeItemStack(slotStack, 0, INVENTORY_OFFSET, false))
				return null;
			
			if (slotStack.stackSize == 0)
				invSlot.putStack(null);
			else
				invSlot.onSlotChanged();
		}
		
		return stack;
	}
}
