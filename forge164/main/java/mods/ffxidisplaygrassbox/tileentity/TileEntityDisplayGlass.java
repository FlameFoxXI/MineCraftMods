package mods.ffxidisplaygrassbox.tileentity;

import mods.ffxidisplaygrassbox.FFxiDisplayGlassMod;

import java.util.Random;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;


public class TileEntityDisplayGlass extends TileEntity implements IInventory
{
	private EntityItem dispEntityItem;
	private ItemStack[] chestContents = new ItemStack[54];
	private String customName;
	private ItemStack dispItemStack = null;
	public int numUsingPlayers;
	private int ticksSinceSync;

	@Override
	public int getSizeInventory()
	{
		return 45;
	}

	@Override
	public ItemStack getStackInSlot(int par1)
	{
		return this.chestContents[par1];
	}

	@Override
	public ItemStack decrStackSize(int par1, int par2)
	{
		if (this.chestContents[par1] != null)
		{
			ItemStack itemStack1;

			if (this.chestContents[par1].stackSize <= par2)
			{
				itemStack1 = this.chestContents[par1];
				this.chestContents[par1] = null;
				this.onInventoryChanged();
				return itemStack1;
			}
			else
			{
				itemStack1 = this.chestContents[par1].splitStack(par2);

				if (this.chestContents[par1].stackSize == 0)
				{
					this.chestContents[par1] = null;
				}

				this.onInventoryChanged();
				return itemStack1;
			}
		}
		else
		{
			return null;
		}
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int par1)
	{
		if (this.chestContents[par1] != null)
		{
			ItemStack itemStack = this.chestContents[par1];
			this.chestContents[par1] = null;
			return itemStack;
		}
		else
		{
			return null;
		}
	}

	@Override
	public void setInventorySlotContents(int par1, ItemStack par2ItemStack)
	{
		this.chestContents[par1] = par2ItemStack;

		if (par2ItemStack != null && par2ItemStack.stackSize > this.getInventoryStackLimit())
		{
			par2ItemStack.stackSize = this.getInventoryStackLimit();
		}

		this.onInventoryChanged();
	}

	@Override
	public String getInvName()
	{
		return this.isInvNameLocalized() ? this.customName : "container.glassBox";
	}

	@Override
	public boolean isInvNameLocalized()
	{
		return this.customName != null && this.customName.length() > 0;
	}

	public void setChestGuiName(String par1Str)
	{
		this.customName = par1Str;
	}

	@Override
	public void readFromNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.readFromNBT(par1NBTTagCompound);
		NBTTagList nbttaglist = par1NBTTagCompound.getTagList("Items");
		this.chestContents = new ItemStack[this.getSizeInventory()];

		for (int i = 0; i < nbttaglist.tagCount(); ++i)
		{
			NBTTagCompound nbttagcompound = (NBTTagCompound)nbttaglist.tagAt(i);
			int j = nbttagcompound.getByte("Slot") & 255;

			if (j >= 0 && j < this.chestContents.length)
			{
				this.chestContents[j] = ItemStack.loadItemStackFromNBT(nbttagcompound);
			}
		}

		if (par1NBTTagCompound.hasKey("CustomName"))
		{
			this.customName = par1NBTTagCompound.getString("CustomName");
		}

