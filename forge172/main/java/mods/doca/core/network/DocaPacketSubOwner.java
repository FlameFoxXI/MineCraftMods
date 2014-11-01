package mods.doca.core.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.World;
import mods.doca.core.DocaSet;
import mods.doca.core.DocaTools;
import mods.doca.entity.DocaEntityBase;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;

public class DocaPacketSubOwner implements IMessage, IMessageHandler<DocaPacketSubOwner, IMessage>
{
	private int tmpID;
	private String tmpSubOwner;

	public DocaPacketSubOwner() {}

	public DocaPacketSubOwner(DocaEntityBase entity)
	{
		this.tmpID = entity.getEntityId();
		this.tmpSubOwner = entity.getSubOwners();
	}

	@Override
	public IMessage onMessage(DocaPacketSubOwner message, MessageContext ctx)
	{
		if (ctx.side == Side.CLIENT && message instanceof DocaPacketSubOwner)
		{
			DocaPacketSubOwner sendMessage = (DocaPacketSubOwner)message;
			
			Entity tmpEntity = Minecraft.getMinecraft().theWorld.getEntityByID(sendMessage.tmpID);
			
			if (DocaSet.DebugPaket)
			{
				DocaTools.info("execute ->" + tmpEntity);
			}
			
			if (tmpEntity instanceof DocaEntityBase)
			{
				DocaEntityBase tmpDocaEntityBase = (DocaEntityBase)tmpEntity;
				tmpDocaEntityBase.setSubOwners(sendMessage.tmpSubOwner);
			}
		}
		return null;
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		this.tmpID = buf.readInt();
		this.tmpSubOwner = ByteBufUtils.readUTF8String(buf);
		if (DocaSet.DebugPaket)
		{
			DocaTools.info("readData ->" + "[0]" + this.tmpID +"[1]" + tmpSubOwner);
		}
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		buf.writeInt(this.tmpID);
		ByteBufUtils.writeUTF8String(buf, this.tmpSubOwner);
		if (DocaSet.DebugPaket)
		{
			DocaTools.info("writeData ->" + "[0]" + this.tmpID +"[1]" + this.tmpSubOwner);
		}
	}
}