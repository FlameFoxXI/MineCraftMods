package mods.doca.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;

import org.lwjgl.input.Keyboard;

import cpw.mods.fml.common.*;
import cpw.mods.fml.common.event.*;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.*;
import net.minecraft.client.Minecraft;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.*;
import net.minecraft.util.StatCollector;
import net.minecraftforge.common.*;
import net.minecraftforge.common.config.Configuration;
import mods.doca.client.render.entity.*;
import mods.doca.client.render.entity.biped.*;
import mods.doca.client.render.entity.passive.*;
import mods.doca.entity.*;
import mods.doca.entity.biped.*;
import mods.doca.entity.passive.*;
import mods.doca.item.*;

public class DocaSet
{
	/*-------------------------------------------------------------
		ModSetup
	--------------------------------------------------------------*/
	public final static String MODID = "Doca";
	public final static String MODNAME = "DogCatPlus";
	public final static String MCVERSION = "1.7.4";
	public final static String MODVERSION = MCVERSION + "_" +"2.2.7";
	public static final String MODCLIENT = "mods.doca.core.proxy.ClientProxy";
	public static final String MODSERVER = "mods.doca.core.proxy.CommonProxy";
	
	/*-------------------------------------------------------------
		CATEGORY
	--------------------------------------------------------------*/
//	public static String DOCA_CATEGORY_ITEM  = Configuration.CATEGORY_ITEM;
	public static String DOCA_CATEGORY_ITEM  = "items";
//	public static String DOCA_CATEGORY_BLOCK = Configuration.CATEGORY_BLOCK;
	public static String DOCA_CATEGORY_TAB   = "tab";
	public static String DOCA_CATEGORY_MOB   = "mob";
	public static String DOCA_CATEGORY_ENTITY   = "entity";
	public static String DOCA_CATEGORY_ENTITY_TYPE   = "entity_type";
	public static String DOCA_CATEGORY_ENTITY_USE   = "entity_use";
	public static String DOCA_CATEGORY_KEY   = "key";
	public static String DOCA_CATEGORY_SHOW  = "show";
	public static String DOCA_CATEGORY_FUNC  = "func";
	public static String DOCA_CATEGORY_ATTACK  = "attack";
	public static String DOCA_CATEGORY_TEXTURE  = "texture";
	public static String DOCA_CATEGORY_CONVERT  = "convert";

	/*-------------------------------------------------------------
		item
	--------------------------------------------------------------*/
//	public static int item_SpawnDocaID = 5000;
//	public static int item_CarrierBagID = 5010;
//	public static int item_RibbonID = 5011;
	public static boolean item_CarrierBagReuse = false;

	/*-------------------------------------------------------------
		block
	--------------------------------------------------------------*/
//	public static int block_DocaChestID = 3510;

	/*-------------------------------------------------------------
		tab
	--------------------------------------------------------------*/
	public static boolean tab_AddDocaCreativeTab = true;

	/*-------------------------------------------------------------
		entity_type
	--------------------------------------------------------------*/
	public static boolean entity_type_OldType  = false;

	/*-------------------------------------------------------------
		entity
	--------------------------------------------------------------*/
	public static int entity_DoggyID = 0;
	public static int entity_KittyID = 0;
	public static int entity_SkeletonID = 0;
	public static int entity_BunnyID = 0;
	public static int entity_CactuarID = 0;
	public static int entity_EndermanID = 0;
	public static int entity_HumanCatID = 0;

	/*-------------------------------------------------------------
		entity_use
	--------------------------------------------------------------*/
	public static boolean entity_use_Doggy = true;
	public static boolean entity_use_Kitty = true;
	public static boolean entity_use_Skeleton = true;
	public static boolean entity_use_Bunny = true;
	public static boolean entity_use_Cactuar = true;
	public static boolean entity_use_Enderman = true;
	public static boolean entity_use_HumanCat = true;
	public static boolean entity_use_Zombie = false;
	public static boolean entity_use_Chicken = false;

	/*-------------------------------------------------------------
		mob
	--------------------------------------------------------------*/
	public static boolean mob_GrowAgeDoggy  = false;
	public static boolean mob_GrowAgeKitty  = false;
	public static boolean mob_GrowAgeSkeleton  = false;
	public static boolean mob_GrowAgeBunny = false;
	public static boolean mob_GrowAgeCactuar  = false;
	public static boolean mob_GrowAgeEnderman = false;
	public static boolean mob_GrowAgeHumanCat = false;
	public static boolean mob_GrowAgeZombie = false;
	public static boolean mob_GrowAgeChichen = false;

	/*-------------------------------------------------------------
		key
	--------------------------------------------------------------*/
	public static int key_CommandFree = Keyboard.KEY_NUMPAD0;
	public static int key_CommandComeing  = Keyboard.KEY_NUMPAD3;
	public static int key_CommandDowning  = Keyboard.KEY_NUMPAD2;
	public static int key_CommandDistance = Keyboard.KEY_NUMPAD1;
	public static int key_CommandInfomation  = Keyboard.KEY_I;
	public static int key_ControlInventoryGUI  = Keyboard.KEY_LCONTROL;
	public static int key_ControlSettingGUI = Keyboard.KEY_RCONTROL;
	public static int key_ControlSit  = Keyboard.KEY_LMENU;
	public static int key_ControlHomePoint = Keyboard.KEY_RMENU;

	/*-------------------------------------------------------------
		show
	--------------------------------------------------------------*/
	public static boolean show_LifeBar  = true;
	public static boolean show_NameBar  = true;
	public static int     show_NameColor  = 0xFFFFFF;
	public static int     show_BarBackGroundStrength  = 192;
	public static int     show_BarBackGroundColor  = 0x000000;
	public static boolean show_StatusIcon = true;
	public static int     show_StatusIconType = 0;

	/*-------------------------------------------------------------
		func
	--------------------------------------------------------------*/

	public static boolean func_particleON = false;
	public static int     func_particleStep = 0;
	public static boolean func_CreeperAlertON  = true;
	public static boolean func_StopNolife = false;
	public static boolean func_Speed  = true;
	public static boolean func_ItemPickUpON  = true;
	public static int     func_ItemPickUpWidely = 10;
	public static boolean func_ItemPickUpOffEquipItem = false;
	public static boolean func_ItemPickUpOffItemID = false;
	public static String  func_ItemPickUpOffItemIDs = "";
	public static List<Integer> func_ItemPickUpOffItemIDsDoca = new ArrayList<Integer>();
	public static boolean func_ItemPopUpON  = true;
	public static int     func_MoveSquareToHomePoint = 10;
	public static boolean func_HowlingMoonDoggy = true;
	public static boolean func_HealingBed = true;
	public static boolean func_EmotionBalloonON = true;
	public static boolean func_AccessPermissionOPS = false;

	/*-------------------------------------------------------------
		attack
	--------------------------------------------------------------*/
	public static boolean attack_AdditionalEffectON = true;
	public static boolean attack_PetInvincibleOFF = false;

