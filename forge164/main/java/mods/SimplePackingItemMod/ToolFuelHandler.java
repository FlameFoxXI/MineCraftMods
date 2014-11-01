package mods.SimplePackingItemMod;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.IFuelHandler;

public class ToolFuelHandler implements IFuelHandler {
	private int listSimpleCondensedItemDamage[] = { 0,68,69,70,71 };
	private int listSimpleCondensedItemItemDamage[] = { 0 };

	@Override
	public int getBurnTime(ItemStack fuel) {

		if(SimplePackingItemCoreMod.SimplePackingItemID != 0){
			if(fuel.itemID == SimplePackingItemCoreMod.SimplePackingItem.itemID){
				for(int i = 0; i < this.listSimpleCondensedItemDamage.length; i++){
					if (fuel.getItemDamage() == listSimpleCondensedItemDamage[i]){
						return (1600*8);
					}
				}
			}
		}
		if(SimplePackingItemCoreMod.SimplePackingItemItemID != 0){
			if(fuel.itemID == SimplePackingItemCoreMod.SimplePackingItemItem.itemID){
				for(int i = 0; i < this.listSimpleCondensedItemItemDamage.length; i++){
					if (fuel.getItemDamage() == listSimpleCondensedItemItemDamage[i]){
						return (1600*8);
					}
				}
			}
		}
		return 0;
	}
}