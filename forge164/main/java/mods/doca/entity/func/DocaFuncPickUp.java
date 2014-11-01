package mods.doca.entity.func;
import java.util.List;
import java.util.Random;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemShears;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import mods.doca.core.*;
import mods.doca.entity.*;



public class DocaFuncPickUp
{
	private Random rand = new Random();

	public boolean onLivingUpdate(DocaEntityBase theBase)
	{
		boolean tmp = false;
		if (!theBase.worldObj.isRemote && theBase.getMode() != 0)
		{
			tmp = this.pickupItem(theBase);
		}
		return tmp;
	}

	public boolean pickupItem(DocaEntityBase theBase)
	{
		if (theBase.getLife() > 1.0F && DocaSet.func_ItemPickUpON && !DocaSet.copyToInventoryCheck & !theBase.isWaiting() & !theBase.isSitting())
		{

			List tmplist = theBase.worldObj.getEntitiesWithinAABB(EntityItem.class, theBase.boundingBox.expand(1.0D, 0.0D, 1.0D));

			if (tmplist != null)
			{
				for (int i = 0; i < tmplist.size(); i++)
				{
					Entity tempEntity = (Entity)tmplist.get(i);

					if (tempEntity.isDead)
					{
						continue;
					}

					EntityItem tmpEntityItem = (EntityItem)tempEntity;

					if (tmpEntityItem.delayBeforeCanPickup != 0)
					{
						continue;
					}

					if (theBase.inventory.getFirstEmptyStack() == -1)
					{
						theBase.theInventoryFull = true;
						continue;
					}

					theBase.theInventoryFull = false;
					
					if(DocaSet.func_ItemPickUpOffEquipItem)
					{
						if (tmpEntityItem.getEntityItem().getItem() instanceof ItemSword
							|| tmpEntityItem.getEntityItem().getItem() instanceof ItemArmor
							|| tmpEntityItem.getEntityItem().getItem() instanceof ItemTool //ItemAxe,ItemPickaxe,ItemSpade
							|| tmpEntityItem.getEntityItem().getItem() instanceof ItemBow
							|| tmpEntityItem.getEntityItem().getItem() instanceof ItemHoe
							|| tmpEntityItem.getEntityItem().getItem() instanceof ItemShears)
						{
							continue;
						}
					}
					
					if(DocaSet.func_ItemPickUpOffItemID)
					{
						if (!DocaSet.func_ItemPickUpOffItemIDsDoca.isEmpty())
						{
							boolean check = false;
							for (Integer z : DocaSet.func_ItemPickUpOffItemIDsDoca)
							{
								if (tmpEntityItem.getEntityItem().getItem().itemID == z)
								{
									check = true;
									break;
								}
							}
							if (check)
							{
								continue;
							}
						}
					}
					
					if (!theBase.inventory.addItemStackToInventory(tmpEntityItem.getEntityItem()))
					{
						continue;
					}

					theBase.worldObj.playSoundAtEntity(tempEntity, "random.pop", 0.2F, ((rand.nextFloat() - rand.nextFloat()) * 0.7F + 1.0F) * 2.0F);

					if (tmpEntityItem.getEntityItem().stackSize <= 0)
					{
						tmpEntityItem.setDead();
						return true;
					}
				}
			}
		}
		return false;
	}
}