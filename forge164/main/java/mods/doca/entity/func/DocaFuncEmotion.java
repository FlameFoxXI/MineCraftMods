package mods.doca.entity.func;
import java.util.List;
import java.util.Random;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import mods.doca.core.*;
import mods.doca.entity.*;



public class DocaFuncEmotion
{
	public final static int EMOTION_EMPTY = 0;
	public final static int EMOTION_ANGRY = 1;
	public final static int EMOTION_FOOD  = 2;
	public final static int EMOTION_HAPPY = 3;
	public final static int EMOTION_HEART = 4;

	public static ResourceLocation[] iconEmotion = {
		new ResourceLocation(DocaSet.textureEmoteWhite),
		new ResourceLocation(DocaSet.textureEmoteAngry),
		new ResourceLocation(DocaSet.textureEmoteFood),
		new ResourceLocation(DocaSet.textureEmoteHappy),
		new ResourceLocation(DocaSet.textureEmoteheart)
	};

	private Random rand = new Random();
	private int emotionEvent;
	private int emotionCount;

	public DocaFuncEmotion()
	{
		this.emotionEvent = DocaFuncEmotion.EMOTION_EMPTY;
		this.emotionCount = 0;
	}

	public boolean onLivingUpdate(DocaEntityBase theBase)
	{
		boolean tmp = false;
		if (this.emotionCount == 0)
		{
			theBase.setEmote(DocaFuncEmotion.EMOTION_EMPTY);
			tmp = true;
		}
		else
		{
			this.emotionCount--;
		}
		return tmp;
	}

	public void setEmotionLiving(DocaEntityBase theBase, int type)
	{
		Side side = FMLCommonHandler.instance().getEffectiveSide();
		if (DocaSet.func_EmotionBalloonON && theBase.isTamed() && this.emotionCount == 0 && side == Side.CLIENT)
		{
			theBase.setEmote(type);
			this.emotionCount = DocaSet.maxEmotionTimer;
		}
	}

	public int isEmotionLiving(DocaEntityBase theBase)
	{
		return theBase.getEmote();
	}
}