package mods.doca.entity.func;
import java.util.Random;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import mods.doca.core.*;
import mods.doca.entity.*;



public class DocaFuncParticle
{
	private Random rand = new Random();
	private int baseParticleTimer;

	void DocaFuncPpoUp()
	{
		this.baseParticleTimer = 0;
	}

	public boolean onLivingUpdate(DocaEntityBase theBase)
	{
		boolean tmp = false;
		if (DocaSet.func_particleON && theBase.isTamed())
		{
			if (this.baseParticleTimer == 0)
			{
				tmp = this.funcParticle(theBase);
			}
			else
			{
				this.baseParticleTimer--;
			}
		}
		return tmp;
	}

	public boolean funcParticle(DocaEntityBase theBase)
	{
		if (DocaSet.func_particleON && theBase.isTamed())
		{
			int tmpStep = DocaSet.func_particleStep + 1;

			if (theBase.isSitting())
			{
				this.baseParticleTimer = (80 / tmpStep) + this.rand.nextInt(10);
				theBase.setParticle("enchantmenttable");
			}
			else
			{
				switch (theBase.getMode())
				{
				case 1:
					this.baseParticleTimer = (70 / tmpStep) + this.rand.nextInt(10);
					theBase.setParticle("flame");
					break;

				case 2:
					this.baseParticleTimer = (50 / tmpStep) + this.rand.nextInt(10);
					theBase.setParticle("splash");
					break;

				case 3:
					this.baseParticleTimer = (80 / tmpStep) + this.rand.nextInt(10);
					theBase.setParticle("portal");
					break;

				default:
					break;
				}
				return true;
			}
		}
		return false;
	}
}