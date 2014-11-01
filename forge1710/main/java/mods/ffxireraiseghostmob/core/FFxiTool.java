package mods.ffxireraiseghostmob.core;



import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemSword;
import net.minecraft.util.ObjectIntIdentityMap;
import net.minecraft.util.RegistryNamespaced;

import org.apache.logging.log4j.Level;

import mods.ffxireraiseghostmob.core.FFxiSet;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.ModContainer;
import cpw.mods.fml.common.registry.FMLControlledNamespacedRegistry;
import cpw.mods.fml.common.registry.GameData;


public class FFxiTool
{
	protected static Random rand = new Random();

	public static ModContainer instance()
	{
		return FMLCommonHandler.instance().findContainerFor(FFxiSet.MODID);
	}
		
	public static void severe(String format, Object... data)
	{
		FMLLog.log(FFxiSet.MODNAME, Level.ERROR, format, data);
	}
	
	public static void warning(String format, Object... data)
	{
		FMLLog.log(FFxiSet.MODNAME, Level.WARN, format, data);
	}

	public static void log(String format, Object... data)
	{
		FMLLog.log(FFxiSet.MODNAME, Level.INFO, format, data);
	}
	
	public static ObjectIntIdentityMap getBlockObjectIntIdentityMap()
	{
		FMLControlledNamespacedRegistry<Item> tmpFMLControlledNamespacedRegistry = GameData.getItemRegistry();
		try
		{
			Field tmpField = RegistryNamespaced.class.getDeclaredFields()[0];
			tmpField.setAccessible(true);
			return ((ObjectIntIdentityMap)tmpField.get(tmpFMLControlledNamespacedRegistry));
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	public static ArrayList<ArrayList<Item>> getItemList()
	{
		ArrayList<ArrayList<Item>> itemList = new ArrayList();
		
		ArrayList<Item> tmpSowrd = new ArrayList();
		ArrayList<Item> tmpHead = new ArrayList();
		ArrayList<Item> tmpBody = new ArrayList();
		ArrayList<Item> tmpLegs = new ArrayList();
		ArrayList<Item> tmpFeet = new ArrayList();

		FMLControlledNamespacedRegistry<Item> temp = (FMLControlledNamespacedRegistry<Item>)GameData.getItemRegistry();

		ObjectIntIdentityMap tmp = FFxiTool.getBlockObjectIntIdentityMap();
		Iterator iterator = tmp.iterator();
		while (iterator.hasNext())
		{
			Item item = (Item)iterator.next();

			if(item instanceof ItemSword)
			{
				tmpSowrd.add(item);
			}
			else if (item instanceof ItemArmor)
			{
				switch (((ItemArmor)item).armorType)
				{
				case 0:
					tmpHead.add(item);
					break;
				case 1:
					tmpBody.add(item);
					break;
				case 2:
					tmpLegs.add(item);
					break;
				case 3:
					tmpFeet.add(item);
					break;
				default:
					break;
				}
			}
		}
		itemList.add(tmpSowrd);
		itemList.add(tmpFeet);
		itemList.add(tmpLegs);
		itemList.add(tmpBody);
		itemList.add(tmpHead);
		
		return itemList;
	}
	
	public static Item getEquipItems(int equipType)
	{
		if (5 <= equipType)
		{
			return null;
		}

		ArrayList<Item> tmpEquipmentItem = FFxiSet.equipmentList.get(equipType);
		
		switch (equipType)
		{
		case 0:
			if (FFxiSet.equipmentList.get(equipType) != null && !FFxiSet.equipmentList.get(equipType).isEmpty())
			{
				return FFxiSet.equipmentList.get(equipType).get(rand.nextInt(FFxiSet.equipmentList.get(equipType).size()));
			}
			break;
		case 1:
		case 2:
		case 3:
		case 4:
			if (FFxiSet.equipmentList.get(equipType) != null && !FFxiSet.equipmentList.get(equipType).isEmpty() && rand.nextInt(2) == 0)
			{
				return FFxiSet.equipmentList.get(equipType).get(rand.nextInt(FFxiSet.equipmentList.get(equipType).size()));
			}
			break;
		default:
			break;

		}
		return null;
	}
}
