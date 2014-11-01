package mods.ffxicallanimalbell;


import java.io.File;
import java.util.Iterator;
import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.ai.EntityAITasks;
import net.minecraft.entity.ai.EntityAITasks.EntityAITaskEntry;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@Mod(modid = FFxiCallAnimalBellMod.MODID, name = FFxiCallAnimalBellMod.MODNAME, version = FFxiCallAnimalBellMod.MODVERSION)
public class FFxiCallAnimalBellMod
{
	//setting
	public static final String MODID = "FFxiCallAnimalBellMod";
	public static final String MODNAME = "FFxiCallAnimalBellMod";
	public static final String MCVERSION = "1.7.2";
	public static final String MODVERSION = MCVERSION + "_" + "1.0.3";

	//inc
	public static Item itemAnimalBell;
	public static String[] animalStringArray = new String[0];
	public static String imageFolda = "ffxicallanimalbell:";
	
	//config
	public static int animal_dist = 40;
	public static int animal_delayTemptTime = 0;
	public static String animal_List= "Pig,Sheep,Cow,Chicken,Wolf,Ozelot,EntityHorse";

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		this.configrationfile(event.getSuggestedConfigurationFile());
		
		itemAnimalBell = (new ItemCallAnimalBell()).setUnlocalizedName("bellAnimal").setTextureName(imageFolda + "animal_bell");
		GameRegistry.registerItem(itemAnimalBell, "bellAnimal");
	}

	@Mod.EventHandler
	public void PostInit(FMLPostInitializationEvent event)
	{
		addItemRecipe();
		MinecraftForge.EVENT_BUS.register(this);
	}

	@SubscribeEvent
	public void onLivingUpdate(LivingUpdateEvent event)
	{
		if (event.entityLiving != null && event.entityLiving.ticksExisted % 10 == 0 && event.entityLiving instanceof EntityPlayer && !event.entityLiving.worldObj.isRemote)
		{
			EntityPlayer player = (EntityPlayer)event.entityLiving;

			if (player.getCurrentEquippedItem() != null && player.getCurrentEquippedItem().getItem() != null && player.getCurrentEquippedItem().getItem() == itemAnimalBell)
			{
				repEntityAIAnimalBell(player);
			}
		}
	}

	private void repEntityAIAnimalBell(EntityPlayer player)
	{
		List list  = player.worldObj.getEntitiesWithinAABB(EntityAnimal.class, player.boundingBox.expand((double)animal_dist, 5.0D, (double)animal_dist));

		if (list != null && !list.isEmpty())
		{
			Iterator iterator = list.iterator();

			while (iterator.hasNext())
			{
				EntityAnimal entity = (EntityAnimal)iterator.next();

				if (!checkAnimalType(entity))
				{
					continue;
				}

				List<EntityAITasks.EntityAITaskEntry> tasks = entity.tasks.taskEntries;

				 tasks.size();

				boolean taskFound = false;
				for (EntityAITaskEntry task: tasks)
				{
					if (task.priority == 2 && (task.action instanceof EntityAIAnimalBell))
					{
						taskFound = true;
						break;
					}
				}
				if (!taskFound)
				{
					entity.tasks.addTask(2, new EntityAIAnimalBell(entity, 2.0D, itemAnimalBell, false, (double)animal_dist, animal_delayTemptTime));
	            }
			}
		}
	}

	private boolean checkAnimalType(EntityAnimal entity)
	{
		if (animalStringArray != null && animalStringArray.length != 0)
		{
			for (String animal : animalStringArray)
			{
				if (EntityList.getEntityString(entity) != null && !EntityList.getEntityString(entity).equalsIgnoreCase(""))
				{
					if (animal.equalsIgnoreCase(EntityList.getEntityString(entity)))
					{
						return true;
					}
				}
			}
		}
		return false;
	}

	private void configrationfile(File file)
	{
		Configuration config = new Configuration(file);
		try
		{
			String tmpString = chopString(config.get("animal", "animal_List", animal_List, "allowed animal list.").getString());
			if (!tmpString.equalsIgnoreCase(""))
			{
				animalStringArray = tmpString.split(",", 0);
			}
			animal_dist = calcLimit(config.get("animal", "animal_dist", animal_dist, "animal find player distance. min 10 to max 120. default 25.").getInt(), 10, 40);
			animal_delayTemptTime = calcLimit(config.get("animal", "animal_delayTemptTime", animal_delayTemptTime, "animal delay tempt time. min 0 to max 120. default 0.").getInt(), 0, 120);
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
		GameRegistry.addRecipe(new ItemStack(itemAnimalBell, 1),
				new Object[] { " F ", " S ", " G ", 'F', Items.feather, 'S', Items.stick, 'G', Items.gold_ingot});
	}
	
	private int calcLimit(int value, int min, int max)
	{
		value = Math.max(Math.min(value, max), min);
		return value;
	}

	public static String chopString(String data)
	{
		data = data.trim();
		data = data.replaceAll(" ", "");
		return data;
	}
	
	@SideOnly(Side.CLIENT)
	public static void soundRightClick(String name, World world, Entity entity, int volume, int pich)
	{
		world.playSoundAtEntity(entity, imageFolda + name, volume, pich);
	}
}