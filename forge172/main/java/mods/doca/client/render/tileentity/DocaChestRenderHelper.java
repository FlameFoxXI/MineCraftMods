package mods.doca.client.render.tileentity;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.tileentity.TileEntityRendererChestHelper;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import mods.doca.core.*;
import mods.doca.tileentity.*;

//public class DocaChestRenderHelper extends ChestItemRenderHelper
public class DocaChestRenderHelper extends TileEntityRendererChestHelper
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
			TileEntityRendererDispatcher.instance.renderTileEntityAt(this.theDocaChest, 0.0D, 0.0D, 0.0D, 0.0F);
		}
		else
		{
			super.renderChest(block, i, f);
		}
	}
}
