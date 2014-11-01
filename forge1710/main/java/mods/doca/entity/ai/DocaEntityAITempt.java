package mods.doca.entity.ai;

import mods.doca.core.DocaTools;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class DocaEntityAITempt extends EntityAIBase
{
	private EntityCreature temptedEntity;
	private double field_75282_b;
	private double field_75283_c;
	private double field_75280_d;
	private double field_75281_e;
	private double field_75278_f;
	private double field_75279_g;
	private EntityPlayer temptingPlayer;
	private int delayTemptCounter;
	private boolean field_75287_j;
	private Item breedingFood;
	private boolean scaredByPlayerMovement;
	private boolean field_75286_m;

	public DocaEntityAITempt(EntityCreature par1EntityCreature, double par2, Item par4, boolean par5)
	{
		this.temptedEntity = par1EntityCreature;
		this.field_75282_b = par2;
		this.breedingFood = par4;
		this.scaredByPlayerMovement = par5;
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
			this.temptingPlayer = this.temptedEntity.worldObj.getClosestPlayerToEntity(this.temptedEntity, 10.0D);

			if (this.temptingPlayer == null)
			{
				return false;
			}
			else
			{
				ItemStack itemstack = this.temptingPlayer.getCurrentEquippedItem();
				return DocaTools.ofItem(itemstack, this.breedingFood);
			}
		}
	}

	@Override
	public boolean continueExecuting()
	{
		if (this.scaredByPlayerMovement)
		{
			if (this.temptedEntity.getDistanceSqToEntity(this.temptingPlayer) < 36.0D)
			{
				if (this.temptingPlayer.getDistanceSq(this.field_75283_c, this.field_75280_d, this.field_75281_e) > 0.010000000000000002D)
				{
					return false;
				}

				if (Math.abs((double)this.temptingPlayer.rotationPitch - this.field_75278_f) > 5.0D || Math.abs((double)this.temptingPlayer.rotationYaw - this.field_75279_g) > 5.0D)
				{
					return false;
				}
			}
			else
			{
				this.field_75283_c = this.temptingPlayer.posX;
				this.field_75280_d = this.temptingPlayer.posY;
				this.field_75281_e = this.temptingPlayer.posZ;
			}

			this.field_75278_f = (double)this.temptingPlayer.rotationPitch;
			this.field_75279_g = (double)this.temptingPlayer.rotationYaw;
		}

		return this.shouldExecute();
	}

	@Override
	public void startExecuting()
	{
		this.field_75283_c = this.temptingPlayer.posX;
		this.field_75280_d = this.temptingPlayer.posY;
		this.field_75281_e = this.temptingPlayer.posZ;
		this.field_75287_j = true;
		this.field_75286_m = this.temptedEntity.getNavigator().getAvoidsWater();
		this.temptedEntity.getNavigator().setAvoidsWater(false);
	}

	@Override
	public void resetTask()
	{
		this.temptingPlayer = null;
		this.temptedEntity.getNavigator().clearPathEntity();
		this.delayTemptCounter = 100;
		this.field_75287_j = false;
		this.temptedEntity.getNavigator().setAvoidsWater(this.field_75286_m);
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
			this.temptedEntity.getNavigator().tryMoveToEntityLiving(this.temptingPlayer, this.field_75282_b);
		}
	}

	public boolean func_75277_f()
	{
		return this.field_75287_j;
	}
}
