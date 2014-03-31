package com.co2.molsci.client.render;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelCoffeeMachine extends ModelBase
{
	//Boxes in the model
	ModelRenderer base;
	ModelRenderer wall1;
	ModelRenderer wall2;
	ModelRenderer back;
	ModelRenderer top;
	ModelRenderer slant;
	ModelRenderer spout;
	ModelRenderer control;
	ModelRenderer cup;
	ModelRenderer handle;
	
	public boolean renderCup;

	public ModelCoffeeMachine()
	{
		textureWidth = 64;
		textureHeight = 64;
		renderCup = false;

		base = new ModelRenderer(this, 0, 0);
		base.addBox(0F, 0F, 0F, 7, 1, 4);
		base.setRotationPoint(-2F, 23F, -2F);
		base.setTextureSize(64, 64);
		base.mirror = true;
		setRotation(base, 0F, 0F, 0F);
		wall1 = new ModelRenderer(this, 0, 15);
		wall1.addBox(0F, 0F, 0F, 3, 7, 1);
		wall1.setRotationPoint(-2F, 16F, -2F);
		wall1.setTextureSize(64, 64);
		wall1.mirror = true;
		setRotation(wall1, 0F, 0F, 0F);
		wall2 = new ModelRenderer(this, 0, 15);
		wall2.addBox(0F, 0F, 0F, 3, 7, 1);
		wall2.setRotationPoint(-2F, 16F, 1F);
		wall2.setTextureSize(64, 64);
		wall2.mirror = true;
		setRotation(wall2, 0F, 0F, 0F);
		back = new ModelRenderer(this, 8, 15);
		back.addBox(0F, 0F, 0F, 2, 9, 2);
		back.setRotationPoint(-2F, 14F, -1F);
		back.setTextureSize(64, 64);
		back.mirror = true;
		setRotation(back, 0F, 0F, 0F);
		top = new ModelRenderer(this, 0, 10);
		top.addBox(0F, 0F, 0F, 5, 1, 4);
		top.setRotationPoint(-2F, 15F, -2F);
		top.setTextureSize(64, 64);
		top.mirror = true;
		setRotation(top, 0F, 0F, 0F);
		slant = new ModelRenderer(this, 0, 5);
		slant.addBox(0F, 0F, 0F, 3, 1, 4);
		slant.setRotationPoint(1F, 15F, -2F);
		slant.setTextureSize(64, 64);
		slant.mirror = true;
		setRotation(slant, 0F, 0F, 0.4089647F);
		spout = new ModelRenderer(this, 14, 5);
		spout.addBox(0F, 0F, 0F, 1, 3, 2);
		spout.setRotationPoint(2F, 16F, -1F);
		spout.setTextureSize(64, 64);
		spout.mirror = true;
		setRotation(spout, 0F, 0F, -0.2974289F);
		control = new ModelRenderer(this, 22, 0);
		control.addBox(0F, 0F, 0F, 1, 2, 4);
		control.setRotationPoint(4F, 23F, -2F);
		control.setTextureSize(64, 64);
		control.mirror = true;
		setRotation(control, 0F, 0F, -0.3346075F);
		cup = new ModelRenderer(this, 16, 15);
		cup.addBox(0F, 0F, 0F, 2, 3, 2);
		cup.setRotationPoint(1.5F, 20F, -1F);
		cup.setTextureSize(64, 64);
		cup.mirror = true;
		setRotation(cup, 0F, 0F, 0F);
		handle = new ModelRenderer(this, 18, 12);
		handle.addBox(0F, 0F, 0F, 1, 2, 1);
		handle.setRotationPoint(2F, 20.5F, 0.5F);
		handle.setTextureSize(64, 64);
		handle.mirror = true;
		setRotation(handle, 0F, 0F, 0F);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
	{
		super.render(entity, f, f1, f2, f3, f4, f5);
		setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		base.render(f5);
		wall1.render(f5);
		wall2.render(f5);
		back.render(f5);
		top.render(f5);
		slant.render(f5);
		spout.render(f5);
		control.render(f5);
		if (renderCup)
		{
			cup.render(f5);
			handle.render(f5);
		}
	}

	private void setRotation(ModelRenderer model, float x, float y, float z)
	{
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}
}