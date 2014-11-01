package mods.ffxiwatereraser.proxy;

import mods.ffxiwatereraser.core.FFxiSet;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class FFxiClientProxy extends FFxiCommonProxy
{
	@Override
	public void registerModRenders()
	{
	}

	@Override
	public void registerModRenderEntitys()
	{
	}

	@Override
	public void registerModRenderTileEntitys()
	{
	}
	
	@Override
	public void registerModSideHandlers()
	{
		FFxiSet.registerModClientHandlers();
	}
}
