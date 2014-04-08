package com.co2.molsci.client.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;

import org.lwjgl.opengl.GL11;

import com.co2.molsci.entity.tile.TileEntityCoffeeMachine;
import com.co2.molsci.lib.Reference;

public class CoffeeMachineRenderer extends TileEntitySpecialRenderer implements IItemRenderer
{
	public static final ResourceLocation COFFEE_MACHINE_TEXTURE = new ResourceLocation(Reference.MOD_ID.toLowerCase(), "textures/models/coffeeMachine.png");
	public static int renderId;
	
	// Two are needed becasue the models are rendered at different sizes
	private ModelCoffeeMachine model;
	private ModelCoffeeMachine itemModel;
	
	public CoffeeMachineRenderer()
	{
		model = new ModelCoffeeMachine();
		itemModel = new ModelCoffeeMachine();
	}
	
	@Override
	public void renderTileEntityAt(TileEntity entity, double x, double y, double z, float f) 
	{
		TileEntityCoffeeMachine coffeeEntity = (TileEntityCoffeeMachine) entity;
		
		if (coffeeEntity == null)
			return;
		
		model.renderCup = coffeeEntity.hasOutput();

		Minecraft.getMinecraft().renderEngine.bindTexture(COFFEE_MACHINE_TEXTURE);
		
		GL11.glPushMatrix();
		
		GL11.glTranslatef((float)x + 0.5f, (float)y - 0.5f, (float)z + 0.5f);
		GL11.glScalef(1.0f, -1.0f, -1.0f);
		GL11.glRotatef(180.0f, 1.0f, 0.0f, 1.0f);
		GL11.glScalef(-1.0f, 1.0f, 1.0f);
		GL11.glRotatef(180.0f, 0.0f, 0.0f, 0.0f);
		switch (entity.getWorldObj().getBlockMetadata(entity.xCoord, entity.yCoord, entity.zCoord))
		{
		case 1:
			GL11.glRotatef(90, 0, 1, 0);
			break;
		case 2:
			GL11.glRotatef(180, 0, 1, 0);
			break;
		case 3:
			GL11.glRotatef(270, 0, 1, 0);
			break;
		}
		GL11.glTranslatef(0.0f, -2.0f, 0.0f);
		
		model.render((Entity)null, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0625f);
		
		GL11.glPopMatrix();
	}

	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) 
	{
		return true;
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) 
	{
		return true;
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data) 
	{
		GL11.glPushMatrix();
		GL11.glScalef(-1.0f, -1.0f, 1.0f);
		
		if (type == ItemRenderType.INVENTORY)
		{
			GL11.glTranslatef(0.0f, -2.0f, 0.0f);
			GL11.glRotatef(-90.0f, 0.0f, 1.0f, 0.0f);
		}
		else if (type == ItemRenderType.EQUIPPED)
		{
			GL11.glTranslatef(-0.7f, -2.7f, 0.7f);
		}
		else if (type == ItemRenderType.EQUIPPED_FIRST_PERSON)
		{
			GL11.glTranslatef(-0.6f, -2.5f, 0.6f);
		}
		else if (type == ItemRenderType.ENTITY)
		{
			GL11.glTranslatef(0.0f, -2.1f, 0.0f);
		}
		
		Minecraft.getMinecraft().renderEngine.bindTexture(COFFEE_MACHINE_TEXTURE);
		
		itemModel.renderCup = false;
		itemModel.render((Entity)null, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.1f);
		
		GL11.glPopMatrix();
	}
}