package mods.doca.client.render.entity.biped;

import cpw.mods.fml.relauncher.*;

import mods.doca.*;
import mods.doca.client.model.*;
import mods.doca.client.model.biped.*;
import mods.doca.client.render.entity.*;
import mods.doca.core.*;
import mods.doca.entity.*;

import net.minecraft.block.*;
import net.minecraft.client.entity.*;
import net.minecraft.client.model.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.entity.*;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;

import java.util.*;
import org.lwjgl.opengl.*;

@SideOnly(Side.CLIENT)
public class DocaRenderEnderman extends DocaRenderBase
{
	protected ModelBiped modelBipedMain;

	public DocaRenderEnderman()
	{
		this(new DocaModelEnderman(), new DocaModelEnderman(), 0.5F);
	}

	public DocaRenderEnderman(ModelBiped par1ModelBiped, ModelBiped par2ModelBiped, float par2)
	{
		super(par1ModelBiped, par2);
		this.setRenderPassModel(par2ModelBiped);
		this.modelBipedMain = par1ModelBiped;
		this.stringSelector = DocaSet.PET_ENDER;
	}

	@Override
	public void doRenderLiving(EntityLiving par1EntityLiving, double par2, double par4, double par6, float par8, float par9)
	{
		float var10 = 1.0F;
		GL11.glColor3f(var10, var10, var10);
		ItemStack var11 = par1EntityLiving.getHeldItem();
		this.func_82420_a(par1EntityLiving, var11);
		double var12 = par4 - (double)par1EntityLiving.yOffset;

		if (par1EntityLiving.isSneaking() && !((EntityLivingBase)par1EntityLiving instanceof EntityPlayerSP))
		{
			var12 -= 0.125D;
		}

		super.doRenderLiving(par1EntityLiving, par2, var12, par6, par8, par9);
		this.modelBipedMain.aimedBow = false;
		this.modelBipedMain.isSneak = false;
		this.modelBipedMain.heldItemRight = 0;
	}

	protected void func_82420_a(EntityLiving par1EntityLiving, ItemStack par2ItemStack)
	{
		this.modelBipedMain.heldItemRight = par2ItemStack != null ? 1 : 0;
		this.modelBipedMain.isSneak = par1EntityLiving.isSneaking();
	}

	protected int renderDocaEnderman(DocaEntityBase par1DocaEntity, int par2, float par3)
	{
		float f1;

		if (par2 == 0 && par1DocaEntity.getShaking())
		{
			f1 = par1DocaEntity.getBrightness(par3) * par1DocaEntity.getShadingWhileShaking(par3);
			this.bindTexture(this.getEntityTexture(par1DocaEntity));
			GL11.glColor3f(f1, f1, f1);
			return 1;
		}
		else if (par2 == 1 && par1DocaEntity.isTamed())
		{
			this.bindTexture(DocaRenderInit.getRenderTable(DocaReg.getNameToType(stringSelector), par1DocaEntity.getIndexTexture(), "collar"));
			f1 = 2.0F;
			GL11.glDisable(GL11.GL_LIGHTING);
			int i = par1DocaEntity.getCollarColor();
			char c0 = 61680;
			int j = c0 % 65536;
			int k = c0 / 65536;
			OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)j / 1.0F, (float)k / 1.0F);
			GL11.glColor4f(f1 * EntitySheep.fleeceColorTable[i][0], f1 * EntitySheep.fleeceColorTable[i][1], f1 * EntitySheep.fleeceColorTable[i][2], 1.0F);
			GL11.glEnable(GL11.GL_LIGHTING);
			return 1;
		}
		else
		{
			return -1;
		}
	}

	protected void renderCarrying(DocaEntityBase par1DocaEntity, float par2)
	{
		super.renderEquippedItems(par1DocaEntity, par2);

		if (par1DocaEntity.getCarried() > 0)
		{
			float f1 = 0.5F;
			float f2 = 0.0F;
			float f3 = 0.6875F;
			float f4 = -0.75F;

			if (par1DocaEntity.isChild() || par1DocaEntity.getModelSize() == 2)
			{
				f1 = 0.25F;
				f3 = 1.025F;
				f4 = -0.475F;
			}
			else if (par1DocaEntity.getModelSize() == 1)
			{
				f1 = 0.65F;
				f3 = 0.375F;
				f4 = -1.0F;
			}

			GL11.glEnable(GL12.GL_RESCALE_NORMAL);
			GL11.glPushMatrix();
			GL11.glTranslatef(f2, f3, f4);
			f1 *= 1.0F;
			GL11.glRotatef(20.0F, 1.0F, 0.0F, 0.0F);
			GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
			GL11.glScalef(-f1, -f1, f1);
			int i = par1DocaEntity.getBrightnessForRender(par2);
			int j = i % 65536;
			int k = i / 65536;
			OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)j / 1.0F, (float)k / 1.0F);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			this.bindTexture(TextureMap.locationBlocksTexture);
			this.renderBlocks.renderBlockAsItem(Block.blocksList[par1DocaEntity.getCarried()], par1DocaEntity.getCarryingData(), 1.0F);
			GL11.glPopMatrix();
			GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		}
	}

	@Override
	protected int shouldRenderPass(EntityLivingBase par1EntityLivingBase, int par2, float par3)
	{
		return this.renderDocaEnderman((DocaEntityBase)par1EntityLivingBase, par2, par3);
	}

	@Override
	protected void renderEquippedItems(EntityLivingBase par1EntityLivingBase, float par2)
	{
		this.renderCarrying((DocaEntityBase)par1EntityLivingBase, par2);
	}
}
