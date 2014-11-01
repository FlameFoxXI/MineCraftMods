package mods.doca.item;

import cpw.mods.fml.relauncher.*;

import mods.doca.*;
import mods.doca.core.*;
import mods.doca.entity.*;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.creativetab.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.potion.Potion;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraft.world.*;

import java.util.*;

public class DocaItemCarrierBag extends Item
{
	public boolean petAngry;
	public int petTexture;
	public int petMode;
	public int petCollarColor;
	public String petName;
	public String petOwnerName;
	public int petSize;
	public int petAge;
	public String petTypeString;
	public String petSubOwnerName;
	
	DocaEntityBase theBase;

//	public DocaItemCarrierBag(int par1)
	public DocaItemCarrierBag()
	{
//		super(par1);
		super();
		this.maxStackSize = 1;
		this.setCreativeTab(CreativeTabs.tabMisc);
		this.petTypeString = "";
	}

	@Override
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
	{
		boolean flagUseItem = false;

		if (!par2World.isRemote)
		{
			NBTTagCompound tmpNBTTagCompound = par1ItemStack.getTagCompound();

			if (tmpNBTTagCompound != null)
			{
				this.petTypeString = tmpNBTTagCompound.getString("petTypeString");

				if (!this.petTypeString.equalsIgnoreCase(""))
				{
					this.petAngry = tmpNBTTagCompound.getBoolean("petAngry");
					this.petMode = tmpNBTTagCompound.getInteger("petMode");
					this.petTexture = tmpNBTTagCompound.getInteger("petTexture");

					if (tmpNBTTagCompound.hasKey("petCollarColor"))
					{
						this.petCollarColor = tmpNBTTagCompound.getByte("petCollarColor");
					}

					this.petName = tmpNBTTagCompound.getString("petName");
					this.petSize = tmpNBTTagCompound.getInteger("petSize");
					this.petOwnerName = tmpNBTTagCompound.getString("petOwnerName");
					this.petAge = tmpNBTTagCompound.getInteger("petAge");
					this.petSubOwnerName = tmpNBTTagCompound.getString("SubOwners");
					
					if (DocaSet.Debug)
					{
						this.petOwnerName = par3EntityPlayer.getUniqueID().toString();
					}
					
					if (par3EntityPlayer.getUniqueID().toString().equalsIgnoreCase(this.petOwnerName))
					{

						for(int i = 0; i < DocaReg.getSettingsMax(); i++)
						{
							if(DocaReg.getTypeDisp(i).equalsIgnoreCase(this.petTypeString) && DocaReg.getUse(i))
							{
								DocaEntityBase tmpEntity = DocaTools.createEntity(DocaReg.getNameToType(this.petTypeString), par2World);
								tmpEntity.setAngry(this.petAngry);
								tmpEntity.setMode(this.petMode);
								tmpEntity.setIndexTexture(this.petTexture);
								tmpEntity.setCollarColor(this.petCollarColor);
								tmpEntity.setName(this.petName);
								tmpEntity.setModelSize(this.petSize);
								tmpEntity.func_152115_b(this.petOwnerName);
								tmpEntity.setGrowingAge(this.petAge);
								tmpEntity.setSubOwners(this.petSubOwnerName);
								par2World.spawnEntityInWorld(new DocaEntityCarrierBag(par2World, par3EntityPlayer, tmpEntity, this.petTypeString));
								flagUseItem = true;
								break;
							}
						}
					}
				}
			}
		}

		if (flagUseItem)
		{
			if (DocaSet.item_CarrierBagReuse)
			{
				par1ItemStack.setTagCompound(null);
				this.petTypeString = "";
			}
			else
			{
				par1ItemStack.stackSize--;
			}

			par2World.playSoundAtEntity(par3EntityPlayer, "random.bow", 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
		}

		return par1ItemStack;
	}

	@Override
	public boolean hitEntity(ItemStack par1ItemStack, EntityLivingBase par2EntityLivingBase, EntityLivingBase par3EntityLivingBase)
	{
		NBTTagCompound tmpNBTTagCompound = par1ItemStack.getTagCompound();

		if (tmpNBTTagCompound == null)
		{
			tmpNBTTagCompound = new NBTTagCompound();
			par1ItemStack.setTagCompound(tmpNBTTagCompound);
			this.petTypeString = "";
		}

		if (!par3EntityLivingBase.worldObj.isRemote)
		{
			if (par2EntityLivingBase != null && par2EntityLivingBase instanceof DocaEntityBase)
			{
				DocaEntityBase tmpDocaEntityBase = (DocaEntityBase)par2EntityLivingBase;
				EntityPlayer tmpEntityPlayer = (EntityPlayer)par3EntityLivingBase;

				if (tmpEntityPlayer.getUniqueID().toString().equalsIgnoreCase(tmpDocaEntityBase.func_152113_b()) && DocaReg.getUse(DocaReg.getNameToType(tmpDocaEntityBase.getEntityDocaString())))
				{
					if (this.petTypeString.equalsIgnoreCase(""))
					{
						this.petTypeString = tmpDocaEntityBase.getEntityDocaString();
						this.petAngry = tmpDocaEntityBase.isAngry();
						this.petMode = tmpDocaEntityBase.getMode();
						this.petTexture = tmpDocaEntityBase.getIndexTexture();
						this.petCollarColor = tmpDocaEntityBase.getCollarColor();
						this.petName = tmpDocaEntityBase.getName();
						this.petSize = tmpDocaEntityBase.getModelSize();
						this.petOwnerName = tmpDocaEntityBase.func_152113_b();
						this.petAge = tmpDocaEntityBase.getGrowingAge();
						this.petSubOwnerName = tmpDocaEntityBase.getSubOwners();

						tmpNBTTagCompound.setBoolean("petAngry", this.petAngry);
						tmpNBTTagCompound.setInteger("petMode", this.petMode);
						tmpNBTTagCompound.setInteger("petTexture", this.petTexture);
						tmpNBTTagCompound.setByte("petCollarColor", (byte)this.petCollarColor);

						if (this.petName == null)
						{
							tmpNBTTagCompound.setString("petName", "");
						}
						else
						{
							tmpNBTTagCompound.setString("petName", this.petName);
						}

						tmpNBTTagCompound.setInteger("petSize", this.petSize);

						if (this.petOwnerName == null)
						{
							tmpNBTTagCompound.setString("petOwnerName", "");
						}
						else
						{
							tmpNBTTagCompound.setString("petOwnerName", this.petOwnerName);
						}

						tmpNBTTagCompound.setInteger("petAge", this.petAge);
						tmpNBTTagCompound.setString("petTypeString", this.petTypeString);
						tmpNBTTagCompound.setString("SubOwners", this.petSubOwnerName);
						DocaSet.copyToInventoryCheck = true;
						DocaSet.copyToInventoryCount = 50;
						tmpDocaEntityBase.inventory.dropAllItems();
						tmpDocaEntityBase.setDead();
					}
				}
			}
		}

		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
//	public void registerIcons(IconRegister par1IconRegister)
	public void registerIcons(IIconRegister par1IconRegister)
	{
		this.itemIcon = par1IconRegister.registerIcon(DocaSet.texture_ItemCarrierBag);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean hasEffect(ItemStack par1ItemStack)
	{
		NBTTagCompound tmpNBTTagCompound = par1ItemStack.getTagCompound();

		if (tmpNBTTagCompound == null)
		{
			return false;
		}
		else
		{
			String tempType = tmpNBTTagCompound.getString("petTypeString");

			if (tempType.equalsIgnoreCase(""))
			{
				return false;
			}
		}

		return true;
	}

	@Override
	public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4)
	{
		NBTTagCompound tmpNBTTagCompound = par1ItemStack.getTagCompound();

		if (tmpNBTTagCompound == null)
		{
			par3List.add(EnumChatFormatting.GOLD + "[ " + StatCollector.translateToLocal("doca.tag.empty") + " ]");
		}
		else
		{
			String tempType = tmpNBTTagCompound.getString("petTypeString");

			if (!tempType.equalsIgnoreCase(""))
			{
				par3List.add(EnumChatFormatting.GREEN + "[ " + StatCollector.translateToLocal("doca.tag.type") + ": " + tmpNBTTagCompound.getString("petTypeString") + " ]");
				par3List.add(EnumChatFormatting.RED + "[ " + StatCollector.translateToLocal("doca.tag.name") + ": " + tmpNBTTagCompound.getString("petName") + " ]");
				par3List.add(EnumChatFormatting.BLUE + "[ " + StatCollector.translateToLocal("doca.tag.owner") + ": " + tmpNBTTagCompound.getString("petOwnerName") + " ]");
			}
			else
			{
				par3List.add(EnumChatFormatting.GOLD + "[ " + StatCollector.translateToLocal("doca.tag.empty") + " ]");
			}
		}
	}
}
