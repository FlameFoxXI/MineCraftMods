package mods.ffxibuildassistblock.core;


import java.util.logging.Level;

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
		FMLLog.log(FFxiSet.MODNAME, Level.SEVERE, format, data);
	}
	
	public static void warning(String format, Object... data)
	{
		FMLLog.log(FFxiSet.MODNAME, Level.WARNING, format, data);
	}

	public static void log(String format, Object... data)
	{
		FMLLog.log(FFxiSet.MODNAME, Level.INFO, format, data);
	}
}
