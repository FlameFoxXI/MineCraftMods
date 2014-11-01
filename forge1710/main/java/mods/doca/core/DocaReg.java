package mods.doca.core;

import java.util.*;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

public class DocaReg
{
	public static HashMap<String,Integer> petModeList = new HashMap<String,Integer>();
	public static HashMap<String,Integer> petSizeList = new HashMap<String,Integer>();

	public static ArrayList<String> convTypeToName = new ArrayList<String>();
	public static HashMap<String,Integer> convNameToType = new HashMap<String,Integer>();

	public static String petModeLabelList[] =
		{
		"Normal",
		"FireSpirit",
		"WaterSpirit",
		"WoodSpirit"
		};

	public static String petSizeLabelList[] =
		{
		"Normal",
		"Big",
		"Small"
		};

	public static ArrayList<DocaSetTable> mapPetSettings = new ArrayList<DocaSetTable>();

	public static int maxTexSize = 0;

	public static boolean addPetSettingList(DocaSetTable entity)
	{
		if (!DocaReg.mapPetSettings.isEmpty()){
			for (int i = 0; i < DocaReg.mapPetSettings.size(); i++)
			{
				if (entity.memDocaPetNo == DocaReg.mapPetSettings.get(i).memDocaPetNo)
				{
					DocaTools.warning("CoreSystemError : It has already been registered. pet no %s", entity.memDocaPetNo);
					return false;
				}
			}
			for (int i = 0; i < DocaReg.mapPetSettings.size(); i++)
			{
				if (entity.memDocaPetTypeDisp.equalsIgnoreCase(DocaReg.mapPetSettings.get(i).memDocaPetTypeDisp))
				{
					DocaTools.warning("CoreSystemError : It has already been registered. pet Name.%s", entity.memDocaPetTypeDisp);
					return false;
				}
			}
		}
		DocaReg.mapPetSettings.add(entity);
		DocaReg.convNameToType.put(entity.memDocaPetTypeDisp, DocaReg.convNameToType.size());
		DocaReg.convTypeToName.add(entity.memDocaPetTypeDisp);
		return true;
	}

	public static void makeDocaSettingInit()
	{
		for(int i = 0; i < petModeLabelList.length; i++)
		{
			String tmp  = petModeLabelList[i];
			petModeList.put(tmp, i);
		}
		for(int i = 0; i < petSizeLabelList.length; i++)
		{
			String tmp  = petSizeLabelList[i];
			petSizeList.put(tmp, i);
		}
	}

	public static boolean addEntityUserSet(int no, String name, boolean use, int entityID, boolean growAge)
	{
		if (!DocaReg.mapPetSettings.isEmpty()){
			for (int i = 0; i < DocaReg.mapPetSettings.size(); i++)
			{
				if (no == DocaReg.mapPetSettings.get(i).memDocaPetNo && name.equalsIgnoreCase(DocaReg.mapPetSettings.get(i).memDocaPetTypeDisp))
				{
					DocaReg.mapPetSettings.get(i).addEntityUserSet(no, name, use, entityID, growAge);
					return true;
				}
			}
			DocaTools.warning("CoreSystemError : No mach no and name RenderSetTable.");
			return false;
		}
		DocaTools.warning("CoreSystemError : mapPetSettings is empty. for RenderSetTable.");
		return false;
	}

	public static boolean addEntityRenderSet(int no, String name, Render render, int tMax, String Tex, String TexT, String TexC)
	{
		if (!DocaReg.mapPetSettings.isEmpty()){
			for (int i = 0; i < DocaReg.mapPetSettings.size(); i++)
			{
				if (no == DocaReg.mapPetSettings.get(i).memDocaPetNo && name.equalsIgnoreCase(DocaReg.mapPetSettings.get(i).memDocaPetTypeDisp))
				{
					DocaReg.mapPetSettings.get(i).setRenderSetTable(no, name, render, tMax, Tex, TexT, TexC);
					return true;
				}
			}
			DocaTools.warning("CoreSystemError : No mach no and name RenderSetTable.");
			return false;
		}
		DocaTools.warning("CoreSystemError : mapPetSettings is empty. for RenderSetTable.");
		return false;
	}

