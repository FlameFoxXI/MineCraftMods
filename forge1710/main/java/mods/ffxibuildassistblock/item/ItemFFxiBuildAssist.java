package mods.ffxibuildassistblock.item;

import mods.ffxibuildassistblock.core.FFxiSet;
import net.minecraft.block.Block;
import net.minecraft.item.ItemMultiTexture;

public class ItemFFxiBuildAssist extends ItemMultiTexture
{
    public ItemFFxiBuildAssist(Block par1Block)
    {
        super(par1Block, par1Block, FFxiSet.blockNames);
    }
}