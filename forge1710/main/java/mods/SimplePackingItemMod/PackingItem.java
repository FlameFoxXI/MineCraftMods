package mods.SimplePackingItemMod;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class PackingItem extends Item
{
	private IIcon[] field_94586_c;
	public PackingItem()
	{
		this.maxStackSize = 64;
		this.setHasSubtypes(true);
		this.setMaxDamage(0);
		this.setCreativeTab(CreativeTabs.tabBlock);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public String getUnlocalizedName(ItemStack par1ItemStack)
	{
		if(par1ItemStack.getItemDamage() < SimplePackingItemCoreMod.SimplePackingCompressItemStartIndex){
			return super.getUnlocalizedName() + ".SP." + SimplePackingItemCoreMod.SimplePackingItemStringUS[par1ItemStack.getItemDamage()];
		}
		else{
			return super.getUnlocalizedName() + ".SPC." + SimplePackingItemCoreMod.SimplePackingItemStringUS[par1ItemStack.getItemDamage() - SimplePackingItemCoreMod.SimplePackingCompressItemStartIndex];
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item par1, CreativeTabs par2CreativeTabs, List par3List)
	{
		if(!SimplePackingItemCoreMod.SimplePackingItemHideCreativeTabs){
			for (int i = 0; i < SimplePackingItemCoreMod.SimplePackingItemMax; i++){
				if (!SimplePackingItemCoreMod.SimplePackingItemStringUS[i].equals("")) {
					par3List.add(new ItemStack(this, 1, i));
				}
			}
			if(SimplePackingItemCoreMod.SimplePackingItemMoreCompress){
				for (int i = SimplePackingItemCoreMod.SimplePackingCompressItemStartIndex; i < SimplePackingItemCoreMod.SimplePackingCompressItemMax; i++){
					if (!SimplePackingItemCoreMod.SimplePackingItemStringUS[i-SimplePackingItemCoreMod.SimplePackingCompressItemStartIndex].equals("")) {
						par3List.add(new ItemStack(this, 1, i));
					}
				}
			}
		}

	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIconFromDamage(int par1)
	{
		return this.field_94586_c[par1];
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister par1IconRegister)
	{
		this.field_94586_c = new IIcon[SimplePackingItemCoreMod.SimplePackingCompressTableMax];

		for (int i = 0; i < SimplePackingItemCoreMod.SimplePackingCompressTableMax; ++i)
		{
			this.field_94586_c[i] = par1IconRegister.registerIcon("simplepackingitem:SimplePackingItem" + i);
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean hasEffect(ItemStack par1ItemStack)
	{
		if(SimplePackingItemCoreMod.SimplePackingCompressItemStartIndex <= par1ItemStack.getItemDamage()){
			return true;
		}
		else{
			return false;
		}
	}
}
