package mods.doca.core.handler;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.StatCollector;
import mods.doca.core.*;
import mods.doca.entity.DocaEntityBase;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.ClientTickEvent;

public class DocaClientTickEvent
{

	private static KeyBinding keyBindingFRE;
	private static KeyBinding keyBindingCOM;
	private static KeyBinding keyBindingDWN;
	private static KeyBinding keyBindingDIS;
	private static KeyBinding keyBindingINF;
	private static KeyBinding keyBindingSIT;
	private static KeyBinding keyBindingINV;
	private static KeyBinding keyBindingSET;
	private static KeyBinding keyBindingHOM;

	private static KeyBinding[] keyBindings;
	private static boolean[] keyDown;
	private static boolean[] repeatings;
	private boolean isDummy;

	public static boolean[] stateCtrlkey = new boolean[DocaTools.KEY_MAX];

	public static void registerKeyBinding()
	{
		keyBindingFRE = new KeyBinding("doca.key.Free", DocaSet.key_CommandFree, "key.categories.dogcatplus");
		keyBindingCOM = new KeyBinding("doca.key.Comeing", DocaSet.key_CommandComeing, "key.categories.dogcatplus");
		keyBindingDWN = new KeyBinding("doca.key.Downing", DocaSet.key_CommandDowning, "key.categories.dogcatplus");
		keyBindingDIS = new KeyBinding("doca.key.Distance", DocaSet.key_CommandDistance, "key.categories.dogcatplus");
		keyBindingINF = new KeyBinding("doca.key.Infomation", DocaSet.key_CommandInfomation, "key.categories.dogcatplus");
		keyBindingSIT = new KeyBinding("doca.key.Sit", DocaSet.key_ControlSit, "key.categories.dogcatplus");
		keyBindingINV = new KeyBinding("doca.key.Inventory", DocaSet.key_ControlInventoryGUI, "key.categories.dogcatplus");
		keyBindingSET = new KeyBinding("doca.key.Setting", DocaSet.key_ControlSettingGUI, "key.categories.dogcatplus");
		keyBindingHOM = new KeyBinding("doca.key.Home", DocaSet.key_ControlHomePoint, "key.categories.dogcatplus");

		KeyBinding[] initKey = { keyBindingFRE, keyBindingCOM, keyBindingDWN, keyBindingDIS, keyBindingINF, keyBindingSIT, keyBindingINV, keyBindingSET, keyBindingHOM };
		boolean[] initRepeatings = {false,         false,         false,         false,         false,         false,         false,         false,         false };

		keyBindings = initKey;
		repeatings = initRepeatings;
		keyDown = initRepeatings;
			
		for (int i = 0; i < keyBindings.length; i++) {
			ClientRegistry.registerKeyBinding(keyBindings[i]);
		}

/**		GameSettings gameSettings = Minecraft.getMinecraft().gameSettings;//new GameSettings(Minecraft.getMinecraft(), Minecraft.getMinecraft().mcDataDir);
		
		gameSettings.loadOptions();

		KeyBinding[] mcKeyBinding = gameSettings.keyBindings;
		if (mcKeyBinding != null)
		{
			for(KeyBinding mckey : mcKeyBinding)
			{
				for(KeyBinding modKey : keyBindings)
				{
					if (modKey.getKeyDescription().equalsIgnoreCase(mckey.getKeyDescription()))
					{
						mckey = modKey;
					}
				}
			}
		}
**/
	}

	@SubscribeEvent
	public void onClientTickEvent(ClientTickEvent event)
	{
		GuiScreen guiscreen = Minecraft.getMinecraft().currentScreen;

		if (guiscreen != null)
		{
		}
		else
		{
			this.onKeyDocaClientTick();
			this.onTickInGame();
		}

	}

	private void onKeyDocaClientTick()
	{
		for (int i = 0; i < keyBindings.length; i++)
		{
			KeyBinding keyBinding = keyBindings[i];
			int keyCode = keyBinding.getKeyCode();
			boolean state = (keyCode < 0 ? Mouse.isButtonDown(keyCode + 100) : Keyboard.isKeyDown(keyCode));
			if (state != keyDown[i] || (state && repeatings[i]))
			{
				if (state)
				{
					keyDownDoca(keyBinding, state!=keyDown[i]);
				}
				else
				{
					keyUpDoca(keyBinding);
				}
				keyDown[i] = state;
			}
		}
	}

