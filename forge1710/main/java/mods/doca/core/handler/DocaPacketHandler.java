package mods.doca.core.handler;

import java.util.Map;
import java.util.Map.Entry;

import com.google.common.io.ByteStreams;

import mods.doca.core.DocaSet;
import mods.doca.core.network.DocaPacketKey;
import mods.doca.core.network.DocaPacketOPS;
import mods.doca.core.network.DocaPacketSubOwner;
import mods.doca.core.network.DocaPacketType;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.internal.FMLProxyPacket;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;


public class DocaPacketHandler
{
	public static SimpleNetworkWrapper network = NetworkRegistry.INSTANCE.newSimpleChannel(DocaSet.MODID);

	public static void DocaPacketHandlerInit()
	{
		network.registerMessage(DocaPacketOPS.class, DocaPacketOPS.class, 0, Side.CLIENT);
		network.registerMessage(DocaPacketKey.class, DocaPacketKey.class, 1, Side.SERVER);
		network.registerMessage(DocaPacketSubOwner.class, DocaPacketSubOwner.class, 2, Side.CLIENT);
		network.registerMessage(DocaPacketType.class, DocaPacketType.class, 3, Side.SERVER);
	}
}