package mods.doca.core.proxy;

import cpw.mods.fml.client.*;
import cpw.mods.fml.client.registry.*;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.*;
import mods.doca.*;
import mods.doca.block.DocaBlockChest;
import mods.doca.core.*;
import mods.doca.entity.*;
import mods.doca.core.handler.*;
import mods.doca.inventory.*;
import mods.doca.tileentity.*;
import mods.doca.client.render.*;
import mods.doca.client.render.tileentity.*;
import mods.doca.client.gui.*;
import mods.doca.client.gui.inventory.*;
import net.minecraft.client.*;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.*;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.world.*;
import net.minecraftforge.client.*;

@SideOnly(Side.CLIENT)
public class ClientProxy extends CommonProxy
{
	@Override
	public void registerRenderInformation()
	{
		DocaInit.regRenderPreInit();
		DocaRenderInit.rendeInitInit();
		DocaInit.regEntityRenderData();
		DocaInit.regTilerenderData();
	}

	@Override
	public void registerHandler()
	{
		FMLCommonHandler.instance().bus().register(new DocaClientTickEvent());
	}

	@Override
	public void registerKeyBinding()
	{
//		KeyBindingRegistry.registerKeyBinding(new DocaKeyHandler());
		DocaClientTickEvent.registerKeyBinding();
	}

	@Override
	public void openGui(DocaEntityBase par1DocaEntityBase)
	{
		Minecraft.getMinecraft().displayGuiScreen(new DocaGuiScreen(par1DocaEntityBase));
	}

	@Override
	public boolean geKeyPlessUpdate(DocaEntityBase theBase, int i)
	{
		return DocaSet.keyLocalDoca.getUserName().equalsIgnoreCase(theBase.getOwnerName()) ? DocaSet.keyLocalDoca.getKeys(i) : false;
	}
	
	@Override
	public void setChatMassageProxy(String message)
	{
		Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentTranslation(message, new Object[0]));
	}

	@Override
	public boolean getOps(EntityPlayer player)
	{
		return false;
	}
	
	@Override
	public boolean chkOps(EntityPlayer player)
	{
		return DocaSet.isUserOps;
	}
	
	@Override
	public boolean getPermissionAccessInventory()
	{
		return false;
	}
}