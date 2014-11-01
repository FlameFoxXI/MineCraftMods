package mods.doca.entity.ai;

import net.minecraft.entity.*;

import java.util.*;

public class DocaEntityAIAttackMobTargetSortery implements Comparator
{
	private Entity theEntity;
	final DocaEntityAIAttackMob parent;

	public DocaEntityAIAttackMobTargetSortery(DocaEntityAIAttackMob par1DocaEntityAIAttackMob, Entity par2Entity)
	{
		this.parent = par1DocaEntityAIAttackMob;
		this.theEntity = par2Entity;
	}

	public int compareDistanceSq(Entity par1Entity, Entity par2Entity)
	{
		double var3 = this.theEntity.getDistanceSqToEntity(par1Entity);
		double var5 = this.theEntity.getDistanceSqToEntity(par2Entity);
		return var3 < var5 ? -1 : (var3 > var5 ? 1 : 0);
	}

	@Override
	public int compare(Object par1Obj, Object par2Obj)
	{
		return this.compareDistanceSq((Entity)par1Obj, (Entity)par2Obj);
	}
}
