package mods.ffxireraiseghostmob.entity;

import java.util.Calendar;
import java.util.UUID;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mods.ffxireraiseghostmob.core.FFxiSet;
import mods.ffxireraiseghostmob.core.FFxiTool;
import mods.ffxireraiseghostmob.entity.ai.EntityAIReraiseGhostAttackOnCollide;
import mods.ffxireraiseghostmob.entity.ai.EntityAIReraiseGhostNearestAttackableTarget;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIBreakDoor;
import net.minecraft.entity.ai.EntityAIFleeSun;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveThroughVillage;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIRestrictSun;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.ai.attributes.RangedAttribute;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeModContainer;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.event.entity.living.ZombieEvent.SummonAidEvent;

public class EntityReraiseGhost extends EntityMob
{
    protected static final IAttribute field_110186_bp = (new RangedAttribute("zombie.spawnReinforcements", 0.0D, 0.0D, 1.0D)).setDescription("Spawn Reinforcements Chance");
	private static final UUID babySpeedBoostUUID = UUID.fromString("B9766B59-9566-4402-BC1F-2EE2A276D836");
	private static final AttributeModifier babySpeedBoostModifier = new AttributeModifier(babySpeedBoostUUID, "Baby speed boost", 0.2D, 1);
    private final EntityAIBreakDoor field_146075_bs = new EntityAIBreakDoor(this);

	private float field_146074_bv = -1.0F;
    private float field_146073_bw;
    
