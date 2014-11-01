package mods.doca.core;

import java.util.ArrayList;
import java.util.Collection;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.client.model.*;
import net.minecraft.util.*;

@SideOnly(Side.CLIENT)
public class DocaRenderInit
{
	public static ArrayList<ResourceLocation[]> tex = new ArrayList();
	public static ArrayList<ResourceLocation[]> texTame = new ArrayList();
	public static ArrayList<ResourceLocation[]> texCollar = new ArrayList();

	public static void rendeInitInit()
	{
		for (int i = 0; i < DocaReg.getSettingsMax(); i++)
		{
			rendeInitSub(i);
		}
	}

	public static void rendeInitSub(int type)
	{
		if (DocaReg.getSettingsMax() < type)
		{
			return;
		}

		DocaRenderTable.rendeTableMake(type);

		DocaRenderInit.tex.add(DocaRenderTable.textureListTable);
		DocaRenderInit.texTame.add(DocaRenderTable.textureTameListTable);
		DocaRenderInit.texCollar.add(DocaRenderTable.textureCollarListTable);
	}


	public static ResourceLocation getRenderTable(int type, int no, String table)
	{
		ResourceLocation[] tmp = null;
		if (table.equalsIgnoreCase("tame"))
		{
			tmp = (ResourceLocation[])DocaRenderInit.texTame.get(type);
		}
		else if(table.equalsIgnoreCase("collar"))
		{
			tmp = (ResourceLocation[])DocaRenderInit.texCollar.get(type);
		}
		else
		{
			tmp = (ResourceLocation[])DocaRenderInit.tex.get(type);
		}

		return (ResourceLocation)tmp[no];
	}

}