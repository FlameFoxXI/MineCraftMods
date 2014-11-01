package mods.doca.entity.func;
import java.util.List;
import java.util.Random;

import net.minecraft.entity.*;
import net.minecraft.entity.item.*;
import net.minecraft.entity.monster.*;
import net.minecraft.item.*;
import mods.doca.core.*;
import mods.doca.entity.*;

public class DocaFuncCreeperAlert
{
	private Random rand = new Random();

	public DocaFuncCreeperAlert()
	{
	}

	public boolean getCreeperAlert(DocaEntityBase theBase, String sound, float volume)
	{
		if (theBase.getLife() > 1.0F && DocaSet.func_CreeperAlertON && theBase.isTamed())
		{
			List var1 = theBase.worldObj.getEntitiesWithinAABB(EntityCreeper.class, theBase.boundingBox.expand(16.0D, 4.0D, 16.0D));

			if (!var1.isEmpty())
			{
				theBase.playSound(sound, volume - 0.1F, (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
				return true;
			}
		}
		return false;
	}
}