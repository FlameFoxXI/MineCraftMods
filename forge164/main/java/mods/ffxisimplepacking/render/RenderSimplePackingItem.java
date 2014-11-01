package mods.ffxisimplepacking.render;

import java.util.Random;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.ForgeHooksClient;

public class RenderSimplePackingItem
{
	protected RenderManager renderManager;
	protected RenderBlocks renderBlocks = new RenderBlocks();
	protected Random random = new Random();

	private static final ResourceLocation RES_ITEM_GLINT = new ResourceLocation("textures/misc/enchanted_item_glint.png");
	private RenderBlocks itemRenderBlocks = new RenderBlocks();

	public boolean renderWithColor;
	public static boolean renderInFrame;

	public RenderSimplePackingItem()
	{
		super();
		this.renderManager = RenderManager.instance;
		this.renderInFrame = false;
		this.renderWithColor = false;
	}

	protected ResourceLocation getEntityTextureSimplePackingItem(EntityItem par1EntityItem)
	{
		return this.renderManager.renderEngine.getResourceLocation(par1EntityItem.getEntityItem().getItemSpriteNumber());
	}

	protected ResourceLocation getEntityTexture(Entity par1Entity)
	{
		return this.getEntityTextureSimplePackingItem((EntityItem)par1Entity);
	}

	protected void bindEntityTexture(Entity par1Entity)
	{
		this.bindTexture(this.getEntityTexture(par1Entity));
	}

	protected void bindTexture(ResourceLocation par1ResourceLocation)
	{
		this.renderManager.renderEngine.bindTexture(par1ResourceLocation);
	}

