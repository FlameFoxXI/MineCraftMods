package mods.doca.item;

import mods.doca.core.*;
import mods.doca.entity.*;

import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.projectile.*;
import net.minecraft.nbt.*;
import net.minecraft.util.*;
import net.minecraft.world.*;

public class DocaEntityCarrierBag extends EntityThrowable
{
	DocaEntityBase theBase;
	String petTypeString;

	public DocaEntityCarrierBag(World par1World)
	{
		super(par1World);
	}

	public DocaEntityCarrierBag(World par1World, EntityPlayer par3EntityPlayer, DocaEntityBase par3DocaEntityBase, String par4)
	{
		super(par1World, par3EntityPlayer);
		this.theBase = par3DocaEntityBase;
		this.petTypeString = par4;
	}

	public DocaEntityCarrierBag(World par1World, double par2, double par3, double par4)
	{
		super(par1World, par2, par3, par4);
	}

	@Override
	protected void onImpact(MovingObjectPosition par1MovingObjectPosition)
	{
		if (!this.worldObj.isRemote)
		{
			if (this.theBase != null && !this.petTypeString.equalsIgnoreCase(""))
			{
				this.theBase.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, 0.0F);

				for (int i = 0; i < DocaReg.getSettingsMax(); i++)
				{
					if (DocaReg.getTypeToName(i).equalsIgnoreCase(this.petTypeString) && DocaReg.getUse(i))
					{
						DocaEntityBase tmpEntity = DocaTools.createEntity(DocaReg.getNameToType(this.petTypeString), this.worldObj);
						tmpEntity.setTamed(true);
						tmpEntity.setAngry(this.theBase.isAngry());
						tmpEntity.setMode(this.theBase.getMode());
						tmpEntity.setIndexTexture(this.theBase.getIndexTexture());
						tmpEntity.setCollarColor(this.theBase.getCollarColor());
						tmpEntity.setName(this.theBase.getName());
						tmpEntity.setModelSize(this.theBase.getModelSize());
						tmpEntity.setOwner(this.theBase.getOwnerName());
						tmpEntity.setGrowingAge(this.theBase.getGrowingAge());
						tmpEntity.setHealth(20);
						tmpEntity.setSubOwners(this.theBase.getSubOwners());
						tmpEntity.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, 0.0F);
						this.worldObj.setEntityState(this, (byte)7);
						this.worldObj.spawnEntityInWorld(tmpEntity);
						break;
					}
				}
			}
		}

		this.worldObj.spawnParticle("snowballpoof", this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D);

		if (!this.worldObj.isRemote)
		{
			this.setDead();
		}
	}
}
