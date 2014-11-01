package mods.doca.client.render.entity.passive;

import cpw.mods.fml.relauncher.*;

import mods.doca.client.model.passive.*;
import mods.doca.client.render.entity.*;
import mods.doca.core.*;
import mods.doca.entity.*;
import mods.doca.entity.passive.*;

import net.minecraft.client.model.*;
import net.minecraft.entity.*;
import net.minecraft.entity.passive.*;
import net.minecraft.util.*;

import org.lwjgl.opengl.*;

@SideOnly(Side.CLIENT)
public class DocaRenderBunny extends DocaRenderBase
{
	public DocaRenderBunny()
	{
		super(new DocaModelBunny(), 0.5F);
		this.setRenderPassModel(new DocaModelBunny());
		this.stringSelector = DocaSet.PET_BUNNY;
	}

	@Override
	protected int shouldRenderPass(EntityLivingBase par1EntityLivingBase, int par2, float par3)
	{
		return this.renderBaseMain((DocaEntityBase)par1EntityLivingBase, par2, par3);
	}

	protected float getTailRotation(DocaEntityBunny par1DocaEntity, float par2)
	{
		return par1DocaEntity.getTailRotation();
	}

	@Override
	protected float handleRotationFloat(EntityLivingBase par1EntityLivingBase, float par2)
	{
		return this.getTailRotation((DocaEntityBunny)par1EntityLivingBase, par2);
	}
}