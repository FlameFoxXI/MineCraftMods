package mods.doca.entity.func;
import java.util.ArrayList;
import java.util.Random;

import cpw.mods.fml.relauncher.*;

import net.minecraft.block.Block;
import net.minecraft.item.*;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.world.World;
import mods.doca.core.*;
import mods.doca.entity.*;

public class DocaFuncFoodsEeat
{

	public DocaFuncFoodsEeat()
	{
	}

	public boolean onLivingUpdate(DocaEntityBase theBase)
	{
		boolean tmpReturn = false;
		if (!theBase.worldObj.isRemote && theBase.getMode() != DocaReg.getModeNameToNo("Normal") && !theBase.isSitting() && theBase.getLife() < (theBase.getMaxHealth() / 4))
		{
			ItemStack itemStack = null;
			int getFoodsItemTablePointer = 0;

			for (int i = 0; i <  theBase.inventory.getFoodsItemSize(); i++)
			{
				if (theBase.inventory.getFoodsItem(i) != null && theBase.inventory.getFoodsItem(i).getItem() instanceof ItemFood)
				{
					itemStack = theBase.inventory.getFoodsItem(i);
					getFoodsItemTablePointer = i;
					break;
				}
			}

			if (itemStack != null && getFoodsItemTablePointer < theBase.inventory.getFoodsItemSize())
			{
				--theBase.inventory.getFoodsItem(getFoodsItemTablePointer).stackSize;
				theBase.heal((float)((ItemFood)DocaTools.newItem(itemStack)).getHealAmount());
				if (itemStack.stackSize <= 0)
				{
					theBase.inventory.setInventorySlotContents(theBase.inventory.foodsItem[getFoodsItemTablePointer], (ItemStack)null);
				}
				tmpReturn = true;
			}
		}
		return tmpReturn;
	}
}