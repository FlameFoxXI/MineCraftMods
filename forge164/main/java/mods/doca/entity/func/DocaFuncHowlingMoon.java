package mods.doca.entity.func;
import java.util.Random;

import cpw.mods.fml.relauncher.*;

import net.minecraft.item.*;
import mods.doca.core.*;
import mods.doca.entity.*;

public class DocaFuncHowlingMoon
{
	private Random rand = new Random();
	private int bufLocaltime;
	private boolean howlingDoggy;
	private int howlingTime;

	public DocaFuncHowlingMoon()
	{
		this.bufLocaltime = 24;
		this.howlingDoggy = false;
		this.howlingTime = 0;
	}

	public boolean onLivingUpdate(DocaEntityBase theBase, String sound, float volume)
	{
		boolean tmpReturn = false;
		if (theBase.getLife() > 1.0F && DocaSet.func_HowlingMoonDoggy && theBase.isTamed())
		{
			int localTime = makeLocalTime(theBase.worldObj.getWorldTime());

			if (this.bufLocaltime == 23 && localTime == 0)
			{
				this.howlingDoggy = true;
				this.howlingTime = 90;

				if (!theBase.worldObj.isRemote && !theBase.isSleeping())
				{
					theBase.playSound(sound, volume, (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
				}
				tmpReturn = true;
			}

			this.bufLocaltime = localTime;

			if (0 < this.howlingTime)
			{
				this.howlingTime--;
			}
			else
			{
				this.howlingDoggy = false;
			}
		}
		return tmpReturn;
	}

	@SideOnly(Side.CLIENT)
	public boolean isHowling()
	{
		return this.howlingDoggy;
	}

	private int makeLocalTime(Long par1Long)
	{
		int tmpInt = (int)(((par1Long / 1000L) + 24L) % 24L);
		return tmpInt >= 18 ? tmpInt - 18 : tmpInt + 6;
	}
}