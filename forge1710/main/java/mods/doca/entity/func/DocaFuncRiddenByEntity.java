package mods.doca.entity.func;
import java.util.List;
import java.util.Random;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import mods.doca.core.*;
import mods.doca.entity.*;



public class DocaFuncRiddenByEntity
{
	private Random rand = new Random();

	public DocaFuncRiddenByEntity()
	{
	}

	public boolean canBeSteered(ItemStack itemstack, Item item)
	{
		return DocaTools.ofItem(itemstack, item);
	}

	public void interact(EntityPlayer player, DocaEntityBase theBase, Item item)
	{
		if (theBase.getLife() > 1.0F && !theBase.worldObj.isRemote)
		{
			if (DocaTools.ofItem(player.inventory.getCurrentItem(), item))
			{
				if ((theBase.riddenByEntity == null || theBase.riddenByEntity == player) && player.getUniqueID().toString().equalsIgnoreCase(theBase.func_152113_b())
						&& !theBase.isSitting() && !theBase.isComeing() && !theBase.isDistance() && !theBase.isDowning() && !theBase.isWaiting())
				{
					player.mountEntity(theBase);
				}
			}
		}
	}

	public void onLivingUpdate(DocaEntityBase theBase)
	{
		theBase.setRiden(false);
		if (theBase.riddenByEntity != null)
		{
			theBase.setRiden(true);
		}
	}



}