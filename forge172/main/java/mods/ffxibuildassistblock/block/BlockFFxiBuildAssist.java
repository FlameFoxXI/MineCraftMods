package mods.ffxibuildassistblock.block;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mods.ffxibuildassistblock.core.FFxiSet;
import mods.ffxibuildassistblock.tileentity.TileEntityFFxiBuildAssist;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;

public class BlockFFxiBuildAssist extends Block implements ITileEntityProvider
{
    public static final String[] blockDamage = new String[] { FFxiSet.image_AssistScaffoldWood, FFxiSet.image_AssistScaffoldIron };
    public static final String[] blockFromSide = new String[] { "top", "side", "bottom" };
    @SideOnly(Side.CLIENT)
    private IIcon[][] iconFFxiBuildAssist;

    private final Random random = new Random();

	public BlockFFxiBuildAssist(Material par1Material)
	{
		super(par1Material);
		this.setLightLevel(1.0F);
		this.setHardness(50.0F);
		this.setResistance(2000.0F);
		this.setStepSound(Block.soundTypePiston);
		this.setBlockTextureName("obsidian");
	}

	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}
	
	@Override
    public int quantityDropped(Random par1Random)
    {
        return 0;
    }
	
	@Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int par1, int par2)
    {
		int icon = (par1 == 1 ? 0 : (par1 == 0 ? 2 : 1));

        if (par1 < 0 || par1 >= this.blockDamage.length)
        {
        	par1 = 0;
        }
        
        return this.iconFFxiBuildAssist[par2][icon];
    }
    
	@Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister par1IconRegister)
    {
        this.iconFFxiBuildAssist = new IIcon[blockDamage.length][blockFromSide.length];

        for (int i = 0; i < this.blockDamage.length; ++i)
        {
            for (int j = 0; j < this.blockFromSide.length; ++j)
            {
            	this.iconFFxiBuildAssist[i][j] = par1IconRegister.registerIcon(this.getTextureName() + "_" + blockDamage[i] + "_" + blockFromSide[j]);
            }
        }
    }
	
	@Override
	@SideOnly(Side.CLIENT)
    public void getSubBlocks(Item par1Item, CreativeTabs par2CreativeTabs, List par3List)
    {
		par3List.add(new ItemStack(par1Item, 1, 0));
		par3List.add(new ItemStack(par1Item, 1, 1));
    }	
	
    @Override
    public void onBlockDestroyedByPlayer(World par1World, int par2, int par3, int par4, int par5)
	{
		super.onBlockDestroyedByPlayer(par1World, par2, par3, par4, par5);

		if (!par1World.isRemote)
		{
			BlockFFxiBuildAssistCore.tryToChainBreaken(par1World, par2, par3, par4, par5);
		}
    }
	
	@Override
	public float getPlayerRelativeBlockHardness(EntityPlayer par1EntityPlayer, World par2World, int par3, int par4, int par5)
	{
		Item tmpItem = Item.getItemFromBlock(this);

		if (tmpItem != null && par1EntityPlayer.getCurrentEquippedItem() != null && par1EntityPlayer.getCurrentEquippedItem().getItem() != null
				&& par1EntityPlayer.getCurrentEquippedItem().getItem() == tmpItem)
		{
			return 0.3F;
		}
		else
		{
			return super.getPlayerRelativeBlockHardness(par1EntityPlayer, par2World, par3, par4, par5);
		}
	}

	@Override
	public boolean canEntityDestroy(IBlockAccess world, int x, int y, int z, Entity entity)
	{
		if (entity instanceof EntityPlayer)
		{
			return true;
		}
		return false;
	}

	@Override
	public TileEntity createNewTileEntity(World var1, int var2)
	{
		return new TileEntityFFxiBuildAssist();
	}
	
	@Override
    public void breakBlock(World par1World, int par2, int par3, int par4, Block par5Block, int par6)
    {
		float f = this.random.nextFloat() * 0.8F + 0.1F;
		float f1 = 0.05F;

		EntityItem entityitem = new EntityItem(par1World, (double)((float)par2 + f), (double)((float)par3 + f), (double)((float)par4 + f), new ItemStack(par5Block, 1, par6));

		entityitem.motionX = (double)((float)this.random.nextGaussian() * f1);
		entityitem.motionY = (double)((float)this.random.nextGaussian() * f1 + 0.2F);
		entityitem.motionZ = (double)((float)this.random.nextGaussian() * f1);

		par1World.spawnEntityInWorld(entityitem);

		super.breakBlock(par1World, par2, par3, par4, par5Block, par6);
	}

	@Override
    public boolean isLadder(IBlockAccess world, int x, int y, int z, EntityLivingBase entity)
    {
        return false;
    }
}
