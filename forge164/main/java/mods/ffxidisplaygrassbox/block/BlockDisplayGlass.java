package mods.ffxidisplaygrassbox.block;

import java.util.Iterator;
import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mods.ffxidisplaygrassbox.FFxiDisplayGlassMod;
import mods.ffxidisplaygrassbox.tileentity.TileEntityDisplayGlass;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryLargeChest;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockDisplayGlass extends BlockContainer
{
	public BlockDisplayGlass(int par1, Material par2Material)
	{
		super(par1, par2Material);
		this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
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
		return FFxiDisplayGlassMod.renderType;
	}	

	@Override
	protected boolean canSilkHarvest()
	{
		return true;
	}

	@Override
	public void onBlockPlacedBy(World par1World, int par2, int par3, int par4, EntityLivingBase par5EntityLivingBase, ItemStack par6ItemStack)
	{
		byte b0 = 0;
		int l = MathHelper.floor_double(par5EntityLivingBase.rotationYaw * 4.0F / 360.0F + 0.5D) & 0x3;

		if (l == 0)
		{
			b0 = 2;
		}

		if (l == 1)
		{
			b0 = 5;
		}

		if (l == 2)
		{
			b0 = 3;
		}

		if (l == 3)
		{
			b0 = 4;
		}

		par1World.setBlockMetadataWithNotify(par2, par3, par4, b0, 2);
	}

	@Override
	public boolean onBlockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9)
	{
		if (par1World.isRemote)
		{
			return true;
		}
		else
		{
			IInventory iinventory = this.getInventory(par1World, par2, par3, par4);

			if (iinventory != null)
			{
				par5EntityPlayer.displayGUIChest(iinventory);
			}
			return true;
		}
	}

	@Override
	public void breakBlock(World par1World, int par2, int par3, int par4, int par5, int par6)
	{
		TileEntityDisplayGlass tileentitydisplayglass = (TileEntityDisplayGlass)par1World.getBlockTileEntity(par2, par3, par4);

		if (tileentitydisplayglass != null)
		{
			for (int i = 0; i < tileentitydisplayglass.getSizeInventory(); ++i)
			{
				ItemStack itemstack = tileentitydisplayglass.getStackInSlot(i);

				if (itemstack != null)
				{
					float f = par1World.rand.nextFloat() * 0.8F + 0.1F;
					float f1 = par1World.rand.nextFloat() * 0.8F + 0.1F;
					EntityItem entityitem;

					for (float f2 = par1World.rand.nextFloat() * 0.8F + 0.1F; itemstack.stackSize > 0; par1World.spawnEntityInWorld(entityitem))
					{
						int j = par1World.rand.nextInt(21) + 10;

						if (j > itemstack.stackSize)
						{
							j = itemstack.stackSize;
						}

						itemstack.stackSize -= j;
						entityitem = new EntityItem(par1World, (double)((float)par2 + f), (double)((float)par3 + f1), (double)((float)par4 + f2), new ItemStack(itemstack.itemID, j, itemstack.getItemDamage()));
						float f3 = 0.05F;
						entityitem.motionX = (double)((float)par1World.rand.nextGaussian() * f3);
						entityitem.motionY = (double)((float)par1World.rand.nextGaussian() * f3 + 0.2F);
						entityitem.motionZ = (double)((float)par1World.rand.nextGaussian() * f3);

						if (itemstack.hasTagCompound())
						{
							entityitem.getEntityItem().setTagCompound((NBTTagCompound)itemstack.getTagCompound().copy());
						}
					}
				}
			}
		}

		super.breakBlock(par1World, par2, par3, par4, par5, par6);
	}

	@Override
	public TileEntity createNewTileEntity(World par1World)
	{
		return new TileEntityDisplayGlass();
	}

	@Override
	public boolean hasComparatorInputOverride()
	{
		return true;
	}

	@Override
	public int getComparatorInputOverride(World par1World, int par2, int par3, int par4, int par5)
	{
		return Container.calcRedstoneFromInventory(this.getInventory(par1World, par2, par3, par4));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister par1IconRegister)
	{
		this.blockIcon = par1IconRegister.registerIcon("glass");
	}
	
	public static boolean isOcelotBlockingChest(World par0World, int par1, int par2, int par3)
	{
		Iterator iterator = par0World.getEntitiesWithinAABB(EntityOcelot.class, AxisAlignedBB.getAABBPool().getAABB((double)par1, (double)(par2 + 1), (double)par3, (double)(par1 + 1), (double)(par2 + 2), (double)(par3 + 1))).iterator();
		EntityOcelot entityocelot;

		do
		{
			if (!iterator.hasNext())
			{
				return false;
			}

			EntityOcelot entityocelot1 = (EntityOcelot)iterator.next();
			entityocelot = (EntityOcelot)entityocelot1;
		}
		while (!entityocelot.isSitting());

		return true;
	}

	public IInventory getInventory(World par1World, int par2, int par3, int par4)
	{
		Object object = (TileEntityDisplayGlass)par1World.getBlockTileEntity(par2, par3, par4);
		return object == null ? null : (IInventory)par1World.getBlockTileEntity(par2, par3, par4);
	}
}
