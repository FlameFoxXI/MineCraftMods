package mods.doca.client.model.passive;

import cpw.mods.fml.relauncher.*;
import mods.doca.entity.passive.*;
import net.minecraft.client.model.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class DocaModelDoggy extends ModelBase
{
	public ModelRenderer wolfHeadMain;
	public ModelRenderer wolfBody;
	public ModelRenderer wolfLeg1;
	public ModelRenderer wolfLeg2;
	public ModelRenderer wolfLeg3;
	public ModelRenderer wolfLeg4;
	ModelRenderer wolfTail;
	ModelRenderer wolfMane;

	public DocaModelDoggy()
	{
		/**        float var1 = 0.0F;
        float var2 = 13.5F;
        this.wolfHeadMain = new ModelRenderer(this, 0, 0);
        this.wolfHeadMain.addBox(-3.0F, -3.0F, -2.0F, 6, 6, 4);
        this.wolfHeadMain.setRotationPoint(-1.0F, 13.5F, -7.0F);
        this.wolfBody = new ModelRenderer(this, 18, 14);
        this.wolfBody.addBox(-4.0F, -2.0F, -3.0F, 6, 9, 6);
        this.wolfBody.setRotationPoint(0.0F, 14.0F, 2.0F);
        this.wolfMane = new ModelRenderer(this, 21, 0);
        this.wolfMane.addBox(-4.0F, -3.0F, -3.0F, 8, 6, 7);
        this.wolfMane.setRotationPoint(-1.0F, 14.0F, 2.0F);
        this.wolfLeg1 = new ModelRenderer(this, 0, 18);
        this.wolfLeg1.addBox(-1.0F, 0.0F, -1.0F, 2, 8, 2);
        this.wolfLeg1.setRotationPoint(-2.5F, 16.0F, 7.0F);
        this.wolfLeg2 = new ModelRenderer(this, 0, 18);
        this.wolfLeg2.addBox(-1.0F, 0.0F, -1.0F, 2, 8, 2);
        this.wolfLeg2.setRotationPoint(0.5F, 16.0F, 7.0F);
        this.wolfLeg3 = new ModelRenderer(this, 0, 18);
        this.wolfLeg3.addBox(-1.0F, 0.0F, -1.0F, 2, 8, 2);
        this.wolfLeg3.setRotationPoint(-2.5F, 16.0F, -4.0F);
        this.wolfLeg4 = new ModelRenderer(this, 0, 18);
        this.wolfLeg4.addBox(-1.0F, 0.0F, -1.0F, 2, 8, 2);
        this.wolfLeg4.setRotationPoint(0.5F, 16.0F, -4.0F);
        this.wolfTail = new ModelRenderer(this, 9, 18);
        this.wolfTail.addBox(-1.0F, 0.0F, -1.0F, 2, 8, 2);
        this.wolfTail.setRotationPoint(-1.0F, 12.0F, 8.0F);
		**/
		wolfHeadMain = new ModelRenderer(this, 0, 0);
		wolfHeadMain.addBox(-3F, -3F, -2F, 6, 6, 4);
		wolfHeadMain.setRotationPoint(-1F, 13.5F, -7F);
		wolfHeadMain.setTextureSize(64, 32);
		wolfHeadMain.mirror = true;
		setRotation(wolfHeadMain, 0F, 0F, 0F);
		wolfBody = new ModelRenderer(this, 18, 14);
		wolfBody.addBox(-4F, -2F, -3F, 6, 9, 6);
		wolfBody.setRotationPoint(0F, 14F, 2F);
		wolfBody.setTextureSize(64, 32);
		wolfBody.mirror = true;
		setRotation(wolfBody, 1.570796F, 0F, 0F);
		wolfMane = new ModelRenderer(this, 21, 0);
		wolfMane.addBox(-4F, -3F, -3F, 8, 6, 7);
		wolfMane.setRotationPoint(-1F, 14F, -3F);
		wolfMane.setTextureSize(64, 32);
		wolfMane.mirror = true;
		setRotation(wolfMane, 1.570796F, 0F, 0F);
		wolfLeg1 = new ModelRenderer(this, 0, 18);
		wolfLeg1.addBox(-1F, 0F, -1F, 2, 8, 2);
		wolfLeg1.setRotationPoint(-2.5F, 16F, 7F);
		wolfLeg1.setTextureSize(64, 32);
		wolfLeg1.mirror = true;
		setRotation(wolfLeg1, 0F, 0F, 0F);
		wolfLeg2 = new ModelRenderer(this, 0, 18);
		wolfLeg2.addBox(-1F, 0F, -1F, 2, 8, 2);
		wolfLeg2.setRotationPoint(0.5F, 16F, 7F);
		wolfLeg2.setTextureSize(64, 32);
		wolfLeg2.mirror = true;
		setRotation(wolfLeg2, 0F, 0F, 0F);
		wolfLeg3 = new ModelRenderer(this, 0, 18);
		wolfLeg3.addBox(-1F, 0F, -1F, 2, 8, 2);
		wolfLeg3.setRotationPoint(-2.5F, 16F, -4F);
		wolfLeg3.setTextureSize(64, 32);
		wolfLeg3.mirror = true;
		setRotation(wolfLeg3, 0F, 0F, 0F);
		wolfLeg4 = new ModelRenderer(this, 0, 18);
		wolfLeg4.addBox(-1F, 0F, -1F, 2, 8, 2);
		wolfLeg4.setRotationPoint(0.5F, 16F, -4F);
		wolfLeg4.setTextureSize(64, 32);
		wolfLeg4.mirror = true;
		setRotation(wolfLeg4, 0F, 0F, 0F);
		wolfTail = new ModelRenderer(this, 9, 18);
		wolfTail.addBox(-1F, 0F, -1F, 2, 8, 2);
		wolfTail.setRotationPoint(-1F, 12F, 8F);
		wolfTail.setTextureSize(64, 32);
		this.wolfHeadMain.setTextureOffset(16, 14).addBox(-3.0F, -5.0F, 0.0F, 2, 2, 1);
		this.wolfHeadMain.setTextureOffset(16, 14).addBox(1.0F, -5.0F, 0.0F, 2, 2, 1);
		this.wolfHeadMain.setTextureOffset(0, 10).addBox(-1.5F, 0F, -5F, 3, 3, 4);
		this.wolfHeadMain.setTextureOffset(43, 14).addBox(-4.0F, -2.5F, -1.0F, 1, 4, 2);
		this.wolfHeadMain.setTextureOffset(43, 20).addBox(3.0F, -2.5F, -1.0F, 1, 4, 2);
	}

	@Override
	public void render(Entity par1Entity, float par2, float par3, float par4, float par5, float par6, float par7)
	{
		super.render(par1Entity, par2, par3, par4, par5, par6, par7);
		this.setRotationAngles(par2, par3, par4, par5, par6, par7, par1Entity);
		DocaEntityDoggy var3 = (DocaEntityDoggy)par1Entity;

		if (this.isChild)
		{
			float var8 = 2.0F;
			GL11.glPushMatrix();
			GL11.glTranslatef(0.025F, 0.3F, 0.16F);
			this.wolfHeadMain.renderWithRotation(par7);
			GL11.glPopMatrix();
			GL11.glPushMatrix();
			GL11.glScalef(1.0F / var8, 1.0F / var8, 1.0F / var8);
			GL11.glTranslatef(0.0F, 1.5F, 0.0F);
			this.wolfBody.render(par7);
			this.wolfLeg1.render(par7);
			this.wolfLeg2.render(par7);
			this.wolfLeg3.render(par7);
			this.wolfLeg4.render(par7);
			this.wolfTail.renderWithRotation(par7);
			this.wolfMane.render(par7);
			GL11.glPopMatrix();
		}
		else if (var3.getModelSize() == 1)
		{
			float var8 = 1.3F;
			GL11.glPushMatrix();
			GL11.glScalef(1.0F * var8, 1.0F * var8, 1.0F * var8);
			GL11.glTranslatef(0.0F, -5.5F * par7, 0.0F);
			this.wolfHeadMain.renderWithRotation(par7);
			this.wolfBody.render(par7);
			this.wolfLeg1.render(par7);
			this.wolfLeg2.render(par7);
			this.wolfLeg3.render(par7);
			this.wolfLeg4.render(par7);
			this.wolfTail.renderWithRotation(par7);
			this.wolfMane.render(par7);
			GL11.glPopMatrix();
		}
		else if (var3.getModelSize() == 2)
		{
			float var8 = 1.7F;
			GL11.glPushMatrix();
			GL11.glScalef(1.0F / var8, 1.0F / var8, 1.0F / var8);
			GL11.glTranslatef(0.0F, 17.0F * par7, 0.0F);
			this.wolfHeadMain.renderWithRotation(par7);
			this.wolfBody.render(par7);
			this.wolfLeg1.render(par7);
			this.wolfLeg2.render(par7);
			this.wolfLeg3.render(par7);
			this.wolfLeg4.render(par7);
			this.wolfTail.renderWithRotation(par7);
			this.wolfMane.render(par7);
			GL11.glPopMatrix();
		}
		else
		{
			this.wolfHeadMain.renderWithRotation(par7);
			this.wolfBody.render(par7);
			this.wolfLeg1.render(par7);
			this.wolfLeg2.render(par7);
			this.wolfLeg3.render(par7);
			this.wolfLeg4.render(par7);
			this.wolfTail.renderWithRotation(par7);
			this.wolfMane.render(par7);
		}
	}

	@Override
	public void setLivingAnimations(EntityLivingBase par1EntityLivingBase, float par2, float par3, float par4)
	{
		DocaEntityDoggy var5 = (DocaEntityDoggy)par1EntityLivingBase;

		if (var5.isAngry())
		{
			this.wolfTail.rotateAngleY = 0.0F;
		}
		else
		{
			this.wolfTail.rotateAngleY = MathHelper.cos(par2 * 0.6662F) * 1.4F * par3;
		}

		if (var5.isDowning() || var5.isSleeping())
		{
			this.wolfBody.setRotationPoint(0.0F, 17.5F, 2.0F);
			this.wolfBody.rotateAngleX = ((float)Math.PI / 2.25F);
			this.wolfMane.setRotationPoint(-1.0F, 17.0F, -3.0F);
			this.wolfMane.rotateAngleX = this.wolfBody.rotateAngleX;
			this.wolfTail.setRotationPoint(-1.0F, 17F, 8.0F);
			this.wolfLeg1.setRotationPoint(-3F, 20.5F, 7.5F);
			this.wolfLeg2.setRotationPoint(1F, 20.5F, 7.5F);
			this.wolfLeg3.setRotationPoint(-3.5F, 20.0F, -4.0F);
			this.wolfLeg4.setRotationPoint(1.5F, 20.0F, -4.0F);
			this.wolfLeg1.rotateAngleX = -83.0F;
			this.wolfLeg1.rotateAngleY =  0.25F;
			this.wolfLeg2.rotateAngleX = -83.0F;
			this.wolfLeg2.rotateAngleY =  -0.25F;
			this.wolfLeg3.rotateAngleX = -83.0F;	//lestfrontFront
			this.wolfLeg3.rotateAngleY =  0.25F;
			this.wolfLeg4.rotateAngleX = -83.0F;
			this.wolfLeg4.rotateAngleY =  -0.25F;	//RigtFront
		}
		else if (var5.isSitting())
		{
			this.wolfMane.setRotationPoint(-1.0F, 16.0F, -3.0F);
			this.wolfMane.rotateAngleX = ((float)Math.PI * 2F / 5F);
			this.wolfMane.rotateAngleY = 0.0F;
			this.wolfBody.setRotationPoint(0.0F, 18.0F, 0.0F);
			this.wolfBody.rotateAngleX = ((float)Math.PI / 4F);
			this.wolfTail.setRotationPoint(-1.0F, 21.0F, 6.0F);
			this.wolfLeg1.setRotationPoint(-2.5F, 22.0F, 2.0F);
			this.wolfLeg1.rotateAngleX = ((float)Math.PI * 3F / 2F);
			this.wolfLeg2.setRotationPoint(0.5F, 22.0F, 2.0F);
			this.wolfLeg2.rotateAngleX = ((float)Math.PI * 3F / 2F);
			this.wolfLeg3.rotateAngleX = 5.811947F;
			this.wolfLeg3.setRotationPoint(-2.49F, 17.0F, -4.0F);
			this.wolfLeg4.rotateAngleX = 5.811947F;
			this.wolfLeg4.setRotationPoint(0.51F, 17.0F, -4.0F);
			this.wolfLeg1.rotateAngleY =  0.0F;
			this.wolfLeg2.rotateAngleY =  0.0F;
			this.wolfLeg3.rotateAngleY =  0.0F;
			this.wolfLeg4.rotateAngleY =  0.0F;	//RigtFront
		}
		else
		{
			this.wolfBody.setRotationPoint(0.0F, 14.0F, 2.0F);
			this.wolfBody.rotateAngleX = ((float)Math.PI / 2F);
			this.wolfMane.setRotationPoint(-1.0F, 14.0F, -3.0F);
			this.wolfMane.rotateAngleX = this.wolfBody.rotateAngleX;
			this.wolfTail.setRotationPoint(-1.0F, 12.0F, 8.0F);
			this.wolfLeg1.setRotationPoint(-2.5F, 16.0F, 7.0F);
			this.wolfLeg2.setRotationPoint(0.5F, 16.0F, 7.0F);
			this.wolfLeg3.setRotationPoint(-2.5F, 16.0F, -4.0F);
			this.wolfLeg4.setRotationPoint(0.5F, 16.0F, -4.0F);
			this.wolfLeg1.rotateAngleX = MathHelper.cos(par2 * 0.6662F) * 1.4F * par3;
			this.wolfLeg2.rotateAngleX = MathHelper.cos(par2 * 0.6662F + (float)Math.PI) * 1.4F * par3;
			this.wolfLeg3.rotateAngleX = MathHelper.cos(par2 * 0.6662F + (float)Math.PI) * 1.4F * par3;
			this.wolfLeg4.rotateAngleX = MathHelper.cos(par2 * 0.6662F) * 1.4F * par3;
			this.wolfLeg1.rotateAngleY =  0.0F;
			this.wolfLeg2.rotateAngleY =  0.0F;
			this.wolfLeg3.rotateAngleY =  0.0F;
			this.wolfLeg4.rotateAngleY =  0.0F;	//RigtFront
		}

		if (var5.isDowning() || var5.isSleeping())
		{
			if (var5.isSleeping())
			{
				this.wolfHeadMain.setRotationPoint(-1.0F, 16.5F, -6.5F);
				this.wolfHeadMain.rotateAngleX = 0.15F;
				this.wolfHeadMain.rotateAngleY = -0.5F;
				this.wolfHeadMain.rotateAngleX = 0.25F;
			}
			else if (var5.isHowling())
			{
				this.wolfHeadMain.setRotationPoint(-1.0F, 15.0F, -6.5F);
				this.wolfHeadMain.rotateAngleX = -0.8F;
				this.wolfHeadMain.rotateAngleY = 0F;
				this.wolfHeadMain.rotateAngleZ = 0F;
			}
			else
			{
				this.wolfHeadMain.setRotationPoint(-1.0F, 16.0F, -7.0F);
				this.wolfHeadMain.rotateAngleX = 0F;
				this.wolfHeadMain.rotateAngleY = 0F;
				this.wolfHeadMain.rotateAngleZ = 0F;
			}
		}
		else
		{
			if (var5.isHowling())
			{
				this.wolfHeadMain.setRotationPoint(-1.0F, 12.5F, -6.5F);
				this.wolfHeadMain.rotateAngleX = -0.8F;
				this.wolfHeadMain.rotateAngleY = 0F;
				this.wolfHeadMain.rotateAngleZ = 0F;
			}
			else
			{
				this.wolfHeadMain.setRotationPoint(-1.0F, 13.5F, -7.0F);
				this.wolfHeadMain.rotateAngleX = 0F;
				this.wolfHeadMain.rotateAngleY = 0F;
				this.wolfHeadMain.rotateAngleZ = 0F;
			}
		}

		this.wolfHeadMain.rotateAngleZ = var5.getInterestedAngle(par4) + var5.getShakeAngle(par4, 0.0F);
		this.wolfMane.rotateAngleZ = var5.getShakeAngle(par4, -0.08F);
		this.wolfBody.rotateAngleZ = var5.getShakeAngle(par4, -0.16F);
		this.wolfTail.rotateAngleZ = var5.getShakeAngle(par4, -0.2F);
	}

	@Override
	public void setRotationAngles(float par1, float par2, float par3, float par4, float par5, float par6, Entity par7Entity)
	{
		super.setRotationAngles(par1, par2, par3, par4, par5, par6, par7Entity);
		DocaEntityDoggy var5 = (DocaEntityDoggy)par7Entity;

		if (var5.isHowling() || var5.isSleeping())
		{
			;
		}
		else
		{
			this.wolfHeadMain.rotateAngleX = par5 / (180F / (float)Math.PI);
			this.wolfHeadMain.rotateAngleY = par4 / (180F / (float)Math.PI);
		}

		this.wolfTail.rotateAngleX = par3;
	}

	private void setRotation(ModelRenderer model, float x, float y, float z)
	{
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}
}
