package mods.doca.core.handler;

import java.util.EnumSet;
import java.util.List;

import org.lwjgl.input.Keyboard;

import mods.doca.*;
import mods.doca.core.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;

public class DocaTickInGameServer implements ITickHandler
{
	@Override
	public void tickStart(EnumSet<TickType> type, Object... tickData) {}

	@Override
	public void tickEnd(EnumSet<TickType> type, Object... tickData)
	{
		if (type.equals(EnumSet.of(TickType.SERVER)))
		{
			onTickInGame();
		}
	}

	@Override
	public EnumSet<TickType> ticks()
	{
		return EnumSet.of(TickType.RENDER, TickType.SERVER);
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

		if (DocaSet.keySeverDocaTickTimer <= 0)
		{
			if (!DocaSet.keySeverDoca.isEmpty())
			{
				for (int i = 0; i < DocaSet.keySeverDoca.size(); i++)
				{
					if (DocaSet.keySeverDoca.get(i).getTimer() != 0)
					{
						DocaSet.keySeverDoca.get(i).setTimer(DocaSet.keySeverDoca.get(i).getTimer() - 1);
					}
					else
					{
						DocaSet.keySeverDoca.remove(i);
					}
				}
			}

			DocaSet.keySeverDocaTickTimer = DocaSet.keySeverDocaTickTimerMAX;
		}
		else
		{
			DocaSet.keySeverDocaTickTimer--;
		}


		if (DocaSet.copyToInventoryCheck)
		{
			if (DocaSet.copyToInventoryCount == 0)
			{
				DocaSet.copyToInventoryCheck = false;
				DocaSet.copyToInventoryCount = 0;
			}
			else
			{
				DocaSet.copyToInventoryCount--;
			}
		}
	}
}
