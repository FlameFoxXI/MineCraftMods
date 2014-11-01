package mods.doca.entity.ai;

import mods.doca.entity.*;

import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.util.*;

public class DocaEntityAIHome extends EntityAIBase
{
	private DocaEntityBase theBase;
	private EntityCreature entity;
	private double xPosition;
	private double yPosition;
	private double zPosition;
	private double speed;
	private int limit;
	private int move;

	public DocaEntityAIHome(DocaEntityBase par1EntityDoggy, double par2, int par3, int par4)
	{
		this.theBase = par1EntityDoggy;
		this.entity = par1EntityDoggy;
		this.speed = par2;
		this.limit = par3;
		this.move = par4;
		this.setMutexBits(3);
	}

	@Override
	public boolean shouldExecute()
	{
		if (this.entity.getRNG().nextInt(100) != 0)
		{
			return false;
		}
		else if (this.theBase.isSitting())
		{
			return false;
		}
		else
		{
			if (this.theBase.isWaiting())
			{
				Vec3 var1 = RandomPositionGenerator.findRandomTarget(this.entity, this.move, 2);

				if (var1 == null)
				{
					return false;
				}
				else
				{
					this.xPosition = var1.xCoord;
					this.yPosition = var1.yCoord;
					this.zPosition = var1.zCoord;
					return true;
				}
			}
			else
			{
				return false;
			}
		}
	}

	@Override
	public boolean continueExecuting()
	{
		return !this.entity.getNavigator().noPath();
	}

	@Override
	public void startExecuting()
	{
		if (this.execPosAround(this.xPosition, (double)this.theBase.getHomePoint().posX, this.limit)
				|| this.execPosAround(this.yPosition, (double)this.theBase.getHomePoint().posY, (this.limit / 3) + 2)
				|| this.execPosAround(this.zPosition, (double)this.theBase.getHomePoint().posZ, this.limit))
		{
			this.xPosition = (double)this.theBase.getHomePoint().posX;
			this.yPosition = (double)this.theBase.getHomePoint().posY;
			this.zPosition = (double)this.theBase.getHomePoint().posZ;
		}

		this.entity.getNavigator().tryMoveToXYZ(this.xPosition, this.yPosition, this.zPosition, this.speed);
	}

	public boolean execPosAround(double ver1, double ver2, double ver3)
	{
		if (ver1 > (ver2 - ver3))
		{
			if (ver1 < (ver2 + ver3))
			{
				return false;
			}
		}

		return true;
	}
}