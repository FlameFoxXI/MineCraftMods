package mods.doca.entity.ai;

import mods.doca.entity.*;

import net.minecraft.block.*;
import net.minecraft.entity.ai.*;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.*;
import net.minecraft.world.*;

public class DocaEntityAIKittySit extends EntityAIBase
{
	private final DocaEntityBase theBase;
	private final float field_75404_b;
	private int currentTick = 0;
	private int field_75402_d = 0;
	private int maxSittingTicks = 0;
	private int sitableBlockX = 0;
	private int sitableBlockY = 0;
	private int sitableBlockZ = 0;

	public DocaEntityAIKittySit(DocaEntityBase par1DocaEntityBase, float par2)
	{
		this.theBase = par1DocaEntityBase;
		this.field_75404_b = par2;
		this.setMutexBits(5);
	}

	@Override
	public boolean shouldExecute()
	{
		if (!this.theBase.isTamed())
		{
			return false;
		}
		else if (this.theBase.isSitting())
		{
			return false;
		}
		else if (this.theBase.isHealing())
		{
			return false;
		}
		else
		{
			return this.theBase.getRNG().nextDouble() <= 0.006500000134110451D && this.getNearbySitableBlockDistance();
		}
	}

	@Override
	public boolean continueExecuting()
	{
		return this.currentTick <= this.maxSittingTicks && this.field_75402_d <= 60 && this.isSittableBlock(this.theBase.worldObj, this.sitableBlockX, this.sitableBlockY, this.sitableBlockZ);
	}

	@Override
	public void startExecuting()
	{
		this.theBase.getNavigator().tryMoveToXYZ((double)((float)this.sitableBlockX) + 0.5D, (double)(this.sitableBlockY + 1), (double)((float)this.sitableBlockZ) + 0.5D, this.field_75404_b);
		this.currentTick = 0;
		this.field_75402_d = 0;
		this.maxSittingTicks = this.theBase.getRNG().nextInt(this.theBase.getRNG().nextInt(1200) + 1200) + 1200;
		this.theBase.func_70907_r().setSitting(false);
	}

	@Override
	public void resetTask()
	{
		this.theBase.setSitting(false);
	}

	@Override
	public void updateTask()
	{
		++this.currentTick;
		this.theBase.func_70907_r().setSitting(false);

		if (this.theBase.getDistanceSq((double)this.sitableBlockX, (double)(this.sitableBlockY + 1), (double)this.sitableBlockZ) > 1.0D)
		{
			this.theBase.setSitting(false);
			this.theBase.getNavigator().tryMoveToXYZ((double)((float)this.sitableBlockX) + 0.5D, (double)(this.sitableBlockY + 1), (double)((float)this.sitableBlockZ) + 0.5D, this.field_75404_b);
			++this.field_75402_d;
		}
		else if (!this.theBase.isSitting())
		{
			this.theBase.setSitting(true);
		}
		else
		{
			--this.field_75402_d;
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
		Block block = par1World.getBlock(par2, par3, par4);
		int var6 = par1World.getBlockMetadata(par2, par3, par4);

		if (block == Blocks.chest)
		{
        	TileEntityChest var7 = (TileEntityChest)par1World.getTileEntity(par2, par3, par4);

			if (var7.numPlayersUsing < 1)
			{
				return true;
			}
		}
		else
		{
			if (block == Blocks.lit_furnace)
			{
				return true;
			}

			if (block == Blocks.bed && !BlockBed.isBlockHeadOfBed(var6))
			{
				return true;
			}
		}

		return false;
	}
}
