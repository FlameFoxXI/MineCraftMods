package mods.doca.entity.ai;

import mods.doca.entity.*;

import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.util.*;

public class DocaEntityAIWander extends EntityAIBase
{
	private DocaEntityBase thtBase;
	private EntityCreature entity;
	private double xPosition;
	private double yPosition;
	private double zPosition;
	private double speed;

	public DocaEntityAIWander(DocaEntityBase par1DocaEntityBase, double  par2)
	{
		this.thtBase = par1DocaEntityBase;
		this.entity = par1DocaEntityBase;
		this.speed = par2;
		this.setMutexBits(1);
	}

	@Override
	public boolean shouldExecute()
	{
		if (this.entity.getAge() >= 100)
		{
			return false;
		}
		else if (this.entity.getRNG().nextInt(120) != 0)
		{
			return false;
		}
		else if (this.thtBase.isWaiting())
		{
			return false;
		}
		else
		{
			Vec3 var1 = RandomPositionGenerator.findRandomTarget(this.entity, 10, 7);

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
	}

	@Override
	public boolean continueExecuting()
	{
		return !this.entity.getNavigator().noPath();
	}

	@Override
	public void startExecuting()
	{
		this.entity.getNavigator().tryMoveToXYZ(this.xPosition, this.yPosition, this.zPosition, this.speed);
//		this.entity.getLookHelper().setLookPosition(this.xPosition, this.yPosition, this.zPosition, 10.0F, (float)this.entity.getVerticalFaceSpeed());
	}
}
