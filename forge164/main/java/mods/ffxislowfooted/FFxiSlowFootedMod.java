package mods.ffxislowfooted;

import java.io.File;
import java.math.BigDecimal;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.client.event.RenderPlayerEvent.SetArmorModel;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;

@Mod(modid = FFxiSlowFootedMod.MODID, name = FFxiSlowFootedMod.MODNAME, version = FFxiSlowFootedMod.MODVERSION)
public class FFxiSlowFootedMod
{
	public final static String MODID = "FFxiSlowFootedMod";
	public final static String MODNAME = "FFxiSlowFootedMod";
	public final static String MCVERSION = "1.6.4";
	public final static String MODVERSION = MCVERSION +"_"+ "1.0.0";

	public static int slowfootedItemid = 5350;
	public static Item slowfootedItemHelMet;
	public static Item slowfootedItemChest;
	public static Item slowfootedItemLegs;
	public static Item slowfootedItemBoots;

	public static String textureFolder = "ffxislowfooted:";
	public static String texturesSlowFeetedLayer_1 = textureFolder + "textures/models/armor/slowfeeted_layer_1.png";
	public static String texturesSlowFeetedLayer_2 = textureFolder + "textures/models/armor/slowfeeted_layer_2.png";

	private static int renderId;

	private static int fallSpeed = 5;

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		configrationfile(event.getSuggestedConfigurationFile());

		if (FMLCommonHandler.instance().getSide() == Side.CLIENT)
		{
			renderId = RenderingRegistry.addNewArmourRendererPrefix("slowfeeted");
		}

		slowfootedItemHelMet = (new ItemSlowFooted(slowfootedItemid+0, EnumArmorMaterial.GOLD, renderId, 0)).setUnlocalizedName("helmetSlowFooted").setTextureName(textureFolder + "slowfeeted_helmet").setCreativeTab(CreativeTabs.tabCombat);
		slowfootedItemChest = (new ItemSlowFooted(slowfootedItemid+1, EnumArmorMaterial.GOLD, renderId, 1)).setUnlocalizedName("chestplateSlowFooted").setTextureName(textureFolder + "slowfeeted_chestplate").setCreativeTab(CreativeTabs.tabCombat);
		slowfootedItemLegs = (new ItemSlowFooted(slowfootedItemid+2, EnumArmorMaterial.GOLD, renderId, 2)).setUnlocalizedName("leggingsSlowFooted").setTextureName(textureFolder + "slowfeeted_leggings").setCreativeTab(CreativeTabs.tabCombat);
		slowfootedItemBoots = (new ItemSlowFooted(slowfootedItemid+3, EnumArmorMaterial.GOLD, renderId, 3)).setUnlocalizedName("bootsSlowFooted").setTextureName(textureFolder + "slowfeeted_boots").setCreativeTab(CreativeTabs.tabCombat);
		GameRegistry.registerItem(slowfootedItemHelMet, "helmetSlowFooted");
		GameRegistry.registerItem(slowfootedItemChest, "chestplateSlowFooted");
		GameRegistry.registerItem(slowfootedItemLegs, "leggingsSlowFooted");
		GameRegistry.registerItem(slowfootedItemBoots, "bootsSlowFooted");
	}

	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
		addslowfootedItemRecipe();
		MinecraftForge.EVENT_BUS.register(this);
	}

	@ForgeSubscribe
	public void onLivingUpdate(LivingUpdateEvent event)
	{
		if (event.entityLiving instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer)event.entityLiving;

			if (isSlowfootedBoots(player))
			{
				if (!player.onGround && player.motionY < 0.0D)
				{
					player.motionY *= calcSpeed(fallSpeed);
				}
			}
		}
	}

	@ForgeSubscribe
	public void onLivingFall(LivingFallEvent event)
	{
		if (event.entityLiving instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer)event.entityLiving;

			if (isSlowfootedBoots(player))
			{
				event.distance = 0;
			}
		}
	}

	private boolean isSlowfootedBoots(EntityPlayer player)
	{
		if ((player.inventory.armorInventory[0] != null && player.inventory.armorInventory[0].getItem() != null && player.inventory.armorInventory[0].getItem() == this.slowfootedItemBoots)
		|| (player.inventory.armorInventory[1] != null && player.inventory.armorInventory[1].getItem() != null && player.inventory.armorInventory[1].getItem() == this.slowfootedItemLegs)
		|| (player.inventory.armorInventory[2] != null && player.inventory.armorInventory[2].getItem() != null && player.inventory.armorInventory[2].getItem() == this.slowfootedItemChest)
		|| (player.inventory.armorInventory[3] != null && player.inventory.armorInventory[3].getItem() != null && player.inventory.armorInventory[3].getItem() == this.slowfootedItemHelMet))
		{
			return true;
		}
		return false;
	}

	private void configrationfile(File file)
	{
		Configuration config = new Configuration(file);
		try
		{
			slowfootedItemid = config.getItem("slowfootedItemid", slowfootedItemid, "set item id. set item id start to use 4 ids").getInt();
			fallSpeed = config.get("setting", "fallSpeed", fallSpeed, "a fall Speed. 1-9. default:5").getInt();
		}
		catch (Exception e)
		{
			FMLLog.warning("DogCatPlus has a problem loading it's configuration");
		}
		finally
		{
			config.save();
		}
	}

	private double calcSpeed(int speed)
	{
		int tmp = Math.max(Math.min(speed, 10), 1);
		BigDecimal bi = new BigDecimal(String.valueOf((10-(tmp-1)) *0.1D));
		return bi.setScale(2, BigDecimal.ROUND_DOWN).doubleValue();
	}
	
	private void addslowfootedItemRecipe()
	{
		GameRegistry.addRecipe(new ItemStack(slowfootedItemBoots, 1),
				new Object[] { "R R", "G G", "F F", 'R', Item.leather, 'G', Item.ingotGold, 'F', Item.feather });
	}
}