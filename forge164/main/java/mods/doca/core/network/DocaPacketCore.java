package mods.doca.core.network;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import cpw.mods.fml.relauncher.Side;
import mods.doca.core.DocaSet;
import mods.doca.core.DocaTools;
import mods.doca.entity.*;
import mods.doca.entity.func.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.NetServerHandler;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.ServerConfigurationManager;

public abstract class DocaPacketCore
{
	private static Map<String, Class<? extends DocaPacketCore>> idToClassMap = new HashMap<String, Class<? extends DocaPacketCore>>();
	private static Map<Class<? extends DocaPacketCore>, String> classToIdMap = new HashMap<Class<? extends DocaPacketCore>, String>();

	private static void addMapping(String id, Class<? extends DocaPacketCore> clazz) {
		idToClassMap.put(id, clazz);
		classToIdMap.put(clazz, id);
	}
	
	static {
		addMapping(DocaTools.PACKET_OPS_CHECK, DocaPacketOPS.class);
		addMapping(DocaTools.PACKET_KEY_CHECK, DocaPacketKey.class);
		addMapping(DocaTools.PACKET_SOWN_DATA, DocaPacketSubOwner.class);
		addMapping(DocaTools.PACKET_TYPE_DATA, DocaPacketType.class);
	}
	
	public final Packet generatePacket()
	{
		ByteArrayDataOutput data = ByteStreams.newDataOutput();

		if (!classToIdMap.containsKey(getClass()))
		{
			DocaTools.wrning("Packet " + getClass() + " is missing.");
			return null;
		}

		String id = classToIdMap.get(getClass());
		
		if (DocaSet.DebugPaket)
		{
			DocaTools.info("********************************************************");
			DocaTools.info("send paket to :" + id);
		}

		writeData(data);
		
		return new Packet250CustomPayload(id, data.toByteArray());
	}
	
	abstract void writeData(ByteArrayDataOutput data);
	
	abstract void readData(ByteArrayDataInput data);
	
	abstract void execute(Player player);
	
	public final void sendToServer()
	{
		Side side = FMLCommonHandler.instance().getEffectiveSide();
		if(side == Side.CLIENT)
		{
			Minecraft.getMinecraft().getNetHandler().addToSendQueue(generatePacket());
		}
	}

	public final void sendToPlayer(EntityPlayer player)
	{
		if(player instanceof EntityPlayerMP)
		{
			EntityPlayerMP tmpEntityPlayerMP = (EntityPlayerMP)player;
			tmpEntityPlayerMP.playerNetServerHandler.sendPacketToPlayer(generatePacket());
		}
	}

	public static void execute(ByteArrayDataInput input, String channel, Player player)
	{
		Class<? extends DocaPacketCore> packetClass = idToClassMap.get(channel);
		
		if (packetClass == null)
		{
			DocaTools.wrning("DocaPacketCore execute" + channel);
		}
		else
		{
			try
			{
				DocaPacketCore parsedPacket = packetClass.newInstance();
				parsedPacket.readData(input);
				parsedPacket.execute(player);
				
				if (DocaSet.DebugPaket)
				{
					DocaTools.info("receive paket : " + channel);
				}
			}
			catch (Exception e)
			{
				DocaTools.wrning("DocaPacketCore execute: " + e.getClass().getSimpleName() + " (" + e.getMessage() + ")");
				e.printStackTrace();
			}
		}
	}

}
