package mods.doca.core;

import java.util.Set;

import org.apache.logging.log4j.Level;

import mods.doca.*;
import mods.doca.core.handler.DocaPacketHandler;
import mods.doca.core.network.*;
import mods.doca.core.proxy.CommonProxy;
import mods.doca.entity.DocaEntityBase;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.common.config.Configuration;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

public class DocaTools
{
	public static final String PACKET_TYPE_DATA = "doca_type_update";
	public static final String PACKET_KEY_CHECK = "doca_keys_update";
	public static final String PACKET_OPS_CHECK = "doca_item_update";
	public static final String PACKET_SOWN_DATA = "doca_sown_update";

	public static final int KEY_MIN   = 0;
	public static final int KEY_FRE   = 0;
	public static final int KEY_COM   = 1;
	public static final int KEY_DWN   = 2;
	public static final int KEY_DIS   = 3;
	public static final int KEY_INF   = 4;
	public static final int KEY_SIT   = 5;
	public static final int KEY_INV   = 6;
	public static final int KEY_SET   = 7;
	public static final int KEY_HOM   = 8;
	public static final int KEY_MAX   = 9;
	public static final int KEY_UNLOADTIMER   = 1;

	public static void regConfigurationile(FMLPreInitializationEvent event)
	{
		DocaConigFileMaker config = new DocaConigFileMaker(event.getSuggestedConfigurationFile());

		try
		{
			DocaSet.makeConfigFile(config);
		}
		catch (Exception e)
		{
			DocaTools.warning("has a problem loading it's configuration");
		}
		finally
		{
			config.save();
		}
		DocaSet.afterConfigFile();
	}

	public static void registerEntity(Class entityClass, String name, String nameEntity, String nameEntityUS, int par3, int par4, boolean par5)
	{

		if (par5)
		{
			if (DocaSet.entity_type_OldType)
			{
				int entityID;
				String tmpName = "Entity" + name;

				if (par3 == 0)
				{
					entityID = EntityRegistry.findGlobalUniqueEntityId();
				}
				else
				{
					entityID = par3;
				}
				EntityRegistry.registerGlobalEntityID(entityClass, tmpName, entityID);
				EntityRegistry.registerModEntity(entityClass, tmpName, entityID, getModInstance(), 160, 1, true);
			}
			else
			{
				EntityRegistry.registerModEntity(entityClass, nameEntity, par4, getModInstance(), 160, 1, true);
			}
		}
	}

	public static void registerRender(Class<? extends Entity> entityClass, Render renderer, boolean b0)
	{
		if (b0)
		{
			RenderingRegistry.registerEntityRenderingHandler(entityClass, renderer);
		}
	}

	public static void bindTileEntitySpecialRendererDoca(Class <? extends TileEntity> tileEntityClass, TileEntitySpecialRenderer specialRenderer)
	{
		ClientRegistry.bindTileEntitySpecialRenderer(tileEntityClass, specialRenderer);
	}

	public static int mathMaxMin(int max, int min, int value)
	{
		return Math.max(min, Math.min(max, value));
	}

	public static void warning(String format)
	{
		DocaTools.msDoca(Level.WARN, format);
	}

	public static void warning(String format, Object... data)
	{
		DocaTools.msDoca(Level.WARN, format, data);
	}

	public static void info(String format)
	{
		DocaTools.msDoca(Level.INFO, format);
	}
	
	public static void info(String format, Object... data)
	{
		DocaTools.msDoca(Level.INFO, format, data);
	}

	public static void severe(String format)
	{
		DocaTools.msDoca(Level.ERROR, format);
	}
	
	public static void severe(String format, Object... data)
	{
		DocaTools.msDoca(Level.ERROR, format, data);
	}

	public static void msDoca(Level level, String format)
	{
		DocaTools.msDoca(level, format, new Object[0]);
	}
	
	public static void msDoca(Level level, String format, Object... data)
	{
		FMLLog.log(DocaSet.MODID, level, format, data);
	}

	public static String getRenderIndex(String par1, int par2)
	{
		if (par2 != 0)
		{
			par1 = par1.replaceAll(".png", "") + par2 + ".png";
		}

		return par1;
	}

	public static void registerItemDoca(net.minecraft.item.Item item, String name)
	{
		GameRegistry.registerItem(item, name, null);
	}

	public static void registerBlockDoca(net.minecraft.block.Block block, String name)
	{
		GameRegistry.registerBlock(block, ItemBlock.class, name);
	}

	public static void addRecipeDoca(ItemStack output, Object... params)
	{
		GameRegistry.addRecipe(output, params);
	}

	public static void addShapelessRecipeDoca(ItemStack output, Object... params)
	{
		GameRegistry.addShapelessRecipe(output, params);
	}

	public static void openEntityGuiDoca(int modGuiId, World world, int entityId, EntityPlayer par1EntityPlayer)
	{
		par1EntityPlayer.openGui(getModInstance(), modGuiId, world, entityId, 0, 0);
	}

	public static void setDocaToSendQueue(DocaEntityBase theBase, EntityPlayer player, String type)
	{
		if (type.equalsIgnoreCase(PACKET_TYPE_DATA))
		{
			DocaPacketHandler.network.sendToServer(new DocaPacketType(theBase));
//			DocaPacketCore.sendToServer(new DocaPacketType(theBase));
		}
		else if (type.equalsIgnoreCase(PACKET_SOWN_DATA) && player instanceof EntityPlayerMP)
		{
			DocaPacketHandler.network.sendTo(new DocaPacketSubOwner(theBase), (EntityPlayerMP)player);
//			DocaPacketCore.sendToPlayer(new DocaPacketSubOwner(theBase), player);
		}
	}

