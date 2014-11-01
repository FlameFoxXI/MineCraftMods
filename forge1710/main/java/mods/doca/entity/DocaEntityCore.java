package mods.doca.entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import mods.doca.core.*;
import mods.doca.entity.func.*;
import mods.doca.inventory.*;
import net.minecraft.block.Block;
import net.minecraft.entity.*;
import net.minecraft.entity.passive.*;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.*;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.*;

public class DocaEntityCore extends EntityTameable
{
	public DocaInventory inventory;
	public String subOwners;
	public boolean theInventoryFull;
	private final String DefaultHomePoint = "-1:-1:-1";

	public DocaEntityCore(World par1World) {
		super(par1World);
		this.inventory = new DocaInventory(this);
		this.theInventoryFull = false;
	}

	@Override
	protected void entityInit()
	{
		super.entityInit();
		//dataWatcher  0 EntityFlag             : Entity
		//dataWatcher  1 Air                    : Entity
		//dataWatcher  6 Health                 : EntityLivingBase
		//dataWatcher  7 calcPotionLiquidColor  : EntityLivingBase
		//dataWatcher  8 activePotionsMap       : EntityLivingBase
		//dataWatcher  9 ArrowCountInEntity     : EntityLivingBase
		//dataWatcher 10 NameTag                : EntityLiving
		//dataWatcher 11 AlwaysRenderNameTag    : EntityLiving
		//dataWatcher 12 Age                    : EntityAgeable
		//dataWatcher 16 Tamed & Sitting        : EntityTameable
		//dataWatcher 17 OwnerName              : EntityTameable
		this.dataWatcher.addObject(18, new Float(this.getHealth()));        //MaxHelth
		this.dataWatcher.addObject(19, new Integer(0));                     //Status(2byte) : Type(1byte) : XXXX(1byte)
		this.dataWatcher.addObject(20, new Integer(0));                     //Texture(1byte) : Collar(2byte) : Mode(1byte) : Size(1byte)
		this.dataWatcher.addObject(21, DefaultHomePoint);				    //HomePoint
		this.dataWatcher.addObject(22, "");                                 //PetName
		this.dataWatcher.addObject(23, new Integer(0));						//Carried
		this.dataWatcher.addObject(24, new Integer(0));						//CarryingData
		this.dataWatcher.addObject(25, new ItemStack(Items.bow));           //ItemStack
		this.dataWatcher.addObject(26, new Integer(0));						//CarryingData
		this.inventory = new DocaInventory(this);
		this.subOwners = "";
		this.theInventoryFull = false;
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.writeEntityToNBT(par1NBTTagCompound);
		par1NBTTagCompound.setBoolean("Angry", this.isAngry());
		par1NBTTagCompound.setInteger("Mode", this.getMode());
		par1NBTTagCompound.setInteger("IndexTexture", this.getIndexTexture());
		par1NBTTagCompound.setByte("CollarColor", (byte)this.getCollarColor());
		par1NBTTagCompound.setInteger("HomePosX", this.getHomePoint().posX);
		par1NBTTagCompound.setInteger("HomePosY", this.getHomePoint().posY);
		par1NBTTagCompound.setInteger("HomePosZ", this.getHomePoint().posZ);
		par1NBTTagCompound.setString("Name", this.getName());
		par1NBTTagCompound.setTag("Inventory", this.inventory.writeToNBT(new NBTTagList()));
		par1NBTTagCompound.setBoolean("HealingCopy", this.isHealing());
		par1NBTTagCompound.setBoolean("Downing", this.isDowning());
		par1NBTTagCompound.setBoolean("Sleeping", this.isSleeping());
		par1NBTTagCompound.setInteger("ModelSize", this.getModelSize());
		par1NBTTagCompound.setBoolean("Waiting", this.isWaiting());
		par1NBTTagCompound.setString("SubOwners", this.getSubOwners());

		if (DocaSet.DebugData)
		{
			DocaTools.info("writeEntityToNBT ->" + par1NBTTagCompound);
		}

	}

	@Override
	public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.readEntityFromNBT(par1NBTTagCompound);
		this.setAngry(par1NBTTagCompound.getBoolean("Angry"));
		this.setMode(par1NBTTagCompound.getInteger("Mode"));
		this.setIndexTexture(par1NBTTagCompound.getInteger("IndexTexture"));

		if (par1NBTTagCompound.hasKey("CollarColor"))
		{
			this.setCollarColor(par1NBTTagCompound.getByte("CollarColor"));
		}

		this.setHomePoint(new ChunkCoordinates(
				par1NBTTagCompound.getInteger("HomePosX"),
				par1NBTTagCompound.getInteger("HomePosY"),
				par1NBTTagCompound.getInteger("HomePosZ")
				));

