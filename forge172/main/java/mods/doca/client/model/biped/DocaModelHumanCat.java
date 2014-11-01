package mods.doca.client.model.biped;

import mods.doca.client.model.*;
import mods.doca.entity.*;
import net.minecraft.client.model.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;
import org.lwjgl.opengl.GL11;
import cpw.mods.fml.relauncher.*;

@SideOnly(Side.CLIENT)
public class DocaModelHumanCat extends DocaModelBiped
{
	public ModelRenderer bipedTail;

	public DocaModelHumanCat()
	{
		super(0.0F, 10.0F, 64, 32);

		float f = 8.0F;

		this.bipedHead = new ModelRenderer(this, 0, 0);
		this.bipedHead.addBox(-6.5F, -8.0F, -3.0F, 13, 8, 6, 0.0F);
		this.bipedHead.setRotationPoint(0.0F, f, 0.0F);
		this.bipedHeadwear = new ModelRenderer(this, 25, 16);
		this.bipedHeadwear.addBox(-6.5F, -8.0F, -3.0F, 13, 8, 6, 0.5F);
		this.bipedHeadwear.setRotationPoint(0.0F, -10.0F, 0.0F);
		this.bipedBody = new ModelRenderer(this, 38, 0);
		this.bipedBody.addBox(-3.0F, 0.0F, -1.0F, 6, 10, 2, 0.0F);
		this.bipedBody.setRotationPoint(0.0F, f, 0.1F);
		this.bipedRightArm = new ModelRenderer(this, 9, 16);
		this.bipedRightArm.addBox(-1.0F, -2.0F, -1.0F, 2, 10, 2, 0.0F);
		this.bipedRightArm.setRotationPoint(-3.5F, f + 1.0F, -1.0F);
		this.bipedLeftArm = new ModelRenderer(this, 9, 16);
		this.bipedLeftArm.mirror = true;
		this.bipedLeftArm.addBox(-1.0F, -2.0F, -1.0F, 2, 10, 2, 0.0F);
		this.bipedLeftArm.setRotationPoint(3.5F, f + 1.0F, -1.0F);
		this.bipedRightLeg = new ModelRenderer(this, 0, 16);
		this.bipedRightLeg.addBox(-1.0F, 0.0F, -1.0F, 2, 8, 2, 0.0F);
		this.bipedRightLeg.setRotationPoint(-1.8F, f + 10.0F, 0.0F);
		this.bipedLeftLeg = new ModelRenderer(this, 0, 16);
		this.bipedLeftLeg.mirror = true;
		this.bipedLeftLeg.addBox(-1.0F, 0.0F, -1.0F, 2, 8, 2, 0.0F);
		this.bipedLeftLeg.setRotationPoint(1.8F, f + 10.0F, 0.0F);
		this.setRotation(bipedLeftLeg, 0.0F, 0.0F, 0.0F);
		this.bipedTail = new ModelRenderer(this, 18, 16);
		this.bipedTail.addBox(-0.5F, 0.0F, -0.5F, 1, 8, 1, 0.0F);
		this.bipedTail.setRotationPoint(0.0F, f + 9.0F, 0F);
		this.setRotation(bipedTail, 1.012291F, 0.0F, 0.0F);
		this.bipedHead.setTextureOffset(54, 0).addBox(-6.0F, -11.0F, -1.5F, 4, 4, 1);
		this.bipedHead.setTextureOffset(54, 0).addBox(2.0F, -11.0F, -1.5F, 4, 4, 1);
	}

	@Override
	public void render(Entity par1Entity, float par2, float par3, float par4, float par5, float par6, float par7)
	{
		this.setRotationAngles(par2, par3, par4, par5, par6, par7, par1Entity);
		DocaEntityBiped var3 = (DocaEntityBiped)par1Entity;

		if (this.isChild || var3.getModelSize() == 2)
		{
			float var8 = 0.4F;
			GL11.glPushMatrix();
			GL11.glScalef(1.0F * var8, 1.0F * var8, 1.0F * var8);
			GL11.glTranslatef(0.0F, 35.0F * par7, 0.0F);
			this.bipedHead.render(par7);
			this.bipedHeadwear.render(par7);
			this.bipedBody.render(par7);
			this.bipedRightArm.render(par7);
			this.bipedLeftArm.render(par7);
			this.bipedRightLeg.render(par7);
			this.bipedLeftLeg.render(par7);
			this.bipedTail.render(par7);
			GL11.glPopMatrix();
		}
		else if (var3.getModelSize() == 1)
		{
			float var8 = 1.0F;
			GL11.glPushMatrix();
			GL11.glScalef(1.0F * var8, 1.0F * var8, 1.0F * var8);
			GL11.glTranslatef(0.0F, 0.0F * par7, 0.0F);
			this.bipedHead.render(par7);
			this.bipedHeadwear.render(par7);
			this.bipedBody.render(par7);
			this.bipedRightArm.render(par7);
			this.bipedLeftArm.render(par7);
			this.bipedRightLeg.render(par7);
			this.bipedLeftLeg.render(par7);
			this.bipedTail.render(par7);
			GL11.glPopMatrix();
		}
		else {
			float var8 = 0.7F;
			GL11.glPushMatrix();
			GL11.glScalef(1.0F * var8, 1.0F * var8, 1.0F * var8);
			GL11.glTranslatef(0.0F, 9.0F * par7, 0.0F);
			this.bipedHead.render(par7);
			this.bipedHeadwear.render(par7);
			this.bipedBody.render(par7);
			this.bipedRightArm.render(par7);
			this.bipedLeftArm.render(par7);
			this.bipedRightLeg.render(par7);
			this.bipedLeftLeg.render(par7);
			this.bipedTail.render(par7);
			this.bipedTail.render(par7);
			GL11.glPopMatrix();
		}

	}

	@Override
	public void setLivingAnimations(EntityLivingBase par1EntityLivingBase, float par2, float par3, float par4)
	{
		this.aimedBow = true;
		super.setLivingAnimations(par1EntityLivingBase, par2, par3, par4);

		DocaEntityBiped var5 = (DocaEntityBiped)par1EntityLivingBase;

		float rotateSpeed = 0.7F;
		if (var5.isAngry())
		{
			this.bipedTail.rotateAngleY = 0.0F;
		}
		else
		{
			this.bipedTail.rotateAngleY = MathHelper.cos(par2 * 0.6662F) * 1.4F * par3;
		}
		//		this.bipedRightArm.rotateAngleX = MathHelper.cos(par2 * 0.6662F) * rotateSpeed * par3;
		//		this.bipedLeftArm.rotateAngleX = MathHelper.cos(par2 * 0.6662F + (float)Math.PI) * rotateSpeed * par3;
		//		this.bipedRightLeg.rotateAngleX = MathHelper.cos(par2 * 0.6662F + (float)Math.PI) * rotateSpeed * par3;
		//		this.bipedLeftLeg.rotateAngleX = MathHelper.cos(par2 * 0.6662F) * rotateSpeed * par3;
	}

	public void setRotationAngles(float par1, float par2, float par3, float par4, float par5, float par6, Entity par7Entity)
	{
		super.setRotationAngles(par1, par2, par3, par4, par5, par6, par7Entity);
	}

	private void setRotation(ModelRenderer model, float x, float y, float z)
	{
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}
}
