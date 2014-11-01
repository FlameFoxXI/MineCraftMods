package mods.ffxibuildassistblock.block;

import mods.ffxibuildassistblock.core.FFxiSet;
import mods.ffxibuildassistblock.tileentity.TileEntityFFxiBuildAssist;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockFFxiBuildAssistCore
{
	protected static final int[] blockSerachTableX = {  0,  0, -1,  1,  0,  0 };
	protected static final int[] blockSerachTableY = {  0, -1,  0,  0,  1,  0 };
	protected static final int[] blockSerachTableZ = { -1,  0,  0,  0,  0,  1 };
	protected static final int[] blockSerachTableS = { -1,  1 };
	
	public static void tryToChainBreaken(World par1World, int par2, int par3, int par4, int par5)
	{
		if (par5 == 1)
		{
			for (int serach = 0; serach < blockSerachTableX.length; serach++)
			{
				int searchPosX = par2 + blockSerachTableX[serach];
				int searchPosY = par3 + blockSerachTableY[serach];
				int searchPosZ = par4 + blockSerachTableZ[serach];
	
				int tmpBlockId = par1World.getBlockId(searchPosX, searchPosY, searchPosZ);
	
				if(tmpBlockId == FFxiSet.blockFFxiBuildAssist.blockID && Block.blocksList[FFxiSet.blockFFxiBuildAssist.blockID] instanceof BlockFFxiBuildAssist)
				{
					TileEntity tmpTileEntity = par1World.getBlockTileEntity(searchPosX, searchPosY, searchPosZ);
					
					if (tmpTileEntity != null && tmpTileEntity instanceof TileEntityFFxiBuildAssist)
					{
						int tmpMetaData = par1World.getBlockMetadata(searchPosX, searchPosY, searchPosZ);
						
						if (tmpMetaData == par5)
						{
							TileEntityFFxiBuildAssist tmpTileEntityFFxiBuildAssist = (TileEntityFFxiBuildAssist)tmpTileEntity;
							tmpTileEntityFFxiBuildAssist.setBloken(true);
						}
					}
				}
			}
		}
		else
		{
			for (int serach = 0; serach < blockSerachTableS.length; serach++)
			{
				int searchPosX = par2;
				int searchPosY = par3 + blockSerachTableS[serach];
				int searchPosZ = par4;
	
				int tmpBlockId = par1World.getBlockId(searchPosX, searchPosY, searchPosZ);
	
				if(tmpBlockId == FFxiSet.blockFFxiBuildAssist.blockID && Block.blocksList[FFxiSet.blockFFxiBuildAssist.blockID] instanceof BlockFFxiBuildAssist)
				{
					TileEntity tmpTileEntity = par1World.getBlockTileEntity(searchPosX, searchPosY, searchPosZ);
					if (tmpTileEntity != null && tmpTileEntity instanceof TileEntityFFxiBuildAssist)
					{
						int tmpMetaData = par1World.getBlockMetadata(searchPosX, searchPosY, searchPosZ);
						
						if (tmpMetaData == par5)
						{
							TileEntityFFxiBuildAssist tmpTileEntityFFxiBuildAssist = (TileEntityFFxiBuildAssist)tmpTileEntity;
							tmpTileEntityFFxiBuildAssist.setBloken(true);
						}
					}
				}
			}
		}
	}
	
	public static void tryToBreaken(World par1World, int par2, int par3, int par4)
	{
		int tmpBlockId = par1World.getBlockId(par2, par3, par4);

		if(tmpBlockId == FFxiSet.blockFFxiBuildAssist.blockID && Block.blocksList[FFxiSet.blockFFxiBuildAssist.blockID] instanceof BlockFFxiBuildAssist)
		{
			TileEntity tmpTileEntity = par1World.getBlockTileEntity(par2, par3, par4);
			
			if (tmpTileEntity != null && tmpTileEntity instanceof TileEntityFFxiBuildAssist)
			{
				TileEntityFFxiBuildAssist tmpTileEntityFFxiBuildAssist = (TileEntityFFxiBuildAssist)tmpTileEntity;
				
				if (tmpTileEntityFFxiBuildAssist.isBloken())
				{
					par1World.setBlockToAir(par2, par3, par4);
				}
			}
		}
	}
}
