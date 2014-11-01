package mods.doca.client.model.passive;

import cpw.mods.fml.relauncher.*;
import mods.doca.entity.passive.*;
import net.minecraft.client.model.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class DocaModelCactuar extends ModelBase
{
	ModelRenderer cactuarMain;
	ModelRenderer cactuarHand1;
	ModelRenderer cactuarHand2;
	ModelRenderer cactuarLeg1;
	ModelRenderer cactuarLeg2;

	public DocaModelCactuar()
	{
		this.cactuarMain = new ModelRenderer(this, 0, 0);
		this.cactuarMain.addBox(-2F, -5.5F, -2.5F, 4, 11, 5);
		this.cactuarMain.setRotationPoint(0F, 14F, 0F);
		this.cactuarMain.setTextureOffset(20, 0).addBox(0F, -7F, -2.5F, 0, 2, 5);
		this.cactuarHand1 = new ModelRenderer(this, 0, 17);
		this.cactuarHand1.addBox(-1F, -1F, 1F, 2, 2, 5);
		this.cactuarHand1.setRotationPoint(0F, 14F, 0F);
		this.cactuarHand1.mirror = true;
		this.cactuarHand1.setTextureOffset(0, 25).addBox(-1F, 1F, 4F, 2, 3, 2);
		this.cactuarHand2 = new ModelRenderer(this, 0, 17);
		this.cactuarHand2.addBox(-1F, -1F, -6F, 2, 2, 5);
		this.cactuarHand2.setRotationPoint(0F, 14F, 0F);
		this.cactuarHand2.mirror = true;
		this.cactuarHand2.setTextureOffset(0, 25).addBox(-1F, -4F, -6F, 2, 3, 2);
		this.cactuarLeg1 = new ModelRenderer(this, 20, 15);
		this.cactuarLeg1.addBox(-1F, 1F, 0F, 2, 5, 2);
		this.cactuarLeg1.setRotationPoint(0F, 17F, 1F);
		this.cactuarLeg1.mirror = true;
		this.cactuarLeg1.setTextureOffset(20, 23).addBox(-1F, 4F, 2F, 2, 2, 4);
		this.cactuarLeg2 = new ModelRenderer(this, 20, 15);
		this.cactuarLeg2.addBox(-1F, 1F, 0F, 2, 5, 2);
		this.cactuarLeg2.setRotationPoint(0F, 17F, 1F);
		this.cactuarLeg2.mirror = true;
		this.cactuarLeg2.setTextureOffset(20, 23).addBox(-1F, 4F, 2F, 2, 2, 4);
	}

	@Override
	public void render(Entity par1Entity, float par2, float par3, float par4, float par5, float par6, float par7)
	{
		super.render(par1Entity, par2, par3, par4, par5, par6, par7);
		this.setRotationAngles(par2, par3, par4, par5, par6, par7, par1Entity);
		DocaEntityCactuar var3 = (DocaEntityCactuar)par1Entity;

		if (this.isChild)
		{
			float var8 = 2.0F;
			GL11.glPushMatrix();
			GL11.glScalef(1.0F / var8, 1.0F / var8, 1.0F / var8);
			GL11.glTranslatef(0.0F, 24.0F * par7, 0.0F);
			this.cactuarMain.render(par7);
			this.cactuarHand1.render(par7);
			this.cactuarHand2.render(par7);
			this.cactuarLeg1.render(par7);
			this.cactuarLeg2.render(par7);
			GL11.glPopMatrix();
		}
		else if (var3.getModelSize() == 1)
		{
			float var8 = 1.3F;
			GL11.glPushMatrix();
			GL11.glScalef(1.0F * var8, 1.0F * var8, 1.0F * var8);
			GL11.glTranslatef(0.0F, -5.5F * par7, 0.0F);
			this.cactuarMain.render(par7);
			this.cactuarHand1.render(par7);
			this.cactuarHand2.render(par7);
			this.cactuarLeg1.render(par7);
			this.cactuarLeg2.render(par7);
			GL11.glPopMatrix();
		}
		else if (var3.getModelSize() == 2)
		{
			float var8 = 1.7F;
			GL11.glPushMatrix();
			GL11.glScalef(1.0F / var8, 1.0F / var8, 1.0F / var8);
			GL11.glTranslatef(0.0F, 17.0F * par7, 0.0F);
			this.cactuarMain.render(par7);
			this.cactuarHand1.render(par7);
			this.cactuarHand2.render(par7);
			this.cactuarLeg1.render(par7);
			this.cactuarLeg2.render(par7);
			GL11.glPopMatrix();
		}
		else
		{
			this.cactuarMain.render(par7);
			this.cactuarHand1.render(par7);
			this.cactuarHand2.render(par7);
			this.cactuarLeg1.render(par7);
			this.cactuarLeg2.render(par7);
		}
	}

	@Override
	public void setLivingAnimations(EntityLivingBase par1EntityLivingBase, float par2, float par3, float par4)
	{
		DocaEntityCactuar var5 = (DocaEntityCactuar)par1EntityLivingBase;

		if (var5.isSitting())
		{
			float ver6 = 3F;
			this.cactuarMain.setRotationPoint(0F, 14F + ver6 , 0F);
			this.cactuarHand1.setRotationPoint(0F, 14F + ver6, 0F);
			this.cactuarHand2.setRotationPoint(0F, 14F + ver6, 0F);
			this.cactuarLeg1.setRotationPoint(1.5F, 10.5F + ver6, 0F);
			this.cactuarLeg2.setRotationPoint(1.5F, 10.5F + ver6, 0F);
			//			this.cactuarLeg1.setRotationPoint(0F, 10.5F + ver6, 1.5F);
			//			this.cactuarLeg2.setRotationPoint(0F, 10.5F + ver6, 1.5F);
			this.cactuarMain.rotateAngleX = 0.0F;
			this.cactuarHand1.rotateAngleX = 0.0F;//-0.9599311F;
			this.cactuarHand2.rotateAngleX = 0.0F;//2.181662F;
			this.cactuarLeg1.rotateAngleX = -0.8726646F;
			this.cactuarLeg2.rotateAngleX = -0.8726646F;
			this.cactuarMain.rotateAngleY = ((float)Math.PI / 2F);
			this.cactuarHand1.rotateAngleY = ((float)Math.PI / 2F);
			this.cactuarHand2.rotateAngleY = ((float)Math.PI / 2F);
			this.cactuarLeg1.rotateAngleY = ((float)Math.PI / 2F);
			this.cactuarLeg2.rotateAngleY = ((float)Math.PI / 2F);
		}
		else
		{
			this.cactuarMain.setRotationPoint(0F, 14F, 0F);
			this.cactuarHand1.setRotationPoint(0F, 14F, 0F);
			this.cactuarHand2.setRotationPoint(0F, 14F, 0F);
			this.cactuarLeg1.setRotationPoint(0F, 17F, 1F);
			this.cactuarLeg2.setRotationPoint(0F, 17F, 1F);
			this.cactuarMain.rotateAngleX = 0.2617994F;
			this.cactuarHand1.rotateAngleX = 0.2617994F;
			this.cactuarHand2.rotateAngleX = 0.2617994F;
			this.cactuarLeg1.rotateAngleX = 0.2617994F;
			this.cactuarLeg2.rotateAngleX = -1.308997F;
			this.cactuarMain.rotateAngleY = 0.0F;
			this.cactuarHand1.rotateAngleY = 0.0F;
			this.cactuarHand2.rotateAngleY = 0.0F;
			this.cactuarLeg1.rotateAngleY = 0.0F;
			this.cactuarLeg2.rotateAngleY = 0.0F;
		}
	}

	@Override
	public void setRotationAngles(float par1, float par2, float par3, float par4, float par5, float par6, Entity par7Entity)
	{
		super.setRotationAngles(par1, par2, par3, par4, par5, par6, par7Entity);
	}
}