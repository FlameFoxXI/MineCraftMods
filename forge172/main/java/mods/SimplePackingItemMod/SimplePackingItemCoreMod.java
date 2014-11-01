package mods.SimplePackingItemMod;

import java.util.logging.Level;

import mods.SimplePackingItemMod.proxy.*;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.IRenderContextHandler;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.config.Configuration;

@Mod(modid = SimplePackingItemCoreMod.MODID, name = SimplePackingItemCoreMod.MODNAME, version = SimplePackingItemCoreMod.MODVERSION)
public class SimplePackingItemCoreMod
{
	public final static String MODID = "SimplePackingItemMod";
	public final static String MODNAME = "Simple PackingMod Mod";
	public final static String MCVERSION = "1.7.2";
	public final static String MODVERSION = "1.0.0.a";

	@SidedProxy(clientSide="mods.SimplePackingItemMod.proxy.ClientProxy", serverSide="mods.SimplePackingItemMod.proxy.CommonProxy")
	public static CommonProxy proxy;

	@Mod.Instance("SimplePackingItemMod")
	public static SimplePackingItemCoreMod instance;

	//Configuration Setting Member
	public static boolean SimplePackingItemUseBlock;
	public static boolean SimplePackingItemUseItem;
	public static boolean SimplePackingItemHideCreativeTabs;
	public static boolean SimplePackingItemMoreCompress;
	public static boolean SimplePackingItemRecipeOff;
	public static boolean SimplePackingItemRecipeRotation;
	public static boolean SimplePackingItemRecipeOption;

	public static boolean debugLanguageOutPut = false;
	
	//Class Member
	public static Item SimplePackingItem;
	public static Item SimplePackingItemItem;

	public static String Recipemold[] = {"","",""};

	public static int SimplePackingItemMax = 127;
	public static int SimplePackingCompressItemStartIndex = SimplePackingItemMax + 1;
	public static int SimplePackingCompressItemMax = SimplePackingCompressItemStartIndex + SimplePackingItemMax;
	public static int SimplePackingCompressTableMax = SimplePackingCompressItemMax + 1;

	public static String SimplePackingItemStringUS[] = {
		"Block of Coal","Block of Iron","Block of Gold","Block of Diamond","Block of Emerald","Lapis Lazuli Block","Block of Redstone","Block of Glowdust","Block of Quartz","","","","","Coal Ore","Iron Ore","Gold Ore",
		"Diamond Ore","Emerald Ore","Lapis Lazuli Ore","Redstone Ore","Block of Glowstone","Nether Quartz Ore","","","","","Dirt","Gravel","Sand","Sand Stone","Chiseled Sand Stone","Smooth Sand Stone",
		"Chiseled Quartz Block","Pillar Quartz Block","","","Glass","Ice","Snow","Snow Block","Mycelium","","","","","","Stone","Cobblestone",
		"Stone Brick","Mossy Stone Brick","Cracked Stone Brick","Chiseled Stone Brick","Moss Stone","","","","Clays","Bricks","Obsidian","Soul Sand","Nether Brick","Netherack","End Stone","",
		"","","","","Ork Wood Planks","Spruce Wood Planks","Brich Wood Planks","Jungle Wood Planks","","","Ork Wood","Spruce Wood","Brich Wood","Jungle Wood","","",
		"White Wool","Orange Wool","Magenta Wool","Light Blue Wool","Yellow Wool","Light Green Wool","Pink Wool","Gray Wool","Light Gray Wool"," Cyan Wool","Purple Wool","Blue Wool","Brown Wool","Dark Green Wool","Red Wool","Black Wool",
		"","","","","","","","","","","","","","","","",
		"","","","","","","","","","","","","","","",""
	};
	public static String SimplePackingItemItemStringUS[] = {
		"Coal","Iron Ingot","Gold Ingot","Diamond","Emerald","Redstone","Glowdust","","","","Gunpowder","Flint","Clay","Brick","Nether Quartz","Nether Brick",
		"Blaze Rod","Gold Nugget","Ghast Tear","Nether Star","","","","Stick","Bowl","Feather","String","Leather","Slimeball","Ender Pearl","Eyes of Ender","",
		"","","Bone","Paper","Book","","Empty Map","","","","Bottle o'Enchanting","Fire Charge","Firework Star","","","",
		"Seed","Wheat","Sugar Canes","Pumpkin Seeds","Melon Seeds","Nether Wart","","","","Sugar","","","","Ink Sac","Rose Red","Cactus Green",
		"Cocoa Beans","Lapis Lazuli","Purple Dye","Cyan Dye","Light Gray Dye","Gray Dye","Pink Dye","Lime Dye","Dandelion Yellow","Light Blue Dye","Magenta Dye","Orange Dye","Bone Meal","","","",
		"","","","","","","","","","","","","","Apple","Golden Apple","Golden Apple",
		"Mushroom Stew","Bread","Raw Porkchop","Cooked Porkchop","Raw Fish","Cooked Fish","Cake","Cookie","Melon","Raw Beef","Steak","Raw Chicken","Cooked Chicken","Carrot","Golden Carrot","Potato",
		"Baked Potato","Poisonous Potato","Pumpkin pie","","","","Rotten Flesh","Spider Eye","Egg","","","","","","",""
	};

	//Texture Setting Member
	public static String textureSimplePackingItem = "/mods/SimplePackingItemMod/textures/items/SimplePackingItem.png";
	public static String textureSimplePackingItemItem = "/mods/SimplePackingItemMod/textures/items/SimplePackingItemItem.png";


    @Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		//Configuration Load & Save
		Configuration config = new Configuration(event.getSuggestedConfigurationFile());
		try{
			config.load();
			SimplePackingItemUseBlock = config.get(Configuration.CATEGORY_GENERAL, "SimplePackingItemUseBlock", true).getBoolean(true);
			SimplePackingItemUseItem = config.get(Configuration.CATEGORY_GENERAL, "SimplePackingItemUseItem", true).getBoolean(true);
			SimplePackingItemHideCreativeTabs = config.get(Configuration.CATEGORY_GENERAL, "SimplePackingItemHideCreativeTabs", false).getBoolean(true);
			SimplePackingItemMoreCompress = config.get(Configuration.CATEGORY_GENERAL, "SimplePackingItemMoreCompress", true).getBoolean(true);
			SimplePackingItemRecipeOff = config.get(Configuration.CATEGORY_GENERAL, "SimplePackingItemRecipeOff", false).getBoolean(true);
			SimplePackingItemRecipeRotation = config.get(Configuration.CATEGORY_GENERAL, "SimplePackingItemRecipeRotation", false).getBoolean(true);
			SimplePackingItemRecipeOption = config.get(Configuration.CATEGORY_GENERAL, "SimplePackingItemRecipeOption", false).getBoolean(true);
		}
		catch (Exception e)
		{
			//Error Log Printout
			FMLLog.severe("Simple SimplePackingItemMod : Error configuration file", e);
		}
		finally
		{
			config.save();
		}

