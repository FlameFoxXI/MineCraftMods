package mods.doca.inventory;

import mods.doca.entity.*;

import net.minecraft.block.*;
import net.minecraft.crash.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.util.*;

public class DocaInventory implements IInventory
{

	public static final int HotbarSize = 9;
	public static final int HotBarLine = 6;
	public static final int MainInventorySize = HotbarSize * HotBarLine;
	public static final int CurrentInventorySize = 1;
	public static final int FoodsInventorySize = 3;
	public static final int FuncInventorySize = 1;
	public static final int ArmorInventorySize = 4;

	public static final int MainInventoryMin = 0;
	public static final int MainInventoryMax = MainInventorySize;

	public static final int ArmorInventoryMin = 0;
	public static final int ArmorInventoryMax = ArmorInventorySize;

	public static final int CurrentInventoryMin = MainInventorySize;
	public static final int CurrentInventoryMax = MainInventorySize + CurrentInventorySize;

	public static final int FoodsInventoryMin = MainInventorySize + CurrentInventorySize;
	public static final int FoodsInventoryMax = MainInventorySize + CurrentInventorySize + FoodsInventorySize;

	public static final int FuncInventoryMin = MainInventorySize + CurrentInventorySize + FoodsInventorySize;
	public static final int FuncInventoryMax = MainInventorySize + CurrentInventorySize + FoodsInventorySize + FuncInventorySize;

	public static final int MainInventoryTableSizeMax = MainInventorySize + CurrentInventorySize + FoodsInventorySize + FuncInventorySize;
	public static final int ArmorInventoryTableSizeMax = ArmorInventorySize;
	public static final int ALLInventoryTableSizeMax = MainInventoryTableSizeMax + ArmorInventoryTableSizeMax;

	public ItemStack[] mainInventory = new ItemStack[MainInventoryTableSizeMax];
	public ItemStack[] armorInventory = new ItemStack[ArmorInventoryTableSizeMax];
	public int currentItem;
	public int foodsItem[] = new int[FoodsInventorySize];
	public int functionItem;
	private ItemStack currentItemStack;
	public DocaEntityCore theBase;
	private ItemStack itemStack;
	public boolean inventoryChanged;

	public DocaInventory(DocaEntityCore docaEntityCore)
	{
		this.theBase = docaEntityCore;
		this.currentItem = CurrentInventoryMin;
		this.functionItem = FuncInventoryMin;
		for (int i = 0; i < FoodsInventorySize; i++){
			this.foodsItem[i] = FoodsInventoryMin + i;
		}
	}

	public ItemStack getCurrentItem()
	{
		return this.currentItem < CurrentInventoryMax && this.currentItem >= CurrentInventoryMin ? this.mainInventory[this.currentItem] : null;
	}

	public void updateCurrentItem(int no, ItemStack itemstack)
	{
		if (no < CurrentInventoryMax && no >= CurrentInventoryMin)
		{
			if (itemstack.stackSize <= itemstack.getMaxStackSize())
			{
				this.mainInventory[no] = itemstack;
			}
			else
			{
				itemstack.stackSize = itemstack.getMaxStackSize();
				this.mainInventory[no] = itemstack;
			}

		}
	}

	public ItemStack getFoodsItem(int par1)
	{
		return (par1 < FoodsInventorySize) ? this.foodsItem[par1] < FoodsInventoryMax && this.foodsItem[par1] >= FoodsInventoryMin ? this.mainInventory[foodsItem[par1]] : null : null;
	}

	public ItemStack getFunctionItem()
	{
		return this.functionItem < FuncInventoryMax && this.functionItem >= FuncInventoryMin ? this.mainInventory[this.functionItem] : null;
	}


	public int getMainInventoryItemSize()
	{
		return MainInventorySize;
	}

	public int getFoodsItemSize()
	{
		return FoodsInventorySize;
	}

	public int getFunctionItemSize()
	{
		return FuncInventoryMax;
	}

	public static int getHotbarSize()
	{
		return HotbarSize;
	}

	private int getInventorySlotContainItem(int par1)
	{
		for (int var2 = 0; var2 < this.mainInventory.length; ++var2)
		{
			if (this.mainInventory[var2] != null && this.mainInventory[var2].itemID == par1)
			{
				return var2;
			}
		}

		return -1;
	}

