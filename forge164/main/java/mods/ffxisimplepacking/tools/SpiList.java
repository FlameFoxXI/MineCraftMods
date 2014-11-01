package mods.ffxisimplepacking.tools;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;

public class SpiList
{
	private Object item;
	private int meta;
	private String name;
	private Object craftItem;
	
	public SpiList(Object item, int meta, Object craftItem, String name)
	{
		this.item = item;
		this.meta = meta;
		this.name = name;
		this.craftItem = craftItem;
	}

	public int getMataData()
	{
		return this.meta;
	}
	
	public String getName()
	{
		return this.name;
	}
	
	public boolean isItemBlock()
	{
		if (this.item instanceof Block)
		{
			return true;
		}
		return false;
	}
	
	public Object getObject()
	{
		return this.item;
	}
	
	public Item getItem()
	{
		if(!this.isItemBlock())
		{
			return (Item)this.item;
		}
		return null;
	}
	
	public Block getBlock()
	{
		if(this.isItemBlock())
		{
			return (Block)this.item;
		}
		return null;
	}

	public boolean isCraftItemBlock()
	{
		if (this.craftItem instanceof Block)
		{
			return true;
		}
		return false;
	}
	
	public Item getCraftItem()
	{
		if(!this.isCraftItemBlock())
		{
			return (Item)this.craftItem;
		}
		return null;
	}
	
	public Block getCraftBlock()
	{
		if(this.isCraftItemBlock())
		{
			return (Block)this.craftItem;
		}
		return null;
	}
	
	public Object getCraftObject()
	{
		return this.craftItem;
	}
}
