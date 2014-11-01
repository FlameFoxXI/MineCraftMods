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

public class DocaItemRibbon extends Item
{
	DocaEntityBase theBase;
	private final String subOwnerName = "petSubOwnerName";
	
	public DocaItemRibbon()
//	public DocaItemRibbon(int par1)
	{
//		super(par1);
		this.maxStackSize = 1;
		this.setCreativeTab(CreativeTabs.tabMisc);
	}
	@Override
    public boolean itemInteractionForEntity(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, EntityLivingBase par3EntityLivingBase)
    {
		if (!par2EntityPlayer.worldObj.isRemote)
		{
			NBTTagCompound tmpNBTTagCompound = par1ItemStack.getTagCompound();
			
			if (tmpNBTTagCompound != null && par3EntityLivingBase instanceof DocaEntityBase)
			{
				DocaEntityBase tmpDocaEntityBase = (DocaEntityBase)par3EntityLivingBase;
				if (tmpDocaEntityBase.getOwnerName().equalsIgnoreCase(par2EntityPlayer.getCommandSenderName()) || DocaSet.Debug)
				{
					tmpDocaEntityBase.addSubOwnersDoca(tmpNBTTagCompound.getString(subOwnerName), par2EntityPlayer);
					if (DocaSet.Debug)
					{
						DocaTools.info("subOwners list -> " + tmpDocaEntityBase.getSubOwners());
					}
				}
			}
		}
		return false;
	}

	@Override
	public boolean hitEntity(ItemStack par1ItemStack, EntityLivingBase par2EntityLivingBase, EntityLivingBase par3EntityLivingBase)
	{
		NBTTagCompound tmpNBTTagCompound = par1ItemStack.getTagCompound();

		if (tmpNBTTagCompound == null)
		{
			tmpNBTTagCompound = new NBTTagCompound();
			par1ItemStack.setTagCompound(tmpNBTTagCompound);
		}
		
		if (par2EntityLivingBase instanceof EntityPlayer)
		{
			EntityPlayer tmpEntityPlayer = (EntityPlayer)par2EntityLivingBase;
			if (tmpEntityPlayer.getCommandSenderName() != null && !tmpEntityPlayer.getCommandSenderName().equalsIgnoreCase(""))
			{
				tmpNBTTagCompound.setString(subOwnerName, tmpEntityPlayer.getCommandSenderName());
				return true;
			}
		}
		else if (DocaSet.Debug && !(par2EntityLivingBase instanceof EntityPlayer) && !(par2EntityLivingBase instanceof DocaEntityBase))
		{
			EntityPlayer tmpEntityPlayer = (EntityPlayer)par3EntityLivingBase;
			tmpNBTTagCompound.setString(subOwnerName, tmpEntityPlayer.getCommandSenderName());
			return true;
		}
		return false;
	}


	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister par1IconRegister)
	{
		this.itemIcon = par1IconRegister.registerIcon(DocaSet.texture_ItemRibbon);
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
			String tempType = tmpNBTTagCompound.getString(subOwnerName);

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
			par3List.add(EnumChatFormatting.GOLD + "[ " + StatCollector.translateToLocal("doca.tag.ribbon.empty") + " ]");
		}
		else
		{
			String petSubOwnerName = tmpNBTTagCompound.getString(subOwnerName);

			if (!petSubOwnerName.equalsIgnoreCase(""))
			{
				par3List.add(EnumChatFormatting.GREEN + "[ " + StatCollector.translateToLocal("doca.tag.ribbon.name") + ": " +  tmpNBTTagCompound.getString(subOwnerName) + " ]");
			}
			else
			{
				par3List.add(EnumChatFormatting.GOLD + "[ " + StatCollector.translateToLocal("doca.tag.ribbon.empty") + " ]");
			}
		}
	}
}