	private int getInventorySlotContainItemAndDamage(int par1, int par2)
	{
		for (int var3 = 0; var3 < this.mainInventory.length; ++var3)
		{
			if (this.mainInventory[var3] != null && this.mainInventory[var3].itemID == par1 && this.mainInventory[var3].getItemDamage() == par2)
			{
				return var3;
			}
		}

		return -1;
	}

	private int storeItemStack(ItemStack par1ItemStack)
	{
		for (int var2 = 0; var2 < MainInventorySize; ++var2)
		{
			if (this.mainInventory[var2] != null && this.mainInventory[var2].itemID == par1ItemStack.itemID && this.mainInventory[var2].isStackable() && this.mainInventory[var2].stackSize < this.mainInventory[var2].getMaxStackSize() && this.mainInventory[var2].stackSize < this.getInventoryStackLimit() && (!this.mainInventory[var2].getHasSubtypes() || this.mainInventory[var2].getItemDamage() == par1ItemStack.getItemDamage()) && ItemStack.areItemStackTagsEqual(this.mainInventory[var2], par1ItemStack))
			{
				return var2;
			}
		}

		return -1;
	}

	public int getFirstEmptyStack()
	{
		for (int var1 = 0; var1 < MainInventorySize; ++var1)
		{
			if (this.mainInventory[var1] == null)
			{
				return var1;
			}
		}

		return -1;
	}

	public int getCurrentFirstEmptyStack()
	{
		for (int var1 = CurrentInventoryMin; var1 < CurrentInventoryMax; ++var1)
		{
			if (this.mainInventory[var1] == null)
			{
				return var1;
			}
		}

		return -1;
	}
	public void setCurrentItem(int par1, int par2, boolean par3, boolean par4)
	{
		boolean var5 = true;
		this.currentItemStack = this.getCurrentItem();
		int var7;

		if (par3)
		{
			var7 = this.getInventorySlotContainItemAndDamage(par1, par2);
		}
		else
		{
			var7 = this.getInventorySlotContainItem(par1);
		}

		if (var7 >= CurrentInventoryMin && var7 < CurrentInventoryMax)
		{
			this.currentItem = var7;
		}
		else
		{
			if (par4 && par1 > 0)
			{
				int var6 = this.getCurrentFirstEmptyStack();

				if (var6 >= CurrentInventoryMin && var6 < CurrentInventoryMax)
				{
					this.currentItem = var6;
				}

				this.func_70439_a(Item.itemsList[par1], par2);
			}
		}
	}

	public void changeCurrentItem(int par1)
	{
		if (par1 > 0)
		{
			par1 = 1;
		}

		if (par1 < 0)
		{
			par1 = -1;
		}

		for (this.currentItem -= par1; this.currentItem < CurrentInventoryMin; this.currentItem += CurrentInventoryMax)
		{
			;
		}

		while (this.currentItem >= CurrentInventoryMax)
		{
			this.currentItem -= CurrentInventoryMax;
		}
	}

	public int clearInventory(int par1, int par2)
	{
		int k = 0;
		int l;
		ItemStack itemstack;

		for (l = 0; l < this.mainInventory.length; ++l)
		{
			itemstack = this.mainInventory[l];

			if (itemstack != null && (par1 <= -1 || itemstack.itemID == par1) && (par2 <= -1 || itemstack.getItemDamage() == par2))
			{
				k += itemstack.stackSize;
				this.mainInventory[l] = null;
			}
		}

		for (l = 0; l < this.armorInventory.length; ++l)
		{
			itemstack = this.armorInventory[l];

			if (itemstack != null && (par1 <= -1 || itemstack.itemID == par1) && (par2 <= -1 || itemstack.getItemDamage() == par2))
			{
				k += itemstack.stackSize;
				this.armorInventory[l] = null;
			}
		}

		if (this.itemStack != null)
		{
			if (par1 > -1 && this.itemStack.itemID != par1)
			{
				return k;
			}

			if (par2 > -1 && this.itemStack.getItemDamage() != par2)
			{
				return k;
			}

			k += this.itemStack.stackSize;
			this.setItemStack((ItemStack)null);
		}

		return k;
	}

