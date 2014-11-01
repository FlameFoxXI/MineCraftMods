package mods.doca.item;

import mods.doca.core.*;
import mods.doca.entity.*;

import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.projectile.*;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.world.*;

public class DocaEntityEgg extends EntityThrowable
{
	private EntityLivingBase theEntityLivingBase;
	private ItemStack theItemStack;

	public DocaEntityEgg(World par1World)
	{
		super(par1World);
	}

	public DocaEntityEgg(World par1World, EntityLivingBase par2EntityLivingBase, ItemStack par3ItemStack)
	{
		super(par1World, par2EntityLivingBase);
		this.theEntityLivingBase = par2EntityLivingBase;
		this.theItemStack = par3ItemStack;
	}

	public DocaEntityEgg(World par1World, double par2, double par3, double par4)
	{
		super(par1World, par2, par3, par4);
	}

	@Override
	protected void onImpact(MovingObjectPosition par1MovingObjectPosition)
	{
		if (!this.worldObj.isRemote)
		{
			if (!DocaReg.getTypeToName(this.theItemStack.getItemDamage()).equalsIgnoreCase(""))
			{

				DocaEntityBase tmpEntity = DocaTools.createEntity(this.theItemStack.getItemDamage(), this.worldObj);
				EntityPlayer tmpEntityPlayer = (EntityPlayer)this.theEntityLivingBase;
				tmpEntity.setTamed(true);
				tmpEntity.func_152115_b(tmpEntityPlayer.getUniqueID().toString());
				tmpEntity.setGrowingAge(-24000);
				tmpEntity.setHealth(20);
				tmpEntity.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, 0.0F);
				this.worldObj.setEntityState(this, (byte)7);
				this.worldObj.spawnEntityInWorld(tmpEntity);
			}
		}

		this.worldObj.spawnParticle("snowballpoof", this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D);

		if (!this.worldObj.isRemote)
		{
			this.setDead();
		}
	}
}