		proxy.registerRenderInformation();

		setRecipeSimplePackingSetting();

		if(SimplePackingItemUseBlock){
			setSimplePackingBlocks();
			regiSimplePackingBlocks();
			if (debugLanguageOutPut){
				regiSimplePackingBlockNames();
			}
			if(!SimplePackingItemRecipeOff){
				addRecipeSimplePackingBlocks();
			}
		}

		if(SimplePackingItemUseItem){
			setSimplePackingItems();
			regiSimplePackingitems();
			if (debugLanguageOutPut){
				regiSimplePackingItemNames();
			}
			if(!SimplePackingItemRecipeOff){
				addRecipeSimplePackingItems();
			}
		}

		if(SimplePackingItemRecipeOption){
			addRecipeOptionsBlock();
		}

		
		
		GameRegistry.registerFuelHandler(new ToolFuelHandler());
	}

	public void setSimplePackingBlocks(){
		SimplePackingItem = (new PackingItem()).setUnlocalizedName("SimplePackingItem");
	}

	public void setSimplePackingItems(){
		SimplePackingItemItem = (new PackingItemItem()).setUnlocalizedName("SimplePackingItemItem");
	}

	public void regiSimplePackingBlocks(){
		GameRegistry.registerItem(SimplePackingItem, "SimplePacking");
	}

	public void regiSimplePackingitems(){
		GameRegistry.registerItem(SimplePackingItemItem, "SimplePackingItem");
	}

	public void regiSimplePackingBlockNames(){
		for(int i = 0; i < SimplePackingItemMax; i++){
			if (!SimplePackingItemStringUS[i].equals("")) {
				System.out.println("item.SimplePackingItem" + ".SP."+ SimplePackingItemStringUS[i] +".name=SP." + SimplePackingItemStringUS[i]);
//				LanguageRegistry.instance().addNameForObject(new ItemStack(SimplePackingItem,1,i), "en_US", "SP."+ SimplePackingItemStringUS[i]);
			}
		}
		if(SimplePackingItemCoreMod.SimplePackingItemMoreCompress){
			for(int i = 0; i < SimplePackingItemMax; i++){
				if (!SimplePackingItemStringUS[i].equals("")) {
					System.out.println("item.SimplePackingItem" + ".SPC."+ SimplePackingItemStringUS[i] +".name=SPC." + SimplePackingItemStringUS[i]);
//					LanguageRegistry.instance().addNameForObject(new ItemStack(SimplePackingItem,1,SimplePackingCompressItemStartIndex+i), "en_US", "SPC."+ SimplePackingItemStringUS[i]);
				}
			}
		}
	}

	public void regiSimplePackingItemNames(){
		for(int i = 0; i < SimplePackingItemMax; i++){
			if (!SimplePackingItemItemStringUS[i].equals("")) {
				System.out.println("item.SimplePackingItemItem" + ".SP."+ SimplePackingItemItemStringUS[i] +".name=SP." + SimplePackingItemItemStringUS[i]);
//				LanguageRegistry.instance().addNameForObject(new ItemStack(SimplePackingItemItem,1,i), "en_US", "SP."+ SimplePackingItemItemStringUS[i]);
			}
		}
		if(SimplePackingItemCoreMod.SimplePackingItemMoreCompress){
			for(int i = 0; i < SimplePackingItemMax; i++){
				if (!SimplePackingItemItemStringUS[i].equals("")) {
					System.out.println("item.SimplePackingItemItem" + ".SPC."+ SimplePackingItemItemStringUS[i] +".name=SPC." + SimplePackingItemItemStringUS[i]);
//					LanguageRegistry.instance().addNameForObject(new ItemStack(SimplePackingItemItem,1,SimplePackingCompressItemStartIndex+i), "en_US", "SPC."+ SimplePackingItemItemStringUS[i]);
				}
			}
		}
	}

	public void setRecipeSimplePackingSetting(){
		if(!SimplePackingItemRecipeRotation){
			Recipemold[0] = "XXZ";
			Recipemold[1] = "XXX";
			Recipemold[2] = "XXX";
		}
		else{
			Recipemold[0] = "XXX";
			Recipemold[1] = "XXX";
			Recipemold[2] = "ZXX";
		}
	}

	public void addRecipeSimplePackingBlocks(){
		//Craft
		GameRegistry.addRecipe(new ItemStack(SimplePackingItem,1,1), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), Blocks.coal_block });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItem,1,1), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), Blocks.iron_block });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItem,1,2), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), Blocks.gold_block });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItem,1,3), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), Blocks.diamond_block });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItem,1,4), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), Blocks.emerald_block });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItem,1,5), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), Blocks.lapis_block });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItem,1,6), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), Blocks.redstone_block });
		if(!SimplePackingItemUseItem){
			GameRegistry.addRecipe(new ItemStack(SimplePackingItem,1,7), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), Items.glowstone_dust });
		}
		GameRegistry.addRecipe(new ItemStack(SimplePackingItem,1,8), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), new ItemStack(Blocks.quartz_block, 1, 0) });
		//blank 8 for 12
		GameRegistry.addRecipe(new ItemStack(SimplePackingItem,1,13), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), Blocks.coal_ore });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItem,1,14), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), Blocks.iron_ore });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItem,1,15), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), Blocks.gold_ore });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItem,1,16), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), Blocks.diamond_ore });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItem,1,17), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), Blocks.emerald_ore });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItem,1,18), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), Blocks.lapis_ore });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItem,1,19), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), Blocks.redstone_ore });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItem,1,20), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), Blocks.glowstone });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItem,1,21), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), Blocks.quartz_ore });
		//blank 22 for 25
		GameRegistry.addRecipe(new ItemStack(SimplePackingItem,1,26), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), Blocks.dirt });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItem,1,27), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), Blocks.gravel });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItem,1,28), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), Blocks.sand });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItem,1,29), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), new ItemStack(Blocks.sandstone, 1, 0) });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItem,1,30), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), new ItemStack(Blocks.sandstone, 1, 1) });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItem,1,31), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), new ItemStack(Blocks.sandstone, 1, 2) });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItem,1,32), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), new ItemStack(Blocks.quartz_block, 1, 1) });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItem,1,33), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), new ItemStack(Blocks.quartz_block, 1, 2) });
		//blank 34 for 35
		GameRegistry.addRecipe(new ItemStack(SimplePackingItem,1,36), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), Blocks.glass });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItem,1,37), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), Blocks.ice });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItem,1,38), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), Blocks.snow_layer });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItem,1,39), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), Blocks.snow });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItem,1,40), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), Blocks.mycelium });
		//blank 41 for 45
		GameRegistry.addRecipe(new ItemStack(SimplePackingItem,1,46), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), Blocks.stone });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItem,1,47), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), Blocks.cobblestone });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItem,1,48), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), new ItemStack(Blocks.stonebrick, 1, 0) });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItem,1,49), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), new ItemStack(Blocks.stonebrick, 1, 1) });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItem,1,50), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), new ItemStack(Blocks.stonebrick, 1, 2) });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItem,1,51), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), new ItemStack(Blocks.stonebrick, 1, 3) });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItem,1,52), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), Blocks.mossy_cobblestone});
		//blank 53 for 55
		GameRegistry.addRecipe(new ItemStack(SimplePackingItem,1,56), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), Blocks.clay });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItem,1,57), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), Blocks.brick_block });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItem,1,58), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), Blocks.obsidian });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItem,1,59), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), Blocks.soul_sand });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItem,1,60), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), Blocks.nether_brick });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItem,1,61), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), Blocks.netherrack });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItem,1,62), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), Blocks.end_stone });
		//blank 63 for 67
		GameRegistry.addRecipe(new ItemStack(SimplePackingItem,1,68), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), new ItemStack(Blocks.planks, 1, 0) });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItem,1,69), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), new ItemStack(Blocks.planks, 1, 1) });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItem,1,70), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), new ItemStack(Blocks.planks, 1, 2) });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItem,1,71), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), new ItemStack(Blocks.planks, 1, 3) });
		//blank 72 for 73
		GameRegistry.addRecipe(new ItemStack(SimplePackingItem,1,74), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), new ItemStack(Blocks.log, 1, 0) });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItem,1,75), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), new ItemStack(Blocks.log, 1, 1) });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItem,1,76), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), new ItemStack(Blocks.log, 1, 2) });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItem,1,77), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), new ItemStack(Blocks.log, 1, 3) });
		//blank 78 for 79
		GameRegistry.addRecipe(new ItemStack(SimplePackingItem,1,80), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), new ItemStack(Blocks.wool, 1, 0) });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItem,1,81), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), new ItemStack(Blocks.wool, 1, 1) });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItem,1,82), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), new ItemStack(Blocks.wool, 1, 2) });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItem,1,83), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), new ItemStack(Blocks.wool, 1, 3) });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItem,1,84), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), new ItemStack(Blocks.wool, 1, 4) });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItem,1,85), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), new ItemStack(Blocks.wool, 1, 5) });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItem,1,86), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), new ItemStack(Blocks.wool, 1, 6) });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItem,1,87), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), new ItemStack(Blocks.wool, 1, 7) });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItem,1,88), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), new ItemStack(Blocks.wool, 1, 8) });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItem,1,89), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), new ItemStack(Blocks.wool, 1, 9) });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItem,1,90), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), new ItemStack(Blocks.wool, 1, 10) });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItem,1,91), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), new ItemStack(Blocks.wool, 1, 11) });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItem,1,92), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), new ItemStack(Blocks.wool, 1, 12) });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItem,1,93), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), new ItemStack(Blocks.wool, 1, 13) });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItem,1,94), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), new ItemStack(Blocks.wool, 1, 14) });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItem,1,95), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), new ItemStack(Blocks.wool, 1, 15) });
		//blank 96 for 127

		//ReverseCraft
		GameRegistry.addShapelessRecipe(new ItemStack(Blocks.coal_block,8,0)      , new Object[]{ new ItemStack(SimplePackingItem,1,0) });
		GameRegistry.addShapelessRecipe(new ItemStack(Blocks.iron_block,8)        , new Object[]{ new ItemStack(SimplePackingItem,1,1) });
		GameRegistry.addShapelessRecipe(new ItemStack(Blocks.gold_block,8)        , new Object[]{ new ItemStack(SimplePackingItem,1,2) });
		GameRegistry.addShapelessRecipe(new ItemStack(Blocks.diamond_block,8)     , new Object[]{ new ItemStack(SimplePackingItem,1,3) });
		GameRegistry.addShapelessRecipe(new ItemStack(Blocks.emerald_block,8)     , new Object[]{ new ItemStack(SimplePackingItem,1,4) });
		GameRegistry.addShapelessRecipe(new ItemStack(Blocks.lapis_block,8)       , new Object[]{ new ItemStack(SimplePackingItem,1,5) });
		GameRegistry.addShapelessRecipe(new ItemStack(Blocks.redstone_block,8)    , new Object[]{ new ItemStack(SimplePackingItem,1,6) });
		GameRegistry.addShapelessRecipe(new ItemStack(Items.glowstone_dust,8)     , new Object[]{ new ItemStack(SimplePackingItem,1,7) });
		GameRegistry.addShapelessRecipe(new ItemStack(Blocks.quartz_block,8,0)    , new Object[]{ new ItemStack(SimplePackingItem,1,8) });
		//blank 9 for 12
		GameRegistry.addShapelessRecipe(new ItemStack(Blocks.coal_ore,8)          , new Object[]{ new ItemStack(SimplePackingItem,1,13) });
		GameRegistry.addShapelessRecipe(new ItemStack(Blocks.iron_ore,8)          , new Object[]{ new ItemStack(SimplePackingItem,1,14) });
		GameRegistry.addShapelessRecipe(new ItemStack(Blocks.gold_ore,8)          , new Object[]{ new ItemStack(SimplePackingItem,1,15) });
		GameRegistry.addShapelessRecipe(new ItemStack(Blocks.diamond_ore,8)       , new Object[]{ new ItemStack(SimplePackingItem,1,16) });
		GameRegistry.addShapelessRecipe(new ItemStack(Blocks.emerald_ore,8)       , new Object[]{ new ItemStack(SimplePackingItem,1,17) });
		GameRegistry.addShapelessRecipe(new ItemStack(Blocks.lapis_ore,8)         , new Object[]{ new ItemStack(SimplePackingItem,1,18) });
		GameRegistry.addShapelessRecipe(new ItemStack(Blocks.redstone_ore,8)      , new Object[]{ new ItemStack(SimplePackingItem,1,19) });
		GameRegistry.addShapelessRecipe(new ItemStack(Blocks.glowstone,8)         , new Object[]{ new ItemStack(SimplePackingItem,1,20) });
		GameRegistry.addShapelessRecipe(new ItemStack(Blocks.quartz_ore,8)        , new Object[]{ new ItemStack(SimplePackingItem,1,21) });
		//blank 22 for 25
		GameRegistry.addShapelessRecipe(new ItemStack(Blocks.dirt,8)              , new Object[]{ new ItemStack(SimplePackingItem,1,26) });
		GameRegistry.addShapelessRecipe(new ItemStack(Blocks.gravel,8)            , new Object[]{ new ItemStack(SimplePackingItem,1,27) });
		GameRegistry.addShapelessRecipe(new ItemStack(Blocks.sand,8)              , new Object[]{ new ItemStack(SimplePackingItem,1,28) });
		GameRegistry.addShapelessRecipe(new ItemStack(Blocks.sandstone,8,0)       , new Object[]{ new ItemStack(SimplePackingItem,1,29) });
		GameRegistry.addShapelessRecipe(new ItemStack(Blocks.sandstone,8,1)       , new Object[]{ new ItemStack(SimplePackingItem,1,30) });
		GameRegistry.addShapelessRecipe(new ItemStack(Blocks.sandstone,8,2)       , new Object[]{ new ItemStack(SimplePackingItem,1,31) });
		GameRegistry.addShapelessRecipe(new ItemStack(Blocks.quartz_block,8,1)    , new Object[]{ new ItemStack(SimplePackingItem,1,32) });
		GameRegistry.addShapelessRecipe(new ItemStack(Blocks.quartz_block,8,2)    , new Object[]{ new ItemStack(SimplePackingItem,1,33) });
		//blank 34 for 35
		GameRegistry.addShapelessRecipe(new ItemStack(Blocks.glass,8)             , new Object[]{ new ItemStack(SimplePackingItem,1,36) });
		GameRegistry.addShapelessRecipe(new ItemStack(Blocks.ice,8)               , new Object[]{ new ItemStack(SimplePackingItem,1,37) });
		GameRegistry.addShapelessRecipe(new ItemStack(Blocks.snow_layer,8)        , new Object[]{ new ItemStack(SimplePackingItem,1,38) });
		GameRegistry.addShapelessRecipe(new ItemStack(Blocks.snow,8)              , new Object[]{ new ItemStack(SimplePackingItem,1,39) });
		GameRegistry.addShapelessRecipe(new ItemStack(Blocks.mycelium,8)          , new Object[]{ new ItemStack(SimplePackingItem,1,40) });
		//blank 41 for 45
		GameRegistry.addShapelessRecipe(new ItemStack(Blocks.stone,8)             , new Object[]{ new ItemStack(SimplePackingItem,1,46) });
		GameRegistry.addShapelessRecipe(new ItemStack(Blocks.cobblestone,8)       , new Object[]{ new ItemStack(SimplePackingItem,1,47) });
		GameRegistry.addShapelessRecipe(new ItemStack(Blocks.stonebrick,8,0)      , new Object[]{ new ItemStack(SimplePackingItem,1,48) });
		GameRegistry.addShapelessRecipe(new ItemStack(Blocks.stonebrick,8,1)      , new Object[]{ new ItemStack(SimplePackingItem,1,49) });
		GameRegistry.addShapelessRecipe(new ItemStack(Blocks.stonebrick,8,2)      , new Object[]{ new ItemStack(SimplePackingItem,1,50) });
		GameRegistry.addShapelessRecipe(new ItemStack(Blocks.stonebrick,8,3)      , new Object[]{ new ItemStack(SimplePackingItem,1,51) });
		GameRegistry.addShapelessRecipe(new ItemStack(Blocks.mossy_cobblestone,8) , new Object[]{ new ItemStack(SimplePackingItem,1,52) });
		//blank 53 for 55
		GameRegistry.addShapelessRecipe(new ItemStack(Blocks.clay,8)              , new Object[]{ new ItemStack(SimplePackingItem,1,56) });
		GameRegistry.addShapelessRecipe(new ItemStack(Blocks.brick_block,8)       , new Object[]{ new ItemStack(SimplePackingItem,1,57) });
		GameRegistry.addShapelessRecipe(new ItemStack(Blocks.obsidian,8)          , new Object[]{ new ItemStack(SimplePackingItem,1,58) });
		GameRegistry.addShapelessRecipe(new ItemStack(Blocks.soul_sand,8)         , new Object[]{ new ItemStack(SimplePackingItem,1,59) });
		GameRegistry.addShapelessRecipe(new ItemStack(Blocks.nether_brick,8)      , new Object[]{ new ItemStack(SimplePackingItem,1,60) });
		GameRegistry.addShapelessRecipe(new ItemStack(Blocks.netherrack,8)        , new Object[]{ new ItemStack(SimplePackingItem,1,61) });
		GameRegistry.addShapelessRecipe(new ItemStack(Blocks.end_stone,8)         , new Object[]{ new ItemStack(SimplePackingItem,1,62) });
		//blank 63 for 67
		GameRegistry.addShapelessRecipe(new ItemStack(Blocks.planks,8,0)          , new Object[]{ new ItemStack(SimplePackingItem,1,68) });
		GameRegistry.addShapelessRecipe(new ItemStack(Blocks.planks,8,1)          , new Object[]{ new ItemStack(SimplePackingItem,1,69) });
		GameRegistry.addShapelessRecipe(new ItemStack(Blocks.planks,8,2)          , new Object[]{ new ItemStack(SimplePackingItem,1,70) });
		GameRegistry.addShapelessRecipe(new ItemStack(Blocks.planks,8,3)          , new Object[]{ new ItemStack(SimplePackingItem,1,71) });
		//blank 72 for 73(new ItemStack
		GameRegistry.addShapelessRecipe(new ItemStack(Blocks.log,8,0)             , new Object[]{ new ItemStack(SimplePackingItem,1,74) });
		GameRegistry.addShapelessRecipe(new ItemStack(Blocks.log,8,1)             , new Object[]{ new ItemStack(SimplePackingItem,1,75) });
		GameRegistry.addShapelessRecipe(new ItemStack(Blocks.log,8,2)             , new Object[]{ new ItemStack(SimplePackingItem,1,76) });
		GameRegistry.addShapelessRecipe(new ItemStack(Blocks.log,8,3)             , new Object[]{ new ItemStack(SimplePackingItem,1,77) });
		//blank 78 for 79
		GameRegistry.addShapelessRecipe(new ItemStack(Blocks.wool,8,0)            , new Object[]{ new ItemStack(SimplePackingItem,1,80) });
		GameRegistry.addShapelessRecipe(new ItemStack(Blocks.wool,8,1)            , new Object[]{ new ItemStack(SimplePackingItem,1,81) });
		GameRegistry.addShapelessRecipe(new ItemStack(Blocks.wool,8,2)            , new Object[]{ new ItemStack(SimplePackingItem,1,82) });
		GameRegistry.addShapelessRecipe(new ItemStack(Blocks.wool,8,3)            , new Object[]{ new ItemStack(SimplePackingItem,1,83) });
		GameRegistry.addShapelessRecipe(new ItemStack(Blocks.wool,8,4)            , new Object[]{ new ItemStack(SimplePackingItem,1,84) });
		GameRegistry.addShapelessRecipe(new ItemStack(Blocks.wool,8,5)            , new Object[]{ new ItemStack(SimplePackingItem,1,85) });
		GameRegistry.addShapelessRecipe(new ItemStack(Blocks.wool,8,6)            , new Object[]{ new ItemStack(SimplePackingItem,1,86) });
		GameRegistry.addShapelessRecipe(new ItemStack(Blocks.wool,8,7)            , new Object[]{ new ItemStack(SimplePackingItem,1,87) });
		GameRegistry.addShapelessRecipe(new ItemStack(Blocks.wool,8,8)            , new Object[]{ new ItemStack(SimplePackingItem,1,88) });
		GameRegistry.addShapelessRecipe(new ItemStack(Blocks.wool,8,9)            , new Object[]{ new ItemStack(SimplePackingItem,1,89) });
		GameRegistry.addShapelessRecipe(new ItemStack(Blocks.wool,8,10)           , new Object[]{ new ItemStack(SimplePackingItem,1,90) });
		GameRegistry.addShapelessRecipe(new ItemStack(Blocks.wool,8,11)           , new Object[]{ new ItemStack(SimplePackingItem,1,91) });
		GameRegistry.addShapelessRecipe(new ItemStack(Blocks.wool,8,12)           , new Object[]{ new ItemStack(SimplePackingItem,1,92) });
		GameRegistry.addShapelessRecipe(new ItemStack(Blocks.wool,8,13)           , new Object[]{ new ItemStack(SimplePackingItem,1,93) });
		GameRegistry.addShapelessRecipe(new ItemStack(Blocks.wool,8,14)           , new Object[]{ new ItemStack(SimplePackingItem,1,94) });
		GameRegistry.addShapelessRecipe(new ItemStack(Blocks.wool,8,15)           , new Object[]{ new ItemStack(SimplePackingItem,1,95) });
		//blank 96 for 127

		//CompressCraft
		if(SimplePackingItemCoreMod.SimplePackingItemMoreCompress){
			for(int i = 0; i < SimplePackingItemMax; i++){
				if (!SimplePackingItemStringUS[i].equals("")) {
					GameRegistry.addRecipe(new ItemStack(SimplePackingItem,1,SimplePackingCompressItemStartIndex+i), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), new ItemStack(SimplePackingItem, 1, i) });
					GameRegistry.addShapelessRecipe(new ItemStack(SimplePackingItem,8,i), new Object[]{ new ItemStack(SimplePackingItem,1,SimplePackingCompressItemStartIndex+i) });
				}
			}
		}
	}

	public void addRecipeSimplePackingItems(){
		//Craft
		GameRegistry.addRecipe(new ItemStack(SimplePackingItemItem,1,  0), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), new ItemStack(Items.coal, 1, -1) });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItemItem,1,  1), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), Items.iron_ingot });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItemItem,1,  2), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), Items.gold_ingot });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItemItem,1,  3), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), Items.diamond });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItemItem,1,  4), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), Items.emerald });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItemItem,1,  5), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), Items.redstone });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItemItem,1,  6), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), Items.glowstone_dust });
		//blank 7 for 9
		GameRegistry.addRecipe(new ItemStack(SimplePackingItemItem,1, 10), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), Items.gunpowder });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItemItem,1, 11), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), Items.flint });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItemItem,1, 12), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), Items.clay_ball });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItemItem,1, 13), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), Items.brick });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItemItem,1, 14), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), Items.quartz });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItemItem,1, 15), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), Items.netherbrick });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItemItem,1, 16), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), Items.blaze_rod });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItemItem,1, 17), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), Items.gold_nugget });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItemItem,1, 18), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), Items.ghast_tear });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItemItem,1, 19), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), Items.nether_star });
		//blank 20 for 22
		GameRegistry.addRecipe(new ItemStack(SimplePackingItemItem,1, 23), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), Items.stick });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItemItem,1, 24), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), Items.bowl });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItemItem,1, 25), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), Items.feather });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItemItem,1, 26), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), Items.string });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItemItem,1, 27), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), Items.leather });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItemItem,1, 28), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), Items.slime_ball });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItemItem,1, 29), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), Items.ender_pearl });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItemItem,1, 30), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), Items.ender_eye });
		//blank 31 for 33
		GameRegistry.addRecipe(new ItemStack(SimplePackingItemItem,1, 34), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), Items.bone });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItemItem,1, 35), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), Items.paper });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItemItem,1, 36), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), Items.book });
