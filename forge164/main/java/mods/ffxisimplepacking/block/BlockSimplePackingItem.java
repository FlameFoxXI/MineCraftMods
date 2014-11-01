package mods.ffxisimplepacking.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mods.ffxisimplepacking.tileentity.TileEntitySimplePacking;
import mods.ffxisimplepacking.tools.SpiTools;
import mods.ffxisimplepacking.tools.SpiSetting;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockSimplePackingItem extends BlockContainer
{
	int type = 0;
	ItemStack dispItemStack = null;
	String splist;

	public BlockSimplePackingItem(int par1, Material par2Material)
	{
		this(par1, par2Material, 0);
	}

	public BlockSimplePackingItem(int par1, Material par2Material, int type)
	{
		super(par1, par2Material);
		this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
		this.type = type;
		this.splist = SpiSetting.ITEM;
	}
	
	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}

	@Override
	public boolean renderAsNormalBlock()
	{
		return false;
	}

	@Override
	public int getRenderType()
	{
		return SpiSetting.renderType;
	}	

	@Override
	protected boolean canSilkHarvest()
	{
		return true;
	}
	
	@Override
    public boolean hasTileEntity(int metadata)
    {
        return true;
    }   
	
	@Override
	public TileEntity createNewTileEntity(World world)
	{
		return new TileEntitySimplePacking();
	}
	
	@Override
    public TileEntity createTileEntity(World world, int metadata)
    {
		if (dispItemStack != null)
		{
			return new TileEntitySimplePacking(dispItemStack, this.type);
		}
		return new TileEntitySimplePacking();
    }

	@Override
    public int onBlockPlaced(World par1World, int par2, int par3, int par4, int par5, float par6, float par7, float par8, int par9)
    {
		this.dispItemStack = null;
		if (SpiTools.getList(splist).containsKey(par9))
		{
			if(!SpiTools.getList(splist).get(par9).isItemBlock())
			{
				Item item = (Item)SpiTools.getList(splist).get(par9).getItem();
				this.dispItemStack = new ItemStack(item, SpiTools.getSpiStask(splist, type), SpiTools.getList(splist).get(par9).getMataData());
			}
		}
		return par9;
    }

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister par1IconRegister)
	{
		this.blockIcon = par1IconRegister.registerIcon(SpiSetting.imageBlock[Math.min(SpiSetting.imageBlock.length - 1, this.type)]);
	}

	public int getPackingType()
	{
		return this.type;
	}
	
	@Override
	public void breakBlock(World par1World, int par2, int par3, int par4, int par5, int par6)
	{
		TileEntitySimplePacking tileEntity = (TileEntitySimplePacking)par1World.getBlockTileEntity(par2, par3, par4);

		if (tileEntity != null)
		{
			ItemStack item = tileEntity.getDispItemStack();

			if (item != null)
			{
				int type = Math.min(SpiSetting.spiTypeMax, tileEntity.getTypePacking());

				if (SpiSetting.drop_PackingBlockDrop)
				{
					this.randomDropCobe(par1World, par2, par3, par4, par5, par6, item, type);
				}
				else
				{
					if (type > 1 && item.getMaxStackSize() == 1)
					{
						this.randomDropCobe(par1World, par2, par3, par4, par5, par6, item, type);
					}
					else
					{
						this.randomDropItem(par1World, par2, par3, par4, par5, par6, item, type);
					}
				}
			}
		}
		super.breakBlock(par1World, par2, par3, par4, par5, par6);
	}

	private void randomDrop(World par1World, int par2, int par3, int par4, int par5, int par6, ItemStack item)
	{
		if(par1World != null && item != null)
		{
			float f = par1World.rand.nextFloat() * 0.8F + 0.1F;
			float f1 = par1World.rand.nextFloat() * 0.8F + 0.1F;
			EntityItem entityitem;

			for (float f2 = par1World.rand.nextFloat() * 0.8F + 0.1F; item.stackSize > 0; par1World.spawnEntityInWorld(entityitem))
			{
				int j = par1World.rand.nextInt(21) + 10;

				if (j > item.stackSize)
				{
					j = item.stackSize;
				}

				item.stackSize -= j;
				entityitem = new EntityItem(par1World, (double)((float)par2 + f), (double)((float)par3 + f1), (double)((float)par4 + f2), new ItemStack(item.itemID, j, item.getItemDamage()));
				float f3 = 0.05F;
				entityitem.motionX = (double)((float)par1World.rand.nextGaussian() * f3);
				entityitem.motionY = (double)((float)par1World.rand.nextGaussian() * f3 + 0.2F);
				entityitem.motionZ = (double)((float)par1World.rand.nextGaussian() * f3);

				if (item.hasTagCompound())
				{
					entityitem.getEntityItem().setTagCompound((NBTTagCompound)item.getTagCompound().copy());
				}
			}
		}
	}
	
	private void randomDropCobe(World par1World, int par2, int par3, int par4, int par5, int par6, ItemStack item, int type)
	{
		int id = SpiTools.getSpiBlockMetaData(splist, item.itemID, item.getItemDamage());
		item = new ItemStack(SpiTools.getBlockInstance(splist, type).blockID, 1, id);
		randomDrop(par1World, par2, par3, par4, par5, par6, item);
	}

	private void randomDropItem(World par1World, int par2, int par3, int par4, int par5, int par6, ItemStack item, int type)
	{
		int value = SpiSetting.stakBlock[type];
		int max = value / item.getMaxStackSize();
		int amari = value % item.getMaxStackSize();

		for (int i = 0; i < max; i++)
		{
			item.stackSize = item.getMaxStackSize();
			randomDrop(par1World, par2, par3, par4, par5, par6, item);
		}

		if(item.getMaxStackSize() != 1 && amari != 0)
		{
			item.stackSize = amari;
			randomDrop(par1World, par2, par3, par4, par5, par6, item);
		}
	}
}
