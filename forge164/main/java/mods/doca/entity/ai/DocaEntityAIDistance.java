package mods.doca.entity.ai;

import mods.doca.entity.*;

import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.player.*;
import net.minecraft.pathfinding.*;
import net.minecraft.util.*;

import java.util.*;

public class DocaEntityAIDistance extends EntityAIBase
{
	private DocaEntityBase theEntity;
	private double speed;
	private Entity closestLivingEntity;
	private float distanceFromEntity;
	private PathEntity entityPathEntity;
	private PathNavigate entityPathNavigate;
	private Class targetEntityClass;

	public DocaEntityAIDistance(DocaEntityBase par1DocaEntityBase, Class par2Class, float par3, double par4)
	{
		this.theEntity = par1DocaEntityBase;
		this.targetEntityClass = par2Class;
		this.distanceFromEntity = par3;
		this.speed = par4;
		this.entityPathNavigate = par1DocaEntityBase.getNavigator();
		this.setMutexBits(3);
	}

	@Override
	public boolean shouldExecute()
	{
		if (this.targetEntityClass == EntityPlayer.class)
		{
			if (!this.theEntity.isTamed())
			{
				return false;
			}
			else if (!this.theEntity.isDistance())
			{
				return false;
			}

			this.closestLivingEntity = this.theEntity.worldObj.getClosestPlayerToEntity(this.theEntity, (double)this.distanceFromEntity);

			if (this.closestLivingEntity == null)
			{
				return false;
			}
		}
		else
		{
			List var1 = this.theEntity.worldObj.getEntitiesWithinAABB(this.targetEntityClass, this.theEntity.boundingBox.expand((double)this.distanceFromEntity, 3.0D, (double)this.distanceFromEntity));

			if (var1.isEmpty())
			{
				return false;
			}

			this.closestLivingEntity = (Entity)var1.get(0);
		}

		Vec3 var2 = RandomPositionGenerator.findRandomTargetBlockAwayFrom(this.theEntity, (int)this.distanceFromEntity, 5, this.theEntity.worldObj.getWorldVec3Pool().getVecFromPool(this.closestLivingEntity.posX, this.closestLivingEntity.posY, this.closestLivingEntity.posZ));

		if (var2 == null)
		{
			return false;
		}
		else if (this.closestLivingEntity.getDistanceSq(var2.xCoord, var2.yCoord, var2.zCoord) < this.closestLivingEntity.getDistanceSqToEntity(this.theEntity))
		{
			return false;
		}
		else
		{
			this.entityPathEntity = this.entityPathNavigate.getPathToXYZ(var2.xCoord, var2.yCoord, var2.zCoord);
			return this.entityPathEntity == null ? false : this.entityPathEntity.isDestinationSame(var2);
		}
	}

	@Override
	public boolean continueExecuting()
	{
		return !this.entityPathNavigate.noPath();
	}

	@Override
	public void startExecuting()
	{
		this.entityPathNavigate.setPath(this.entityPathEntity, this.speed);
	}

	@Override
	public void resetTask()
	{
		this.closestLivingEntity = null;
	}

	@Override
	public void updateTask()
	{
		this.theEntity.getNavigator().setSpeed(this.speed);
	}
}