package mods.ffxisimplepacking.render;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import mods.ffxisimplepacking.block.BlockSimplePackingBlock;
import mods.ffxisimplepacking.block.BlockSimplePackingItem;
import mods.ffxisimplepacking.tools.SpiList;
import mods.ffxisimplepacking.tools.SpiSetting;
import mods.ffxisimplepacking.tools.SpiTools;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ReportedException;
import net.minecraft.world.IBlockAccess;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public class RenderSimplePackingBlock implements ISimpleBlockRenderingHandler
{
	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer)
	{
		if (modelID == this.getRenderId())
		{
			for (int i = 0; i < 2; i++)
			{
				if (i == 0)
				{
					this.renderSpiBlock(block, metadata, modelID, renderer);
				}
				else
				{
					this.renderSpiItem(block, metadata, modelID, renderer);
				}
			}
		}
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer)
	{
		if (modelId == this.getRenderId())
		{
			float f = 0.1875F;
			renderer.setOverrideBlockTexture(renderer.getBlockIcon(block));
			renderer.setRenderBounds(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
			renderer.renderStandardBlock(block, x, y, z);
			renderer.renderAllFaces = true;
			renderer.clearOverrideBlockTexture();
			return true;
		}
		return false;
	}

	@Override
	public boolean shouldRender3DInInventory()
	{
		return SpiSetting.drow_InventoryItemFlat ? false : true;
	}

	@Override
	public int getRenderId()
	{
		return SpiSetting.renderType;
	}

	private void renderSpiBlock(Block block, int metadata, int modelID, RenderBlocks renderer)
	{
		Tessellator tessellator = Tessellator.instance;

		renderer.setRenderBounds(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
		renderer.setOverrideBlockTexture(renderer.getBlockIcon(block));

		GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, -1.0F, 0.0F);
		renderer.renderFaceYNeg(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 0, metadata));
		tessellator.draw();
		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, 1.0F, 0.0F);
		renderer.renderFaceYPos(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 1, metadata));
		tessellator.draw();
		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, 0.0F, -1.0F);
		renderer.renderFaceZNeg(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 2, metadata));
		tessellator.draw();
		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, 0.0F, 1.0F);
		renderer.renderFaceZPos(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 3, metadata));
		tessellator.draw();
		tessellator.startDrawingQuads();
		tessellator.setNormal(-1.0F, 0.0F, 0.0F);
		renderer.renderFaceXNeg(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 4, metadata));
		tessellator.draw();
		tessellator.startDrawingQuads();
		tessellator.setNormal(1.0F, 0.0F, 0.0F);
		renderer.renderFaceXPos(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 5, metadata));
		tessellator.draw();
		GL11.glTranslatef(0.5F, 0.5F, 0.5F);

		renderer.setRenderBounds(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
		renderer.clearOverrideBlockTexture();
	}

	private void renderSpiItem(Block block, int metadata, int modelID, RenderBlocks renderer)
	{
		EntityItem entiy = null;
		double pos = -0.1D;
		float size = 3.0F;

		if (block instanceof BlockSimplePackingBlock)
		{
			if (SpiTools.getList(SpiSetting.BLOCK).containsKey(metadata))
			{
				if (SpiTools.getList(SpiSetting.BLOCK).get(metadata) != null && SpiTools.getList(SpiSetting.BLOCK).get(metadata).isItemBlock())
				{
					Block item = SpiTools.getList(SpiSetting.BLOCK).get(metadata).getBlock();
					int damage = SpiTools.getList(SpiSetting.BLOCK).get(metadata).getMataData();

					entiy = new EntityItem(null, 0.0D, 0.0D, 0.0D, new ItemStack(item.blockID, 1, damage));

					if (!RenderBlocks.renderItemIn3d(Block.blocksList[item.blockID].getRenderType()))
					{
						pos = -0.3D;
						size = 2.0F;

					}
					if (item.blockID == 69 || item.blockID == 50)
					{
						pos = -0.1D;
						size = 2.5F;
					}
				}
			}
		}
		else if (block instanceof BlockSimplePackingItem)
		{
			if (SpiTools.getList(SpiSetting.ITEM).containsKey(metadata))
			{
				if (SpiTools.getList(SpiSetting.ITEM).get(metadata) != null && !SpiTools.getList(SpiSetting.ITEM).get(metadata).isItemBlock())
				{
					Item item = SpiTools.getList(SpiSetting.ITEM).get(metadata).getItem();
					int damage = SpiTools.getList(SpiSetting.ITEM).get(metadata).getMataData();

					entiy = new EntityItem(null, 0.0D, 0.0D, 0.0D, new ItemStack(item.itemID, 1, damage));

					pos = -0.3D;
					size = 2.0F;
				}
			}
		}

		if (entiy != null)
		{
			entiy.age = 0;
			entiy.hoverStart = 0.0F;

			GL11.glPushMatrix();
			GL11.glTranslatef(0.0F, 0.0F, 0.0F);
			GL11.glScalef(size, size, size);

			this.renderSimplePackingItem(entiy, 0.0D, pos, 0.0D, 0.0F, 0.0F);
			GL11.glPopMatrix();
		}
	}
	
	public static void renderSimplePackingItem(Entity par1Entity, double par2, double par4, double par6, float par8, float par9)
	{
		RenderManager rendermanager = RenderManager.instance;
		RenderSimplePackingItem renderitem = new RenderSimplePackingItem();

		if (rendermanager.renderEngine != null && par1Entity != null)
		{
			try
			{
				renderitem.doRender(par1Entity, par2, par4, par6, par8, par9);
			}
			catch (Throwable throwable1)
			{
				throw new ReportedException(CrashReport.makeCrashReport(throwable1, "Rendering entity in world"));
			}
		}
	}
}
