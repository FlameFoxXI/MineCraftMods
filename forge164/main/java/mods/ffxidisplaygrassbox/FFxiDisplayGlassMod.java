package mods.ffxidisplaygrassbox;

import java.io.File;

import mods.ffxidisplaygrassbox.block.BlockDisplayGlass;
import mods.ffxidisplaygrassbox.proxy.CommonProxy;
import mods.ffxidisplaygrassbox.tileentity.TileEntityDisplayGlass;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.Configuration;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;

@Mod(modid=FFxiDisplayGlassMod.MODID, name=FFxiDisplayGlassMod.MODNAME, version=FFxiDisplayGlassMod.MODVERSION)
@NetworkMod(clientSideRequired = true, serverSideRequired = false)
public class FFxiDisplayGlassMod
{
	public static final String MODID = "FFxiDisplayGlassMod";
	public static final String MODNAME = "FFxiDisplayGlassMod";
	public static final String MCVERSION = "1.6.4";
	public static final String MODVERSION = MCVERSION + "_" + "1.0.1";
	public static final String MODCLIENT = "mods.ffxidisplaygrassbox.proxy.ClientProxy";
	public static final String MODSERVER = "mods.ffxidisplaygrassbox.proxy.CommonProxy";
	
	@Mod.Instance(FFxiDisplayGlassMod.MODID)
	public static FFxiDisplayGlassMod instance;

	@SidedProxy(clientSide=FFxiDisplayGlassMod.MODCLIENT, serverSide=FFxiDisplayGlassMod.MODSERVER)
	public static CommonProxy proxy;	

	public static int blockDisplayGlassID = 2014;
	public static boolean blockDisplayStacks = true;
	public static boolean blockRotationItem = true;
	public static boolean blockDisplayLighting = true;

	public static Block blockDisplayGlass;
	public static int renderType = 0;
	
	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		configrationfile(event.getSuggestedConfigurationFile());

		float light = blockDisplayLighting ? 1.0F : 0.0F;

		blockDisplayGlass = new BlockDisplayGlass(blockDisplayGlassID, Material.glass);
		blockDisplayGlass.setHardness(2.5F).setLightValue(light).setStepSound(Block.soundGlassFootstep).setUnlocalizedName("displayGras").setCreativeTab(CreativeTabs.tabTools);

		GameRegistry.registerBlock(blockDisplayGlass, "displayGras");
		GameRegistry.registerTileEntity(TileEntityDisplayGlass.class, "TileEntityDisplayGras");
	}

	@Mod.EventHandler
	public void init(FMLInitializationEvent event)
	{
		proxy.registerRender();
		addItemRecipe();
	}
	
	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
	}
	
	private void configrationfile(File file)
	{
		Configuration config = new Configuration(file);
		try
		{
			blockDisplayGlassID = config.getBlock("blockDisplayGlassID", blockDisplayGlassID, "block id for Display glass").getInt();
			blockDisplayLighting = config.get("setting", "blockLighting", blockDisplayLighting, "block Lighting around.").getBoolean(true);
			blockDisplayStacks = config.get("setting", "blockDisplayStacks", blockDisplayStacks, "items render stacks.").getBoolean(true);
			blockRotationItem = config.get("setting", "blockMoveingItem", blockRotationItem, "display item is rotation.").getBoolean(true);
		}
		catch (Exception e)
		{
			FMLLog.warning(MODNAME + " has a problem loading it's configuration");
		}
		finally
		{
			config.save();
		}
	}

	private void addItemRecipe()
	{
		GameRegistry.addRecipe(new ItemStack(blockDisplayGlass, 1),
				new Object[] { "GGG", "GTG", "GGG", 'G', Block.glass, 'T', Block.workbench });
	}
}
