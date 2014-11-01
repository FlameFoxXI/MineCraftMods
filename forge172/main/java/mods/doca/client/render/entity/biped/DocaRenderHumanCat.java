package mods.doca.client.render.entity.biped;

import cpw.mods.fml.relauncher.*;

import mods.doca.*;
import mods.doca.client.model.*;
import mods.doca.client.model.biped.*;
import mods.doca.client.render.entity.*;
import mods.doca.core.*;
import mods.doca.entity.*;
import mods.doca.entity.biped.*;

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
public class DocaRenderHumanCat extends DocaRenderBiped
{
	public DocaRenderHumanCat()
	{
		this(new DocaModelHumanCat(), new DocaModelHumanCat(), 0.5F);
	}

	public DocaRenderHumanCat(ModelBiped par1ModelBiped, ModelBiped par2ModelBiped, float par2)
	{
		super(par1ModelBiped, par2ModelBiped, par2, DocaSet.PET_HUMCA);
	}

	protected float getTailRotation(DocaEntityHumanCat par1DocaEntity, float par2)
	{
		return par1DocaEntity.getTailRotation();
	}

	@Override
	protected float handleRotationFloat(EntityLivingBase par1EntityLivingBase, float par2)
	{
		return this.getTailRotation((DocaEntityHumanCat)par1EntityLivingBase, par2);
	}
}
