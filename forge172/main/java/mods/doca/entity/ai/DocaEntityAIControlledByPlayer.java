package mods.doca.entity.ai;

import mods.doca.core.DocaTools;
import mods.doca.entity.DocaEntityBase;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathFinder;
import net.minecraft.pathfinding.PathPoint;
import net.minecraft.util.MathHelper;

public class DocaEntityAIControlledByPlayer extends EntityAIBase
{
	private final DocaEntityBase thisEntity;
	private final float maxSpeed;
	private float currentSpeed;
	private boolean speedBoosted;
	private int speedBoostTime;
	private int maxSpeedBoostTime;

	public DocaEntityAIControlledByPlayer(DocaEntityBase par1EntityLiving, float par2)
	{
		this.thisEntity = par1EntityLiving;
		this.maxSpeed = par2;
		this.setMutexBits(7);
	}

	@Override
	public void startExecuting()
	{
		this.currentSpeed = 0.0F;
	}

	@Override
	public void resetTask()
	{
		this.speedBoosted = false;
		this.currentSpeed = 0.0F;
	}

	@Override
	public boolean shouldExecute()
	{
		return this.thisEntity.isEntityAlive() && this.thisEntity.riddenByEntity != null && this.thisEntity.riddenByEntity instanceof EntityPlayer && (this.speedBoosted || this.thisEntity.canBeSteered());
	}

	@Override
	public void updateTask()
	{
		EntityPlayer entityplayer = (EntityPlayer)this.thisEntity.riddenByEntity;
		EntityCreature entitycreature = (EntityCreature)this.thisEntity;
		float f = MathHelper.wrapAngleTo180_float(entityplayer.rotationYaw - this.thisEntity.rotationYaw) * 0.5F;

		if (f > 5.0F)
		{
			f = 5.0F;
		}

		if (f < -5.0F)
		{
			f = -5.0F;
		}

		this.thisEntity.rotationYaw = MathHelper.wrapAngleTo180_float(this.thisEntity.rotationYaw + f);

		if (this.currentSpeed < this.maxSpeed)
		{
			this.currentSpeed += (this.maxSpeed - this.currentSpeed) * 0.01F;
		}

		if (this.currentSpeed > this.maxSpeed)
		{
			this.currentSpeed = this.maxSpeed;
		}

		int i = MathHelper.floor_double(this.thisEntity.posX);
		int j = MathHelper.floor_double(this.thisEntity.posY);
		int k = MathHelper.floor_double(this.thisEntity.posZ);
		float f1 = this.currentSpeed;

		if (this.speedBoosted)
		{
			if (this.speedBoostTime++ > this.maxSpeedBoostTime)
			{
				this.speedBoosted = false;
			}

			f1 += f1 * 1.15F * MathHelper.sin((float)this.speedBoostTime / (float)this.maxSpeedBoostTime * (float)Math.PI);
		}

		float f2 = 0.91F;

		if (this.thisEntity.onGround)
		{
//			f2 = 0.54600006F;
//			int l = this.thisEntity.worldObj.getBlockId(MathHelper.floor_float((float)i), MathHelper.floor_float((float)j) - 1, MathHelper.floor_float((float)k));

//			if (l > 0)
//			{
//				f2 = Block.blocksList[l].slipperiness * 0.91F;
//			}
			f2 = this.thisEntity.worldObj.getBlock(MathHelper.floor_float(i), MathHelper.floor_float(j) - 1, MathHelper.floor_float(k)).slipperiness * 0.91F;
		}

		float f3 = 0.16277136F / (f2 * f2 * f2);
		float f4 = MathHelper.sin(entitycreature.rotationYaw * (float)Math.PI / 180.0F);
		float f5 = MathHelper.cos(entitycreature.rotationYaw * (float)Math.PI / 180.0F);
		float f6 = entitycreature.getAIMoveSpeed() * f3;
		float f7 = Math.max(f1, 1.0F);
		f7 = f6 / f7;
		float f8 = f1 * f7;
		float f9 = -(f8 * f4);
		float f10 = f8 * f5;

		if (MathHelper.abs(f9) > MathHelper.abs(f10))
		{
			if (f9 < 0.0F)
			{
				f9 -= this.thisEntity.width / 2.0F;
			}

			if (f9 > 0.0F)
			{
				f9 += this.thisEntity.width / 2.0F;
			}

			f10 = 0.0F;
		}
		else
		{
			f9 = 0.0F;

			if (f10 < 0.0F)
			{
				f10 -= this.thisEntity.width / 2.0F;
			}

			if (f10 > 0.0F)
			{
				f10 += this.thisEntity.width / 2.0F;
			}
		}

		int i1 = MathHelper.floor_double(this.thisEntity.posX + (double)f9);
		int j1 = MathHelper.floor_double(this.thisEntity.posZ + (double)f10);
		PathPoint pathpoint = new PathPoint(MathHelper.floor_float(this.thisEntity.width + 1.0F), MathHelper.floor_float(this.thisEntity.height + entityplayer.height + 1.0F), MathHelper.floor_float(this.thisEntity.width + 1.0F));

		if (i != i1 || k != j1)
		{
			Block block = thisEntity.worldObj.getBlock(i, j, k);
			boolean flag = !this.func_151498_a(block) && (block.getMaterial() != Material.air || !this.func_151498_a(thisEntity.worldObj.getBlock(i, j - 1, k)));

			if (!flag && PathFinder.func_82565_a(this.thisEntity, i1, j, j1, pathpoint, false, false, true) == 0 && PathFinder.func_82565_a(this.thisEntity, i, j + 1, k, pathpoint, false, false, true) == 1 && PathFinder.func_82565_a(this.thisEntity, i1, j + 1, j1, pathpoint, false, false, true) == 1)
			{
				entitycreature.getJumpHelper().setJumping();
			}
		}

		if (!entityplayer.capabilities.isCreativeMode && this.currentSpeed >= this.maxSpeed * 0.5F && this.thisEntity.getRNG().nextFloat() < 0.006F && !this.speedBoosted)
		{
			ItemStack itemstack = entityplayer.getHeldItem();

//			if (DocaTools.ofItem(itemstack, Item.carrotOnAStick))
			if (DocaTools.ofItem(itemstack, Items.carrot_on_a_stick))
			{
				itemstack.damageItem(1, entityplayer);

				if (itemstack.stackSize == 0)
				{
//					ItemStack itemstack1 = new ItemStack(Item.fishingRod);
					ItemStack itemstack1 = new ItemStack(Items.fishing_rod);
					itemstack1.setTagCompound(itemstack.stackTagCompound);
					entityplayer.inventory.mainInventory[entityplayer.inventory.currentItem] = itemstack1;
				}
			}
		}

		this.thisEntity.moveEntityWithHeading(0.0F, f1);
	}

//	private boolean func_98216_b(int par1)
//	{
//		return Block.blocksList[par1] != null && (Block.blocksList[par1].getRenderType() == 10 || Block.blocksList[par1] instanceof BlockHalfSlab);
//	}

	private boolean func_151498_a(Block par1)
	{
		return par1.getRenderType() == 10 || (par1 instanceof BlockSlab);
	}

	public boolean isSpeedBoosted()
	{
		return this.speedBoosted;
	}

	public void boostSpeed()
	{
		this.speedBoosted = true;
		this.speedBoostTime = 0;
		this.maxSpeedBoostTime = this.thisEntity.getRNG().nextInt(841) + 140;
	}

	public boolean isControlledByPlayer()
	{
		return !this.isSpeedBoosted() && this.currentSpeed > this.maxSpeed * 0.3F;
	}
}