	public static void setDocaToSendQueue(DocaKeys key, String type)
	{
		if (type.equalsIgnoreCase(PACKET_KEY_CHECK))
		{
			DocaPacketHandler.network.sendToServer(new DocaPacketKey(key));
//			DocaPacketKey.sendToServer(new DocaPacketKey(DocaSet.keyLocalDoca));
		}
	}

	public static boolean geKeyPlessUpdateDoca(DocaEntityBase theBase, int no)
	{
		return getModProxy().geKeyPlessUpdate(theBase, no);
	}

	public static void setbindTextureDoca(ResourceLocation texture)
	{
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
	}

	public static void setChatMassageDoca(String message)
	{
		getModProxy().setChatMassageProxy(message);
	}
	
	public static void setChatMassageDoca2(boolean remote, String message)
	{
		if(remote)
		{
			getModProxy().setChatMassageProxy(message);
		}
	}

	public static boolean isOps(EntityPlayer player)
	{
		return getModProxy().chkOps(player);
	}

	
	public static boolean ofItem(ItemStack baseitem, Object compItem)
	{
		if (baseitem == null || compItem == null)
		{
			return false;
		}

		if (baseitem.getItem() == null)
		{
			return false;
		}

		if (compItem instanceof Block && baseitem.getItem() == Item.getItemFromBlock((Block)compItem))
		{
			return true;
		}
		else if (compItem instanceof Item && baseitem.getItem() == ((Item)compItem))
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	public static boolean ofItem(Item baseitem, Object compItem)
	{
		if (baseitem == null || compItem == null)
		{
			return false;
		}

		if (compItem instanceof Item && baseitem == ((Item)compItem))
		{
			return true;
		}
//		else if (compItem instanceof Block && baseitem.itemID == (((Block)compItem).blockID + 256))
//		{
//			return true;
//		}
		else
		{
			return false;
		}
	}

	public static Item newItem(int itemID)
	{
		return Item.getItemById(itemID);
	}

	public static Item newItem(ItemStack itemstack)
	{
		if (itemstack != null && itemstack.getItem() != null){
//			return Item.itemsList[itemstack.itemID];
			return itemstack.getItem();
		}
		else
		{
			return null;
		}
	}

	public static int newItemID(Item item)
	{
		return Item.getIdFromItem(item);
	}

	public static int newItemID(ItemStack itemstack)
	{
		return itemstack != null && itemstack.getItem() != null ? Item.getIdFromItem(itemstack.getItem()) : 0;
	}

	public static DocaEntityBase createEntity(int no, World par1World)
	{
		DocaEntityBase entity = null;

		try
		{
			Class clazz = (Class)DocaReg.getEntity(no);

			if (clazz != null)
			{
				entity = (DocaEntityBase)clazz.getConstructor(new Class[] {World.class}).newInstance(new Object[] {par1World});
			}
		}
		catch (Exception e)
		{
			DocaTools.warning("exception create entity. entity=" + entity + ", no=" + no);
			e.printStackTrace();
		}

		return entity;
	}

	public static int calcCountUpRoop(int value, int min, int max)
	{
		if (max <= value + 1)
		{
			return min;
		}
		else
		{
			return value + 1;
		}
	}

	public static int calcCountDownRoop(int value, int min, int max)
	{
		if (min <= value - 1)
		{
			return value - 1;
		}
		else
		{
			return max - 1;
		}
	}

	public static void setLoginUser(String UserName)
	{
		if (!UserName.equalsIgnoreCase(""))
		{
			if (!DocaSet.keySeverDoca.isEmpty())
			{
				for (DocaKeys tmpKey : DocaSet.keySeverDoca)
				{
					if (UserName.equalsIgnoreCase(tmpKey.getUserName()))
					{
						DocaSet.keySeverDoca.remove(tmpKey);
					}
				}
			}
			DocaSet.keySeverDoca.add(new DocaKeys(UserName));
		}
		else
		{
			DocaTools.warning("ERROR: user name is empty. 0001");
		}
	}

	public static DocaKeys getLoginUser(String UserName)
	{
		if (!UserName.equalsIgnoreCase(""))
		{
			if (!DocaSet.keySeverDoca.isEmpty())
			{
				for (DocaKeys tmpKey : DocaSet.keySeverDoca)
				{
					if (UserName.equalsIgnoreCase(tmpKey.getUserName()))
					{
						return tmpKey;
					}
				}
			}
		}
		else
		{
			DocaTools.warning("ERROR: user name is empty. 0002");
		}
		return null;
	}

	public static void updateLoginUser(DocaKeys data)
	{
		if (!data.getUserName().equalsIgnoreCase(""))
		{
			if (!DocaSet.keySeverDoca.isEmpty())
			{
				for (DocaKeys tmpKey : DocaSet.keySeverDoca)
				{
					if (data.getUserName().equalsIgnoreCase(tmpKey.getUserName()))
					{
						DocaSet.keySeverDoca.remove(tmpKey);
						break;
					}
				}
			}
			DocaSet.keySeverDoca.add(data);
		}
		else
		{
			DocaTools.warning("ERROR: user name is empty. 0003");
		}
	}

	public static String chopStringDoca(String data)
	{
		data = data.trim();
		data = data.replaceAll(" ", "");
		return data;
	}

    public static boolean checkInt(String str) {
        try {
        	Integer.parseInt(str);
            return true;
        } catch(NumberFormatException e) {
            return false;
        }
    }
    
    public static CommonProxy getModProxy()
    {
    	return DocaMod.proxy;
    }
    
    public static DocaMod getModInstance()
    {
    	return DocaMod.instance;
    }
}
