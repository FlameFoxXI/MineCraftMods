package mods.doca.entity.ai;

import mods.doca.entity.*;

import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;

public class DocaEntityAIOwnerHurtTarget extends EntityAITarget
{
	DocaEntityBase theBase;
	EntityLivingBase theTarget;
	private int field_142050_e;

	public DocaEntityAIOwnerHurtTarget(DocaEntityBase par1DocaEntityBase)
	{
		super(par1DocaEntityBase, false);
		this.theBase = par1DocaEntityBase;
		this.setMutexBits(1);
	}

	@Override
	public boolean shouldExecute()
	{
		if (!this.theBase.isTamed())
		{
			return false;
		}
		else
		{
//			EntityLivingBase var1 = this.theBase.func_130012_q();
			EntityLivingBase var1 = this.theBase.getOwner();

			if (var1 == null)
			{
				return false;
			}
			else if (!this.theBase.isAttackTargetCheck(var1.getLastAttacker()))
			{
				return false;
			}
			else
			{
				this.theTarget = var1.getLastAttacker();
				int var2 = var1.getLastAttackerTime();
				return var2 != this.field_142050_e && this.isSuitableTarget(this.theTarget, false) && this.theBase.func_142018_a(this.theTarget, var1);
			}
		}
	}

	@Override
	public void startExecuting()
	{
		this.taskOwner.setAttackTarget(this.theTarget);
//		EntityLivingBase var1 = this.theBase.func_130012_q();
		EntityLivingBase var1 = this.theBase.getOwner();

		if (var1 != null)
		{
			this.field_142050_e = var1.getLastAttackerTime();
		}

		super.startExecuting();
	}
}
