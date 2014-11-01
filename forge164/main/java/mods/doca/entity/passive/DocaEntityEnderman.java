package mods.doca.entity.passive;

import cpw.mods.fml.relauncher.*;
import mods.doca.*;
import mods.doca.core.*;
import mods.doca.entity.*;
import mods.doca.entity.ai.*;
import mods.doca.entity.func.*;
import net.minecraft.block.*;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraft.world.*;

public class DocaEntityEnderman extends DocaEntityBase
{
	public static boolean[] carriableBlocks = new boolean[256];

	public DocaEntityEnderman(World par1World)
	{
		super(par1World);
		this.entityDocaString = DocaSet.PET_ENDER;
		this.sizeMaxTexture = DocaReg.getDocaTMax(DocaReg.getNameToType(this.entityDocaString));
		this.setSize(this.getWidthModelSize(), this.getHeightModelSizeDefault());
		this.moveSpeed = getMoveSpeed();
		this.getNavigator().setAvoidsWater(true);
		int countTask = 1;
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
		this.entityDocaString = DocaSet.PET_ENDER;
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
	*	SettingFunction																	*
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
		this.playSound("mob.wolf.step", 0.15F, 1.0F);
	}

	@Override
	protected String getLivingSound()
	{
		return "";
	}

	@Override
	protected String getHurtSound()
	{
		return "mob.endermen.hit";
	}

	@Override
	protected String getDeathSound()
	{
		return "mob.endermen.death";
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

		if (!this.worldObj.isRemote)// && this.worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing"))
		{
			int i;
			int j;
			int k;
			int l;

			if (this.getCarried() == 0)
			{
				if (this.rand.nextInt(20) == 0)
				{
					i = MathHelper.floor_double(this.posX - 2.0D + this.rand.nextDouble() * 4.0D);
					j = MathHelper.floor_double(this.posY + this.rand.nextDouble() * 3.0D);
					k = MathHelper.floor_double(this.posZ - 2.0D + this.rand.nextDouble() * 4.0D);
					l = this.worldObj.getBlockId(i, j, k);

					//                  if (carriableBlocks[l])
					if (l < 255)
					{
						this.setCarried(this.worldObj.getBlockId(i, j, k));
						this.setCarryingData(this.worldObj.getBlockMetadata(i, j, k));
					}
				}
			}
			else if (this.rand.nextInt(2000) == 0)
			{
				i = MathHelper.floor_double(this.posX - 1.0D + this.rand.nextDouble() * 2.0D);
				j = MathHelper.floor_double(this.posY + this.rand.nextDouble() * 2.0D);
				k = MathHelper.floor_double(this.posZ - 1.0D + this.rand.nextDouble() * 2.0D);
				l = this.worldObj.getBlockId(i, j, k);
				int i1 = this.worldObj.getBlockId(i, j - 1, k);

				if (l == 0 && i1 > 0 && Block.blocksList[i1].renderAsNormalBlock())
				{
					this.setCarried(0);
				}
			}
		}
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

	/********************************************************************************************
	*	EventCallFunction																		*
	********************************************************************************************/
	@Override
	public void handleHealthUpdate(byte par1)
	{
		super.handleHealthUpdate(par1);
	}

	@Override
	public EntityAgeable createChild(EntityAgeable par1EntityAgeable)
	{
		return this.spawnBabyAnimal(par1EntityAgeable);
	}

	@Override
	public EntityAgeable spawnBabyAnimal(EntityAgeable par1EntityAgeable)
	{
		DocaEntityEnderman var2 = new DocaEntityEnderman(this.worldObj);

		if (this.isTamed())
		{
			var2.setOwner(this.getOwnerName());
			var2.setTamed(true);
		}

		return var2;
	}
}
