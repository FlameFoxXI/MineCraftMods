package mods.doca.entity.func;
import java.util.ArrayList;
import java.util.Random;

import cpw.mods.fml.relauncher.*;

import net.minecraft.block.Block;
import net.minecraft.item.*;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.world.World;
import mods.doca.core.*;
import mods.doca.entity.*;
import mods.doca.tileentity.DocaTileEntityChest;

public class DocaFuncChsetSearch
{
	private int baseCestSearchTimer;
	private Random rand = new Random();

	public DocaFuncChsetSearch()
	{
		this.baseCestSearchTimer = DocaSet.maxCestSearchTimer;
	}

	public boolean onLivingUpdate(DocaEntityBase theBase)
	{
		boolean tmpReturn = false;
		if (!theBase.worldObj.isRemote && theBase.getMode() != DocaReg.getModeNameToNo("Normal") && theBase.inventory.getFunctionItem() != null && !theBase.isSitting() && 1 < theBase.getLife() && this.baseCestSearchTimer <= 0){
			DocaTileEntityChest tmpTileEntityChest = this.getSearchTileEntityChestForBlock(theBase);
			if (tmpTileEntityChest != null)
			{
				boolean chestfull = false;
				boolean itemCompFlg = false;
				for (int i = 0; i < theBase.inventory.getMainInventoryItemSize(); i++)
				{
					ItemStack tmpItemStack = theBase.inventory.getStackInSlot(i);

					if (tmpItemStack != null) {

						if (tmpItemStack.hasTagCompound())
						{
							continue;
						}
						
						ArrayList<Integer> slotList = new ArrayList<Integer>();
						ArrayList<Integer> slotListItemStack = new ArrayList<Integer>();
						boolean foundItemFlg = false;

						for (int j = 0; j < tmpTileEntityChest.getSizeInventory(); j++)
						{
							if(DocaTools.ofItem(tmpTileEntityChest.getStackInSlot(j), DocaTools.newItem(tmpItemStack)) && tmpTileEntityChest.getStackInSlot(j).getItemDamage() == tmpItemStack.getItemDamage())
							{
								if (tmpTileEntityChest.getStackInSlot(j).stackSize < tmpTileEntityChest.getStackInSlot(j).getMaxStackSize())
								{
									slotList.add(j);
									slotListItemStack.add(tmpTileEntityChest.getStackInSlot(j).stackSize);
									tmpTileEntityChest.getStackInSlotOnClosing(j);
									foundItemFlg = true;
								}
							}
						}

						if (foundItemFlg)
						{
							int goukei = tmpItemStack.stackSize;
							if (!slotListItemStack.isEmpty())
							{
								for (int k = 0; k < slotListItemStack.size(); k++)
								{
									goukei += slotListItemStack.get(k);
								}
							}

							int tmp = goukei / tmpItemStack.getMaxStackSize();
							int tmp2 = goukei % tmpItemStack.getMaxStackSize();

							theBase.inventory.getStackInSlotOnClosing(i);

							int count = 0;

							if (0 < tmp)
							{
								for (int h = 0; h < tmp; h++)
								{
									ItemStack copy = new ItemStack(DocaTools.newItem(tmpItemStack), tmpItemStack.getMaxStackSize(), tmpItemStack.getItemDamage());
									tmpTileEntityChest.setInventorySlotContents(slotList.get(count), copy);
									count++;
								}
							}

							if (tmp < slotList.size())
							{
								if (0 < tmp2)
								{
									ItemStack copy = new ItemStack(DocaTools.newItem(tmpItemStack), tmp2, tmpItemStack.getItemDamage());
									tmpTileEntityChest.setInventorySlotContents(slotList.get(count), copy);
								}
							}
							else
							{
								boolean findNull = false;
								for(int c = 0; c < tmpTileEntityChest.getSizeInventory(); c++)
								{
									if(tmpTileEntityChest.getStackInSlot(c) == null)
									{
										if (0 < tmp2)
										{
											ItemStack copy = new ItemStack(DocaTools.newItem(tmpItemStack), tmp2, tmpItemStack.getItemDamage());
											tmpTileEntityChest.setInventorySlotContents(c, copy);
										}
										findNull = true;
										break;
									}
								}
								if (!findNull)
								{
									if (0 < tmp2)
									{
										ItemStack copy = new ItemStack(DocaTools.newItem(tmpItemStack), tmp2, tmpItemStack.getItemDamage());
										theBase.inventory.setInventorySlotContents(i, copy);
										chestfull = true;
									}
								}
							}
						}
						else
						{
							boolean findNull = false;
							for(int c = 0; c < tmpTileEntityChest.getSizeInventory(); c++)
							{
								if(tmpTileEntityChest.getStackInSlot(c) == null)
								{
									ItemStack copy = new ItemStack(DocaTools.newItem(tmpItemStack), tmpItemStack.stackSize, tmpItemStack.getItemDamage());
									tmpTileEntityChest.setInventorySlotContents(c, copy);
									theBase.inventory.getStackInSlotOnClosing(i);
									findNull = true;
									break;
								}
							}
							if (!findNull)
							{
								chestfull = true;
							}
						}
						theBase.inventory.onInventoryChanged();
						if (chestfull){break;}
					}
					theBase.inventory.onInventoryChanged();
					if (chestfull){break;}
				}
			}
			this.baseCestSearchTimer = DocaSet.maxCestSearchTimer + rand.nextInt(200);
			tmpReturn = true;
		}
		else
		{
			this.baseCestSearchTimer--;
		}
		return tmpReturn;
	}

	protected DocaTileEntityChest getSearchTileEntityChestForBlock(DocaEntityBase theBase)
	{
		DocaTileEntityChest tmpTileEntityChest = null;

		for (int var4 = (int)theBase.posX - 8; (double)var4 < theBase.posX + 8.0D; ++var4)
		{
			for (int var5 = (int)theBase.posY - 8; (double)var5 < theBase.posY + 8.0D; ++var5)
			{
				for (int var6 = (int)theBase.posZ - 8; (double)var6 < theBase.posZ + 8.0D; ++var6)
				{
					DocaTileEntityChest tmp2TileEntityChest = this.getTileEntityChestForBlock(theBase.worldObj, var4, var5, var6);
					if(tmp2TileEntityChest != null)
					{
						for(int i = 0; i < tmp2TileEntityChest.getSizeInventory(); i++)
						{
							if(tmp2TileEntityChest.getStackInSlot(i) == null)
							{
								tmpTileEntityChest = tmp2TileEntityChest;
								break;
							}
						}
					}
					if (tmpTileEntityChest != null) { break; }
				}
				if (tmpTileEntityChest != null) { break; }
			}
			if (tmpTileEntityChest != null) { break; }
		}
		return tmpTileEntityChest;
	}

	protected DocaTileEntityChest getTileEntityChestForBlock(World par1World, int par2, int par3, int par4)
	{
		DocaTileEntityChest tmpTileEntityChest = null;

		if (par1World.getBlockId(par2, par3, par4) == DocaSet.blockDocaChest.blockID)
		{
			tmpTileEntityChest = (DocaTileEntityChest)par1World.getBlockTileEntity(par2, par3, par4);

			if (tmpTileEntityChest.numUsingPlayers != 0)
			{
				return null;
			}
		}
		return tmpTileEntityChest;
	}
}