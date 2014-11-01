package mods.doca.entity.passive;

import mods.doca.core.*;
import mods.doca.entity.*;
import mods.doca.entity.ai.*;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.Item;
import net.minecraft.world.*;

public class DocaEntityChicken extends DocaEntityBase
{
	public float field_70886_e;
	public float destPos;
	public float field_70884_g;
	public float field_70888_h;
	public float field_70889_i = 1.0F;

	public DocaEntityChicken(World par1World)
	{
		super(par1World);
		this.entityDocaString = DocaSet.PET_CHIKN;
		this.sizeMaxTexture = DocaReg.getDocaTMax(DocaReg.getNameToType(this.entityDocaString));
		this.setSize(this.getWidthModelSize(), this.getHeightModelSizeDefault());
		this.moveSpeed = getMoveSpeed();
		int countTask = 1;
		this.getNavigator().setAvoidsWater(true);
		this.tasks.addTask(countTask++, new EntityAISwimming(this));
		this.tasks.addTask(countTask++, this.aiSit);
		this.tasks.addTask(countTask++, this.aiControlledByPlayer);
		this.tasks.addTask(countTask++, new DocaEntityAITempt(this, this.moveSpeed, Item.carrotOnAStick, false));
		this.tasks.addTask(countTask++, new DocaEntityAILeapAtTarget(this, 0.4F));
		this.tasks.addTask(countTask++, new DocaEntityAIHome(this, (this.moveSpeed * 0.75D), DocaSet.func_MoveSquareToHomePoint, this.getMinMoveSize()));
		this.tasks.addTask(countTask++, new DocaEntityAIHealing(this, (this.moveSpeed * 0.5D)));
		this.tasks.addTask(countTask++, new DocaEntityAIDistance(this, EntityPlayer.class, 4.0F, this.moveSpeed));
		this.tasks.addTask(countTask++, new DocaEntityAICome(this, this.moveSpeed, 4.0F, 2.0F));
		this.tasks.addTask(countTask++, new DocaEntityAIAttack(this, this.moveSpeed, true));
		this.tasks.addTask(countTask++, new DocaEntityAIPickUp(this, this.moveSpeed, 10.0F, 5.0F));
		this.tasks.addTask(countTask++, new DocaEntityAIFollowOwner(this, this.moveSpeed, 10.0F, 5.0F));
		this.tasks.addTask(countTask++, new EntityAIMate(this, this.moveSpeed));
		this.tasks.addTask(countTask++, new DocaEntityAIWander(this, (this.moveSpeed * 0.75D)));
		this.tasks.addTask(countTask++, new DocaEntityAIBeg(this, this.moveSpeed, 20.0F));
		this.tasks.addTask(countTask++, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
		this.tasks.addTask(countTask, new EntityAILookIdle(this));
		this.targetTasks.addTask(1, new DocaEntityAIOwnerHurtByTarget(this));
		this.targetTasks.addTask(2, new DocaEntityAIOwnerHurtTarget(this));
		this.targetTasks.addTask(3, new DocaEntityAIHurtByTarget(this, true));
		this.targetTasks.addTask(4, new DocaEntityAIAttackMob(this, 30, false));
	}

	@Override
	protected void entityInit()
	{
		super.entityInit();
		this.entityDocaString = DocaSet.PET_CHIKN;
		this.sizeMaxTexture = DocaReg.getDocaTMax(DocaReg.getNameToType(this.entityDocaString));
		this.setSize(this.getWidthModelSize(), this.getHeightModelSizeDefault());
		this.moveSpeed = getMoveSpeed();
	}

	@Override
	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setAttribute(this.setMoveSpeed());
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setAttribute(this.getDocaMobMaxHealth());
	}

	/********************************************************************************************
	*	SettingFunction																			*
	********************************************************************************************/
	@Override
	public boolean isAIEnabled()
	{
		return true;
	}

	@Override
	protected void fall(float par1) {}

	@Override
	protected void playStepSound(int par1, int par2, int par3, int par4)
	{
		this.playSound("mob.chicken.step", 0.15F, 1.0F);
	}

	@Override
	protected String getLivingSound()
	{
		return "mob.chicken.say";
	}

	@Override
	protected String getHurtSound()
	{
		return "mob.chicken.hurt";
	}

	@Override
	protected String getDeathSound()
	{
		return "mob.chicken.hurt";
	}

	@Override
	protected int getDropItemId()
	{
		return -1;
	}

	@Override
	protected boolean canDespawn()
	{
		return !this.isTamed();
	}

	@Override
	protected float getSoundVolume()
	{
		return 0.4F;
	}

	/********************************************************************************************
	*	TaskCallFunction																		*
	********************************************************************************************/
	@Override
	public void onLivingUpdate()
	{
		super.onLivingUpdate();
		this.field_70888_h = this.field_70886_e;
		this.field_70884_g = this.destPos;
		this.destPos = (float)((double)this.destPos + (double)(this.onGround ? -1 : 4) * 0.3D);

		if (this.destPos < 0.0F)
		{
			this.destPos = 0.0F;
		}

		if (this.destPos > 1.0F)
		{
			this.destPos = 1.0F;
		}

		if (!this.onGround && this.field_70889_i < 1.0F)
		{
			this.field_70889_i = 1.0F;
		}

		this.field_70889_i = (float)((double)this.field_70889_i * 0.9D);

		if (!this.onGround && this.motionY < 0.0D)
		{
			this.motionY *= 0.6D;
		}

		this.field_70886_e += this.field_70889_i * 2.0F;
	}

	@Override
	public void onUpdate()
	{
		super.onUpdate();
	}

	@Override
	public void updateAITick()
	{
		super.updateAITick();
	}

	@Override
	public EntityAgeable createChild(EntityAgeable par1EntityAgeable)
	{
		return this.spawnBabyAnimal(par1EntityAgeable);
	}

	@Override
	public EntityAgeable spawnBabyAnimal(EntityAgeable par1EntityAgeable)
	{
		DocaEntityChicken var2 = new DocaEntityChicken(this.worldObj);

		if (this.isTamed())
		{
			var2.setOwner(this.getOwnerName());
			var2.setTamed(true);
		}

		return var2;
	}
}
