package mods.ffxidisplaygrassbox.client.render;

import org.lwjgl.opengl.GL11;

import mods.ffxidisplaygrassbox.FFxiDisplayGlassMod;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.IBlockAccess;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public class RenderDisplayGlassBlock implements ISimpleBlockRenderingHandler
{

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer)
	{
		if (modelID == this.getRenderId())
		{
			Tessellator tessellator = Tessellator.instance;

			for (int k = 0; k < 2; ++k)
			{
				if (k == 0)
				{
					renderer.setRenderBounds(0.125D, 0.0D, 0.125D, 0.875D, 0.1875D, 0.875D);
					renderer.setOverrideBlockTexture(renderer.getBlockIcon(Block.obsidian));
				}
				else if (k == 1)
				{
					renderer.setRenderBounds(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
					renderer.setOverrideBlockTexture(renderer.getBlockIcon(Block.glass));
				}

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
			}

			renderer.setRenderBounds(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
			renderer.clearOverrideBlockTexture();
		}
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer)
	{
		if (modelId == this.getRenderId())
		{
			float f = 0.1875F;
			renderer.setOverrideBlockTexture(renderer.getBlockIcon(Block.glass));
			renderer.setRenderBounds(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
			renderer.renderStandardBlock(block, x, y, z);
			renderer.renderAllFaces = true;
			renderer.setOverrideBlockTexture(renderer.getBlockIcon(Block.obsidian));
			renderer.setRenderBounds(0.125D, 0.0062500000931322575D, 0.125D, 0.875D, (double)f, 0.875D);
			renderer.renderStandardBlock(block, x, y, z);
			renderer.renderAllFaces = false;
			renderer.clearOverrideBlockTexture();
			return true;
		}
		return false;
	}

	@Override
	public boolean shouldRender3DInInventory()
	{
		return true;
	}

	@Override
	public int getRenderId()
	{
		return FFxiDisplayGlassMod.renderType;
	}
}
