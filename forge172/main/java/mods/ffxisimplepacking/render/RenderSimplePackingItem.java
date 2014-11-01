package mods.ffxisimplepacking.render;

import java.util.Random;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemCloth;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
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
        ItemStack itemstack = par1EntityItem.getEntityItem();

        if (itemstack.getItem() != null)
        {
            this.bindEntityTexture(par1EntityItem);
            this.random.setSeed(187L);
            GL11.glPushMatrix();
            float f2 = shouldBob() ? MathHelper.sin(((float)par1EntityItem.age + par9) / 10.0F + par1EntityItem.hoverStart) * 0.1F + 0.1F : 0F;
            float f3 = (((float)par1EntityItem.age + par9) / 20.0F + par1EntityItem.hoverStart) * (180F / (float)Math.PI);
 			byte b0 = getMiniBlockCount(itemstack);

            GL11.glTranslatef((float)par2, (float)par4 + f2, (float)par6);
//            GL11.glEnable(GL12.GL_RESCALE_NORMAL);
            float f6;
            float f7;
            int k;

            if (ForgeHooksClient.renderEntityItem(par1EntityItem, itemstack, f2, f3, random, renderManager.renderEngine, renderBlocks, b0))
            {
                ;
            }
            else // Code Style break here to prevent the patch from editing this line
            if (itemstack.getItemSpriteNumber() == 0 && itemstack.getItem() instanceof ItemBlock && RenderBlocks.renderItemIn3d(Block.getBlockFromItem(itemstack.getItem()).getRenderType()))
            {
                Block block = Block.getBlockFromItem(itemstack.getItem());
                GL11.glRotatef(f3, 0.0F, 1.0F, 0.0F);

                if (renderInFrame)
                {
                    GL11.glScalef(1.25F, 1.25F, 1.25F);
                    GL11.glTranslatef(0.0F, 0.05F, 0.0F);
                    GL11.glRotatef(-90.0F, 0.0F, 1.0F, 0.0F);
                }

                float f9 = 0.25F;
                k = block.getRenderType();

                if (k == 1 || k == 19 || k == 12 || k == 2)
                {
                    f9 = 0.5F;
                }

                if (block.getRenderBlockPass() > 0)
                {
                    GL11.glAlphaFunc(GL11.GL_GREATER, 0.1F);
                    GL11.glEnable(GL11.GL_BLEND);
                    OpenGlHelper.glBlendFunc(770, 771, 1, 0);
                }

                GL11.glScalef(f9, f9, f9);

                for (int l = 0; l < b0; ++l)
                {
                    GL11.glPushMatrix();

                    if (l > 0)
                    {
                        f6 = (this.random.nextFloat() * 2.0F - 1.0F) * 0.2F / f9;
                        f7 = (this.random.nextFloat() * 2.0F - 1.0F) * 0.2F / f9;
                        float f8 = (this.random.nextFloat() * 2.0F - 1.0F) * 0.2F / f9;
                        GL11.glTranslatef(f6, f7, f8);
                    }

                    this.renderBlocks.renderBlockAsItem(block, itemstack.getItemDamage(), 1.0F);
                    GL11.glPopMatrix();
                }

                if (block.getRenderBlockPass() > 0)
                {
                    GL11.glDisable(GL11.GL_BLEND);
                }
            }
            else
            {
                float f5;

                if (/*itemstack.getItemSpriteNumber() == 1 &&*/ itemstack.getItem().requiresMultipleRenderPasses())
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

                    for (int j = 0; j < itemstack.getItem().getRenderPasses(itemstack.getItemDamage()); ++j)
                    {
                        this.random.setSeed(187L);
                        IIcon iicon1 = itemstack.getItem().getIcon(itemstack, j);

                        if (this.renderWithColor)
                        {
                            k = itemstack.getItem().getColorFromItemStack(itemstack, j);
                            f5 = (float)(k >> 16 & 255) / 255.0F;
                            f6 = (float)(k >> 8 & 255) / 255.0F;
                            f7 = (float)(k & 255) / 255.0F;
                            GL11.glColor4f(f5, f6, f7, 1.0F);
                            this.renderDroppedItem(par1EntityItem, iicon1, b0, par9, f5, f6, f7, j);
                        }
                        else
                        {
                            this.renderDroppedItem(par1EntityItem, iicon1, b0, par9, 1.0F, 1.0F, 1.0F, j);
                        }
                    }
                }
                else
                {
                    if (itemstack != null && itemstack.getItem() instanceof ItemCloth)
                    {
                        GL11.glAlphaFunc(GL11.GL_GREATER, 0.1F);
                        GL11.glEnable(GL11.GL_BLEND);
                        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
                    }

                    if (renderInFrame)
                    {
                        GL11.glScalef(0.5128205F, 0.5128205F, 0.5128205F);
                        GL11.glTranslatef(0.0F, -0.05F, 0.0F);
                    }
                    else
                    {
                        GL11.glScalef(0.5F, 0.5F, 0.5F);
                    }

                    IIcon iicon = itemstack.getIconIndex();

                    if (this.renderWithColor)
                    {
                        int i = itemstack.getItem().getColorFromItemStack(itemstack, 0);
                        float f4 = (float)(i >> 16 & 255) / 255.0F;
                        f5 = (float)(i >> 8 & 255) / 255.0F;
                        f6 = (float)(i & 255) / 255.0F;
                        this.renderDroppedItem(par1EntityItem, iicon, b0, par9, f4, f5, f6);
                    }
                    else
                    {
                        this.renderDroppedItem(par1EntityItem, iicon, b0, par9, 1.0F, 1.0F, 1.0F);
                    }

                    if (itemstack != null && itemstack.getItem() instanceof ItemCloth)
                    {
                        GL11.glDisable(GL11.GL_BLEND);
                    }
                }
            }