	public static boolean attack_PeacefulON  = false;
	public static boolean attack_TameableON  = false;

	public static String   attack_EntityForWoodSpirit = "Spider,Skeleton,Zombie";
	public static String[] attack_EntityDoca = new String[0];
	public static String   attack_FriendlyEntity = "Creeper";
	public static String[] attack_FriendlyEntityDoca = new String[0];

	/*-------------------------------------------------------------
		texture
	--------------------------------------------------------------*/
	public static int texture_SizeMaxDoggy = 13;
	public static int texture_SizeMaxKitty = 5;
	public static int texture_SizeMaxSkeleton = 2;
	public static int texture_SizeMaxBunny = 2;
	public static int texture_SizeMaxCactuar = 2;
	public static int texture_SizeMaxEnderman = 2;
	public static int texture_SizeMaxHumanCat = 2;
	public static int texture_SizeMaxZombie = 4;
	public static int texture_SizeMaxChicken = 2;

	/*-------------------------------------------------------------
		convert
	--------------------------------------------------------------*/
	public static String   convert_NameString = "";
	public static String[] convert_NameStringDoca = new String[0];


	/*---------------------------------------------------------------------------------------------------------------------------------

	-----------------------------------------------------------------------------------------------------------------------------------*/
	//texture
	public static int texture_SizeMaxDefault = 1;

	public static String imageFoldaDoca = "doca:";

	public static String texture_Doggy = "textures/entity/doggy/doggy";
	public static String texture_Kitty = "textures/entity/kitty/kitty";
	public static String texture_Skeleton = "textures/entity/skeleton/skeleton";
	public static String texture_Bunny = "textures/entity/bunny/bunny";
	public static String texture_Cactuar = "textures/entity/cactuar/cactuar";
	public static String texture_Enderman = "textures/entity/enderman/enderman";
	public static String texture_HumanCat = "textures/entity/humancat/humancat";
	public static String texture_Zombie = "textures/entity/zombie/zombie";
	public static String texture_Chicken = "textures/entity/chicken/chicken";
	public static String texture_Chest = "textures/entity/Chest/";

	public static String textureDoggy = imageFoldaDoca + texture_Doggy + ".png";
	public static String textureDoggyTame = imageFoldaDoca + texture_Doggy +"_tame.png";
	public static String textureDoggyCollar = imageFoldaDoca + texture_Doggy + "_collar.png";

	public static String textureKitty = imageFoldaDoca + texture_Kitty + ".png";
	public static String textureKittyTame = imageFoldaDoca + texture_Kitty +"_tame.png";
	public static String textureKittyCollar = imageFoldaDoca + texture_Kitty + "_collar.png";

	public static String textureSkeleton = imageFoldaDoca + texture_Skeleton + ".png";
	public static String textureSkeletonTame = imageFoldaDoca + texture_Skeleton + "_tame.png";
	public static String textureSkeletonCollar = imageFoldaDoca + texture_Skeleton + "_collar.png";

	public static String textureBunny = imageFoldaDoca + texture_Bunny + ".png";
	public static String textureBunnyTame = imageFoldaDoca + texture_Bunny + "_tame.png";
	public static String textureBunnyCollar = imageFoldaDoca + texture_Bunny + "_collar.png";

	public static String textureCactuar = imageFoldaDoca + texture_Cactuar + ".png";
	public static String textureCactuarTame = imageFoldaDoca + texture_Cactuar + "_tame.png";
	public static String textureCactuarCollar = imageFoldaDoca + texture_Cactuar + "_collar.png";

	public static String textureEnderman = imageFoldaDoca + texture_Enderman + ".png";
	public static String textureEndermanTame = imageFoldaDoca + texture_Enderman + "_tame.png";
	public static String textureEndermanCollar = imageFoldaDoca + texture_Enderman + "_collar.png";

	public static String textureHumanCat = imageFoldaDoca + texture_HumanCat + ".png";
	public static String textureHumanCatTame = imageFoldaDoca + texture_HumanCat + "_tame.png";
	public static String textureHumanCatCollar = imageFoldaDoca + texture_HumanCat + "_collar.png";

	public static String textureZombie = imageFoldaDoca + texture_Zombie + ".png";
	public static String textureZombieTame = imageFoldaDoca + texture_Zombie + "_tame.png";
	public static String textureZombieCollar = imageFoldaDoca + texture_Zombie + "_collar.png";

	public static String textureChicken = imageFoldaDoca + texture_Chicken + ".png";
	public static String textureChickenTame = imageFoldaDoca + texture_Chicken + "_tame.png";
	public static String textureChickenCollar = imageFoldaDoca + texture_Chicken + "_collar.png";

	public static String textureSettingGUI = imageFoldaDoca + "textures/gui/background.png";
	public static String textureInventoryGUI = imageFoldaDoca + "textures/gui/container/inventory.png";
	public static String textureInfoGUI = imageFoldaDoca + "textures/gui/infoicon.png";
	public static String textureEmoteWhite = imageFoldaDoca + "textures/gui/emotion/white.png";
	public static String textureEmoteAngry = imageFoldaDoca + "textures/gui/emotion/angry.png";
	public static String textureEmoteFood = imageFoldaDoca + "textures/gui/emotion/food.png";
	public static String textureEmoteHappy = imageFoldaDoca + "textures/gui/emotion/happy.png";
	public static String textureEmoteheart = imageFoldaDoca + "textures/gui/emotion/heart.png";

	public static String texture_ItemEgg = "egg";
	public static String texture_ItemCarrierBag = "book_normal";
	public static String texture_ItemRibbon = "paper";
	public static String textureItemEmptySword = imageFoldaDoca + "empty_sword";
	public static String textureItemEmptyFoods = imageFoldaDoca + "empty_foods";
	public static String textureItemEmptyFunction = imageFoldaDoca + "empty_function";
	public static String textureCreativeTabsSymbol = imageFoldaDoca + "symbol";

	public static String texture_TileEntity_Path = imageFoldaDoca + texture_Chest;
	public static String texture_TileEntityChest = "textures/entity/chest/normal.png";
	public static String texture_TileEntityChestChristmas = "textures/entity/chest/christmas.png";
	
	/*---------------------------------------------------------------------------------------------------------------------------------
		tmpConfig
	-----------------------------------------------------------------------------------------------------------------------------------*/
	public static String tmpfunc_ItemPickUpOffItemIDs;
	
	//Setting ID
	public static int containerContainerID = 0;
	public static int containerSettingID = 1;

	//Item / Block
	public static Item spawnDocaItem;
	public static Item spawnCarrierBagItem;
	public static Item spawnItemRibbon;
	public static Block blockDocaChest;

