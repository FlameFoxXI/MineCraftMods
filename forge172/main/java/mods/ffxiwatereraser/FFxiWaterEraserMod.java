package mods.ffxiwatereraser;

import net.minecraftforge.common.MinecraftForge;
import mods.ffxiwatereraser.core.FFxiSet;
import mods.ffxiwatereraser.proxy.FFxiCommonProxy;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

@Mod(modid=FFxiSet.MODID, name=FFxiSet.MODNAME, version=FFxiSet.MODVERSION)
public class FFxiWaterEraserMod
{
	@Mod.Instance(FFxiSet.MODID)
	public static FFxiWaterEraserMod instance;

	@SidedProxy(clientSide=FFxiSet.MODCLIENT, serverSide=FFxiSet.MODSERVER)
	public static FFxiCommonProxy proxy;

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		FFxiSet.configrationfile(event.getSuggestedConfigurationFile());
		FFxiSet.registerModBlocks();
		FFxiSet.registerModItems();
		FFxiSet.registerModEntitys();
		FFxiSet.registerModTileEntitys();
	}

	@Mod.EventHandler
	public void init(FMLInitializationEvent event)
	{
		FFxiSet.registerRecipeItems();
		this.proxy.registerModRenders();
		this.proxy.registerModRenderEntitys();
		this.proxy.registerModRenderTileEntitys();
		this.proxy.registerModSideHandlers();
		FFxiSet.registerModHandlers();
	}

	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
	}
}