		this.setName(par1NBTTagCompound.getString("Name"));
        NBTTagList nbttaglist = par1NBTTagCompound.getTagList("Inventory", 10);

        this.inventory.readFromNBT(nbttaglist);
		this.setHealing(par1NBTTagCompound.getBoolean("HealingCopy"));
		this.setDowning(par1NBTTagCompound.getBoolean("Downing"));
		this.setSleeping(par1NBTTagCompound.getBoolean("Sleeping"));
		this.setModelSize(par1NBTTagCompound.getInteger("ModelSize"));
		this.setWaiting(par1NBTTagCompound.getBoolean("Waiting"));
		this.setSubOwners(par1NBTTagCompound.getString("SubOwners"));

		if (this.isSleeping())
		{
			this.setSitting(false);
			this.setSleeping(false);
			this.aiSit.setSitting(false);
		}
		if (DocaSet.DebugData)
		{
			DocaTools.info("readEntityFromNBT ->" + par1NBTTagCompound);
			DocaTools.info("readEntityFromNBT ->" + this.func_152113_b());
		}
	}

	/********************************************************************************************
	 *	getDataWatcher() Getter / Setter														*
	 ********************************************************************************************/

	/* -------------------------------- */
	/* getDataWatcher() 16				*/
	/* -------------------------------- */
	public boolean isAngry()
	{
		return (this.dataWatcher.getWatchableObjectByte(16) & 2) != 0;
	}

	public void setAngry(boolean par1)
	{
		byte var2 = this.dataWatcher.getWatchableObjectByte(16);

		if (par1)
		{
			this.dataWatcher.updateObject(16, Byte.valueOf((byte)(var2 | 2)));
		}
		else
		{
			this.dataWatcher.updateObject(16, Byte.valueOf((byte)(var2 & -3)));
		}
	}

	/* -------------------------------- */
	/* getDataWatcher() 18				*/
	/* -------------------------------- */
	public void updateHelth()
	{
		this.dataWatcher.updateObject(18, Float.valueOf(this.getHealth()));
	}

	public float getLife()
	{
		return this.dataWatcher.getWatchableObjectFloat(18);
	}

	/* -------------------------------- */
	/* getDataWatcher() 19				*/
	/* -------------------------------- */
	public boolean func_70922_bv()
	{
		return (this.dataWatcher.getWatchableObjectInt(19) & 0x000000FF) == 1;
	}

	public void func_70918_i(boolean par1)
	{
		if (par1)
		{
			this.dataWatcher.updateObject(19, Integer.valueOf((1 & 0x000000FF) + (this.dataWatcher.getWatchableObjectInt(19) & 0xFFFFFF00)));
		}
		else
		{
			this.dataWatcher.updateObject(19, Integer.valueOf((0 & 0x000000FF) + (this.dataWatcher.getWatchableObjectInt(19) & 0xFFFFFF00)));
		}
	}

	public int getType()
	{
		return ((this.dataWatcher.getWatchableObjectInt(19) & 0x0000FF00) >> 8);
	}

	public void setType(int par1)
	{
		this.dataWatcher.updateObject(19, Integer.valueOf(((par1 & 0x000000FF) << 8) | (this.dataWatcher.getWatchableObjectInt(19) & 0xFFFF00FF)));
	}

	public boolean isHealing()
	{
		return (this.dataWatcher.getWatchableObjectInt(19) & 65536) != 0;
	}

	public void setHealing(boolean par1)
	{
		if (par1)
		{
			this.dataWatcher.updateObject(19, Integer.valueOf(this.dataWatcher.getWatchableObjectInt(19) | 65536));
		}
		else
		{
			this.dataWatcher.updateObject(19, Integer.valueOf(this.dataWatcher.getWatchableObjectInt(19) & -65537));
		}
	}

	public boolean isDowning()
	{
		return !this.onGround ? false : (this.dataWatcher.getWatchableObjectInt(19) & 131072) != 0;
	}

	public void setDowning(boolean par1)
	{
		if (par1)
		{
			this.dataWatcher.updateObject(19, Integer.valueOf(this.dataWatcher.getWatchableObjectInt(19) | 131072));
		}
		else
		{
			this.dataWatcher.updateObject(19, Integer.valueOf(this.dataWatcher.getWatchableObjectInt(19) & -131073));
		}
	}

	public boolean isSleeping()
	{
		return (this.dataWatcher.getWatchableObjectInt(19) & 262144) != 0;
	}

	public void setSleeping(boolean par1)
	{
		if (par1)
		{
			this.dataWatcher.updateObject(19, Integer.valueOf(this.dataWatcher.getWatchableObjectInt(19) | 262144));
		}
		else
		{
			this.dataWatcher.updateObject(19, Integer.valueOf(this.dataWatcher.getWatchableObjectInt(19) & -262145));
		}
	}

	public boolean isComeing()
	{
		return (this.dataWatcher.getWatchableObjectInt(19) & 524288) != 0;
	}

	public void setComeing(boolean par1)
	{
		if (par1)
		{
			this.dataWatcher.updateObject(19, Integer.valueOf(this.dataWatcher.getWatchableObjectInt(19) | 524288));
		}
		else
		{
			this.dataWatcher.updateObject(19, Integer.valueOf(this.dataWatcher.getWatchableObjectInt(19) & -524289));
		}
	}

	public boolean isDistance()
	{
		return (this.dataWatcher.getWatchableObjectInt(19) & 1048576) != 0;
	}

	public void setDistance(boolean par1)
	{
		if (par1)
		{
			this.dataWatcher.updateObject(19, Integer.valueOf(this.dataWatcher.getWatchableObjectInt(19) | 1048576));
		}
		else
		{
			this.dataWatcher.updateObject(19, Integer.valueOf(this.dataWatcher.getWatchableObjectInt(19) & -1048577));
		}
	}

	public boolean isWaiting()
	{
		return (this.dataWatcher.getWatchableObjectInt(19) & 2097152) != 0;
	}

	public void setWaiting(boolean par1)
	{
		if (par1)
		{
			this.dataWatcher.updateObject(19, Integer.valueOf(this.dataWatcher.getWatchableObjectInt(19) | 2097152));
		}
		else
		{
			this.dataWatcher.updateObject(19, Integer.valueOf(this.dataWatcher.getWatchableObjectInt(19) & -2097153));
		}
	}

	public boolean isRiden()
	{
		return (this.dataWatcher.getWatchableObjectInt(19) & 4194304) != 0;
	}

	public void setRiden(boolean par1)
	{
		if (par1)
		{
			this.dataWatcher.updateObject(19, Integer.valueOf(this.dataWatcher.getWatchableObjectInt(19) | 4194304));
		}
		else
		{
			this.dataWatcher.updateObject(19, Integer.valueOf(this.dataWatcher.getWatchableObjectInt(19) & -4194305));
		}
	}

	/* -------------------------------- */
	/* getDataWatcher() 20				*/
	/* -------------------------------- */
	public int getModelSize()
	{
		return (this.dataWatcher.getWatchableObjectInt(20) & 0x000000FF);
	}

	public void setModelSize(int par1)
	{
		this.dataWatcher.updateObject(20, Integer.valueOf((par1 & 0x000000FF) + (this.dataWatcher.getWatchableObjectInt(20) & 0xFFFFFF00)));
	}

	public int getMode()
	{
		return ((this.dataWatcher.getWatchableObjectInt(20) & 0x0000FF00) >> 8);
	}

	public void setMode(int par1)
	{
		this.dataWatcher.updateObject(20, Integer.valueOf(((par1 & 0x000000FF) << 8) | (this.dataWatcher.getWatchableObjectInt(20) & 0xFFFF00FF)));
	}

	public int getCollarColor()
	{
		return (((this.dataWatcher.getWatchableObjectInt(20) & 0x00FF0000) >> 16) & 0x0000000F);
	}

	public void setCollarColor(int par1)
	{
		this.dataWatcher.updateObject(20, Integer.valueOf(((par1 & 0x000000FF) << 16) | (this.dataWatcher.getWatchableObjectInt(20) & 0xFF00FFFF)));
	}

	public int getIndexTexture()
	{
		return ((this.dataWatcher.getWatchableObjectInt(20) & 0xFF000000) >> 24);
	}

	public void setIndexTexture(int par1)
	{
		this.dataWatcher.updateObject(20, Integer.valueOf(((par1 & 0x000000FF) << 24) | (this.dataWatcher.getWatchableObjectInt(20) & 0x00FFFFFF)));
	}

	/* -------------------------------- */
	/* getDataWatcher() 21				*/
	/* -------------------------------- */
	public ChunkCoordinates getHomePoint()
	{
		ItemStack tmpItemStack = null;
		int[] tmpInt = this.convertStrToIntTable(this.dataWatcher.getWatchableObjectString(21), 3);
		return new ChunkCoordinates(tmpInt[0], tmpInt[1], tmpInt[2]);
	}

	public void setHomePoint(ChunkCoordinates par1)
	{
		this.dataWatcher.updateObject(21, (par1.posX +":"+ par1.posY + ":"+ par1.posZ));
	}
	/* -------------------------------- */
	/* getDataWatcher() 22				*/
	/* -------------------------------- */
	public String getName()
	{
		return this.dataWatcher.getWatchableObjectString(22);
	}

	public void setName(String par1Str)
	{
		this.dataWatcher.updateObject(22, par1Str);
	}

	/* -------------------------------- */
	/* getDataWatcher() 23				*/
	/* -------------------------------- */
	public Block getCarried()
	{
		return Block.getBlockById(this.dataWatcher.getWatchableObjectInt(23));
	}

	public void setCarried(Block par1)
	{
		this.dataWatcher.updateObject(23, Integer.valueOf(Block.getIdFromBlock(par1)));
	}

	/* -------------------------------- */
	/* getDataWatcher() 24				*/
	/* -------------------------------- */
	public int getCarryingData()
	{
		return this.dataWatcher.getWatchableObjectInt(24);
	}

	public void setCarryingData(int par1)
	{
		this.dataWatcher.updateObject(24, Integer.valueOf(par1));
	}

	/* -------------------------------- */
	/* getDataWatcher() 25				*/
	/* -------------------------------- */
	public ItemStack getCurrentItemData()
	{
		ItemStack tmpItemStack = this.dataWatcher.getWatchableObjectItemStack(25);
		
		if (tmpItemStack != null && tmpItemStack.getItem() != null)
		{
			return tmpItemStack;
		}
		else
		{
			return new ItemStack(Items.bow);
		}
	}

	public void setCurrentItemData(ItemStack par1ItemStack)
	{
		if (par1ItemStack != null)
		{
			this.dataWatcher.updateObject(25, par1ItemStack);
		}
		else
		{
			this.dataWatcher.updateObject(25, new ItemStack(Items.bow));
		}
	}

	
	
	
	/* -------------------------------- */
	/* getDataWatcher() 26				*/
	/* -------------------------------- */
	public int getEmote()
	{
		return (this.dataWatcher.getWatchableObjectInt(26) & 0x000000FF);
	}

	public void setEmote(int par1)
	{
		this.dataWatcher.updateObject(26, Integer.valueOf((par1 & 0x000000FF) + (this.dataWatcher.getWatchableObjectInt(26) & 0xFFFFFF00)));
	}
	
	/* -------------------------------- */
	/* getDataWatcher() no use			*/
	/* -------------------------------- */
	public String getSubOwners()
	{
		return this.subOwners;
	}

	public void setSubOwners(String par1String)
	{
		this.subOwners = par1String;
	}

	public void addSubOwners(String par1String)
	{
		String data = this.subOwners.toLowerCase();
		String tmpString = "";
		String[] tmp = data.split(":");
		Set<String> tmpSet = new HashSet<String>();

		for (int i = 0; i < tmp.length; i++)
		{
			if(!(tmp[i].equalsIgnoreCase("")))
			{
				tmpSet.add(tmp[i].toLowerCase());
			}
		}

		if(!(tmpSet.contains(par1String.toLowerCase())))
		{
			tmpSet.add(par1String.toLowerCase());
		}

		for(String tmpEntry: tmpSet)
		{
			if (!(tmpEntry.equalsIgnoreCase("")))
			{
				tmpString = tmpString + ":" + tmpEntry.toLowerCase();
			}
		}
		this.subOwners = tmpString;
	}

	public boolean checkSubOwners(String par1String)
	{
		String data = this.subOwners.toLowerCase();
		String[] tmp = data.split(":");
		Set<String> tmpSet = new HashSet<String>();

		for (int i = 0; i < tmp.length; i++)
		{
			if(!(tmp[i].equalsIgnoreCase("")))
			{
				tmpSet.add(tmp[i].toLowerCase());
			}
		}

		return tmpSet.contains(par1String.toLowerCase());
	}

	@Override
	public EntityAgeable createChild(EntityAgeable entityageable) {
		return null;
	}

	private int[] convertStrToIntTable(String par1String, int tableSize)
	{
		String[] tmp = par1String.split(":");
		int[] tmpInt = new int[tableSize];

		if (tmp.length == tmpInt.length)
		{
			for(int i = 0; i < tmpInt.length; i++)
			{
				try
				{
					tmpInt[i] = new Integer(tmp[i]);
				}
				catch(Exception e)
				{
					DocaTools.warning("dataWatcher string to convert integer error!!");
					e.printStackTrace();
				}
			}
		}
		else 
		{
			for(int i = 0; i < tmpInt.length; i++)
			{
				tmpInt[i] = -1;
			}			
			DocaTools.warning("dataWatcher string error!!");
		}
		return tmpInt;
	}
}