	public void func_70439_a(Item par1Item, int par2)
	{
		if (par1Item != null)
		{
			if (this.currentItemStack != null && this.currentItemStack.isItemEnchantable() && this.getInventorySlotContainItemAndDamage(this.currentItemStack.itemID, this.currentItemStack.getItemDamageForDisplay()) == this.currentItem)
			{
				return;
			}

			int j = this.getInventorySlotContainItemAndDamage(par1Item.itemID, par2);

			if (j >= 0)
			{
				int k = this.mainInventory[j].stackSize;
				this.mainInventory[j] = this.mainInventory[this.currentItem];
				this.mainInventory[this.currentItem] = new ItemStack(Item.itemsList[par1Item.itemID], k, par2);
			}
			else
			{
				this.mainInventory[this.currentItem] = new ItemStack(Item.itemsList[par1Item.itemID], 1, par2);
			}
		}
	}

	private int storePartialItemStack(ItemStack par1ItemStack)
	{
		int var2 = par1ItemStack.itemID;
		int var3 = par1ItemStack.stackSize;
		int var4;

		if (par1ItemStack.getMaxStackSize() == 1)
		{
			var4 = this.getFirstEmptyStack();

			if (var4 < 0)
			{
				return var3;
			}
			else
			{
				if (this.mainInventory[var4] == null)
				{
					this.mainInventory[var4] = ItemStack.copyItemStack(par1ItemStack);
				}

				return 0;
			}
		}
		else
		{
			var4 = this.storeItemStack(par1ItemStack);

			if (var4 < 0)
			{
				var4 = this.getFirstEmptyStack();
			}

			if (var4 < 0)
			{
				return var3;
			}
			else
			{
				if (this.mainInventory[var4] == null)
				{
					this.mainInventory[var4] = new ItemStack(var2, 0, par1ItemStack.getItemDamage());

					if (par1ItemStack.hasTagCompound())
					{
						this.mainInventory[var4].setTagCompound((NBTTagCompound)par1ItemStack.getTagCompound().copy());
					}
				}

				int var5 = var3;

				if (var3 > this.mainInventory[var4].getMaxStackSize() - this.mainInventory[var4].stackSize)
				{
					var5 = this.mainInventory[var4].getMaxStackSize() - this.mainInventory[var4].stackSize;
				}

				if (var5 > this.getInventoryStackLimit() - this.mainInventory[var4].stackSize)
				{
					var5 = this.getInventoryStackLimit() - this.mainInventory[var4].stackSize;
				}

				if (var5 == 0)
				{
					return var3;
				}
				else
				{
					var3 -= var5;
					this.mainInventory[var4].stackSize += var5;
					this.mainInventory[var4].animationsToGo = 5;
					return var3;
				}
			}
		}
	}

	public void decrementAnimations()
	{
		for (int var1 = 0; var1 < this.mainInventory.length; ++var1)
		{
			if (this.mainInventory[var1] != null)
			{
				this.mainInventory[var1].updateAnimation(this.theBase.worldObj, this.theBase, var1, this.currentItem == var1);
			}
		}
	}

	public boolean consumeInventoryItem(int par1)
	{
		int var2 = this.getInventorySlotContainItem(par1);

		if (var2 < 0)
		{
			return false;
		}
		else
		{
			if (--this.mainInventory[var2].stackSize <= 0)
			{
				this.mainInventory[var2] = null;
			}

			return true;
		}
	}

	public boolean hasItem(int par1)
	{
		int var2 = this.getInventorySlotContainItem(par1);
		return var2 >= 0;
	}

	public boolean addItemStackToInventory(ItemStack par1ItemStack)
	{
		if (par1ItemStack == null)
		{
			return false;
		}
		else if (par1ItemStack.stackSize == 0)
		{
			return false;
		}
		else
		{
			try
			{
				int var2;

				if (par1ItemStack.isItemDamaged())
				{
					var2 = this.getFirstEmptyStack();

					if (var2 >= 0)
					{
						this.mainInventory[var2] = ItemStack.copyItemStack(par1ItemStack);
						this.mainInventory[var2].animationsToGo = 5;
						par1ItemStack.stackSize = 0;
						return true;
					}
					else
					{
						return false;
					}
				}
				else
				{
					do
					{
						var2 = par1ItemStack.stackSize;
						par1ItemStack.stackSize = this.storePartialItemStack(par1ItemStack);
					}
					while (par1ItemStack.stackSize > 0 && par1ItemStack.stackSize < var2);

					if (par1ItemStack.stackSize == var2)// && this.player.capabilities.isCreativeMode)
						{
						par1ItemStack.stackSize = 0;
						return true;
						}
					else
					{
						return par1ItemStack.stackSize < var2;
					}
				}
			}
			catch (Throwable var5)
			{
				CrashReport var3 = CrashReport.makeCrashReport(var5, "Adding item to inventory");
				throw new ReportedException(var3);
			}
		}
	}

