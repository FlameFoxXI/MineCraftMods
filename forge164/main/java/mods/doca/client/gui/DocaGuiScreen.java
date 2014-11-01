package mods.doca.client.gui;

import cpw.mods.fml.relauncher.*;

import mods.doca.*;
import mods.doca.core.*;
import mods.doca.entity.*;

import net.minecraft.client.gui.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.item.*;
import net.minecraft.network.packet.*;
import net.minecraft.src.*;
import net.minecraft.util.*;

import java.io.*;
import java.util.*;

import org.lwjgl.input.*;
import org.lwjgl.opengl.*;

@SideOnly(Side.CLIENT)
public class DocaGuiScreen extends GuiScreen
{
	private float xSize_lo;
	private float ySize_lo;
	private DocaEntityBase theBase;
	private GuiTextField theGuiTextField;
	private static final ResourceLocation field_110421_t = new ResourceLocation(DocaSet.textureSettingGUI);

	public DocaGuiScreen(DocaEntityBase par1DocaEntityBase)
	{
		this.theBase = par1DocaEntityBase;
	}

	@Override
	public void initGui()
	{
		this.buttonList.clear();
		byte var1 = -16;
		int var2 = (this.width - 251) / 2 + 10;
		int var3 = (this.height - 167) / 2 + 8;
		this.buttonList.add(new GuiButton(1, this.width / 2 + 2, this.height / 2 + 70 + var1, 114, 20, StatCollector.translateToLocal("CLOSE")));
		this.buttonList.add(new GuiButton(2, this.width / 2 + 70, this.height / 2 - 50 + var1, 45, 20, StatCollector.translateToLocal("NAME")));
		this.buttonList.add(new GuiButton(3, this.width / 2 + 70, this.height / 2 - 27 + var1, 20, 20, StatCollector.translateToLocal("<")));
		this.buttonList.add(new GuiButton(4, this.width / 2 + 95, this.height / 2 - 27 + var1, 20, 20, StatCollector.translateToLocal(">")));
		this.buttonList.add(new GuiButton(5, this.width / 2 + 70, this.height / 2 - 4 + var1, 20, 20, StatCollector.translateToLocal("<")));
		this.buttonList.add(new GuiButton(6, this.width / 2 + 95, this.height / 2 - 4 + var1, 20, 20, StatCollector.translateToLocal(">")));
		this.buttonList.add(new GuiButton(7, this.width / 2 + 70, this.height / 2 + 19 + var1, 20, 20, StatCollector.translateToLocal("<")));
		this.buttonList.add(new GuiButton(8, this.width / 2 + 95, this.height / 2 + 19 + var1, 20, 20, StatCollector.translateToLocal(">")));
		this.buttonList.add(new GuiButton(9, this.width / 2 + 70, this.height / 2 + 42 + var1, 20, 20, StatCollector.translateToLocal("<")));
		this.buttonList.add(new GuiButton(10, this.width / 2 + 95, this.height / 2 + 42 + var1, 20, 20, StatCollector.translateToLocal(">")));
		this.theGuiTextField = new GuiTextField(this.fontRenderer, var2 + 90, var3 + 11, 90, 18);
		this.theGuiTextField.setMaxStringLength(10);
		this.theGuiTextField.setFocused(true);
		this.theGuiTextField.setText(this.theBase.getName());
	}

	@Override
	public void onGuiClosed()
	{
		Keyboard.enableRepeatEvents(false);
	}

	@Override
	protected void keyTyped(char par1, int par2)
	{
		this.theGuiTextField.textboxKeyTyped(par1, par2);
		((GuiButton)this.buttonList.get(0)).enabled = this.theGuiTextField.getText().trim().length() > 0;

		if (par1 == 13)
		{
			this.actionPerformed((GuiButton)this.buttonList.get(1));
		}
		else
		{
			super.keyTyped(par1, par2);
		}
	}

	@Override
	protected void mouseClicked(int par1, int par2, int par3)
	{
		super.mouseClicked(par1, par2, par3);
		this.theGuiTextField.mouseClicked(par1, par2, par3);
	}

