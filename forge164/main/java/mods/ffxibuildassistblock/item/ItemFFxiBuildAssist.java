package mods.ffxibuildassistblock.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mods.ffxibuildassistblock.core.FFxiSet;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;

public class ItemFFxiBuildAssist extends ItemBlock
{
    public ItemFFxiBuildAssist(int par1)
    {
        super(par1);
        this.setMaxDamage(0);
        this.setHasSubtypes(true);
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public Icon getIconFromDamage(int par1)
    {
    	return Block.blocksList[this.getBlockID()].getIcon(par1, 0);
    }
    
    @Override
    public int getMetadata(int par1)
    {
        return par1;
    }
    
    @Override
    public String getUnlocalizedName(ItemStack par1ItemStack)
    {
        int i = par1ItemStack.getItemDamage();

        if (i < 0 || i >= FFxiSet.blockNames.length)
        {
            i = 0;
        }

        return super.getUnlocalizedName() + "." + FFxiSet.blockNames[i];
    }
}