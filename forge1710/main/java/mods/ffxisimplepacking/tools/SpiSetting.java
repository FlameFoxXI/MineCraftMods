package mods.ffxisimplepacking.tools;

import java.util.HashMap;
import java.util.Map;

import mods.ffxisimplepacking.creativetab.CreativeTabsSimplePacking;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class SpiSetting
{
	//Configuration Setting Member
	public static int SimplePackingItemID = 1210;
	public static int SimplePackingBlockID = 1220;
	public static boolean tabs_HiddenToCreativeTabs = true;
	public static boolean tabs_OriginalCreativeTabs = true;
	public static boolean comp_MoreCompress = true;
	public static boolean recipe_RecipeOff = false;
	public static boolean recipe_RecipeRotation = false;
	public static boolean recipe_RecipeOption = false;
	public static boolean drow_InventoryItemFlat = false;
	public static boolean drow_StackItemAtOnce = false;
	public static boolean drop_PackingBlockDrop = true;
	public static boolean func_ConvertOldVersion = false;
	
	public static final int spiTypeMax = 3;
	public static final int spiTypeTableMax = spiTypeMax + 1;
	
	//Block
	public static Block[] spiblock = new Block[spiTypeTableMax];
	public static Block[] spiItem = new Block[spiTypeTableMax];
	
	//item list
	public static int spiListMax = 0xFFFF;
	public static Map<Integer, SpiList> spiListBlock = new HashMap();
	public static Map<Integer, SpiList> spiListItem = new HashMap();
	public static String BLOCK = "block"; 
	public static String ITEM = "item"; 
	
	//image
	public static String imageBlock[] = 
		{
			"simplepacking",
			"simplecompress",
			"simplecompress1",
			"simplecompress2"
		};

	public static String nemeBlock[] = 
		{
			"FFxiSimplePackingBlock",
			"FFxiSimpleCompressBlock",
			"FFxiSimpleCompressBlock1",
			"FFxiSimpleCompressBlock2"
		};
	
	public static String nemeItem[] = 
		{
			"FFxiSimplePackingItem",
			"FFxiSimpleCompressItem",
			"FFxiSimpleCompressItem1",
			"FFxiSimpleCompressItem2"
		};

	public static String nemeSpi[] = 
		{
			"Pac ",
			"Cmp ",
			"Mcp ",
			"Mmc "
		};

	public static int stakBlock[] = { 8, 64, 512, 4096 };

	//render
	public static int renderType = 0;

	//recipe
	public static String rp[] = 
		{
			"",
			"",
			""
		};
	
	public static int renderStakBlock[] =
		{
			1,
			2,
			6,
			21
		};
	
	public static int renderStakItem[] =
		{
			1,
			2,
			16,
			32
		};
	
	public static CreativeTabs tabSpiItem = null;
	public static CreativeTabs tabSpiBlock = null;
}
