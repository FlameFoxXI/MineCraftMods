package mods.doca.core.handler;

import mods.doca.DocaMod;
import mods.doca.core.DocaTools;
import mods.doca.core.network.DocaPacketOPS;
import mods.doca.core.network.DocaPacketSubOwner;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;

public class DocaPlayerHandler
{
	@SubscribeEvent
	public void onPlayerLogin(PlayerLoggedInEvent event)
	{
		if (event.player instanceof EntityPlayerMP && DocaTools.getModProxy().getPermissionAccessInventory())
		{
			DocaPacketHandler.network.sendTo(new DocaPacketOPS(event.player, DocaTools.getModProxy().getOps(event.player)), (EntityPlayerMP)event.player);
//			DocaPacketCore.sendToPlayer(new DocaPacketOPS(event.player, DocaTools.getModProxy().getOps(event.player)), event.player);
		}
	}
}
