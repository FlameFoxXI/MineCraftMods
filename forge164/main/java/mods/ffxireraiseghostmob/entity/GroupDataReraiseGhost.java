package mods.ffxireraiseghostmob.entity;
import net.minecraft.entity.EntityLivingData;

public class GroupDataReraiseGhost implements EntityLivingData
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