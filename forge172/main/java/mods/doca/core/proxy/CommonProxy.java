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

public class CommonProxy
{
	public void registerRenderInformation()
	{
	}

	public void registerHandler()
	{
		FMLCommonHandler.instance().bus().register(new DocaServerTickEvent());
	}

	public void registerKeyBinding()
	{
	}

	public void openGui(DocaEntityBase par1DocaEntityBase)
	{
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