		if (par1NBTTagCompound.hasKey("DispItemStack"))
		{
			NBTTagCompound nBTTagCompound = par1NBTTagCompound.getCompoundTag("DispItemStack");
			this.dispItemStack = ItemStack.loadItemStackFromNBT(nBTTagCompound);
			this.dispEntityItem = this.getEntityDisplayItemStack(this.dispItemStack);
		}
		else
		{
			this.dispItemStack = null;
			this.dispEntityItem = null;
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.writeToNBT(par1NBTTagCompound);
		NBTTagList nbttaglist = new NBTTagList();

		for (int i = 0; i < this.chestContents.length; ++i)
		{
			if (this.chestContents[i] != null)
			{
				NBTTagCompound tagCompound = new NBTTagCompound();
				tagCompound.setByte("Slot", (byte)i);
				this.chestContents[i].writeToNBT(tagCompound);
				nbttaglist.appendTag(tagCompound);
			}
		}

		par1NBTTagCompound.setTag("Items", nbttaglist);

		if (this.isInvNameLocalized())
		{
			par1NBTTagCompound.setString("CustomName", this.customName);
		}

		if (this.dispItemStack != null)
		{
			NBTTagCompound nBTTagCompound = new NBTTagCompound();
			this.dispItemStack.writeToNBT(nBTTagCompound);
			par1NBTTagCompound.setCompoundTag("DispItemStack", nBTTagCompound);
		}
	}

	@Override
	public int getInventoryStackLimit()
	{
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer par1EntityPlayer)
	{
		return true;
	}

	@Override
	public void updateContainingBlockInfo()
	{
		super.updateContainingBlockInfo();
	}

	@Override
	public void updateEntity()
	{
		super.updateEntity();

		if (++this.ticksSinceSync % 20 * 4 == 0)
		{
			this.worldObj.addBlockEvent(this.xCoord, this.yCoord, this.zCoord, FFxiDisplayGlassMod.blockDisplayGlass.blockID, 1, this.numUsingPlayers);
		}
		
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
	public boolean receiveClientEvent(int par1, int par2)
	{
		if (par1 == 1)
		{
			this.numUsingPlayers = par2;
			return true;
		}
		else
		{
			return super.receiveClientEvent(par1, par2);
		}
	}

	@Override
	public void openChest()
	{
		++this.numUsingPlayers;
		this.worldObj.addBlockEvent(this.xCoord, this.yCoord, this.zCoord, FFxiDisplayGlassMod.blockDisplayGlass.blockID, 1, this.numUsingPlayers);
		this.worldObj.playSoundEffect((double)this.xCoord + 0.5D, (double)this.yCoord + 0.5D, (double)this.zCoord + 0.5D, "random.chestopen", 0.5F, this.worldObj.rand.nextFloat() * 0.1F + 0.9F);
	}

	@Override
	public void closeChest()
	{
		--this.numUsingPlayers;
		this.worldObj.addBlockEvent(this.xCoord, this.yCoord, this.zCoord, FFxiDisplayGlassMod.blockDisplayGlass.blockID, 1, this.numUsingPlayers);
		this.worldObj.playSoundEffect((double)this.xCoord + 0.5D, (double)this.yCoord + 0.5D, (double)this.zCoord + 0.5D, "random.chestopen", 0.5F, this.worldObj.rand.nextFloat() * 0.1F + 0.9F);
	}

	@Override
	public boolean isItemValidForSlot(int par1, ItemStack par2ItemStack)
	{
		return true;
	}

	@Override
	public void invalidate()
	{
		this.updateContainingBlockInfo();
		super.invalidate();
	}

	@Override
	public void onInventoryChanged()
	{
		if (this.worldObj != null)
		{
			this.dispItemStack = this.getDisplayItemStack();
			this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
		}
		super.onInventoryChanged();
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
		if ((this.worldObj != null) && (this.worldObj.isRemote) && (par1ItemStack != null))
		{
			if (!FFxiDisplayGlassMod.blockDisplayStacks)
			{
				par1ItemStack.stackSize = 1;
			}
			return (new EntityItem(this.worldObj, (double)this.xCoord + 0.5D, (double)this.yCoord + 0.4D, (double)this.zCoord + 0.5D, par1ItemStack));
		}
		return null;
	}

	private ItemStack getDisplayItemStack()
	{
		for (int i = 0; i < this.getSizeInventory(); ++i)
		{
			if (this.getStackInSlot(i) != null)
			{
				return this.getStackInSlot(i).copy();
			}
		}
		return null;
	}
}
