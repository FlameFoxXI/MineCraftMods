package mods.doca.client.render.entity;

import org.lwjgl.opengl.GL11;

import mods.doca.core.DocaSet;
import mods.doca.core.DocaTools;
import mods.doca.entity.DocaEntityBase;
import mods.doca.entity.DocaEntityBiped;
import net.minecraft.block.Block;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class DocaRenderBiped extends DocaRenderBase
{
	protected ModelBiped modelBipedMain;

	public DocaRenderBiped(ModelBiped par1ModelBiped, ModelBiped par2ModelBiped, float par2, String par3)
	{
		super(par1ModelBiped, par2);
		this.setRenderPassModel(par2ModelBiped);
		this.modelBipedMain = par1ModelBiped;
		this.stringSelector = par3;
	}

	@Override
//	public void doRenderLiving(EntityLiving par1EntityLiving, double par2, double par4, double par6, float par8, float par9)
	public void doRender(EntityLiving par1EntityLiving, double par2, double par4, double par6, float par8, float par9)
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

//		super.doRenderLiving(par1EntityLiving, par2, var12, par6, par8, par9);
		super.doRender(par1EntityLiving, par2, var12, par6, par8, par9);
		this.modelBipedMain.aimedBow = false;
		this.modelBipedMain.isSneak = false;
		this.modelBipedMain.heldItemRight = 0;
	}

	protected void func_82420_a(EntityLiving par1EntityLiving, ItemStack par2ItemStack)
	{
		this.modelBipedMain.heldItemRight = par2ItemStack != null ? 1 : 0;
		this.modelBipedMain.isSneak = par1EntityLiving.isSneaking();
	}

	protected void renderDocaItem(DocaEntityBiped par1DocaEntity, float par2)
	{
		float var3 = 1.0F;
		GL11.glColor3f(var3, var3, var3);
		super.renderEquippedItems(par1DocaEntity, par2);
		ItemStack var4 = par1DocaEntity.getHeldItem();
		float var6 = 1.0F;

		if (var4 != null  && var4.getItem() != null)
		{
			GL11.glPushMatrix();

			if (this.stringSelector.equalsIgnoreCase(DocaSet.PET_HUMCA))
			{
				if (this.mainModel.isChild || par1DocaEntity.getModelSize() == 2)
				{
					var6 = 0.4F;
					GL11.glTranslatef(0.0F, 0.925F, 0.0F);
				}
				else if (par1DocaEntity.getModelSize() == 1)
				{
					var6 = 1.0F;
					GL11.glTranslatef(0.0F, 0.025F, 0.025F);
				}
				else
				{
					var6 = 0.7F;
					GL11.glTranslatef(0.0F, 0.425F, 0.0F);
				}
			}
			else
			{
				if (this.mainModel.isChild || par1DocaEntity.getModelSize() == 2)
				{
					var6 = 0.5F;
					GL11.glTranslatef(0.0F, 0.625F, 0.0F);
				}
				else if (par1DocaEntity.getModelSize() == 1)
				{
					var6 = 1.3F;
					GL11.glTranslatef(0.0F, -0.425F, 0.0F);
				}
			}
			GL11.glScalef(var6, var6, var6);

			this.modelBipedMain.bipedRightArm.postRender(0.0625F);
			GL11.glTranslatef(-0.0625F, 0.4375F, 0.0625F);

			if (DocaTools.newItem(var4) instanceof ItemBlock && RenderBlocks.renderItemIn3d(Block.getBlockFromItem(DocaTools.newItem(var4)).getRenderType()))
			{
				var6 = 0.5F;
				GL11.glTranslatef(0.0F, 0.1875F, -0.3125F);
				var6 *= 0.75F;
				GL11.glRotatef(20.0F, 1.0F, 0.0F, 0.0F);
				GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
				GL11.glScalef(-var6, -var6, var6);
			}
			else if (DocaTools.ofItem(var4, Items.bow))
			{
				var6 = 0.625F;
				GL11.glTranslatef(0.0F, 0.125F, 0.3125F);
				GL11.glRotatef(-20.0F, 0.0F, 1.0F, 0.0F);
				GL11.glScalef(var6, -var6, var6);
				GL11.glRotatef(-100.0F, 1.0F, 0.0F, 0.0F);
				GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
			}
			else if (DocaTools.newItem(var4).isFull3D())
			{
				var6 = 0.625F;

				if (DocaTools.newItem(var4).shouldRotateAroundWhenRendering())
				{
					GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
					GL11.glTranslatef(0.0F, -0.125F, 0.0F);
				}

				GL11.glTranslatef(0.09375F, 0.1875F, 0.0F);
				GL11.glScalef(var6, -var6, var6);
				GL11.glRotatef(-100.0F, 1.0F, 0.0F, 0.0F);
				GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
			}
			else
			{
				var6 = 0.375F;
				GL11.glTranslatef(0.25F, 0.1875F, -0.1875F);
				GL11.glScalef(var6, var6, var6);
				GL11.glRotatef(60.0F, 0.0F, 0.0F, 1.0F);
				GL11.glRotatef(-90.0F, 1.0F, 0.0F, 0.0F);
				GL11.glRotatef(20.0F, 0.0F, 0.0F, 1.0F);
			}

			this.renderManager.itemRenderer.renderItem(par1DocaEntity, var4, 0);

			if (var4.getItem().requiresMultipleRenderPasses())
			{
				this.renderManager.itemRenderer.renderItem(par1DocaEntity, var4, 1);
			}

			GL11.glPopMatrix();
		}
	}

	@Override
	protected void renderEquippedItems(EntityLivingBase par1EntityLivingBase, float par2)
	{
		this.renderDocaItem((DocaEntityBiped)par1EntityLivingBase, par2);
	}

	@Override
	protected int shouldRenderPass(EntityLivingBase par1EntityLivingBase, int par2, float par3)
	{
		return this.renderBaseMain((DocaEntityBase)par1EntityLivingBase, par2, par3);
	}
}
