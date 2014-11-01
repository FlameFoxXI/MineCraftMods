package mods.ffxisimplepacking.tools;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
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
		cnvItemList.put(item.itemID, convTable);
	}
	
	private static void convertBlockList(Block block, int meta, int id)
	{
		int[] convTable = { meta , id };
		cnvBlockList.put(block.blockID, convTable);
	}	
	
	public static void regConvertListToAll()
	{
		if (!SpiSetting.func_ConvertOldVersion)
		{
			return;
		}

		convertItemList(Item.coal, 0, 0);
		convertItemList(Item.ingotIron, 0, 1);
		convertItemList(Item.ingotGold, 0, 2);
		convertItemList(Item.diamond, 0, 3);
		convertItemList(Item.emerald, 0, 4);
		convertItemList(Item.redstone, 0, 5);
		convertItemList(Item.glowstone, 0, 6);
		convertItemList(Item.gunpowder, 0, 10);
		convertItemList(Item.flint, 0, 11);
		convertItemList(Item.clay, 0, 12);
		convertItemList(Item.brick, 0, 13); 
		convertItemList(Item.netherQuartz, 0, 14);
		convertItemList(Item.netherrackBrick, 0, 15);
		convertItemList(Item.blazeRod, 0, 16);
		convertItemList(Item.goldNugget, 0, 17); 
		convertItemList(Item.ghastTear, 0, 18);
//		convertItemList(Item.netherStar, 0, 19);
		convertItemList(Item.stick, 0, 23);
		convertItemList(Item.bowlEmpty, 0, 24);
		convertItemList(Item.feather, 0, 25);
		convertItemList(Item.silk, 0, 26);
		convertItemList(Item.leather, 0, 27);
		convertItemList(Item.slimeBall, 0, 28);
		convertItemList(Item.enderPearl, 0, 29);
		convertItemList(Item.eyeOfEnder, 0, 30);
		convertItemList(Item.bone, 0, 34);
		convertItemList(Item.paper, 0, 35);
		convertItemList(Item.book, 0, 36);
		convertItemList(Item.emptyMap, 0, 38);
		convertItemList(Item.glassBottle, 0, 42);
		convertItemList(Item.firework, 0, 43);
		convertItemList(Item.firework, 0, 44);
		convertItemList(Item.seeds, 0, 48);
		convertItemList(Item.wheat, 0, 49);
		convertItemList(Item.reed, 0, 50);
		convertItemList(Item.pumpkinSeeds, 0, 51);
		convertItemList(Item.melonSeeds, 0, 52);
		convertItemList(Item.netherStalkSeeds, 0, 53);
		convertItemList(Item.sugar, 0, 57);
		convertItemList(Item.dyePowder, 0, 61);
		convertItemList(Item.dyePowder, 1, 62);
		convertItemList(Item.dyePowder, 2, 63);
		convertItemList(Item.dyePowder, 3, 64);
		convertItemList(Item.dyePowder, 4, 65);
		convertItemList(Item.dyePowder, 5, 66);
		convertItemList(Item.dyePowder, 6, 67);
		convertItemList(Item.dyePowder, 7, 68);
		convertItemList(Item.dyePowder, 8, 69);
		convertItemList(Item.dyePowder, 9, 70);
		convertItemList(Item.dyePowder, 10, 71);
		convertItemList(Item.dyePowder, 11, 72);
		convertItemList(Item.dyePowder, 12, 73);
		convertItemList(Item.dyePowder, 13, 74);
		convertItemList(Item.dyePowder, 14, 75);
		convertItemList(Item.dyePowder, 15, 76);
		convertItemList(Item.appleRed, 0, 93);
		convertItemList(Item.appleGold, 0, 94);
//		convertItemList(Item.appleGold, 1, 95);
		convertItemList(Item.bowlSoup, 0, 96);
		convertItemList(Item.bread, 0, 97);
		convertItemList(Item.porkRaw, 0, 98);
		convertItemList(Item.porkCooked, 0, 99);
		convertItemList(Item.fishRaw, 0, 100);
		convertItemList(Item.fishCooked, 0, 101);
		convertItemList(Item.cake, 0, 102);
		convertItemList(Item.cookie, 0, 103);
		convertItemList(Item.melon, 0, 104);
		convertItemList(Item.beefRaw, 0, 105);
		convertItemList(Item.beefCooked, 0, 106);
		convertItemList(Item.chickenRaw, 0, 107);
		convertItemList(Item.chickenCooked, 0, 108);
		convertItemList(Item.carrot, 0, 109);
		convertItemList(Item.goldenCarrot, 0, 110);
		convertItemList(Item.potato, 0, 111);
		convertItemList(Item.bakedPotato, 0, 112);
		convertItemList(Item.poisonousPotato, 0, 113);
		convertItemList(Item.pumpkinPie, 0, 114);
		convertItemList(Item.rottenFlesh, 0, 118);
		convertItemList(Item.spiderEye, 0, 119);
		convertItemList(Item.egg, 0, 120);
		
		convertBlockList(Block.blockIron,0,1);
		convertBlockList(Block.blockGold,0,2);
		convertBlockList(Block.blockDiamond,0,3);
		convertBlockList(Block.blockEmerald,0,4);
		convertBlockList(Block.blockLapis,0,5);
		convertBlockList(Block.blockRedstone,0,6);
		convertBlockList(Block.blockNetherQuartz,0,8);
		convertBlockList(Block.oreCoal,0,13);
		convertBlockList(Block.oreIron,0,14);
		convertBlockList(Block.oreGold,0,15);
		convertBlockList(Block.oreEmerald,0,16);
		convertBlockList(Block.oreEmerald,0,17);
		convertBlockList(Block.oreLapis,0,18);
		convertBlockList(Block.oreRedstone,0,19);
		convertBlockList(Block.glowStone,0,20);
		convertBlockList(Block.oreNetherQuartz,0,21);
		convertBlockList(Block.dirt,0,26);
		convertBlockList(Block.gravel,0,27);
		convertBlockList(Block.sand,0,28);
		convertBlockList(Block.sandStone,0,29);
		convertBlockList(Block.sandStone,1,30);
		convertBlockList(Block.sandStone,2,31);
		convertBlockList(Block.blockNetherQuartz,1,32);
		convertBlockList(Block.blockNetherQuartz,2,33);
		convertBlockList(Block.glass,0,36);
		convertBlockList(Block.ice,0,37);
		convertBlockList(Block.snow,0,38);
		convertBlockList(Block.blockSnow,0,39);
		convertBlockList(Block.mycelium,0,40);
		convertBlockList(Block.stone,0,46);
		convertBlockList(Block.cobblestone,0,47);
		convertBlockList(Block.stoneBrick,0,48);
		convertBlockList(Block.stoneBrick,1,49);
		convertBlockList(Block.stoneBrick,2,50);
		convertBlockList(Block.stoneBrick,3,51);
		convertBlockList(Block.cobblestoneMossy,0,52);
		convertBlockList(Block.blockClay,0,56);
		convertBlockList(Block.brick,0,57);
		convertBlockList(Block.obsidian,0,58);
		convertBlockList(Block.slowSand,0,59);
		convertBlockList(Block.netherBrick,0,60);
		convertBlockList(Block.netherrack,0,61);
		convertBlockList(Block.whiteStone,0,62);
		convertBlockList(Block.planks,0,68);
		convertBlockList(Block.planks,1,69);
		convertBlockList(Block.planks,2,70);
		convertBlockList(Block.planks,3,71);
		convertBlockList(Block.wood,0,74);
		convertBlockList(Block.wood,1,75);
		convertBlockList(Block.wood,2,76);
		convertBlockList(Block.wood,3,77);
		convertBlockList(Block.cloth,0,80);
		convertBlockList(Block.cloth,1,81);
		convertBlockList(Block.cloth,2,82);
		convertBlockList(Block.cloth,3,83);
		convertBlockList(Block.cloth,4,84);
		convertBlockList(Block.cloth,5,85);
		convertBlockList(Block.cloth,6,86);
		convertBlockList(Block.cloth,7,87);
		convertBlockList(Block.cloth,8,88);
		convertBlockList(Block.cloth,9,89);
		convertBlockList(Block.cloth,10,90);
		convertBlockList(Block.cloth,11,91);
		convertBlockList(Block.cloth,12,92);
		convertBlockList(Block.cloth,13,93);
		convertBlockList(Block.cloth,14,94);
		convertBlockList(Block.cloth,15,95);
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

					if (spilist.getItem().itemID == key.getKey())
					{
						GameRegistry.addShapelessRecipe(new ItemStack(SpiTools.getBlockInstance(SpiSetting.ITEM, 0), 1, id)
						, new Object[]{ new ItemStack(convItem, 1, key.getValue()[1]) });
						GameRegistry.addShapelessRecipe(new ItemStack(SpiTools.getBlockInstance(SpiSetting.ITEM, 1), 1, id)
						, new Object[]{ new ItemStack(convItem, 1, key.getValue()[1] + 128) });
					}
					else
					{
						FMLLog.warning("error spiListItem itemId missmatch newid=" + spilist.getItem().itemID +". oldid=" + key.getKey());
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

					if (spilist.getBlock().blockID == key.getKey())
					{
						GameRegistry.addShapelessRecipe(new ItemStack(SpiTools.getBlockInstance(SpiSetting.BLOCK, 0), 1, id)
						, new Object[]{ new ItemStack(convBlock, 1, key.getValue()[1]) });
						GameRegistry.addShapelessRecipe(new ItemStack(SpiTools.getBlockInstance(SpiSetting.BLOCK, 1), 1, id)
						, new Object[]{ new ItemStack(convBlock, 1, key.getValue()[1] + 128) });
					}
					else
					{
						FMLLog.warning("error spiListBlock itemId missmatch newid=" + spilist.getBlock().blockID +". oldid=" + key.getKey());
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
		int id = ((Item.coal.itemID - 256) << 4);
		GameRegistry.addShapelessRecipe(new ItemStack(SpiTools.getBlockInstance(SpiSetting.ITEM, 0), 1, id)
			, new Object[]{ new ItemStack(convBlock, 1, 0) });
		GameRegistry.addShapelessRecipe(new ItemStack(SpiTools.getBlockInstance(SpiSetting.ITEM, 1), 1, id)
			, new Object[]{ new ItemStack(convBlock, 1, 0 + 128) });

		//glowstoneBlock
		id = ((Item.glowstone.itemID - 256) << 4);
		GameRegistry.addShapelessRecipe(new ItemStack(SpiTools.getBlockInstance(SpiSetting.ITEM, 0), 1, id)
		, new Object[]{ new ItemStack(convBlock, 1, 7) });
		GameRegistry.addShapelessRecipe(new ItemStack(SpiTools.getBlockInstance(SpiSetting.ITEM, 1), 1, id)
		, new Object[]{ new ItemStack(convBlock, 1, 7 + 128) });
	}
}
