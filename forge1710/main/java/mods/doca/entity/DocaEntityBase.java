package mods.doca.entity;

import java.io.*;
import java.util.*;

import org.lwjgl.input.Keyboard;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mods.doca.*;
import mods.doca.core.*;
import mods.doca.entity.ai.*;
import mods.doca.entity.func.*;
import mods.doca.inventory.*;
import mods.doca.item.*;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.client.*;
import net.minecraft.client.entity.*;
import net.minecraft.entity.*;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.item.*;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.projectile.*;
import net.minecraft.init.Items;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.pathfinding.*;
import net.minecraft.potion.*;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.*;
import net.minecraft.world.*;

public class DocaEntityBase extends DocaEntityCore
{
	private int baseHelthTimer;
	private int baseUpToFire;
	public int sizeMaxTexture;
	public String entityDocaString;
	public double moveSpeed;
	public String attackerMode;
	private int currentItemDataID;

	public DocaFuncPopup funcPopup;
	public DocaFuncParticle funcParticle;
	public DocaFuncPickUp funcPickup;
	public DocaFuncEmotion funcEmotion;
	public DocaFuncShaking funcShaking;
	public DocaFuncChsetSearch funcChsetSearch;
	public DocaFuncFoodsEeat funcFoodsEeat;
	public DocaFuncRiddenByEntity funcRiddenByEntity;

	public final DocaEntityAIControlledByPlayer aiControlledByPlayer;

	public DocaEntityBase(World par1World)
	{
		super(par1World);
		this.entityDocaString = "";
		this.sizeMaxTexture = DocaSet.texture_SizeMaxDefault;
		this.attackerMode = "";
		this.baseHelthTimer = DocaSet.maxHelthTimer;
		this.baseUpToFire = 0;
		this.currentItemDataID = -1;
		this.funcPopup = new DocaFuncPopup();
		this.funcParticle = new DocaFuncParticle();
		this.funcPickup = new DocaFuncPickUp();
		this.funcEmotion = new DocaFuncEmotion();
		this.funcShaking = new DocaFuncShaking();
		this.funcChsetSearch = new DocaFuncChsetSearch();
		this.funcFoodsEeat = new DocaFuncFoodsEeat();
		this.funcRiddenByEntity = new DocaFuncRiddenByEntity();
		this.aiControlledByPlayer = new DocaEntityAIControlledByPlayer(this, 0.6F);
	}

	@Override
	protected void entityInit()
	{
		super.entityInit();
		this.entityDocaString = "";
		this.sizeMaxTexture = DocaSet.texture_SizeMaxDefault;
		this.attackerMode = "";
		this.baseHelthTimer = DocaSet.maxHelthTimer;
		this.baseUpToFire = 0;
		this.currentItemDataID = -1;
		this.theInventoryFull = false;
		this.funcPopup = new DocaFuncPopup();
		this.funcParticle = new DocaFuncParticle();
		this.funcPickup = new DocaFuncPickUp();
		this.funcEmotion = new DocaFuncEmotion();
		this.funcShaking = new DocaFuncShaking();
		this.funcChsetSearch = new DocaFuncChsetSearch();
		this.funcFoodsEeat = new DocaFuncFoodsEeat();
		this.funcRiddenByEntity = new DocaFuncRiddenByEntity();
	}

