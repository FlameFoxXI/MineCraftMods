package mods.doca.core.handler;

import java.util.EnumSet;
import java.util.List;

import org.lwjgl.input.Keyboard;

import mods.doca.*;
import mods.doca.core.*;
import mods.doca.entity.DocaEntityBase;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.StatCollector;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;

public class DocaTickInGame implements ITickHandler
{
	@Override
	public void tickStart(EnumSet<TickType> type, Object... tickData) {}

	@Override
	public void tickEnd(EnumSet<TickType> type, Object... tickData)
	{
		if (type.equals(EnumSet.of(TickType.RENDER)))
		{
			onRenderTick();
		}
		else if (type.equals(EnumSet.of(TickType.CLIENT)))
		{
			GuiScreen guiscreen = Minecraft.getMinecraft().currentScreen;

			if (guiscreen != null)
			{
				onTickInGUI(guiscreen);
			}
			else
			{
				onTickInGame();
			}
		}
	}

	@Override
	public EnumSet<TickType> ticks()
	{
		return EnumSet.of(TickType.RENDER, TickType.CLIENT);
	}

	@Override
	public String getLabel()
	{
		return null;
	}

	public void onRenderTick()
	{
	}

	public void onTickInGUI(GuiScreen guiscreen)
	{
	}

	public void onTickInGame()
	{
		if (DocaSet.keyLocalDoca.getTimer() == 0)
		{
			boolean tmpSend = false;
			for (int i = DocaTools.KEY_MIN; i < DocaTools.KEY_MAX; i++)
			{
				if (DocaSet.keyLocalDoca.getKeys(i) != DocaSet.tempKeyLocalDoca.getKeys(i))
				{
					DocaSet.tempKeyLocalDoca.setUserName(Minecraft.getMinecraft().thePlayer.username);
					tmpSend = true;
					break;
				}
			}
			if(tmpSend)
			{
				DocaSet.keyLocalDoca.setUserName(DocaSet.tempKeyLocalDoca.getUserName());
				DocaSet.keyLocalDoca.setTimer(2);

				if (!DocaSet.keyLocalDoca.getKeys(DocaTools.KEY_COM) && DocaSet.tempKeyLocalDoca.getKeys(DocaTools.KEY_COM))
				{
					DocaTickInGame.printMassageDoca(DocaTools.KEY_COM, DocaSet.tempKeyLocalDoca.getUserName());
				}
				else if (!DocaSet.keyLocalDoca.getKeys(DocaTools.KEY_DIS) && DocaSet.tempKeyLocalDoca.getKeys(DocaTools.KEY_DIS))
				{
					DocaTickInGame.printMassageDoca(DocaTools.KEY_DIS, DocaSet.tempKeyLocalDoca.getUserName());
				}
				else if (!DocaSet.keyLocalDoca.getKeys(DocaTools.KEY_DWN) && DocaSet.tempKeyLocalDoca.getKeys(DocaTools.KEY_DWN))
				{
					DocaTickInGame.printMassageDoca(DocaTools.KEY_DWN, DocaSet.tempKeyLocalDoca.getUserName());
				}
				else if (!DocaSet.keyLocalDoca.getKeys(DocaTools.KEY_FRE) && DocaSet.tempKeyLocalDoca.getKeys(DocaTools.KEY_FRE))
				{
					DocaTickInGame.printMassageDoca(DocaTools.KEY_FRE, DocaSet.tempKeyLocalDoca.getUserName());
				}
				else if (!DocaSet.keyLocalDoca.getKeys(DocaTools.KEY_INF) && DocaSet.tempKeyLocalDoca.getKeys(DocaTools.KEY_INF))
				{
					DocaTickInGame.printMassageDoca(DocaTools.KEY_INF, DocaSet.tempKeyLocalDoca.getUserName());
				}
				for (int i = DocaTools.KEY_MIN; i < DocaTools.KEY_MAX; i++)
				{
					DocaSet.keyLocalDoca.setKeys(i, DocaSet.tempKeyLocalDoca.getKeys(i));
				}
				
				DocaTools.setDocaToSendQueue(DocaSet.keyLocalDoca, DocaTools.PACKET_KEY_CHECK);
			}

		}
		else if (0 < DocaSet.keyLocalDoca.getTimer())
		{
			DocaSet.keyLocalDoca.setTimer(DocaSet.keyLocalDoca.getTimer() - 1);
		}
	}

	private static void printMassageDoca(int type, String name)
	{
		if (type == DocaTools.KEY_COM)
		{
			DocaTools.setChatMassageDoca(StatCollector.translateToLocal("doca.chat.message.ModeStatusCome"));
		}
		else if (type == DocaTools.KEY_DIS)
		{
			DocaTools.setChatMassageDoca(StatCollector.translateToLocal("doca.chat.message.ModeStatusDistance"));
		}
		else if (type == DocaTools.KEY_DWN)
		{
			DocaTools.setChatMassageDoca(StatCollector.translateToLocal("doca.chat.message.ModeStatusDown"));
		}
		else if (type == DocaTools.KEY_FRE)
		{
			DocaTools.setChatMassageDoca(StatCollector.translateToLocal("doca.chat.message.ModeStatusFree"));
		}
		else if (type == DocaTools.KEY_INF)
		{
			int tempCounter = 0;
			List tempEList = Minecraft.getMinecraft().theWorld.loadedEntityList;

			if (!tempEList.isEmpty())
			{
				for (int i = 0; i < tempEList.size(); i++)
				{
					if (tempEList.get(i) instanceof DocaEntityBase)
					{
						DocaEntityBase tmpEntity = (DocaEntityBase)tempEList.get(i);
						String tmpPosX = (new StringBuilder()).append((int)tmpEntity.posX).toString();
						String tmpPosY = (new StringBuilder()).append((int)tmpEntity.posY).toString();
						String tmpPosZ = (new StringBuilder()).append((int)tmpEntity.posZ).toString();

						if (name.equalsIgnoreCase(tmpEntity.getOwnerName()))
						{
							String textMassage = tmpEntity.getEntityDocaString();
							String textMassageName = tmpEntity.getName();
							if (textMassageName == "") { textMassageName = "No Name"; }
							DocaTools.setChatMassageDoca(StatCollector.translateToLocal("doca.chat.message.Infoation") + " " + textMassage + "(" + tempCounter + ":" + textMassageName + ")" + " X: " + tmpPosX + " Y: " + tmpPosY + " Z:" + tmpPosZ);
							tempCounter++;
						}
						if (20 <= tempCounter)
						{
							DocaTools.setChatMassageDoca(StatCollector.translateToLocal("doca.chat.message.InfoationOver"));
							break;
						}
					}
				}
			}
		}
	}
}