//		GameRegistry.addRecipe(new ItemStack(SimplePackingItemItem,1, 37), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), Item.writableBook });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItemItem,1, 38), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), Items.map });
		//blank 39 for 41
		GameRegistry.addRecipe(new ItemStack(SimplePackingItemItem,1, 42), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), Items.glass_bottle });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItemItem,1, 43), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), Items.fire_charge });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItemItem,1, 44), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), Items.firework_charge });
		//blank 45 for 47
		GameRegistry.addRecipe(new ItemStack(SimplePackingItemItem,1, 48), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), Items.wheat_seeds });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItemItem,1, 49), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), Items.wheat });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItemItem,1, 50), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), Items.reeds });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItemItem,1, 51), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), Items.pumpkin_seeds });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItemItem,1, 52), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), Items.melon_seeds });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItemItem,1, 53), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), Items.nether_wart });
		//blank 54 for 56
		GameRegistry.addRecipe(new ItemStack(SimplePackingItemItem,1, 57), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), Items.sugar });
		//blank 58 for 60
		GameRegistry.addRecipe(new ItemStack(SimplePackingItemItem,1, 61), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), new ItemStack(Items.dye, 1, 0) });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItemItem,1, 62), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), new ItemStack(Items.dye, 1, 1) });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItemItem,1, 63), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), new ItemStack(Items.dye, 1, 2) });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItemItem,1, 64), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), new ItemStack(Items.dye, 1, 3) });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItemItem,1, 65), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), new ItemStack(Items.dye, 1, 4) });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItemItem,1, 66), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), new ItemStack(Items.dye, 1, 5) });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItemItem,1, 67), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), new ItemStack(Items.dye, 1, 6) });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItemItem,1, 68), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), new ItemStack(Items.dye, 1, 7) });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItemItem,1, 69), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), new ItemStack(Items.dye, 1, 8) });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItemItem,1, 70), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), new ItemStack(Items.dye, 1, 9) });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItemItem,1, 71), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), new ItemStack(Items.dye, 1, 10) });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItemItem,1, 72), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), new ItemStack(Items.dye, 1, 11) });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItemItem,1, 73), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), new ItemStack(Items.dye, 1, 12) });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItemItem,1, 74), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), new ItemStack(Items.dye, 1, 13) });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItemItem,1, 75), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), new ItemStack(Items.dye, 1, 14) });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItemItem,1, 76), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), new ItemStack(Items.dye, 1, 15) });
		//blank 77 for 92
		GameRegistry.addRecipe(new ItemStack(SimplePackingItemItem,1, 93), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), Items.apple });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItemItem,1, 94), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), new ItemStack(Items.golden_apple, 1, 0) });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItemItem,1, 95), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), new ItemStack(Items.golden_apple, 1, 1) });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItemItem,1, 96), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), Items.mushroom_stew });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItemItem,1, 97), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), Items.bread });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItemItem,1, 98), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), Items.porkchop });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItemItem,1, 99), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), Items.cooked_porkchop });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItemItem,1,100), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), Items.fish });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItemItem,1,101), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), Items.cooked_fished });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItemItem,1,102), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), Items.cake });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItemItem,1,103), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), Items.cookie });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItemItem,1,104), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), Items.melon });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItemItem,1,105), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), Items.beef });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItemItem,1,106), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), Items.cooked_beef });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItemItem,1,107), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), Items.chicken });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItemItem,1,108), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), Items.cooked_chicken });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItemItem,1,109), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), Items.carrot });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItemItem,1,110), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), Items.golden_carrot });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItemItem,1,111), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), Items.potato });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItemItem,1,112), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), Items.baked_potato });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItemItem,1,113), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), Items.poisonous_potato });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItemItem,1,114), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), Items.pumpkin_pie });
		//blank 115 for 117
		GameRegistry.addRecipe(new ItemStack(SimplePackingItemItem,1,118), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), Items.rotten_flesh });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItemItem,1,119), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), Items.spider_eye });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItemItem,1,120), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), Items.egg });

		//ReverseCraft
		GameRegistry.addShapelessRecipe(new ItemStack(Items.coal,8,0)             , new Object[]{ new ItemStack(SimplePackingItemItem,1,  0) });
		GameRegistry.addShapelessRecipe(new ItemStack(Items.iron_ingot,8)         , new Object[]{ new ItemStack(SimplePackingItemItem,1,  1) });
		GameRegistry.addShapelessRecipe(new ItemStack(Items.gold_ingot,8)         , new Object[]{ new ItemStack(SimplePackingItemItem,1,  2) });
		GameRegistry.addShapelessRecipe(new ItemStack(Items.diamond,8)            , new Object[]{ new ItemStack(SimplePackingItemItem,1,  3) });
		GameRegistry.addShapelessRecipe(new ItemStack(Items.emerald,8)            , new Object[]{ new ItemStack(SimplePackingItemItem,1,  4) });
		GameRegistry.addShapelessRecipe(new ItemStack(Items.redstone,8)           , new Object[]{ new ItemStack(SimplePackingItemItem,1,  5) });
		GameRegistry.addShapelessRecipe(new ItemStack(Items.glowstone_dust,8)     , new Object[]{ new ItemStack(SimplePackingItemItem,1,  6) });
		//blank 7 for 9
		GameRegistry.addShapelessRecipe(new ItemStack(Items.gunpowder,8)          , new Object[]{ new ItemStack(SimplePackingItemItem,1, 10) });
		GameRegistry.addShapelessRecipe(new ItemStack(Items.flint,8)              , new Object[]{ new ItemStack(SimplePackingItemItem,1, 11) });
		GameRegistry.addShapelessRecipe(new ItemStack(Items.clay_ball,8)          , new Object[]{ new ItemStack(SimplePackingItemItem,1, 12) });
		GameRegistry.addShapelessRecipe(new ItemStack(Items.brick,8)              , new Object[]{ new ItemStack(SimplePackingItemItem,1, 13) });
		GameRegistry.addShapelessRecipe(new ItemStack(Items.quartz,8)             , new Object[]{ new ItemStack(SimplePackingItemItem,1, 14) });
		GameRegistry.addShapelessRecipe(new ItemStack(Items.netherbrick,8)        , new Object[]{ new ItemStack(SimplePackingItemItem,1, 15) });
		GameRegistry.addShapelessRecipe(new ItemStack(Items.blaze_rod,8)          , new Object[]{ new ItemStack(SimplePackingItemItem,1, 16) });
		GameRegistry.addShapelessRecipe(new ItemStack(Items.gold_nugget,8)        , new Object[]{ new ItemStack(SimplePackingItemItem,1, 17) });
		GameRegistry.addShapelessRecipe(new ItemStack(Items.ghast_tear,8)         , new Object[]{ new ItemStack(SimplePackingItemItem,1, 18) });
		GameRegistry.addShapelessRecipe(new ItemStack(Items.nether_star,8)        , new Object[]{ new ItemStack(SimplePackingItemItem,1, 19) });
		//blank 20 for 22
		GameRegistry.addShapelessRecipe(new ItemStack(Items.stick,8)              , new Object[]{ new ItemStack(SimplePackingItemItem,1, 23) });
		GameRegistry.addShapelessRecipe(new ItemStack(Items.bowl,8)               , new Object[]{ new ItemStack(SimplePackingItemItem,1, 24) });
		GameRegistry.addShapelessRecipe(new ItemStack(Items.feather,8)            , new Object[]{ new ItemStack(SimplePackingItemItem,1, 25) });
		GameRegistry.addShapelessRecipe(new ItemStack(Items.string,8)             , new Object[]{ new ItemStack(SimplePackingItemItem,1, 26) });
		GameRegistry.addShapelessRecipe(new ItemStack(Items.leather,8)            , new Object[]{ new ItemStack(SimplePackingItemItem,1, 27) });
		GameRegistry.addShapelessRecipe(new ItemStack(Items.slime_ball,8)         , new Object[]{ new ItemStack(SimplePackingItemItem,1, 28) });
		GameRegistry.addShapelessRecipe(new ItemStack(Items.ender_pearl,8)        , new Object[]{ new ItemStack(SimplePackingItemItem,1, 29) });
		GameRegistry.addShapelessRecipe(new ItemStack(Items.ender_eye,8)          , new Object[]{ new ItemStack(SimplePackingItemItem,1, 30) });
		//blank 31 for 33
		GameRegistry.addShapelessRecipe(new ItemStack(Items.bone,8)               , new Object[]{ new ItemStack(SimplePackingItemItem,1, 34) });
		GameRegistry.addShapelessRecipe(new ItemStack(Items.paper,8)              , new Object[]{ new ItemStack(SimplePackingItemItem,1, 35) });
		GameRegistry.addShapelessRecipe(new ItemStack(Items.book,8)               , new Object[]{ new ItemStack(SimplePackingItemItem,1, 36) });
