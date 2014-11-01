package mods.doca.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mods.doca.core.DocaSet;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class DocaCreativeTabs extends CreativeTabs
{
	public DocaCreativeTabs(String type)
	{
		super(type);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Item getTabIconItem()
	{
		return DocaSet.spawnDocaItem;
	}

	@Override
	public ItemStack getIconItemStack()
	{
		return new ItemStack(getTabIconItem(), 1, 256);
	}
}
