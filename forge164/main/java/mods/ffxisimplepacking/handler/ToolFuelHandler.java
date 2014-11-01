package mods.ffxisimplepacking.handler;

import mods.ffxisimplepacking.block.BlockSimplePackingBlock;
import mods.ffxisimplepacking.block.BlockSimplePackingItem;
import mods.ffxisimplepacking.tools.SpiSetting;
import mods.ffxisimplepacking.tools.SpiTools;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityFurnace;
import cpw.mods.fml.common.IFuelHandler;

public class ToolFuelHandler implements IFuelHandler
{
	@Override
	public int getBurnTime(ItemStack fuel)
	{
		for (int i = 0; i < SpiSetting.spiTypeTableMax; i++)
		{
			ItemStack itemstack = new ItemStack(SpiTools.getBlockInstance(SpiSetting.BLOCK, i));

			if(fuel.itemID == itemstack.itemID)
			{
				if (SpiTools.getList(SpiSetting.BLOCK).containsKey(fuel.getItemDamage()))
				{
					if(SpiTools.getList(SpiSetting.BLOCK).get(fuel.getItemDamage()).isItemBlock())
					{
						Block block = (Block)SpiTools.getList(SpiSetting.BLOCK).get(fuel.getItemDamage()).getBlock();
						int meta = SpiTools.getList(SpiSetting.BLOCK).get(fuel.getItemDamage()).getMataData();
						itemstack  = new ItemStack(block.blockID, 1, meta);
						int burnTime = TileEntityFurnace.getItemBurnTime(itemstack);

						if (burnTime > 1048575)
						{
							return burnTime;
						}

						if (SpiTools.getBlockInstance(SpiSetting.BLOCK, i) instanceof BlockSimplePackingBlock)
						{
							int type = ((BlockSimplePackingBlock)SpiTools.getBlockInstance(SpiSetting.BLOCK, i)).getPackingType();
							int staks = SpiSetting.stakBlock[Math.min(SpiSetting.spiTypeMax, type)];
							return burnTime * staks;
						}
					}
				}
			}
		}

		for (int i = 0; i < SpiSetting.spiTypeTableMax; i++)
		{
			ItemStack itemstack = new ItemStack(SpiTools.getBlockInstance(SpiSetting.ITEM, i));

			if(fuel.itemID == itemstack.itemID)
			{
				if (SpiTools.getList(SpiSetting.ITEM).containsKey(fuel.getItemDamage()))
				{
					if(!SpiTools.getList(SpiSetting.ITEM).get(fuel.getItemDamage()).isItemBlock())
					{
						Item item = (Item)SpiTools.getList(SpiSetting.ITEM).get(fuel.getItemDamage()).getItem();
						int meta = SpiTools.getList(SpiSetting.ITEM).get(fuel.getItemDamage()).getMataData();
						itemstack  = new ItemStack(item.itemID, 1, meta);
						int burnTime = TileEntityFurnace.getItemBurnTime(itemstack);

						if (burnTime > 1048575)
						{
							return burnTime;
						}

						if (SpiTools.getBlockInstance(SpiSetting.ITEM, i) instanceof BlockSimplePackingItem)
						{
							int type = ((BlockSimplePackingItem)SpiTools.getBlockInstance(SpiSetting.ITEM, i)).getPackingType();
							int staks = SpiSetting.stakBlock[Math.min(SpiSetting.spiTypeMax, type)];
							return burnTime = burnTime * staks;
						}
					}				
				}
			}
		}
		return 0;
	}
}