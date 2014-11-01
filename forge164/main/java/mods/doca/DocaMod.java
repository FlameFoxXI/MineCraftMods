package mods.doca;

import cpw.mods.fml.common.*;
import cpw.mods.fml.common.Mod.*;
import cpw.mods.fml.common.event.*;
import cpw.mods.fml.common.network.*;
import cpw.mods.fml.common.registry.*;
import cpw.mods.fml.relauncher.*;

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
@NetworkMod(channels = {DocaTools.PACKET_TYPE_DATA, DocaTools.PACKET_KEY_CHECK, DocaTools.PACKET_OPS_CHECK, DocaTools.PACKET_SOWN_DATA },
			clientSideRequired = true, serverSideRequired = false, packetHandler = DocaPacketHandler.class)
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
		this.proxy.registerKeyHandler();
		
		GameRegistry.registerPlayerTracker(new DocaPlayerHandler());
		NetworkRegistry.instance().registerGuiHandler(instance, proxy);
		TickRegistry.registerTickHandler(new DocaTickInGame(), Side.CLIENT);
		TickRegistry.registerTickHandler(new DocaTickInGameServer(), Side.SERVER);
	}
}
