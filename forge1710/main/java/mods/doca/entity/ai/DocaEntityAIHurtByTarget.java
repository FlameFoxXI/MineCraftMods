package mods.doca.entity.ai;

import mods.doca.entity.*;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.*;

import java.util.*;

public class DocaEntityAIHurtByTarget extends EntityAITarget
{
	boolean field_75312_a;
	private int field_142052_b;
	DocaEntityBase theBase;
	EntityLiving entityPathNavigate;

	public DocaEntityAIHurtByTarget(DocaEntityBase par1DocaEntityBase, boolean par2)
	{
		super(par1DocaEntityBase, false);
		this.theBase = par1DocaEntityBase;
		this.field_75312_a = par2;
		this.setMutexBits(1);
	}

	@Override
	public boolean shouldExecute()
	{
		EntityLivingBase var1 = this.taskOwner.getAITarget();

		if (var1 == null)
		{
			return false;
		}
		else if (!this.theBase.isAttackTargetCheck(var1))
		{
			return false;
		}
		else
		{
			return this.isSuitableTarget(this.taskOwner.getAITarget(), false);
		}
	}

	@Override
	public void startExecuting()
	{
		this.taskOwner.setAttackTarget(this.taskOwner.getAITarget());
		this.field_142052_b = this.taskOwner.func_142015_aE();

		if (this.field_75312_a)
		{
			double d0 = this.getTargetDistance();
			List list = this.taskOwner.worldObj.getEntitiesWithinAABB(this.taskOwner.getClass(), AxisAlignedBB.getBoundingBox(this.taskOwner.posX, this.taskOwner.posY, this.taskOwner.posZ, this.taskOwner.posX + 1.0D, this.taskOwner.posY + 1.0D, this.taskOwner.posZ + 1.0D).expand(d0, 10.0D, d0));
			Iterator iterator = list.iterator();

			while (iterator.hasNext())
			{
				EntityCreature entitycreature = (EntityCreature)iterator.next();

				if (this.taskOwner != entitycreature && entitycreature.getAttackTarget() == null && !entitycreature.isOnSameTeam(this.taskOwner.getAITarget()))
				{
					entitycreature.setAttackTarget(this.taskOwner.getAITarget());
				}
			}
		}

		super.startExecuting();
	}
}
