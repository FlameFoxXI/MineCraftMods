package mods.ffxireraiseghostmob.client.render;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mods.ffxireraiseghostmob.core.FFxiSet;
import mods.ffxireraiseghostmob.entity.EntityReraiseGhost;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelZombie;
import net.minecraft.client.model.ModelZombieVillager;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class RenderReraiseGhost extends RenderBiped
{
    private static final Map<String, ResourceLocation> texturesList = new HashMap();
    
    public RenderReraiseGhost()
    {
        super(new ModelZombie(0.0F, true), 0.5F, 1.0F);
        
        for (Entry<String, String> entry : FFxiSet.mobsTexture.entrySet())
        {
        	texturesList.put(entry.getKey(), new ResourceLocation(entry.getValue()));
        }
    }

    @Override
    protected void func_82421_b()
    {
        this.field_82423_g = new ModelZombie(1.0F, true);
        this.field_82425_h = new ModelZombie(0.5F, true);
    }

    protected ResourceLocation getEntityReraiseGhostTexture(EntityReraiseGhost par1EntityReraiseGhost)
    {
        if (texturesList.containsKey(par1EntityReraiseGhost.getReraiseGhostName()))
        {
        	return texturesList.get(par1EntityReraiseGhost.getReraiseGhostName());
        }
    	return  texturesList.get("Pig");
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityLiving par1EntityLiving)
    {
        return this.getEntityReraiseGhostTexture((EntityReraiseGhost)par1EntityLiving);
    }
}
