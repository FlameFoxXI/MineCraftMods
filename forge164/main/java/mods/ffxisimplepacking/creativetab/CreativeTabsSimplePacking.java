package mods.ffxisimplepacking.creativetab;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mods.ffxisimplepacking.tools.SpiSetting;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class CreativeTabsSimplePacking extends CreativeTabs
{
	private String select;

	public CreativeTabsSimplePacking(String type, String select)
	{
		super(type);
		this.select = select;
	}

	public CreativeTabsSimplePacking(String type)
	{
		this(type, SpiSetting.BLOCK);
	}

	@Override
    @SideOnly(Side.CLIENT)
    public int getTabIconItemIndex()
    {
        if (select.equalsIgnoreCase(SpiSetting.ITEM))
        {
        	return Item.appleGold.itemID;
        }
        else
        {
        	return Block.blockClay.blockID;
        }
    }
}