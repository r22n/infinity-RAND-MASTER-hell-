package Tests;

import GameScenes.*;
import ShooterComponents.*;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.Map;

import AwtInputBridges.*;
import GameComponents.*;
import java.awt.event.*;

public class AwtKeyboardInputTest implements Scene.Visitor{
	
	private AwtKeyBridge keyboard = new AwtKeyBridge();
	public AwtKeyboardInputTest(java.awt.Frame _keyboardObserver)throws IllegalArgumentException {
		if(!(_keyboardObserver!=null))throw new IllegalArgumentException("AwtKeyboardInputTest _keyboardObserver is null");
		_keyboardObserver.addKeyListener(keyboard);
	}
	@Override
	public void Visit(Scene _this) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void Visit(ComponentContainer _container) {
		// TODO Auto-generated method stub
		try {
			keyboard.AddChild(_container);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		final Fighter me = new Fighter();
		try {
			_container.AddChild(me);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		final Rect bounds = me.GetBounds();
		bounds.x = 100;
		bounds.y = 100;
		final Point ds = new Point();
		me.AddGameCharacterDrawer(new GameCharacterDrawer(){
			@Override
			public void BeginDraw(GameCharacter _sender, Graphics _g) {
				// TODO Auto-generated method stub
				_g.setColor(Color.white);
				_g.drawRect(
					(int)bounds.x,
					(int)bounds.y,
					(int)bounds.w,
					(int)bounds.h
					);
				int j = 0;
				for(Map.Entry<Integer,Boolean> i : keyboard.GetKeyState().entrySet()) {
					_g.drawString(
							String.format("%d : %d", i.getKey(),i.getValue()?1:0),
							10,
							30+12*j
							);
					j++;
				}
			}

			@Override
			public void EndDraw(GameCharacter _sender, Graphics _g) {
				// TODO Auto-generated method stub
				
			}
		});
		me.AddGameCharacterUpdater(new GameCharacterUpdater() {
			@Override
			public void BeginUpdate(GameCharacter _sender) {
				// TODO Auto-generated method stub
			}

			@Override
			public void EndUpdate(GameCharacter _sender) {
				// TODO Auto-generated method stub
				bounds.x += ds.x;
				bounds.y += ds.y;
			}
		});
		keyboard.AddKeyBridgeListener(new KeyBridge.Listener() {
			private boolean Check(Map<Integer,Boolean> _key,int _vk) {
				return _key.containsKey(_vk) && _key.get(_vk);
			}
			@Override
			public void EndKeyDown(KeyBridge _sender, Map<Integer, Boolean> _keyboard) {
				// TODO Auto-generated method stub
				if(!Check(_keyboard,KeyEvent.VK_UP) && !Check(_keyboard,KeyEvent.VK_DOWN)){
					ds.y = 0;
				}

				if(!Check(_keyboard,KeyEvent.VK_RIGHT) && !Check(_keyboard,KeyEvent.VK_LEFT)){
					ds.x = 0;
				}
			}
			
			@Override
			public void BeginKeyDown(KeyBridge _sender, Map<Integer, Boolean> _keyboard) {
				// TODO Auto-generated method stub
				if(Check(_keyboard,KeyEvent.VK_UP)){
					ds.y = -1;
				}else if(Check(_keyboard,KeyEvent.VK_DOWN)){
					ds.y = 1;
				}

				if(Check(_keyboard,KeyEvent.VK_RIGHT)){
					ds.x = 1;
				}else if(Check(_keyboard,KeyEvent.VK_LEFT)){
					ds.x = -1;
				}
			}
		});
	}

}
