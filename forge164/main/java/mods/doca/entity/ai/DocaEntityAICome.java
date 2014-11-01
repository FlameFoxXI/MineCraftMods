package mods.doca.entity.ai;

import mods.doca.entity.*;

import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.pathfinding.*;
import net.minecraft.util.*;
import net.minecraft.world.*;

public class DocaEntityAICome extends EntityAIBase
{
	private DocaEntityBase	theBase;
	private EntityLivingBase theOwner;
	World theWorld;
	private double speed;
	private PathNavigate petPathfinder;
	private int field_75343_h;
	float maxDist;
	float minDist;
	private boolean field_75344_i;
	int status;

	public DocaEntityAICome(DocaEntityBase par1DocaEntityBase, double par2, float par3, float par4)
	{
		this.theBase = par1DocaEntityBase;
		this.theWorld = par1DocaEntityBase.worldObj;
		this.speed = par2;
		this.petPathfinder = par1DocaEntityBase.getNavigator();
		this.minDist = par3;
		this.maxDist = par4;
		this.setMutexBits(3);
	}

	@Override
	public boolean shouldExecute()
	{
		EntityLivingBase var1 = this.theBase.func_130012_q();

		if (var1 == null)
		{
			return false;
		}
		else if (!this.theBase.isComeing())
		{
			return false;
		}
		else if (this.theBase.getDistanceSqToEntity(var1) < (double)(this.minDist * this.minDist))
		{
			return false;
		}
		else
		{
			this.theOwner = var1;
			return true;
		}
	}

	@Override
	public boolean continueExecuting()
	{
		return !this.petPathfinder.noPath() && this.theBase.getDistanceSqToEntity(this.theOwner) > (double)(this.maxDist * this.maxDist) && !this.theBase.isSitting();
	}

	@Override
	public void startExecuting()
	{
		this.field_75343_h = 0;
		this.field_75344_i = this.theBase.getNavigator().getAvoidsWater();
		this.theBase.getNavigator().setAvoidsWater(false);
	}

	@Override
	public void resetTask()
	{
		this.theOwner = null;
		this.petPathfinder.clearPathEntity();
		this.theBase.getNavigator().setAvoidsWater(this.field_75344_i);
	}

	@Override
	public void updateTask()
	{
		this.theBase.getLookHelper().setLookPositionWithEntity(this.theOwner, 10.0F, (float)this.theBase.getVerticalFaceSpeed());

		if (!this.theBase.isSitting())
		{
			if (--this.field_75343_h <= 0)
			{
				this.field_75343_h = 10;

				if (!this.petPathfinder.tryMoveToEntityLiving(this.theOwner, this.speed))
				{
					if (this.theBase.getDistanceSqToEntity(this.theOwner) >= 144.0D)
					{
						int var1 = MathHelper.floor_double(this.theOwner.posX) - 2;
						int var2 = MathHelper.floor_double(this.theOwner.posZ) - 2;
						int var3 = MathHelper.floor_double(this.theOwner.boundingBox.minY);

						for (int var4 = 0; var4 <= 4; ++var4)
						{
							for (int var5 = 0; var5 <= 4; ++var5)
							{
								if ((var4 < 1 || var5 < 1 || var4 > 3 || var5 > 3) && this.theWorld.doesBlockHaveSolidTopSurface(var1 + var4, var3 - 1, var2 + var5) && !this.theWorld.isBlockNormalCube(var1 + var4, var3, var2 + var5) && !this.theWorld.isBlockNormalCube(var1 + var4, var3 + 1, var2 + var5))
								{
									this.theBase.setLocationAndAngles((double)((float)(var1 + var4) + 0.5F), (double)var3, (double)((float)(var2 + var5) + 0.5F), this.theBase.rotationYaw, this.theBase.rotationPitch);
									this.petPathfinder.clearPathEntity();
									return;
								}
							}
						}
					}
				}
			}
		}
	}
}
