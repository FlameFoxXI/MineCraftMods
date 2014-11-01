package mods.ffxisimplepacking.item;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mods.ffxisimplepacking.block.BlockSimplePackingBlock;
import mods.ffxisimplepacking.tools.SpiList;
import mods.ffxisimplepacking.tools.SpiSetting;
import mods.ffxisimplepacking.tools.SpiTools;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class ItemSimplePackingBlock extends ItemBlock
{
	private IIcon itemIcon;
	private Map<Integer, IIcon> overlayIcon = new HashMap<Integer, IIcon>();
	String splist;

	public ItemSimplePackingBlock(Block par1Block)
	{
		super(par1Block);
		this.maxStackSize = 64;
		this.setHasSubtypes(true);
		this.setMaxDamage(0);
		this.splist = SpiSetting.BLOCK;
	}
	
	@Override
	public int getMetadata(int par1)
	{
		return par1;
	}

	@Override
	public void getSubItems(Item par1Item, CreativeTabs par2CreativeTabs, List par3List)
	{
		Object[] key = SpiTools.getList(splist).keySet().toArray();
		Arrays.sort(key);
	
		for(int i = 0 ; i < key.length ; i ++ )
		{
			if (SpiTools.getList(splist).containsKey(key[i]))
			{
				par3List.add(new ItemStack(par1Item, 1, (Integer)key[i]));
			}
		}	
	}

	@Override
	@SideOnly(Side.CLIENT)
	public String getUnlocalizedName(ItemStack par1ItemStack)
	{
		Block block = this.field_150939_a;

		if (block instanceof BlockSimplePackingBlock)
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
	public IIcon getIconFromDamage(int par1)
	{
		if (!SpiTools.getList(splist).containsKey(par1))
		{
			return this.itemIcon;
		}

		if(SpiTools.getList(splist).get(par1).isItemBlock())
		{
			return Item.getItemFromBlock(SpiTools.getList(splist).get(par1).getBlock()).getIconFromDamage(SpiTools.getList(splist).get(par1).getMataData());
		}
		else
		{
			return SpiTools.getList(splist).get(par1).getItem().getIconFromDamage(SpiTools.getList(splist).get(par1).getMataData());
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister par1IconRegister)
	{
		Block block = this.field_150939_a;

		String tmpSrting = SpiSetting.imageBlock[0];
		
		if (block instanceof BlockSimplePackingBlock)
		{
			tmpSrting = SpiSetting.imageBlock[Math.min(SpiSetting.spiTypeMax, ((BlockSimplePackingBlock)block).getPackingType())];
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
	public IIcon getIconFromDamageForRenderPass(int par1, int par2)
	{
		if (par2 == 0)
		{
			if (!SpiTools.getList(splist).containsKey(par1))
			{
				return this.itemIcon;
			}

			if(SpiTools.getList(splist).get(par1).isItemBlock())
			{

				return Item.getItemFromBlock(((Block)SpiTools.getList(splist).get(par1).getBlock())).getIconFromDamage(SpiTools.getList(splist).get(par1).getMataData());
			}
			else
			{
				return SpiTools.getList(splist).get(par1).getItem().getIconFromDamage(SpiTools.getList(splist).get(par1).getMataData());
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
		return super.getSpriteNumber();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean hasEffect(ItemStack par1ItemStack)
	{
		return false;
	}
}
