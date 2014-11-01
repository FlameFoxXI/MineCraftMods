package mods.doca.core.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import mods.doca.core.DocaSet;
import mods.doca.core.DocaTools;
import mods.doca.entity.DocaEntityBase;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;

public class DocaPacketOPS implements IMessage, IMessageHandler<DocaPacketOPS, IMessage>
{
	int entityId;
	boolean ops;

	public DocaPacketOPS() {}

	public DocaPacketOPS(EntityPlayer entity, boolean ops)
	{
		this.entityId = entity.getEntityId();
		this.ops = ops;
	}

	@Override
	public IMessage onMessage(DocaPacketOPS message, MessageContext ctx)
	{
		if (ctx.side == Side.CLIENT && message instanceof DocaPacketOPS)
		{
			DocaPacketOPS sendMessage = (DocaPacketOPS)message;
			
			if (Minecraft.getMinecraft().thePlayer.getEntityId() == sendMessage.entityId)
			{
				if (DocaSet.DebugPaket)
				{
					DocaTools.info("execute ->" + Minecraft.getMinecraft().thePlayer.getEntityId());
				}
				
				DocaSet.isUserOps = sendMessage.ops;
			}			
		}
		return null;
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		this.entityId = buf.readInt();
		this.ops = buf.readBoolean();
		if (DocaSet.DebugPaket)
		{
			DocaTools.info("readData ->" +"[0]"+ this.entityId +"[1]"+ this.ops);
		}
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		buf.writeInt(this.entityId);
		buf.writeBoolean(this.ops);
		if (DocaSet.DebugPaket)
		{
			DocaTools.info("writeData ->" +"[0]"+ this.entityId +"[1]"+ this.ops);
		}
	}
}