//		GameRegistry.addShapelessRecipe(new ItemStack(Items.writableBook,8)       , new Object[]{ new ItemStack(SimplePackingItemItem,1, 37) });
		GameRegistry.addShapelessRecipe(new ItemStack(Items.map,8)                , new Object[]{ new ItemStack(SimplePackingItemItem,1, 38) });
		//blank 39 for 41
		GameRegistry.addShapelessRecipe(new ItemStack(Items.glass_bottle,8)       , new Object[]{ new ItemStack(SimplePackingItemItem,1, 42) });
		GameRegistry.addShapelessRecipe(new ItemStack(Items.fire_charge,8)        , new Object[]{ new ItemStack(SimplePackingItemItem,1, 43) });
		GameRegistry.addShapelessRecipe(new ItemStack(Items.firework_charge,8)    , new Object[]{ new ItemStack(SimplePackingItemItem,1, 44) });
		//blank 45 for 47
		GameRegistry.addShapelessRecipe(new ItemStack(Items.wheat_seeds,8)        , new Object[]{ new ItemStack(SimplePackingItemItem,1, 48) });
		GameRegistry.addShapelessRecipe(new ItemStack(Items.wheat,8)              , new Object[]{ new ItemStack(SimplePackingItemItem,1, 49) });
		GameRegistry.addShapelessRecipe(new ItemStack(Items.reeds,8)              , new Object[]{ new ItemStack(SimplePackingItemItem,1, 50) });
		GameRegistry.addShapelessRecipe(new ItemStack(Items.pumpkin_seeds,8)      , new Object[]{ new ItemStack(SimplePackingItemItem,1, 51) });
		GameRegistry.addShapelessRecipe(new ItemStack(Items.melon_seeds,8)        , new Object[]{ new ItemStack(SimplePackingItemItem,1, 52) });
		GameRegistry.addShapelessRecipe(new ItemStack(Items.nether_wart,8)        , new Object[]{ new ItemStack(SimplePackingItemItem,1, 53) });
		//blank 54 for 56
		GameRegistry.addShapelessRecipe(new ItemStack(Items.sugar,8)              , new Object[]{ new ItemStack(SimplePackingItemItem,1, 57) });
		//blank 58 for 60
		GameRegistry.addShapelessRecipe(new ItemStack(Items.dye,8,0)              , new Object[]{ new ItemStack(SimplePackingItemItem,1, 61) });
		GameRegistry.addShapelessRecipe(new ItemStack(Items.dye,8,1)              , new Object[]{ new ItemStack(SimplePackingItemItem,1, 62) });
		GameRegistry.addShapelessRecipe(new ItemStack(Items.dye,8,2)              , new Object[]{ new ItemStack(SimplePackingItemItem,1, 63) });
		GameRegistry.addShapelessRecipe(new ItemStack(Items.dye,8,3)              , new Object[]{ new ItemStack(SimplePackingItemItem,1, 64) });
		GameRegistry.addShapelessRecipe(new ItemStack(Items.dye,8,4)              , new Object[]{ new ItemStack(SimplePackingItemItem,1, 65) });
		GameRegistry.addShapelessRecipe(new ItemStack(Items.dye,8,5)              , new Object[]{ new ItemStack(SimplePackingItemItem,1, 66) });
		GameRegistry.addShapelessRecipe(new ItemStack(Items.dye,8,6)              , new Object[]{ new ItemStack(SimplePackingItemItem,1, 67) });
		GameRegistry.addShapelessRecipe(new ItemStack(Items.dye,8,7)              , new Object[]{ new ItemStack(SimplePackingItemItem,1, 68) });
		GameRegistry.addShapelessRecipe(new ItemStack(Items.dye,8,8)              , new Object[]{ new ItemStack(SimplePackingItemItem,1, 69) });
		GameRegistry.addShapelessRecipe(new ItemStack(Items.dye,8,9)              , new Object[]{ new ItemStack(SimplePackingItemItem,1, 70) });
		GameRegistry.addShapelessRecipe(new ItemStack(Items.dye,8,10)             , new Object[]{ new ItemStack(SimplePackingItemItem,1, 71) });
		GameRegistry.addShapelessRecipe(new ItemStack(Items.dye,8,11)             , new Object[]{ new ItemStack(SimplePackingItemItem,1, 72) });
		GameRegistry.addShapelessRecipe(new ItemStack(Items.dye,8,12)             , new Object[]{ new ItemStack(SimplePackingItemItem,1, 73) });
		GameRegistry.addShapelessRecipe(new ItemStack(Items.dye,8,13)             , new Object[]{ new ItemStack(SimplePackingItemItem,1, 74) });
		GameRegistry.addShapelessRecipe(new ItemStack(Items.dye,8,14)             , new Object[]{ new ItemStack(SimplePackingItemItem,1, 75) });
		GameRegistry.addShapelessRecipe(new ItemStack(Items.dye,8,15)             , new Object[]{ new ItemStack(SimplePackingItemItem,1, 76) });
		//blank 77 for 92
		GameRegistry.addShapelessRecipe(new ItemStack(Items.apple,8)              , new Object[]{ new ItemStack(SimplePackingItemItem,1, 93) });
		GameRegistry.addShapelessRecipe(new ItemStack(Items.golden_apple,8,0)     , new Object[]{ new ItemStack(SimplePackingItemItem,1, 94) });
		GameRegistry.addShapelessRecipe(new ItemStack(Items.golden_apple,8,1)     , new Object[]{ new ItemStack(SimplePackingItemItem,1, 95) });
		GameRegistry.addShapelessRecipe(new ItemStack(Items.mushroom_stew,8)      , new Object[]{ new ItemStack(SimplePackingItemItem,1, 96) });
		GameRegistry.addShapelessRecipe(new ItemStack(Items.bread,8)              , new Object[]{ new ItemStack(SimplePackingItemItem,1, 97) });
		GameRegistry.addShapelessRecipe(new ItemStack(Items.porkchop,8)           , new Object[]{ new ItemStack(SimplePackingItemItem,1, 98) });
		GameRegistry.addShapelessRecipe(new ItemStack(Items.cooked_porkchop,8)    , new Object[]{ new ItemStack(SimplePackingItemItem,1, 99) });
		GameRegistry.addShapelessRecipe(new ItemStack(Items.fish,8)               , new Object[]{ new ItemStack(SimplePackingItemItem,1,100) });
		GameRegistry.addShapelessRecipe(new ItemStack(Items.cooked_fished,8)      , new Object[]{ new ItemStack(SimplePackingItemItem,1,101) });
		GameRegistry.addShapelessRecipe(new ItemStack(Items.cake,8)               , new Object[]{ new ItemStack(SimplePackingItemItem,1,102) });
		GameRegistry.addShapelessRecipe(new ItemStack(Items.cookie,8)             , new Object[]{ new ItemStack(SimplePackingItemItem,1,103) });
		GameRegistry.addShapelessRecipe(new ItemStack(Items.melon,8)              , new Object[]{ new ItemStack(SimplePackingItemItem,1,104) });
		GameRegistry.addShapelessRecipe(new ItemStack(Items.beef,8)               , new Object[]{ new ItemStack(SimplePackingItemItem,1,105) });
		GameRegistry.addShapelessRecipe(new ItemStack(Items.cooked_beef,8)        , new Object[]{ new ItemStack(SimplePackingItemItem,1,106) });
		GameRegistry.addShapelessRecipe(new ItemStack(Items.chicken,8)            , new Object[]{ new ItemStack(SimplePackingItemItem,1,107) });
		GameRegistry.addShapelessRecipe(new ItemStack(Items.cooked_chicken,8)     , new Object[]{ new ItemStack(SimplePackingItemItem,1,108) });
		GameRegistry.addShapelessRecipe(new ItemStack(Items.carrot,8)             , new Object[]{ new ItemStack(SimplePackingItemItem,1,109) });
		GameRegistry.addShapelessRecipe(new ItemStack(Items.golden_carrot,8)      , new Object[]{ new ItemStack(SimplePackingItemItem,1,110) });
		GameRegistry.addShapelessRecipe(new ItemStack(Items.potato,8)             , new Object[]{ new ItemStack(SimplePackingItemItem,1,111) });
		GameRegistry.addShapelessRecipe(new ItemStack(Items.baked_potato,8)       , new Object[]{ new ItemStack(SimplePackingItemItem,1,112) });
		GameRegistry.addShapelessRecipe(new ItemStack(Items.poisonous_potato,8)   , new Object[]{ new ItemStack(SimplePackingItemItem,1,113) });
		GameRegistry.addShapelessRecipe(new ItemStack(Items.pumpkin_pie,8)        , new Object[]{ new ItemStack(SimplePackingItemItem,1,114) });
		//blank 115 for 117
		GameRegistry.addShapelessRecipe(new ItemStack(Items.rotten_flesh,8)       , new Object[]{ new ItemStack(SimplePackingItemItem,1,118) });
		GameRegistry.addShapelessRecipe(new ItemStack(Items.spider_eye,8)         , new Object[]{ new ItemStack(SimplePackingItemItem,1,119) });
		GameRegistry.addShapelessRecipe(new ItemStack(Items.egg,8)                , new Object[]{ new ItemStack(SimplePackingItemItem,1,120) });

		//CompressCraft
		if(SimplePackingItemCoreMod.SimplePackingItemMoreCompress){
			for(int i = 0; i < SimplePackingItemMax; i++){
				if (!SimplePackingItemItemStringUS[i].equals("")) {
					GameRegistry.addRecipe(new ItemStack(SimplePackingItemItem,1,SimplePackingCompressItemStartIndex+i), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), new ItemStack(SimplePackingItemItem, 1, i) });
					GameRegistry.addShapelessRecipe(new ItemStack(SimplePackingItemItem,8,i), new Object[]{ new ItemStack(SimplePackingItemItem,1,SimplePackingCompressItemStartIndex+i) });
				}
			}
		}
	}

	public void addRecipeOptionsBlock(){
		GameRegistry.addShapelessRecipe(new ItemStack(Blocks.sandstone,1,1)      , new Object[]{ new ItemStack(Blocks.sandstone,1,-1), Blocks.dirt });
		GameRegistry.addShapelessRecipe(new ItemStack(Blocks.sandstone,1,2)      , new Object[]{ new ItemStack(Blocks.sandstone,1,-1), Blocks.sand });
		GameRegistry.addShapelessRecipe(new ItemStack(Blocks.stonebrick,1,1)     , new Object[]{ new ItemStack(Blocks.stonebrick,1,-1), Blocks.vine });
		GameRegistry.addShapelessRecipe(new ItemStack(Blocks.stonebrick,1,2)     , new Object[]{ new ItemStack(Blocks.stonebrick,1,-1), Blocks.gravel });
		GameRegistry.addShapelessRecipe(new ItemStack(Blocks.stonebrick,1,3)     , new Object[]{ new ItemStack(Blocks.stonebrick,1,-1), Blocks.dirt });
	}
}