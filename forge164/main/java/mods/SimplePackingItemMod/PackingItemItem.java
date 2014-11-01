package mods.SimplePackingItemMod;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;

public class PackingItemItem extends Item
{
	private Icon[] field_94586_c;
	public PackingItemItem(int par1)
	{
		super(par1);
		this.maxStackSize = 64;
		this.setHasSubtypes(true);
		this.setMaxDamage(0);
		this.setCreativeTab(CreativeTabs.tabMisc);
	}

	@SideOnly(Side.CLIENT)
	public String getUnlocalizedName(ItemStack par1ItemStack)
	{
		if(par1ItemStack.getItemDamage() < SimplePackingItemCoreMod.SimplePackingCompressItemStartIndex){
			return super.getUnlocalizedName() + ".SP." + SimplePackingItemCoreMod.SimplePackingItemItemStringUS[par1ItemStack.getItemDamage()];
		}
		else{
			return super.getUnlocalizedName() + ".SPC." + SimplePackingItemCoreMod.SimplePackingItemItemStringUS[par1ItemStack.getItemDamage() - SimplePackingItemCoreMod.SimplePackingCompressItemStartIndex];
		}
	}

	@SideOnly(Side.CLIENT)
	public void getSubItems(int par1, CreativeTabs par2CreativeTabs, List par3List)
	{
		if(!SimplePackingItemCoreMod.SimplePackingItemHideCreativeTabs){
			for (int i = 0; i < SimplePackingItemCoreMod.SimplePackingItemMax; i++){
				if (!SimplePackingItemCoreMod.SimplePackingItemItemStringUS[i].equals("")) {
					par3List.add(new ItemStack(par1, 1, i));
				}
			}
			if(SimplePackingItemCoreMod.SimplePackingItemMoreCompress){
				for (int i = SimplePackingItemCoreMod.SimplePackingCompressItemStartIndex; i < SimplePackingItemCoreMod.SimplePackingCompressItemMax; i++){
					if (!SimplePackingItemCoreMod.SimplePackingItemItemStringUS[i-SimplePackingItemCoreMod.SimplePackingCompressItemStartIndex].equals("")) {
						par3List.add(new ItemStack(par1, 1, i));
					}
				}
			}
		}

	}

	@SideOnly(Side.CLIENT)
	public Icon getIconFromDamage(int par1)
	{
		return this.field_94586_c[par1];
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister par1IconRegister)
	{
		this.field_94586_c = new Icon[SimplePackingItemCoreMod.SimplePackingCompressTableMax];

		for (int i = 0; i < SimplePackingItemCoreMod.SimplePackingCompressTableMax; ++i)
		{
			this.field_94586_c[i] = par1IconRegister.registerIcon("SimplePackingItemItem" + i);
		}
	}

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