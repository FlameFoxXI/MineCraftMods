package mods.SimplePackingItemMod;

import java.util.logging.Level;

import mods.SimplePackingItemMod.proxy.*;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.src.ModLoader;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.IRenderContextHandler;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.Configuration;

@Mod(modid = "SimplePackingItemMod", name = "Simple PackingMod Mod", version = "1.0.0")
@NetworkMod(clientSideRequired=true, serverSideRequired=false)
public class SimplePackingItemCoreMod
{
	@SidedProxy(clientSide="mods.SimplePackingItemMod.proxy.ClientProxy", serverSide="mods.SimplePackingItemMod.proxy.CommonProxy")
	public static CommonProxy proxy;

	@Mod.Instance("SimplePackingItemMod")
	public static SimplePackingItemCoreMod instance;

	//Configuration Setting Member
	public static int SimplePackingItemID = 6625;
	public static int SimplePackingItemItemID = 6626;
	public static boolean SimplePackingItemHideCreativeTabs;
	public static boolean SimplePackingItemMoreCompress;
	public static boolean SimplePackingItemRecipeOff;
	public static boolean SimplePackingItemRecipeRotation;
	public static boolean SimplePackingItemRecipeOption;

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


    @EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		//Configuration Load & Save
		Configuration config = new Configuration(event.getSuggestedConfigurationFile());
		try{
			config.load();
			SimplePackingItemID = config.getItem("SimplePackingItemID", SimplePackingItemID).getInt();
			SimplePackingItemItemID = config.getItem("SimplePackingItemItemID", SimplePackingItemItemID).getInt();
			SimplePackingItemHideCreativeTabs = config.get(Configuration.CATEGORY_GENERAL, "SimplePackingItemHideCreativeTabs", false).getBoolean(true);
			SimplePackingItemMoreCompress = config.get(Configuration.CATEGORY_GENERAL, "SimplePackingItemMoreCompress", true).getBoolean(true);
			SimplePackingItemRecipeOff = config.get(Configuration.CATEGORY_GENERAL, "SimplePackingItemRecipeOff", false).getBoolean(true);
			SimplePackingItemRecipeRotation = config.get(Configuration.CATEGORY_GENERAL, "SimplePackingItemRecipeRotation", false).getBoolean(true);
			SimplePackingItemRecipeOption = config.get(Configuration.CATEGORY_GENERAL, "SimplePackingItemRecipeOption", false).getBoolean(true);
		}
		catch (Exception e)
		{
			//Error Log Printout
			FMLLog.log(Level.SEVERE, e, "Simple SimplePackingItemMod : Error configuration file");
		}
		finally
		{
			config.save();
		}
	}

    @EventHandler
	public void load(FMLInitializationEvent event)
	{
		proxy.registerRenderInformation();

		setRecipeSimplePackingSetting();

		if(SimplePackingItemID != 0){
			setSimplePackingBlocks();
			regiSimplePackingBlocks();
			regiSimplePackingBlockNames();
			if(!SimplePackingItemRecipeOff){
				addRecipeSimplePackingBlocks();
			}
		}

		if(SimplePackingItemItemID != 0){
			setSimplePackingItems();
			regiSimplePackingitems();
			regiSimplePackingItemNames();

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
		SimplePackingItem = (new PackingItem(SimplePackingItemID - 256)).setUnlocalizedName("SimplePackingItem");
	}

	public void setSimplePackingItems(){
		SimplePackingItemItem = (new PackingItemItem(SimplePackingItemItemID - 256)).setUnlocalizedName("SimplePackingItemItem");
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
				LanguageRegistry.instance().addNameForObject(new ItemStack(SimplePackingItem,1,i), "en_US", "SP."+ SimplePackingItemStringUS[i]);
			}
		}
		if(SimplePackingItemCoreMod.SimplePackingItemMoreCompress){
			for(int i = 0; i < SimplePackingItemMax; i++){
				if (!SimplePackingItemStringUS[i].equals("")) {
					LanguageRegistry.instance().addNameForObject(new ItemStack(SimplePackingItem,1,SimplePackingCompressItemStartIndex+i), "en_US", "SPC."+ SimplePackingItemStringUS[i]);
				}
			}
		}
	}

	public void regiSimplePackingItemNames(){
		for(int i = 0; i < SimplePackingItemMax; i++){
			if (!SimplePackingItemItemStringUS[i].equals("")) {
				LanguageRegistry.instance().addNameForObject(new ItemStack(SimplePackingItemItem,1,i), "en_US", "SP."+ SimplePackingItemItemStringUS[i]);
			}
		}
		if(SimplePackingItemCoreMod.SimplePackingItemMoreCompress){
			for(int i = 0; i < SimplePackingItemMax; i++){
				if (!SimplePackingItemItemStringUS[i].equals("")) {
					LanguageRegistry.instance().addNameForObject(new ItemStack(SimplePackingItemItem,1,SimplePackingCompressItemStartIndex+i), "en_US", "SPC."+ SimplePackingItemItemStringUS[i]);
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
		if(SimplePackingItemItemID == 0){
			GameRegistry.addRecipe(new ItemStack(SimplePackingItem,1,0), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), new ItemStack(Item.coal, 1, -1) });
		}
		GameRegistry.addRecipe(new ItemStack(SimplePackingItem,1,1), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), Block.blockIron });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItem,1,2), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), Block.blockGold });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItem,1,3), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), Block.blockDiamond });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItem,1,4), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), Block.blockEmerald });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItem,1,5), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), Block.blockLapis });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItem,1,6), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), Block.blockRedstone });
		if(SimplePackingItemItemID == 0){
			GameRegistry.addRecipe(new ItemStack(SimplePackingItem,1,7), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), Item.glowstone });
		}
		GameRegistry.addRecipe(new ItemStack(SimplePackingItem,1,8), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), new ItemStack(Block.blockNetherQuartz, 1, 0) });
		//blank 8 for 12
		GameRegistry.addRecipe(new ItemStack(SimplePackingItem,1,13), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), Block.oreCoal });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItem,1,14), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), Block.oreIron });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItem,1,15), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), Block.oreGold });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItem,1,16), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), Block.oreEmerald });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItem,1,17), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), Block.oreEmerald });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItem,1,18), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), Block.oreLapis });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItem,1,19), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), Block.oreRedstone });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItem,1,20), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), Block.glowStone });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItem,1,21), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), Block.oreNetherQuartz });
		//blank 22 for 25
		GameRegistry.addRecipe(new ItemStack(SimplePackingItem,1,26), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), Block.dirt });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItem,1,27), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), Block.gravel });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItem,1,28), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), Block.sand });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItem,1,29), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), new ItemStack(Block.sandStone, 1, 0) });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItem,1,30), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), new ItemStack(Block.sandStone, 1, 1) });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItem,1,31), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), new ItemStack(Block.sandStone, 1, 2) });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItem,1,32), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), new ItemStack(Block.blockNetherQuartz, 1, 1) });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItem,1,33), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), new ItemStack(Block.blockNetherQuartz, 1, 2) });
		//blank 34 for 35
		GameRegistry.addRecipe(new ItemStack(SimplePackingItem,1,36), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), Block.glass });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItem,1,37), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), Block.ice });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItem,1,38), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), Block.snow });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItem,1,39), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), Block.blockSnow });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItem,1,40), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), Block.mycelium });
		//blank 41 for 45
		GameRegistry.addRecipe(new ItemStack(SimplePackingItem,1,46), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), Block.stone });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItem,1,47), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), Block.cobblestone });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItem,1,48), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), new ItemStack(Block.stoneBrick, 1, 0) });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItem,1,49), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), new ItemStack(Block.stoneBrick, 1, 1) });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItem,1,50), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), new ItemStack(Block.stoneBrick, 1, 2) });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItem,1,51), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), new ItemStack(Block.stoneBrick, 1, 3) });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItem,1,52), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), Block.cobblestoneMossy});
		//blank 53 for 55
		GameRegistry.addRecipe(new ItemStack(SimplePackingItem,1,56), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), Block.blockClay });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItem,1,57), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), Block.brick });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItem,1,58), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), Block.obsidian });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItem,1,59), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), Block.slowSand });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItem,1,60), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), Block.netherBrick });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItem,1,61), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), Block.netherrack });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItem,1,62), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), Block.whiteStone });
		//blank 63 for 67
		GameRegistry.addRecipe(new ItemStack(SimplePackingItem,1,68), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), new ItemStack(Block.planks, 1, 0) });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItem,1,69), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), new ItemStack(Block.planks, 1, 1) });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItem,1,70), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), new ItemStack(Block.planks, 1, 2) });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItem,1,71), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), new ItemStack(Block.planks, 1, 3) });
		//blank 72 for 73
		GameRegistry.addRecipe(new ItemStack(SimplePackingItem,1,74), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), new ItemStack(Block.wood, 1, 0) });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItem,1,75), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), new ItemStack(Block.wood, 1, 1) });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItem,1,76), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), new ItemStack(Block.wood, 1, 2) });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItem,1,77), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), new ItemStack(Block.wood, 1, 3) });
		//blank 78 for 79
		GameRegistry.addRecipe(new ItemStack(SimplePackingItem,1,80), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), new ItemStack(Block.cloth, 1, 0) });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItem,1,81), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), new ItemStack(Block.cloth, 1, 1) });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItem,1,82), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), new ItemStack(Block.cloth, 1, 2) });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItem,1,83), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), new ItemStack(Block.cloth, 1, 3) });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItem,1,84), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), new ItemStack(Block.cloth, 1, 4) });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItem,1,85), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), new ItemStack(Block.cloth, 1, 5) });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItem,1,86), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), new ItemStack(Block.cloth, 1, 6) });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItem,1,87), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), new ItemStack(Block.cloth, 1, 7) });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItem,1,88), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), new ItemStack(Block.cloth, 1, 8) });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItem,1,89), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), new ItemStack(Block.cloth, 1, 9) });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItem,1,90), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), new ItemStack(Block.cloth, 1, 10) });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItem,1,91), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), new ItemStack(Block.cloth, 1, 11) });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItem,1,92), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), new ItemStack(Block.cloth, 1, 12) });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItem,1,93), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), new ItemStack(Block.cloth, 1, 13) });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItem,1,94), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), new ItemStack(Block.cloth, 1, 14) });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItem,1,95), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), new ItemStack(Block.cloth, 1, 15) });
		//blank 96 for 127

		//ReverseCraft
		GameRegistry.addShapelessRecipe(new ItemStack(Item.coal,8,0)            , new Object[]{ new ItemStack(SimplePackingItem,1,0) });
		GameRegistry.addShapelessRecipe(new ItemStack(Block.blockIron,8)       , new Object[]{ new ItemStack(SimplePackingItem,1,1) });
		GameRegistry.addShapelessRecipe(new ItemStack(Block.blockGold,8)        , new Object[]{ new ItemStack(SimplePackingItem,1,2) });
		GameRegistry.addShapelessRecipe(new ItemStack(Block.blockDiamond,8)     , new Object[]{ new ItemStack(SimplePackingItem,1,3) });
		GameRegistry.addShapelessRecipe(new ItemStack(Block.blockEmerald,8)     , new Object[]{ new ItemStack(SimplePackingItem,1,4) });
		GameRegistry.addShapelessRecipe(new ItemStack(Block.blockLapis,8)       , new Object[]{ new ItemStack(SimplePackingItem,1,5) });
		GameRegistry.addShapelessRecipe(new ItemStack(Block.blockRedstone,8)    , new Object[]{ new ItemStack(SimplePackingItem,1,6) });
		GameRegistry.addShapelessRecipe(new ItemStack(Item.glowstone,8)    , new Object[]{ new ItemStack(SimplePackingItem,1,7) });
		GameRegistry.addShapelessRecipe(new ItemStack(Block.blockNetherQuartz,8,0)   , new Object[]{ new ItemStack(SimplePackingItem,1,8) });
		//blank 9 for 12
		GameRegistry.addShapelessRecipe(new ItemStack(Block.oreCoal,8)          , new Object[]{ new ItemStack(SimplePackingItem,1,13) });
		GameRegistry.addShapelessRecipe(new ItemStack(Block.oreIron,8)          , new Object[]{ new ItemStack(SimplePackingItem,1,14) });
		GameRegistry.addShapelessRecipe(new ItemStack(Block.oreGold,8)          , new Object[]{ new ItemStack(SimplePackingItem,1,15) });
		GameRegistry.addShapelessRecipe(new ItemStack(Block.oreEmerald,8)       , new Object[]{ new ItemStack(SimplePackingItem,1,16) });
		GameRegistry.addShapelessRecipe(new ItemStack(Block.oreEmerald,8)       , new Object[]{ new ItemStack(SimplePackingItem,1,17) });
		GameRegistry.addShapelessRecipe(new ItemStack(Block.oreLapis,8)         , new Object[]{ new ItemStack(SimplePackingItem,1,18) });
		GameRegistry.addShapelessRecipe(new ItemStack(Block.oreRedstone,8)      , new Object[]{ new ItemStack(SimplePackingItem,1,19) });
		GameRegistry.addShapelessRecipe(new ItemStack(Block.glowStone,8)        , new Object[]{ new ItemStack(SimplePackingItem,1,20) });
		GameRegistry.addShapelessRecipe(new ItemStack(Block.oreNetherQuartz,8)   , new Object[]{ new ItemStack(SimplePackingItem,1,21) });
		//blank 22 for 25
		GameRegistry.addShapelessRecipe(new ItemStack(Block.dirt,8)             , new Object[]{ new ItemStack(SimplePackingItem,1,26) });
		GameRegistry.addShapelessRecipe(new ItemStack(Block.gravel,8)           , new Object[]{ new ItemStack(SimplePackingItem,1,27) });
		GameRegistry.addShapelessRecipe(new ItemStack(Block.sand,8)             , new Object[]{ new ItemStack(SimplePackingItem,1,28) });
		GameRegistry.addShapelessRecipe(new ItemStack(Block.sandStone,8,0)      , new Object[]{ new ItemStack(SimplePackingItem,1,29) });
		GameRegistry.addShapelessRecipe(new ItemStack(Block.sandStone,8,1)      , new Object[]{ new ItemStack(SimplePackingItem,1,30) });
		GameRegistry.addShapelessRecipe(new ItemStack(Block.sandStone,8,2)      , new Object[]{ new ItemStack(SimplePackingItem,1,31) });
		GameRegistry.addShapelessRecipe(new ItemStack(Block.blockNetherQuartz,8,1) , new Object[]{ new ItemStack(SimplePackingItem,1,32) });
		GameRegistry.addShapelessRecipe(new ItemStack(Block.blockNetherQuartz,8,2) , new Object[]{ new ItemStack(SimplePackingItem,1,33) });
		//blank 34 for 35
		GameRegistry.addShapelessRecipe(new ItemStack(Block.glass,8)            , new Object[]{ new ItemStack(SimplePackingItem,1,36) });
		GameRegistry.addShapelessRecipe(new ItemStack(Block.ice,8)              , new Object[]{ new ItemStack(SimplePackingItem,1,37) });
		GameRegistry.addShapelessRecipe(new ItemStack(Block.snow,8)             , new Object[]{ new ItemStack(SimplePackingItem,1,38) });
		GameRegistry.addShapelessRecipe(new ItemStack(Block.blockSnow,8)        , new Object[]{ new ItemStack(SimplePackingItem,1,39) });
		GameRegistry.addShapelessRecipe(new ItemStack(Block.mycelium,8)         , new Object[]{ new ItemStack(SimplePackingItem,1,40) });
		//blank 41 for 45
		GameRegistry.addShapelessRecipe(new ItemStack(Block.stone,8)            , new Object[]{ new ItemStack(SimplePackingItem,1,46) });
		GameRegistry.addShapelessRecipe(new ItemStack(Block.cobblestone,8)      , new Object[]{ new ItemStack(SimplePackingItem,1,47) });
		GameRegistry.addShapelessRecipe(new ItemStack(Block.stoneBrick,8,0)     , new Object[]{ new ItemStack(SimplePackingItem,1,48) });
		GameRegistry.addShapelessRecipe(new ItemStack(Block.stoneBrick,8,1)     , new Object[]{ new ItemStack(SimplePackingItem,1,49) });
		GameRegistry.addShapelessRecipe(new ItemStack(Block.stoneBrick,8,2)     , new Object[]{ new ItemStack(SimplePackingItem,1,50) });
		GameRegistry.addShapelessRecipe(new ItemStack(Block.stoneBrick,8,3)     , new Object[]{ new ItemStack(SimplePackingItem,1,51) });
		GameRegistry.addShapelessRecipe(new ItemStack(Block.cobblestoneMossy,8) , new Object[]{ new ItemStack(SimplePackingItem,1,52) });
		//blank 53 for 55
		GameRegistry.addShapelessRecipe(new ItemStack(Block.blockClay,8)        , new Object[]{ new ItemStack(SimplePackingItem,1,56) });
		GameRegistry.addShapelessRecipe(new ItemStack(Block.brick,8)            , new Object[]{ new ItemStack(SimplePackingItem,1,57) });
		GameRegistry.addShapelessRecipe(new ItemStack(Block.obsidian,8)         , new Object[]{ new ItemStack(SimplePackingItem,1,58) });
		GameRegistry.addShapelessRecipe(new ItemStack(Block.slowSand,8)         , new Object[]{ new ItemStack(SimplePackingItem,1,59) });
		GameRegistry.addShapelessRecipe(new ItemStack(Block.netherBrick,8)      , new Object[]{ new ItemStack(SimplePackingItem,1,60) });
		GameRegistry.addShapelessRecipe(new ItemStack(Block.netherrack,8)       , new Object[]{ new ItemStack(SimplePackingItem,1,61) });
		GameRegistry.addShapelessRecipe(new ItemStack(Block.whiteStone,8)       , new Object[]{ new ItemStack(SimplePackingItem,1,62) });
		//blank 63 for 67
		GameRegistry.addShapelessRecipe(new ItemStack(Block.planks,8,0)         , new Object[]{ new ItemStack(SimplePackingItem,1,68) });
		GameRegistry.addShapelessRecipe(new ItemStack(Block.planks,8,1)         , new Object[]{ new ItemStack(SimplePackingItem,1,69) });
		GameRegistry.addShapelessRecipe(new ItemStack(Block.planks,8,2)         , new Object[]{ new ItemStack(SimplePackingItem,1,70) });
		GameRegistry.addShapelessRecipe(new ItemStack(Block.planks,8,3)         , new Object[]{ new ItemStack(SimplePackingItem,1,71) });
		//blank 72 for 73(new ItemStack
		GameRegistry.addShapelessRecipe(new ItemStack(Block.wood,8,0)           , new Object[]{ new ItemStack(SimplePackingItem,1,74) });
		GameRegistry.addShapelessRecipe(new ItemStack(Block.wood,8,1)           , new Object[]{ new ItemStack(SimplePackingItem,1,75) });
		GameRegistry.addShapelessRecipe(new ItemStack(Block.wood,8,2)           , new Object[]{ new ItemStack(SimplePackingItem,1,76) });
		GameRegistry.addShapelessRecipe(new ItemStack(Block.wood,8,3)           , new Object[]{ new ItemStack(SimplePackingItem,1,77) });
		//blank 78 for 79
		GameRegistry.addShapelessRecipe(new ItemStack(Block.cloth,8,0)          , new Object[]{ new ItemStack(SimplePackingItem,1,80) });
		GameRegistry.addShapelessRecipe(new ItemStack(Block.cloth,8,1)          , new Object[]{ new ItemStack(SimplePackingItem,1,81) });
		GameRegistry.addShapelessRecipe(new ItemStack(Block.cloth,8,2)          , new Object[]{ new ItemStack(SimplePackingItem,1,82) });
		GameRegistry.addShapelessRecipe(new ItemStack(Block.cloth,8,3)          , new Object[]{ new ItemStack(SimplePackingItem,1,83) });
		GameRegistry.addShapelessRecipe(new ItemStack(Block.cloth,8,4)          , new Object[]{ new ItemStack(SimplePackingItem,1,84) });
		GameRegistry.addShapelessRecipe(new ItemStack(Block.cloth,8,5)          , new Object[]{ new ItemStack(SimplePackingItem,1,85) });
		GameRegistry.addShapelessRecipe(new ItemStack(Block.cloth,8,6)          , new Object[]{ new ItemStack(SimplePackingItem,1,86) });
		GameRegistry.addShapelessRecipe(new ItemStack(Block.cloth,8,7)          , new Object[]{ new ItemStack(SimplePackingItem,1,87) });
		GameRegistry.addShapelessRecipe(new ItemStack(Block.cloth,8,8)          , new Object[]{ new ItemStack(SimplePackingItem,1,88) });
		GameRegistry.addShapelessRecipe(new ItemStack(Block.cloth,8,9)          , new Object[]{ new ItemStack(SimplePackingItem,1,89) });
		GameRegistry.addShapelessRecipe(new ItemStack(Block.cloth,8,10)         , new Object[]{ new ItemStack(SimplePackingItem,1,90) });
		GameRegistry.addShapelessRecipe(new ItemStack(Block.cloth,8,11)         , new Object[]{ new ItemStack(SimplePackingItem,1,91) });
		GameRegistry.addShapelessRecipe(new ItemStack(Block.cloth,8,12)         , new Object[]{ new ItemStack(SimplePackingItem,1,92) });
		GameRegistry.addShapelessRecipe(new ItemStack(Block.cloth,8,13)         , new Object[]{ new ItemStack(SimplePackingItem,1,93) });
		GameRegistry.addShapelessRecipe(new ItemStack(Block.cloth,8,14)         , new Object[]{ new ItemStack(SimplePackingItem,1,94) });
		GameRegistry.addShapelessRecipe(new ItemStack(Block.cloth,8,15)         , new Object[]{ new ItemStack(SimplePackingItem,1,95) });
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
		GameRegistry.addRecipe(new ItemStack(SimplePackingItemItem,1,  0), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), new ItemStack(Item.coal, 1, -1) });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItemItem,1,  1), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), Item.ingotIron });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItemItem,1,  2), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), Item.ingotGold });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItemItem,1,  3), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), Item.diamond });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItemItem,1,  4), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), Item.emerald });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItemItem,1,  5), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), Item.redstone });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItemItem,1,  6), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), Item.glowstone });
		//blank 7 for 9
		GameRegistry.addRecipe(new ItemStack(SimplePackingItemItem,1, 10), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), Item.gunpowder });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItemItem,1, 11), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), Item.flint });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItemItem,1, 12), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), Item.clay });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItemItem,1, 13), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), Item.brick });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItemItem,1, 14), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), Item.netherQuartz });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItemItem,1, 15), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), Item.netherrackBrick });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItemItem,1, 16), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), Item.blazeRod });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItemItem,1, 17), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), Item.goldNugget });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItemItem,1, 18), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), Item.ghastTear });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItemItem,1, 19), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), Item.netherStar });
		//blank 20 for 22
		GameRegistry.addRecipe(new ItemStack(SimplePackingItemItem,1, 23), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), Item.stick });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItemItem,1, 24), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), Item.bowlEmpty });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItemItem,1, 25), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), Item.feather });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItemItem,1, 26), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), Item.silk });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItemItem,1, 27), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), Item.leather });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItemItem,1, 28), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), Item.slimeBall });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItemItem,1, 29), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), Item.enderPearl });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItemItem,1, 30), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), Item.eyeOfEnder });
		//blank 31 for 33
		GameRegistry.addRecipe(new ItemStack(SimplePackingItemItem,1, 34), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), Item.bone });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItemItem,1, 35), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), Item.paper });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItemItem,1, 36), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), Item.book });
