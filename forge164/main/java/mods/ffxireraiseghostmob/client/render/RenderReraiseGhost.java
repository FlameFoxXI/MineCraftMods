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

    protected void func_82421_b()
    {
        this.field_82423_g = new ModelZombie(1.0F, true);
        this.field_82425_h = new ModelZombie(0.5F, true);
    }

    protected int func_82429_a(EntityReraiseGhost par1EntityReraiseGhost, int par2, float par3)
    {
//        this.func_82427_a(par1EntityReraiseGhost);
        return super.func_130006_a(par1EntityReraiseGhost, par2, par3);
    }

    public void doRenderReraiseGhost(EntityReraiseGhost par1EntityReraiseGhost, double par2, double par4, double par6, float par8, float par9)
    {
 //       this.func_82427_a(par1EntityReraiseGhost);
        super.doRenderLiving(par1EntityReraiseGhost, par2, par4, par6, par8, par9);
    }

    protected ResourceLocation func_110863_a(EntityReraiseGhost par1EntityReraiseGhost)
    {
        if (texturesList.containsKey(par1EntityReraiseGhost.getReraiseGhostName()))
        {
        	return texturesList.get(par1EntityReraiseGhost.getReraiseGhostName());
        }
    	return  texturesList.get("Pig");
    }

    protected void func_82428_a(EntityReraiseGhost par1EntityReraiseGhost, float par2)
    {
//      this.func_82427_a(par1EntityReraiseGhost);
        super.func_130005_c(par1EntityReraiseGhost, par2);
    }

//    private void func_82427_a(EntityReraiseGhost par1EntityReraiseGhost)
//    {
//        this.modelBipedMain = (ModelBiped)this.mainModel;
//    }

    protected void func_82430_a(EntityReraiseGhost par1EntityReraiseGhost, float par2, float par3, float par4)
    {
        super.rotateCorpse(par1EntityReraiseGhost, par2, par3, par4);
    }

    @Override
    protected void func_130005_c(EntityLiving par1EntityLiving, float par2)
    {
        this.func_82428_a((EntityReraiseGhost)par1EntityLiving, par2);
    }

    @Override
    protected ResourceLocation func_110856_a(EntityLiving par1EntityLiving)
    {
        return this.func_110863_a((EntityReraiseGhost)par1EntityLiving);
    }

    @Override
    public void doRenderLiving(EntityLiving par1EntityLiving, double par2, double par4, double par6, float par8, float par9)
    {
        this.doRenderReraiseGhost((EntityReraiseGhost)par1EntityLiving, par2, par4, par6, par8, par9);
    }

    @Override
    protected int func_130006_a(EntityLiving par1EntityLiving, int par2, float par3)
    {
        return this.func_82429_a((EntityReraiseGhost)par1EntityLiving, par2, par3);
    }

    @Override
    protected int shouldRenderPass(EntityLivingBase par1EntityLivingBase, int par2, float par3)
    {
        return this.func_82429_a((EntityReraiseGhost)par1EntityLivingBase, par2, par3);
    }

    @Override
    protected void renderEquippedItems(EntityLivingBase par1EntityLivingBase, float par2)
    {
        this.func_82428_a((EntityReraiseGhost)par1EntityLivingBase, par2);
    }

    @Override
    protected void rotateCorpse(EntityLivingBase par1EntityLivingBase, float par2, float par3, float par4)
    {
        this.func_82430_a((EntityReraiseGhost)par1EntityLivingBase, par2, par3, par4);
    }

    @Override
    public void renderPlayer(EntityLivingBase par1EntityLivingBase, double par2, double par4, double par6, float par8, float par9)
    {
        this.doRenderReraiseGhost((EntityReraiseGhost)par1EntityLivingBase, par2, par4, par6, par8, par9);
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity par1Entity)
    {
        return this.func_110863_a((EntityReraiseGhost)par1Entity);
    }

    @Override
    public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9)
    {
        this.doRenderReraiseGhost((EntityReraiseGhost)par1Entity, par2, par4, par6, par8, par9);
    }
}
