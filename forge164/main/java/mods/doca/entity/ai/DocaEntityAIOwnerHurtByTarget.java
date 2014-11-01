package mods.doca.entity.ai;

import mods.doca.entity.*;

import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.passive.*;

public class DocaEntityAIOwnerHurtByTarget extends EntityAITarget
{
	DocaEntityBase theBase;
	EntityLivingBase theOwnerAttacker;
	private int field_142051_e;

	public DocaEntityAIOwnerHurtByTarget(DocaEntityBase par1DocaEntityBase)
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
			EntityLivingBase var1 = this.theBase.func_130012_q();

			if (var1 == null)
			{
				return false;
			}
			else if (!this.theBase.isAttackTargetCheck(var1.getAITarget()))
			{
				return false;
			}
			else
			{
				this.theOwnerAttacker = var1.getAITarget();
				int var2 = var1.func_142015_aE();
				return var2 != this.field_142051_e && this.isSuitableTarget(this.theOwnerAttacker, false) && this.theBase.func_142018_a(this.theOwnerAttacker, var1);
			}
		}
	}

	@Override
	public void startExecuting()
	{
		this.taskOwner.setAttackTarget(this.theOwnerAttacker);
		EntityLivingBase var1 = this.theBase.func_130012_q();

		if (var1 != null)
		{
			this.field_142051_e = var1.func_142015_aE();
		}

		super.startExecuting();
	}
}
