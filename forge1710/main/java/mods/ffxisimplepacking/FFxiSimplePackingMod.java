package mods.ffxisimplepacking;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import mods.ffxisimplepacking.block.BlockSimplePackingBlock;
import mods.ffxisimplepacking.block.BlockSimplePackingItem;
import mods.ffxisimplepacking.creativetab.CreativeTabsSimplePacking;
import mods.ffxisimplepacking.handler.ToolFuelHandler;
import mods.ffxisimplepacking.item.ItemSimplePackingBlock;
import mods.ffxisimplepacking.item.ItemSimplePackingItem;
import mods.ffxisimplepacking.proxy.CommonProxySimplePacking;
import mods.ffxisimplepacking.tileentity.TileEntitySimplePacking;
import mods.ffxisimplepacking.tools.SpiConvert;
import mods.ffxisimplepacking.tools.SpiItems;
import mods.ffxisimplepacking.tools.SpiList;
import mods.ffxisimplepacking.tools.SpiSetting;
import mods.ffxisimplepacking.tools.SpiTools;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.IRenderContextHandler;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.config.Configuration;

@Mod(modid = FFxiSimplePackingMod.MODID, name = FFxiSimplePackingMod.MODNAME, version = FFxiSimplePackingMod.MODVERSION)
public class FFxiSimplePackingMod
{
	public final static String MODID = "FFxiSimplePackingMod";
	public final static String MODNAME = "FFxiSimplePackingMod";
	public final static String MCVERSION = "1.7.2";
	public final static String MODVERSION = MCVERSION +"_"+ "1.0.0";

	@SidedProxy(clientSide="mods.ffxisimplepacking.proxy.ClientProxySimplePacking", serverSide="mods.ffxisimplepacking.proxy.CommonProxySimplePacking")
	public static CommonProxySimplePacking proxy;

	@Mod.Instance("FFxiSimplePackingMod")
	public static FFxiSimplePackingMod instance;

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		this.setConfigrationfile(event.getSuggestedConfigurationFile());

		this.createCreativeTabs();
		this.setGameRegistryBlocks(SpiSetting.BLOCK);
		this.setGameRegistryBlocks(SpiSetting.ITEM);
		this.setGameRegistryTileEntity();
	}

	@Mod.EventHandler
	public void init(FMLInitializationEvent event)
	{
		SpiItems.regListToAll();
//		SpiTools.debugList();
		this.setRecipeSimplePackingSetting();
		this.setRecipeOptionsBlock();
		this.setRecipeSimplePacking(SpiSetting.BLOCK);
		this.setRecipeSimplePacking(SpiSetting.ITEM);
	}

	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
		GameRegistry.registerFuelHandler(new ToolFuelHandler());
		this.proxy.registerRenderInformation();
		
		SpiConvert.getConvertItemforOld();
		SpiConvert.regConvertListToAll();
		SpiConvert.addConvertRecipe();
		SpiConvert.addConvertRecipePlus();
