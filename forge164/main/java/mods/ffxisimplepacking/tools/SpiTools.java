package mods.ffxisimplepacking.tools;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.logging.Level;

import cpw.mods.fml.common.FMLLog;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class SpiTools
{
	public SpiTools()
	{
	}

	public static void addLisToSend(int id, int id2, Object item, int meta, Object citem)
	{
		if (SpiSetting.spiListItem == null)
		{
			FMLLog.log(Level.SEVERE, "item list is null." + id);
			return;
		}

		if (SpiSetting.spiListBlock == null)
		{
			FMLLog.log(Level.SEVERE, "block list is null." + id);
			return;
		}

		if (id >= SpiSetting.spiListMax)
		{
			FMLLog.log(Level.SEVERE, "out of array. no." + id);
			return;
		}

		if (item == null)
		{
			FMLLog.log(Level.SEVERE, "resist item abnormal. (null pointer)" + item);
			return;
		}

		if (!(item instanceof Block || item instanceof Item))
		{
			FMLLog.log(Level.SEVERE, "resist item abnormal. (can resist block or item.)" + item);
			return;
		}

		String name = "";

		id = (id << 4);
		id = id + (id2 & 0x0000000F);
		
		
		
		if (id >= Short.MAX_VALUE)
		{
			FMLLog.log(Level.SEVERE, "item id or damage abnormal. (can resist block or item.)" + item);
			return;
		}
		
		if(item instanceof Block)
		{
			ItemStack aaa = new ItemStack((Block)item, 1, meta);
			name = aaa.getItem().getUnlocalizedName(aaa);
			name = name.replaceAll("tile.", "");
			name = name.replaceAll("item.", "");
			name = name.replaceAll(".name", "");
			SpiSetting.spiListBlock.put(id, new SpiList(item, meta, citem, name));
		}
		else
		{
			ItemStack aaa = new ItemStack((Item)item, 1, meta);
			name = aaa.getItem().getUnlocalizedName(aaa);
			name = name.replaceAll("tile.", "");
			name = name.replaceAll("item.", "");
			name = name.replaceAll(".name", "");
			SpiSetting.spiListItem.put(id, new SpiList(item, meta, citem, name));
		}

	}

	public static void addLisToSend(int id, int id2, Object item, int meta)
	{
		addLisToSend(id, id2, item, meta, item);
	}


	public static void addLisToSendBlockId(int id, int id2, int meta, String name)
	{
		addLisToSend(id, id2, Block.blocksList[id], meta, Block.blocksList[id]);
	}

	public static void addLisToSendItemId(int id, int itemid, int id2, int meta, String name)
	{
		addLisToSend(id, id2, Item.itemsList[itemid], meta, Item.itemsList[itemid]);
	}

	public static Map<Integer, SpiList> getList(String list)
	{
		if (list.equalsIgnoreCase(SpiSetting.BLOCK))
		{
			return SpiSetting.spiListBlock;
		}
		else
		{
			return SpiSetting.spiListItem;
		}
	}

	public static void setBlockInstance(String text, int no, Block block)
	{
		if (text.equalsIgnoreCase(SpiSetting.BLOCK))
		{
			SpiSetting.spiblock[no] = block;
		}
		else
		{
			SpiSetting.spiItem[no] = block;
		}
	}

	public static Block getBlockInstance(String text, int no)
	{
		if (text.equalsIgnoreCase(SpiSetting.BLOCK))
		{
			return SpiSetting.spiblock[no];
		}
		else
		{
			return SpiSetting.spiItem[no];	
		}
	}

	public static int getSpiBlockMetaData(String text, int itemid, int itemmeta)
	{
		int value = 0;
		int id = 0;
		int meta = 0;
		for (Entry<Integer, SpiList> key: getList(text).entrySet())
		{
			if(key.getValue().isItemBlock())
			{
				id = key.getValue().getBlock().blockID;
				meta = key.getValue().getMataData();
			}
			else
			{
				id = key.getValue().getItem().itemID;
				meta = key.getValue().getMataData();
			}
			if (itemid == id && meta == itemmeta)
			{
				value = key.getKey();
				break;
			}
		}
		return value;
	}

	public static String getSpiName(String text, int no)
	{
		if (text.equalsIgnoreCase(SpiSetting.BLOCK))
		{
			return SpiSetting.nemeBlock[no];
		}
		else
		{
			return SpiSetting.nemeItem[no];	
		}
	}

	public static int getSpiStask(String text, int no)
	{
		if (text.equalsIgnoreCase(SpiSetting.BLOCK))
		{
			return SpiSetting.renderStakBlock[no];
		}
		else
		{
			return SpiSetting.renderStakItem[no];	
		}
	}
	
	public static void printOutListName()
	{
		try{
			File file = new File("d:\\tmp\\name.txt");

			if (canfile(file)){
				FileWriter pw = new FileWriter(file);

				for (int i = 0; i < SpiSetting.spiTypeTableMax; i++)
				{
					for (Entry<Integer, SpiList> key: SpiSetting.spiListBlock.entrySet())
					{
						pw.write("tile." + getSpiName(SpiSetting.BLOCK, i) +"."+ key.getValue().getName() +".name="+ SpiSetting.nemeSpi[i] + Item.itemsList[key.getValue().getBlock().blockID].getItemDisplayName(new ItemStack(key.getValue().getBlock().blockID, 1, key.getValue().getMataData())) + "\r\n");
					}
				}
				for (int i = 0; i < SpiSetting.spiTypeTableMax; i++)
				{
					for (Entry<Integer, SpiList> key: SpiSetting.spiListItem.entrySet())
					{
						pw.write("tile." + getSpiName(SpiSetting.ITEM, i) +"."+ key.getValue().getName() +".name="+ SpiSetting.nemeSpi[i] + Item.itemsList[key.getValue().getItem().itemID].getItemDisplayName(new ItemStack(key.getValue().getItem().itemID, 1, key.getValue().getMataData())) + "\r\n");
					}
				}

				pw.close();
				System.out.println("writted!!");
			}
			else
			{
				System.out.println("do not writter!!");
			}
		}
		catch(IOException e)
		{
			System.out.println(e);
		}
	}

	private static boolean canfile(File file)
	{
		if (file.exists())
		{
			if (file.isFile() && file.canWrite())
			{
				return true;
			}
		}
		return false;
	}

	private static Set<Integer> blackId = new HashSet(Arrays.asList(
			8, 9, 10, 11, 23, 25, 26, 27, 28, 29, 33, 34, 36, 46, 51, 52, 54, 55,
			58, 59, 61, 62, 63, 64, 65, 66, 68, 71, 75, 76, 83, 84, 90, 92, 93, 94, 95, 97, 99, 100, 104,
			105, 115, 116, 117, 118, 119, 120, 122, 123, 124, 127, 130, 131, 132, 138, 140,
			141, 142, 144, 145, 146, 147, 148, 149, 150, 151, 154, 157, 158, 166,
			
			256, 257, 258, 259, 261, 262, 267, 268, 269, 270, 271, 272,
			273, 274, 275, 276, 277, 278, 279, 283, 284, 285, 286, 290, 291, 292, 293, 294,
			298, 299, 300, 301, 302, 303, 304, 305, 306, 307, 308, 309, 310, 311, 312,
			313, 314, 315, 316, 317, 328, 333, 342, 343, 345, 346, 347, 355, 356, 358,
			359, 373, 379, 380, 383, 384, 387, 389, 397, 398, 399, 403, 404, 407, 408, 417,
			418, 419, 422
		));
	
	public static void getAllItemListAAA()
	{

		
		for (int i = 0; i < Item.itemsList.length; i++)
		{
			if(Item.itemsList[i] == null) { continue; }
			
			if (blackId.contains(i))
			{
				continue;
			}
			
			if (Item.itemsList[i] instanceof ItemBlock)
			{
				if(Item.itemsList[i].getHasSubtypes())
				{
					String tmp = "";
					for (int j = 0; j < 16; j++)
					{
						if (j == 0)
						{
							System.out.println("SpiTools.addLisToSendBlockId(" + Item.itemsList[i].itemID +", "+ j +", "+ j +", \""+ Item.itemsList[i].getItemDisplayName(new ItemStack(Item.itemsList[i].itemID, 1, j)) + "\");");
							tmp = Item.itemsList[i].getItemDisplayName(new ItemStack(Item.itemsList[i].itemID, 1, 0));
						}
						else if (!Item.itemsList[i].getItemDisplayName(new ItemStack(Item.itemsList[i].itemID, 1, j)).equalsIgnoreCase(tmp))
						{
							System.out.println("SpiTools.addLisToSendBlockId(" + Item.itemsList[i].itemID +", "+ j +", "+ j +", \""+ Item.itemsList[i].getItemDisplayName(new ItemStack(Item.itemsList[i].itemID, 1, j)) + "\");");
						}
					}
				}
				else
				{
					System.out.println("SpiTools.addLisToSendBlockId(" + Item.itemsList[i].itemID +", 0, 0, \""+ Item.itemsList[i].getItemDisplayName(new ItemStack(Item.itemsList[i].itemID, 1, 0)) + "\");");
				}
			}
			else
			{
				if(Item.itemsList[i].getHasSubtypes())
				{
					String tmp = "";
					for (int j = 0; j < 16; j++)
					{
						if (j == 0)
						{
							System.out.println("SpiTools.addLisToSendItemId(" + (Item.itemsList[i].itemID-256) +", "+ Item.itemsList[i].itemID +", "+ j +", "+ j +", \""+ Item.itemsList[i].getItemDisplayName(new ItemStack(Item.itemsList[i].itemID, 1, j)) + "\");");
							tmp = Item.itemsList[i].getItemDisplayName(new ItemStack(Item.itemsList[i].itemID, 1, 0));
						}
						else if (!Item.itemsList[i].getItemDisplayName(new ItemStack(Item.itemsList[i].itemID, 1, j)).equalsIgnoreCase(tmp))
						{
							System.out.println("SpiTools.addLisToSendItemId(" + (Item.itemsList[i].itemID-256) +", "+ Item.itemsList[i].itemID +", "+ j +", "+ j +", \""+ Item.itemsList[i].getItemDisplayName(new ItemStack(Item.itemsList[i].itemID, 1, j)) + "\");");
						}
					}
				}
				else
				{
					System.out.println("SpiTools.addLisToSendItemId(" + (Item.itemsList[i].itemID-256) +", "+ Item.itemsList[i].itemID +", 0, 0, \""+ Item.itemsList[i].getItemDisplayName(new ItemStack(Item.itemsList[i].itemID, 1, 0)) + "\");");
				}
			}
		}
	}
	
	
	public static void debugList()
	{
		for (Entry<Integer, SpiList> key : SpiTools.getList(SpiSetting.BLOCK).entrySet())
		{
			System.out.println("id="+ key.getKey() +":iid="+ key.getValue().getBlock().blockID +":ob="+ key.getValue().getObject() +":mt="+ key.getValue().getMataData());
		}
		for (Entry<Integer, SpiList> key : SpiTools.getList(SpiSetting.ITEM).entrySet())
		{
			System.out.println("id="+ key.getKey() +":iid="+ key.getValue().getItem().itemID +":ob="+ key.getValue().getObject() +":mt="+ key.getValue().getMataData());
		}
	
	}
}
