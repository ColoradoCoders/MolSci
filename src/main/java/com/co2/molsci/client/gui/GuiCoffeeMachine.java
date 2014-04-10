package com.co2.molsci.client.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import com.co2.molsci.client.container.ContainerCoffeeMachine;
import com.co2.molsci.entity.tile.TileEntityCoffeeMachine;
import com.co2.molsci.lib.Reference;
import com.co2.molsci.network.PacketHandler;
import com.co2.molsci.network.TileUpdatePacket;

public class GuiCoffeeMachine extends GuiContainer
{
	public static final ResourceLocation texture = new ResourceLocation(Reference.MOD_ID.toLowerCase(), "textures/gui/coffeeMachine.png");
	
	public TileEntityCoffeeMachine entity;
	
	public GuiCoffeeMachine(InventoryPlayer player, TileEntityCoffeeMachine entity)
	{
		super(new ContainerCoffeeMachine(player, entity));
		
		this.entity = entity;
		this.xSize = 176;
		this.ySize = 166;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public void initGui()
	{
		super.initGui();
		this.buttonList.add(0, new GuiButton(0, this.guiLeft + 112, this.guiTop + 56, 55, 20, entity.shouldUseCream() ? "Cream" : "No Cream"));
	}
	
	@Override
	public void updateScreen()
	{
		super.updateScreen();
		
		((GuiButton)this.buttonList.get(0)).displayString = entity.shouldUseCream() ? "Cream" : "No Cream";
	}
	
	@Override
	protected void actionPerformed(GuiButton button)
	{
		if (buttonList.indexOf(button) == 0) 
			PacketHandler.instance.sendToServer(new TileUpdatePacket(entity));
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
		fontRendererObj.drawString("Coffee Machine", 5, 4, 4210752);
		
		String water = String.format("%4d/%d mB", entity.getWaterLevel(), TileEntityCoffeeMachine.MAX_WATER_LEVEL);
		String milk = String.format ("%4d/%d mB", entity.getMilkLevel(), TileEntityCoffeeMachine.MAX_MILK_LEVEL);
		
		int blue = 0;
		blue += (255 << 24);
		blue += 255;
		int white = 0;
		white += (255 << 24);
		white += (255 << 16);
		white += (255 << 8);
		white += 255;
		
		fontRendererObj.drawString(water, 80, 31, blue);
		fontRendererObj.drawString(milk, 80, 45, white);
	}
}