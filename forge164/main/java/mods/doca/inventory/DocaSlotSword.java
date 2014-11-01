package mods.doca.inventory;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mods.doca.core.DocaSet;
import mods.doca.entity.DocaEntityBase;
import mods.doca.item.DocaItemEgg;
import net.minecraft.block.Block;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.util.Icon;

public class DocaSlotSword extends Slot
{
	DocaEntityBase theBase;

	public DocaSlotSword(DocaEntityBase par0DocaEntityBase, IInventory par1IInventory, int par2, int par3, int par4)
	{
		super(par1IInventory, par2, par3, par4);
		this.theBase = par0DocaEntityBase;
	}

	@Override
	public int getSlotStackLimit()
	{
		return 1;
	}

	@Override
	public boolean isItemValid(ItemStack par1ItemStack)
	{
		Item item = (par1ItemStack == null ? null : par1ItemStack.getItem());
		return item != null && (item instanceof ItemSword || item instanceof ItemBow /*|| item instanceof ItemTool*/);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getBackgroundIconIndex()
	{
		return ((DocaItemEgg)DocaSet.spawnDocaItem).getDocaIconforDummy(0);
	}
}
