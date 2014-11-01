package mods.doca.client.render.tileentity;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.ChestItemRenderHelper;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import mods.doca.core.*;
import mods.doca.tileentity.*;

public class DocaChestRenderHelper extends ChestItemRenderHelper
{
	private DocaTileEntityChest theDocaChest;


	public DocaChestRenderHelper()
	{
		this.theDocaChest = new DocaTileEntityChest();
	}

	@Override
	public void renderChest(Block block, int i, float f)
	{
		if (block == DocaSet.blockDocaChest)
		{
			TileEntityRenderer.instance.renderTileEntityAt(this.theDocaChest, 0.0D, 0.0D, 0.0D, 0.0F);
		}
		else
		{
			super.renderChest(block, i, f);
		}
	}
}
