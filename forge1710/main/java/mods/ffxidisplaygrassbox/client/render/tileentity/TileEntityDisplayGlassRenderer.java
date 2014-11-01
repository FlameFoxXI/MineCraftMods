package mods.ffxidisplaygrassbox.client.render.tileentity;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mods.ffxidisplaygrassbox.FFxiDisplayGlassMod;
import mods.ffxidisplaygrassbox.tileentity.TileEntityDisplayGlass;
import java.io.PrintStream;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

@SideOnly(Side.CLIENT)
public class TileEntityDisplayGlassRenderer extends TileEntitySpecialRenderer
{
	private float sizeEntity = 0.9F;
	private float sizeEntityRStop = 1.0F;
	private float sizeEntityForStack = 0.7F;
	private float sizeEntityForStackRStop = 0.8F;

	public void renderTileEntityDisplayGlassAt(TileEntityDisplayGlass par1TileEntity, double par2, double par4, double par6, float par8)
	{
		EntityItem entityItem = par1TileEntity.getDisplayEntityItem();

		if (entityItem != null)
		{
			int i = 0;
			int pos = 2;

			if (par1TileEntity.hasWorldObj())
			{
				i = par1TileEntity.getBlockMetadata();

				i = Math.max(Math.min(i, 5), 2);

				pos = 3;

				if (i == 3)
				{
					pos = 0;
				}

				if (i == 4)
				{
					pos = 1;
				}

				if (i == 5)
				{
					pos = 2;
				}
			}

			float size = this.sizeEntity;

			if ((entityItem.getEntityItem().getItem() instanceof ItemBlock))
			{
				if (!FFxiDisplayGlassMod.blockRotationItem)
				{
					par8 = 0.0F;
					entityItem.age = 0;
					entityItem.hoverStart = -1.55F;

					System.out.println(pos);

					if (pos == 1)
					{
						entityItem.hoverStart = 3.15F;
					}
					else if (pos == 2)
					{
						entityItem.hoverStart = 0.0F;
					}
					else if (pos == 3)
					{
						entityItem.hoverStart = 1.55F;
					}

					if (entityItem.getEntityItem().stackSize != 1)
					{
						size = this.sizeEntityForStackRStop;
					}
					else
					{
						size = this.sizeEntityRStop;
					}

				}
				else if (entityItem.getEntityItem().stackSize != 1)
				{
					size = this.sizeEntityForStack;
				}
				else
				{
					size = this.sizeEntity;
				}

			}
			else if (!FFxiDisplayGlassMod.blockRotationItem)
			{
				par8 = 0.0F;
				entityItem.age = 0;
				entityItem.hoverStart = 3.15F;

				System.out.println(pos);

				if ((pos == 1) || (pos == 2))
				{
					entityItem.hoverStart = 1.55F;
				}
				else if (pos == 3)
				{
					entityItem.hoverStart = 3.15F;
				}

				if (entityItem.getEntityItem().stackSize != 1)
				{
					size = this.sizeEntityForStackRStop;
				}
				else
				{
					size = this.sizeEntityRStop;
				}

			}
			else if (entityItem.getEntityItem().stackSize != 1)
			{
				size = this.sizeEntityForStack;
			}
			else
			{
				size = this.sizeEntity;
			}

			GL11.glPushMatrix();
			GL11.glTranslatef((float)par2 + 0.5F, (float)par4 + 0.4F, (float)par6 + 0.5F);
			GL11.glScalef(size, size, size);
			RenderManager.instance.renderEntityWithPosYaw(entityItem, 0.0D, 0.0D, 0.0D, 0.0F, par8);
			GL11.glPopMatrix();
		}
	}

	@Override
	public void renderTileEntityAt(TileEntity par1TileEntity, double par2, double par4, double par6, float par8)
	{
		this.renderTileEntityDisplayGlassAt((TileEntityDisplayGlass)par1TileEntity, par2, par4, par6, par8);
	}
}