//		GameRegistry.addRecipe(new ItemStack(SimplePackingItemItem,1, 37), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), Item.writableBook });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItemItem,1, 38), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), Item.emptyMap });
		//blank 39 for 41
		GameRegistry.addRecipe(new ItemStack(SimplePackingItemItem,1, 42), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), Item.glassBottle });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItemItem,1, 43), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), Item.firework });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItemItem,1, 44), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), Item.firework });
		//blank 45 for 47
		GameRegistry.addRecipe(new ItemStack(SimplePackingItemItem,1, 48), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), Item.seeds });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItemItem,1, 49), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), Item.wheat });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItemItem,1, 50), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), Item.reed });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItemItem,1, 51), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), Item.pumpkinSeeds });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItemItem,1, 52), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), Item.melonSeeds });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItemItem,1, 53), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), Item.netherStalkSeeds });
		//blank 54 for 56
		GameRegistry.addRecipe(new ItemStack(SimplePackingItemItem,1, 57), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), Item.sugar });
		//blank 58 for 60
		GameRegistry.addRecipe(new ItemStack(SimplePackingItemItem,1, 61), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), new ItemStack(Item.dyePowder, 1, 0) });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItemItem,1, 62), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), new ItemStack(Item.dyePowder, 1, 1) });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItemItem,1, 63), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), new ItemStack(Item.dyePowder, 1, 2) });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItemItem,1, 64), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), new ItemStack(Item.dyePowder, 1, 3) });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItemItem,1, 65), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), new ItemStack(Item.dyePowder, 1, 4) });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItemItem,1, 66), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), new ItemStack(Item.dyePowder, 1, 5) });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItemItem,1, 67), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), new ItemStack(Item.dyePowder, 1, 6) });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItemItem,1, 68), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), new ItemStack(Item.dyePowder, 1, 7) });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItemItem,1, 69), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), new ItemStack(Item.dyePowder, 1, 8) });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItemItem,1, 70), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), new ItemStack(Item.dyePowder, 1, 9) });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItemItem,1, 71), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), new ItemStack(Item.dyePowder, 1, 10) });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItemItem,1, 72), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), new ItemStack(Item.dyePowder, 1, 11) });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItemItem,1, 73), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), new ItemStack(Item.dyePowder, 1, 12) });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItemItem,1, 74), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), new ItemStack(Item.dyePowder, 1, 13) });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItemItem,1, 75), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), new ItemStack(Item.dyePowder, 1, 14) });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItemItem,1, 76), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), new ItemStack(Item.dyePowder, 1, 15) });
		//blank 77 for 92
		GameRegistry.addRecipe(new ItemStack(SimplePackingItemItem,1, 93), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), Item.appleRed });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItemItem,1, 94), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), new ItemStack(Item.appleGold, 1, 0) });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItemItem,1, 95), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), new ItemStack(Item.appleGold, 1, 1) });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItemItem,1, 96), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), Item.bowlSoup });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItemItem,1, 97), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), Item.bread });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItemItem,1, 98), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), Item.porkRaw });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItemItem,1, 99), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), Item.porkCooked });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItemItem,1,100), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), Item.fishRaw });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItemItem,1,101), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), Item.fishCooked });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItemItem,1,102), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), Item.cake });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItemItem,1,103), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), Item.cookie });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItemItem,1,104), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), Item.melon });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItemItem,1,105), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), Item.beefRaw });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItemItem,1,106), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), Item.beefCooked });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItemItem,1,107), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), Item.chickenRaw });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItemItem,1,108), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), Item.chickenCooked });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItemItem,1,109), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), Item.carrot });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItemItem,1,110), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), Item.goldenCarrot });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItemItem,1,111), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), Item.potato });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItemItem,1,112), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), Item.bakedPotato });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItemItem,1,113), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), Item.poisonousPotato });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItemItem,1,114), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), Item.pumpkinPie });
		//blank 115 for 117
		GameRegistry.addRecipe(new ItemStack(SimplePackingItemItem,1,118), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), Item.rottenFlesh });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItemItem,1,119), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), Item.spiderEye });
		GameRegistry.addRecipe(new ItemStack(SimplePackingItemItem,1,120), new Object[]{ Recipemold[0],Recipemold[1],Recipemold[2], Character.valueOf('X'), Item.egg });

		//ReverseCraft
		GameRegistry.addShapelessRecipe(new ItemStack(Item.coal,8,0)               , new Object[]{ new ItemStack(SimplePackingItemItem,1,  0) });
		GameRegistry.addShapelessRecipe(new ItemStack(Item.ingotIron,8)            , new Object[]{ new ItemStack(SimplePackingItemItem,1,  1) });
		GameRegistry.addShapelessRecipe(new ItemStack(Item.ingotGold,8)            , new Object[]{ new ItemStack(SimplePackingItemItem,1,  2) });
		GameRegistry.addShapelessRecipe(new ItemStack(Item.diamond,8)              , new Object[]{ new ItemStack(SimplePackingItemItem,1,  3) });
		GameRegistry.addShapelessRecipe(new ItemStack(Item.emerald,8)              , new Object[]{ new ItemStack(SimplePackingItemItem,1,  4) });
		GameRegistry.addShapelessRecipe(new ItemStack(Item.redstone,8)             , new Object[]{ new ItemStack(SimplePackingItemItem,1,  5) });
		GameRegistry.addShapelessRecipe(new ItemStack(Item.glowstone,8)       , new Object[]{ new ItemStack(SimplePackingItemItem,1,  6) });
		//blank 7 for 9
		GameRegistry.addShapelessRecipe(new ItemStack(Item.gunpowder,8)            , new Object[]{ new ItemStack(SimplePackingItemItem,1, 10) });
		GameRegistry.addShapelessRecipe(new ItemStack(Item.flint,8)                , new Object[]{ new ItemStack(SimplePackingItemItem,1, 11) });
		GameRegistry.addShapelessRecipe(new ItemStack(Item.clay,8)                 , new Object[]{ new ItemStack(SimplePackingItemItem,1, 12) });
		GameRegistry.addShapelessRecipe(new ItemStack(Item.brick,8)                , new Object[]{ new ItemStack(SimplePackingItemItem,1, 13) });
		GameRegistry.addShapelessRecipe(new ItemStack(Item.netherQuartz,8)       , new Object[]{ new ItemStack(SimplePackingItemItem,1, 14) });
		GameRegistry.addShapelessRecipe(new ItemStack(Item.netherrackBrick,8)       , new Object[]{ new ItemStack(SimplePackingItemItem,1, 15) });
		GameRegistry.addShapelessRecipe(new ItemStack(Item.blazeRod,8)             , new Object[]{ new ItemStack(SimplePackingItemItem,1, 16) });
		GameRegistry.addShapelessRecipe(new ItemStack(Item.goldNugget,8)           , new Object[]{ new ItemStack(SimplePackingItemItem,1, 17) });
		GameRegistry.addShapelessRecipe(new ItemStack(Item.ghastTear,8)            , new Object[]{ new ItemStack(SimplePackingItemItem,1, 18) });
		GameRegistry.addShapelessRecipe(new ItemStack(Item.netherStar,8)           , new Object[]{ new ItemStack(SimplePackingItemItem,1, 19) });
		//blank 20 for 22
		GameRegistry.addShapelessRecipe(new ItemStack(Item.stick,8)                , new Object[]{ new ItemStack(SimplePackingItemItem,1, 23) });
		GameRegistry.addShapelessRecipe(new ItemStack(Item.bowlEmpty,8)            , new Object[]{ new ItemStack(SimplePackingItemItem,1, 24) });
		GameRegistry.addShapelessRecipe(new ItemStack(Item.feather,8)              , new Object[]{ new ItemStack(SimplePackingItemItem,1, 25) });
		GameRegistry.addShapelessRecipe(new ItemStack(Item.silk,8)                 , new Object[]{ new ItemStack(SimplePackingItemItem,1, 26) });
		GameRegistry.addShapelessRecipe(new ItemStack(Item.leather,8)              , new Object[]{ new ItemStack(SimplePackingItemItem,1, 27) });
		GameRegistry.addShapelessRecipe(new ItemStack(Item.slimeBall,8)            , new Object[]{ new ItemStack(SimplePackingItemItem,1, 28) });
		GameRegistry.addShapelessRecipe(new ItemStack(Item.enderPearl,8)           , new Object[]{ new ItemStack(SimplePackingItemItem,1, 29) });
		GameRegistry.addShapelessRecipe(new ItemStack(Item.eyeOfEnder,8)           , new Object[]{ new ItemStack(SimplePackingItemItem,1, 30) });
		//blank 31 for 33
		GameRegistry.addShapelessRecipe(new ItemStack(Item.bone,8)                 , new Object[]{ new ItemStack(SimplePackingItemItem,1, 34) });
		GameRegistry.addShapelessRecipe(new ItemStack(Item.paper,8)                , new Object[]{ new ItemStack(SimplePackingItemItem,1, 35) });
		GameRegistry.addShapelessRecipe(new ItemStack(Item.book,8)                 , new Object[]{ new ItemStack(SimplePackingItemItem,1, 36) });
