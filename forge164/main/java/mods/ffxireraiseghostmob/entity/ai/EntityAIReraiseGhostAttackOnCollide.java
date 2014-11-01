package mods.ffxireraiseghostmob.entity.ai;

import mods.ffxireraiseghostmob.entity.EntityReraiseGhost;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.pathfinding.PathPoint;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityAIReraiseGhostAttackOnCollide extends EntityAIBase
{
    private World worldObj;
    private EntityReraiseGhost attacker;

    int attackTick;

    double speedTowardsTarget;

    boolean longMemory;

    private PathEntity entityPathEntity;
    private Class classTarget;
    private int field_75445_i;

    private int failedPathFindingPenalty;

    public EntityAIReraiseGhostAttackOnCollide(EntityReraiseGhost par1EntityCreature, Class par2Class, double par3, boolean par5)
    {
        this.classTarget = par2Class;
        this.attacker = par1EntityCreature;
        this.worldObj = par1EntityCreature.worldObj;
        this.speedTowardsTarget = par3;
        this.longMemory = par5;
        this.setMutexBits(3);
    }

    public EntityAIReraiseGhostAttackOnCollide(EntityReraiseGhost par1EntityCreature, double par2, boolean par4)
    {
        this(par1EntityCreature, null, par2, par4);
    }

    @Override
    public boolean shouldExecute()
    {
        EntityLivingBase entitylivingbase = this.attacker.getAttackTarget();

        if(this.attacker.getReraiseGhostName().equalsIgnoreCase(""))
        {
        	return false;
        }

        if (EntityList.stringToClassMapping.containsKey(this.attacker.getReraiseGhostName()))
        {
            this.classTarget = (Class)EntityList.stringToClassMapping.get(this.attacker.getReraiseGhostName());
        }
        
        if (entitylivingbase == null)
        {
            return false;
        }
        else if (!entitylivingbase.isEntityAlive())
        {
            return false;
        }
        else if (this.classTarget != null && !this.classTarget.isAssignableFrom(entitylivingbase.getClass()))
        {
            return false;
        }
        else
        {
            if (-- this.field_75445_i <= 0)
            {
                this.entityPathEntity = this.attacker.getNavigator().getPathToEntityLiving(entitylivingbase);
                this.field_75445_i = 4 + this.attacker.getRNG().nextInt(7);
                return this.entityPathEntity != null;
            }
            else
            {
                return true;
            }
        }
    }

    @Override
    public boolean continueExecuting()
    {
        EntityLivingBase entitylivingbase = this.attacker.getAttackTarget();
        return entitylivingbase == null ? false : (!entitylivingbase.isEntityAlive() ? false : (!this.longMemory ? !this.attacker.getNavigator().noPath() : this.attacker.func_110176_b(MathHelper.floor_double(entitylivingbase.posX), MathHelper.floor_double(entitylivingbase.posY), MathHelper.floor_double(entitylivingbase.posZ))));
    }

    @Override
    public void startExecuting()
    {
        this.attacker.getNavigator().setPath(this.entityPathEntity, this.speedTowardsTarget);
        this.field_75445_i = 0;
    }

    @Override
    public void resetTask()
    {
        this.attacker.getNavigator().clearPathEntity();
    }

    @Override
    public void updateTask()
    {
        EntityLivingBase entitylivingbase = this.attacker.getAttackTarget();
        this.attacker.getLookHelper().setLookPositionWithEntity(entitylivingbase, 30.0F, 30.0F);

        if ((this.longMemory || this.attacker.getEntitySenses().canSee(entitylivingbase)) && --this.field_75445_i <= 0)
        {
            this.field_75445_i = failedPathFindingPenalty + 4 + this.attacker.getRNG().nextInt(7);
            this.attacker.getNavigator().tryMoveToEntityLiving(entitylivingbase, this.speedTowardsTarget);
            if (this.attacker.getNavigator().getPath() != null)
            {
                PathPoint finalPathPoint = this.attacker.getNavigator().getPath().getFinalPathPoint();
                if (finalPathPoint != null && entitylivingbase.getDistanceSq(finalPathPoint.xCoord, finalPathPoint.yCoord, finalPathPoint.zCoord) < 1)
                {
                    failedPathFindingPenalty = 0;
                }
                else
                {
                    failedPathFindingPenalty += 10;
                }
            }
            else
            {
                failedPathFindingPenalty += 10;
            }
        }

        this.attackTick = Math.max(this.attackTick - 1, 0);
        double d0 = (double)(this.attacker.width * 2.0F * this.attacker.width * 2.0F + entitylivingbase.width);

        if (this.attacker.getDistanceSq(entitylivingbase.posX, entitylivingbase.boundingBox.minY, entitylivingbase.posZ) <= d0)
        {
            if (this.attackTick <= 0)
            {
                this.attackTick = 20;

                if (this.attacker.getHeldItem() != null)
                {
                    this.attacker.swingItem();
                }

                this.attacker.attackEntityAsMob(entitylivingbase);
            }
        }
    }
}