	public void keyDownDoca(KeyBinding kb, boolean isRepeat)
	{
		if (kb == keyBindingFRE){
			DocaSet.tempKeyLocalDoca.setKeys(DocaTools.KEY_FRE, true);
		}
		else if (kb == keyBindingCOM){
			DocaSet.tempKeyLocalDoca.setKeys(DocaTools.KEY_COM, true);
		}
		else if (kb == keyBindingDWN){
			DocaSet.tempKeyLocalDoca.setKeys(DocaTools.KEY_DWN, true);
		}
		else if (kb == keyBindingDIS){
			DocaSet.tempKeyLocalDoca.setKeys(DocaTools.KEY_DIS, true);
		}
		else if (kb == keyBindingINF){
			DocaSet.tempKeyLocalDoca.setKeys(DocaTools.KEY_INF, true);
		}
		else if (kb == keyBindingSIT){
			DocaSet.tempKeyLocalDoca.setKeys(DocaTools.KEY_SIT, true);
		}
		else if (kb == keyBindingINV){
			DocaSet.tempKeyLocalDoca.setKeys(DocaTools.KEY_INV, true);
		}
		else if (kb == keyBindingSET){
			DocaSet.tempKeyLocalDoca.setKeys(DocaTools.KEY_SET, true);
		}
		else if (kb == keyBindingHOM){
			DocaSet.tempKeyLocalDoca.setKeys(DocaTools.KEY_HOM, true);
		}
	}

	public void keyUpDoca(KeyBinding kb)
	{
		if (kb == keyBindingFRE){
			DocaSet.tempKeyLocalDoca.setKeys(DocaTools.KEY_FRE, false);
		}
		else if (kb == keyBindingCOM){
			DocaSet.tempKeyLocalDoca.setKeys(DocaTools.KEY_COM, false);
		}
		else if (kb == keyBindingDWN){
			DocaSet.tempKeyLocalDoca.setKeys(DocaTools.KEY_DWN, false);
		}
		else if (kb == keyBindingDIS){
			DocaSet.tempKeyLocalDoca.setKeys(DocaTools.KEY_DIS, false);
		}
		else if (kb == keyBindingINF){
			DocaSet.tempKeyLocalDoca.setKeys(DocaTools.KEY_INF, false);
		}
		else if (kb == keyBindingSIT){
			DocaSet.tempKeyLocalDoca.setKeys(DocaTools.KEY_SIT, false);
		}
		else if (kb == keyBindingINV){
			DocaSet.tempKeyLocalDoca.setKeys(DocaTools.KEY_INV, false);
		}
		else if (kb == keyBindingSET){
			DocaSet.tempKeyLocalDoca.setKeys(DocaTools.KEY_SET, false);
		}
		else if (kb == keyBindingHOM){
			DocaSet.tempKeyLocalDoca.setKeys(DocaTools.KEY_HOM, false);
		}
	}