	/**************************************************************************************************************************
	* Getter
	**************************************************************************************************************************/

	public static int getPetNo(int no)
	{
		if (mapPetSettings.isEmpty() || mapPetSettings.size() < no){
			DocaTools.warning("memDocaPetNo Error");
			return 0;
		}
		return (mapPetSettings.get(no)).memDocaPetNo;
	}

	public static String getTypeDisp(int no)
	{
		if (mapPetSettings.isEmpty() || mapPetSettings.size() < no)
		{
			DocaTools.warning("memDocaPetTypeDisp Error");
			return "";
		}
		return (mapPetSettings.get(no)).memDocaPetTypeDisp;
	}

	public static String getTypeEntityName(int no)
	{
		if (mapPetSettings.isEmpty() || mapPetSettings.size() < no)
		{
			DocaTools.warning("memDocaPetNameEntity Error");
			return "";
		}
		return (mapPetSettings.get(no)).memDocaPetNameEntity;
	}

	public static String getTypeEntityNameUS(int no)
	{
		if (mapPetSettings.isEmpty() || mapPetSettings.size() < no)
		{
			DocaTools.warning("memDocaPetNameEntityUS Error");
			return "";
		}
		return (mapPetSettings.get(no)).memDocaPetNameEntityUS;
	}

	public static Class getEntity(int no)
	{
		if (mapPetSettings.isEmpty() || mapPetSettings.size() < no){
			DocaTools.warning("memEntityType Error");
			return null;
		}
		return (mapPetSettings.get(no)).memEntityType;
	}

	public static Render getRender(int no)
	{
		if (mapPetSettings.isEmpty() || mapPetSettings.size() < no){
			DocaTools.warning("memEntityRender Error");
			return null;
		}
		return (mapPetSettings.get(no)).memEntityRender;
	}

	public static boolean getUse(int no)
	{
		if (mapPetSettings.isEmpty() || mapPetSettings.size() < no)
		{
			DocaTools.warning("memDocaUse Error");
			return false;
		}
		return (mapPetSettings.get(no)).memDocaUse;
	}

	public static int getEntityId(int no)
	{
		if (mapPetSettings.isEmpty() || mapPetSettings.size() < no){
			DocaTools.warning("memDocaEntityId Error");
			return 0;
		}
		return (mapPetSettings.get(no)).memDocaEntityId;
	}

	public static boolean getGrowAge(int no){
		if (mapPetSettings.isEmpty() || mapPetSettings.size() < no){
			DocaTools.warning("memDocaGrowAge Error");
			return false;
		}
		return (mapPetSettings.get(no)).memDocaGrowAge;
	}

	public static Object getEggMakeItem0(int no){
		if (mapPetSettings.isEmpty() || mapPetSettings.size() < no){
			DocaTools.warning("memEggMakeItem[0] Error");
			return false;
		}
		return (mapPetSettings.get(no)).memEggMakeItem[0];
	}

	public static Object getEggMakeItem1(int no){
		if (mapPetSettings.isEmpty() || mapPetSettings.size() < no){
			DocaTools.warning("memEggMakeItem1 Error");
			return false;
		}
		return (mapPetSettings.get(no)).memEggMakeItem[1];
	}

	public static double getDocaSpeed(int no){
		if (mapPetSettings.isEmpty() || mapPetSettings.size() < no){
			DocaTools.warning("memDocaSpeed Error");
			return 0.30000001192092896D;
		}
		return (mapPetSettings.get(no)).memDocaSpeed;
	}

	public static Item getFeedItem(int no, int type){
		if (mapPetSettings.isEmpty() || mapPetSettings.size() < no || 4 < type){
			DocaTools.warning("memDocaFeedItem Error");
			return Items.apple;
		}
		return (mapPetSettings.get(no)).memDocaFeedItem[type];
	}

