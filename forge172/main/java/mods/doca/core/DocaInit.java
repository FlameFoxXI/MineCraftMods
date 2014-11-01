package mods.doca.core;

import java.util.*;
import java.util.logging.*;

import mods.doca.*;
import mods.doca.block.DocaBlockChest;
import mods.doca.client.render.tileentity.DocaChestRenderHelper;
import mods.doca.client.render.tileentity.DocaTileEntityChestRenderer;
import mods.doca.entity.*;
import mods.doca.item.*;
import mods.doca.tileentity.DocaTileEntityChest;
import net.minecraft.block.*;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.tileentity.TileEntityRendererChestHelper;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.creativetab.*;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.*;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraftforge.common.*;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.*;
import cpw.mods.fml.common.event.*;
import cpw.mods.fml.common.registry.*;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class DocaInit
{
	public static boolean makeDocaInitFunc()
	{

		if (DocaSet.addEntityCoreSet.length != 0)
		{
			for (DocaSetTable i : DocaSet.addEntityCoreSet)
			{
				if (!DocaReg.addPetSettingList(i))
				{
					return false;
				}
			}
			return true;
		}
		DocaTools.warning("CoreSystemError : AddPetList is Empty.");
		return false;

	}

	public static void regEntityInit()
	{
		DocaReg.makeDocaSettingInit();
		DocaSet.addEntityUserSet();
	}

	public static void regEntityDoca()
	{
		for (int i = 0; i < DocaReg.getSettingsMax(); i++)
		{
			DocaTools.registerEntity(DocaReg.getEntity(i), DocaReg.getTypeDisp(i), DocaReg.getTypeEntityName(i), DocaReg.getTypeEntityNameUS(i), DocaReg.getEntityId(i), DocaReg.getPetNo(i), DocaReg.getUse(i));
		}
	}

	public static void regTileEntity()
	{
		GameRegistry.registerTileEntity(DocaTileEntityChest.class, "DocaTileEntityChest");
	}

	public static void regItemDoca()
	{
//		DocaSet.spawnDocaItem = (new DocaItemEgg(DocaSet.item_SpawnDocaID)).setUnlocalizedName("SpawnEggDoca").setCreativeTab(DocaSet.tabsDocaCreativeTabs);
		DocaSet.spawnDocaItem = (new DocaItemEgg()).setUnlocalizedName("SpawnEggDoca").setCreativeTab(DocaSet.tabsDocaCreativeTabs);
		DocaTools.registerItemDoca(DocaSet.spawnDocaItem, "SpawnEggDoca");

		for (int i = 0; i < DocaReg.getSettingsMax(); i++)
		{
			if (DocaReg.getUse(i))
			{
				DocaTools.addShapelessRecipeDoca(new ItemStack(DocaSet.spawnDocaItem,1,i),
//					new Object[] { DocaReg.getEggMakeItem0(i), DocaReg.getEggMakeItem1(i), Item.egg });
						new Object[] { DocaReg.getEggMakeItem0(i), DocaReg.getEggMakeItem1(i), Items.egg });
			}
		}

//		DocaSet.spawnCarrierBagItem = (new DocaItemCarrierBag(DocaSet.item_CarrierBagID)).setUnlocalizedName("CarrierBag").setCreativeTab(DocaSet.tabsDocaCreativeTabs);
		DocaSet.spawnCarrierBagItem = (new DocaItemCarrierBag()).setUnlocalizedName("CarrierBag").setCreativeTab(DocaSet.tabsDocaCreativeTabs);
		DocaTools.registerItemDoca(DocaSet.spawnCarrierBagItem, "CarrierBag");
		DocaTools.addShapelessRecipeDoca(new ItemStack(DocaSet.spawnCarrierBagItem, 1),
				new Object[] { Items.diamond, Items.book, Items.bone  });

		DocaSet.spawnItemRibbon = (new DocaItemRibbon()).setUnlocalizedName("Ribbon").setCreativeTab(DocaSet.tabsDocaCreativeTabs);
		DocaTools.registerItemDoca(DocaSet.spawnItemRibbon, "Ribbon");
		DocaTools.addShapelessRecipeDoca(new ItemStack(DocaSet.spawnItemRibbon, 1),
				new Object[] { Items.string, Items.bone });
	}

	public static void regBlockDoca()
	{
		DocaSet.blockDocaChest = new DocaBlockChest().setHardness(2.5F).setStepSound(Block.soundTypeWood).setBlockName("DocaChest").setCreativeTab(DocaSet.tabsDocaCreativeTabs);
		DocaTools.registerBlockDoca(DocaSet.blockDocaChest, "DocaChest");
		DocaTools.addRecipeDoca(new ItemStack(DocaSet.blockDocaChest, 1),
				new Object[] { "XZX", "XYX", "XXX", 'X', new ItemStack(Blocks.planks, 1 , Short.MAX_VALUE), 'Y', Items.ender_pearl, 'Z', Items.bone });
	}

	@SideOnly(Side.CLIENT)
	public static void regRenderPreInit()
	{
		DocaSet.addEntityRenderCoreSet();
	}

	@SideOnly(Side.CLIENT)
	public static void regEntityRenderData()
	{
		for (int i = 0; i < DocaReg.getSettingsMax(); i++)
		{
			DocaTools.registerRender(DocaReg.getEntity(i), DocaReg.getRender(i), DocaReg.getUse(i));
		}
	}

	@SideOnly(Side.CLIENT)
	public static void regTilerenderData()
	{
		DocaTools.bindTileEntitySpecialRendererDoca(DocaTileEntityChest.class, new DocaTileEntityChestRenderer());
		TileEntityRendererChestHelper.instance = new DocaChestRenderHelper();
	}

	public static void regDebug()
	{
		if (DocaSet.Debug)
		{
			DocaSet.maxHelthTimer  = DocaSet.maxHelthTimer / 4;
			DocaSet.maxItemPopTimer  = DocaSet.maxItemPopTimer / 6;
			DocaSet.maxCestSearchTimer = DocaSet.maxCestSearchTimer / 4;
		}

	}

	public static void regCreativeTab()
	{
		if (DocaSet.tab_AddDocaCreativeTab)
		{
			DocaSet.tabsDocaCreativeTabs = new DocaCreativeTabs("DogCatPlusMod");
		}
		else
		{
			DocaSet.tabsDocaCreativeTabs = CreativeTabs.tabMisc;
		}
		}
}