	public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9)
	{
		this.doRenderItem((EntityItem)par1Entity, par2, par4, par6, par8, par9);
	}

	public void doRenderItem(EntityItem par1EntityItem, double par2, double par4, double par6, float par8, float par9)
	{
		this.bindEntityTexture(par1EntityItem);
		this.random.setSeed(187L);
		ItemStack itemstack = par1EntityItem.getEntityItem();

		if (itemstack.getItem() != null)
		{
			GL11.glPushMatrix();
			float f2 = shouldBob() ? MathHelper.sin(((float)par1EntityItem.age + par9) / 10.0F + par1EntityItem.hoverStart) * 0.1F + 0.1F : 0F;
			float f3 = (((float)par1EntityItem.age + par9) / 20.0F + par1EntityItem.hoverStart) * (180F / (float)Math.PI);
			byte b0 = getMiniBlockCount(itemstack);

			GL11.glTranslatef((float)par2, (float)par4 + f2, (float)par6);
//          GL11.glEnable(GL12.GL_RESCALE_NORMAL);
			float f4;
			float f5;
			float f6;
			int i;

			Block block = null;
			if (itemstack.itemID < Block.blocksList.length)
			{
				block = Block.blocksList[itemstack.itemID];
			}

			if (ForgeHooksClient.renderEntityItem(par1EntityItem, itemstack, f2, f3, random, renderManager.renderEngine, renderBlocks))
			{
				;
			}
			else if (itemstack.getItemSpriteNumber() == 0 && block != null && RenderBlocks.renderItemIn3d(Block.blocksList[itemstack.itemID].getRenderType()))
			{
				GL11.glRotatef(f3, 0.0F, 1.0F, 0.0F);

				if (renderInFrame)
				{
					GL11.glScalef(1.25F, 1.25F, 1.25F);
					GL11.glTranslatef(0.0F, 0.05F, 0.0F);
					GL11.glRotatef(-90.0F, 0.0F, 1.0F, 0.0F);
				}

				float f7 = 0.25F;
				int j = block.getRenderType();

				if (j == 1 || j == 19 || j == 12 || j == 2)
				{
					f7 = 0.5F;
				}

				GL11.glScalef(f7, f7, f7);

				for (i = 0; i < b0; ++i)
				{
					GL11.glPushMatrix();

					if (i > 0)
					{
						f5 = (this.random.nextFloat() * 2.0F - 1.0F) * 0.2F / f7;
						f4 = (this.random.nextFloat() * 2.0F - 1.0F) * 0.2F / f7;
						f6 = (this.random.nextFloat() * 2.0F - 1.0F) * 0.2F / f7;
						GL11.glTranslatef(f5, f4, f6);
					}

					f5 = 1.0F;
					this.itemRenderBlocks.renderBlockAsItem(block, itemstack.getItemDamage(), f5);
					GL11.glPopMatrix();
				}
			}
			else
			{
				float f8;

				if (itemstack.getItem().requiresMultipleRenderPasses())
				{
					if (renderInFrame)
					{
						GL11.glScalef(0.5128205F, 0.5128205F, 0.5128205F);
						GL11.glTranslatef(0.0F, -0.05F, 0.0F);
					}
					else
					{
						GL11.glScalef(0.5F, 0.5F, 0.5F);
					}

					for (int k = 0; k < itemstack.getItem().getRenderPasses(itemstack.getItemDamage()); ++k)
					{
						this.random.setSeed(187L);
						Icon icon = itemstack.getItem().getIcon(itemstack, k);
						f8 = 1.0F;

						if (this.renderWithColor)
						{
							i = Item.itemsList[itemstack.itemID].getColorFromItemStack(itemstack, k);
							f5 = (float)(i >> 16 & 255) / 255.0F;
							f4 = (float)(i >> 8 & 255) / 255.0F;
							f6 = (float)(i & 255) / 255.0F;
							GL11.glColor4f(f5 * f8, f4 * f8, f6 * f8, 1.0F);
							this.renderDroppedItem(par1EntityItem, icon, b0, par9, f5 * f8, f4 * f8, f6 * f8, k);
						}
						else
						{
							this.renderDroppedItem(par1EntityItem, icon, b0, par9, 1.0F, 1.0F, 1.0F, k);
						}
					}
				}
				else
				{
					if (renderInFrame)
					{
						GL11.glScalef(0.5128205F, 0.5128205F, 0.5128205F);
						GL11.glTranslatef(0.0F, -0.05F, 0.0F);
					}
					else
					{
						GL11.glScalef(0.5F, 0.5F, 0.5F);
					}

					Icon icon1 = itemstack.getIconIndex();

					if (this.renderWithColor)
					{
						int l = Item.itemsList[itemstack.itemID].getColorFromItemStack(itemstack, 0);
						f8 = (float)(l >> 16 & 255) / 255.0F;
						float f9 = (float)(l >> 8 & 255) / 255.0F;
						f5 = (float)(l & 255) / 255.0F;
						f4 = 1.0F;
						this.renderDroppedItem(par1EntityItem, icon1, b0, par9, f8 * f4, f9 * f4, f5 * f4);
					}
					else
					{
						this.renderDroppedItem(par1EntityItem, icon1, b0, par9, 1.0F, 1.0F, 1.0F);
					}
				}
			}

//          GL11.glDisable(GL12.GL_RESCALE_NORMAL);
			GL11.glPopMatrix();
		}
	}

	private void renderDroppedItem(EntityItem par1EntityItem, Icon par2Icon, int par3, float par4, float par5, float par6, float par7)
	{
		renderDroppedItem(par1EntityItem, par2Icon, par3, par4, par5, par6, par7, 0);
	}
	private void renderDroppedItem(EntityItem par1EntityItem, Icon par2Icon, int par3, float par4, float par5, float par6, float par7, int pass)
	{
		Tessellator tessellator = Tessellator.instance;

		if (par2Icon == null)
		{
			TextureManager texturemanager = Minecraft.getMinecraft().getTextureManager();
			ResourceLocation resourcelocation = texturemanager.getResourceLocation(par1EntityItem.getEntityItem().getItemSpriteNumber());
			par2Icon = ((TextureMap)texturemanager.getTexture(resourcelocation)).getAtlasSprite("missingno");
		}

		float f4 = ((Icon)par2Icon).getMinU();
		float f5 = ((Icon)par2Icon).getMaxU();
		float f6 = ((Icon)par2Icon).getMinV();
		float f7 = ((Icon)par2Icon).getMaxV();
		float f8 = 1.0F;
		float f9 = 0.5F;
		float f10 = 0.25F;
		float f11;

		if (this.renderManager.options.fancyGraphics)
		{
			GL11.glPushMatrix();

			if (renderInFrame)
			{
				GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
			}
			else
			{
				GL11.glRotatef((((float)par1EntityItem.age + par4) / 20.0F + par1EntityItem.hoverStart) * (180F / (float)Math.PI), 0.0F, 1.0F, 0.0F);
			}

			float f12 = 0.0625F;
			f11 = 0.021875F;
			ItemStack itemstack = par1EntityItem.getEntityItem();
			int j = itemstack.stackSize;
			byte b0 = getMiniItemCount(itemstack);

			GL11.glTranslatef(-f9, -f10, -((f12 + f11) * (float)b0 / 2.0F));

			for (int k = 0; k < b0; ++k)
			{
				// Makes items offset when in 3D, like when in 2D, looks much better. Considered a vanilla bug...
				if (k > 0 && shouldSpreadItems())
				{
					float x = (random.nextFloat() * 2.0F - 1.0F) * 0.3F / 0.5F;
					float y = (random.nextFloat() * 2.0F - 1.0F) * 0.3F / 0.5F;
					float z = (random.nextFloat() * 2.0F - 1.0F) * 0.3F / 0.5F;
					GL11.glTranslatef(x, y, f12 + f11);
				}
				else
				{
					GL11.glTranslatef(0f, 0f, f12 + f11);
				}

				if (itemstack.getItemSpriteNumber() == 0)
				{
					this.bindTexture(TextureMap.locationBlocksTexture);
				}
				else
				{
					this.bindTexture(TextureMap.locationItemsTexture);
				}

				GL11.glColor4f(par5, par6, par7, 1.0F);
				ItemRenderer.renderItemIn2D(tessellator, f5, f6, f4, f7, ((Icon)par2Icon).getIconWidth(), ((Icon)par2Icon).getIconHeight(), f12);

				if (itemstack.hasEffect(pass))
				{
					GL11.glDepthFunc(GL11.GL_EQUAL);
					GL11.glDisable(GL11.GL_LIGHTING);
					this.renderManager.renderEngine.bindTexture(RES_ITEM_GLINT);
					GL11.glEnable(GL11.GL_BLEND);
					GL11.glBlendFunc(GL11.GL_SRC_COLOR, GL11.GL_ONE);
					float f13 = 0.76F;
					GL11.glColor4f(0.5F * f13, 0.25F * f13, 0.8F * f13, 1.0F);
					GL11.glMatrixMode(GL11.GL_TEXTURE);
					GL11.glPushMatrix();
					float f14 = 0.125F;
					GL11.glScalef(f14, f14, f14);
					float f15 = (float)(Minecraft.getSystemTime() % 3000L) / 3000.0F * 8.0F;
					GL11.glTranslatef(f15, 0.0F, 0.0F);
					GL11.glRotatef(-50.0F, 0.0F, 0.0F, 1.0F);
					ItemRenderer.renderItemIn2D(tessellator, 0.0F, 0.0F, 1.0F, 1.0F, 255, 255, f12);
					GL11.glPopMatrix();
					GL11.glPushMatrix();
					GL11.glScalef(f14, f14, f14);
					f15 = (float)(Minecraft.getSystemTime() % 4873L) / 4873.0F * 8.0F;
					GL11.glTranslatef(-f15, 0.0F, 0.0F);
					GL11.glRotatef(10.0F, 0.0F, 0.0F, 1.0F);
					ItemRenderer.renderItemIn2D(tessellator, 0.0F, 0.0F, 1.0F, 1.0F, 255, 255, f12);
					GL11.glPopMatrix();
					GL11.glMatrixMode(GL11.GL_MODELVIEW);
					GL11.glDisable(GL11.GL_BLEND);
					GL11.glEnable(GL11.GL_LIGHTING);
					GL11.glDepthFunc(GL11.GL_LEQUAL);
				}
			}

			GL11.glPopMatrix();
		}
		else
		{
			for (int l = 0; l < par3; ++l)
			{
				GL11.glPushMatrix();

				if (l > 0)
				{
					f11 = (this.random.nextFloat() * 2.0F - 1.0F) * 0.3F;
					float f16 = (this.random.nextFloat() * 2.0F - 1.0F) * 0.3F;
					float f17 = (this.random.nextFloat() * 2.0F - 1.0F) * 0.3F;
					GL11.glTranslatef(f11, f16, f17);
				}

				if (!renderInFrame)
				{
					GL11.glRotatef(180.0F - this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
				}

				GL11.glColor4f(par5, par6, par7, 1.0F);
				tessellator.startDrawingQuads();
				tessellator.setNormal(0.0F, 1.0F, 0.0F);
				tessellator.addVertexWithUV((double)(0.0F - f9), (double)(0.0F - f10), 0.0D, (double)f4, (double)f7);
				tessellator.addVertexWithUV((double)(f8 - f9), (double)(0.0F - f10), 0.0D, (double)f5, (double)f7);
				tessellator.addVertexWithUV((double)(f8 - f9), (double)(1.0F - f10), 0.0D, (double)f5, (double)f6);
				tessellator.addVertexWithUV((double)(0.0F - f9), (double)(1.0F - f10), 0.0D, (double)f4, (double)f6);
				tessellator.draw();
				GL11.glPopMatrix();
			}
		}
	}


	private boolean shouldBob()
	{
		return true;
	}

	private byte getMiniBlockCount(ItemStack stack)
	{
		byte ret = 1;
		if (stack.stackSize > 1 ) ret = 2;
		if (stack.stackSize > 5 ) ret = 3;
		if (stack.stackSize > 20) ret = 4;
		if (stack.stackSize > 40) ret = 5;
		return ret;
	}

	private byte getMiniItemCount(ItemStack stack)
	{
		byte ret = 1;
		if (stack.stackSize > 1) ret = 2;
		if (stack.stackSize > 15) ret = 3;
		if (stack.stackSize > 31) ret = 4;
		return ret;
	}

	private boolean shouldSpreadItems()
	{
		return true;
	}
}
