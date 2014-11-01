package mods.doca.entity.ai;

import mods.doca.*;
import mods.doca.core.*;
import mods.doca.entity.*;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.item.*;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemShears;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.pathfinding.*;
import net.minecraft.world.*;

import java.util.*;

public class DocaEntityAIPickUp extends EntityAIBase
{
	private DocaEntityBase theBase;
	World theWorld;
	private PathNavigate petPathfinder;
	private Entity theItem;
	private double xPosition;
	private double yPosition;
	private double zPosition;
	private double speed;

	public DocaEntityAIPickUp(DocaEntityBase par1DocaEntityBase, double par2, float par3, float par4)
	{
		this.theBase = par1DocaEntityBase;
		this.theWorld = par1DocaEntityBase.worldObj;
		this.petPathfinder = par1DocaEntityBase.getNavigator();
		this.speed = par2;
		this.setMutexBits(3);
	}

	@Override
	public boolean shouldExecute()
	{
//		EntityLivingBase var1 = this.theBase.func_130012_q();
		EntityLivingBase var1 = this.theBase.getOwner();
		theItem = null;

		if (this.theBase.getRNG().nextInt(20) != 0)
		{
			return false;
		}
		else if (this.theBase.isWaiting())
		{
			return false;
		}
		else if (this.theBase.isRiden())
		{
			return false;
		}
		else if (var1 == null)
		{
			return false;
		}
		else if (this.theBase.isSitting())
		{
			return false;
		}
		else if (!DocaSet.func_ItemPickUpON)
		{
			return false;
		}
		else if (this.theBase.getLife() <= 1.0F)
		{
			return false;
		}
		else if (this.theBase.getMode() == 0)
		{
			return false;
		}
		else
		{
			List tmplist = this.theWorld.getEntitiesWithinAABBExcludingEntity(this.theBase, this.theBase.boundingBox.expand((double)DocaSet.func_ItemPickUpWidely, 1.0D, (double)DocaSet.func_ItemPickUpWidely));

			if (tmplist != null)
			{
				for (int i = 0; i < tmplist.size(); i++)
				{
					Entity tmpEntity = (Entity)tmplist.get(i);

					if (!tmpEntity.isDead && (tmpEntity instanceof EntityItem))
					{
						EntityItem tmpEntityItem = (EntityItem)tmpEntity;

						boolean check1 = false;
						if(DocaSet.func_ItemPickUpOffEquipItem)
						{
							if (tmpEntityItem.getEntityItem().getItem() instanceof ItemSword
								|| tmpEntityItem.getEntityItem().getItem() instanceof ItemArmor
								|| tmpEntityItem.getEntityItem().getItem() instanceof ItemTool //ItemAxe,ItemPickaxe,ItemSpade
								|| tmpEntityItem.getEntityItem().getItem() instanceof ItemBow
								|| tmpEntityItem.getEntityItem().getItem() instanceof ItemHoe
								|| tmpEntityItem.getEntityItem().getItem() instanceof ItemShears)
							{
								check1 = true;
							}
						}
						
						boolean check = false;
						if(DocaSet.func_ItemPickUpOffItemID)
						{
							if (!DocaSet.func_ItemPickUpOffItemIDsDoca.isEmpty())
							{
								for (Integer z : DocaSet.func_ItemPickUpOffItemIDsDoca)
								{
									if (tmpEntityItem.getEntityItem().getItem() == Item.getItemById(z))
									{
										check = true;
										break;
									}
								}
							}
						}						
						
						if (!check && !check1)
						{
							this.theItem  = tmpEntity;
							xPosition = tmpEntity.posX;
							yPosition = tmpEntity.posY;
							zPosition = tmpEntity.posZ;
							return true;
						}
					}
				}
			}

			return false;
		}
	}

	@Override
	public boolean continueExecuting()
	{
		return !this.theBase.getNavigator().noPath();
	}

	@Override
	public void startExecuting()
	{
		this.theBase.getNavigator().tryMoveToXYZ(this.xPosition, this.yPosition, this.zPosition, this.speed);
	}
}
