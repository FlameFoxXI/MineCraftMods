package mods.doca.client.render.entity;

import java.util.ArrayList;
import java.util.List;

import cpw.mods.fml.client.*;
import cpw.mods.fml.relauncher.*;

import mods.doca.*;
import mods.doca.core.*;
import mods.doca.entity.*;
import mods.doca.entity.func.*;

import net.minecraft.block.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.*;
import net.minecraft.client.model.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.entity.*;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.src.*;
import net.minecraft.util.*;
import net.minecraft.world.*;

import org.lwjgl.opengl.*;

@SideOnly(Side.CLIENT)
public class DocaRenderBase extends RenderLiving
{
	public String stringSelector = "";
	private static final ResourceLocation field_110420_t = new ResourceLocation(DocaSet.textureInfoGUI);

	public DocaRenderBase(ModelBase par1ModelBase, float par3)
	{
		super(par1ModelBase, par3);
	}

	protected int renderBaseMain(DocaEntityBase par1DocaEntity, int par2, float par3)
	{
		float var4 = 1.0F;

		if (par2 == 0 && par1DocaEntity.getShaking())
		{
			var4 = par1DocaEntity.getBrightness(par3) * par1DocaEntity.getShadingWhileShaking(par3);
			this.bindTexture(this.getEntityTexture(par1DocaEntity));
			GL11.glColor3f(var4, var4, var4);
			return 1;
		}
		else if (par2 == 1 && par1DocaEntity.isTamed())
		{
			this.bindTexture(DocaRenderInit.getRenderTable(DocaReg.getNameToType(stringSelector), par1DocaEntity.getIndexTexture(), "collar"));
			if (this.stringSelector.equalsIgnoreCase(DocaSet.PET_ENDER) || DocaSet.Debug)
			{
				var4 = 2.0F;
				GL11.glDisable(GL11.GL_LIGHTING);
				int var5 = par1DocaEntity.getCollarColor();
				char c0 = 61680;
				int j = c0 % 65536;
				int k = c0 / 65536;
				OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)j / 1.0F, (float)k / 1.0F);
				GL11.glColor4f(var4 * EntitySheep.fleeceColorTable[var5][0], var4 * EntitySheep.fleeceColorTable[var5][1], var4 * EntitySheep.fleeceColorTable[var5][2], 1.0F);
				GL11.glEnable(GL11.GL_LIGHTING);
			}
			else
			{
				int var5 = par1DocaEntity.getCollarColor();
				GL11.glColor3f(var4 * EntitySheep.fleeceColorTable[var5][0], var4 * EntitySheep.fleeceColorTable[var5][1], var4 * EntitySheep.fleeceColorTable[var5][2]);
			}
			return 1;
		}
		else
		{
			return -1;
		}
	}

	@Override
	protected float handleRotationFloat(EntityLivingBase par1EntityLivingBase, float par2)
	{
		return (float)par1EntityLivingBase.ticksExisted + par2;
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity par1Entity)
	{
		return this.getDocaResourceLocation((DocaEntityBase)par1Entity);
	}

	protected ResourceLocation getDocaResourceLocation(DocaEntityBase par1DocaEntity)
	{
		return par1DocaEntity.isTamed() ? DocaRenderInit.getRenderTable(DocaReg.getNameToType(stringSelector), par1DocaEntity.getIndexTexture(), "tame") : DocaRenderInit.getRenderTable(DocaReg.getNameToType(stringSelector), par1DocaEntity.getIndexTexture(), "tex");
	}

	@Override
	protected void passSpecialRender(EntityLivingBase par1EntityLivingBase, double d, double d1, double d2)
	{
		DocaEntityBase tempDocaEntityBase = (DocaEntityBase)par1EntityLivingBase;

		if (DocaSet.show_NameBar || DocaSet.show_LifeBar)
		{
			if (tempDocaEntityBase.isTamed())
			{
				if (!tempDocaEntityBase.isRiden()){
					renderName(tempDocaEntityBase, d, d1, d2);
				}
			}
		}
		if (tempDocaEntityBase.isEmotion() != 0)
		{
			renderEmotion(tempDocaEntityBase, d, d1, d2);
		}
	}

	protected void renderEmotion(DocaEntityBase par1DocaEntityBase, double par2, double par4, double par6)
	{
		par4 = par4 - 0.7D;

		if (par1DocaEntityBase != renderManager.livingPlayer)
		{
			float f = 1.6F;
			float f1 = 0.01666667F * f;
			float f2 = par1DocaEntityBase.getDistanceToEntity(renderManager.livingPlayer);
			float f3 = par1DocaEntityBase.isSneaking() ? 32F : 64F;

			if (f2 < f3)
			{
				double posWidthPoint = -30.0D;
				double posTopPoint = par1DocaEntityBase.getLabelHeight();
				double posUnderPoint = posTopPoint + 8.0D;
				double indexIconSize = 16.0D;
				DocaTools.setbindTextureDoca(DocaFuncEmotion.iconEmotion[par1DocaEntityBase.isEmotion()]);
				GL11.glPushMatrix();
				GL11.glTranslatef((float)par2, (float)par4 + 2.3F, (float)par6);
				GL11.glNormal3f(0.0F, 1.0F, 0.0F);
				GL11.glRotatef(-renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
				GL11.glRotatef(renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
				GL11.glScalef(-f1, -f1, f1);
				GL11.glDisable(GL11.GL_LIGHTING);
				GL11.glTranslatef(0.0F, 0.25F / f1, 0.0F);
				GL11.glDepthMask(false);
				GL11.glEnable(GL11.GL_BLEND);
				GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
				GL11.glDepthMask(true);
				Tessellator tessellatorEmotion = Tessellator.instance;
				tessellatorEmotion.startDrawingQuads();
				tessellatorEmotion.setColorOpaque_I(16777215);

				tessellatorEmotion.addVertexWithUV(-posWidthPoint,                 posUnderPoint + 1D + indexIconSize, 0.0D, 0.0D, 1.0D);
				tessellatorEmotion.addVertexWithUV(-posWidthPoint + indexIconSize, posUnderPoint + 1D + indexIconSize, 0.0D, 1.0D, 1.0D);
				tessellatorEmotion.addVertexWithUV(-posWidthPoint + indexIconSize, posUnderPoint + 1D,                 0.0D, 1.0D, 0.0D);
				tessellatorEmotion.addVertexWithUV(-posWidthPoint,                 posUnderPoint + 1D,                 0.0D, 0.0D, 0.0D);
				tessellatorEmotion.draw();
				GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
				GL11.glDisable(GL11.GL_BLEND);
				GL11.glEnable(GL11.GL_LIGHTING);
				GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
				GL11.glPopMatrix();
			}

		}
	}

	protected void renderName(DocaEntityBase par1DocaEntityBase, double par2, double par4, double par6)
	{
		par4 = par4 - 0.7D;

		if (par1DocaEntityBase != renderManager.livingPlayer)
		{
			float f = 1.6F;
			float f1 = 0.01666667F * f;
			float f2 = par1DocaEntityBase.getDistanceToEntity(renderManager.livingPlayer);
			float f3 = par1DocaEntityBase.isSneaking() ? 32F : 64F;

			if (f2 < f3)
			{
				int posWidthPoint = DocaSet.show_NameBar ? 30 : 15;
				double posTopPoint = par1DocaEntityBase.getLabelHeight();
				posTopPoint += DocaSet.show_NameBar ?  0D : 5D ;
				double posUnderPoint = DocaSet.show_NameBar ? posTopPoint + 11D : posTopPoint + 2D ;
				GL11.glPushMatrix();
				GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
				GL11.glTranslatef((float)par2, (float)par4 + 2.3F, (float)par6);
				GL11.glNormal3f(0.0F, 1.0F, 0.0F);
				GL11.glRotatef(-renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
				GL11.glRotatef(renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
				GL11.glScalef(-f1, -f1, f1);
				GL11.glDisable(GL11.GL_LIGHTING);
				GL11.glTranslatef(0.0F, 0.25F / f1, 0.0F);
				GL11.glDepthMask(false);
				GL11.glEnable(GL11.GL_BLEND);
				GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
				int x = par1DocaEntityBase.getBrightnessForRender((float)par2);
				int y = x % 65536;
				int z = x / 65536;
				OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)y / 1.0F, (float)z / 1.0F);
				Tessellator tessellator = Tessellator.instance;
				GL11.glDisable(GL11.GL_TEXTURE_2D);
				tessellator.startDrawingQuads();
				tessellator.setColorRGBA_I(DocaSet.show_BarBackGroundColor, DocaSet.show_BarBackGroundStrength);
				tessellator.addVertex(-posWidthPoint - 1, posTopPoint - 0.5D, 0.0D);
				tessellator.addVertex(-posWidthPoint - 1, posUnderPoint + 0.5D, 0.0D);
				tessellator.addVertex(posWidthPoint + 1, posUnderPoint + 0.5D, 0.0D);
				tessellator.addVertex(posWidthPoint + 1, posTopPoint - 0.5D, 0.0D);
				tessellator.draw();
				GL11.glEnable(GL11.GL_TEXTURE_2D);
				GL11.glDisable(GL11.GL_BLEND);
				GL11.glDepthMask(true);

				if (DocaSet.show_StatusIcon && DocaSet.show_StatusIconType == 0)
				{
					double indexIcon = (double)(par1DocaEntityBase.getRenderStatus() * 0.1);
					double indexIcon2 = indexIcon + 0.1D;
					double indexIconSize = 8D;
					Tessellator tessellator2 = Tessellator.instance;
					DocaTools.setbindTextureDoca(field_110420_t);
					tessellator2.startDrawingQuads();
					tessellator2.setColorOpaque_I(16777215);
					tessellator2.addVertexWithUV(-posWidthPoint, posUnderPoint + 1D + indexIconSize, 0.0D, indexIcon, 1.0D);
					tessellator2.addVertexWithUV(-posWidthPoint + indexIconSize, posUnderPoint + 1D + indexIconSize, 0.0D, indexIcon2, 1.0D);
					tessellator2.addVertexWithUV(-posWidthPoint + indexIconSize,  posUnderPoint + 1D, 0.0D, indexIcon2, 0.0D);
					tessellator2.addVertexWithUV(-posWidthPoint,  posUnderPoint + 1D, 0.0D, indexIcon, 0.0D);
					tessellator2.draw();
					GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
				}

				if (DocaSet.show_NameBar)
				{
					String s = par1DocaEntityBase.getName();
					FontRenderer fontrenderer = getFontRendererFromRenderManager();
					fontrenderer.drawString(s, -fontrenderer.getStringWidth(s) / 2, (int)posTopPoint + 1, DocaSet.show_NameColor);

					if (DocaSet.show_StatusIcon && DocaSet.show_StatusIconType != 0)
					{
						String s2 = par1DocaEntityBase.getRenderStatusText();
						fontrenderer.drawString(s2, -((fontrenderer.getStringWidth(s2) + posWidthPoint) / 2), (int)posUnderPoint + 1, DocaSet.show_NameColor);
					}
				}
				else
				{
					if (DocaSet.show_StatusIcon && DocaSet.show_StatusIconType != 0)
					{
						String s2 = par1DocaEntityBase.getRenderStatusText();
						FontRenderer fontrenderer = getFontRendererFromRenderManager();
						fontrenderer.drawString(s2, -(fontrenderer.getStringWidth(s2) / 2), (int)posUnderPoint + 1, DocaSet.show_NameColor);
					}
				}

				if (DocaSet.show_LifeBar)
				{
					Tessellator tessellator1 = Tessellator.instance;
					GL11.glDisable(GL11.GL_TEXTURE_2D);
					int k = (int)((2D * (double)posWidthPoint * (double)par1DocaEntityBase.getLife()) / (double)par1DocaEntityBase.getMaxHealth());
					tessellator1.startDrawingQuads();
					tessellator1.setColorRGBA_I(par1DocaEntityBase.getLifeColor(), 196);

					if (DocaSet.show_NameBar)
					{
						int shiftLifeBar = 1;
						tessellator1.addVertex(-posWidthPoint, posUnderPoint - shiftLifeBar, 0.0D);
						tessellator1.addVertex(-posWidthPoint, posUnderPoint, 0.0D);
						tessellator1.addVertex(-posWidthPoint + k, posUnderPoint, 0.0D);
						tessellator1.addVertex(-posWidthPoint + k, posUnderPoint - shiftLifeBar, 0.0D);
					}
					else
					{
						tessellator1.addVertex(-posWidthPoint, posTopPoint, 0.0D);
						tessellator1.addVertex(-posWidthPoint, posUnderPoint, 0.0D);
						tessellator1.addVertex(-posWidthPoint + k, posUnderPoint, 0.0D);
						tessellator1.addVertex(-posWidthPoint + k, posTopPoint, 0.0D);
					}

					tessellator1.draw();
					GL11.glEnable(GL11.GL_TEXTURE_2D);
				}

				GL11.glDisable(GL11.GL_BLEND);
				GL11.glEnable(GL11.GL_LIGHTING);
				GL11.glPopMatrix();
			}
		}
	}
}
