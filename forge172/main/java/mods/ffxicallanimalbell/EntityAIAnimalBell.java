package mods.ffxicallanimalbell;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class EntityAIAnimalBell extends EntityAIBase
{
	private EntityCreature temptedEntity;
	private double speed;
	private double targetX;
	private double targetY;
	private double targetZ;
	private double rotationPitch;
	private double rotationYaw;
	private double dist;

	private EntityPlayer temptingPlayer;
	private int delayTemptCounter;
	private boolean isRunning;
	private Item breedingFood;
	private boolean scaredByPlayerMovement;
	private boolean buffAvoidsWater;
	private int delay;

	public EntityAIAnimalBell(EntityCreature creature, double speed, Item item, boolean scared, double dist, int delay)
	{
		this.temptedEntity = creature;
		this.speed = speed;
		this.breedingFood = item;
		this.scaredByPlayerMovement = scared;
		this.dist = dist;
		this.delay = delay;
		this.setMutexBits(3);	
	}

	@Override
	public boolean shouldExecute()
	{
		if (this.delayTemptCounter > 0)
		{
			--this.delayTemptCounter;
			return false;
		}
		else
		{
			this.temptingPlayer = this.temptedEntity.worldObj.getClosestPlayerToEntity(this.temptedEntity, this.dist);

			if (this.temptingPlayer == null)
			{
				return false;
			}
			else
			{
				ItemStack itemstack = this.temptingPlayer.getCurrentEquippedItem();
				return itemstack == null ? false : itemstack.getItem() == this.breedingFood;
			}
		}
	}

	@Override
	public boolean continueExecuting()
	{
		if (this.scaredByPlayerMovement)
		{
			if (this.temptedEntity.getDistanceSqToEntity(this.temptingPlayer) < 64.0D)
			{
				if (this.temptingPlayer.getDistanceSq(this.targetX, this.targetY, this.targetZ) > 0.010000000000000002D)
				{
					return false;
				}

				if (Math.abs((double)this.temptingPlayer.rotationPitch - this.rotationPitch) > 5.0D || Math.abs((double)this.temptingPlayer.rotationYaw - this.rotationYaw) > 5.0D)
				{
					return false;
				}
			}
			else
			{
				this.targetX = this.temptingPlayer.posX;
				this.targetY = this.temptingPlayer.posY;
				this.targetZ = this.temptingPlayer.posZ;
			}

			this.rotationPitch = (double)this.temptingPlayer.rotationPitch;
			this.rotationYaw = (double)this.temptingPlayer.rotationYaw;
		}

		return this.shouldExecute();
	}

	@Override
	public void startExecuting()
	{
		this.targetX = this.temptingPlayer.posX;
		this.targetY = this.temptingPlayer.posY;
		this.targetZ = this.temptingPlayer.posZ;
		this.isRunning = true;
		this.buffAvoidsWater = this.temptedEntity.getNavigator().getAvoidsWater();
		this.temptedEntity.getNavigator().setAvoidsWater(false);
	}

	@Override
	public void resetTask()
	{
		this.temptingPlayer = null;
		this.temptedEntity.getNavigator().clearPathEntity();
		this.delayTemptCounter = this.delay;
		this.isRunning = false;
		this.temptedEntity.getNavigator().setAvoidsWater(this.buffAvoidsWater);
	}

	@Override
	public void updateTask()
	{
		this.temptedEntity.getLookHelper().setLookPositionWithEntity(this.temptingPlayer, 30.0F, (float)this.temptedEntity.getVerticalFaceSpeed());

		if (this.temptedEntity.getDistanceSqToEntity(this.temptingPlayer) < 6.25D)
		{
			this.temptedEntity.getNavigator().clearPathEntity();
		}
		else
		{
			this.temptedEntity.getNavigator().tryMoveToEntityLiving(this.temptingPlayer, this.speed);
		}
	}

	public boolean isRunning()
	{
		return this.isRunning;
	}
}
