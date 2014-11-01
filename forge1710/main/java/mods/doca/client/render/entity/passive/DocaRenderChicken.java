package mods.doca.client.render.entity.passive;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mods.doca.client.model.passive.DocaModelBunny;
import mods.doca.client.model.passive.DocaModelChicken;
import mods.doca.client.render.entity.DocaRenderBase;
import mods.doca.core.DocaSet;
import mods.doca.entity.DocaEntityBase;
import mods.doca.entity.passive.*;
import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class DocaRenderChicken extends DocaRenderBase
{
	public DocaRenderChicken()
	{
		super(new DocaModelChicken(), 0.5F);
		this.setRenderPassModel(new DocaModelChicken());
		this.stringSelector = DocaSet.PET_CHIKN;
	}

	@Override
	protected int shouldRenderPass(EntityLivingBase par1EntityLivingBase, int par2, float par3)
	{
		return this.renderBaseMain((DocaEntityBase)par1EntityLivingBase, par2, par3);
	}

	protected float getWingRotation(DocaEntityChicken par1EntityChicken, float par2)
	{
		float f1 = par1EntityChicken.field_70888_h + (par1EntityChicken.field_70886_e - par1EntityChicken.field_70888_h) * par2;
		float f2 = par1EntityChicken.field_70884_g + (par1EntityChicken.destPos - par1EntityChicken.field_70884_g) * par2;
		return (MathHelper.sin(f1) + 1.0F) * f2;
	}

	protected float handleRotationFloat(EntityLivingBase par1EntityLivingBase, float par2)
	{
		return this.getWingRotation((DocaEntityChicken)par1EntityLivingBase, par2);
	}
}
