package mods.ffxidisplaygrassbox.proxy;

import mods.ffxidisplaygrassbox.FFxiDisplayGlassMod;
import mods.ffxidisplaygrassbox.client.render.RenderDisplayGlassBlock;
import mods.ffxidisplaygrassbox.client.render.tileentity.TileEntityDisplayGlassRenderer;
import mods.ffxidisplaygrassbox.tileentity.TileEntityDisplayGlass;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.relauncher.SideOnly;
import cpw.mods.fml.relauncher.Side;

@SideOnly(Side.CLIENT)
public class ClientProxy extends CommonProxy
{
	@Override
	public void registerRender()
	{
		FFxiDisplayGlassMod.renderType = RenderingRegistry.getNextAvailableRenderId();
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityDisplayGlass.class, new TileEntityDisplayGlassRenderer());
		RenderingRegistry.registerBlockHandler(new RenderDisplayGlassBlock());
	}
}