package mods.doca.entity.func;
import java.util.List;
import java.util.Random;

import cpw.mods.fml.relauncher.*;

import net.minecraft.entity.*;
import net.minecraft.entity.item.*;
import net.minecraft.item.*;
import net.minecraft.util.*;
import mods.doca.core.*;
import mods.doca.entity.*;



public class DocaFuncShaking
{
	private Random rand = new Random();
	private float field_70924_f;
	private float field_70926_e;
	private boolean field_70928_h;
	private boolean isShaking;
	private float timeIsShaking;
	private float prevTimeIsShaking;

	public DocaFuncShaking()
	{
		this.isShaking = false;
	}

	public boolean onLivingUpdate(DocaEntityBase theBase)
	{
		boolean tmp = false;
		if (!theBase.worldObj.isRemote && this.isShaking && !this.field_70928_h && !theBase.hasPath() && theBase.onGround)
		{
			this.field_70928_h = true;
			this.timeIsShaking = 0.0F;
			this.prevTimeIsShaking = 0.0F;
			theBase.worldObj.setEntityState(theBase, (byte)8);
			return true;
		}
		return tmp;
	}

	public void onUpdate(DocaEntityBase theBase)
	{
		this.field_70924_f = this.field_70926_e;

		if (theBase.func_70922_bv())
		{
			this.field_70926_e += (1.0F - this.field_70926_e) * 0.4F;
		}
		else
		{
			this.field_70926_e += (0.0F - this.field_70926_e) * 0.4F;
		}

		if (theBase.getUpToFire())
		{
			theBase.extinguish();
			this.isShaking = true;
			this.field_70928_h = false;
			this.timeIsShaking = 0.0F;
			this.prevTimeIsShaking = 0.0F;
			theBase.setUpToFire(0);
		}

		if (theBase.isWet())
		{
			theBase.extinguish();
			this.isShaking = true;
			this.field_70928_h = false;
			this.timeIsShaking = 0.0F;
			this.prevTimeIsShaking = 0.0F;
			theBase.setUpToFire(0);
		}
		else if ((this.isShaking || this.field_70928_h) && this.field_70928_h)
		{
			if (this.timeIsShaking == 0.0F)
			{
				//				this.playSound("mob.wolf.shake", this.getSoundVolume(), (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
				theBase.playSound("mob.wolf.shake", 0.4F, (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
			}

			this.prevTimeIsShaking = this.timeIsShaking;
			this.timeIsShaking += 0.05F;

			if (this.prevTimeIsShaking >= 2.0F)
			{
				this.isShaking = false;
				this.field_70928_h = false;
				this.prevTimeIsShaking = 0.0F;
				this.timeIsShaking = 0.0F;
			}

			if (this.timeIsShaking > 0.4F)
			{
				float var1 = (float)theBase.boundingBox.minY;
				int var2 = (int)(MathHelper.sin((this.timeIsShaking - 0.4F) * (float)Math.PI) * 7.0F);

				for (int var3 = 0; var3 < var2; ++var3)
				{
					float var4 = (this.rand.nextFloat() * 2.0F - 1.0F) * theBase.width * 0.5F;
					float var5 = (this.rand.nextFloat() * 2.0F - 1.0F) * theBase.width * 0.5F;
					theBase.worldObj.spawnParticle("splash", theBase.posX + (double)var4, (double)(var1 + 0.8F), theBase.posZ + (double)var5, theBase.motionX, theBase.motionY, theBase.motionZ);
				}
			}
		}
	}

	public void handleHealthUpdate(DocaEntityBase theBase)
	{
		this.field_70928_h = true;
		this.timeIsShaking = 0.0F;
		this.prevTimeIsShaking = 0.0F;
	}



	@SideOnly(Side.CLIENT)
	public boolean getShaking()
	{
		return this.isShaking;
	}

	@SideOnly(Side.CLIENT)
	public float getShadingWhileShaking(float par1)
	{
		return 0.75F + (this.prevTimeIsShaking + (this.timeIsShaking - this.prevTimeIsShaking) * par1) / 2.0F * 0.25F;
	}

	@SideOnly(Side.CLIENT)
	public float getShakeAngle(float par1, float par2)
	{
		float var3 = (this.prevTimeIsShaking + (this.timeIsShaking - this.prevTimeIsShaking) * par1 + par2) / 1.8F;

		if (var3 < 0.0F)
		{
			var3 = 0.0F;
		}
		else if (var3 > 1.0F)
		{
			var3 = 1.0F;
		}

		return MathHelper.sin(var3 * (float)Math.PI) * MathHelper.sin(var3 * (float)Math.PI * 11.0F) * 0.15F * (float)Math.PI;
	}

	@SideOnly(Side.CLIENT)
	public float getInterestedAngle(float par1)
	{
		return (this.field_70924_f + (this.field_70926_e - this.field_70924_f) * par1) * 0.15F * (float)Math.PI;
	}
}