	//Timer
	public static int keyTimer  = 0;
	public static int maxHelthTimer  = 6000;
	public static int maxItemPopTimer  = 24000;
	public static int maxEmotionTimer  = 30;
	public static int maxCestSearchTimer = 2000;

//	public static Item[] listToItemPop1 = {Item.coal, Item.ingotIron, Item.ingotGold };
	public static Item[] listToItemPop1 = {Items.coal, Items.iron_ingot, Items.gold_ingot };
//	public static Item[] listToItemPop2 = {Item.beefCooked, Item.chickenCooked, Item.appleRed };
	public static Item[] listToItemPop2 = {Items.cooked_beef, Items.cooked_chicken, Items.apple };
//	public static Item[] listToItemPop3 = {Item.seeds, Item.carrot, Item.potato };
	public static Item[] listToItemPop3 = {Items.wheat_seeds, Items.carrot, Items.potato };

	//InventoryCheck
	public static boolean copyToInventoryCheck = false;
	public static int copyToInventoryCount = 0;

	//Ops
	public static boolean isUserOps = false;
	
	//Key Status
	public static boolean statusComeing = false;
	public static boolean statusDowning = false;
	public static boolean statusDistance = false;
	public static boolean statusFree = false;
	public static String statusKeyplessUser = "";
	public static ArrayList<DocaKeys> keySeverDoca = new ArrayList<DocaKeys>();
	public static DocaKeys keyLocalDoca = new DocaKeys("");
	public static DocaKeys tempKeyLocalDoca = new DocaKeys("");
	public static int keySeverDocaTickTimerMAX = 24000;
	public static int keySeverDocaTickTimer = keySeverDocaTickTimerMAX;

	//CreativeTabs
	public static CreativeTabs tabsDocaCreativeTabs;

	//Debug code
	public static boolean Debug = false;
	public static boolean DebugPaket = false;
	public static boolean DebugData = false;
	public static boolean DebugItem = false;
	public static boolean DebugStop = false;

	public final static String PET_DOGGY = "Doggy";
	public final static String PET_KITTY = "Kitty";
	public final static String PET_SKELE = "Skeleton";
	public final static String PET_BUNNY = "Bunny";
	public final static String PET_CACTU = "Cactuar";
	public final static String PET_ENDER = "Enderman";
	public final static String PET_HUMCA = "HumanCat";
	public final static String PET_CHIKN = "Gallus";
	public final static String PET_ZOMBE = "ZomChan";

	public static DocaSetTable addEntityCoreSet[] = {																																											/*   width, height,  hNormal, hBig, hSmall, LNormal, LBig, LSmall */
//		new DocaSetTable(0, DocaSet.PET_DOGGY, "DocaEntityDog",  "DocaDog",   DocaEntityDoggy.class,    Item.diamond, Item.beefCooked,    0.30000001192092896D, Item.beefRaw,     Item.beefCooked, Item.porkRaw,    Item.porkCooked,    0.6F,  0.8F,     0.8F,  0.8F,  0.8F,    1,     2,    0),
		new DocaSetTable(0, DocaSet.PET_DOGGY, "DocaEntityDog",  "DocaDog",   DocaEntityDoggy.class,    Items.diamond, Items.cooked_beef,     0.30000001192092896D, Items.beef,         Items.cooked_beef,     Items.porkchop,    Items.cooked_porkchop,  0.6F,  0.8F,     0.8F,  0.8F,  0.8F,    1,     2,    0),
//		new DocaSetTable(1, DocaSet.PET_KITTY, "DocaEntityKit",  "DocaKit",   DocaEntityKitty.class,    Item.diamond, Item.fishCooked,    0.30000001192092896D, Item.fishRaw,     Item.fishCooked, Item.chickenRaw, Item.chickenCooked, 0.6F,  0.8F,     0.8F,  0.8F,  0.8F,    1,     2,    0),
		new DocaSetTable(1, DocaSet.PET_KITTY, "DocaEntityKit",  "DocaKit",   DocaEntityKitty.class,    Items.diamond, Items.cooked_fished,   0.30000001192092896D, Items.fish,         Items.cooked_fished,   Items.chicken,     Items.cooked_chicken,   0.6F,  0.8F,     0.8F,  0.8F,  0.8F,    1,     2,    0),
//		new DocaSetTable(2, DocaSet.PET_SKELE, "DocaEntitySke",  "DocaSke",   DocaEntitySkeleton.class, Item.diamond, Item.bone,          0.30000001192092896D, Item.rottenFlesh, Item.beefRaw,    Item.porkRaw,    Item.chickenRaw,    0.6F,  1.8F,     1.8F,  1.8F,  0.9F,    5,     8,    2),
		new DocaSetTable(2, DocaSet.PET_SKELE, "DocaEntitySke",  "DocaSke",   DocaEntitySkeleton.class, Items.diamond, Items.bone,            0.30000001192092896D, Items.rotten_flesh, Items.beef,            Items.porkchop,    Items.chicken,          0.6F,  1.8F,     1.8F,  1.8F,  0.9F,    5,     8,    2),
//		new DocaSetTable(3, DocaSet.PET_BUNNY, "DocaEntityBun",  "DocaBun",   DocaEntityBunny.class,    Item.diamond, Item.wheat,         0.30000001192092896D, Item.bread,       Item.appleRed,   Item.carrot,     Item.potato,        0.6F,  0.8F,     0.8F,  0.8F,  0.8F,    1,     2,    0),
		new DocaSetTable(3, DocaSet.PET_BUNNY, "DocaEntityBun",  "DocaBun",   DocaEntityBunny.class,    Items.diamond, Items.wheat,           0.30000001192092896D, Items.bread,        Items.apple,           Items.carrot,      Items.potato,           0.6F,  0.8F,     0.8F,  0.8F,  0.8F,    1,     2,    0),
//		new DocaSetTable(4, DocaSet.PET_CACTU, "DocaEntityCac",  "DocaCac",   DocaEntityCactuar.class,  Item.diamond, Block.cactus,       0.30000001192092896D, Item.appleRed,    Item.appleGold,  Item.melon,      Item.potato,        0.6F,  0.8F,     0.8F,  0.8F,  0.8F,    1,     2,    0),
		new DocaSetTable(4, DocaSet.PET_CACTU, "DocaEntityCac",  "DocaCac",   DocaEntityCactuar.class,  Items.diamond, Blocks.cactus,         0.30000001192092896D, Items.apple,        Items.golden_apple,    Items.melon,       Items.potato,           0.6F,  0.8F,     0.8F,  0.8F,  0.8F,    1,     2,    0),
//		new DocaSetTable(5, DocaSet.PET_ENDER, "DocaEntityEnd",  "DocaEnd",   DocaEntityEnderman.class, Items.diamond, Item.emerald,       0.30000001192092896D, Item.appleRed,    Item.appleGold,  Item.pumpkinPie, Item.melon,         0.6F,  2.9F,     1.8F,  2.9F,  1.4F,    7,     11,   3),
		new DocaSetTable(5, DocaSet.PET_ENDER, "DocaEntityEnd",  "DocaEnd",   DocaEntityEnderman.class, Items.diamond, Items.emerald,         0.30000001192092896D, Items.apple,        Items.golden_apple,    Items.pumpkin_pie, Items.melon,            0.6F,  2.9F,     1.8F,  2.9F,  1.4F,    7,     11,   3),
//		new DocaSetTable(6, DocaSet.PET_HUMCA, "DocaEntityHCa",  "DocaHCa",   DocaEntityHumanCat.class, Item.diamond, Item.chickenCooked, 0.30000001192092896D, Item.porkRaw,     Item.porkCooked, Item.chickenRaw, Item.chickenCooked, 0.6F,  1.1F,     1.1F,  1.6F,  0.7F,    2,     4,    0),
		new DocaSetTable(6, DocaSet.PET_HUMCA, "DocaEntityHCa",  "DocaHCa",   DocaEntityHumanCat.class, Items.diamond, Items.cooked_chicken,  0.30000001192092896D, Items.porkchop,     Items.cooked_porkchop, Items.chicken,     Items.cooked_chicken,   0.6F,  1.1F,     1.1F,  1.6F,  0.7F,    2,     4,    0),
//		new DocaSetTable(7, DocaSet.PET_CHIKN, "DocaEntityChk",  "DocaChk",   DocaEntityChicken.class,  Item.diamond, Item.seeds,         0.30000001192092896D, Item.bread,       Item.appleRed,   Item.carrot,     Item.potato,        0.6F,  0.8F,     0.8F,  0.8F,  0.8F,    1,     2,    0),
		new DocaSetTable(7, DocaSet.PET_CHIKN, "DocaEntityChk",  "DocaChk",   DocaEntityChicken.class,  Items.diamond, Items.wheat_seeds,     0.30000001192092896D, Items.bread,        Items.apple,           Items.carrot,      Items.potato,           0.6F,  0.8F,     0.8F,  0.8F,  0.8F,    1,     2,    0),
//		new DocaSetTable(8, DocaSet.PET_ZOMBE, "DocaEntityZom",  "DocaZom",   DocaEntityZombie.class,   Item.diamond, Item.rottenFlesh,   0.30000001192092896D, Item.rottenFlesh, Item.beefRaw,    Item.porkRaw,    Item.chickenRaw,    0.6F,  1.8F,     1.8F,  1.8F,  0.9F,    5,     8,    2)
		new DocaSetTable(8, DocaSet.PET_ZOMBE, "DocaEntityZom",  "DocaZom",   DocaEntityZombie.class,   Items.diamond, Items.rotten_flesh,    0.30000001192092896D, Items.rotten_flesh, Items.beef,            Items.porkchop,    Items.chicken,          0.6F,  1.8F,     1.8F,  1.8F,  0.9F,    5,     8,    2)
	};

