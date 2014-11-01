package mods.doca.core.network;

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

import cpw.mods.fml.common.network.Player;

public class DocaPacketType extends DocaPacketCore
{
	private int tmpID, tmpIndexTexture, tmpCollarColor, tmpMode, tmpModelSize;
	private String tmpName;

	public DocaPacketType() {}

	public DocaPacketType(DocaEntityBase entity)
	{
		this.tmpID = entity.entityId;
		this.tmpIndexTexture = entity.getIndexTexture();
		this.tmpCollarColor = entity.getCollarColor();
		this.tmpMode = entity.getMode();
		this.tmpModelSize = entity.getModelSize();
		this.tmpName = entity.getName();
	}

	@Override
	void writeData(ByteArrayDataOutput data)
	{
		data.writeInt(tmpID);
		data.writeInt(tmpIndexTexture);
		data.writeInt(tmpCollarColor);
		data.writeInt(tmpMode);
		data.writeInt(tmpModelSize);
		data.writeUTF(tmpName);
		if (DocaSet.DebugPaket)
		{
			DocaTools.info("paket ->" +"[0]"+ tmpID +"[1]"+ tmpIndexTexture +"[2]"+ tmpCollarColor +"[3]"+ tmpMode
					+"[4]"+ tmpModelSize +"[5]"+ tmpName);
		}
	}

	@Override
	void readData(ByteArrayDataInput data)
	{
		this.tmpID = data.readInt();
		this.tmpIndexTexture = data.readInt();
		this.tmpCollarColor = data.readInt();
		this.tmpMode = data.readInt();
		this.tmpModelSize = data.readInt();
		this.tmpName = data.readUTF();
	}

	@Override
	void execute(Player player)
	{
		if (player instanceof EntityPlayerMP)
		{
			Entity tmpEntity = ((EntityPlayerMP)player).worldObj.getEntityByID(this.tmpID);

			if (tmpEntity instanceof DocaEntityBase)
			{
				DocaEntityBase tmpDocaEntityBase = (DocaEntityBase)tmpEntity;
				tmpDocaEntityBase.setIndexTexture(this.tmpIndexTexture);
				tmpDocaEntityBase.setCollarColor(this.tmpCollarColor);
				tmpDocaEntityBase.setMode(this.tmpMode);
				tmpDocaEntityBase.setModelSize(this.tmpModelSize);
				tmpDocaEntityBase.setName(this.tmpName);
				((EntityPlayerMP)player).worldObj.markBlockForUpdate((int)tmpDocaEntityBase.posX, (int)tmpDocaEntityBase.posY, (int)tmpDocaEntityBase.posZ);
			}
		}
	}
}