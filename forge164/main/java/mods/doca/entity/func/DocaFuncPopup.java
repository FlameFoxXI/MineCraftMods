package mods.doca.entity.func;
import java.util.Random;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import mods.doca.core.*;
import mods.doca.entity.*;



public class DocaFuncPopup
{
	private Random rand = new Random();
	private int baseItemPopTimer;

	public DocaFuncPopup()
	{
		this.baseItemPopTimer = DocaSet.maxItemPopTimer + this.rand.nextInt(200);
	}

	public boolean onLivingUpdate(DocaEntityBase theBase)
	{
		if (this.baseItemPopTimer == 0)
		{
			this.baseItemPopTimer = DocaSet.maxItemPopTimer + this.rand.nextInt(200);

			if(theBase.getMode() != 0)
			{
				this.funcPopup(theBase);
				return true;
			}
		}
		else
		{
			if (theBase.getLife() > 1.0F)
			{
				this.baseItemPopTimer--;
			}
		}
		return false;
	}

	public boolean funcPopup(DocaEntityBase theBase)
	{
		if (DocaSet.func_ItemPopUpON && !theBase.worldObj.isRemote && theBase.isTamed())
		{
			int itemPopSize = 1;
			int sizeLength = 0;
			Item itemIdset = null;

			theBase.funcEmotion.setEmotionLiving(theBase, DocaFuncEmotion.EMOTION_HAPPY);

			switch (theBase.getMode())
			{
			case 1:
				sizeLength = DocaSet.listToItemPop1.length;
				itemIdset = DocaSet.listToItemPop1[this.rand.nextInt(sizeLength)];
				break;

			case 2:
				sizeLength = DocaSet.listToItemPop2.length;
				itemIdset = DocaSet.listToItemPop2[this.rand.nextInt(sizeLength)];
				break;

			case 3:
				sizeLength = DocaSet.listToItemPop3.length;
				itemIdset = DocaSet.listToItemPop3[this.rand.nextInt(sizeLength)];
				break;

			default:
				return false;
			}

			if (itemIdset != null)
			{
				ItemStack tmpItemStack = new ItemStack(itemIdset, this.rand.nextInt(2) + 1);

				if (theBase.inventory.getFirstEmptyStack() != -1)
				{
					theBase.inventory.addItemStackToInventory(tmpItemStack);
				}
				else
				{
					theBase.entityDropItem(tmpItemStack, 0.0F);
				}
				return true;
			}
		}
		return false;
	}
}