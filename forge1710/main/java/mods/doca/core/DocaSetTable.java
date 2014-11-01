package mods.doca.core;

import java.util.*;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.item.Item;

public class DocaSetTable
{
	public int memDocaPetNo = -1;
	public String memDocaPetTypeDisp = "";
	public String memDocaPetNameEntity = "";
	public String memDocaPetNameEntityUS = "";
	public Class memEntityType = null;
	public Render memEntityRender = null;

	public boolean memDocaUse = false;
	public int memDocaEntityId = 0;
	public Object memEggMakeItem[] = { null, null };
	public boolean memDocaGrowAge = false;

	public int memDocaTextureMax = 2;
	public String memDocaTexture = "";
	public String memDocaTextureTame = "";
	public String memDocaTextureCollar = "";

	public double memDocaSpeed = 0.30000001192092896D;
	public Item[] memDocaFeedItem = { null, null, null, null };

	public float memDocaPetWidth = 0.6F;
	public float memDocaPetHeight = 0.8F;
	public float[] memDocaPetHeightSize = { 0.8F, 0.8F, 0.8F };
	public int[] memDocaPetLabelHeight = { 1, 1, 1 };

	public DocaSetTable(int no, String name, String nameEntity, String nameEntityUS, Class type, Object item0, Object item1,
			double speed, Item fItem0, Item fItem1, Item fItem2, Item fItem3,
			float dWidth, float dHeight, float sHeight0, float sHeight1, float sHeight2,int lHeight0, int lHeight1, int lHeight2)
	{
		this.memDocaPetNo = no;
		this.memDocaPetTypeDisp = name;
		this.memDocaPetNameEntity = nameEntity;
		this.memDocaPetNameEntityUS = nameEntityUS;
		this.memEntityType = type;

		this.memEggMakeItem[0] = item0;
		this.memEggMakeItem[1] = item1;

		this.memDocaSpeed = speed;
		this.memDocaFeedItem[0] = fItem0;
		this.memDocaFeedItem[1] = fItem1;
		this.memDocaFeedItem[2] = fItem2;
		this.memDocaFeedItem[3] = fItem3;

		this.memDocaPetWidth = dWidth;
		this.memDocaPetHeight = dHeight;
		this.memDocaPetHeightSize[0] = sHeight0;
		this.memDocaPetHeightSize[1] = sHeight1;
		this.memDocaPetHeightSize[2] = sHeight2;
		this.memDocaPetLabelHeight[0] = lHeight0;
		this.memDocaPetLabelHeight[1] = lHeight1;
		this.memDocaPetLabelHeight[2] = lHeight2;
	}

	public void addEntityUserSet(int no, String name, boolean use, int entityID, boolean growAge)
	{
		this.memDocaUse = use;
		this.memDocaEntityId = entityID;
		this.memDocaGrowAge = growAge;
	}

	public void setRenderSetTable(int no, String name, Render render, int tMax, String Tex, String TexT, String TexC)
	{
		this.memEntityRender = render;
		this.memDocaTextureMax = tMax;
		this.memDocaTexture = Tex;
		this.memDocaTextureTame = TexT;
		this.memDocaTextureCollar = TexC;
	}
}
