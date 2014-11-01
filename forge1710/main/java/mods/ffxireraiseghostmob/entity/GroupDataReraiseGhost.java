package mods.ffxireraiseghostmob.entity;
import net.minecraft.entity.IEntityLivingData;

public class GroupDataReraiseGhost implements IEntityLivingData
{
	public boolean child;

	private GroupDataReraiseGhost(boolean par2)
	{
		this.child = false;
		this.child = par2;
	}

	public GroupDataReraiseGhost(boolean par2, Object par4EntityZombieINNER1)
	{
		this(par2);
	}
}