package mods.ffxicallanimalbell;

import java.util.Random;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemCallAnimalBell extends Item
{
	Random rand = new Random();
	
	public ItemCallAnimalBell(int par1)
	{
		super(par1);
        this.maxStackSize = 64;
        this.setCreativeTab(CreativeTabs.tabMaterials);
	}
    
    @Override
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
   		if (!par2World.isRemote)
   		{
   			FFxiCallAnimalBellMod.soundRightClick(FFxiCallAnimalBellMod.soundBell, par2World, par3EntityPlayer, 1, rand.nextInt(3));
   		}
    	return par1ItemStack;
    }
}
