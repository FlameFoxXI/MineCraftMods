package mods.doca.core.handler;

import mods.doca.DocaMod;
import mods.doca.core.DocaTools;
import mods.doca.core.network.DocaPacketOPS;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import cpw.mods.fml.common.IPlayerTracker;

public class DocaPlayerHandler implements IPlayerTracker
{

	@Override
	public void onPlayerLogin(EntityPlayer player)
	{
		if (player instanceof EntityPlayerMP && DocaTools.getModProxy().getPermissionAccessInventory())
		{
			new DocaPacketOPS(player, DocaTools.getModProxy().getOps(player)).sendToPlayer(player);
		}
	}

	@Override
	public void onPlayerLogout(EntityPlayer player)
	{
	}

	@Override
	public void onPlayerChangedDimension(EntityPlayer player)
	{
	}

	@Override
	public void onPlayerRespawn(EntityPlayer player)
	{
	}

}
