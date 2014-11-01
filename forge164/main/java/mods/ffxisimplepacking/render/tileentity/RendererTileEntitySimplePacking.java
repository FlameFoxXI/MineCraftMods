package mods.ffxisimplepacking.render.tileentity;

import org.lwjgl.opengl.GL11;

import mods.ffxisimplepacking.render.RenderSimplePackingBlock;
import mods.ffxisimplepacking.render.RenderSimplePackingItem;
import mods.ffxisimplepacking.tileentity.TileEntitySimplePacking;
import mods.ffxisimplepacking.tools.SpiSetting;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.crash.CrashReport;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ReportedException;

public class RendererTileEntitySimplePacking extends TileEntitySpecialRenderer
{
	private float sizeEntityItem = 0.9F;
	private float sizeEntityBlock = 1.1F;
	private float sizeEntityBlock3D = 1.4F;
	
	RenderSimplePackingItem renderItem = new RenderSimplePackingItem();
	
	public void renderTileEntitySimplePackingAt(TileEntitySimplePacking par1TileEntity, double par2, double par4, double par6, float par8)
	{
		EntityItem entityItem = par1TileEntity.getDisplayEntityItem();

		if(entityItem != null)
		{
			
			float size = sizeEntityItem;
			double height = -0.23D;

			if (entityItem.getEntityItem().getItem() instanceof ItemBlock)
			{
				size = sizeEntityBlock;
				
				ItemBlock itemBlock = (ItemBlock)entityItem.getEntityItem().getItem();
				if (RenderBlocks.renderItemIn3d(Block.blocksList[itemBlock.getBlockID()].getRenderType()))
				{
					size = sizeEntityBlock3D;
					height = -0.025D;
				}
			}

			if(SpiSetting.drow_StackItemAtOnce)
			{
				entityItem.getEntityItem().stackSize = 1;
			}
			
			if (entityItem != null)
			{
				GL11.glPushMatrix();
				GL11.glTranslatef((float)par2 + 0.5F, (float)par4 + 0.4F, (float)par6 + 0.5F);
				GL11.glScalef(size, size, size);
				RenderSimplePackingBlock.renderSimplePackingItem(entityItem, 0.0D, height, 0.0D, 0.0F, par8);
				GL11.glPopMatrix();
			}

		}
	}

	@Override
	public void renderTileEntityAt(TileEntity par1TileEntity, double par2, double par4, double par6, float par8)
	{
		this.renderTileEntitySimplePackingAt((TileEntitySimplePacking)par1TileEntity, par2, par4, par6, par8);
	}
}
