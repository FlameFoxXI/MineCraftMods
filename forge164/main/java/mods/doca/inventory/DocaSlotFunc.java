package mods.doca.inventory;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mods.doca.core.DocaSet;
import mods.doca.core.DocaTools;
import mods.doca.entity.DocaEntityBase;
import mods.doca.item.DocaItemEgg;
import net.minecraft.block.Block;
import net.minecraft.inventory.ContainerPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;

public class DocaSlotFunc extends Slot
{
	DocaEntityBase theBase;

	public DocaSlotFunc(DocaEntityBase par0DocaEntityBase, IInventory par1IInventory, int par2, int par3, int par4)
	{
		super(par1IInventory, par2, par3, par4);
		this.theBase = par0DocaEntityBase;
	}

	@Override
	public boolean isItemValid(ItemStack par1ItemStack)
	{
		return DocaTools.ofItem(par1ItemStack, Block.chest);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getBackgroundIconIndex()
	{
		return ((DocaItemEgg)DocaSet.spawnDocaItem).getDocaIconforDummy(2);
	}
}
