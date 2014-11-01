package mods.doca.core.network;

import io.netty.buffer.ByteBuf;
import mods.doca.core.DocaSet;
import mods.doca.core.DocaTools;
import mods.doca.entity.DocaEntityBase;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.World;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;

public class DocaPacketType implements IMessage, IMessageHandler<DocaPacketType, IMessage>
{
	private int tmpID, tmpIndexTexture, tmpCollarColor, tmpMode, tmpModelSize;
	private String tmpName;

	public DocaPacketType() {}

	public DocaPacketType(DocaEntityBase entity)
	{
		this.tmpID = entity.getEntityId();
		this.tmpIndexTexture = entity.getIndexTexture();
		this.tmpCollarColor = entity.getCollarColor();
		this.tmpMode = entity.getMode();
		this.tmpModelSize = entity.getModelSize();
		this.tmpName = entity.getName();
	}

	@Override
	public IMessage onMessage(DocaPacketType message, MessageContext ctx)
	{
		if (ctx.side == Side.SERVER && ctx.getServerHandler().playerEntity != null &&  message instanceof DocaPacketType)
		{
			DocaPacketType sendMessage = (DocaPacketType)message;

			
			Entity tmpEntity = ctx.getServerHandler().playerEntity.worldObj.getEntityByID(sendMessage.tmpID);

			if (DocaSet.DebugPaket)
			{
				DocaTools.info("execute ->" + tmpEntity);
			}

			if (tmpEntity instanceof DocaEntityBase)
			{
				DocaEntityBase tmpDocaEntityBase = (DocaEntityBase)tmpEntity;
				tmpDocaEntityBase.setIndexTexture(sendMessage.tmpIndexTexture);
				tmpDocaEntityBase.setCollarColor(sendMessage.tmpCollarColor);
				tmpDocaEntityBase.setMode(sendMessage.tmpMode);
				tmpDocaEntityBase.setModelSize(sendMessage.tmpModelSize);
				tmpDocaEntityBase.setName(sendMessage.tmpName);
				ctx.getServerHandler().playerEntity.worldObj.markBlockForUpdate((int)tmpDocaEntityBase.posX, (int)tmpDocaEntityBase.posY, (int)tmpDocaEntityBase.posZ);
			}
		}
		return null;
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		this.tmpID = buf.readInt();
		this.tmpIndexTexture = buf.readInt();
		this.tmpCollarColor = buf.readInt();
		this.tmpMode = buf.readInt();
		this.tmpModelSize = buf.readInt();
		this.tmpName = ByteBufUtils.readUTF8String(buf);
		if (DocaSet.DebugPaket)
		{
			DocaTools.info("paket ->" +"[0]"+ tmpID +"[1]"+ tmpIndexTexture +"[2]"+ tmpCollarColor +"[3]"+ tmpMode
					+"[4]"+ tmpModelSize +"[5]"+ tmpName);
		}
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		buf.writeInt(this.tmpID);
		buf.writeInt(this.tmpIndexTexture);
		buf.writeInt(this.tmpCollarColor);
		buf.writeInt(this.tmpMode);
		buf.writeInt(this.tmpModelSize);
		ByteBufUtils.writeUTF8String(buf, this.tmpName);
		if (DocaSet.DebugPaket)
		{
			DocaTools.info("paket ->" +"[0]"+ tmpID +"[1]"+ tmpIndexTexture +"[2]"+ tmpCollarColor +"[3]"+ tmpMode
					+"[4]"+ tmpModelSize +"[5]"+ tmpName);
		}
	}
}