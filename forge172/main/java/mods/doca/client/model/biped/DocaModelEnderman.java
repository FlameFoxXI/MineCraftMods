package mods.doca.client.model.biped;

import mods.doca.client.model.*;
import mods.doca.entity.*;
import net.minecraft.client.model.*;
import net.minecraft.entity.*;
import net.minecraft.init.Blocks;
import net.minecraft.util.*;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.*;

@SideOnly(Side.CLIENT)
public class DocaModelEnderman extends DocaModelBiped
{
	public boolean isCarrying;
	public boolean isAttacking;

	public DocaModelEnderman()
	{
		super(0.0F, -14.0F, 64, 32);
		float f = -14.0F;
		float f1 = 0.0F;
		this.bipedHeadwear = new ModelRenderer(this, 0, 16);
		this.bipedHeadwear.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, f1 - 0.5F);
		this.bipedHeadwear.setRotationPoint(0.0F, 0.0F + f, 0.0F);
		this.bipedBody = new ModelRenderer(this, 32, 16);
		this.bipedBody.addBox(-4.0F, 0.0F, -2.0F, 8, 12, 4, f1);
		this.bipedBody.setRotationPoint(0.0F, 0.0F + f, 0.0F);
		this.bipedRightArm = new ModelRenderer(this, 56, 0);
		this.bipedRightArm.addBox(-1.0F, -2.0F, -1.0F, 2, 30, 2, f1);
		this.bipedRightArm.setRotationPoint(-5.0F, 2.0F + f, 0.0F);
		this.bipedLeftArm = new ModelRenderer(this, 56, 0);
		this.bipedLeftArm.mirror = true;
		this.bipedLeftArm.addBox(-1.0F, -2.0F, -1.0F, 2, 30, 2, f1);
		this.bipedLeftArm.setRotationPoint(5.0F, 2.0F + f, 0.0F);
		this.bipedRightLeg = new ModelRenderer(this, 56, 0);
		this.bipedRightLeg.addBox(-1.0F, 0.0F, -1.0F, 2, 30, 2, f1);
		this.bipedRightLeg.setRotationPoint(-2.0F, 12.0F + f, 0.0F);
		this.bipedLeftLeg = new ModelRenderer(this, 56, 0);
		this.bipedLeftLeg.mirror = true;
		this.bipedLeftLeg.addBox(-1.0F, 0.0F, -1.0F, 2, 30, 2, f1);
		this.bipedLeftLeg.setRotationPoint(2.0F, 12.0F + f, 0.0F);
	}

	@Override
	public void render(Entity par1Entity, float par2, float par3, float par4, float par5, float par6, float par7)
	{
		this.setRotationAngles(par2, par3, par4, par5, par6, par7, par1Entity);
		DocaEntityBase theBase = (DocaEntityBase)par1Entity;

		if (this.isChild)
		{
			float f6 = 2.0F;
			GL11.glPushMatrix();
			GL11.glTranslatef(0.0F, 14.0F * par7, 0.0F);
			this.bipedHead.render(par7);
			this.bipedHeadwear.render(par7);
			GL11.glPopMatrix();
			GL11.glPushMatrix();
			GL11.glScalef(1.0F / f6, 1.0F / f6, 1.0F / f6);
			GL11.glTranslatef(0.0F, 24.0F * par7, 0.0F);
			this.bipedBody.render(par7);
			this.bipedRightArm.render(par7);
			this.bipedLeftArm.render(par7);
			this.bipedRightLeg.render(par7);
			this.bipedLeftLeg.render(par7);
			GL11.glPopMatrix();
		}
		else if (theBase.getModelSize() == 1)
		{
			float f6 = 1.3F;
			GL11.glPushMatrix();
			GL11.glScalef(1.0F * f6, 1.0F * f6, 1.0F * f6);
			GL11.glTranslatef(0.0F, -5.5F * par7, 0.0F);
			this.bipedHead.render(par7);
			this.bipedBody.render(par7);
			this.bipedRightArm.render(par7);
			this.bipedLeftArm.render(par7);
			this.bipedRightLeg.render(par7);
			this.bipedLeftLeg.render(par7);
			this.bipedHeadwear.render(par7);
			GL11.glPopMatrix();
		}
		else if (theBase.getModelSize() == 2)
		{
			float var8 = 1.7F;
			GL11.glPushMatrix();
			GL11.glScalef(1.0F / var8, 1.0F / var8, 1.0F / var8);
			GL11.glTranslatef(0.0F, 17.0F * par7, 0.0F);
			this.bipedHead.render(par7);
			this.bipedBody.render(par7);
			this.bipedRightArm.render(par7);
			this.bipedLeftArm.render(par7);
			this.bipedRightLeg.render(par7);
			this.bipedLeftLeg.render(par7);
			this.bipedHeadwear.render(par7);
			GL11.glPopMatrix();
		}
		else
		{
			this.bipedHead.render(par7);
			this.bipedBody.render(par7);
			this.bipedRightArm.render(par7);
			this.bipedLeftArm.render(par7);
			this.bipedRightLeg.render(par7);
			this.bipedLeftLeg.render(par7);
			this.bipedHeadwear.render(par7);
		}
	}

	@Override
	public void setRotationAngles(float par1, float par2, float par3, float par4, float par5, float par6, Entity par7Entity)
	{
		super.setRotationAngles(par1, par2, par3, par4, par5, par6, par7Entity);
		DocaEntityBase var3 = (DocaEntityBase)par7Entity;
		this.bipedHead.showModel = true;
		float f6 = -14.0F;
		this.bipedBody.rotateAngleX = 0.0F;
		this.bipedBody.rotationPointY = f6;
		this.bipedBody.rotationPointZ = -0.0F;
		this.bipedRightLeg.rotateAngleX -= 0.0F;
		this.bipedLeftLeg.rotateAngleX -= 0.0F;
		this.bipedRightArm.rotateAngleX = (float)((double)this.bipedRightArm.rotateAngleX * 0.5D);
		this.bipedLeftArm.rotateAngleX = (float)((double)this.bipedLeftArm.rotateAngleX * 0.5D);
		this.bipedRightLeg.rotateAngleX = (float)((double)this.bipedRightLeg.rotateAngleX * 0.5D);
		this.bipedLeftLeg.rotateAngleX = (float)((double)this.bipedLeftLeg.rotateAngleX * 0.5D);
		float f7 = 0.4F;

		if (this.bipedRightArm.rotateAngleX > f7)
		{
			this.bipedRightArm.rotateAngleX = f7;
		}

		if (this.bipedLeftArm.rotateAngleX > f7)
		{
			this.bipedLeftArm.rotateAngleX = f7;
		}

		if (this.bipedRightArm.rotateAngleX < -f7)
		{
			this.bipedRightArm.rotateAngleX = -f7;
		}

		if (this.bipedLeftArm.rotateAngleX < -f7)
		{
			this.bipedLeftArm.rotateAngleX = -f7;
		}

		if (this.bipedRightLeg.rotateAngleX > f7)
		{
			this.bipedRightLeg.rotateAngleX = f7;
		}

		if (this.bipedLeftLeg.rotateAngleX > f7)
		{
			this.bipedLeftLeg.rotateAngleX = f7;
		}

		if (this.bipedRightLeg.rotateAngleX < -f7)
		{
			this.bipedRightLeg.rotateAngleX = -f7;
		}

		if (this.bipedLeftLeg.rotateAngleX < -f7)
		{
			this.bipedLeftLeg.rotateAngleX = -f7;
		}

		if (var3.getCarried() != Blocks.air)
		{
			this.bipedRightArm.rotateAngleX = -0.5F;
			this.bipedLeftArm.rotateAngleX = -0.5F;
			this.bipedRightArm.rotateAngleZ = 0.05F;
			this.bipedLeftArm.rotateAngleZ = -0.05F;
		}

		this.bipedRightArm.rotationPointZ = 0.0F;
		this.bipedLeftArm.rotationPointZ = 0.0F;
		this.bipedRightLeg.rotationPointZ = 0.0F;
		this.bipedLeftLeg.rotationPointZ = 0.0F;
		this.bipedRightLeg.rotationPointY = 9.0F + f6;
		this.bipedLeftLeg.rotationPointY = 9.0F + f6;

		if (this.isChild)
		{
			this.bipedHead.rotationPointZ = -0.0F;
			this.bipedHead.rotationPointY = f6 + 5.0F;
		}
		else
		{
			this.bipedHead.rotationPointZ = -0.0F;
			this.bipedHead.rotationPointY = f6 + 1.0F;
		}

		this.bipedHeadwear.rotationPointX = this.bipedHead.rotationPointX;
		this.bipedHeadwear.rotationPointY = this.bipedHead.rotationPointY;
		this.bipedHeadwear.rotationPointZ = this.bipedHead.rotationPointZ;
		this.bipedHeadwear.rotateAngleX = this.bipedHead.rotateAngleX;
		this.bipedHeadwear.rotateAngleY = this.bipedHead.rotateAngleY;
		this.bipedHeadwear.rotateAngleZ = this.bipedHead.rotateAngleZ;

		if (this.isAttacking)
		{
			float f8 = 1.0F;
			this.bipedHead.rotationPointY -= f8 * 5.0F;
		}
	}
}
