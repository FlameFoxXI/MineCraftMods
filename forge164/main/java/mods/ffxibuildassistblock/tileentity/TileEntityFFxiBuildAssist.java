package mods.ffxibuildassistblock.tileentity;

import mods.ffxibuildassistblock.block.BlockFFxiBuildAssist;
import mods.ffxibuildassistblock.block.BlockFFxiBuildAssistCore;
import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class TileEntityFFxiBuildAssist extends TileEntity
{
	protected final int[] blockSerachTableX = {  0,  0, -1,  1,  0,  0 };
	protected final int[] blockSerachTableY = {  0, -1,  0,  0,  1,  0 };
	protected final int[] blockSerachTableZ = { -1,  0,  0,  0,  0,  1 };
	protected final int[] blockSerachTableS = { -1,  1 };
	
	private boolean canBloken;

	public TileEntityFFxiBuildAssist()
	{
		this.canBloken = false;
	}
	
	public void updateEntity()
	{
		if (this.isBloken() && !this.worldObj.isRemote)
		{
			BlockFFxiBuildAssistCore.tryToChainBreaken(this.worldObj, this.xCoord, this.yCoord, this.zCoord, this.getBlockMetadata());
			BlockFFxiBuildAssistCore.tryToBreaken(this.worldObj, this.xCoord, this.yCoord, this.zCoord);
		}
	}
	
	@Override
	public void readFromNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.readFromNBT(par1NBTTagCompound);
		this.setBloken(par1NBTTagCompound.getBoolean("canBloken"));
	}

	@Override
	public void writeToNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.writeToNBT(par1NBTTagCompound);
		par1NBTTagCompound.setBoolean("canBloken", this.isBloken());
	}

	@Override
	public Packet getDescriptionPacket() {
		NBTTagCompound nbttagcompound = new NBTTagCompound();
		this.writeToNBT(nbttagcompound);
		return new Packet132TileEntityData(this.xCoord, this.yCoord, this.zCoord, 1, nbttagcompound);
	}

	@Override
	public void onDataPacket(INetworkManager net, Packet132TileEntityData packet) {
		this.readFromNBT(packet.data);
	}

	public boolean isBloken()
	{
		return this.canBloken;
	}

	public void setBloken(boolean canBloken)
	{
		this.canBloken = canBloken;
	}
}
