package mods.doca.entity;

import mods.doca.core.DocaReg;
import mods.doca.core.DocaSet;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class DocaEntityBiped extends DocaEntityBase implements IRangedAttackMob
{
	public DocaEntityBiped(World par1World)
	{
		super(par1World);
	}

	@Override
	protected void entityInit()
	{
		super.entityInit();
		this.attackerMode = getAttackerMode();
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.readEntityFromNBT(par1NBTTagCompound);
		this.setCurrentItemData(this.inventory.getCurrentItem());
	}

	@Override
	public void onLivingUpdate()
	{
		this.updateArmSwingProgress();

		super.onLivingUpdate();

		this.updateCurrentItem();

		if (!this.worldObj.isRemote)
		{
			this.attackerMode = getAttackerMode();
		}
	}

	@Override
	public void attackEntityWithRangedAttack(EntityLivingBase par1EntityLivingBase, float par2)
	{
		EntityArrow tmpEntityArrow = new EntityArrow(this.worldObj, this, par1EntityLivingBase, 1.6F, (float)(14 - this.worldObj.difficultySetting * 4));

		byte var3 = 0;

		if (this.worldObj.difficultySetting == 2)
		{
			var3 = 7;
		}
		else if (this.worldObj.difficultySetting == 3)
		{
			var3 = 15;
		}
		else
		{
			var3 = 5;
		}

		switch (this.getMode())
		{
		case 1:
			tmpEntityArrow.setFire(var3);
			break;

		case 2:
			tmpEntityArrow.setKnockbackStrength(2);
			break;

		case 3:
			tmpEntityArrow.setDamage(tmpEntityArrow.getDamage() + (double)var3 * 0.25D + 0.5D);
			break;

		default:
			break;
		}

		this.playSound("random.bow", 1.0F, 1.0F / (this.getRNG().nextFloat() * 0.4F + 0.8F));
		this.worldObj.spawnEntityInWorld(tmpEntityArrow);
	}

	@Override
	public ItemStack getHeldItem()
	{
		if (this.getCurrentItemData() == null)
		{
			return this.getDefaultHeldItem();
		}
		else
		{
			return this.getCurrentItemData();
		}
	}

	public String getAttackerMode()
	{
		ItemStack itemstack = this.getHeldItem();

		if (itemstack != null && itemstack.getItem() instanceof ItemBow)
		{
			return "range";
		}
		else
		{
			return "";
		}

	}

	public void updateCurrentItem()
	{
		if (this.getMode() == DocaReg.getModeNameToNo("Normal"))
		{
			this.setCurrentItemData(this.getDefaultHeldItem());
		}
		else
		{
			if (this.inventory.getCurrentItem() != null)
			{
				this.setCurrentItemData(this.inventory.getCurrentItem());
			}
		}
	}

	public ItemStack getDefaultHeldItem()
	{
		return new ItemStack(Item.bow, 1);
	}
}
