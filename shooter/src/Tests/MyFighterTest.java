package Tests;
import GameScenes.*;
import ShooterComponents.Bullet;
import ShooterComponents.Fighter;
import ShooterDrawers.FighterA;
import ShooterDrawers.ParticleJetEffect;
import ShooterUpdater.RangeToBound;
import GameComponents.*;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import AwtInputBridges.*;

public class MyFighterTest implements Scene.Visitor {
	private AwtKeyBridge keyboard = 
			new AwtKeyBridge();
	private double FPS;
	private double width,height;
	
	public MyFighterTest(java.awt.Component _form,double _FPS,double _width,double _height) throws IllegalArgumentException {
		if(!(_form!=null))throw new IllegalArgumentException("MyFighterTest _form is null");
		if(!(_FPS > 0))throw new IllegalArgumentException("MyFigterTest _FPS must be greater than 0");
		if(!(_width > 0))throw new IllegalArgumentException("MyFighter _width must be greater than 0");
		if(!(_height > 0))throw new IllegalArgumentException("Myfighter _height must be greater than 0");
		
		_form.addKeyListener(keyboard);
		FPS = _FPS;
		width = _width;
		height = _height;
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
		
		Fighter me = new Fighter();
		try {
			_container.AddChild(me);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Rect bound = me.GetBounds();
		bound.w = 4;
		bound.h = 4;
		bound.x = width/2;
		bound.y = height-30;
		
		me.AddGameCharacterDrawer(
				new ShooterDrawers.FighterA(
						false,Color.white,10,3
						)
				);
		me.AddGameCharacterDrawer(
				new ShooterDrawers.ComponentBound(true, Color.red)
				);
		me.AddGameCharacterDrawer(
				new ShooterDrawers.ParticleJetEffect(
						5,2,
						20,Math.PI/2.0,
						Math.PI * 0.1, 150,
						3, Color.red,
						20, FPS,
						0,10
						));
		
		ShooterUpdater.MyFighterMove move =
				new ShooterUpdater.MyFighterMove(keyboard);
		double speed = 100;
		move.RegisterKeyConfig(KeyEvent.VK_UP, new Vector2(0,-speed),FPS);
		move.RegisterKeyConfig(KeyEvent.VK_DOWN, new Vector2(0,speed),FPS);
		move.RegisterKeyConfig(KeyEvent.VK_LEFT, new Vector2(-speed,0),FPS);
		move.RegisterKeyConfig(KeyEvent.VK_RIGHT, new Vector2(speed,0),FPS);
		me.AddGameCharacterUpdater(move);
		

		me.AddGameCharacterUpdater(new RangeToBound(20,20,width-40,height-40));
		
		keyboard.AddKeyBridgeListener(
				new KeyBridge.Listener() {

					@Override
					public void BeginKeyDown(KeyBridge _sender, Map<Integer, Boolean> _keyboard) {
						// TODO Auto-generated method stub
						if(!_sender.GetKeyDown(KeyEvent.VK_SPACE)) return;
						try {
							me.Shot(
									new ShooterFactories.NWwayShotVisitor(3, 400, FPS, -Math.PI/2.0, 10, -Math.PI*0),
									0, -10);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}

					@Override
					public void EndKeyDown(KeyBridge _sender, Map<Integer, Boolean> _keyboard) {
						// TODO Auto-generated method stub
						
					}
					
				});
	}

}
