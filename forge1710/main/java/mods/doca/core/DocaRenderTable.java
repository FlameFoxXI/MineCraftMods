package mods.doca.core;

import java.util.ArrayList;
import net.minecraft.util.ResourceLocation;

public class DocaRenderTable
{
	public static ResourceLocation[] textureListTable;
	public static ResourceLocation[] textureTameListTable;
	public static ResourceLocation[] textureCollarListTable;

	public static void rendeTableMake(int type)
	{
		if (DocaReg.getSettingsMax() < type)
		{
			return;
		}
		DocaRenderTable.textureListTable = new ResourceLocation[DocaReg.getDocaTMax(type)];
		DocaRenderTable.textureTameListTable = new ResourceLocation[DocaReg.getDocaTMax(type)];
		DocaRenderTable.textureCollarListTable = new ResourceLocation[DocaReg.getDocaTMax(type)];

		for(int j = 0; j < DocaReg.getDocaTMax(type); j++){
			DocaRenderTable.textureListTable[j] = (new ResourceLocation(DocaTools.getRenderIndex(DocaReg.getDocaTex(type), j)));
			DocaRenderTable.textureTameListTable[j] = (new ResourceLocation(DocaTools.getRenderIndex(DocaReg.getDocaTTame(type), j)));
			DocaRenderTable.textureCollarListTable[j]  = (new ResourceLocation(DocaTools.getRenderIndex(DocaReg.getDocaTCollar(type), j)));
		}
	}
}