	@Override
	public ItemStack decrStackSize(int par1, int par2)
	{
		ItemStack[] var3 = this.mainInventory;

		if (par1 >= this.mainInventory.length)
		{
			var3 = this.armorInventory;
			par1 -= this.mainInventory.length;
		}

		if (var3[par1] != null)
		{
			ItemStack var4;

			if (var3[par1].stackSize <= par2)
			{
				var4 = var3[par1];
				var3[par1] = null;
				return var4;
			}
			else
			{
				var4 = var3[par1].splitStack(par2);

				if (var3[par1].stackSize == 0)
				{
					var3[par1] = null;
				}

				return var4;
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
		ItemStack[] var2 = this.mainInventory;

		if (par1 >= this.mainInventory.length)
		{
			var2 = this.armorInventory;
			par1 -= this.mainInventory.length;
		}

		if (var2[par1] != null)
		{
			ItemStack var3 = var2[par1];
			var2[par1] = null;
			return var3;
		}
		else
		{
			return null;
		}
	}

	@Override
	public void setInventorySlotContents(int par1, ItemStack par2ItemStack)
	{
		ItemStack[] var3 = this.mainInventory;

		if (par1 >= var3.length)
		{
			par1 -= var3.length;
			var3 = this.armorInventory;
		}

		var3[par1] = par2ItemStack;
	}

	public float getStrVsBlock(Block par1Block)
	{
		float var2 = 1.0F;

		if (this.mainInventory[this.currentItem] != null)
		{
			var2 *= this.mainInventory[this.currentItem].getStrVsBlock(par1Block);
		}

		return var2;
	}

	public NBTTagList writeToNBT(NBTTagList par1NBTTagList)
	{
		int var2;
		NBTTagCompound var3;

		for (var2 = 0; var2 < this.mainInventory.length; ++var2)
		{
			if (this.mainInventory[var2] != null)
			{
				var3 = new NBTTagCompound();
				var3.setByte("Slot", (byte)var2);
				this.mainInventory[var2].writeToNBT(var3);
				par1NBTTagList.appendTag(var3);
			}
		}

		for (var2 = 0; var2 < this.armorInventory.length; ++var2)
		{
			if (this.armorInventory[var2] != null)
			{
				var3 = new NBTTagCompound();
				var3.setByte("Slot", (byte)(var2 + 100));
				this.armorInventory[var2].writeToNBT(var3);
				par1NBTTagList.appendTag(var3);
			}
		}

		return par1NBTTagList;
	}

	public void readFromNBT(NBTTagList par1NBTTagList)
	{
		this.mainInventory = new ItemStack[MainInventoryTableSizeMax];
		this.armorInventory = new ItemStack[ArmorInventoryTableSizeMax];

		for (int var2 = 0; var2 < par1NBTTagList.tagCount(); ++var2)
		{
			NBTTagCompound var3 = (NBTTagCompound)par1NBTTagList.tagAt(var2);
			int var4 = var3.getByte("Slot") & 255;
			ItemStack var5 = ItemStack.loadItemStackFromNBT(var3);

			if (var5 != null)
			{
				if (var4 >= 0 && var4 < this.mainInventory.length)
				{
					this.mainInventory[var4] = var5;
				}

				if (var4 >= 100 && var4 < this.armorInventory.length + 100)
				{
					this.armorInventory[var4 - 100] = var5;
				}
			}
		}
	}

	@Override
	public int getSizeInventory()
	{
		return DocaInventory.ALLInventoryTableSizeMax;
	}

	@Override
	public ItemStack getStackInSlot(int par1)
	{
		ItemStack[] var2 = this.mainInventory;

		if (par1 >= var2.length)
		{
			par1 -= var2.length;
			var2 = this.armorInventory;
		}

		return var2[par1];
	}

	@Override
	public String getInvName()
	{
		return "container.inventory";
	}

	@Override
	public boolean isInvNameLocalized()
	{
		return false;
	}

	@Override
	public int getInventoryStackLimit()
	{
		return 64;
	}

	public boolean canHarvestBlock(Block par1Block)
	{
		if (par1Block.blockMaterial.isToolNotRequired())
		{
			return true;
		}
		else
		{
			ItemStack var2 = this.getStackInSlot(this.currentItem);
			return var2 != null ? var2.canHarvestBlock(par1Block) : false;
		}
	}

	public ItemStack armorItemInSlot(int par1)
	{
		return this.armorInventory[par1];
	}

	public int getTotalArmorValue()
	{
		int var1 = 0;

		for (int var2 = 0; var2 < this.armorInventory.length; ++var2)
		{
			if (this.armorInventory[var2] != null && this.armorInventory[var2].getItem() instanceof ItemArmor)
			{
				int var3 = ((ItemArmor)this.armorInventory[var2].getItem()).damageReduceAmount;
				var1 += var3;
			}
		}

		return var1;
	}

	public void damageArmor(int par1)
	{
		par1 /= 4;

		if (par1 < 1)
		{
			par1 = 1;
		}

		for (int var2 = 0; var2 < this.armorInventory.length; ++var2)
		{
			if (this.armorInventory[var2] != null && this.armorInventory[var2].getItem() instanceof ItemArmor)
			{
				this.armorInventory[var2].damageItem(par1, this.theBase);

				if (this.armorInventory[var2].stackSize == 0)
				{
					this.armorInventory[var2] = null;
				}
			}
		}
	}

	public void dropAllItems()
	{
		int var1;

		for (var1 = 0; var1 < this.mainInventory.length; ++var1)
		{
			if (this.mainInventory[var1] != null)
			{
				this.theBase.entityDropItem(this.mainInventory[var1], 0.0F);
				this.mainInventory[var1] = null;
			}
		}

		for (var1 = 0; var1 < this.armorInventory.length; ++var1)
		{
			if (this.armorInventory[var1] != null)
			{
				this.theBase.entityDropItem(this.armorInventory[var1], 0.0F);
				this.armorInventory[var1] = null;
			}
		}
	}

	@Override
	public void onInventoryChanged()
	{
		this.inventoryChanged = true;
	}

	public void setItemStack(ItemStack par1ItemStack)
	{
		this.itemStack = par1ItemStack;
	}

	public ItemStack getItemStack()
	{
		return this.itemStack;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer par1EntityPlayer)
	{
		return this.theBase.isDead ? false : par1EntityPlayer.getDistanceSqToEntity(this.theBase) <= 64.0D;
	}

	public boolean hasItemStack(ItemStack par1ItemStack)
	{
		int var2;

		for (var2 = 0; var2 < this.armorInventory.length; ++var2)
		{
			if (this.armorInventory[var2] != null && this.armorInventory[var2].isItemEqual(par1ItemStack))
			{
				return true;
			}
		}

		for (var2 = 0; var2 < this.mainInventory.length; ++var2)
		{
			if (this.mainInventory[var2] != null && this.mainInventory[var2].isItemEqual(par1ItemStack))
			{
				return true;
			}
		}

		return false;
	}

	@Override
	public void openChest() {}

	@Override
	public void closeChest() {}

	@Override
	public boolean isItemValidForSlot(int par1, ItemStack par2ItemStack)
	{
		return true;
	}

	public void copyInventory(InventoryPlayer par1InventoryPlayer)
	{
		int i;

		for (i = 0; i < this.mainInventory.length; ++i)
		{
			this.mainInventory[i] = ItemStack.copyItemStack(par1InventoryPlayer.mainInventory[i]);
		}

		for (i = 0; i < this.armorInventory.length; ++i)
		{
			this.armorInventory[i] = ItemStack.copyItemStack(par1InventoryPlayer.armorInventory[i]);
		}

		this.currentItem = par1InventoryPlayer.currentItem;
	}
}