	public static int getDocaTMax(int no){
		if (mapPetSettings.isEmpty() || mapPetSettings.size() < no){
			DocaTools.warning("memDocaTextureMax Error");
			return 2;
		}
		return (mapPetSettings.get(no)).memDocaTextureMax + 1;
	}

	public static String getDocaTex(int no){
		if (mapPetSettings.isEmpty() || mapPetSettings.size() < no){
			DocaTools.warning("memDocaTexture Error");
			return "";
		}
		return (mapPetSettings.get(no)).memDocaTexture;
	}

	public static String getDocaTTame(int no){
		if (mapPetSettings.isEmpty() || mapPetSettings.size() < no){
			DocaTools.warning("memDocaTextureTame Error");
			return "";
		}
		return (mapPetSettings.get(no)).memDocaTextureTame;
	}

	public static String getDocaTCollar(int no){
		if (mapPetSettings.isEmpty() || mapPetSettings.size() < no){
			DocaTools.warning("memDocaTextureCollar Error");
			return "";
		}
		return (mapPetSettings.get(no)).memDocaTextureCollar;
	}

	public static float getDocaWidth(int no){
		if (mapPetSettings.isEmpty() || mapPetSettings.size() < no){
			DocaTools.warning("memDocaPetWidth Error");
			return 0.6F;
		}
		return (mapPetSettings.get(no)).memDocaPetWidth;
	}

	public static float getDocaHeight(int no){
		if (mapPetSettings.isEmpty() || mapPetSettings.size() < no){
			DocaTools.warning("memDocaPetWidth Error");
			return 0.8F;
		}
		return (mapPetSettings.get(no)).memDocaPetHeight;
	}


	public static float getDocaHeightSize(int no, int type){
		if (mapPetSettings.isEmpty() || mapPetSettings.size() < no || 3 < type){
			DocaTools.warning("memDocaLivingSound Error");
			return 0.8F;
		}
		return (mapPetSettings.get(no)).memDocaPetHeightSize[type];
	}


	public static int getDocaLabelSize(int no, int type){
		if (mapPetSettings.isEmpty() || mapPetSettings.size() < no || 3 < type){
			DocaTools.warning("memDocaLivingSound Error");
			return 1;
		}
		return (mapPetSettings.get(no)).memDocaPetLabelHeight[type];
	}

	/* ------------------------------------------------------------------------------------------------------------------
		Get Mode
	------------------------------------------------------------------------------------------------------------------- */

	public static int getSettingsMax(){
		return mapPetSettings.size();
	}

	public static int getNameToType(String type){
		return convNameToType.get(type);
	}

	public static String getTypeToName(int no){
		if (mapPetSettings.isEmpty() || mapPetSettings.size() < no){
			DocaTools.warning("getTypeName Error");
			return "";
		}
		return convTypeToName.get(no);
	}

	/* ------------------------------------------------------------------------------------------------------------------
		Get Mode
	------------------------------------------------------------------------------------------------------------------- */
	public static int getModeMax()
	{
		return petModeLabelList.length;
	}

	public static int getModeNameToNo(String type)
	{
		return petModeList.get(type);
	}

	public static String getModeTypeToName(int no){
		if (petModeLabelList.length < no){
			DocaTools.warning("getModeTypeToName Error");
			return "";
		}
		return petModeLabelList[no];
	}

	/* ------------------------------------------------------------------------------------------------------------------
		Get Size
	------------------------------------------------------------------------------------------------------------------- */
	public static int getSizeMax()
	{
		return petSizeLabelList.length;
	}

	public static int getSizeNameToNo(String type)
	{
		return petSizeList.get(type);
	}

	public static String getSizeTypeToName(int no){
		if (petSizeLabelList.length < no){
			DocaTools.warning("getSizeTypeToName Error");
			return "";
		}
		return petSizeLabelList[no];
	}
}
