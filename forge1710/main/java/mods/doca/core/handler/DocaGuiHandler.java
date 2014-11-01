package mods.doca.core.handler;

import mods.doca.client.gui.DocaGuiScreen;
import mods.doca.client.gui.inventory.DocaGuiContainer;
import mods.doca.core.DocaSet;
import mods.doca.entity.DocaEntityBase;
import mods.doca.inventory.DocaContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;

public class DocaGuiHandler implements IGuiHandler
{

	@Override
	public Object getClientGuiElement(int par1, EntityPlayer par2Player, World par3World, int par4, int par5, int par6)
	{
		if (par1 == DocaSet.containerContainerID)
		{
			DocaEntityBase var1 = (DocaEntityBase)par3World.getEntityByID(par4);

			if (var1 != null && var1 instanceof DocaEntityBase)
			{
				return new DocaGuiContainer(par2Player, var1);
			}
		}
		else if (par1 == DocaSet.containerSettingID)
		{
			DocaEntityBase var1 = (DocaEntityBase)par3World.getEntityByID(par4);
			return new DocaGuiScreen(var1);
		}
		return null;
	}

	@Override
	public Object getServerGuiElement(int par1, EntityPlayer par2Player, World par3World, int par4, int par5, int par6)
	{
		if (par1 == DocaSet.containerContainerID)
		{
			DocaEntityBase var1 = (DocaEntityBase)par3World.getEntityByID(par4);
			
			if (var1 != null && var1 instanceof DocaEntityBase)
			{
				return new DocaContainer(par2Player, var1);
			}
		}

		return null;
	}

}
