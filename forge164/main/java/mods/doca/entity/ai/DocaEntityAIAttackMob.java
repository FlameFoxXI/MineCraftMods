package mods.doca.entity.ai;

import mods.doca.core.DocaSet;
import mods.doca.entity.*;

import net.minecraft.command.*;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.*;

import java.util.*;

public class DocaEntityAIAttackMob extends EntityAITarget
{
	DocaEntityBase theBase;

	private final int targetChance;
	private final IEntitySelector field_82643_g;
	private EntityLivingBase targetEntity;
	private DocaEntityAIAttackMobTargetSortery theDocaEntityAIAttackMobTargetSortery;

	public DocaEntityAIAttackMob(DocaEntityBase par1EntityDoggy, int par3, boolean par4)
	{
		this(par1EntityDoggy, par3, par4, false);
	}

	public DocaEntityAIAttackMob(DocaEntityBase par1EntityDoggy, int par3, boolean par4, boolean par5)
	{
		this(par1EntityDoggy, par3, par4, par5, (IEntitySelector)null);
	}

	public DocaEntityAIAttackMob(DocaEntityBase par1EntityDoggy, int par3, boolean par4, boolean par5, IEntitySelector par7IEntitySelector)
	{
		super(par1EntityDoggy, par4, par5);
		this.theBase	= par1EntityDoggy;
		this.targetChance = par3;
		this.theDocaEntityAIAttackMobTargetSortery = new DocaEntityAIAttackMobTargetSortery(this, par1EntityDoggy);
		this.field_82643_g = par7IEntitySelector;
		this.setMutexBits(1);
	}

	@Override
	public boolean shouldExecute()
	{
		if (this.targetChance > 0 && this.taskOwner.getRNG().nextInt(this.targetChance) != 0)
		{
			return false;
		}
		else if (this.theBase.getMode() != 3)
		{
			return false;
		}
		else if (this.theBase.isDowning())
		{
			return false;
		}
		else if (this.theBase.isComeing())
		{
			return false;
		}
		else if (this.theBase.isSleeping())
		{
			return false;
		}
		else if (this.theBase.isDistance())
		{
			return false;
		}
		else if (this.theBase.isWaiting())
		{
			return false;
		}
		else if (DocaSet.attack_EntityDoca.length == 0)
		{
			return false;
		}
		else
		{
			double d0 = this.getTargetDistance();

			List var5 = null;

			for (int i = 0; i < DocaSet.attack_EntityDoca.length; i++)
			{

				Class tmpAttackerClass = (Class)EntityList.stringToClassMapping.get(DocaSet.attack_EntityDoca[i]);
				if (tmpAttackerClass == null)
				{
					break;
				}

				var5 = this.taskOwner.worldObj.selectEntitiesWithinAABB(tmpAttackerClass, this.taskOwner.boundingBox.expand(d0, 4.0D, d0), this.field_82643_g);
				if (!var5.isEmpty())
				{
					break;
				}
			}

			if (var5.isEmpty())
			{
				return false;
			}

			Collections.sort(var5, this.theDocaEntityAIAttackMobTargetSortery);
			Iterator var2 = var5.iterator();

			while (var2.hasNext())
			{
				Entity var3 = (Entity)var2.next();
				EntityLivingBase var4 = (EntityLivingBase)var3;

				if (!this.theBase.isAttackTargetCheck(var4))
				{
					return false;
				}

				if (this.isSuitableTarget(var4, false))
				{
					this.targetEntity = var4;
					return true;
				}
			}
		}

		return false;
	}

	@Override
	public void startExecuting()
	{
		this.taskOwner.setAttackTarget(this.targetEntity);
		super.startExecuting();
	}
}
