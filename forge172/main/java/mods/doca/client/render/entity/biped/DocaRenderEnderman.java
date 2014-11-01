package mods.doca.client.render.entity.biped;

import cpw.mods.fml.relauncher.*;
import mods.doca.*;
import mods.doca.client.model.*;
import mods.doca.client.model.biped.*;
import mods.doca.client.render.entity.*;
import mods.doca.core.*;
import mods.doca.entity.*;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.client.entity.*;
import net.minecraft.client.model.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.entity.*;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;

import java.util.*;

import org.lwjgl.opengl.*;

@SideOnly(Side.CLIENT)
public class DocaRenderEnderman extends DocaRenderBase
{
    public DocaRenderEnderman()
	{
		this(new DocaModelEnderman(), new DocaModelEnderman(), 0.5F);
	}

	public DocaRenderEnderman(ModelBiped par1ModelBiped, ModelBiped par2ModelBiped, float par2)
	{
		super(par1ModelBiped, par2);
		this.setRenderPassModel(par2ModelBiped);
		this.stringSelector = DocaSet.PET_ENDER;
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
			f1 = 2.0F;
			int i = par1DocaEntity.getCollarColor();
			int c0 = 15728640;
			int j = c0 % 65536;
			int k = c0 / 65536;
			
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glDisable(GL11.GL_ALPHA_TEST);
            GL11.glBlendFunc(GL11.GL_ONE, GL11.GL_ONE);
            GL11.glDisable(GL11.GL_LIGHTING);
//			RenderHelper.enableStandardItemLighting();
            GL11.glDepthMask(true);

            this.bindTexture(DocaRenderInit.getRenderTable(DocaReg.getNameToType(stringSelector), par1DocaEntity.getIndexTexture(), "collar"));
			OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)j / 1.0F, (float)k / 1.0F);
			GL11.glColor4f(f1 * EntitySheep.fleeceColorTable[i][0], f1 * EntitySheep.fleeceColorTable[i][1], f1 * EntitySheep.fleeceColorTable[i][2], 1.0F);
//			RenderHelper.disableStandardItemLighting();
			GL11.glEnable(GL11.GL_LIGHTING);
            GL11.glEnable(GL11.GL_ALPHA_TEST);
            GL11.glDisable(GL11.GL_BLEND);
			return 1;
		}
		else
		{
			return -1;
		}
	}

	protected void renderCarrying(DocaEntityBase par1DocaEntity, float par2)
	{
//		super.renderEquippedItems(par1DocaEntity, par2);

//		if (par1DocaEntity.getCarried() > 0)
//		if (par1DocaEntity.getCarried().func_149688_o() != Material.field_151579_a)
		if (par1DocaEntity.getCarried() != Blocks.air)
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
			this.bindTexture(TextureMap.locationBlocksTexture);
			this.field_147909_c.renderBlockAsItem(par1DocaEntity.getCarried(), par1DocaEntity.getCarryingData(), 1.0F);
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
