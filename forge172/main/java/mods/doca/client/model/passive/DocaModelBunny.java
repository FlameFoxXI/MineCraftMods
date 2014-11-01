package mods.doca.client.model.passive;

import cpw.mods.fml.relauncher.*;
import mods.doca.entity.passive.*;
import net.minecraft.client.model.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class DocaModelBunny extends ModelBase
{
	public ModelRenderer bunnyHeadMain;
	public ModelRenderer bunnyBody;
	public ModelRenderer bunnyLeg1;
	public ModelRenderer bunnyLeg2;
	public ModelRenderer bunnyLeg3;
	public ModelRenderer bunnyLeg4;
	ModelRenderer bunnyTail;
	ModelRenderer bunnyMane;

	public DocaModelBunny()
	{
		this.bunnyHeadMain = new ModelRenderer(this, 0, 0);
		this.bunnyHeadMain.addBox(-3F, -1F, -2F, 6, 4, 4);
		this.bunnyHeadMain.setRotationPoint(-1F, 18.5F, -7F);
		this.bunnyBody = new ModelRenderer(this, 18, 14);
		this.bunnyBody.addBox(-4F, -2F, -3F, 6, 6, 4);
		this.bunnyBody.setRotationPoint(0F, 19F, 0F);
		this.bunnyMane = new ModelRenderer(this, 21, 0);
		this.bunnyMane.addBox(-4F, -2F, -3F, 6, 5, 5);
		this.bunnyMane.setRotationPoint(0F, 19F, -3F);
		this.bunnyLeg1 = new ModelRenderer(this, 0, 13);
		this.bunnyLeg1.addBox(-1F, 0F, -1F, 2, 2, 2);
		this.bunnyLeg1.setRotationPoint(-2.5F, 22F, 3F);
		this.bunnyLeg2 = new ModelRenderer(this, 0, 13);
		this.bunnyLeg2.addBox(-1F, 0F, -1F, 2, 2, 2);
		this.bunnyLeg2.setRotationPoint(0.5F, 22F, 3F);
		this.bunnyLeg3 = new ModelRenderer(this, 0, 18);
		this.bunnyLeg3.addBox(-1F, 0F, -1F, 2, 2, 2);
		this.bunnyLeg3.setRotationPoint(-2.5F, 22F, -4F);
		this.bunnyLeg4 = new ModelRenderer(this, 0, 18);
		this.bunnyLeg4.addBox(-1F, 0F, -1F, 2, 2, 2);
		this.bunnyLeg4.setRotationPoint(0.5F, 22F, -4F);
		this.bunnyTail = new ModelRenderer(this, 9, 21);
		this.bunnyTail.addBox(-1F, 0F, -1F, 2, 3, 2);
		this.bunnyTail.setRotationPoint(-1F, 19F, 3F);
		this.bunnyHeadMain.setTextureOffset(0, 9).addBox(-1.5F, 2F, -3F, 3, 1, 1); //Nose
		this.bunnyHeadMain.setTextureOffset(9, 9).addBox(-2.2F, -5F, 0F, 2, 4, 1);  //ear1
		this.bunnyHeadMain.setTextureOffset(9, 15).addBox(0.2F, -5F, 0F, 2, 4, 1);  //ear2
	}

	@Override
	public void render(Entity par1Entity, float par2, float par3, float par4, float par5, float par6, float par7)
	{
		super.render(par1Entity, par2, par3, par4, par5, par6, par7);
		this.setRotationAngles(par2, par3, par4, par5, par6, par7, par1Entity);
		DocaEntityBunny var3 = (DocaEntityBunny)par1Entity;

		if (this.isChild)
		{
			float var8 = 2.0F;
			GL11.glPushMatrix();
			GL11.glTranslatef(0.0F, 1.5F * par7, 3.0F * par7);
			this.bunnyHeadMain.renderWithRotation(par7);
			GL11.glPopMatrix();
			GL11.glPushMatrix();
			GL11.glScalef(1.0F / var8, 1.0F / var8, 1.0F / var8);
			GL11.glTranslatef(-0.075F, 24.0F * par7, 0.0F);
			this.bunnyBody.render(par7);
			this.bunnyLeg1.render(par7);
			this.bunnyLeg2.render(par7);
			this.bunnyLeg3.render(par7);
			this.bunnyLeg4.render(par7);
			this.bunnyTail.renderWithRotation(par7);
			this.bunnyMane.render(par7);
			GL11.glPopMatrix();
		}
		else if (var3.getModelSize() == 1)
		{
			this.bunnyHeadMain.renderWithRotation(par7);
			this.bunnyBody.render(par7);
			this.bunnyLeg1.render(par7);
			this.bunnyLeg2.render(par7);
			this.bunnyLeg3.render(par7);
			this.bunnyLeg4.render(par7);
			this.bunnyTail.renderWithRotation(par7);
			this.bunnyMane.render(par7);
		}
		else if (var3.getModelSize() == 2)
		{
			float var8 = 1.7F;
			GL11.glPushMatrix();
			GL11.glScalef(1.0F / var8, 1.0F / var8, 1.0F / var8);
			GL11.glTranslatef(0.0F, 17.0F * par7, 0.0F);
			this.bunnyHeadMain.renderWithRotation(par7);
			this.bunnyBody.render(par7);
			this.bunnyLeg1.render(par7);
			this.bunnyLeg2.render(par7);
			this.bunnyLeg3.render(par7);
			this.bunnyLeg4.render(par7);
			this.bunnyTail.renderWithRotation(par7);
			this.bunnyMane.render(par7);
			GL11.glPopMatrix();
		}
		else
		{
			float var8 = 1.3F;
			GL11.glPushMatrix();
			GL11.glScalef(1.0F / var8, 1.0F / var8, 1.0F / var8);
			GL11.glTranslatef(0.0F, 7.0F * par7, 0.0F);
			this.bunnyHeadMain.renderWithRotation(par7);
			this.bunnyBody.render(par7);
			this.bunnyLeg1.render(par7);
			this.bunnyLeg2.render(par7);
			this.bunnyLeg3.render(par7);
			this.bunnyLeg4.render(par7);
			this.bunnyTail.renderWithRotation(par7);
			this.bunnyMane.render(par7);
			GL11.glPopMatrix();
		}
	}

	@Override
	public void setLivingAnimations(EntityLivingBase par1EntityLivingBase, float par2, float par3, float par4)
	{
		DocaEntityBunny var5 = (DocaEntityBunny)par1EntityLivingBase;

		if (var5.isSitting())
		{
			this.bunnyBody.setRotationPoint(0F, 19.5F, -0.5F);
			this.bunnyBody.rotateAngleX = ((float)Math.PI / 3.2F);
			this.bunnyMane.setRotationPoint(0F, 19F, -4F);
			this.bunnyMane.rotateAngleX = ((float)Math.PI / 2.2F);
			this.bunnyMane.rotateAngleY = 0.0F;
			this.bunnyTail.setRotationPoint(-1F, 21.5F, 2F);
			this.bunnyLeg1.setRotationPoint(-2.5F, 23F, 0.7F);
			this.bunnyLeg1.rotateAngleX = ((float)Math.PI * 3F / 2F);
			this.bunnyLeg2.setRotationPoint(0.5F, 23F, 0.7F);
			this.bunnyLeg2.rotateAngleX = ((float)Math.PI * 3F / 2F);
			this.bunnyLeg3.setRotationPoint(-2.5F, 22F, -4F);
			this.bunnyLeg3.rotateAngleX = 0.0F;
			this.bunnyLeg4.setRotationPoint(0.5F, 22F, -4F);
			this.bunnyLeg4.rotateAngleX = 0.0F;
		}
		else
		{
			this.bunnyBody.setRotationPoint(0F, 18.5F, 0F);
			this.bunnyBody.rotateAngleX = ((float)Math.PI / 2.2F);
			this.bunnyMane.setRotationPoint(0F, 19F, -4F);
			this.bunnyMane.rotateAngleX = ((float)Math.PI / 2.1F);
			this.bunnyLeg1.setRotationPoint(-2.5F, 22F, 3F);
			this.bunnyLeg2.setRotationPoint(0.5F, 22F, 3F);
			this.bunnyLeg3.setRotationPoint(-2.5F, 22F, -4F);
			this.bunnyLeg4.setRotationPoint(0.5F, 22F, -4F);
			this.bunnyTail.setRotationPoint(-1F, 19F, 3F);
			this.bunnyLeg1.rotateAngleX = MathHelper.cos(par2 * 0.6662F + (float)Math.PI) * 1.4F * par3;
			this.bunnyLeg2.rotateAngleX = MathHelper.cos(par2 * 0.6662F + (float)Math.PI) * 1.4F * par3;
			this.bunnyLeg3.rotateAngleX = MathHelper.cos(par2 * 0.6662F) * 1.4F * par3;
			this.bunnyLeg4.rotateAngleX = MathHelper.cos(par2 * 0.6662F) * 1.4F * par3;
		}

		this.bunnyHeadMain.rotateAngleZ = var5.getInterestedAngle(par4) + var5.getShakeAngle(par4, 0.0F);
		this.bunnyMane.rotateAngleZ = var5.getShakeAngle(par4, -0.08F);
		this.bunnyBody.rotateAngleZ = var5.getShakeAngle(par4, -0.16F);
		this.bunnyTail.rotateAngleZ = var5.getShakeAngle(par4, -0.2F);
	}

	@Override
	public void setRotationAngles(float par1, float par2, float par3, float par4, float par5, float par6, Entity par7Entity)
	{
		super.setRotationAngles(par1, par2, par3, par4, par5, par6, par7Entity);
		this.bunnyHeadMain.rotateAngleX = par5 / (180F / (float)Math.PI);
		this.bunnyHeadMain.rotateAngleY = par4 / (180F / (float)Math.PI);
		this.bunnyTail.rotateAngleX = par3;
	}
}