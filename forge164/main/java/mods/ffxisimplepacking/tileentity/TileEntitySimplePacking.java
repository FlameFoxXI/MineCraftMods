package mods.ffxisimplepacking.tileentity;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.tileentity.TileEntity;

public class TileEntitySimplePacking extends TileEntity
{
	private EntityItem dispEntityItem;
	private ItemStack dispItemStack = null;
	private int typePacking = 0;
	
	public TileEntitySimplePacking()
	{
		this(null, 0);
	}
	
	public TileEntitySimplePacking(ItemStack item, int type)
	{
		super();
		this.dispItemStack = item;
		this.typePacking = type;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.readFromNBT(par1NBTTagCompound);

		if (par1NBTTagCompound.hasKey("SimplePackingItemStack"))
		{
			NBTTagCompound nBTTagCompound = par1NBTTagCompound.getCompoundTag("SimplePackingItemStack");
			this.dispItemStack = ItemStack.loadItemStackFromNBT(nBTTagCompound);
			this.dispEntityItem = this.getEntityDisplayItemStack(this.dispItemStack);
		}
		else
		{
			this.dispItemStack = null;
			this.dispEntityItem = null;
		}
		this.typePacking= par1NBTTagCompound.getInteger("SimplePackingBlockType");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.writeToNBT(par1NBTTagCompound);
		
		NBTTagCompound nBTTagCompound = new NBTTagCompound();

		if (this.dispItemStack != null)
		{
			this.dispItemStack.writeToNBT(nBTTagCompound);
		}
		par1NBTTagCompound.setCompoundTag("SimplePackingItemStack", nBTTagCompound);

		par1NBTTagCompound.setInteger("SimplePackingBlockType", this.typePacking);
	}
	
	@Override
	public void updateEntity()
	{
		super.updateEntity();
		
		if (this.dispEntityItem != null)
		{
			++this.dispEntityItem.age;

			if (Short.MAX_VALUE <= this.dispEntityItem.age)
			{
				this.dispEntityItem.age = 0;
			}
		}
	}
	
	@Override
	public Packet getDescriptionPacket()
	{
		NBTTagCompound nbttagcompound = new NBTTagCompound();
		this.writeToNBT(nbttagcompound);
		return new Packet132TileEntityData(this.xCoord, this.yCoord, this.zCoord, 1, nbttagcompound);
	}

	@Override
	public void onDataPacket(INetworkManager net, Packet132TileEntityData packet)
	{
		this.readFromNBT(packet.data);
	}
	
	public EntityItem getDisplayEntityItem()
	{
		return this.dispEntityItem;
	}

	private EntityItem getEntityDisplayItemStack(ItemStack par1ItemStack)
	{
		if (this.worldObj != null && this.worldObj.isRemote && par1ItemStack != null)
		{
			return (new EntityItem(this.worldObj, (double)this.xCoord + 0.5D, (double)this.yCoord, (double)this.zCoord + 0.5D, par1ItemStack));
		}
		return null;
	}
	
	public void setDispItemStack(ItemStack itemStack)
	{
		this.dispItemStack = itemStack;
	}
	
	public ItemStack getDispItemStack()
	{
		return dispItemStack;
	}
	
	public int getTypePacking()
	{
		return typePacking;
	}
}
