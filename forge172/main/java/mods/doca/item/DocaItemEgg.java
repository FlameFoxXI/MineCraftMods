package mods.doca.item;

import java.util.List;

import cpw.mods.fml.relauncher.*;

import mods.doca.core.*;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.creativetab.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.*;

public class DocaItemEgg extends Item
{
	private IIcon iconSword;
	private IIcon iconFoods;
	private IIcon iconFunction;
	private IIcon iconCreativeTab;

	public DocaItemEgg()
	{
		super();
		this.maxStackSize = 1;
		this.setHasSubtypes(true);
		this.setMaxDamage(0);
		this.setCreativeTab(CreativeTabs.tabMisc);
	}

	@Override
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
	{
		if (!par3EntityPlayer.capabilities.isCreativeMode)
		{
			par1ItemStack.stackSize--;
		}

		par2World.playSoundAtEntity(par3EntityPlayer, "random.bow", 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));

		if (!par2World.isRemote)
		{
			par2World.spawnEntityInWorld(new DocaEntityEgg(par2World, par3EntityPlayer, par1ItemStack));
		}

		return par1ItemStack;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public String getUnlocalizedName(ItemStack par1ItemStack)
	{
		return super.getUnlocalizedName() + "Spawn" + DocaReg.getTypeToName(par1ItemStack.getItemDamage());
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item par1, CreativeTabs par2CreativeTabs, List par3List)
	{
		for (int i = 0; i < DocaReg.getSettingsMax(); i++)
		{
			if (DocaReg.getUse(i))
			{
				par3List.add(new ItemStack(par1, 1, i));
			}
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister par1IconRegister)
	{
		this.itemIcon = par1IconRegister.registerIcon(DocaSet.texture_ItemEgg);
		this.iconSword = par1IconRegister.registerIcon(DocaSet.textureItemEmptySword);
		this.iconFoods = par1IconRegister.registerIcon(DocaSet.textureItemEmptyFoods);
		this.iconFunction = par1IconRegister.registerIcon(DocaSet.textureItemEmptyFunction);
		this.iconCreativeTab = par1IconRegister.registerIcon(DocaSet.textureCreativeTabsSymbol);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean hasEffect(ItemStack par1ItemStack)
	{
		if (par1ItemStack.getItemDamage() < DocaReg.getSettingsMax())
		{
			return true;
		}
		return false;
	}

	@SideOnly(Side.CLIENT)
//	public Icon getDocaIconforDummy(int type)
	public IIcon getDocaIconforDummy(int type)
	{
		return type == 1 ? this.iconFoods : (type == 2 ? this.iconFunction : this.iconSword);
	}

	@Override
//	public Icon getIconFromDamage(int par1)
	public IIcon getIconFromDamage(int par1)
	{
		if (par1 == 256)
		{
			return iconCreativeTab;
		}
		return itemIcon;
	}
}
