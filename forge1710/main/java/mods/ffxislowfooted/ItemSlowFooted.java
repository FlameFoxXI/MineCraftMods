package mods.ffxislowfooted;

import net.minecraft.entity.Entity;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

public class ItemSlowFooted extends ItemArmor 
{

	public ItemSlowFooted(ArmorMaterial par1ArmorMaterial, int par2, int par3)
	{
		super(par1ArmorMaterial, par2, par3);
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
