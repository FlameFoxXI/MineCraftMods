package mods.doca.core.handler;

import com.google.common.io.*;

import cpw.mods.fml.common.network.*;
import mods.doca.*;
import mods.doca.core.*;
import mods.doca.core.network.DocaPacketCore;
import mods.doca.entity.*;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.ItemStack;
import net.minecraft.network.*;
import net.minecraft.network.packet.*;
import net.minecraft.world.*;

public class DocaPacketHandler implements IPacketHandler
{
	@Override
	public void onPacketData(INetworkManager network, Packet250CustomPayload packet, Player player)
	{
		DocaPacketCore.execute(ByteStreams.newDataInput(packet.data), packet.channel, player);
	}
}