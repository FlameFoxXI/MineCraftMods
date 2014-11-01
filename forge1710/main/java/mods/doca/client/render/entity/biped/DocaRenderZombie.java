package mods.doca.client.render.entity.biped;

import net.minecraft.client.model.*;
import mods.doca.client.model.biped.*;
import mods.doca.client.render.entity.*;
import mods.doca.core.DocaSet;
import cpw.mods.fml.relauncher.*;


@SideOnly(Side.CLIENT)
public class DocaRenderZombie extends DocaRenderBiped
{
    public DocaRenderZombie()
    {
		this(new DocaModelZombie(), new DocaModelZombie(), 0.5F);
	}

	public DocaRenderZombie(ModelBiped par1ModelBiped, ModelBiped par2ModelBiped, float par2)
	{
		super(par1ModelBiped, par2ModelBiped, par2, DocaSet.PET_ZOMBE);
	}
}
