package mods.doca.core.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import mods.doca.core.DocaKeys;
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

public class DocaPacketKey implements IMessage, IMessageHandler<DocaPacketKey, IMessage>
{
	String tmpName;
	boolean keys[] = new boolean[DocaTools.KEY_MAX];

	public DocaPacketKey() {}

	public DocaPacketKey(DocaKeys key)
	{
		this.tmpName = key.getUserName();
		for (int i = DocaTools.KEY_MIN; i < DocaTools.KEY_MAX; i++)
		{
			this.keys[i] = key.getKeys(i);
		}
	}

	@Override
	public IMessage onMessage(DocaPacketKey message, MessageContext ctx)
	{
		if (ctx.side == Side.SERVER && ctx.getServerHandler().playerEntity != null && message instanceof DocaPacketKey)
		{
			DocaPacketKey sendMessage = (DocaPacketKey)message;
			
			DocaKeys tempDocaSet = new DocaKeys(sendMessage.tmpName);
			tempDocaSet.setTimer(DocaTools.KEY_UNLOADTIMER);
			for (int i = 0; i < DocaTools.KEY_MAX; i++)
			{
				tempDocaSet.setKeys(i, sendMessage.keys[i]);
			}
			
			if(DocaSet.DebugPaket)
			{
				String tmp = "";
				for (int i = DocaTools.KEY_MIN; i < DocaTools.KEY_MAX; i++)
				{
					tmp += ("["+(i+1)+"]" + sendMessage.keys[i]);
				}
				DocaTools.info("execute ->" + "[0]" + sendMessage.tmpName + tmp);
			}
		}
		return null;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		this.tmpName = ByteBufUtils.readUTF8String(buf);
		for (int i = 0; i < DocaTools.KEY_MAX; i++)
		{
			this.keys[i] = buf.readBoolean();
		}
		if(DocaSet.DebugPaket)
		{
			String tmp = "";
			for (int i = DocaTools.KEY_MIN; i < keys.length; i++)
			{
				tmp += ("["+(i+1)+"]" + keys[i]);
			}
			DocaTools.info("readData ->" + "[0]" + tmpName + tmp);
		}
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		ByteBufUtils.writeUTF8String(buf, tmpName);
		for (int i = DocaTools.KEY_MIN; i < keys.length; i++)
		{
			buf.writeBoolean(keys[i]);
		}
		if(DocaSet.DebugPaket)
		{
			String tmp = "";
			for (int i = DocaTools.KEY_MIN; i < keys.length; i++)
			{
				tmp += ("["+(i+1)+"]" + keys[i]);
			}
			DocaTools.info("writeData ->" + "[0]" + tmpName + tmp);
		}	}
}