//		GameRegistry.addShapelessRecipe(new ItemStack(Item.writableBook,8)         , new Object[]{ new ItemStack(SimplePackingItemItem,1, 37) });
		GameRegistry.addShapelessRecipe(new ItemStack(Item.emptyMap,8)             , new Object[]{ new ItemStack(SimplePackingItemItem,1, 38) });
		//blank 39 for 41
		GameRegistry.addShapelessRecipe(new ItemStack(Item.glassBottle,8)          , new Object[]{ new ItemStack(SimplePackingItemItem,1, 42) });
		GameRegistry.addShapelessRecipe(new ItemStack(Item.firework,8)             , new Object[]{ new ItemStack(SimplePackingItemItem,1, 43) });
		GameRegistry.addShapelessRecipe(new ItemStack(Item.firework,8)             , new Object[]{ new ItemStack(SimplePackingItemItem,1, 44) });
		//blank 45 for 47
		GameRegistry.addShapelessRecipe(new ItemStack(Item.seeds,8)                , new Object[]{ new ItemStack(SimplePackingItemItem,1, 48) });
		GameRegistry.addShapelessRecipe(new ItemStack(Item.wheat,8)                , new Object[]{ new ItemStack(SimplePackingItemItem,1, 49) });
		GameRegistry.addShapelessRecipe(new ItemStack(Item.reed,8)                 , new Object[]{ new ItemStack(SimplePackingItemItem,1, 50) });
		GameRegistry.addShapelessRecipe(new ItemStack(Item.pumpkinSeeds,8)         , new Object[]{ new ItemStack(SimplePackingItemItem,1, 51) });
		GameRegistry.addShapelessRecipe(new ItemStack(Item.melonSeeds,8)           , new Object[]{ new ItemStack(SimplePackingItemItem,1, 52) });
		GameRegistry.addShapelessRecipe(new ItemStack(Item.netherStalkSeeds,8)     , new Object[]{ new ItemStack(SimplePackingItemItem,1, 53) });
		//blank 54 for 56
		GameRegistry.addShapelessRecipe(new ItemStack(Item.sugar,8)                , new Object[]{ new ItemStack(SimplePackingItemItem,1, 57) });
		//blank 58 for 60
		GameRegistry.addShapelessRecipe(new ItemStack(Item.dyePowder,8,0)          , new Object[]{ new ItemStack(SimplePackingItemItem,1, 61) });
		GameRegistry.addShapelessRecipe(new ItemStack(Item.dyePowder,8,1)          , new Object[]{ new ItemStack(SimplePackingItemItem,1, 62) });
		GameRegistry.addShapelessRecipe(new ItemStack(Item.dyePowder,8,2)          , new Object[]{ new ItemStack(SimplePackingItemItem,1, 63) });
		GameRegistry.addShapelessRecipe(new ItemStack(Item.dyePowder,8,3)          , new Object[]{ new ItemStack(SimplePackingItemItem,1, 64) });
		GameRegistry.addShapelessRecipe(new ItemStack(Item.dyePowder,8,4)          , new Object[]{ new ItemStack(SimplePackingItemItem,1, 65) });
		GameRegistry.addShapelessRecipe(new ItemStack(Item.dyePowder,8,5)          , new Object[]{ new ItemStack(SimplePackingItemItem,1, 66) });
		GameRegistry.addShapelessRecipe(new ItemStack(Item.dyePowder,8,6)          , new Object[]{ new ItemStack(SimplePackingItemItem,1, 67) });
		GameRegistry.addShapelessRecipe(new ItemStack(Item.dyePowder,8,7)          , new Object[]{ new ItemStack(SimplePackingItemItem,1, 68) });
		GameRegistry.addShapelessRecipe(new ItemStack(Item.dyePowder,8,8)          , new Object[]{ new ItemStack(SimplePackingItemItem,1, 69) });
		GameRegistry.addShapelessRecipe(new ItemStack(Item.dyePowder,8,9)          , new Object[]{ new ItemStack(SimplePackingItemItem,1, 70) });
		GameRegistry.addShapelessRecipe(new ItemStack(Item.dyePowder,8,10)         , new Object[]{ new ItemStack(SimplePackingItemItem,1, 71) });
		GameRegistry.addShapelessRecipe(new ItemStack(Item.dyePowder,8,11)         , new Object[]{ new ItemStack(SimplePackingItemItem,1, 72) });
		GameRegistry.addShapelessRecipe(new ItemStack(Item.dyePowder,8,12)         , new Object[]{ new ItemStack(SimplePackingItemItem,1, 73) });
		GameRegistry.addShapelessRecipe(new ItemStack(Item.dyePowder,8,13)         , new Object[]{ new ItemStack(SimplePackingItemItem,1, 74) });
		GameRegistry.addShapelessRecipe(new ItemStack(Item.dyePowder,8,14)         , new Object[]{ new ItemStack(SimplePackingItemItem,1, 75) });
		GameRegistry.addShapelessRecipe(new ItemStack(Item.dyePowder,8,15)         , new Object[]{ new ItemStack(SimplePackingItemItem,1, 76) });
		//blank 77 for 92
		GameRegistry.addShapelessRecipe(new ItemStack(Item.appleRed,8)             , new Object[]{ new ItemStack(SimplePackingItemItem,1, 93) });
		GameRegistry.addShapelessRecipe(new ItemStack(Item.appleGold,8,0)          , new Object[]{ new ItemStack(SimplePackingItemItem,1, 94) });
		GameRegistry.addShapelessRecipe(new ItemStack(Item.appleGold,8,1)          , new Object[]{ new ItemStack(SimplePackingItemItem,1, 95) });
		GameRegistry.addShapelessRecipe(new ItemStack(Item.bowlSoup,8)             , new Object[]{ new ItemStack(SimplePackingItemItem,1, 96) });
		GameRegistry.addShapelessRecipe(new ItemStack(Item.bread,8)                , new Object[]{ new ItemStack(SimplePackingItemItem,1, 97) });
		GameRegistry.addShapelessRecipe(new ItemStack(Item.porkRaw,8)              , new Object[]{ new ItemStack(SimplePackingItemItem,1, 98) });
		GameRegistry.addShapelessRecipe(new ItemStack(Item.porkCooked,8)           , new Object[]{ new ItemStack(SimplePackingItemItem,1, 99) });
		GameRegistry.addShapelessRecipe(new ItemStack(Item.fishRaw,8)              , new Object[]{ new ItemStack(SimplePackingItemItem,1,100) });
		GameRegistry.addShapelessRecipe(new ItemStack(Item.fishCooked,8)           , new Object[]{ new ItemStack(SimplePackingItemItem,1,101) });
		GameRegistry.addShapelessRecipe(new ItemStack(Item.cake,8)                 , new Object[]{ new ItemStack(SimplePackingItemItem,1,102) });
		GameRegistry.addShapelessRecipe(new ItemStack(Item.cookie,8)               , new Object[]{ new ItemStack(SimplePackingItemItem,1,103) });
		GameRegistry.addShapelessRecipe(new ItemStack(Item.melon,8)                , new Object[]{ new ItemStack(SimplePackingItemItem,1,104) });
		GameRegistry.addShapelessRecipe(new ItemStack(Item.beefRaw,8)              , new Object[]{ new ItemStack(SimplePackingItemItem,1,105) });
		GameRegistry.addShapelessRecipe(new ItemStack(Item.beefCooked,8)           , new Object[]{ new ItemStack(SimplePackingItemItem,1,106) });
		GameRegistry.addShapelessRecipe(new ItemStack(Item.chickenRaw,8)           , new Object[]{ new ItemStack(SimplePackingItemItem,1,107) });
		GameRegistry.addShapelessRecipe(new ItemStack(Item.chickenCooked,8)        , new Object[]{ new ItemStack(SimplePackingItemItem,1,108) });
		GameRegistry.addShapelessRecipe(new ItemStack(Item.carrot,8)               , new Object[]{ new ItemStack(SimplePackingItemItem,1,109) });
		GameRegistry.addShapelessRecipe(new ItemStack(Item.goldenCarrot,8)         , new Object[]{ new ItemStack(SimplePackingItemItem,1,110) });
		GameRegistry.addShapelessRecipe(new ItemStack(Item.potato,8)               , new Object[]{ new ItemStack(SimplePackingItemItem,1,111) });
		GameRegistry.addShapelessRecipe(new ItemStack(Item.bakedPotato,8)          , new Object[]{ new ItemStack(SimplePackingItemItem,1,112) });
		GameRegistry.addShapelessRecipe(new ItemStack(Item.poisonousPotato,8)      , new Object[]{ new ItemStack(SimplePackingItemItem,1,113) });
		GameRegistry.addShapelessRecipe(new ItemStack(Item.pumpkinPie,8)           , new Object[]{ new ItemStack(SimplePackingItemItem,1,114) });
		//blank 115 for 117
		GameRegistry.addShapelessRecipe(new ItemStack(Item.rottenFlesh,8)          , new Object[]{ new ItemStack(SimplePackingItemItem,1,118) });
		GameRegistry.addShapelessRecipe(new ItemStack(Item.spiderEye,8)            , new Object[]{ new ItemStack(SimplePackingItemItem,1,119) });
		GameRegistry.addShapelessRecipe(new ItemStack(Item.egg,8)                  , new Object[]{ new ItemStack(SimplePackingItemItem,1,120) });

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
		GameRegistry.addShapelessRecipe(new ItemStack(Block.sandStone,1,1)      , new Object[]{ new ItemStack(Block.sandStone,1,-1), Block.dirt });
		GameRegistry.addShapelessRecipe(new ItemStack(Block.sandStone,1,2)      , new Object[]{ new ItemStack(Block.sandStone,1,-1), Block.sand });
		GameRegistry.addShapelessRecipe(new ItemStack(Block.stoneBrick,1,1)     , new Object[]{ new ItemStack(Block.stoneBrick,1,-1), Block.vine });
		GameRegistry.addShapelessRecipe(new ItemStack(Block.stoneBrick,1,2)     , new Object[]{ new ItemStack(Block.stoneBrick,1,-1), Block.gravel });
		GameRegistry.addShapelessRecipe(new ItemStack(Block.stoneBrick,1,3)     , new Object[]{ new ItemStack(Block.stoneBrick,1,-1), Block.dirt });
	}
}