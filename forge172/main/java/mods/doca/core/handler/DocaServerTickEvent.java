package mods.doca.core.handler;

import mods.doca.core.*;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.ServerTickEvent;

public class DocaServerTickEvent
{
	@SubscribeEvent
	public void onServerTickEvent(ServerTickEvent event)
	{
		this.onTickInGame();
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
