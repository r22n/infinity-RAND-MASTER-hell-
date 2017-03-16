package Tests;

import GameComponents.ComponentContainer;
import GameComponents.*;
import GameScenes.*;

import java.awt.Color;
import java.awt.Graphics;

import AwtInputBridges.*;
import ShooterComponents.*;

public class AwtMouseInputTest implements Scene.Visitor{
	private AwtMouseBridge mouse = new AwtMouseBridge();
	private double FPS ;
	public AwtMouseInputTest(double _FPS,java.awt.Component _mouse)throws IllegalArgumentException {
		if(!(_mouse!=null))throw new IllegalArgumentException("AwtMouseInputTest _mouse is null");
		if(!(_FPS > 0))throw new IllegalArgumentException("AwtMouseInputTest _FPS must be greater than 0");
		_mouse.addMouseListener(mouse);
		_mouse.addMouseMotionListener(mouse);
		FPS = _FPS;
	}
	
	
	@Override
	public void Visit(Scene _this) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void Visit(ComponentContainer _container) {
		// TODO Auto-generated method stub
		try {
			mouse.AddChild(_container);
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
		bounds.w = 5;
		bounds.h = 5;
		me.AddGameCharacterDrawer(new ShooterDrawers.FighterA(false, Color.white, 10, 3));
		me.AddGameCharacterDrawer(
				new ShooterDrawers.ParticleJetEffect(
						5,1,
						15,Math.PI/2.0,
						Math.PI * 0.3,100,
						3,Color.white,
						50,FPS,
						2,10
						)
				);
		me.AddGameCharacterDrawer(new GameCharacterDrawer() {

			@Override
			public void BeginDraw(GameCharacter _sender, Graphics _g) {
				// TODO Auto-generated method stub
				_g.setColor(Color.white);
				_g.drawRect(
						(int)bounds.x,(int)bounds.y,
						(int)bounds.w,(int)bounds.h
						);
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
				bounds.x = mouse.GetXLocation();
				bounds.y = mouse.GetYLocation();
			}
			
		});
		
	}

}
