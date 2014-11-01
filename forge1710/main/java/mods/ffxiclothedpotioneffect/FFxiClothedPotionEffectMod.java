package mods.ffxiclothedpotioneffect;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemPotion;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

@Mod(modid = FFxiClothedPotionEffectMod.MODID, name = FFxiClothedPotionEffectMod.MODNAME, version = FFxiClothedPotionEffectMod.MODVERSION)
public class FFxiClothedPotionEffectMod
{
	public static final String MODID = "FFxiClothedPotionEffectMod";
	public static final String MODNAME = "FFxiClothedPotionEffectMod";
	public static final String MCVERSION = "1.7.2";
	public static final String MODVERSION = MCVERSION + "_" + "1.0.1";

	@Mod.EventHandler
	public void PostInit(FMLPostInitializationEvent event)
	{
		MinecraftForge.EVENT_BUS.register(this);
	}

	@SubscribeEvent
	public void onLivingUpdate(LivingUpdateEvent event)
	{
		if (event.entityLiving != null && event.entityLiving instanceof EntityPlayer && !event.entityLiving.worldObj.isRemote)
		{
			EntityPlayer player = (EntityPlayer)event.entityLiving;

			Map<Integer,PotionEffect> map = hasPotion(player.inventory);

			if (map != null && !map.isEmpty())
			{
				for (Entry<Integer, PotionEffect> key : map.entrySet())
				{
					if (!player.isPotionActive(key.getKey()))
					{
						player.addPotionEffect(key.getValue());
					}
				}
			}
		}
	}

	private Map<Integer,PotionEffect> hasPotion(InventoryPlayer inventory)
	{
		Map<Integer,PotionEffect> map = new HashMap<Integer,PotionEffect>(); 

		for (int i = 0; i < 10; i++)
		{
			if (inventory.mainInventory[i] != null && inventory.mainInventory[i].getItem() != null
					&& inventory.mainInventory[i].getItem() instanceof ItemPotion)
			{
				ItemPotion tmpItemPotion = (ItemPotion)inventory.mainInventory[i].getItem();
				List tmpList = tmpItemPotion.getEffects(inventory.mainInventory[i].getItemDamage());

				if (tmpList != null && !tmpList.isEmpty())
				{
					Iterator iterator = tmpList.iterator();
					while (iterator.hasNext())
					{
						PotionEffect tmpPotionEffect = (PotionEffect)iterator.next();
						if (tmpPotionEffect != null && !map.containsKey(tmpPotionEffect.getPotionID()))
						{
							map.put(tmpPotionEffect.getPotionID(), new PotionEffect(tmpPotionEffect));
						}
					}
				}
			}
		}
		return map;
	}
}
