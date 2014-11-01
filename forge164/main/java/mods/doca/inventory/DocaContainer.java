package mods.doca.inventory;

import java.util.*;

import mods.doca.entity.DocaEntityBase;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.*;
import net.minecraft.item.*;

public class DocaContainer extends Container
{
	private static final int moveBoxPoint = 18;
	private DocaInventory tmpInventory;
	private DocaEntityBase theBase;

	public DocaContainer(EntityPlayer par0EntityPlayer, DocaEntityBase par2DocaEntityBase)
	{
		this.tmpInventory = par2DocaEntityBase.inventory;
		this.theBase = par2DocaEntityBase;
		par2DocaEntityBase.inventory.openChest();
		int var4;
		int var5;

		for (var4 = 0; var4 < DocaInventory.HotBarLine; ++var4)
		{
			for (var5 = 0; var5 < DocaInventory.HotbarSize; ++var5)
			{
				this.addSlotToContainer(new Slot(par2DocaEntityBase.inventory, var5 + var4 * DocaInventory.HotbarSize, 67 + var5 * moveBoxPoint, 18 + var4 * moveBoxPoint));
			}
		}

		for (var4 = 0; var4 < DocaInventory.CurrentInventorySize; ++var4)
		{
			this.addSlotToContainer(new DocaSlotSword(par2DocaEntityBase, par2DocaEntityBase.inventory, var4 + DocaInventory.CurrentInventoryMin, 8, (86 + 54) + var4 * moveBoxPoint));
		}

		for (var4 = 0; var4 < DocaInventory.FoodsInventorySize; ++var4)
		{
			this.addSlotToContainer(new DocaSlotFoods(par2DocaEntityBase, par2DocaEntityBase.inventory, var4 + DocaInventory.FoodsInventoryMin, 8, (86 + 54 + moveBoxPoint) + var4 * moveBoxPoint));
		}

		for (var4 = 0; var4 < DocaInventory.FuncInventorySize; ++var4)
		{
			this.addSlotToContainer(new DocaSlotFunc(par2DocaEntityBase, par2DocaEntityBase.inventory, var4 + DocaInventory.FuncInventoryMin, 8 + moveBoxPoint, (86 + 54) + var4 * moveBoxPoint));
		}

		for (var4 = 0; var4 < 3; ++var4)
		{
			for (var5 = 0; var5 < DocaInventory.HotbarSize; ++var5)
			{
				this.addSlotToContainer(new Slot(par0EntityPlayer.inventory, var5 + var4 * DocaInventory.HotbarSize + DocaInventory.HotbarSize, 67 + var5 * moveBoxPoint, (86 + 54) + var4 * moveBoxPoint));
			}
		}

		for (var4 = 0; var4 < DocaInventory.HotbarSize; ++var4)
		{
			this.addSlotToContainer(new Slot(par0EntityPlayer.inventory, var4, 67 + var4 * moveBoxPoint, 144 + 54));
		}
	}

	@Override
	public boolean canInteractWith(EntityPlayer par1EntityPlayer)
	{
		return this.tmpInventory.isUseableByPlayer(par1EntityPlayer);
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2)
	{
		ItemStack var3 = null;
		Slot var4 = (Slot)this.inventorySlots.get(par2);

		if (var4 != null && var4.getHasStack())
		{
			ItemStack var5 = var4.getStack();
			var3 = var5.copy();

			if (par2 < DocaInventory.ALLInventoryTableSizeMax)
			{
				if (!this.mergeItemStack(var5, DocaInventory.ALLInventoryTableSizeMax, this.inventorySlots.size(), true))
				{
					return null;
				}
			}
			else if (!this.mergeItemStack(var5, 0, DocaInventory.ALLInventoryTableSizeMax, false))
			{
				return null;
			}

			if (var5.stackSize == 0)
			{
				var4.putStack((ItemStack)null);
			}
			else
			{
				var4.onSlotChanged();
			}
		}

		return var3;
	}

	@Override
	public void onContainerClosed(EntityPlayer par1EntityPlayer)
	{
		super.onContainerClosed(par1EntityPlayer);
		this.tmpInventory.closeChest();
	}
}
