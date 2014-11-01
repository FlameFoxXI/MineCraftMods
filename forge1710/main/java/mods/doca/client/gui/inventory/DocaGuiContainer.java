package mods.doca.client.gui.inventory;

import cpw.mods.fml.relauncher.*;

import mods.doca.core.*;
import mods.doca.entity.*;
import mods.doca.inventory.*;

import net.minecraft.client.gui.inventory.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.*;
import net.minecraft.util.*;

import org.lwjgl.opengl.*;

@SideOnly(Side.CLIENT)
public class DocaGuiContainer extends GuiContainer
{
	private IInventory playerInventory;
	private IInventory baseInventory;
	private EntityPlayer thePlayer;
	private DocaEntityBase theBase;
	private int inventoryRows;
	private float xSize_lo;
	private float ySize_lo;
	private static final ResourceLocation textureInventory = new ResourceLocation(DocaSet.textureInventoryGUI);

	public DocaGuiContainer(EntityPlayer par1entityplayer, DocaEntityBase par2DocaEntityBase)
	{
		super(new DocaContainer(par1entityplayer, par2DocaEntityBase));
		theBase = par2DocaEntityBase;
		thePlayer = par1entityplayer;
		playerInventory = par1entityplayer.inventory;
		baseInventory = par2DocaEntityBase.inventory;
		allowUserInput = false;
		inventoryRows = DocaInventory.HotbarSize;
		this.xSize = 235;
		this.ySize = 222;
	}

	@Override
	public void initGui()
	{
		super.initGui();
		this.mc.thePlayer.openContainer = this.inventorySlots;
		this.guiLeft = (this.width - this.xSize) / 2;
		this.guiTop = (this.height - this.ySize) / 2;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
	{
//		this.drawDefaultBackground();
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		DocaTools.setbindTextureDoca(this.textureInventory);
		int guiGgLeft = this.guiLeft;
		int guiGbTop = this.guiTop;
		int guiSbLeft = this.guiLeft + 35;
		int guiSbTop = this.guiTop  + 115;
		this.drawTexturedModalRect(guiGgLeft, guiGbTop, 0, 0, this.xSize, this.ySize);
		renderSmallBox(this.theBase, guiSbLeft, guiSbTop, 30, (float)guiSbLeft - this.xSize_lo, (float)guiSbTop - this.ySize_lo);
	}

	public static void renderSmallBox(DocaEntityBase par0EDocaEntityBase, int par1, int par2, int par3, float par4, float par5)
	{
		GL11.glEnable(GL11.GL_COLOR_MATERIAL);
		GL11.glPushMatrix();
		GL11.glTranslatef((float)par1, (float)par2, 50.0F);
		GL11.glScalef((float)(-par3), (float)par3, (float)par3);
		GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
		float var6 = par0EDocaEntityBase.renderYawOffset;
		float var7 = par0EDocaEntityBase.rotationYaw;
		float var8 = par0EDocaEntityBase.rotationPitch;
		GL11.glRotatef(135.0F, 0.0F, 1.0F, 0.0F);
		RenderHelper.enableStandardItemLighting();
		GL11.glRotatef(-135.0F, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(-((float)Math.atan((double)(par5 / 40.0F))) * 20.0F, 1.0F, 0.0F, 0.0F);
		par0EDocaEntityBase.renderYawOffset = (float)Math.atan((double)(par4 / 40.0F)) * 20.0F;
		par0EDocaEntityBase.rotationYaw = (float)Math.atan((double)(par4 / 40.0F)) * 40.0F;
		par0EDocaEntityBase.rotationPitch = -((float)Math.atan((double)(par5 / 40.0F))) * 20.0F;
		par0EDocaEntityBase.rotationYawHead = par0EDocaEntityBase.rotationYaw;
		GL11.glTranslatef(0.0F, par0EDocaEntityBase.yOffset, 0.0F);
		RenderManager.instance.playerViewY = 180.0F;
		RenderManager.instance.renderEntityWithPosYaw(par0EDocaEntityBase, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F);
		par0EDocaEntityBase.renderYawOffset = var6;
		par0EDocaEntityBase.rotationYaw = var7;
		par0EDocaEntityBase.rotationPitch = var8;
		GL11.glPopMatrix();
		RenderHelper.disableStandardItemLighting();
		GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		OpenGlHelper.setActiveTexture(OpenGlHelper.lightmapTexUnit);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		OpenGlHelper.setActiveTexture(OpenGlHelper.defaultTexUnit);
	}

	@Override
	public void drawScreen(int par1, int par2, float par3)
	{
		super.drawScreen(par1, par2, par3);
		this.xSize_lo = (float)par1;
		this.ySize_lo = (float)par2;
	}

	@Override
	protected void keyTyped(char c, int i)
	{
		if (i == 1 || i == mc.gameSettings.keyBindInventory.getKeyCode())
		{
			mc.displayGuiScreen(null);
		}
    }
}
