package mods.doca.core.handler;

import java.util.EnumSet;
import java.util.List;

import mods.doca.*;
import mods.doca.core.*;
import mods.doca.entity.*;
import net.minecraft.client.*;
import net.minecraft.client.entity.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.settings.*;

import org.lwjgl.input.*;
import cpw.mods.fml.client.registry.KeyBindingRegistry.*;
import cpw.mods.fml.common.TickType;
import cpw.mods.fml.server.FMLServerHandler;

public class DocaKeyHandler extends KeyHandler
{
	private static KeyBinding keyBindingFRE = new KeyBinding("DocaKey Free", DocaSet.key_CommandFree);
	private static KeyBinding keyBindingCOM = new KeyBinding("DocaKey Comeing", DocaSet.key_CommandComeing);
	private static KeyBinding keyBindingDWN = new KeyBinding("DocaKey Downing", DocaSet.key_CommandDowning);
	private static KeyBinding keyBindingDIS = new KeyBinding("DocaKey Distance", DocaSet.key_CommandDistance);
	private static KeyBinding keyBindingINF = new KeyBinding("DocaKey Infomation", DocaSet.key_CommandInfomation);
	private static KeyBinding keyBindingSIT = new KeyBinding("DocaKey Sit", DocaSet.key_ControlSit);
	private static KeyBinding keyBindingINV = new KeyBinding("DocaKey Inventory", DocaSet.key_ControlInventoryGUI);
	private static KeyBinding keyBindingSET = new KeyBinding("DocaKey Setting", DocaSet.key_ControlSettingGUI);
	private static KeyBinding keyBindingHOM = new KeyBinding("DocaKey Home", DocaSet.key_ControlHomePoint);

	public static boolean[] stateCtrlkey = new boolean[DocaTools.KEY_MAX];

	public DocaKeyHandler()
	{
		super(new KeyBinding[] {keyBindingFRE, keyBindingCOM, keyBindingDWN,
				keyBindingDIS, keyBindingINF, keyBindingSIT, keyBindingINV,
				keyBindingSET, keyBindingHOM},
				new boolean[] {false, false, false, false, false, false, false, false, false});
	}

	@Override
	public String getLabel()
	{
		return "docakeybindings";
	}

	@Override
	public void keyDown(EnumSet<TickType> types, KeyBinding kb, boolean tickEnd, boolean isRepeat)
	{
		this.keyDownDoca(types, kb, tickEnd, isRepeat);
	}

	@Override
	public void keyUp(EnumSet<TickType> types, KeyBinding kb, boolean tickEnd)
	{
		this.keyUpDoca(types, kb, tickEnd);
	}

	@Override
	public EnumSet<TickType> ticks()
	{
		return EnumSet.of(TickType.CLIENT);
	}

	public void keyDownDoca(EnumSet<TickType> types, KeyBinding kb, boolean tickEnd, boolean isRepeat)
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

	public void keyUpDoca(EnumSet<TickType> types, KeyBinding kb, boolean tickEnd)
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
}