	public void onTickInGame()
	{
		if (DocaSet.keyLocalDoca.getTimer() == 0)
		{
			boolean tmpSend = false;
			for (int i = DocaTools.KEY_MIN; i < DocaTools.KEY_MAX; i++)
			{
				if (DocaSet.keyLocalDoca.getKeys(i) != DocaSet.tempKeyLocalDoca.getKeys(i))
				{
					DocaSet.tempKeyLocalDoca.setUserName(Minecraft.getMinecraft().thePlayer.getUniqueID().toString());
					tmpSend = true;
					break;
				}
			}
			if(tmpSend)
			{
				DocaSet.keyLocalDoca.setUserName(DocaSet.tempKeyLocalDoca.getUserName());
				DocaSet.keyLocalDoca.setTimer(2);

				if (DocaSet.DebugPaket)
				{
					String[] temp = new String[DocaTools.KEY_MAX];
					if (DocaSet.keyLocalDoca.getKeys(DocaTools.KEY_COM) != DocaSet.tempKeyLocalDoca.getKeys(DocaTools.KEY_COM))
					{
						temp[0] = "KEY_COM";
					}
					if (DocaSet.keyLocalDoca.getKeys(DocaTools.KEY_DIS) != DocaSet.tempKeyLocalDoca.getKeys(DocaTools.KEY_DIS))
					{
						temp[1] = "KEY_DIS";
					}
					if (DocaSet.keyLocalDoca.getKeys(DocaTools.KEY_DWN) != DocaSet.tempKeyLocalDoca.getKeys(DocaTools.KEY_DWN))
					{
						temp[2] = "KEY_DWN";
					}
					if (DocaSet.keyLocalDoca.getKeys(DocaTools.KEY_FRE) != DocaSet.tempKeyLocalDoca.getKeys(DocaTools.KEY_FRE))
					{
						temp[3] = "KEY_FRE";
					}
					if (DocaSet.keyLocalDoca.getKeys(DocaTools.KEY_INF) != DocaSet.tempKeyLocalDoca.getKeys(DocaTools.KEY_INF))
					{
						temp[4] = "KEY_INF";
					}
					if (DocaSet.keyLocalDoca.getKeys(DocaTools.KEY_SIT) != DocaSet.tempKeyLocalDoca.getKeys(DocaTools.KEY_SIT))
					{
						temp[5] = "KEY_SIT";
					}
					if (DocaSet.keyLocalDoca.getKeys(DocaTools.KEY_INV) != DocaSet.tempKeyLocalDoca.getKeys(DocaTools.KEY_INV))
					{
						temp[6] = "KEY_INV";
					}
					if (DocaSet.keyLocalDoca.getKeys(DocaTools.KEY_SET) != DocaSet.tempKeyLocalDoca.getKeys(DocaTools.KEY_SET))
					{
						temp[7] = "KEY_SET";
					}
					if (DocaSet.keyLocalDoca.getKeys(DocaTools.KEY_HOM) != DocaSet.tempKeyLocalDoca.getKeys(DocaTools.KEY_HOM))
					{
						temp[8] = "KEY_HOM";
					}

//					System.out.println(">>>>>>>>>>>>>>>>>>> : setKeyToSendQueue"
//							+ " : " + temp[0] + "|" + DocaSet.keyLocalDoca.getKeys(DocaTools.KEY_COM) + "|" + DocaSet.tempKeyLocalDoca.getKeys(DocaTools.KEY_COM)
//							+ " : " + temp[1] + "|" + DocaSet.keyLocalDoca.getKeys(DocaTools.KEY_DIS) + "|" + DocaSet.tempKeyLocalDoca.getKeys(DocaTools.KEY_DIS)
//							+ " : " + temp[2] + "|" + DocaSet.keyLocalDoca.getKeys(DocaTools.KEY_DWN) + "|" + DocaSet.tempKeyLocalDoca.getKeys(DocaTools.KEY_DWN)
//							+ " : " + temp[3] + "|" + DocaSet.keyLocalDoca.getKeys(DocaTools.KEY_FRE) + "|" + DocaSet.tempKeyLocalDoca.getKeys(DocaTools.KEY_FRE)
//							+ " : " + temp[4] + "|" + DocaSet.keyLocalDoca.getKeys(DocaTools.KEY_INF) + "|" + DocaSet.tempKeyLocalDoca.getKeys(DocaTools.KEY_INF)
//							+ " : " + temp[5] + "|" + DocaSet.keyLocalDoca.getKeys(DocaTools.KEY_SIT) + "|" + DocaSet.tempKeyLocalDoca.getKeys(DocaTools.KEY_SIT)
//							+ " : " + temp[6] + "|" + DocaSet.keyLocalDoca.getKeys(DocaTools.KEY_INV) + "|" + DocaSet.tempKeyLocalDoca.getKeys(DocaTools.KEY_INV)
//							+ " : " + temp[7] + "|" + DocaSet.keyLocalDoca.getKeys(DocaTools.KEY_SET) + "|" + DocaSet.tempKeyLocalDoca.getKeys(DocaTools.KEY_SET)
//							+ " : " + temp[8] + "|" + DocaSet.keyLocalDoca.getKeys(DocaTools.KEY_HOM) + "|" + DocaSet.tempKeyLocalDoca.getKeys(DocaTools.KEY_HOM));
				}
				if (!DocaSet.keyLocalDoca.getKeys(DocaTools.KEY_COM) && DocaSet.tempKeyLocalDoca.getKeys(DocaTools.KEY_COM))
				{
					DocaClientTickEvent.printMassageDoca(DocaTools.KEY_COM, DocaSet.tempKeyLocalDoca.getUserName());
				}
				else if (!DocaSet.keyLocalDoca.getKeys(DocaTools.KEY_DIS) && DocaSet.tempKeyLocalDoca.getKeys(DocaTools.KEY_DIS))
				{
					DocaClientTickEvent.printMassageDoca(DocaTools.KEY_DIS, DocaSet.tempKeyLocalDoca.getUserName());
				}
				else if (!DocaSet.keyLocalDoca.getKeys(DocaTools.KEY_DWN) && DocaSet.tempKeyLocalDoca.getKeys(DocaTools.KEY_DWN))
				{
					DocaClientTickEvent.printMassageDoca(DocaTools.KEY_DWN, DocaSet.tempKeyLocalDoca.getUserName());
				}
				else if (!DocaSet.keyLocalDoca.getKeys(DocaTools.KEY_FRE) && DocaSet.tempKeyLocalDoca.getKeys(DocaTools.KEY_FRE))
				{
					DocaClientTickEvent.printMassageDoca(DocaTools.KEY_FRE, DocaSet.tempKeyLocalDoca.getUserName());
				}
				else if (!DocaSet.keyLocalDoca.getKeys(DocaTools.KEY_INF) && DocaSet.tempKeyLocalDoca.getKeys(DocaTools.KEY_INF))
				{
					DocaClientTickEvent.printMassageDoca(DocaTools.KEY_INF, DocaSet.tempKeyLocalDoca.getUserName());
				}
				for (int i = DocaTools.KEY_MIN; i < DocaTools.KEY_MAX; i++)
				{
					DocaSet.keyLocalDoca.setKeys(i, DocaSet.tempKeyLocalDoca.getKeys(i));
				}


				DocaTools.setDocaToSendQueue(DocaSet.keyLocalDoca, DocaTools.PACKET_KEY_CHECK);
			}

		}
		else if (0 < DocaSet.keyLocalDoca.getTimer())
		{
			DocaSet.keyLocalDoca.setTimer(DocaSet.keyLocalDoca.getTimer() - 1);
		}
	}

