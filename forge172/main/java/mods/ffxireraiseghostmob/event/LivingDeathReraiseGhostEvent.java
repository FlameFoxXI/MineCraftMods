package mods.ffxireraiseghostmob.event;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.registry.FMLControlledNamespacedRegistry;
import cpw.mods.fml.common.registry.GameData;
import mods.ffxireraiseghostmob.core.FFxiSet;
import mods.ffxireraiseghostmob.core.FFxiTool;
import mods.ffxireraiseghostmob.entity.EntityReraiseGhost;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ObjectIntIdentityMap;
import net.minecraftforge.event.entity.living.LivingDeathEvent;

public class LivingDeathReraiseGhostEvent
{
	protected Random rand = new Random();

	public LivingDeathReraiseGhostEvent()
	{

	}

	@SubscribeEvent
	public void onLivingDeathEvent(LivingDeathEvent event)
	{
		if (!event.entityLiving.worldObj.isRemote && event.source.getEntity() != null
		&&(event.source.getEntity() instanceof EntityPlayer || event.source.getEntity() instanceof EntityReraiseGhost))
		{
			if (event.entityLiving instanceof EntityAnimal)
			{
				if (this.getSpownChance(event.source.getEntity()) == 0 && isSpawnMobList(event.entityLiving) != null)
				{
					spawnReraiseGhostMob(event.entityLiving, event.source.getEntity());
				}
			}
		}
	}

	private Entity isSpawnMobList(Entity entity)
	{
		if (entity instanceof EntityTameable && ((EntityTameable)entity).isTamed())
		{
			return null;
		}
		
		if (EntityList.classToStringMapping.containsKey(entity.getClass())
		&& FFxiSet.mobsList.contains(EntityList.classToStringMapping.get(entity.getClass())))
		{
			return entity;
		}
		return null;
	}
	
	public void spawnReraiseGhostMob(EntityLivingBase entity, Entity target)
	{
		EntityReraiseGhost tmpEntityReraiseGhost = new EntityReraiseGhost(entity.worldObj);
		tmpEntityReraiseGhost.setReraiseGhostName((String)EntityList.classToStringMapping.get(entity.getClass()));
		tmpEntityReraiseGhost.copyLocationAndAnglesFrom(entity);
		tmpEntityReraiseGhost.onSpawnWithEgg((IEntityLivingData)null);

		if (target instanceof EntityPlayer)
		{
			tmpEntityReraiseGhost.setTarget(target);
		}

		if (entity instanceof EntityAgeable && ((EntityAgeable)entity).isChild())
		{
			tmpEntityReraiseGhost.setChild(true);
		}

		entity.worldObj.spawnEntityInWorld(tmpEntityReraiseGhost);
	}
	
	private int getSpownChance(Entity target)
	{
		if (target instanceof EntityReraiseGhost)
		{
			return 0;
		}
		return this.rand.nextInt(FFxiSet.func_probSpawn + 1);
	}
}