//            GL11.glDisable(GL12.GL_RESCALE_NORMAL);
            GL11.glPopMatrix();
        }
    }

	private void renderDroppedItem(EntityItem par1EntityItem, IIcon par2Icon, int par3, float par4, float par5, float par6, float par7)
	{
		renderDroppedItem(par1EntityItem, par2Icon, par3, par4, par5, par6, par7, 0);
	}
    private void renderDroppedItem(EntityItem par1EntityItem, IIcon par2Icon, int par3, float par4, float par5, float par6, float par7, int pass)
    {
        Tessellator tessellator = Tessellator.instance;

        if (par2Icon == null)
        {
            TextureManager texturemanager = Minecraft.getMinecraft().getTextureManager();
            ResourceLocation resourcelocation = texturemanager.getResourceLocation(par1EntityItem.getEntityItem().getItemSpriteNumber());
            par2Icon = ((TextureMap)texturemanager.getTexture(resourcelocation)).getAtlasSprite("missingno");
        }

        float f14 = ((IIcon)par2Icon).getMinU();
        float f15 = ((IIcon)par2Icon).getMaxU();
        float f4 = ((IIcon)par2Icon).getMinV();
        float f5 = ((IIcon)par2Icon).getMaxV();
        float f6 = 1.0F;
        float f7 = 0.5F;
        float f8 = 0.25F;
        float f10;

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

            float f9 = 0.0625F;
            f10 = 0.021875F;
            ItemStack itemstack = par1EntityItem.getEntityItem();
            int j = itemstack.stackSize;
            byte b0 = getMiniItemCount(itemstack);

            GL11.glTranslatef(-f7, -f8, -((f9 + f10) * (float)b0 / 2.0F));

            for (int k = 0; k < b0; ++k)
            {
                // Makes items offset when in 3D, like when in 2D, looks much better. Considered a vanilla bug...
                if (k > 0 && shouldSpreadItems())
                {
                    float x = (random.nextFloat() * 2.0F - 1.0F) * 0.3F / 0.5F;
                    float y = (random.nextFloat() * 2.0F - 1.0F) * 0.3F / 0.5F;
                    float z = (random.nextFloat() * 2.0F - 1.0F) * 0.3F / 0.5F;
                    GL11.glTranslatef(x, y, f9 + f10);
                }
                else
                {
                    GL11.glTranslatef(0f, 0f, f9 + f10);
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
                ItemRenderer.renderItemIn2D(tessellator, f15, f4, f14, f5, ((IIcon)par2Icon).getIconWidth(), ((IIcon)par2Icon).getIconHeight(), f9);

                if (itemstack.hasEffect(pass))
                {
                    GL11.glDepthFunc(GL11.GL_EQUAL);
                    GL11.glDisable(GL11.GL_LIGHTING);
                    this.renderManager.renderEngine.bindTexture(RES_ITEM_GLINT);
                    GL11.glEnable(GL11.GL_BLEND);
                    GL11.glBlendFunc(GL11.GL_SRC_COLOR, GL11.GL_ONE);
                    float f11 = 0.76F;
                    GL11.glColor4f(0.5F * f11, 0.25F * f11, 0.8F * f11, 1.0F);
                    GL11.glMatrixMode(GL11.GL_TEXTURE);
                    GL11.glPushMatrix();
                    float f12 = 0.125F;
                    GL11.glScalef(f12, f12, f12);
                    float f13 = (float)(Minecraft.getSystemTime() % 3000L) / 3000.0F * 8.0F;
                    GL11.glTranslatef(f13, 0.0F, 0.0F);
                    GL11.glRotatef(-50.0F, 0.0F, 0.0F, 1.0F);
                    ItemRenderer.renderItemIn2D(tessellator, 0.0F, 0.0F, 1.0F, 1.0F, 255, 255, f9);
                    GL11.glPopMatrix();
                    GL11.glPushMatrix();
                    GL11.glScalef(f12, f12, f12);
                    f13 = (float)(Minecraft.getSystemTime() % 4873L) / 4873.0F * 8.0F;
                    GL11.glTranslatef(-f13, 0.0F, 0.0F);
                    GL11.glRotatef(10.0F, 0.0F, 0.0F, 1.0F);
                    ItemRenderer.renderItemIn2D(tessellator, 0.0F, 0.0F, 1.0F, 1.0F, 255, 255, f9);
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
                    f10 = (this.random.nextFloat() * 2.0F - 1.0F) * 0.3F;
                    float f16 = (this.random.nextFloat() * 2.0F - 1.0F) * 0.3F;
                    float f17 = (this.random.nextFloat() * 2.0F - 1.0F) * 0.3F;
                    GL11.glTranslatef(f10, f16, f17);
                }

                if (!renderInFrame)
                {
                    GL11.glRotatef(180.0F - this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
                }

                GL11.glColor4f(par5, par6, par7, 1.0F);
                tessellator.startDrawingQuads();
                tessellator.setNormal(0.0F, 1.0F, 0.0F);
                tessellator.addVertexWithUV((double)(0.0F - f7), (double)(0.0F - f8), 0.0D, (double)f14, (double)f5);
                tessellator.addVertexWithUV((double)(f6 - f7), (double)(0.0F - f8), 0.0D, (double)f15, (double)f5);
                tessellator.addVertexWithUV((double)(f6 - f7), (double)(1.0F - f8), 0.0D, (double)f15, (double)f4);
                tessellator.addVertexWithUV((double)(0.0F - f7), (double)(1.0F - f8), 0.0D, (double)f14, (double)f4);
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
