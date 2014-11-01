package mods.doca.core.proxy;

import cpw.mods.fml.client.*;
import cpw.mods.fml.client.registry.*;
import cpw.mods.fml.common.*;
import cpw.mods.fml.common.network.*;
import cpw.mods.fml.relauncher.*;
import mods.doca.*;
import mods.doca.core.*;
import mods.doca.entity.*;
import mods.doca.core.handler.*;
import mods.doca.inventory.*;
import mods.doca.client.render.*;
import mods.doca.client.gui.*;
import mods.doca.client.gui.inventory.*;
import net.minecraft.client.*;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.*;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.*;
import net.minecraftforge.client.*;

public class CommonProxy implements IGuiHandler
{
	public void registerRenderInformation()
	{
	}

	public void registerKeyHandler()
	{
	}

	public void openGui(DocaEntityBase par1DocaEntityBase)
	{
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

	@Override
	public Object getClientGuiElement(int par1, EntityPlayer par2Player, World par3World, int par4, int par5, int par6)
	{
		return null;
	}

	public boolean geKeyPlessUpdate(DocaEntityBase theBase, int i)
	{
		DocaKeys tmp = DocaTools.getLoginUser(theBase.getOwnerName());
		return tmp != null ? tmp.getKeys(i) : false;
	}
	
	public void setChatMassageProxy(String message)
	{
	}
	
	public boolean getOps(EntityPlayer player)
	{
		return DocaSet.func_AccessPermissionOPS ? 
				MinecraftServer.getServer().getConfigurationManager().getOps().contains(player.getCommandSenderName().toLowerCase()) : false;
	}

	public boolean chkOps(EntityPlayer player)
	{
		return DocaSet.func_AccessPermissionOPS ? this.getOps(player) : false;
	}

	public boolean getPermissionAccessInventory()
	{
		return DocaSet.func_AccessPermissionOPS;
	}
}