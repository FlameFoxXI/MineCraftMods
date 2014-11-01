package mods.ffxisimplepacking.proxy;

import mods.ffxisimplepacking.render.RenderSimplePackingBlock;
import mods.ffxisimplepacking.render.tileentity.RendererTileEntitySimplePacking;
import mods.ffxisimplepacking.tileentity.TileEntitySimplePacking;
import mods.ffxisimplepacking.tools.SpiSetting;
import net.minecraftforge.client.MinecraftForgeClient;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.relauncher.SideOnly;
import cpw.mods.fml.relauncher.Side;

@SideOnly(Side.CLIENT)
public class ClientProxySimplePacking extends CommonProxySimplePacking
{
	public void registerRenderInformation()
	{
		SpiSetting.renderType = RenderingRegistry.getNextAvailableRenderId();
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntitySimplePacking.class, new RendererTileEntitySimplePacking());
		RenderingRegistry.registerBlockHandler(new RenderSimplePackingBlock());
	}
}