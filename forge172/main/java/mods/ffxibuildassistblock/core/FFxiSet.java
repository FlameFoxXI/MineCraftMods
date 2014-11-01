package mods.ffxibuildassistblock.core;

import java.io.File;

import mods.ffxibuildassistblock.block.BlockFFxiBuildAssist;
import mods.ffxibuildassistblock.item.ItemFFxiBuildAssist;
import mods.ffxibuildassistblock.tileentity.TileEntityFFxiBuildAssist;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.common.config.Configuration;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class FFxiSet
{
	public static final String MODID = "FFxiBuildAssistBlockMod";
	public static final String MODNAME = "FFxiBuildAssistBlockMod";
	public static final String MCVERSION = "1.7.2";
	public static final String MODVERSION = MCVERSION + "_" + "1.0.0";
	public static final String MODCLIENT = "mods.ffxibuildassistblock.proxy.FFxiClientProxy";
	public static final String MODSERVER = "mods.ffxibuildassistblock.proxy.FFxiCommonProxy";
	
	private static final String image_AssetFolda = "ffxibuildassistblock:";
	private static final String image_AssistScaffold = "scaffold";
	public static final String image_AssistScaffoldWood = "wood";
	public static final String image_AssistScaffoldIron = "iron";
	
	public static Block blockFFxiBuildAssist;
	
	public static final String[] blockNames = { "AssistScaffoldWood" , "AssistScaffoldIron" };
	
	public static void registerModItems() { }

	public static void registerModBlocks()
	{
		blockFFxiBuildAssist = new BlockFFxiBuildAssist(Material.piston).setBlockName("FFxiBuildAssistScaffold")
				.setBlockTextureName(image_AssetFolda + image_AssistScaffold).setCreativeTab(CreativeTabs.tabBlock);
		GameRegistry.registerBlock(blockFFxiBuildAssist, ItemFFxiBuildAssist.class, "FFxiBuildAssistScaffold");
	}

	public static void registerModEntitys() { }
	
	public static void registerModTileEntitys()
	{
		GameRegistry.registerTileEntity(TileEntityFFxiBuildAssist.class, "TileEntityFFxiBuildAssist");
	}
	
	public static void registeraddRecipeBlocks()
	{
		GameRegistry.addShapedRecipe(new ItemStack(blockFFxiBuildAssist, 4, 0),
				new Object[] { "#S#", "S#S", "#S#", 'S', Items.stick });
		GameRegistry.addShapedRecipe(new ItemStack(blockFFxiBuildAssist, 16, 1),
				new Object[] { "#I#", "I#I", "#I#", 'I', Items.iron_ingot });
	}
	
	public static void registerModHandlers() { }
	
	@SideOnly(Side.CLIENT)
	public static void registerModClientHandlers()
	{
		
	}
}