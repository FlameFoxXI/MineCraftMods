package mods.doca.core.network;

import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.entity.player.EntityPlayer;
import mods.doca.core.DocaSet;
import mods.doca.core.DocaTools;
import mods.doca.entity.DocaEntityBase;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

import cpw.mods.fml.common.network.Player;

public class DocaPacketOPS extends DocaPacketCore
{
	int entityId;
	boolean ops;

	public DocaPacketOPS() {}

	public DocaPacketOPS(EntityPlayer entity, boolean ops)
	{
		this.entityId = entity.entityId;
		this.ops = ops;
	}

	@Override
	void writeData(ByteArrayDataOutput data)
	{
		data.writeInt(this.entityId);
		data.writeBoolean(this.ops);
		if (DocaSet.DebugPaket)
		{
			DocaTools.info("paket ->" +"[0]"+ this.entityId +"[1]"+ this.ops);
		}
	}

	@Override
	void readData(ByteArrayDataInput data)
	{
		entityId = data.readInt();
		ops = data.readBoolean();
	}

	@Override
	void execute(Player player)
	{
		if(player instanceof EntityClientPlayerMP)
		{
			if (((EntityClientPlayerMP)player).entityId == this.entityId)
			{
				DocaSet.isUserOps = this.ops;
			}
		}
	}
}