	private static void printMassageDoca(int type, String name)
	{
		if (type == DocaTools.KEY_COM)
		{
			DocaTools.setChatMassageDoca(StatCollector.translateToLocal("doca.chat.message.ModeStatusCome"));
		}
		else if (type == DocaTools.KEY_DIS)
		{
			DocaTools.setChatMassageDoca(StatCollector.translateToLocal("doca.chat.message.ModeStatusDistance"));
		}
		else if (type == DocaTools.KEY_DWN)
		{
			DocaTools.setChatMassageDoca(StatCollector.translateToLocal("doca.chat.message.ModeStatusDown"));
		}
		else if (type == DocaTools.KEY_FRE)
		{
			DocaTools.setChatMassageDoca(StatCollector.translateToLocal("doca.chat.message.ModeStatusFree"));
		}
		else if (type == DocaTools.KEY_INF)
		{
			int tempCounter = 0;
			List tempEList = Minecraft.getMinecraft().theWorld.loadedEntityList;

			if (!tempEList.isEmpty())
			{
				for (int i = 0; i < tempEList.size(); i++)
				{
					if (tempEList.get(i) instanceof DocaEntityBase)
					{
						DocaEntityBase tmpEntity = (DocaEntityBase)tempEList.get(i);
						String tmpPosX = (new StringBuilder()).append((int)tmpEntity.posX).toString();
						String tmpPosY = (new StringBuilder()).append((int)tmpEntity.posY).toString();
						String tmpPosZ = (new StringBuilder()).append((int)tmpEntity.posZ).toString();

						if (name.equalsIgnoreCase(tmpEntity.func_152113_b()))
						{
							String textMassage = tmpEntity.getEntityDocaString();
							String textMassageName = tmpEntity.getName();
							if (textMassageName == "") { textMassageName = "No Name"; }
							DocaTools.setChatMassageDoca(StatCollector.translateToLocal("doca.chat.message.Infoation") + " " + textMassage + "(" + tempCounter + ":" + textMassageName + ")" + " X: " + tmpPosX + " Y: " + tmpPosY + " Z:" + tmpPosZ);
							tempCounter++;
						}
						if (20 <= tempCounter)
						{
							DocaTools.setChatMassageDoca(StatCollector.translateToLocal("doca.chat.message.InfoationOver"));
							break;
						}
					}
				}
			}
		}
	}
}
