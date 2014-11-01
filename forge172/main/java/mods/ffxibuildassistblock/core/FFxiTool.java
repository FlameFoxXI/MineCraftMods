package mods.ffxibuildassistblock.core;

import org.apache.logging.log4j.Level;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.ModContainer;


public class FFxiTool
{
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
}
