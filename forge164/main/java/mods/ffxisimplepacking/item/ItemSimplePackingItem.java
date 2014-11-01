package mods.ffxisimplepacking.item;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mods.ffxisimplepacking.block.BlockSimplePackingItem;
import mods.ffxisimplepacking.tools.SpiList;
import mods.ffxisimplepacking.tools.SpiSetting;
import mods.ffxisimplepacking.tools.SpiTools;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;

public class ItemSimplePackingItem extends ItemBlock
{
	private Icon itemIcon;
    private Map<Integer, Icon> overlayIcon = new HashMap<Integer, Icon>();
	String splist;

	public ItemSimplePackingItem(int par1)
	{
		super(par1);
		this.maxStackSize = 64;
		this.setHasSubtypes(true);
		this.setMaxDamage(0);
		this.splist = SpiSetting.ITEM;
	}

	@Override
    public int getMetadata(int par1)
    {
        return par1;
    }

	@Override
	public void getSubItems(int par1, CreativeTabs par2CreativeTabs, List par3List)
	{
		Object[] key = SpiTools.getList(splist).keySet().toArray();
		Arrays.sort(key);
	
		for(int i = 0 ; i < key.length ; i ++ )
		{
			if (SpiTools.getList(splist).containsKey(key[i]))
			{
				par3List.add(new ItemStack(par1, 1, (Integer)key[i]));
			}
		}	
	}

	@Override
	@SideOnly(Side.CLIENT)
	public String getUnlocalizedName(ItemStack par1ItemStack)
	{
		Block block = Block.blocksList[this.getBlockID()];

		if (block instanceof BlockSimplePackingItem)
		{
			if (SpiTools.getList(splist).containsKey(par1ItemStack.getItemDamage()))
			{
				return super.getUnlocalizedName() +"."+ SpiTools.getList(splist).get(par1ItemStack.getItemDamage()).getName();
			}
		}
		return super.getUnlocalizedName() + ".errorItem";
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIconFromDamage(int par1)
	{
		if (!SpiTools.getList(splist).containsKey(par1))
		{
			return this.itemIcon;
		}

		if(SpiTools.getList(splist).get(par1).isItemBlock())
		{
			return Item.itemsList[((Block)SpiTools.getList(splist).get(par1).getBlock()).blockID].getIconFromDamage(SpiTools.getList(splist).get(par1).getMataData());
		}
		else
		{
			return Item.itemsList[((Item)SpiTools.getList(splist).get(par1).getItem()).itemID].getIconFromDamage(SpiTools.getList(splist).get(par1).getMataData());
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister par1IconRegister)
	{
		Block block = Block.blocksList[this.getBlockID()];

		String tmpSrting = SpiSetting.imageBlock[0];
		
		if (block instanceof BlockSimplePackingItem)
		{
			tmpSrting = SpiSetting.imageBlock[Math.min(SpiSetting.spiTypeMax, ((BlockSimplePackingItem)block).getPackingType())];
		}
		this.itemIcon = par1IconRegister.registerIcon(tmpSrting);
	}

	@Override
    @SideOnly(Side.CLIENT)
    public boolean requiresMultipleRenderPasses()
    {
		return SpiSetting.drow_InventoryItemFlat ? true : false ;
    }
    
	@Override
    @SideOnly(Side.CLIENT)
    public Icon getIconFromDamageForRenderPass(int par1, int par2)
    {
		if (par2 == 0)
		{
			if (!SpiTools.getList(splist).containsKey(par1))
			{
				return this.itemIcon;
			}
	
			if(SpiTools.getList(splist).get(par1).isItemBlock())
			{
				return Item.itemsList[((Block)SpiTools.getList(splist).get(par1).getBlock()).blockID].getIconFromDamage(SpiTools.getList(splist).get(par1).getMataData());
			}
			else
			{
				return Item.itemsList[((Item)SpiTools.getList(splist).get(par1).getItem()).itemID].getIconFromDamage(SpiTools.getList(splist).get(par1).getMataData());
			}
		}
		else
		{
			return this.itemIcon;
		}
    }
	
	@Override
    @SideOnly(Side.CLIENT)
    public int getSpriteNumber()
    {
		return SpiSetting.drow_InventoryItemFlat ? 1 : super.getSpriteNumber();
    }
	
	@Override
	@SideOnly(Side.CLIENT)
	public boolean hasEffect(ItemStack par1ItemStack)
	{
		return false;
	}

}
