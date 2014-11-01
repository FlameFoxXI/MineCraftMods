package mods.doca.entity.ai;

import mods.doca.entity.*;

import net.minecraft.block.*;
import net.minecraft.entity.ai.*;
import net.minecraft.world.*;

public class DocaEntityAIHealing extends EntityAIBase
{
	private final DocaEntityBase theBase;
	private final double speed;
	private int sitableBlockX = 0;
	private int sitableBlockY = 0;
	private int sitableBlockZ = 0;
	private int sitableHealTimer = 0;
	private int sitableTryTimer = 0;

	public DocaEntityAIHealing(DocaEntityBase par1DocaEntityBase, double par2)
	{
		this.theBase = par1DocaEntityBase;
		this.speed = par2;
		this.sitableHealTimer = 0;
		this.sitableTryTimer = 0;
		this.setMutexBits(3);
	}

	@Override
	public boolean shouldExecute()
	{
		if (!this.theBase.isTamed())
		{
			return false;
		}
		else if (!this.theBase.isHealing())
		{
			return false;
		}
		else if (!this.getNearbySitableBlockDistance())
		{
			return false;
		}
		else
		{
			if (this.theBase.getRNG().nextInt(60) != 0)
			{
				return false;
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
		this.theBase.setSleeping(this.theBase.isSitting());
		return this.sitableTryTimer <= 100 && this.theBase.isHealing() && this.isSittableBlock(this.theBase.worldObj, this.sitableBlockX, this.sitableBlockY, this.sitableBlockZ);
	}

	@Override
	public void startExecuting()
	{
		this.theBase.getNavigator().tryMoveToXYZ((double)((float)this.sitableBlockX) + 0.5D, (double)(this.sitableBlockY + 1), (double)((float)this.sitableBlockZ) + 0.5D, this.speed);
		this.theBase.func_70907_r().setSitting(false);
		this.theBase.setSleeping(false);
		this.sitableTryTimer = 0;
	}

	@Override
	public void resetTask()
	{
		this.theBase.setSitting(false);
		this.theBase.setSleeping(false);
		this.sitableTryTimer = 0;
	}

	@Override
	public void updateTask()
	{
		this.sitableHealTimer++;
		this.theBase.func_70907_r().setSitting(false);

		if (this.theBase.getDistanceSq((double)this.sitableBlockX, (double)(this.sitableBlockY + 1), (double)this.sitableBlockZ) > 1.0D)
		{
			this.theBase.setSitting(false);
			this.theBase.getNavigator().tryMoveToXYZ((double)((float)this.sitableBlockX) + 0.5D, (double)(this.sitableBlockY + 1), (double)((float)this.sitableBlockZ) + 0.5D, this.speed);
			++this.sitableTryTimer;
		}
		else if (!this.theBase.isSitting())
		{
			this.theBase.setSitting(true);
		}
		else
		{
			if (2000 < this.sitableHealTimer)
			{
				this.theBase.heal(1);
				this.sitableHealTimer = 0;
			}
		}
	}

	protected boolean getNearbySitableBlockDistance()
	{
		int var1 = (int)this.theBase.posY;
		double var2 = 2.147483647E9D;

		for (int var4 = (int)this.theBase.posX - 8; (double)var4 < this.theBase.posX + 8.0D; ++var4)
		{
			for (int var5 = (int)this.theBase.posZ - 8; (double)var5 < this.theBase.posZ + 8.0D; ++var5)
			{
				if (this.isSittableBlock(this.theBase.worldObj, var4, var1, var5) && this.theBase.worldObj.isAirBlock(var4, var1 + 1, var5))
				{
					double var6 = this.theBase.getDistanceSq((double)var4, (double)var1, (double)var5);

					if (var6 < var2)
					{
						this.sitableBlockX = var4;
						this.sitableBlockY = var1;
						this.sitableBlockZ = var5;
						var2 = var6;
					}
				}
			}
		}

		return var2 < 2.147483647E9D;
	}

	protected boolean isSittableBlock(World par1World, int par2, int par3, int par4)
	{
		int var5 = par1World.getBlockId(par2, par3, par4);
		int var6 = par1World.getBlockMetadata(par2, par3, par4);

		if (var5 == Block.bed.blockID && !BlockBed.isBlockHeadOfBed(var6))
		{
			return true;
		}

		return false;
	}
}
