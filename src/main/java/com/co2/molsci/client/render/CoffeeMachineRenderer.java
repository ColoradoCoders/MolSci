package com.co2.molsci.client.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import com.co2.molsci.entity.tile.TileEntityCoffeeMachine;
import com.co2.molsci.lib.Reference;

public class CoffeeMachineRenderer extends TileEntitySpecialRenderer
{
	public static final ResourceLocation COFFEE_MACHINE_TEXTURE = new ResourceLocation(Reference.MOD_ID.toLowerCase(), "textures/models/coffeeMachine.png");
	public static int renderId;
	
	private ModelCoffeeMachine model;
	
	public CoffeeMachineRenderer()
	{
		model = new ModelCoffeeMachine();
	}
	
	@Override
	public void renderTileEntityAt(TileEntity entity, double x, double y, double z, float f) 
	{
		TileEntityCoffeeMachine coffeeEntity = (TileEntityCoffeeMachine) entity;
		
		if (coffeeEntity == null)
			return;
		
		model.renderCup = coffeeEntity.hasOutput();

		Minecraft.getMinecraft().renderEngine.bindTexture(COFFEE_MACHINE_TEXTURE);

		//TODO orient correctly
		
		GL11.glPushMatrix();
		
		GL11.glTranslatef((float)x + 0.5f, (float)y - 0.5f, (float)z + 0.5f);
		GL11.glScalef(1.0f, -1.0f, -1.0f);
		GL11.glRotatef(180.0f, 1.0f, 0.0f, 1.0f);
		GL11.glScalef(-1.0f, 1.0f, 1.0f);
		GL11.glRotatef(180.0f, 0.0f, 0.0f, 0.0f);
		GL11.glTranslatef(0.0f, -2.0f, 0.0f);
		
		model.render((Entity)null, 0.0f, 0.0f, -0.0f, 0.0f, 0.0f, 0.0625f);
		
		GL11.glPopMatrix();
	}
}