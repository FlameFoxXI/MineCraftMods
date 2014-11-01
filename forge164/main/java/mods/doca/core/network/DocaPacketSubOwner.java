package mods.doca.core.network;

import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import mods.doca.core.DocaSet;
import mods.doca.core.DocaTools;
import mods.doca.entity.DocaEntityBase;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

import cpw.mods.fml.common.network.Player;

public class DocaPacketSubOwner extends DocaPacketCore
{
	private int tmpID;
	private String tmpSubOwner;

	public DocaPacketSubOwner() {}

	public DocaPacketSubOwner(DocaEntityBase entity)
	{
		this.tmpID = entity.entityId;
		this.tmpSubOwner = entity.getSubOwners();
	}

	@Override
	void writeData(ByteArrayDataOutput data)
	{
		data.writeInt(tmpID);
		data.writeUTF(tmpSubOwner);
		if (DocaSet.DebugPaket)
		{
			DocaTools.info("paket ->" + "[0]" + tmpID +"[1]" + tmpSubOwner);
		}
	}

	@Override
	void readData(ByteArrayDataInput data)
	{
		this.tmpID = data.readInt();
		this.tmpSubOwner = data.readUTF();


	}

	@Override
	void execute(Player player)
	{
		if (player instanceof EntityClientPlayerMP)
		{
			Entity tmpEntity = ((EntityClientPlayerMP)player).worldObj.getEntityByID(this.tmpID);

			if (tmpEntity instanceof DocaEntityBase)
			{
				DocaEntityBase tmpDocaEntityBase = (DocaEntityBase)tmpEntity;
				tmpDocaEntityBase.setSubOwners(this.tmpSubOwner);
			}
		}
	}
}