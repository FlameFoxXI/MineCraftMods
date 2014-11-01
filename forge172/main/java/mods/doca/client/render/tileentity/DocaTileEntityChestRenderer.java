package mods.doca.client.render.tileentity;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import java.util.Calendar;

import mods.doca.core.DocaSet;
import mods.doca.tileentity.DocaTileEntityChest;
import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.client.model.ModelChest;
import net.minecraft.client.model.ModelLargeChest;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

@SideOnly(Side.CLIENT)
public class DocaTileEntityChestRenderer extends TileEntitySpecialRenderer
{
	private static final ResourceLocation RES_CHRISTMAS_SINGLE = new ResourceLocation(DocaSet.texture_TileEntityChestChristmas);
	private static final ResourceLocation RES_NORMAL_SINGLE = new ResourceLocation(DocaSet.texture_TileEntityChest);

	private ModelChest chestModel = new ModelChest();
	private boolean isChristmas;

	public DocaTileEntityChestRenderer()
	{
		Calendar calendar = Calendar.getInstance();

		if (calendar.get(2) + 1 == 12 && calendar.get(5) >= 24 && calendar.get(5) <= 26)
		{
			this.isChristmas = true;
		}
	}

	public void renderTileEntityChestAt(DocaTileEntityChest par1Chest, double par2, double par4, double par6, float par8)
	{
		int i = 0;
		ModelChest modelchest = this.chestModel;

		if (par1Chest.hasWorldObj())
		{
			i = par1Chest.getBlockMetadata();
		}

		if (this.isChristmas)
		{
			this.bindTexture(RES_CHRISTMAS_SINGLE);
		}
		else
		{
			this.bindTexture(RES_NORMAL_SINGLE);
		}

		GL11.glPushMatrix();
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glTranslatef((float)par2, (float)par4 + 1.0F, (float)par6 + 1.0F);
		GL11.glScalef(1.0F, -1.0F, -1.0F);
		GL11.glTranslatef(0.5F, 0.5F, 0.5F);
		short short1 = 0;

		if (i == 2)
		{
			short1 = 180;
		}

		if (i == 3)
		{
			short1 = 0;
		}

		if (i == 4)
		{
			short1 = 90;
		}

		if (i == 5)
		{
			short1 = -90;
		}


		GL11.glRotatef((float)short1, 0.0F, 1.0F, 0.0F);
		GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
		float f1 = par1Chest.prevLidAngle + (par1Chest.lidAngle - par1Chest.prevLidAngle) * par8;

		f1 = 1.0F - f1;
		f1 = 1.0F - f1 * f1 * f1;
		modelchest.chestLid.rotateAngleX = -(f1 * (float)Math.PI / 2.0F);
		modelchest.renderAll();
		GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		GL11.glPopMatrix();
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	}

	@Override
	public void renderTileEntityAt(TileEntity par1TileEntity, double par2, double par4, double par6, float par8)
	{
		this.renderTileEntityChestAt((DocaTileEntityChest)par1TileEntity, par2, par4, par6, par8);
	}
}
