package mods.ffxisimplepacking.tools;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class SpiConvert
{
	public static Map<Integer, int[]> cnvItemList = new HashMap();
	public static Map<Integer, int[]> cnvBlockList = new HashMap();
	
	private static Item convItem;
	private static Item convBlock;

	public static void getConvertItemforOld()
	{
		if (!SpiSetting.func_ConvertOldVersion)
		{
			return;
		}

		convItem = GameRegistry.findItem("SimplePackingItemMod", "SimplePackingItem");
		convBlock =  GameRegistry.findItem("SimplePackingItemMod", "SimplePacking");
	}
	
	private static void convertItemList(Item item, int meta, int id)
	{
		int[] convTable = { meta , id };
		cnvItemList.put(Item.getIdFromItem(item), convTable);
	}
	
	private static void convertBlockList(Block block, int meta, int id)
	{
		int[] convTable = { meta , id };
		cnvBlockList.put(Block.getIdFromBlock(block), convTable);
	}	
	
	public static void regConvertListToAll()
	{
		if (!SpiSetting.func_ConvertOldVersion)
		{
			return;
		}

		convertItemList(Items.coal, 0, 0);
		convertItemList(Items.iron_ingot, 0, 1);
		convertItemList(Items.gold_ingot, 0, 2);
		convertItemList(Items.diamond, 0, 3);
		convertItemList(Items.emerald, 0, 4);
		convertItemList(Items.redstone, 0, 5);
		convertItemList(Items.glowstone_dust, 0, 6);
		convertItemList(Items.gunpowder, 0, 10);
		convertItemList(Items.flint, 0, 11);
		convertItemList(Items.clay_ball, 0, 12);
		convertItemList(Items.brick, 0, 13); 
		convertItemList(Items.quartz, 0, 14);
		convertItemList(Items.netherbrick, 0, 15);
		convertItemList(Items.blaze_rod, 0, 16);
		convertItemList(Items.gold_nugget, 0, 17); 
		convertItemList(Items.ghast_tear, 0, 18);
//		convertItemList(Items.netherStar, 0, 19);
		convertItemList(Items.stick, 0, 23);
		convertItemList(Items.bowl, 0, 24);
		convertItemList(Items.feather, 0, 25);
		convertItemList(Items.string, 0, 26);
		convertItemList(Items.leather, 0, 27);
		convertItemList(Items.slime_ball, 0, 28);
		convertItemList(Items.ender_pearl, 0, 29);
		convertItemList(Items.ender_eye, 0, 30);
		convertItemList(Items.bone, 0, 34);
		convertItemList(Items.paper, 0, 35);
		convertItemList(Items.book, 0, 36);
		convertItemList(Items.map, 0, 38);
		convertItemList(Items.glass_bottle, 0, 42);
		convertItemList(Items.fireworks, 0, 43);
		convertItemList(Items.fireworks, 0, 44);
		convertItemList(Items.wheat_seeds, 0, 48);
		convertItemList(Items.wheat, 0, 49);
		convertItemList(Items.reeds, 0, 50);
		convertItemList(Items.pumpkin_seeds, 0, 51);
		convertItemList(Items.melon_seeds, 0, 52);
		convertItemList(Items.nether_wart, 0, 53);
		convertItemList(Items.sugar, 0, 57);
		convertItemList(Items.dye, 0, 61);
		convertItemList(Items.dye, 1, 62);
		convertItemList(Items.dye, 2, 63);
		convertItemList(Items.dye, 3, 64);
		convertItemList(Items.dye, 4, 65);
		convertItemList(Items.dye, 5, 66);
		convertItemList(Items.dye, 6, 67);
		convertItemList(Items.dye, 7, 68);
		convertItemList(Items.dye, 8, 69);
		convertItemList(Items.dye, 9, 70);
		convertItemList(Items.dye, 10, 71);
		convertItemList(Items.dye, 11, 72);
		convertItemList(Items.dye, 12, 73);
		convertItemList(Items.dye, 13, 74);
		convertItemList(Items.dye, 14, 75);
		convertItemList(Items.dye, 15, 76);
		convertItemList(Items.apple, 0, 93);
		convertItemList(Items.golden_apple, 0, 94);
//		convertItemList(Items.golden_apple, 1, 95);
		convertItemList(Items.mushroom_stew, 0, 96);
		convertItemList(Items.bread, 0, 97);
		convertItemList(Items.porkchop, 0, 98);
		convertItemList(Items.cooked_porkchop, 0, 99);
		convertItemList(Items.fish, 0, 100);
		convertItemList(Items.cooked_fished, 0, 101);
		convertItemList(Items.cake, 0, 102);
		convertItemList(Items.cookie, 0, 103);
		convertItemList(Items.melon, 0, 104);
		convertItemList(Items.beef, 0, 105);
		convertItemList(Items.cooked_beef, 0, 106);
		convertItemList(Items.chicken, 0, 107);
		convertItemList(Items.cooked_chicken, 0, 108);
		convertItemList(Items.carrot, 0, 109);
		convertItemList(Items.golden_carrot, 0, 110);
		convertItemList(Items.potato, 0, 111);
		convertItemList(Items.baked_potato, 0, 112);
		convertItemList(Items.poisonous_potato, 0, 113);
		convertItemList(Items.pumpkin_pie, 0, 114);
		convertItemList(Items.rotten_flesh, 0, 118);
		convertItemList(Items.spider_eye, 0, 119);
		convertItemList(Items.egg, 0, 120);
		
		convertBlockList(Blocks.iron_block,0,1);
		convertBlockList(Blocks.gold_block,0,2);
		convertBlockList(Blocks.diamond_block,0,3);
		convertBlockList(Blocks.emerald_block,0,4);
		convertBlockList(Blocks.lapis_block,0,5);
		convertBlockList(Blocks.redstone_block,0,6);
		convertBlockList(Blocks.quartz_block,0,8);
		convertBlockList(Blocks.coal_ore,0,13);
		convertBlockList(Blocks.iron_ore,0,14);
		convertBlockList(Blocks.gold_ore,0,15);
		convertBlockList(Blocks.emerald_ore,0,16);
		convertBlockList(Blocks.emerald_ore,0,17);
		convertBlockList(Blocks.lapis_ore,0,18);
		convertBlockList(Blocks.redstone_ore,0,19);
		convertBlockList(Blocks.glowstone,0,20);
		convertBlockList(Blocks.quartz_ore,0,21);
		convertBlockList(Blocks.dirt,0,26);
		convertBlockList(Blocks.gravel,0,27);
		convertBlockList(Blocks.sand,0,28);
		convertBlockList(Blocks.sandstone,0,29);
		convertBlockList(Blocks.sandstone,1,30);
		convertBlockList(Blocks.sandstone,2,31);
		convertBlockList(Blocks.quartz_block,1,32);
		convertBlockList(Blocks.quartz_block,2,33);
		convertBlockList(Blocks.glass,0,36);
		convertBlockList(Blocks.ice,0,37);
		convertBlockList(Blocks.snow_layer,0,38);
		convertBlockList(Blocks.snow,0,39);
		convertBlockList(Blocks.mycelium,0,40);
		convertBlockList(Blocks.stone,0,46);
		convertBlockList(Blocks.cobblestone,0,47);
		convertBlockList(Blocks.stonebrick,0,48);
		convertBlockList(Blocks.stonebrick,1,49);
		convertBlockList(Blocks.stonebrick,2,50);
		convertBlockList(Blocks.stonebrick,3,51);
		convertBlockList(Blocks.mossy_cobblestone,0,52);
		convertBlockList(Blocks.clay,0,56);
		convertBlockList(Blocks.brick_block,0,57);
		convertBlockList(Blocks.obsidian,0,58);
		convertBlockList(Blocks.soul_sand,0,59);//slowSand
		convertBlockList(Blocks.nether_brick,0,60);
		convertBlockList(Blocks.netherrack,0,61);
		convertBlockList(Blocks.end_stone,0,62);
		convertBlockList(Blocks.planks,0,68);
		convertBlockList(Blocks.planks,1,69);
		convertBlockList(Blocks.planks,2,70);
		convertBlockList(Blocks.planks,3,71);
		convertBlockList(Blocks.log,0,74);
		convertBlockList(Blocks.log,1,75);
		convertBlockList(Blocks.log,2,76);
		convertBlockList(Blocks.log,3,77);
		convertBlockList(Blocks.wool,0,80);
		convertBlockList(Blocks.wool,1,81);
		convertBlockList(Blocks.wool,2,82);
		convertBlockList(Blocks.wool,3,83);
		convertBlockList(Blocks.wool,4,84);
		convertBlockList(Blocks.wool,5,85);
		convertBlockList(Blocks.wool,6,86);
		convertBlockList(Blocks.wool,7,87);
		convertBlockList(Blocks.wool,8,88);
		convertBlockList(Blocks.wool,9,89);
		convertBlockList(Blocks.wool,10,90);
		convertBlockList(Blocks.wool,11,91);
		convertBlockList(Blocks.wool,12,92);
		convertBlockList(Blocks.wool,13,93);
		convertBlockList(Blocks.wool,14,94);
		convertBlockList(Blocks.wool,15,95);
	}
	
	public static void addConvertRecipe()
	{
		if (!SpiSetting.func_ConvertOldVersion)
		{
			return;
		}
		
		if (convItem != null)
		{
			for(Entry<Integer, int[]> key: cnvItemList.entrySet())
			{
				if (key.getValue().length != 2)
				{
					FMLLog.warning("error id=" + key.getKey());
					continue;
				}
				
				int id = ((key.getKey() - 256) << 4) + (key.getValue()[0] & 0x0000000F);

				if(SpiTools.getList(SpiSetting.ITEM).containsKey(id))
				{
					SpiList spilist = SpiTools.getList(SpiSetting.ITEM).get(id);

					if (Item.getIdFromItem(spilist.getItem()) == key.getKey())
					{
						GameRegistry.addShapelessRecipe(new ItemStack(SpiTools.getBlockInstance(SpiSetting.ITEM, 0), 1, id)
						, new Object[]{ new ItemStack(convItem, 1, key.getValue()[1]) });
						GameRegistry.addShapelessRecipe(new ItemStack(SpiTools.getBlockInstance(SpiSetting.ITEM, 1), 1, id)
						, new Object[]{ new ItemStack(convItem, 1, key.getValue()[1] + 128) });
					}
					else
					{
						FMLLog.warning("error spiListItem itemId missmatch newid=" + Item.getIdFromItem(spilist.getItem()) +". oldid=" + key.getKey());
						continue;
					}
				}
				else
				{
					FMLLog.warning("error spiListItem dot found id=" + id +"("+ key.getKey() +"."+ key.getValue()[0] +")");
					continue;
				}
			}
		}
		else
		{
			FMLLog.warning("error convItem is null");
		}
		
		if (convBlock != null)
		{
			for(Entry<Integer, int[]> key: cnvBlockList.entrySet())
			{
				if (key.getValue().length != 2)
				{
					FMLLog.warning("error id=" + key.getKey());
					continue;
				}
				
				int id = (key.getKey() << 4) + (key.getValue()[0] & 0x0000000F);

				if(SpiTools.getList(SpiSetting.BLOCK).containsKey(id))
				{
					SpiList spilist = SpiTools.getList(SpiSetting.BLOCK).get(id);

					if (Block.getIdFromBlock(spilist.getBlock()) == key.getKey())
					{
						GameRegistry.addShapelessRecipe(new ItemStack(SpiTools.getBlockInstance(SpiSetting.BLOCK, 0), 1, id)
						, new Object[]{ new ItemStack(convBlock, 1, key.getValue()[1]) });
						GameRegistry.addShapelessRecipe(new ItemStack(SpiTools.getBlockInstance(SpiSetting.BLOCK, 1), 1, id)
						, new Object[]{ new ItemStack(convBlock, 1, key.getValue()[1] + 128) });
					}
					else
					{
						FMLLog.warning("error spiListBlock itemId missmatch newid=" + Block.getIdFromBlock(spilist.getBlock()) +". oldid=" + key.getKey());
						continue;
					}
				}
				else
				{
					FMLLog.warning("error spiListBlock dot found id=" + id +"("+ key.getKey() +"."+ key.getValue()[0] +")");
					continue;
				}
			}
		}
		else
		{
			FMLLog.warning("error convBlock is null");
		}
	}
	
	public static void addConvertRecipePlus()
	{
		if (!SpiSetting.func_ConvertOldVersion)
		{
			return;
		}
		
		//coalBlock
		int id = ((Item.getIdFromItem(Items.coal) - 256) << 4);
		GameRegistry.addShapelessRecipe(new ItemStack(SpiTools.getBlockInstance(SpiSetting.ITEM, 0), 1, id)
			, new Object[]{ new ItemStack(convBlock, 1, 0) });
		GameRegistry.addShapelessRecipe(new ItemStack(SpiTools.getBlockInstance(SpiSetting.ITEM, 1), 1, id)
			, new Object[]{ new ItemStack(convBlock, 1, 0 + 128) });

		//glowstoneBlock
		id = ((Item.getIdFromItem(Items.glowstone_dust) - 256) << 4);
		GameRegistry.addShapelessRecipe(new ItemStack(SpiTools.getBlockInstance(SpiSetting.ITEM, 0), 1, id)
		, new Object[]{ new ItemStack(convBlock, 1, 7) });
		GameRegistry.addShapelessRecipe(new ItemStack(SpiTools.getBlockInstance(SpiSetting.ITEM, 1), 1, id)
		, new Object[]{ new ItemStack(convBlock, 1, 7 + 128) });
	}
}
