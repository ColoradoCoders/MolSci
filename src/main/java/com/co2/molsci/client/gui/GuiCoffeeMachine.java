package com.co2.molsci.client.gui;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

import com.co2.molsci.client.container.ContainerCoffeeMachine;
import com.co2.molsci.entity.tile.TileEntityCoffeeMachine;
import com.co2.molsci.lib.Reference;

public class GuiCoffeeMachine extends GuiContainer
{
	public static final ResourceLocation texture = new ResourceLocation(Reference.MOD_ID.toLowerCase(), "textures/gui/coffeeMachine.png");
	
	public GuiCoffeeMachine(InventoryPlayer player, TileEntityCoffeeMachine entity)
	{
		super(new ContainerCoffeeMachine(player, entity));
		
		this.xSize = 176;
		this.ySize = 166;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3) 
	{
		GL11.glColor4f(1f, 1f, 1f, 1f);
		
		this.mc.getTextureManager().bindTexture(texture);
		
		int xStart = (this.width - this.xSize) / 2;
		int yStart = (this.height - this.ySize) / 2;
		
		this.drawTexturedModalRect(xStart, yStart, 0, 0, this.xSize, this.ySize);
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j)
	{
		//TODO draw text for name and liquid amounts
	}
}