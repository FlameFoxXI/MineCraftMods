package mods.ffxiwatereraser.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mods.ffxiwatereraser.core.FFxiSet;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;


public class ItemWaterEraser extends Item
{

	public ItemWaterEraser(int par1)
	{
		super(par1);
	}

	@Override
    public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10)
    {
		if (!par3World.isRemote && par1ItemStack.getItem() != null && par1ItemStack.getItem().itemID == FFxiSet.itemWaterEraser.itemID)
		{
			if(!FFxiSet.commandRunning && FFxiSet.commandUserName.equalsIgnoreCase(""))
			{
				FFxiSet.commandUserName = par2EntityPlayer.getCommandSenderName();
				FFxiSet.commandRunning = true;
				FFxiSet.searchX = par4;
				FFxiSet.searchY = par5;
				FFxiSet.searchZ = par6;
				return true;
			}
		}
		return super.onItemUse(par1ItemStack, par2EntityPlayer, par3World, par4, par5, par6, par7, par8, par9, par10);
    }
	
	@Override
	@SideOnly(Side.CLIENT)
	public boolean hasEffect(ItemStack par1ItemStack)
	{
		if (FFxiSet.commandRunning)
		{
			return true;
		}
		return false;
	}

}