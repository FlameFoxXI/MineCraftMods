package mods.doca.entity.ai;

import mods.doca.entity.*;

import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;

public class DocaEntityAISit extends EntityAIBase
{
	private DocaEntityBase theEntity;
	private boolean isSitting;

	public DocaEntityAISit(DocaEntityBase par1DocaEntityBase)
	{
		this.theEntity = par1DocaEntityBase;
		this.setMutexBits(5);
	}

	@Override
	public boolean shouldExecute()
	{
		if (!this.theEntity.isTamed())
		{
			return false;
		}
		else if (this.theEntity.isInWater())
		{
			return false;
		}
		else if (!this.theEntity.onGround)
		{
			return false;
		}
		else
		{
//			EntityLivingBase var1 = this.theEntity.func_130012_q();
			EntityLivingBase var1 = this.theEntity.getOwner();
			return var1 == null ? true : (this.theEntity.getDistanceSqToEntity(var1) < 144.0D && var1.getAITarget() != null ? false : this.isSitting);
		}
	}

	@Override
	public void startExecuting()
	{
		this.theEntity.getNavigator().clearPathEntity();
		this.theEntity.setSitting(true);
	}

	@Override
	public void resetTask()
	{
		this.theEntity.setSitting(false);
		this.theEntity.setSleeping(false);
		this.theEntity.setDistance(false);
	}

	public void setSitting(boolean par1)
	{
		this.isSitting = par1;
	}
}
