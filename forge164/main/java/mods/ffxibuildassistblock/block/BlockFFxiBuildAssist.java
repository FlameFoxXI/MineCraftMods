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
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;

public class BlockFFxiBuildAssist extends Block implements ITileEntityProvider
{
    public static final String[] blockDamage = new String[] { FFxiSet.image_AssistScaffoldWood, FFxiSet.image_AssistScaffoldIron };
    public static final String[] blockFromSide = new String[] { "top", "side", "bottom" };
    @SideOnly(Side.CLIENT)
    private Icon[][] iconFFxiBuildAssist;

    private final Random random = new Random();

	public BlockFFxiBuildAssist(int par1, Material par1Material)
	{
		super(par1, par1Material);
		this.setLightValue(1.0F);
		this.setHardness(50.0F);
		this.setResistance(2000.0F);
		this.setStepSound(Block.soundStoneFootstep);
		this.setTextureName("obsidian");
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
    public Icon getIcon(int par1, int par2)
    {
		par1 = (par1 == 1 ? 0 : (par1 == 0 ? 2 : 1));

	    if (par2 < 0 || par2 >= this.blockDamage.length)
        {
    	   par2 = 0;
        }
        return this.iconFFxiBuildAssist[par2][par1];
    }
    
	@Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister par1IconRegister)
    {
        this.iconFFxiBuildAssist = new Icon[blockDamage.length][blockFromSide.length];

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
    public void getSubBlocks(int par1, CreativeTabs par2CreativeTabs, List par3List)
    {
		par3List.add(new ItemStack(par1, 1, 0));
		par3List.add(new ItemStack(par1, 1, 1));
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
		if (par1EntityPlayer.getCurrentEquippedItem() != null && par1EntityPlayer.getCurrentEquippedItem().getItem() != null
				&& par1EntityPlayer.getCurrentEquippedItem().getItem().itemID == FFxiSet.blockFFxiBuildAssist.blockID)
		{
			return 0.3F;
		}
		else
		{
			return super.getPlayerRelativeBlockHardness(par1EntityPlayer, par2World, par3, par4, par5);
		}
	}

	@Override
	public boolean canEntityDestroy(World world, int x, int y, int z, Entity entity)
	{
		if (entity instanceof EntityPlayer)
		{
			return true;
		}
		return false;
	}

	@Override
	public TileEntity createNewTileEntity(World var1)
	{
		return new TileEntityFFxiBuildAssist();
	}
	
	@Override
    public void breakBlock(World par1World, int par2, int par3, int par4, int par5, int par6)
    {
		float f = this.random.nextFloat() * 0.8F + 0.1F;
		float f1 = 0.05F;

		EntityItem entityitem = new EntityItem(par1World, (double)((float)par2 + f), (double)((float)par3 + f), (double)((float)par4 + f), new ItemStack(par5, 1, par6));

		entityitem.motionX = (double)((float)this.random.nextGaussian() * f1);
		entityitem.motionY = (double)((float)this.random.nextGaussian() * f1 + 0.2F);
		entityitem.motionZ = (double)((float)this.random.nextGaussian() * f1);

		par1World.spawnEntityInWorld(entityitem);

		super.breakBlock(par1World, par2, par3, par4, par5, par6);
	}

	@Override
	public boolean isLadder(World world, int x, int y, int z, EntityLivingBase entity)
	{
		return true;
	}
}