	public EntityReraiseGhost(World par1World)
	{
		super(par1World);
		this.getNavigator().setBreakDoors(true);
		this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIRestrictSun(this));
        this.tasks.addTask(2, new EntityAIFleeSun(this, 1.0D));
		this.tasks.addTask(3, new EntityAIBreakDoor(this));
		this.tasks.addTask(4, new EntityAIAttackOnCollide(this, EntityPlayer.class, 1.0D, false));
		this.tasks.addTask(5, new EntityAIReraiseGhostAttackOnCollide(this, 1.0D, true));
		this.tasks.addTask(6, new EntityAIMoveTowardsRestriction(this, 1.0D));
		this.tasks.addTask(7, new EntityAIMoveThroughVillage(this, 1.0D, false));
		this.tasks.addTask(8, new EntityAIWander(this, 1.0D));
		this.tasks.addTask(9, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
		this.tasks.addTask(9, new EntityAILookIdle(this));
		this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true));
		this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 0, true));
		this.targetTasks.addTask(2, new EntityAIReraiseGhostNearestAttackableTarget(this, 0, false));
        this.setSize(0.6F, 1.8F);
	}

	@Override
	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue((double)FFxiSet.mob_maxHealth);
		this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue((double)FFxiSet.mob_followRange);
		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue((double)FFxiSet.mob_movementSpeed * 0.1);
		this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue((double)FFxiSet.mob_attackDamage);
        this.getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setBaseValue((double)FFxiSet.mob_knockbackResistance * 0.1);
        this.getAttributeMap().registerAttribute(field_110186_bp).setBaseValue(this.rand.nextDouble() * ForgeModContainer.zombieSummonBaseChance);
	}

	@Override
	protected void entityInit()
	{
		super.entityInit();
		this.getDataWatcher().addObject(12, Byte.valueOf((byte)0));
		this.getDataWatcher().addObject(15, "Pig");
	}


	@Override
	public int getTotalArmorValue()
	{
		int i = super.getTotalArmorValue() + 2;

		if (i > 20)
		{
			i = 20;
		}

		return i;
	}

	@Override
	protected boolean isAIEnabled()
	{
		return true;
	}

	@Override
	public boolean isChild()
	{
		return this.getDataWatcher().getWatchableObjectByte(12) == 1;
	}

	public void setChild(boolean par1)
	{
		this.getDataWatcher().updateObject(12, Byte.valueOf((byte)(par1 ? 1 : 0)));

		if (this.worldObj != null && !this.worldObj.isRemote)
		{
			IAttributeInstance iattributeinstance = this.getEntityAttribute(SharedMonsterAttributes.movementSpeed);
			iattributeinstance.removeModifier(babySpeedBoostModifier);

			if (par1)
			{
				iattributeinstance.applyModifier(babySpeedBoostModifier);
			}
		}

		this.setScaleForAge(par1);
	}

	@Override
    protected int getExperiencePoints(EntityPlayer par1EntityPlayer)
    {
        if (this.isChild())
        {
            this.experienceValue = (int)((float)this.experienceValue * 2.5F);
        }

        return super.getExperiencePoints(par1EntityPlayer);
    }
	
	public String getReraiseGhostName()
	{
		return this.getDataWatcher().getWatchableObjectString(15);
	}

	public void setReraiseGhostName(String name)
	{
		this.getDataWatcher().updateObject(15, name);
	}
	

	@Override
    public void onEntityUpdate()
    {
		super.onEntityUpdate();

		if (FFxiSet.func_particle && this.worldObj.getWorldTime() % 10 == 0)
		{
			for (int i = 0; i < 8; ++i)
	        {
	            float f = this.rand.nextFloat() - this.rand.nextFloat();
	            float f1 = this.rand.nextFloat() - this.rand.nextFloat();
	            float f2 = this.rand.nextFloat() - this.rand.nextFloat();
	            this.worldObj.spawnParticle("mobSpell", this.posX + (double)f, this.posY + (double)f1, this.posZ + (double)f2, this.motionX, this.motionY, this.motionZ);
	        }
    	}
    }

	@Override
	public void onLivingUpdate()
	{
		this.equipCounercheck();
		
		if (FFxiSet.func_fireDamage && this.worldObj.isDaytime() && !this.worldObj.isRemote && !this.isChild())
		{
			float f = this.getBrightness(1.0F);
				
			if (f > 0.5F && this.rand.nextFloat() * 30.0F < (f - 0.4F) * 2.0F && this.worldObj.canBlockSeeTheSky(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ)))
			{
				boolean flag = true;
				ItemStack itemstackHead = this.getEquipmentInSlot(4);
				ItemStack itemstackBody = this.getEquipmentInSlot(3);

				if (itemstackHead != null || itemstackBody != null)
				{
					flag = false;
				}

				if (flag)
				{
					this.setFire(8);
				}
			}
		}

		super.onLivingUpdate();
	}

	@Override
	public boolean attackEntityFrom(DamageSource par1DamageSource, float par2)
	{
		if (!FFxiSet.func_fireDamage && (par1DamageSource.getDamageType().equalsIgnoreCase("onFire") || par1DamageSource.getDamageType().equalsIgnoreCase("inFire")))
        {
            return false;
        }
		else if (!super.attackEntityFrom(par1DamageSource, par2))
		{
			return false;
		}
		else
		{
			EntityLivingBase entitylivingbase = this.getAttackTarget();

			if (entitylivingbase == null && this.getEntityToAttack() instanceof EntityLivingBase)
			{
				entitylivingbase = (EntityLivingBase)this.getEntityToAttack();
			}

			if (entitylivingbase == null && par1DamageSource.getEntity() instanceof EntityLivingBase)
			{
				entitylivingbase = (EntityLivingBase)par1DamageSource.getEntity();
			}

			int i = MathHelper.floor_double(this.posX);
			int j = MathHelper.floor_double(this.posY);
			int k = MathHelper.floor_double(this.posZ);
			EntityReraiseGhost entityEntityReraiseGhost = new EntityReraiseGhost(this.worldObj);
			entityEntityReraiseGhost.setReraiseGhostName(this.getReraiseGhostName());

			for (int l = 0; l < 50; ++l)
			{
				int i1 = i + MathHelper.getRandomIntegerInRange(this.rand, 7, 40) * MathHelper.getRandomIntegerInRange(this.rand, -1, 1);
				int j1 = j + MathHelper.getRandomIntegerInRange(this.rand, 7, 40) * MathHelper.getRandomIntegerInRange(this.rand, -1, 1);
				int k1 = k + MathHelper.getRandomIntegerInRange(this.rand, 7, 40) * MathHelper.getRandomIntegerInRange(this.rand, -1, 1);

				if (World.doesBlockHaveSolidTopSurface(this.worldObj, i1, j1 - 1, k1) && this.worldObj.getBlockLightValue(i1, j1, k1) < 10)
				{
					entityEntityReraiseGhost.setPosition((double)i1, (double)j1, (double)k1);

					if (this.worldObj.checkNoEntityCollision(entityEntityReraiseGhost.boundingBox) && this.worldObj.getCollidingBoundingBoxes(entityEntityReraiseGhost, entityEntityReraiseGhost.boundingBox).isEmpty() && !this.worldObj.isAnyLiquid(entityEntityReraiseGhost.boundingBox))
					{
						this.worldObj.spawnEntityInWorld(entityEntityReraiseGhost);
						if (entitylivingbase != null) entityEntityReraiseGhost.setAttackTarget(entitylivingbase);
						entityEntityReraiseGhost.onSpawnWithEgg((IEntityLivingData)null);
						this.getEntityAttribute(field_110186_bp).applyModifier(new AttributeModifier("Zombie reinforcement caller charge", -0.05000000074505806D, 0));
						entityEntityReraiseGhost.getEntityAttribute(field_110186_bp).applyModifier(new AttributeModifier("Zombie reinforcement callee charge", -0.05000000074505806D, 0));
						break;
					}
				}
			}

			return true;
		}
	}

	@Override
	public boolean attackEntityAsMob(Entity par1Entity)
	{
        boolean flag = super.attackEntityAsMob(par1Entity);

        if (flag)
        {
            int i = this.worldObj.difficultySetting.getDifficultyId();

            if (this.getHeldItem() == null && this.isBurning() && this.rand.nextFloat() < (float)i * 0.3F)
            {
                par1Entity.setFire(2 * i);
            }
        }

        return flag;
	}

	@Override
	protected String getLivingSound()
	{
		return "mob.zombie.say";
	}

	@Override
	protected String getHurtSound()
	{
		return "mob.zombie.hurt";
	}

	@Override
	protected String getDeathSound()
	{
		return "mob.zombie.death";
	}

	@Override
	protected void func_145780_a(int par1, int par2, int par3, Block par4Block)
	{
		this.playSound("mob.zombie.step", 0.15F, 1.0F);
	}

	@Override
	protected Item getDropItem()
	{
		return Items.rotten_flesh;
	}

	@Override
	public EnumCreatureAttribute getCreatureAttribute()
	{
		return EnumCreatureAttribute.UNDEAD;
	}

	@Override
	protected void dropRareDrop(int par1)
	{
		switch (this.rand.nextInt(3))
		{
		case 0:
			this.dropItem(Items.iron_ingot, 1);
			break;
		case 1:
			this.dropItem(Items.carrot, 1);
			break;
		case 2:
			this.dropItem(Items.potato, 1);
		}
	}

	@Override
    protected void addRandomArmor()
    {
        super.addRandomArmor();

		for (int i = 0; i < 5; i++)
		{
			Item tmpItem = FFxiTool.getEquipItems(i);
			if (tmpItem != null)
			{
				this.setCurrentItemOrArmor(i, new ItemStack(tmpItem));
			}
		}
    }
	
	@Override
	public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.writeEntityToNBT(par1NBTTagCompound);

		if (this.isChild())
		{
			par1NBTTagCompound.setBoolean("IsBaby", true);
		}
		par1NBTTagCompound.setString("ReraiseGhostName", this.getReraiseGhostName());
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.readEntityFromNBT(par1NBTTagCompound);

		if (par1NBTTagCompound.getBoolean("IsBaby"))
		{
			this.setChild(true);
		}
		this.setReraiseGhostName(par1NBTTagCompound.getString("ReraiseGhostName"));
	}

	@Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData par1EntityLivingData)
    {
        Object p_110161_1_1 = super.onSpawnWithEgg(par1EntityLivingData);
        float f = this.worldObj.func_147462_b(this.posX, this.posY, this.posZ);
        this.setCanPickUpLoot(this.rand.nextFloat() < 0.55F * f);

        if (p_110161_1_1 == null)
        {
            p_110161_1_1 = new GroupDataReraiseGhost(this.worldObj.rand.nextFloat() < ForgeModContainer.zombieBabyChance, null);
        }

        if (p_110161_1_1 instanceof GroupDataReraiseGhost)
        {
        	GroupDataReraiseGhost groupdata = (GroupDataReraiseGhost)p_110161_1_1;

            if (groupdata.child)
            {
                this.setChild(true);
            }
        }

        this.addRandomArmor();
        this.enchantEquipment();

        if (this.getEquipmentInSlot(4) == null)
        {
            Calendar calendar = this.worldObj.getCurrentDate();

            if (calendar.get(2) + 1 == 10 && calendar.get(5) == 31 && this.rand.nextFloat() < 0.25F)
            {
                this.setCurrentItemOrArmor(4, new ItemStack(this.rand.nextFloat() < 0.1F ? Blocks.lit_pumpkin : Blocks.pumpkin));
                this.equipmentDropChances[4] = 0.0F;
            }
        }

        this.getEntityAttribute(SharedMonsterAttributes.knockbackResistance).applyModifier(new AttributeModifier("Random spawn bonus", this.rand.nextDouble() * 0.05000000074505806D, 0));
        double d0 = this.rand.nextDouble() * 1.5D * (double)this.worldObj.func_147462_b(this.posX, this.posY, this.posZ);

        if (d0 > 1.0D)
        {
            this.getEntityAttribute(SharedMonsterAttributes.followRange).applyModifier(new AttributeModifier("Random zombie-spawn bonus", d0, 2));
        }

        if (this.rand.nextFloat() < f * 0.05F)
        {
            this.getEntityAttribute(field_110186_bp).applyModifier(new AttributeModifier("Leader zombie bonus", this.rand.nextDouble() * 0.25D + 0.5D, 0));
            this.getEntityAttribute(SharedMonsterAttributes.maxHealth).applyModifier(new AttributeModifier("Leader zombie bonus", this.rand.nextDouble() * 3.0D + 1.0D, 2));
        }

        return (IEntityLivingData)p_110161_1_1;
    }
	
	@Override
	@SideOnly(Side.CLIENT)
	public void handleHealthUpdate(byte par1)
	{
		if (par1 == 16)
		{
			this.worldObj.playSound(this.posX + 0.5D, this.posY + 0.5D, this.posZ + 0.5D, "mob.zombie.remedy", 1.0F + this.rand.nextFloat(), this.rand.nextFloat() * 0.7F + 0.3F, false);
		}
		else
		{
			super.handleHealthUpdate(par1);
		}
	}

	@Override
	protected boolean canDespawn()
	{
		return FFxiSet.func_canDespawn;
	}
	
	@Override
	protected void fall(float par1)
	{
		if (FFxiSet.func_fallDamage)
		{
			super.fall(par1);
		}
	}

    public void setScaleForAge(boolean par1)
    {
        this.setScale(par1 ? 0.5F : 1.0F);
    }
    
	@Override
    protected final void setSize(float par1, float par2)
    {
        boolean flag = this.field_146074_bv > 0.0F && this.field_146073_bw > 0.0F;
        this.field_146074_bv = par1;
        this.field_146073_bw = par2;

        if (!flag)
        {
            this.setScale(1.0F);
        }
    }

    protected final void setScale(float p_146069_1_)
    {
        super.setSize(this.field_146074_bv * p_146069_1_, this.field_146073_bw * p_146069_1_);
    }

	private void equipCounercheck()
	{
		if (FFxiSet.func_canDespawn && this.worldObj.getWorldTime() % 24000 == 0 && this.rand.nextInt(5) == 0)
		{
			this.setCurrentItemOrArmor(0, null);
		}
	}
}
