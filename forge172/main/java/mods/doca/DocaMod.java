package mods.doca;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.*;
import cpw.mods.fml.common.Mod.*;
import cpw.mods.fml.common.event.FMLLoadCompleteEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.relauncher.Side;
import mods.doca.core.*;
import mods.doca.entity.*;
import mods.doca.entity.func.*;
import mods.doca.core.handler.*;
import mods.doca.item.*;
import mods.doca.core.proxy.*;
import net.minecraft.block.Block;
import net.minecraft.client.*;
import net.minecraft.client.entity.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.gui.inventory.*;
import net.minecraft.creativetab.*;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.*;

import java.io.*;
import java.util.*;
import java.util.logging.*;

@Mod(modid=DocaSet.MODID, name=DocaSet.MODNAME, version=DocaSet.MODVERSION)
public class DocaMod
{
	@SidedProxy(clientSide=DocaSet.MODCLIENT, serverSide=DocaSet.MODSERVER)
	public static CommonProxy proxy;

	@Mod.Instance(DocaSet.MODID)
	public static DocaMod instance;

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		if (DocaSet.DebugStop) {return;}

		DocaTools.regConfigurationile(event);

		if (!DocaInit.makeDocaInitFunc())
		{
			return;
		}
		DocaInit.regEntityInit();
		DocaInit.regEntityDoca();
		DocaInit.regCreativeTab();
		DocaInit.regItemDoca();
		DocaInit.regBlockDoca();
		DocaInit.regTileEntity();
		DocaInit.regDebug();
	}
	
	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
		if (DocaSet.DebugStop) {return;}

		this.proxy.registerRenderInformation();
		this.proxy.registerHandler();
		this.proxy.registerKeyBinding();
		NetworkRegistry.INSTANCE.registerGuiHandler(instance, new DocaGuiHandler());
		DocaPacketHandler.DocaPacketHandlerInit();
		
		FMLCommonHandler.instance().bus().register(new DocaServerTickEvent());
		FMLCommonHandler.instance().bus().register(new DocaPlayerHandler());
	}
}