	@Override
	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(this.setMoveSpeed());
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(this.getDocaMobMaxHealth());
	}

	/********************************************************************************************
	 *	RenderCalc																				*
	 ********************************************************************************************/
	@SideOnly(Side.CLIENT)
	public boolean getShaking()
	{
		return this.funcShaking.getShaking();
	}

	@SideOnly(Side.CLIENT)
	public float getShadingWhileShaking(float par1)
	{
		return this.funcShaking.getShadingWhileShaking(par1);
	}

	@SideOnly(Side.CLIENT)
	public float getShakeAngle(float par1, float par2)
	{
		return this.funcShaking.getShakeAngle(par1, par2);
	}

	@SideOnly(Side.CLIENT)
	public float getInterestedAngle(float par1)
	{
		return this.funcShaking.getInterestedAngle(par1);
	}

	/********************************************************************************************
	 *																							*
	 ********************************************************************************************/
	@SideOnly(Side.CLIENT)
	public int getLifeColor()
	{
		double low = this.getMaxHealth() / 4.0D;
		double mid = this.getMaxHealth() / 2.0D;
		double high = low + mid;
		float tmplife = this.getLife();

		if (high <= tmplife) { return 0x00ff00; }
		else if (mid <= tmplife) { return 0xFF9900; }
		else if (low <= tmplife) { return 0xFFFF00; }
		else { return 0xFF0000;}
	}

	@SideOnly(Side.CLIENT)
	public int isEmotion()
	{
		return this.funcEmotion.isEmotionLiving(this);
	}

	@SideOnly(Side.CLIENT)
	public double getLabelHeight()
	{
		return (double)0 - (this.getHeightType() * 10);
	}

	@SideOnly(Side.CLIENT)
	public String getRenderStatusText()
	{
		if (this.isSleeping()) { return "(Sl)"; }
		if (this.isWaiting())  { return "(Wt)"; }
		if (this.isDowning())  { return "<Dw>"; }
		if (this.isSitting())  { return "(St)"; }
		if (this.isDistance()) { return "<Ds>"; }
		if (this.isComeing())  { return "<Cm>"; }

		switch (this.getMode()) {
		case 1:  return "[Fr]";
		case 2:  return "[Wa]";
		case 3:  return"[Wd]";
		default: return "[Nm]";
		}
	}

	@SideOnly(Side.CLIENT)
	public int getRenderStatus()
	{
		if (this.isSleeping()) { return 4; }
		if (this.isWaiting())  { return 5; }
		if (this.isDowning())  { return 2; }
		if (this.isSitting())  { return 3; }
		if (this.isDistance()) { return 1; }
		if (this.isComeing())  { return 0; }

		switch (this.getMode()){
		case 1:  return 6;
		case 2:  return 7;
		case 3:  return 8;
		default: return 9;
		}
	}

	/********************************************************************************************
	 *	SettingFunction																			*
	 ********************************************************************************************/
	@Override
	public boolean isAIEnabled()
	{
		return true;
	}

	@Override
	public int getVerticalFaceSpeed()
	{
		return this.isSitting() ? 20 : super.getVerticalFaceSpeed();
	}

	@Override
	public float getEyeHeight()
	{
		return this.height * 0.8F;
	}

	public double getDocaMobMaxHealth()
	{
		return this.isTamed() ? 20.0D : 8.0D;
	}

	public String getEntityDocaString()
	{
		return this.entityDocaString;
	}

	public int getHeightType()
	{
		if (this.isChild())
		{
			return DocaReg.getDocaLabelSize(DocaReg.getNameToType(this.entityDocaString), DocaReg.getSizeNameToNo("Small"));
		}
		else if (this.getModelSize() == DocaReg.getSizeNameToNo("Small"))
		{
			return DocaReg.getDocaLabelSize(DocaReg.getNameToType(this.entityDocaString), DocaReg.getSizeNameToNo("Small"));
		}
		else if (this.getModelSize() == DocaReg.getSizeNameToNo("Big"))
		{
			return DocaReg.getDocaLabelSize(DocaReg.getNameToType(this.entityDocaString), DocaReg.getSizeNameToNo("Big"));
		}
		else
		{
			return DocaReg.getDocaLabelSize(DocaReg.getNameToType(this.entityDocaString), DocaReg.getSizeNameToNo("Normal"));
		}
	}

	public float getHeightModelSizeDefault(){
		return DocaReg.getDocaHeightSize(DocaReg.getNameToType(this.entityDocaString), DocaReg.getSizeNameToNo("Normal"));
	}

	public float getHeightModelSize()
	{
		float tmpFloat = getHeightModelSizeDefault();

		if (this.isChild() || this.getModelSize() == DocaReg.getSizeNameToNo("Small"))
		{
			tmpFloat = DocaReg.getDocaHeightSize(DocaReg.getNameToType(this.entityDocaString), DocaReg.getSizeNameToNo("Small"));
		}
		else if (this.getModelSize() == DocaReg.getSizeNameToNo("Big"))
		{
			tmpFloat =  DocaReg.getDocaHeightSize(DocaReg.getNameToType(this.entityDocaString), DocaReg.getSizeNameToNo("Big"));
		}

		return tmpFloat;
	}

	public float getWidthModelSize()
	{
		return DocaReg.getDocaWidth(DocaReg.getNameToType(this.entityDocaString));
	}

	public float getRenderSizeModifier()
	{
		float tmpSize = 1.0F;

		if (this.getModelSize() == DocaReg.getSizeNameToNo("Small"))
		{
			tmpSize = 0.5F;
		}
		else if (this.getModelSize() == DocaReg.getSizeNameToNo("Big"))
		{
			tmpSize = 1.4F;
		}
		if (this.isChild()) { tmpSize = 1.0F; }
		return tmpSize;
	}

	public double setMoveSpeed()
	{
		return DocaReg.getDocaSpeed(DocaReg.getNameToType(this.entityDocaString));
	}

	@Override
	public void setTamed(boolean par1)
	{
		super.setTamed(par1);

		if (par1)
		{
			this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(this.getDocaMobMaxHealth());
		}
		else
		{
			this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(8.0D);
		}
	}

	public void setUpToFire(int par1)
	{
		baseUpToFire = par1;
	}

	public boolean getUpToFire()
	{
		return baseUpToFire == 1 ? true : false;
	}

	public void countUpToFire()
	{
		if (1 < this.baseUpToFire)
		{
			this.baseUpToFire--;
		}
	}

	public int getMinMoveSize()
	{
		if (10 < DocaSet.func_MoveSquareToHomePoint)
		{
			return 6;
		}
		else if (5 < DocaSet.func_MoveSquareToHomePoint)
		{
			return 4;
		}
		else
		{
			return 2;
		}
	}

	public double getMoveSpeed()
	{
		return DocaSet.func_Speed ? 1.2D: 1.0D;
	}

	@Override
	protected boolean canDespawn()
	{
		return !this.isTamed();
	}

	@Override
	protected float getSoundVolume()
	{
		return 0.4F;
	}

	@Override
	protected Item getDropItem()
	{
        return Item.getItemById(0);
	}

	@Override
	public int getMaxSpawnedInChunk()
	{
		return 8;
	}

	@Override
	public boolean isBreedingItem(ItemStack par1ItemStack)
	{
		return false;
	}

	public boolean isFeedItem(ItemStack par1ItemStack)
	{
		boolean tmpRetrun = false;

		if (par1ItemStack != null)
		{
			if (DocaTools.ofItem(par1ItemStack, DocaReg.getFeedItem(DocaReg.getNameToType(this.entityDocaString), 0))
					|| DocaTools.ofItem(par1ItemStack, DocaReg.getFeedItem(DocaReg.getNameToType(this.entityDocaString), 1))
					|| DocaTools.ofItem(par1ItemStack, DocaReg.getFeedItem(DocaReg.getNameToType(this.entityDocaString), 2))
					|| DocaTools.ofItem(par1ItemStack, DocaReg.getFeedItem(DocaReg.getNameToType(this.entityDocaString), 3)))
			{
				tmpRetrun = true;
			}
		}
		return tmpRetrun;
	}

	@Override
	public void onDeath(DamageSource par1DamageSource)
	{
		DocaSet.copyToInventoryCheck = true;
		DocaSet.copyToInventoryCount = 50;
		super.onDeath(par1DamageSource);

		if (!this.worldObj.isRemote)
		{
			this.inventory.dropAllItems();
		}
	}

	/********************************************************************************************
	 *	TaskCallFunction																		*
	 ********************************************************************************************/
	@Override
	public void updateAITick()
	{
		this.updateHelth();

		if (this.getMoveHelper().isUpdating())
		{
			double var1 = this.getMoveHelper().getSpeed();

			if (var1 < this.getMoveSpeed() * 1.0D)
			{
				this.setSneaking(false);
				this.setSprinting(true);
			}
			else
			{
				this.setSneaking(false);
				this.setSprinting(false);
			}
		}
		else
		{
			this.setSneaking(false);
			this.setSprinting(false);
		}
		super.updateAITick();
	}

	@Override
	public void onLivingUpdate()
	{
		this.funcShaking.onLivingUpdate(this);

		if (this.riddenByEntity != null && (this.getLife() <= 1.0F || DocaTools.geKeyPlessUpdateDoca(this, DocaTools.KEY_SIT)))
		{
			this.riddenByEntity.mountEntity((Entity)null);
		}

		super.onLivingUpdate();

		this.statusUpdate();

		if (this.isJumping)
		{
			if (this.isInWater())
			{
				this.motionY += 0.06D;
			}

			if (this.handleLavaMovement())
			{
				this.motionY += 0.1D;
			}
		}

		this.updateHealth();

		this.funcFoodsEeat.onLivingUpdate(this);
		this.funcChsetSearch.onLivingUpdate(this);
		if(this.funcPopup.onLivingUpdate(this))
		{
			this.funcEmotion.setEmotionLiving(this, DocaFuncEmotion.EMOTION_HAPPY);
		}
		this.funcParticle.onLivingUpdate(this);
		this.funcPickup.onLivingUpdate(this);
		this.funcEmotion.onLivingUpdate(this);
		this.funcRiddenByEntity.onLivingUpdate(this);

		this.updateGrowAge();

		this.setSizeDoca(this.getWidthModelSize(), this.getHeightModelSize());
	}

	@Override
	public void onUpdate()
	{
		super.onUpdate();

		this.countUpToFire();

		this.funcShaking.onUpdate(this);
	}

	/********************************************************************************************
	 *	EventCallFunction																		*
	 ********************************************************************************************/
	@Override
	public void setAttackTarget(EntityLivingBase par1EntityLivingBase)
	{
		if (!this.isAttackTargetCheck(par1EntityLivingBase))
		{
			par1EntityLivingBase = null;
		}
		super.setAttackTarget(par1EntityLivingBase);
	}

	public boolean isAttackTargetCheck(EntityLivingBase par1EntityLivingBase)
	{
		if (DocaSet.attack_PeacefulON)
		{
			return false;
		}

		if (par1EntityLivingBase instanceof EntityPlayer)
		{
			return false;
		}

		if (this.isDowning() || this.isComeing() || this.isDistance() || this.isSleeping() || this.isWaiting())
		{
			return false;
		}

		if (par1EntityLivingBase instanceof EntityTameable)
		{
			if (DocaSet.attack_TameableON)
			{
				return false;
			}
			else
			{
				if (((EntityTameable) par1EntityLivingBase).isTamed())
				{
					return false;
				}
			}
		}

		if (this.getLife() <= 1.0F)
		{
			return false;
		}

		if (DocaSet.attack_FriendlyEntityDoca.length != 0 && par1EntityLivingBase instanceof EntityLivingBase)
		{
			String tmpAttacker = EntityList.getEntityString((Entity)par1EntityLivingBase);
			for (int i = 0; i < DocaSet.attack_FriendlyEntityDoca.length; i++)
			{
				if (tmpAttacker.equalsIgnoreCase(DocaSet.attack_FriendlyEntityDoca[i]))
				{
					return false;
				}
			}
		}
		return true;
	}

	@Override
	public boolean attackEntityFrom(DamageSource par1DamageSource, float par2)
	{
		if (this.isEntityInvulnerable())
		{
			return false;
		}
		else
		{
			Entity var3 = par1DamageSource.getEntity();
			this.aiSit.setSitting(false);
			this.setDowning(false);

			if (var3 != null && !(var3 instanceof EntityPlayer) && !(var3 instanceof EntityArrow))
			{
				par2 = (par2 + 1.0F) / 2.0F;
			}

			if (this.getMode() != 0)
			{
				if (this.getLife() <= par2)
				{
					float tmpDamage =  this.getLife() - 1.0F;

					if (1 <= tmpDamage)
					{
						par2 = tmpDamage;
					}
					else
					{
						par2 = 0;
						return false;
					}
				}
			}

			if(DocaSet.attack_PetInvincibleOFF)
			{
				return super.attackEntityFrom(par1DamageSource, par2);
			}

			if (this.rand.nextInt(5) == 0)
			{
				this.funcEmotion.setEmotionLiving(this, DocaFuncEmotion.EMOTION_ANGRY);
			}

			if (this.getMode() != 0)
			{
				if (par1DamageSource.damageType.equals("inFire") || par1DamageSource.damageType.equals("onFire"))
				{
					this.setUpToFire(20);
				}
				return false;
			}
			else
			{
				return super.attackEntityFrom(par1DamageSource, par2);
			}
		}
	}

	@Override
	public boolean attackEntityAsMob(Entity par1Entity)
	{
		int var2 = DocaSet.attack_PetInvincibleOFF ? 2 : 4;
		byte var3 = 0;

		if (DocaSet.attack_AdditionalEffectON)
		{
			if (par1Entity instanceof EntityLiving)
			{

//				if (this.worldObj.difficultySetting == 2)
				if (this.worldObj.difficultySetting == EnumDifficulty.NORMAL)
				{
					var3 = 7;
				}
//				else if (this.worldObj.difficultySetting == 3)
				else if (this.worldObj.difficultySetting == EnumDifficulty.HARD)
				{
					var3 = 15;
				}
				else
				{
					var3 = 5;
				}
			}

			switch (this.getMode())
			{
			case 1:
				((EntityLivingBase)par1Entity).setFire(var3);
				break;

			case 2:
				((EntityLivingBase)par1Entity).addPotionEffect(new PotionEffect(Potion.weakness.id, var3 * 20, 0));
				break;

			case 3:
				((EntityLivingBase)par1Entity).addPotionEffect(new PotionEffect(Potion.poison.id, var3 * 20, 0));
				break;

			default:
				break;
			}
		}

		return par1Entity.attackEntityFrom(DamageSource.causeMobDamage(this), (float)var2);
	}

	@Override
	public boolean interact(EntityPlayer par1EntityPlayer)
	{
		ItemStack itemStack = par1EntityPlayer.inventory.getCurrentItem();

		if (itemStack != null)
		{
			if (DocaTools.newItem(itemStack) instanceof ItemFood)
			{
				ItemFood var3 = (ItemFood)DocaTools.newItem(itemStack);

				if (this.isFeedItem(itemStack) && this.getLife() < 20.0F)
				{
					if (!par1EntityPlayer.capabilities.isCreativeMode)
					{
						--itemStack.stackSize;
					}

//					this.heal((float)var3.getHealAmount());
					this.heal(var3.func_150905_g(itemStack));
					this.baseHelthTimer = DocaSet.maxHelthTimer;

					if (itemStack.stackSize <= 0)
					{
						par1EntityPlayer.inventory.setInventorySlotContents(par1EntityPlayer.inventory.currentItem, (ItemStack)null);
					}

					this.setParticle("heart");
					this.funcEmotion.setEmotionLiving(this, DocaFuncEmotion.EMOTION_HEART);
				}
				else
				{
					this.setParticle("smoke");
				}
				return true;
			}
			else if (DocaTools.ofItem(itemStack, Items.compass))
			{
//				if (!this.isTamed())
//				{
					this.setTamed(true);
					this.setPathToEntity((PathEntity)null);
					this.setTarget((Entity)null);
					this.setAttackTarget((EntityLivingBase)null);
					this.func_152115_b(par1EntityPlayer.getUniqueID().toString());
					this.aiSit.setSitting(false);
					this.setParticle("heart");
					return true;
//				}
//				else if (DocaTools.isOps(par1EntityPlayer) || DocaSet.DebugItem)
//				{
//					this.setTamed(true);
//					this.setPathToEntity((PathEntity)null);
//					this.setTarget((Entity)null);
//					this.setAttackTarget((EntityLivingBase)null);
//					this.func_152115_b(par1EntityPlayer.getUniqueID().toString());
//					this.aiSit.setSitting(false);
//					this.setParticle("heart");
//					return true;					
//				}
			}
			else if (DocaTools.ofItem(itemStack, Items.bone) && this.isTamed()
					&& (par1EntityPlayer.getUniqueID().toString().equalsIgnoreCase(this.func_152113_b()) || DocaSet.DebugItem))
			{
				if (this.isSitting())
				{
					this.aiSit.setSitting(false);
					this.setSitting(false);
					this.setDowning(false);
					this.setComeing(false);
					this.setDistance(false);
				}
				else
				{
					this.aiSit.setSitting(true);
					this.setSitting(true);
				}
				return true;
			}
			else if (DocaTools.ofItem(itemStack, Items.sign) && this.isTamed()
					&& (par1EntityPlayer.getUniqueID().toString().equalsIgnoreCase(this.func_152113_b()) || DocaSet.DebugItem))
			{
				if (!this.isWaiting())
				{
					this.setWaiting(true);
					this.setHomePoint(new ChunkCoordinates((int)this.posX, (int)this.posY, (int)this.posZ));
					this.setDowning(false);
					this.setComeing(false);
					this.setDistance(false);
					DocaTools.setChatMassageDoca2(this.worldObj.isRemote, StatCollector.translateToLocal("doca.chat.message.WaitModeON"));
				}
				else
				{
					this.setWaiting(false);
					this.setHomePoint(new ChunkCoordinates((int)this.posX, (int)this.posY, (int)this.posZ));
					DocaTools.setChatMassageDoca2(this.worldObj.isRemote, StatCollector.translateToLocal("doca.chat.message.WaitModeOFF"));
				}
				return true;
			}
			else if(DocaTools.ofItem(itemStack, Items.carrot_on_a_stick) && this.isTamed()
					&& (par1EntityPlayer.getUniqueID().toString().equalsIgnoreCase(this.func_152113_b()) || DocaSet.DebugItem))
			{
				this.funcRiddenByEntity.interact(par1EntityPlayer, this, Items.carrot_on_a_stick);					
				return true;
			}
			else if (DocaTools.ofItem(itemStack, Items.feather) && this.isTamed()
					&& (par1EntityPlayer.getUniqueID().toString().equalsIgnoreCase(this.func_152113_b()) || DocaSet.DebugItem))
			{
				DocaTools.openEntityGuiDoca(DocaSet.containerSettingID, this.worldObj, this.getEntityId(), par1EntityPlayer);
				return true;
			}
			else if (DocaTools.ofItem(itemStack, Items.shears) && this.isTamed()
					&& (par1EntityPlayer.getUniqueID().toString().equalsIgnoreCase(this.func_152113_b()) || DocaSet.DebugItem))
			{
				this.setName("");
				this.setMode(0);
				this.setWaiting(false);
				this.setHomePoint(new ChunkCoordinates(-1, -1, -1));
				this.setHealing(false);
				this.setDowning(false);
				this.setSleeping(false);
				this.setComeing(false);
				this.setDistance(false);
				this.aiSit.setSitting(false);
				this.setSitting(false);
				DocaSet.copyToInventoryCheck = true;
				DocaSet.copyToInventoryCount = 50;

				if (!this.worldObj.isRemote)
				{
					this.inventory.dropAllItems();
				}

				this.func_152115_b("");
				this.setTamed(false);
				return true;
			}
			else if(DocaTools.ofItem(itemStack, Items.stick) && (this.getMode() != 0 || DocaSet.DebugItem))
			{
				DocaTools.setDocaToSendQueue(this, par1EntityPlayer, DocaTools.PACKET_SOWN_DATA);

				if (par1EntityPlayer.getUniqueID().toString().equalsIgnoreCase(this.func_152113_b()) || this.checkSubOwners(par1EntityPlayer.getUniqueID().toString()) || DocaSet.Debug || DocaTools.isOps(par1EntityPlayer))
				{
					DocaTools.openEntityGuiDoca(DocaSet.containerContainerID, this.worldObj, this.getEntityId(), par1EntityPlayer);
				}
				return true;
			}
		}
		else
		{
			if (this.isTamed() && (par1EntityPlayer.getUniqueID().toString().equalsIgnoreCase(this.func_152113_b()) || DocaSet.DebugItem))
			{
				if(DocaTools.geKeyPlessUpdateDoca(this, DocaTools.KEY_INV))
				{
					if (this.getMode() != 0)
					{
						DocaTools.openEntityGuiDoca(DocaSet.containerContainerID, this.worldObj, this.getEntityId(), par1EntityPlayer);
					}
					return true;
				}
				else if (DocaTools.geKeyPlessUpdateDoca(this, DocaTools.KEY_SET))
				{
					DocaTools.openEntityGuiDoca(DocaSet.containerSettingID, this.worldObj, this.getEntityId(), par1EntityPlayer);
					return true;
				}
				else if (DocaTools.geKeyPlessUpdateDoca(this, DocaTools.KEY_HOM))
				{
					if (!this.isWaiting())
					{
						this.setWaiting(true);
						this.setHomePoint(new ChunkCoordinates((int)this.posX, (int)this.posY, (int)this.posZ));
						this.setDowning(false);
						this.setComeing(false);
						this.setDistance(false);
						if (!this.worldObj.isRemote)
						{
							DocaTools.setChatMassageDoca(StatCollector.translateToLocal("doca.chat.message.WaitModeON"));
						}
					}
					else
					{
						this.setWaiting(false);
						this.setHomePoint(new ChunkCoordinates((int)this.posX, (int)this.posY, (int)this.posZ));

						if (!this.worldObj.isRemote)
						{
							DocaTools.setChatMassageDoca(StatCollector.translateToLocal("doca.chat.message.WaitModeOFF"));
						}
					}
					return true;
				}
				else if (DocaTools.geKeyPlessUpdateDoca(this, DocaTools.KEY_SIT))
				{
					if (this.isSitting())
					{
						this.aiSit.setSitting(false);
						this.setSitting(false);
						this.setDowning(false);
						this.setComeing(false);
						this.setDistance(false);
					}
					else
					{
						this.aiSit.setSitting(true);
						this.setSitting(true);
					}
					return true;
				}
			}
		}
		return super.interact(par1EntityPlayer);
	}

	@Override
	public EntityAgeable createChild(EntityAgeable par1EntityAgeable)
	{
		return this.spawnBabyAnimal(par1EntityAgeable);
	}

	public EntityAgeable spawnBabyAnimal(EntityAgeable par1EntityAgeable)
	{
		DocaEntityBase var2 = new DocaEntityBase(this.worldObj);

		if (this.isTamed())
		{
			var2.func_152115_b(this.func_152113_b());
			var2.setTamed(true);
		}

		return var2;
	}

	@Override
	public void handleHealthUpdate(byte par1)
	{
		if (par1 == 8)
		{
			this.funcShaking.handleHealthUpdate(this);
		}
		else
		{
			super.handleHealthUpdate(par1);
		}
	}

	@Override
	public boolean canMateWith(EntityAnimal par1EntityAnimal)
	{
		if (par1EntityAnimal == this)
		{
			return false;
		}
		else if (!this.isTamed())
		{
			return false;
		}
		else if (!(par1EntityAnimal instanceof DocaEntityBase))
		{
			return false;
		}
		else
		{
			DocaEntityBase var2 = (DocaEntityBase)par1EntityAnimal;
			return !var2.isTamed() ? false : (var2.isSitting() ? false : this.isInLove() && var2.isInLove());
		}
	}

	/********************************************************************************************
	 *	SubThisFunction																			*
	 ********************************************************************************************/
	public void setParticle(String par1)
	{
		if (par1 == "heart" || par1 == "smoke" || par1 == "splash" || par1 == "cloud" || par1 == "flame" || par1 == "portal" || par1 == "snowshovel" || par1 == "slime" || par1 == "enchantmenttable")
		{
			for (int var2 = 0; var2 < 7; ++var2)
			{
				double var3 = this.rand.nextGaussian() * 0.02D;
				double var5 = this.rand.nextGaussian() * 0.02D;
				double var7 = this.rand.nextGaussian() * 0.02D;
				this.worldObj.spawnParticle(par1, this.posX + (double)(this.rand.nextFloat() * this.width * 2.0F) - (double)this.width, this.posY + 0.5D + (double)(this.rand.nextFloat() * this.height), this.posZ + (double)(this.rand.nextFloat() * this.width * 2.0F) - (double)this.width, var3, var5, var7);
			}
		}
	}

	public void statusUpdate()
	{
		if (this.isTamed())
		{
			if (this.isHealing() || this.isSleeping() || this.isWaiting() || this.isRiden())
			{
				this.setComeing(false);
				this.setDowning(false);
				this.setDistance(false);
			}
			else
			{
				if (true)//if (DocaSet.statusKeyplessUser.equalsIgnoreCase(this.getUniqueID().toString()))
				{
					if (DocaTools.geKeyPlessUpdateDoca(this, DocaTools.KEY_COM))//if (DocaSet.statusComeing)
					{
						this.setComeing(true);
						this.setDowning(false);
						this.setDistance(false);
						this.aiSit.setSitting(false);
						this.setSitting(false);
					}

					if (DocaTools.geKeyPlessUpdateDoca(this, DocaTools.KEY_DWN))//if (DocaSet.statusDowning)
					{
						this.setComeing(false);
						this.setDowning(true);
						this.setDistance(false);
						this.aiSit.setSitting(true);
						this.setSitting(true);
					}

					if (DocaTools.geKeyPlessUpdateDoca(this, DocaTools.KEY_DIS))//if (DocaSet.statusDistance)
					{
						this.setComeing(false);
						this.setDowning(false);
						this.setDistance(true);
						this.aiSit.setSitting(false);
						this.setSitting(false);
					}

					if (DocaTools.geKeyPlessUpdateDoca(this, DocaTools.KEY_FRE))//if (DocaSet.statusFree)
					{
						this.setComeing(false);
						this.setDowning(false);
						this.setDistance(false);

						if (this.isHealing() || this.isSleeping() || this.isWaiting())
						{
						}
						else
						{
							this.aiSit.setSitting(false);
							this.setSitting(false);
						}
					}
				}
			}
		}
	}

	public void updateHealth()
	{
		if (this.isWaiting() && DocaSet.func_HealingBed)
		{
			if (this.getLife() < (this.getMaxHealth() / 2))
			{
				this.setHealing(true);
			}

			if (this.getLife() == this.getMaxHealth())
			{
				this.setHealing(false);
			}
		}
		else
		{
			this.setHealing(false);
		}

		if (this.getMode() != DocaReg.getModeNameToNo("Normal"))
		{
			if (this.baseHelthTimer <= 0)
			{
				this.baseHelthTimer = DocaSet.maxHelthTimer;

				if (1 < this.getLife())
				{
					this.setHealth(this.getLife() - 1.0F);
				}
			}
			else
			{
				if (!this.isSitting() && !this.isHealing() && !this.isWaiting())
				{
					this.baseHelthTimer--;
				}
			}
		}

		if (this.getLife() < (this.getMaxHealth() / 2) && this.rand.nextInt(500) == 0){
			this.funcEmotion.setEmotionLiving(this, DocaFuncEmotion.EMOTION_FOOD);
		}
	}

	public void updateGrowAge()
	{
		if (DocaReg.getGrowAge(DocaReg.getNameToType(this.entityDocaString)))
		{
			if (this.isTamed())
			{
				this.setGrowingAge(-24000);
			}
		}
		if (DocaSet.Debug)
		{
			this.setGrowingAge(1);
		}
	}

	@Override
	public void setScaleForAge(boolean par1)
	{
		super.setScaleForAge(false);
	}

	public void setSizeDoca(float par1, float par2)
	{
		if (par1 != this.width || par2 != this.height)
		{
			this.width = par1;
			this.height = par2;
			this.boundingBox.maxX = this.boundingBox.minX + (double)this.width;
			this.boundingBox.maxZ = this.boundingBox.minZ + (double)this.width;
			this.boundingBox.maxY = this.boundingBox.minY + (double)this.height;
		}
	}

	@Override
	public boolean canAttackClass(Class par1Class)
	{
		return EntityBat.class != par1Class && EntityGhast.class != par1Class && EntityBlaze.class != par1Class && EntityDragon.class != par1Class && EntityWither.class != par1Class;
	}

	public DocaEntityAIControlledByPlayer getAIControlledByPlayer()
	{
		return this.aiControlledByPlayer;
	}

	@Override
	public boolean canBeSteered()
	{
		return this.funcRiddenByEntity.canBeSteered(((EntityPlayer)this.riddenByEntity).getHeldItem(), Items.carrot_on_a_stick);
	}
	
    protected void updateFallState(double par1, boolean par3)
    {
        if (!this.isInWater())
        {
            this.handleWaterMovement();
        }

        if (par3 && this.fallDistance > 0.0F)
        {
            int i = MathHelper.floor_double(this.posX);
            int j = MathHelper.floor_double(this.posY - 0.20000000298023224D - (double)this.yOffset);
            int k = MathHelper.floor_double(this.posZ);
            Block block = this.worldObj.getBlock(i, j, k);

            if (block.getMaterial() == Material.air)
            {
                int l = this.worldObj.getBlock(i, j - 1, k).getRenderType();

                if (l == 11 || l == 32 || l == 21)
                {
                    block = this.worldObj.getBlock(i, j - 1, k);
                }
            }
            else if (!this.worldObj.isRemote && this.fallDistance > 3.0F)
            {
                this.worldObj.playAuxSFX(2006, i, j, k, MathHelper.ceiling_float_int(this.fallDistance - 3.0F));
            }
        }
    }

	public void addSubOwnersDoca(String par1String, EntityPlayer player)
	{
		this.addSubOwners(par1String);
		DocaTools.setDocaToSendQueue(this, player, DocaTools.PACKET_SOWN_DATA);
	}
}