	public static void addEntityUserSet()
	{
		DocaReg.addEntityUserSet(0, DocaSet.PET_DOGGY, DocaSet.entity_use_Doggy,    DocaSet.entity_DoggyID,    DocaSet.mob_GrowAgeDoggy     );
		DocaReg.addEntityUserSet(1, DocaSet.PET_KITTY, DocaSet.entity_use_Kitty,    DocaSet.entity_KittyID,    DocaSet.mob_GrowAgeKitty     );
		DocaReg.addEntityUserSet(2, DocaSet.PET_SKELE, DocaSet.entity_use_Skeleton, DocaSet.entity_SkeletonID, DocaSet.mob_GrowAgeSkeleton  );
		DocaReg.addEntityUserSet(3, DocaSet.PET_BUNNY, DocaSet.entity_use_Bunny,    DocaSet.entity_BunnyID,    DocaSet.mob_GrowAgeBunny     );
		DocaReg.addEntityUserSet(4, DocaSet.PET_CACTU, DocaSet.entity_use_Cactuar,  DocaSet.entity_CactuarID,  DocaSet.mob_GrowAgeCactuar   );
		DocaReg.addEntityUserSet(5, DocaSet.PET_ENDER, DocaSet.entity_use_Enderman, DocaSet.entity_EndermanID, DocaSet.mob_GrowAgeEnderman  );
		DocaReg.addEntityUserSet(6, DocaSet.PET_HUMCA, DocaSet.entity_use_HumanCat, DocaSet.entity_HumanCatID, DocaSet.mob_GrowAgeHumanCat  );
		DocaReg.addEntityUserSet(7, DocaSet.PET_CHIKN, DocaSet.entity_use_Chicken,  0,                         DocaSet.mob_GrowAgeChichen   );
		DocaReg.addEntityUserSet(8, DocaSet.PET_ZOMBE, DocaSet.entity_use_Zombie,   0,                         DocaSet.mob_GrowAgeZombie    );
	}

	@SideOnly(Side.CLIENT)
	public static void addEntityRenderCoreSet()
	{
		DocaReg.addEntityRenderSet(0, DocaSet.PET_DOGGY, new DocaRenderDoggy(),      DocaSet.texture_SizeMaxDoggy,    DocaSet.textureDoggy,    DocaSet.textureDoggyTame,    DocaSet.textureDoggyCollar    );
		DocaReg.addEntityRenderSet(1, DocaSet.PET_KITTY, new DocaRenderKitty(),      DocaSet.texture_SizeMaxKitty,    DocaSet.textureKitty,    DocaSet.textureKittyTame,    DocaSet.textureKittyCollar    );
		DocaReg.addEntityRenderSet(2, DocaSet.PET_SKELE, new DocaRenderSkeleton(),   DocaSet.texture_SizeMaxSkeleton, DocaSet.textureSkeleton, DocaSet.textureSkeletonTame, DocaSet.textureSkeletonCollar );
		DocaReg.addEntityRenderSet(3, DocaSet.PET_BUNNY, new DocaRenderBunny(),      DocaSet.texture_SizeMaxBunny,    DocaSet.textureBunny,    DocaSet.textureBunnyTame,    DocaSet.textureBunnyCollar    );
		DocaReg.addEntityRenderSet(4, DocaSet.PET_CACTU, new DocaRenderCactuar(),    DocaSet.texture_SizeMaxCactuar,  DocaSet.textureCactuar,  DocaSet.textureCactuarTame,  DocaSet.textureCactuarCollar  );
		DocaReg.addEntityRenderSet(5, DocaSet.PET_ENDER, new DocaRenderEnderman(),   DocaSet.texture_SizeMaxEnderman, DocaSet.textureEnderman, DocaSet.textureEndermanTame, DocaSet.textureEndermanCollar );
		DocaReg.addEntityRenderSet(6, DocaSet.PET_HUMCA, new DocaRenderHumanCat(),   DocaSet.texture_SizeMaxHumanCat, DocaSet.textureHumanCat, DocaSet.textureHumanCatTame, DocaSet.textureHumanCatCollar );
		DocaReg.addEntityRenderSet(7, DocaSet.PET_CHIKN, new DocaRenderChicken(),    DocaSet.texture_SizeMaxChicken,  DocaSet.textureChicken,  DocaSet.textureChickenTame,  DocaSet.textureChickenCollar  );
		DocaReg.addEntityRenderSet(8, DocaSet.PET_ZOMBE, new DocaRenderZombie(),     DocaSet.texture_SizeMaxZombie,   DocaSet.textureZombie,   DocaSet.textureZombieTame,   DocaSet.textureZombieCollar   );
	}

