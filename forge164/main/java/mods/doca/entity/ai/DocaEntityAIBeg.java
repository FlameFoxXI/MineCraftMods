package mods.doca.entity.ai;

import mods.doca.core.DocaTools;
import mods.doca.entity.*;

import net.minecraft.entity.ai.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.pathfinding.*;
import net.minecraft.util.*;
import net.minecraft.world.*;

public class DocaEntityAIBeg extends EntityAIBase
{
	private DocaEntityBase theBase;
	private EntityPlayer thePlayer;
	private World worldObject;
	private float minPlayerDistance;
	private int field_75384_e;

	private double speed;
	private PathNavigate petPathfinder;
	private int field_75343_h;

	public DocaEntityAIBeg(DocaEntityBase par1EntityDoggy, double par2, float par3)
	{
		this.theBase = par1EntityDoggy;
		this.worldObject = par1EntityDoggy.worldObj;
		this.minPlayerDistance = par3;
		this.speed = par2;
		this.petPathfinder = theBase.getNavigator();
		this.setMutexBits(2);
	}

	@Override
	public boolean shouldExecute()
	{
		this.thePlayer = this.worldObject.getClosestPlayerToEntity(this.theBase, (double)this.minPlayerDistance);

		if (this.theBase.isSleeping() || this.theBase.isRiden())
		{
			return false;
		}
		else
		{
			return this.thePlayer == null ? false : this.hasPlayerGotBoneInHand(this.thePlayer);
		}
	}

	@Override
	public boolean continueExecuting()
	{
		return !this.thePlayer.isEntityAlive() ? false : (this.theBase.getDistanceSqToEntity(this.thePlayer) > (double)(this.minPlayerDistance * this.minPlayerDistance) ? false : this.field_75384_e > 0 && this.hasPlayerGotBoneInHand(this.thePlayer));
	}

	@Override
	public void startExecuting()
	{
		this.theBase.func_70918_i(true);
		this.field_75384_e = 40 + this.theBase.getRNG().nextInt(40);
	}

	@Override
	public void resetTask()
	{
		this.theBase.func_70918_i(false);
		this.petPathfinder.clearPathEntity();
		this.thePlayer = null;
	}

	@Override
	public void updateTask()
	{
		if (!this.theBase.isSitting())
		{
			if (!this.theBase.isDowning() && !this.theBase.isComeing() && !this.theBase.isDistance() && !this.theBase.isWaiting())
			{
				if ( thePlayer.username.equalsIgnoreCase(this.theBase.getOwnerName())) {
					if (--this.field_75343_h <= 0)
					{
						this.field_75343_h = 10;

						if (!this.petPathfinder.tryMoveToEntityLiving(this.thePlayer, this.speed))
						{
							if (this.theBase.getDistanceSqToEntity(this.thePlayer) >= 144.0D)
							{
								int var1 = MathHelper.floor_double(this.thePlayer.posX) - 2;
								int var2 = MathHelper.floor_double(this.thePlayer.posZ) - 2;
								int var3 = MathHelper.floor_double(this.thePlayer.boundingBox.minY);

								for (int var4 = 0; var4 <= 4; ++var4)
								{
									for (int var5 = 0; var5 <= 4; ++var5)
									{
										if ((var4 < 1 || var5 < 1 || var4 > 3 || var5 > 3) && this.worldObject.doesBlockHaveSolidTopSurface(var1 + var4, var3 - 1, var2 + var5) && !this.worldObject.isBlockNormalCube(var1 + var4, var3, var2 + var5) && !this.worldObject.isBlockNormalCube(var1 + var4, var3 + 1, var2 + var5))
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
		this.theBase.getLookHelper().setLookPosition(this.thePlayer.posX, this.thePlayer.posY + (double)this.thePlayer.getEyeHeight(), this.thePlayer.posZ, 10.0F, (float)this.theBase.getVerticalFaceSpeed());
		--this.field_75384_e;
	}

	private boolean hasPlayerGotBoneInHand(EntityPlayer par1EntityPlayer)
	{
		ItemStack var2 = par1EntityPlayer.inventory.getCurrentItem();
		return var2 == null ? false : (!this.theBase.isTamed() && DocaTools.ofItem(var2, Item.bone) ? true : this.theBase.isFeedItem(var2));
	}
}