	@Override
	protected void actionPerformed(GuiButton par1GuiButton)
	{
		switch (par1GuiButton.id)
		{
		case 1:
			DocaTools.setDocaToSendQueue(theBase, null, DocaTools.PACKET_TYPE_DATA);
			this.mc.displayGuiScreen((GuiScreen)null);
			this.mc.setIngameFocus();
			break;

		case 2:
			String tmpName = this.theGuiTextField.getText().trim();
			String[] tmpName1 = new String[0];
			if(DocaSet.convert_NameStringDoca.length != 0)
			{
				for (int i = 0; i < DocaSet.convert_NameStringDoca.length; i++)
				{
					tmpName1 = DocaSet.convert_NameStringDoca[i].split(":", 0);
					if (tmpName1.length == 2 && tmpName.equalsIgnoreCase(tmpName1[0]))
					{
						tmpName = tmpName1[1];
						break;
					}
				}
			}
			this.theBase.setName(tmpName);
			break;

		case 3:
			this.theBase.setIndexTexture(DocaTools.calcCountDownRoop(this.theBase.getIndexTexture(), 0, this.theBase.sizeMaxTexture));
			break;

		case 4:
			this.theBase.setIndexTexture(DocaTools.calcCountUpRoop(this.theBase.getIndexTexture(), 0, this.theBase.sizeMaxTexture));
			break;

		case 5:
			this.theBase.setCollarColor(DocaTools.calcCountDownRoop(this.theBase.getCollarColor(), 0, 16));
			break;

		case 6:
			this.theBase.setCollarColor(DocaTools.calcCountUpRoop(this.theBase.getCollarColor(), 0, 16));
			break;

		case 7:
			this.theBase.setMode(DocaTools.calcCountDownRoop(this.theBase.getMode(), 0, DocaReg.getModeMax()));
			break;

		case 8:
			this.theBase.setMode(DocaTools.calcCountUpRoop(this.theBase.getMode(), 0, DocaReg.getModeMax()));
			break;

		case 9:
			this.theBase.setModelSize(DocaTools.calcCountDownRoop(this.theBase.getModelSize(), 0, DocaReg.getSizeMax()));
			break;

		case 10:
			this.theBase.setModelSize(DocaTools.calcCountUpRoop(this.theBase.getModelSize(), 0, DocaReg.getSizeMax()));
			break;

		default:
			break;
		}
	}

	@Override
	public void updateScreen()
	{
		super.updateScreen();
	}

	@Override
	public void drawScreen(int par1, int par2, float par3)
	{
		this.drawDefaultBackground();
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		DocaTools.setbindTextureDoca(field_110421_t);
		int var2 = (this.width - 251) / 2;
		int var3 = (this.height - 167) / 2;
		int var4 = (this.width - 251) / 2 + 10;
		int var5 = (this.height - 167) / 2 + 8;
		this.xSize_lo = (float)par1;
		this.ySize_lo = (float)par2;
		this.drawTexturedModalRect(var2, var3, 0, 0, 251, 167);
		this.drawScreenSetting(par1, par2, par3);
		renderSmallBox(this.theBase, var4 + 25, var5 + 65, 30, (float)(var4 + 51) - this.xSize_lo, (float)(var5 + 75 - 50) - this.ySize_lo);
		super.drawScreen(par1, par2, par3);
	}

	public void drawScreenSetting(int par1, int par2, float par3)
	{
		int var4 = (this.width - 251) / 2 + 10;
		int var5 = (this.height - 167) / 2 + 8;
		String tmpIndex = (new StringBuilder()).append(this.theBase.getIndexTexture()).toString();
		String tmpColorStirng = ItemDye.dyeColorNames[(15 - this.theBase.getCollarColor())];
		int tmpColor = ItemDye.dyeColors[(15 - this.theBase.getCollarColor())];
		String tmpMode = DocaReg.getModeTypeToName(this.theBase.getMode());
		String tmpSizeText = DocaReg.getSizeTypeToName(this.theBase.getModelSize());

		this.fontRenderer.drawString(StatCollector.translateToLocal("doca.gui.setting.title") +  ": ", var4, var5, 2039583);
		String var6 = "";
		this.theGuiTextField.drawTextBox();
		var6 = StatCollector.translateToLocal("doca.gui.setting.name") + ": ";
		this.fontRenderer.drawString(var6, var4 + 60, var5 + 15, 2039583);
		var6 = StatCollector.translateToLocal("doca.gui.setting.texture") + ": " + tmpIndex;
		this.fontRenderer.drawString(var6, var4 + 60, var5 + 38, 2039583);
		var6 = StatCollector.translateToLocal("doca.gui.setting.callor") + ": " + tmpColorStirng;
		this.fontRenderer.drawString(var6, var4 + 60, var5 + 61, tmpColor);
		var6 = StatCollector.translateToLocal("doca.gui.setting.mode") + ": " + tmpMode;
		this.fontRenderer.drawString(var6, var4 + 60, var5 + 84, 2039583);
		var6 = StatCollector.translateToLocal("doca.gui.setting.size") + ": " + tmpSizeText;
		this.fontRenderer.drawString(var6, var4 + 60, var5 + 107, 2039583);
	}

	public static void renderSmallBox(DocaEntityBase par0EDocaEntityBase, int par1, int par2, int par3, float par4, float par5)
	{
		GL11.glEnable(GL11.GL_COLOR_MATERIAL);
		GL11.glPushMatrix();
		GL11.glColor3f(1.0F, 1.0F, 1.0F);
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
}
