package mods.doca.core.network;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import mods.doca.core.DocaKeys;
import mods.doca.core.DocaSet;
import mods.doca.core.DocaTools;
import mods.doca.entity.DocaEntityBase;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

import cpw.mods.fml.common.network.Player;

public class DocaPacketKey extends DocaPacketCore
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
	void writeData(ByteArrayDataOutput data)
	{
		data.writeUTF(tmpName);
		for (int i = DocaTools.KEY_MIN; i < keys.length; i++)
		{
			data.writeBoolean(keys[i]);
		}
		if(DocaSet.DebugPaket)
		{
			String tmp = "";
			for (int i = DocaTools.KEY_MIN; i < keys.length; i++)
			{
				tmp += ("["+(i+1)+"]" + keys[i]);
			}
			DocaTools.info("paket ->" + "[0]" + tmpName + tmp);
		}
	}

	@Override
	void readData(ByteArrayDataInput data)
	{
		this.tmpName = data.readUTF();
		for (int i = 0; i < DocaTools.KEY_MAX; i++)
		{
			this.keys[i] = data.readBoolean();
		}
	}

	@Override
	void execute(Player player)
	{
		if (player instanceof EntityPlayerMP)
		{
			DocaKeys tempDocaSet = new DocaKeys(this.tmpName);
			tempDocaSet.setTimer(DocaTools.KEY_UNLOADTIMER);
			for (int i = 0; i < DocaTools.KEY_MAX; i++)
			{
				tempDocaSet.setKeys(i, this.keys[i]);
			}
		}
	}
}