//		SpiTools.getAllItemListAAA();
//		SpiTools.printOutListName();
	}

	private void setGameRegistryBlocks(String text)
	{
		for (int i = 0; i < SpiSetting.spiTypeTableMax; i++)
		{
			if (text.equalsIgnoreCase(SpiSetting.BLOCK))
			{
				SpiTools.setBlockInstance(text, i, (new BlockSimplePackingBlock(Material.glass, i))
						.setBlockName(SpiTools.getSpiName(text, i)).setBlockTextureName(SpiSetting.imageBlock[i]).setCreativeTab(this.addCreativeTabs(text, i)));
				GameRegistry.registerBlock(SpiTools.getBlockInstance(text, i), ItemSimplePackingBlock.class, SpiTools.getSpiName(text, i));
			}
			else
			{
				SpiTools.setBlockInstance(text, i, (new BlockSimplePackingItem(Material.glass, i))
						.setBlockName(SpiTools.getSpiName(text, i)).setBlockTextureName(SpiSetting.imageBlock[i]).setCreativeTab(this.addCreativeTabs(text, i)));
				GameRegistry.registerBlock(SpiTools.getBlockInstance(text, i), ItemSimplePackingItem.class, SpiTools.getSpiName(text, i));
			}
		}			
	}

	private void setGameRegistryTileEntity()
	{
		GameRegistry.registerTileEntity(TileEntitySimplePacking.class, "SimplePackingTileEntity");
	}

	public void setRecipeSimplePackingSetting()
	{
		if(!SpiSetting.recipe_RecipeRotation)
		{
			SpiSetting.rp[0] = "XXZ";
			SpiSetting.rp[1] = "XXX";
			SpiSetting.rp[2] = "XXX";
		}
		else{
			SpiSetting.rp[0] = "XXX";
			SpiSetting.rp[1] = "XXX";
			SpiSetting.rp[2] = "ZXX";
		}
	}

	private void setConfigrationfile(File file)
	{
		Configuration config = new Configuration(file);
		try{
			config.load();
			SpiSetting.tabs_HiddenToCreativeTabs = config.get("tabs", "tabs_HiddenToCreativeTabs", SpiSetting.tabs_HiddenToCreativeTabs).getBoolean(true);
			SpiSetting.tabs_OriginalCreativeTabs = config.get("tabs", "tabs_OriginalCreativeTabs", SpiSetting.tabs_OriginalCreativeTabs).getBoolean(true);
			SpiSetting.comp_MoreCompress = config.get("comp", "comp_MoreCompress", SpiSetting.comp_MoreCompress).getBoolean(true);
			SpiSetting.recipe_RecipeOff = config.get("recipe", "recipe_RecipeOff", SpiSetting.recipe_RecipeOff).getBoolean(true);
			SpiSetting.recipe_RecipeRotation = config.get("recipe", "recipe_RecipeRotation", SpiSetting.recipe_RecipeRotation).getBoolean(true);
			SpiSetting.recipe_RecipeOption = config.get("recipe", "recipe_RecipeOption", SpiSetting.recipe_RecipeOption).getBoolean(true);
			SpiSetting.drow_InventoryItemFlat = config.get("drow", "drow_InventoryItemFlat", SpiSetting.drow_InventoryItemFlat).getBoolean(true);
			SpiSetting.drow_StackItemAtOnce = config.get("drow", "drow_StackItemAtOnce", SpiSetting.drow_StackItemAtOnce).getBoolean(true);
			SpiSetting.drop_PackingBlockDrop = config.get("drow", "drop_PackingBlockDrop", SpiSetting.drop_PackingBlockDrop).getBoolean(true);
			SpiSetting.func_ConvertOldVersion = config.get("func", "func_ConvertOldVersion", SpiSetting.func_ConvertOldVersion).getBoolean(true);
			
		}
		catch (Exception e)
		{
			//Error Log Printout
			FMLLog.warning(MODID + " : error configuration file");
		}
		finally
		{
			config.save();
		}
	}

	private void createCreativeTabs()
	{
		if (!SpiSetting.tabs_HiddenToCreativeTabs && SpiSetting.tabs_OriginalCreativeTabs)
		{
			SpiSetting.tabSpiBlock = new CreativeTabsSimplePacking("tabSpiBlock", SpiSetting.BLOCK);
			SpiSetting.tabSpiItem = new CreativeTabsSimplePacking("tabSpiItem", SpiSetting.ITEM);
		}
	}
	
	private CreativeTabs addCreativeTabs(String type, int no)
	{
		CreativeTabs tab = null;
		
		if (no >= 2)
		{
			return null;
		}

		if (!SpiSetting.tabs_HiddenToCreativeTabs)
		{
			if (SpiSetting.tabs_OriginalCreativeTabs)
			{
				if (type.equalsIgnoreCase(SpiSetting.BLOCK))
				{
					tab = SpiSetting.tabSpiBlock;
				}
				else
				{
					tab = SpiSetting.tabSpiItem;
				}
			}
			else
			{
				if (type.equalsIgnoreCase(SpiSetting.BLOCK))
				{
					tab = CreativeTabs.tabBlock;
				}
				else
				{
					tab = CreativeTabs.tabMaterials;
				}
			}
			if (!SpiSetting.comp_MoreCompress && no >= 1)
			{
				tab = null;
			}
		}
		return tab;
	}

	public void setRecipeOptionsBlock()
	{
		if (!SpiSetting.recipe_RecipeOption)
		{
			GameRegistry.addShapelessRecipe(new ItemStack(Blocks.sandstone,1,1)      , new Object[]{ new ItemStack(Blocks.sandstone,1,Short.MAX_VALUE), Blocks.dirt });
			GameRegistry.addShapelessRecipe(new ItemStack(Blocks.sandstone,1,2)      , new Object[]{ new ItemStack(Blocks.sandstone,1,Short.MAX_VALUE), Blocks.sand });
			GameRegistry.addShapelessRecipe(new ItemStack(Blocks.stonebrick,1,1)     , new Object[]{ new ItemStack(Blocks.stonebrick,1,Short.MAX_VALUE), Blocks.vine });
			GameRegistry.addShapelessRecipe(new ItemStack(Blocks.stonebrick,1,2)     , new Object[]{ new ItemStack(Blocks.stonebrick,1,Short.MAX_VALUE), Blocks.gravel });
			GameRegistry.addShapelessRecipe(new ItemStack(Blocks.stonebrick,1,3)     , new Object[]{ new ItemStack(Blocks.stonebrick,1,Short.MAX_VALUE), Blocks.dirt });
		}
	}

	public void setRecipeSimplePacking(String type)
	{
		if (SpiSetting.recipe_RecipeOff)
		{
			return;
		}
		
		for(Entry<Integer, SpiList> entry : SpiTools.getList(type).entrySet())
		{
			if (entry.getValue() != null && entry.getValue() != null && entry.getValue().getObject() != null && entry.getValue().getCraftObject() != null)
			{
				if(entry.getValue().isCraftItemBlock())
				{
					GameRegistry.addRecipe(new ItemStack(SpiTools.getBlockInstance(type, 0), 1, entry.getKey()),
							new Object[]{ SpiSetting.rp[0],SpiSetting.rp[1],SpiSetting.rp[2], Character.valueOf('X'), new ItemStack(entry.getValue().getCraftBlock(), 1, entry.getValue().getMataData()) });
					GameRegistry.addShapelessRecipe(new ItemStack(entry.getValue().getCraftBlock(), 8, entry.getValue().getMataData()), 
							new Object[]{ new ItemStack(SpiTools.getBlockInstance(type, 0), 1, entry.getKey()) });
				}
				else
				{
					GameRegistry.addRecipe(new ItemStack(SpiTools.getBlockInstance(type, 0), 1, entry.getKey()),
							new Object[]{ SpiSetting.rp[0],SpiSetting.rp[1],SpiSetting.rp[2], Character.valueOf('X'), new ItemStack(entry.getValue().getCraftItem(), 1, entry.getValue().getMataData()) });
					GameRegistry.addShapelessRecipe(new ItemStack(entry.getValue().getCraftItem(), 8, entry.getValue().getMataData()),
							new Object[]{ new ItemStack(SpiTools.getBlockInstance(type, 0), 1, entry.getKey()) });
				}

				if (SpiSetting.comp_MoreCompress)
				{
					GameRegistry.addRecipe(new ItemStack(SpiTools.getBlockInstance(type, 1), 1, entry.getKey()),
							new Object[]{ SpiSetting.rp[0],SpiSetting.rp[1],SpiSetting.rp[2], Character.valueOf('X'), new ItemStack(SpiTools.getBlockInstance(type, 0), 1, entry.getKey()) });

					GameRegistry.addShapelessRecipe(new ItemStack(SpiTools.getBlockInstance(type, 0), 8, entry.getKey()),
							new Object[]{ new ItemStack(SpiTools.getBlockInstance(type, 1), 1, entry.getKey()) });

					GameRegistry.addRecipe(new ItemStack(SpiTools.getBlockInstance(type, 2), 1, entry.getKey()),
							new Object[]{ SpiSetting.rp[0],SpiSetting.rp[1],SpiSetting.rp[2], Character.valueOf('X'), new ItemStack(SpiTools.getBlockInstance(type, 1), 1, entry.getKey()) });

					GameRegistry.addShapelessRecipe(new ItemStack(SpiTools.getBlockInstance(type, 1), 8, entry.getKey()),
							new Object[]{ new ItemStack(SpiTools.getBlockInstance(type, 2), 1, entry.getKey()) });

					GameRegistry.addRecipe(new ItemStack(SpiTools.getBlockInstance(type, 3), 1, entry.getKey()),
							new Object[]{ SpiSetting.rp[0],SpiSetting.rp[1],SpiSetting.rp[2], Character.valueOf('X'), new ItemStack(SpiTools.getBlockInstance(type, 2), 1, entry.getKey()) });

					GameRegistry.addShapelessRecipe(new ItemStack(SpiTools.getBlockInstance(type, 2), 8, entry.getKey()),
							new Object[]{ new ItemStack(SpiTools.getBlockInstance(type, 3), 1, entry.getKey()) });
				}
			}
		}
	}
}