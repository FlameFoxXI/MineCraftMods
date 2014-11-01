package mods.ffxireraiseghostmob.core;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import mods.ffxireraiseghostmob.FFxiReraiseGhostMobMod;
import mods.ffxireraiseghostmob.entity.EntityReraiseGhost;
import mods.ffxireraiseghostmob.event.LivingDeathReraiseGhostEvent;
import net.minecraft.item.Item;
import net.minecraft.block.Block;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class FFxiSet
{
	public static final String MODID = "FFxiReraiseGhostMobMod";
	public static final String MODNAME = "FFxiReraiseGhostMobMod";
	public static final String MCVERSION = "1.6.4";
	public static final String MODVERSION = MCVERSION + "_" + "1.0.0";
	public static final String MODCLIENT = "mods.ffxireraiseghostmob.proxy.FFxiClientProxy";
	public static final String MODSERVER = "mods.ffxireraiseghostmob.proxy.FFxiCommonProxy";
	
	public static final Map<String, String> mobsTexture = new HashMap();
	public static final ArrayList<String> mobsList = new ArrayList();
	public static ArrayList<ArrayList<Item>> equipmentList = new ArrayList();

	public static final String modsFolda = "ffxireraiseghostmob:";
	
	static
	{
		addReraiseGhostMobMap("Pig", modsFolda + "textures/entity/ghost_pig.png");
		addReraiseGhostMobMap("Sheep", modsFolda + "textures/entity/ghost_sheep.png");
		addReraiseGhostMobMap("Cow", modsFolda + "textures/entity/ghost_cow.png");
		addReraiseGhostMobMap("Chicken", modsFolda + "textures/entity/ghost_chicken.png");
		addReraiseGhostMobMap("Wolf", modsFolda + "textures/entity/ghost_wolf.png");
		addReraiseGhostMobMap("MushroomCow", modsFolda + "textures/entity/ghost_cow.png");
		addReraiseGhostMobMap("Ozelot", modsFolda + "textures/entity/ghost_ozelot.png");
	}

	public static int mob_maxHealth = 40;
	public static int mob_followRange = 40;
	public static int mob_movementSpeed = 3;
	public static int mob_attackDamage = 6;
	public static int mob_knockbackResistance = 0;
	
	public static boolean func_canDespawn = true;
	public static boolean func_particle = true;
	public static boolean func_fallDamage = false;
	public static boolean func_fireDamage = false;
	public static int func_probSpawn = 10;

	public static void configrationfile(File file)
	{
		Configuration config = new Configuration(file);
		try
		{
			mob_maxHealth = config.get("mob", "mob_maxHealth", mob_maxHealth).getInt();
			mob_followRange = config.get("mob", "mob_followRange", mob_followRange).getInt();
			mob_movementSpeed = config.get("mob", "mob_movementSpeed", mob_movementSpeed).getInt();
			mob_attackDamage = config.get("mob", "mob_attackDamage", mob_attackDamage).getInt();
			mob_knockbackResistance = config.get("mob", "mob_knockbackResistance", mob_knockbackResistance).getInt();

			func_canDespawn = config.get("func", "func_canDespawn", func_canDespawn).getBoolean(false);
			func_particle = config.get("func", "func_particle", func_particle).getBoolean(true);
			func_fallDamage = config.get("func", "func_fallDamage", func_fallDamage).getBoolean(false);
			func_fireDamage = config.get("func", "func_fireDamage", func_fireDamage).getBoolean(false);
			func_probSpawn = config.get("func", "func_probSpawn", func_probSpawn).getInt();
		}
		catch (Exception e)
		{
			FFxiTool.severe("a problem loading it's configuration (%s)", MODID);
		}
		finally
		{
			config.save();
		}
	}
	
	public static void registerModItems() { }

	public static void registerModBlocks() { }

	public static void registerModEntitys()
	{
		EntityRegistry.registerModEntity(EntityReraiseGhost.class, "EntityReraiseGhost", 0, FFxiReraiseGhostMobMod.instance, 80, 3, true);
	}

	public static void registerModTileEntitys() { }

	public static void registerModHandlers()
	{
		MinecraftForge.EVENT_BUS.register(new LivingDeathReraiseGhostEvent());
	}

	public static void afterInitForMod()
	{
		equipmentList = FFxiTool.getItemList();
	}

	@SideOnly(Side.CLIENT)
	public static void registerModClientHandlers() { }
	
	private static void addReraiseGhostMobMap(String name, String texture)
	{
		mobsTexture.put(name, texture);
		mobsList.add(name);
	}
}