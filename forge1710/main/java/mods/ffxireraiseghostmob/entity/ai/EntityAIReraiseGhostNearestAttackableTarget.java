package mods.ffxireraiseghostmob.entity.ai;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import mods.ffxireraiseghostmob.entity.EntityReraiseGhost;
import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAITarget;

public class EntityAIReraiseGhostNearestAttackableTarget extends EntityAITarget
{
    private Class targetClass;
    private final int targetChance;

    private final EntityAINearestAttackableTarget.Sorter theNearestAttackableTargetSorter;

    private final IEntitySelector targetEntitySelector;
    private EntityLivingBase targetEntity;
	private EntityReraiseGhost attacker;

    public EntityAIReraiseGhostNearestAttackableTarget(EntityReraiseGhost par1EntityCreature, int par3, boolean par4)
    {
        this(par1EntityCreature, null, par3, par4, false);
    }
    
    public EntityAIReraiseGhostNearestAttackableTarget(EntityReraiseGhost par1EntityCreature, Class par2Class, int par3, boolean par4)
    {
        this(par1EntityCreature, par2Class, par3, par4, false);
    }

    public EntityAIReraiseGhostNearestAttackableTarget(EntityReraiseGhost par1EntityCreature, Class par2Class, int par3, boolean par4, boolean par5)
    {
        this(par1EntityCreature, par2Class, par3, par4, par5, (IEntitySelector)null);
    }

    public EntityAIReraiseGhostNearestAttackableTarget(EntityReraiseGhost par1EntityCreature, Class par2Class, int par3, boolean par4, boolean par5, final IEntitySelector par6IEntitySelector)
    {
        super(par1EntityCreature, par4, par5);
        this.attacker = par1EntityCreature;
        this.targetClass = par2Class;
        this.targetChance = par3;
        this.theNearestAttackableTargetSorter = new EntityAINearestAttackableTarget.Sorter(par1EntityCreature);
        this.setMutexBits(1);
        this.targetEntitySelector = new IEntitySelector()
        {
            public boolean isEntityApplicable(Entity par1Entity)
            {
                return !(par1Entity instanceof EntityLivingBase) ? false : (par6IEntitySelector != null && !par6IEntitySelector.isEntityApplicable(par1Entity) ? false : EntityAIReraiseGhostNearestAttackableTarget.this.isSuitableTarget((EntityLivingBase)par1Entity, false));
            }
        };
    }

    @Override
    public boolean shouldExecute()
    {
        if(this.attacker.getReraiseGhostName().equalsIgnoreCase(""))
        {
        	return false;
        }

        if (EntityList.stringToClassMapping.containsKey(this.attacker.getReraiseGhostName()))
        {
            this.targetClass = (Class)EntityList.stringToClassMapping.get(this.attacker.getReraiseGhostName());
        }
    	
    	if (this.targetChance > 0 && this.taskOwner.getRNG().nextInt(this.targetChance) != 0)
        {
            return false;
        }
        else
        {
            double d0 = this.getTargetDistance();
            List list = this.taskOwner.worldObj.selectEntitiesWithinAABB(this.targetClass, this.taskOwner.boundingBox.expand(d0, 4.0D, d0), this.targetEntitySelector);
            Collections.sort(list, this.theNearestAttackableTargetSorter);

            if (list.isEmpty())
            {
                return false;
            }
            else
            {
                this.targetEntity = (EntityLivingBase)list.get(0);
                return true;
            }
        }
    }

    @Override
    public void startExecuting()
    {
        this.taskOwner.setAttackTarget(this.targetEntity);
        super.startExecuting();
    }

    @Override
    protected boolean isSuitableTarget(EntityLivingBase par1EntityLivingBase, boolean par2)
    {
    	return super.isSuitableTarget(par1EntityLivingBase, par2);
    }
}