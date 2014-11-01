package mods.ffxiwatereraser.event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import mods.ffxibuildassistblock.block.BlockFFxiBuildAssist;
import mods.ffxiwatereraser.core.FFxiSet;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;



public class FFxiLivingUpdateEvent
{
	private static ArrayList<int[]> nextBlock = new ArrayList();

	@ForgeSubscribe
	public void onLivingUpdate(LivingUpdateEvent event)
	{
		if (event.entityLiving instanceof EntityPlayer && !event.entityLiving.worldObj.isRemote && event.entityLiving.worldObj.getWorldTime() % 3 == 0)
		{
			EntityPlayer player = (EntityPlayer)event.entityLiving;

			if (player.getCurrentEquippedItem() != null && player.getCurrentEquippedItem().getItem().itemID == FFxiSet.itemWaterEraser.itemID)
			{
				if (FFxiSet.commandRunning && !FFxiSet.commandUserName.contentEquals(""))
				{
					if (this.nextBlock.isEmpty())
					{
						int counter = Short.MAX_VALUE;
						for (int serachx = FFxiSet.erase_width_min; serachx <= FFxiSet.erase_width; serachx++)
						{
							for (int serachy = FFxiSet.erase_height_min; serachy <= FFxiSet.erase_height; serachy++)
							{
								for (int serachz = FFxiSet.erase_width_min; serachz <= FFxiSet.erase_width; serachz++)
								{
									int[] pos = new int[3];
									pos[0] = FFxiSet.searchX + serachx;
									pos[1] = FFxiSet.searchY + serachy;
									pos[2] = FFxiSet.searchZ + serachz;

									int tmpBlockId = event.entityLiving.worldObj.getBlockId(pos[0], pos[1], pos[2]);

									if(tmpBlockId == Block.waterStill.blockID)// || tmpBlockId == Block.waterMoving.blockID)
									{
										this.nextBlock.add(pos);
										--counter;
										if (counter <= 0) { return; }
									}
								}
							}
						}
						
						if (this.nextBlock.isEmpty())
						{
							FFxiSet.commandUserName = "";
							FFxiSet.commandRunning = false;
							this.nextBlock.clear();
						}
					}
					else if (!this.nextBlock.isEmpty())
					{
						int counter = this.nextBlock.size() - 1;
						for (int i = counter; 0 <= i; i--)
						{
							if (i <= counter - FFxiSet.erase_blocks) { break; }
							
							int[] pos = new int[3];
							pos = nextBlock.get(i);
							event.entityLiving.worldObj.setBlockToAir(pos[0], pos[1], pos[2]);
							this.nextBlock.remove(i);
						}
						
						if (nextBlock.isEmpty())
						{
							FFxiSet.commandUserName = "";
							FFxiSet.commandRunning = false;
						}
					}
				}
			}
			else
			{
				FFxiSet.commandUserName = "";
				FFxiSet.commandRunning = false;
				this.nextBlock.clear();
			}
		}
	}
}