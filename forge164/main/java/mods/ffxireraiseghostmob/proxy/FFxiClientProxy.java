package mods.ffxireraiseghostmob.proxy;

import mods.ffxireraiseghostmob.client.render.RenderReraiseGhost;
import mods.ffxireraiseghostmob.core.FFxiSet;
import mods.ffxireraiseghostmob.entity.EntityReraiseGhost;
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
		RenderingRegistry.registerEntityRenderingHandler(EntityReraiseGhost.class, new RenderReraiseGhost());
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
