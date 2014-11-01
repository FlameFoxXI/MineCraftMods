package mods.ffxislowfooted;

import net.minecraft.entity.Entity;
import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

public class ItemSlowFooted extends ItemArmor 
{

	public ItemSlowFooted(int par1, EnumArmorMaterial par2ArmorMaterial, int par3, int par4)
	{
		super(par1, par2ArmorMaterial, par3, par4);
	}

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type)
	{
		if(stack.getItem() == FFxiSlowFootedMod.slowfootedItemHelMet || stack.getItem() == FFxiSlowFootedMod.slowfootedItemChest || stack.getItem() == FFxiSlowFootedMod.slowfootedItemBoots)
		{
			return FFxiSlowFootedMod.texturesSlowFeetedLayer_1;
		}
		else if(stack.getItem() == FFxiSlowFootedMod.slowfootedItemLegs)
		{
			return FFxiSlowFootedMod.texturesSlowFeetedLayer_2;
		}
		return null;
	}
}