	public static void makeConfigFile(DocaConigFileMaker config)
	{
		String tmp = "";
		/*-------------------------------------------------------------
			item
		--------------------------------------------------------------*/
//		DocaSet.item_SpawnDocaID =     config.get(DOCA_CATEGORY_ITEM, "item_SpawnDocaID", DocaSet.item_SpawnDocaID, StatCollector.translateToLocal("doca.configuration.setting.item_SpawnDocaID")).getInt();
//		DocaSet.item_CarrierBagID =    config.get(DOCA_CATEGORY_ITEM, "item_CarrierBagID", DocaSet.item_CarrierBagID, StatCollector.translateToLocal("doca.configuration.setting.item_CarrierBagID")).getInt();
//		DocaSet.item_RibbonID =        config.get(DOCA_CATEGORY_ITEM, "item_RibbonID", DocaSet.item_RibbonID, StatCollector.translateToLocal("doca.configuration.setting.item_RibbonID")).getInt();
		DocaSet.item_CarrierBagReuse = config.get(DOCA_CATEGORY_ITEM, "item_CarrierBagReuse", DocaSet.item_CarrierBagReuse, StatCollector.translateToLocal("doca.configuration.setting.item_CarrierBagReuse")).getBoolean(true);
		/*-------------------------------------------------------------
			block
		--------------------------------------------------------------*/
//		DocaSet.block_DocaChestID = config.get(DOCA_CATEGORY_BLOCK, "block_DocaChestID", DocaSet.block_DocaChestID, StatCollector.translateToLocal("doca.configuration.setting.block_DocaChestID")).getInt();
		/*-------------------------------------------------------------
			tab
		--------------------------------------------------------------*/
		DocaSet.tab_AddDocaCreativeTab = config.get(DOCA_CATEGORY_TAB, "tab_AddDocaCreativeTab", DocaSet.tab_AddDocaCreativeTab, StatCollector.translateToLocal("doca.configuration.setting.tab_AddDocaCreativeTab")).getBoolean(true);
		/*-------------------------------------------------------------
			entity_type
		--------------------------------------------------------------*/
		DocaSet.entity_type_OldType = config.get(DOCA_CATEGORY_ENTITY_TYPE, "entity_type_OldType", DocaSet.entity_type_OldType, StatCollector.translateToLocal("doca.configuration.setting.entity_type_OldType")).getBoolean(true);
		/*-------------------------------------------------------------
			entity_use
		--------------------------------------------------------------*/
		DocaSet.entity_use_Skeleton = config.get(DOCA_CATEGORY_ENTITY_USE, "entity_use_Skeleton", DocaSet.entity_use_Skeleton, StatCollector.translateToLocal("doca.configuration.setting.entity_use_Skeleton")).getBoolean(true);
		DocaSet.entity_use_Bunny =    config.get(DOCA_CATEGORY_ENTITY_USE, "entity_use_Bunny", DocaSet.entity_use_Bunny, StatCollector.translateToLocal("doca.configuration.setting.entity_use_Bunny")).getBoolean(true);
		DocaSet.entity_use_Cactuar =  config.get(DOCA_CATEGORY_ENTITY_USE, "entity_use_Cactuar", DocaSet.entity_use_Cactuar, StatCollector.translateToLocal("doca.configuration.setting.entity_use_Cactuar")).getBoolean(true);
		DocaSet.entity_use_Enderman = config.get(DOCA_CATEGORY_ENTITY_USE, "entity_use_Enderman", DocaSet.entity_use_Enderman, StatCollector.translateToLocal("doca.configuration.setting.entity_use_Enderman")).getBoolean(true);
		DocaSet.entity_use_HumanCat = config.get(DOCA_CATEGORY_ENTITY_USE, "entity_use_HumanCat", DocaSet.entity_use_HumanCat, StatCollector.translateToLocal("doca.configuration.setting.entity_use_HumanCat")).getBoolean(true);
		/*-------------------------------------------------------------
			entity
		--------------------------------------------------------------*/
		DocaSet.entity_DoggyID =    config.get(DOCA_CATEGORY_ENTITY, "entity_DoggyID", DocaSet.entity_DoggyID, StatCollector.translateToLocal("doca.configuration.setting.entity_DoggyID")).getInt();
		DocaSet.entity_KittyID =    config.get(DOCA_CATEGORY_ENTITY, "entity_KittyID", DocaSet.entity_KittyID, StatCollector.translateToLocal("doca.configuration.setting.entity_KittyID")).getInt();
		DocaSet.entity_SkeletonID = config.get(DOCA_CATEGORY_ENTITY, "entity_SkeletonID", DocaSet.entity_SkeletonID, StatCollector.translateToLocal("doca.configuration.setting.entity_SkeletonID")).getInt();
		DocaSet.entity_BunnyID =    config.get(DOCA_CATEGORY_ENTITY, "entity_BunnyID", DocaSet.entity_BunnyID,  StatCollector.translateToLocal("doca.configuration.setting.entity_BunnyID")).getInt();
		DocaSet.entity_CactuarID =  config.get(DOCA_CATEGORY_ENTITY, "entity_CactuarID", DocaSet.entity_CactuarID,  StatCollector.translateToLocal("doca.configuration.setting.entity_CactuarID")).getInt();
		DocaSet.entity_EndermanID = config.get(DOCA_CATEGORY_ENTITY, "entity_EndermanID", DocaSet.entity_EndermanID,  StatCollector.translateToLocal("doca.configuration.setting.entity_EndermanID")).getInt();
		DocaSet.entity_HumanCatID = config.get(DOCA_CATEGORY_ENTITY, "entity_HumanCatID", DocaSet.entity_HumanCatID,  StatCollector.translateToLocal("doca.configuration.setting.entity_HumanCatID")).getInt();
		/*-------------------------------------------------------------
			mob
		--------------------------------------------------------------*/
		DocaSet.mob_GrowAgeDoggy =    config.get(DOCA_CATEGORY_MOB, "mob_GrowAgeDoggy", DocaSet.mob_GrowAgeDoggy, StatCollector.translateToLocal("doca.configuration.setting.mob_GrowAgeDoggy")).getBoolean(true);
		DocaSet.mob_GrowAgeKitty =    config.get(DOCA_CATEGORY_MOB, "mob_GrowAgeKitty", DocaSet.mob_GrowAgeKitty, StatCollector.translateToLocal("doca.configuration.setting.mob_GrowAgeKitty")).getBoolean(true);
		DocaSet.mob_GrowAgeSkeleton = config.get(DOCA_CATEGORY_MOB, "mob_GrowAgeSkeleton", DocaSet.mob_GrowAgeSkeleton, StatCollector.translateToLocal("doca.configuration.setting.mob_GrowAgeSkeleton")).getBoolean(true);
		DocaSet.mob_GrowAgeBunny =    config.get(DOCA_CATEGORY_MOB, "mob_GrowAgeBunny", DocaSet.mob_GrowAgeBunny, StatCollector.translateToLocal("doca.configuration.setting.mob_GrowAgeBunny")).getBoolean(true);
		DocaSet.mob_GrowAgeCactuar =  config.get(DOCA_CATEGORY_MOB, "mob_GrowAgeCactuar", DocaSet.mob_GrowAgeCactuar, StatCollector.translateToLocal("doca.configuration.setting.mob_GrowAgeCactuar")).getBoolean(true);
		DocaSet.mob_GrowAgeEnderman = config.get(DOCA_CATEGORY_MOB, "mob_GrowAgeEnderman", DocaSet.mob_GrowAgeEnderman, StatCollector.translateToLocal("doca.configuration.setting.mob_GrowAgeEnderman")).getBoolean(true);
		DocaSet.mob_GrowAgeHumanCat = config.get(DOCA_CATEGORY_MOB, "mob_GrowAgeHumanCat", DocaSet.mob_GrowAgeHumanCat, StatCollector.translateToLocal("doca.configuration.setting.mob_GrowAgeHumanCat")).getBoolean(true);
		DocaSet.mob_GrowAgeChichen =  config.get(DOCA_CATEGORY_MOB, "mob_GrowAgeChichen", DocaSet.mob_GrowAgeChichen, StatCollector.translateToLocal("doca.configuration.setting.mob_GrowAgeChichen")).getBoolean(true);
		DocaSet.mob_GrowAgeZombie =   config.get(DOCA_CATEGORY_MOB, "mob_GrowAgeZombie", DocaSet.mob_GrowAgeZombie, StatCollector.translateToLocal("doca.configuration.setting.mob_GrowAgeZombie")).getBoolean(true);
		/*-------------------------------------------------------------
			show
		--------------------------------------------------------------*/
		DocaSet.show_LifeBar =               config.get(DOCA_CATEGORY_SHOW, "show_LifeBar", DocaSet.show_LifeBar, StatCollector.translateToLocal("doca.configuration.setting.show_LifeBar")).getBoolean(true);
		DocaSet.show_NameBar =               config.get(DOCA_CATEGORY_SHOW, "show_NameBar", DocaSet.show_NameBar, StatCollector.translateToLocal("doca.configuration.setting.show_NameBar")).getBoolean(true);
		DocaSet.show_NameColor =             config.get(DOCA_CATEGORY_SHOW, "show_NameColor", DocaSet.show_NameColor, StatCollector.translateToLocal("doca.configuration.setting.show_NameColor")).getInt();
		DocaSet.show_BarBackGroundStrength = config.get(DOCA_CATEGORY_SHOW, "show_BarBackGroundStrength", DocaSet.show_BarBackGroundStrength, StatCollector.translateToLocal("doca.configuration.setting.show_BarBackGroundStrength")).getInt();
		DocaSet.show_BarBackGroundStrength = DocaTools.mathMaxMin(255, 0, DocaSet.show_BarBackGroundStrength);
		DocaSet.show_BarBackGroundColor =    config.get(DOCA_CATEGORY_SHOW, "show_BarBackGroundColor", DocaSet.show_BarBackGroundColor, StatCollector.translateToLocal("doca.configuration.setting.show_BarBackGroundColor")).getInt();
		DocaSet.show_StatusIcon =            config.get(DOCA_CATEGORY_SHOW, "show_StatusIcon", DocaSet.show_StatusIcon, StatCollector.translateToLocal("doca.configuration.setting.show_StatusIcon")).getBoolean(true);
		DocaSet.show_StatusIconType =        config.get(DOCA_CATEGORY_SHOW, "show_StatusIconType", DocaSet.show_StatusIcon, StatCollector.translateToLocal("doca.configuration.setting.show_StatusIconType")).getInt();
		DocaSet.show_StatusIconType =        DocaTools.mathMaxMin(1, 0, DocaSet.show_StatusIconType);
		/*-------------------------------------------------------------
			func
		--------------------------------------------------------------*/
		DocaSet.func_particleON = config.get(DOCA_CATEGORY_FUNC, "func_particleON", DocaSet.func_particleON, StatCollector.translateToLocal("doca.configuration.setting.func_particleON")).getBoolean(true);
		DocaSet.func_particleStep = config.get(DOCA_CATEGORY_FUNC, "func_particleStep", DocaSet.func_particleStep, StatCollector.translateToLocal("doca.configuration.setting.func_particleStep")).getInt();
		DocaSet.func_particleStep = DocaTools.mathMaxMin(2, 0, DocaSet.func_particleStep);
		DocaSet.func_CreeperAlertON = config.get(DOCA_CATEGORY_FUNC, "func_CreeperAlertON", DocaSet.func_CreeperAlertON, StatCollector.translateToLocal("doca.configuration.setting.func_CreeperAlertON")).getBoolean(true);
		DocaSet.func_StopNolife = config.get(DOCA_CATEGORY_FUNC, "func_StopNolife", DocaSet.func_StopNolife, StatCollector.translateToLocal("doca.configuration.setting.func_StopNolife")).getBoolean(true);
		DocaSet.func_Speed = config.get(DOCA_CATEGORY_FUNC, "func_Speed", DocaSet.func_Speed, StatCollector.translateToLocal("doca.configuration.setting.func_Speed")).getBoolean(true);
		DocaSet.func_ItemPickUpON = config.get(DOCA_CATEGORY_FUNC, "func_ItemPickUpON", DocaSet.func_ItemPickUpON, StatCollector.translateToLocal("doca.configuration.setting.func_ItemPickUpON")).getBoolean(true);
		DocaSet.func_ItemPickUpWidely = config.get(DOCA_CATEGORY_FUNC, "func_ItemPickUpWidely", DocaSet.func_ItemPickUpWidely, StatCollector.translateToLocal("doca.configuration.setting.func_ItemPickUpWidely")).getInt();
		DocaSet.func_ItemPickUpWidely = DocaTools.mathMaxMin(30, 1, DocaSet.func_ItemPickUpWidely);
		DocaSet.func_ItemPickUpOffEquipItem = config.get(DOCA_CATEGORY_FUNC, "func_ItemPickUpOffEquipItem", DocaSet.func_ItemPickUpOffEquipItem, StatCollector.translateToLocal("doca.configuration.setting.func_ItemPickUpOffEquipItem")).getBoolean(true);
		DocaSet.func_ItemPickUpOffItemID = config.get(DOCA_CATEGORY_FUNC, "func_ItemPickUpOffItemID", DocaSet.func_ItemPickUpOffItemID, StatCollector.translateToLocal("doca.configuration.setting.func_ItemPickUpOffItemID")).getBoolean(true);
		tmpfunc_ItemPickUpOffItemIDs = DocaTools.chopStringDoca(config.get(DOCA_CATEGORY_FUNC, "func_ItemPickUpOffItemIDs", DocaSet.func_ItemPickUpOffItemIDs, StatCollector.translateToLocal("doca.configuration.setting.func_ItemPickUpOffItemIDs")).getString());
		DocaSet.func_MoveSquareToHomePoint = config.get(DOCA_CATEGORY_FUNC, "func_MoveSquareToHomePoint", DocaSet.func_MoveSquareToHomePoint, StatCollector.translateToLocal("doca.configuration.setting.func_MoveSquareToHomePoint")).getInt();
		DocaSet.func_MoveSquareToHomePoint = DocaTools.mathMaxMin(15, 5, DocaSet.func_MoveSquareToHomePoint);
		DocaSet.func_ItemPopUpON = config.get(DOCA_CATEGORY_FUNC, "func_ItemPopUpON", DocaSet.func_ItemPopUpON, StatCollector.translateToLocal("doca.configuration.setting.func_ItemPopUpON")).getBoolean(true);
		DocaSet.func_HowlingMoonDoggy = config.get(DOCA_CATEGORY_FUNC, "func_HowlingMoonDoggy", DocaSet.func_HowlingMoonDoggy, StatCollector.translateToLocal("doca.configuration.setting.func_HowlingMoonDoggy")).getBoolean(true);
		DocaSet.func_HealingBed = config.get(DOCA_CATEGORY_FUNC, "func_HealingBed", DocaSet.func_HealingBed, StatCollector.translateToLocal("doca.configuration.setting.func_HealingBed")).getBoolean(true);
		DocaSet.func_EmotionBalloonON = config.get(DOCA_CATEGORY_FUNC, "func_EmotionBalloonON", DocaSet.func_EmotionBalloonON, StatCollector.translateToLocal("doca.configuration.setting.func_EmotionBalloonON")).getBoolean(true);
		DocaSet.func_AccessPermissionOPS = config.get(DOCA_CATEGORY_FUNC, "func_AccessPermissionOPS", DocaSet.func_AccessPermissionOPS, StatCollector.translateToLocal("doca.configuration.setting.func_AccessPermissionOPS")).getBoolean(true);
		
		/*-------------------------------------------------------------
			attack
		--------------------------------------------------------------*/
		DocaSet.attack_AdditionalEffectON = config.get(DOCA_CATEGORY_ATTACK, "attack_AdditionalEffectON", DocaSet.attack_AdditionalEffectON, StatCollector.translateToLocal("doca.configuration.setting.attack_AdditionalEffectON")).getBoolean(true);
		DocaSet.attack_PetInvincibleOFF = config.get(DOCA_CATEGORY_ATTACK, "attack_PetInvincibleOFF", DocaSet.attack_PetInvincibleOFF, StatCollector.translateToLocal("doca.configuration.setting.attack_PetInvincibleOFF")).getBoolean(true);
		DocaSet.attack_PeacefulON = config.get(DOCA_CATEGORY_ATTACK, "attack_PeacefulON", DocaSet.attack_PeacefulON, StatCollector.translateToLocal("doca.configuration.setting.attack_PeacefulON")).getBoolean(true);
		DocaSet.attack_TameableON = config.get(DOCA_CATEGORY_ATTACK, "attack_TameableON", DocaSet.attack_TameableON, StatCollector.translateToLocal("doca.configuration.setting.attack_TameableON")).getBoolean(true);

		tmp = DocaTools.chopStringDoca(config.get(DOCA_CATEGORY_ATTACK, "attack_EntityForWoodSpirit", DocaSet.attack_EntityForWoodSpirit, StatCollector.translateToLocal("doca.configuration.setting.attack_EntityForWoodSpirit")).getString());
		if (!tmp.equalsIgnoreCase(""))
		{
			DocaSet.attack_EntityDoca = tmp.split(",", 0);
			if (DocaSet.Debug)
			{
				DocaTools.info("DocaSystemDebugMessage: attack_EntityForWoodSpirit.length -> " + attack_EntityDoca.length);
				for (int i = 0; i < attack_EntityDoca.length; i++)
				{
					DocaTools.info("DocaSystemDebugMessage: attack_EntityForWoodSpirit[" + i +"] -> " + attack_EntityDoca[i]);
				}
			}
		}

		tmp = DocaTools.chopStringDoca(config.get(DOCA_CATEGORY_ATTACK, "attack_FriendlyEntity", DocaSet.attack_FriendlyEntity, StatCollector.translateToLocal("doca.configuration.setting.attack_FriendlyEntity")).getString());
		if (!tmp.equalsIgnoreCase(""))
		{
			DocaSet.attack_FriendlyEntityDoca = tmp.split(",", 0);
			if (DocaSet.Debug)
			{
				DocaTools.info("DocaSystemDebugMessage: attack_FriendlyEntity.length -> " + attack_FriendlyEntityDoca.length);
				for (int i = 0; i < attack_FriendlyEntityDoca.length; i++)
				{
					DocaTools.info("DocaSystemDebugMessage: attack_FriendlyEntity[" + i +"] -> " + attack_FriendlyEntityDoca[i]);
				}
			}
		}
		/*-------------------------------------------------------------
			texture
		--------------------------------------------------------------*/
		DocaSet.texture_SizeMaxDoggy =    config.get(DOCA_CATEGORY_TEXTURE, "texture_SizeMaxDoggy", DocaSet.texture_SizeMaxDoggy , StatCollector.translateToLocal("doca.configuration.setting.texture_SizeMaxDoggy")).getInt();
		DocaSet.texture_SizeMaxDoggy =    DocaTools.mathMaxMin(255, 1, DocaSet.texture_SizeMaxDoggy);
		DocaSet.texture_SizeMaxKitty =    config.get(DOCA_CATEGORY_TEXTURE, "texture_SizeMaxKitty", DocaSet.texture_SizeMaxKitty, StatCollector.translateToLocal("doca.configuration.setting.texture_SizeMaxKitty")).getInt();
		DocaSet.texture_SizeMaxKitty =    DocaTools.mathMaxMin(255, 1, DocaSet.texture_SizeMaxKitty);
		DocaSet.texture_SizeMaxSkeleton = config.get(DOCA_CATEGORY_TEXTURE, "texture_SizeMaxSkeleton", DocaSet.texture_SizeMaxSkeleton, StatCollector.translateToLocal("doca.configuration.setting.texture_SizeMaxSkeleton")).getInt();
		DocaSet.texture_SizeMaxSkeleton = DocaTools.mathMaxMin(255, 1, DocaSet.texture_SizeMaxSkeleton);
		DocaSet.texture_SizeMaxBunny =    config.get(DOCA_CATEGORY_TEXTURE, "texture_SizeMaxBunny", DocaSet.texture_SizeMaxBunny, StatCollector.translateToLocal("doca.configuration.setting.texture_SizeMaxBunny")).getInt();
		DocaSet.texture_SizeMaxBunny =    DocaTools.mathMaxMin(255, 1, DocaSet.texture_SizeMaxBunny);
		DocaSet.texture_SizeMaxCactuar =  config.get(DOCA_CATEGORY_TEXTURE, "texture_SizeMaxCactuar", DocaSet.texture_SizeMaxCactuar, StatCollector.translateToLocal("doca.configuration.setting.texture_SizeMaxCactuar")).getInt();
		DocaSet.texture_SizeMaxCactuar =  DocaTools.mathMaxMin(255, 1, DocaSet.texture_SizeMaxCactuar);
		DocaSet.texture_SizeMaxEnderman = config.get(DOCA_CATEGORY_TEXTURE, "texture_SizeMaxEnderman", DocaSet.texture_SizeMaxEnderman, StatCollector.translateToLocal("doca.configuration.setting.texture_SizeMaxEnderman")).getInt();
		DocaSet.texture_SizeMaxEnderman = DocaTools.mathMaxMin(255, 1, DocaSet.texture_SizeMaxEnderman);
		DocaSet.texture_SizeMaxHumanCat = config.get(DOCA_CATEGORY_TEXTURE, "texture_SizeMaxHumanCat", DocaSet.texture_SizeMaxHumanCat, StatCollector.translateToLocal("doca.configuration.setting.texture_SizeMaxHumanCat")).getInt();
		DocaSet.texture_SizeMaxHumanCat = DocaTools.mathMaxMin(255, 1, DocaSet.texture_SizeMaxHumanCat);
		DocaSet.texture_SizeMaxChicken =  config.get(DOCA_CATEGORY_TEXTURE, "texture_SizeMaxChicken", DocaSet.texture_SizeMaxChicken, StatCollector.translateToLocal("doca.configuration.setting.texture_SizeMaxChicken")).getInt();
		DocaSet.texture_SizeMaxChicken =  DocaTools.mathMaxMin(255, 1, DocaSet.texture_SizeMaxChicken);
		DocaSet.texture_SizeMaxZombie =   config.get(DOCA_CATEGORY_TEXTURE, "texture_SizeMaxZombie", DocaSet.texture_SizeMaxZombie, StatCollector.translateToLocal("doca.configuration.setting.texture_SizeMaxZombie")).getInt();
		DocaSet.texture_SizeMaxZombie =   DocaTools.mathMaxMin(255, 1, DocaSet.texture_SizeMaxZombie);
		String tmp_texture_ItemEgg =         config.get(DOCA_CATEGORY_TEXTURE, "texture_ItemEgg", "", StatCollector.translateToLocal("doca.configuration.setting.texture_ItemEgg")).getString();
		String tmp_texture_ItemCarrierBag =  config.get(DOCA_CATEGORY_TEXTURE, "texture_ItemCarrierBag", "", StatCollector.translateToLocal("doca.configuration.setting.texture_ItemCarrierBag")).getString();
		String tmp_texture_ItemRibbon =      config.get(DOCA_CATEGORY_TEXTURE, "texture_ItemRibbon", "", StatCollector.translateToLocal("doca.configuration.setting.texture_ItemRibbon")).getString();
		String tmp_texture_TileEntityChest = config.get(DOCA_CATEGORY_TEXTURE, "texture_TileEntityChest", "", StatCollector.translateToLocal("doca.configuration.setting.texture_TileEntityChest")).getString();
		if (!tmp_texture_ItemEgg.equalsIgnoreCase(""))
		{
			DocaSet.texture_ItemEgg = imageFoldaDoca + tmp_texture_ItemEgg;
		}
		if (!tmp_texture_ItemCarrierBag.equalsIgnoreCase(""))
		{
			DocaSet.texture_ItemCarrierBag = imageFoldaDoca + tmp_texture_ItemCarrierBag;
		}
		if (!tmp_texture_ItemRibbon.equalsIgnoreCase(""))
		{
			DocaSet.texture_ItemRibbon = imageFoldaDoca + tmp_texture_ItemRibbon;
		}
		if (!tmp_texture_TileEntityChest.equalsIgnoreCase(""))
		{
			DocaSet.texture_TileEntityChest = DocaSet.texture_TileEntity_Path + tmp_texture_TileEntityChest + ".png";
			DocaSet.texture_TileEntityChestChristmas = DocaSet.texture_TileEntityChest;
		}

		tmp = DocaTools.chopStringDoca(config.get(DOCA_CATEGORY_CONVERT, "convert_NameString", DocaSet.convert_NameString, StatCollector.translateToLocal("doca.configuration.setting.convert_NameString")).getString());
		if (!tmp.equalsIgnoreCase(""))
		{
			DocaSet.convert_NameStringDoca = tmp.split(",", 0);
			if (DocaSet.Debug)
			{
				DocaTools.info("DocaSystemDebugMessage: convert_NameString.length -> " + convert_NameStringDoca.length);
				for (int i = 0; i < attack_FriendlyEntityDoca.length; i++)
				{
					DocaTools.info("DocaSystemDebugMessage: convert_NameString[" + i +"] -> " + convert_NameStringDoca[i]);
				}
			}
		}

		if (!DocaSet.entity_type_OldType)
		{
			DocaSet.entity_use_Doggy = true;
			DocaSet.entity_use_Kitty = true;
			DocaSet.entity_use_Skeleton = true;
			DocaSet.entity_use_Bunny = true;
			DocaSet.entity_use_Cactuar = true;
			DocaSet.entity_use_Enderman = true;
			DocaSet.entity_use_HumanCat = true;
			DocaSet.entity_use_Zombie = true;
			DocaSet.entity_use_Chicken = true;
		}
	}
	
	public static void afterConfigFile()
	{
		if (!tmpfunc_ItemPickUpOffItemIDs.equalsIgnoreCase(""))
		{
			String[] tmpString = tmpfunc_ItemPickUpOffItemIDs.split(",", 0);

			for (int i = 0; i < tmpString.length; i++)
			{
			    if (DocaTools.checkInt(tmpString[i]))
			    {
			    	func_ItemPickUpOffItemIDsDoca.add(Integer.parseInt(tmpString[i]));
			    }
			    else
			    {
			    	DocaTools.warning("a construction error to func_ItemPickUpOffItemIDsDoca. skip list add to [%s]", tmpString[i]);
			    }
			}
			
			if (DocaSet.Debug)
			{
				DocaTools.info("DocaSystemDebugMessage: func_ItemPickUpOffItemIDsDoca.length -> " + func_ItemPickUpOffItemIDsDoca.size());
				for (int i = 0; i < func_ItemPickUpOffItemIDsDoca.size(); i++)
				{
					DocaTools.info("DocaSystemDebugMessage: func_ItemPickUpOffItemIDsDoca[" + i +"] -> " + func_ItemPickUpOffItemIDsDoca.get(i));
				}
			}
		}
		
	}
}
