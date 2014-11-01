package mods.doca.entity.ai;

import mods.doca.entity.*;

import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.util.*;

public class DocaEntityAILeapAtTarget extends EntityAIBase
{
	DocaEntityBase theBase;
	EntityLivingBase leapTarget;
	float leapMotionY;

	public DocaEntityAILeapAtTarget(DocaEntityBase par1DocaEntityBase, float par2)
	{
		this.theBase = par1DocaEntityBase;
		this.leapMotionY = par2;
		this.setMutexBits(5);
	}

	@Override
	public boolean shouldExecute()
	{
		this.leapTarget = this.theBase.getAttackTarget();

		if (this.leapTarget == null)
		{
			return false;
		}
		if (!this.theBase.attackerMode.equalsIgnoreCase(""))
		{
			return false;
		}
		else if (!this.theBase.isAttackTargetCheck(this.leapTarget))
		{
			this.leapTarget = null;
			return false;
		}
		else
		{
			double var2 = this.theBase.getDistanceSqToEntity(this.leapTarget);
			return var2 >= 4.0D && var2 <= 16.0D ? (!this.theBase.onGround ? false : this.theBase.getRNG().nextInt(5) == 0) : false;
		}
	}

	@Override
	public boolean continueExecuting()
	{
		return !this.theBase.onGround;
	}

	@Override
	public void startExecuting()
	{
		double var1 = this.leapTarget.posX - this.theBase.posX;
		double var3 = this.leapTarget.posZ - this.theBase.posZ;
		float var5 = MathHelper.sqrt_double(var1 * var1 + var3 * var3);
		this.theBase.motionX += var1 / (double)var5 * 0.5D * 0.800000011920929D + this.theBase.motionX * 0.20000000298023224D;
		this.theBase.motionZ += var3 / (double)var5 * 0.5D * 0.800000011920929D + this.theBase.motionZ * 0.20000000298023224D;
		this.theBase.motionY = (double)this.leapMotionY;
	}
}
