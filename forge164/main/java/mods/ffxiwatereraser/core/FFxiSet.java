package mods.ffxiwatereraser.core;

import java.io.File;

import mods.ffxiwatereraser.event.FFxiLivingUpdateEvent;
import mods.ffxiwatereraser.item.ItemWaterEraser;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class FFxiSet
{
	public static final String MODID = "FFxiWaterEraserMod";
	public static final String MODNAME = "FFxiWaterEraserMod";
	public static final String MCVERSION = "1.6.4";
	public static final String MODVERSION = MCVERSION + "_" + "1.0.0";
	public static final String MODCLIENT = "mods.ffxiwatereraser.proxy.FFxiClientProxy";
	public static final String MODSERVER = "mods.ffxiwatereraser.proxy.FFxiCommonProxy";
	
	public static int itemWaterEraserId = 5210;
	public static Item itemWaterEraser;
	
	public static String commandUserName = "";
	public static boolean commandRunning = false;
	public static int searchX = 0;
	public static int searchY = 0;
	public static int searchZ= 0;
	
	public static int erase_width = 10;
	public static int erase_height = 20;
	public static int erase_blocks = 500;
	public static int erase_width_min = -10;
	public static int erase_height_min = -20;
	
	public static void configrationfile(File file)
	{
		Configuration config = new Configuration(file);
		try
		{
			itemWaterEraserId = config.getItem("itemWaterWraserId", itemWaterEraserId, "WaterEraser itemId").getInt();
			erase_width = config.get("erase", "erase_wideth", erase_width, "erase width. max40(size:80block). min5(size:10block).").getInt();
			erase_width = mathMaxMin(40, 5, erase_width);
			erase_height = config.get("erase", "erase_height", erase_height, "erase height. max50(size:100block). min5(size:10block).").getInt();
			erase_height = mathMaxMin(50, 5, erase_height);
			erase_blocks = config.get("erase", "erase_blocks", erase_blocks, "1 tick erase max block size. max1000, min300").getInt();
			erase_height = mathMaxMin(1000, 300, erase_height);
		}
		catch (Exception e)
		{
			FFxiTool.severe("a problem loading it's configuration (%s)", MODID);
		}
		finally
		{
			config.save();
		}
		erase_width_min = (0 - erase_width);
		erase_height_min = (0 - erase_height);
	
	}
	
	public static void registerModItems()
	{
		itemWaterEraser = new ItemWaterEraser(itemWaterEraserId).setCreativeTab(CreativeTabs.tabTools).setTextureName("egg").setUnlocalizedName("FFxiWaterWraser");
		GameRegistry.registerItem(itemWaterEraser, "FFxiWaterWraser");
	}

	public static void registerModBlocks() { }

	public static void registerModEntitys() { }
	
	public static void registerModTileEntitys() { }
	
	public static void registerModHandlers()
	{
		MinecraftForge.EVENT_BUS.register(new FFxiLivingUpdateEvent());
	}

	public static void registerRecipeItems()
	{
		GameRegistry.addRecipe(new ItemStack(itemWaterEraser, 1),
				new Object[] { "PDP", "DED", "PDP", 'D', Item.diamond, 'E', Item.emerald, 'P', Item.enderPearl }); 
	}
	
	@SideOnly(Side.CLIENT)
	public static void registerModClientHandlers()
	{
		
	}
	
	public static int mathMaxMin(int max, int min, int value)
	{
		return Math.max(min, Math.min(max, value));
	}
}