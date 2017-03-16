package Tests;

import GameScenes.*;
import ShooterComponents.Bullet;
import ShooterComponents.Fighter;
import ShooterComponents.LifeCharacter;

import java.awt.Color;
import java.awt.Container;
import java.util.List;

import AwtInputBridges.AwtKeyBridge;
import GameComponents.*;

public class AutoFighterFactoryTest implements Scene.Visitor {
	
	private AwtKeyBridge keyboard =
			new AwtKeyBridge();
	private double width,height,FPS;
	private IScreenDrawPos spos;
	public AutoFighterFactoryTest(
			java.awt.Component _parent,
			double _width,
			double _height,
			double _FPS,
			IScreenDrawPos _spos
			)throws IllegalArgumentException {
		if(!(_parent!=null))throw new IllegalArgumentException("AutoFighterFactoryTest _parent is null");
		if(!(_width > 0))throw new IllegalArgumentException("AutoFighterFactoryTest _width must be greater than 0");
		if(!(_height > 0))throw new IllegalArgumentException("AutoFighterFactoryTest _height must be greater than 0");
		if(!(_FPS > 0))throw new IllegalArgumentException("AutoFighterFactoryTest _FPS must be greater than 0");
		if(!(_spos!=null))throw new IllegalArgumentException("AutoFighterFactoryTest _spos must be greater than 0");
		_parent.addKeyListener(keyboard);
		width = _width;
		height = _height;
		FPS = _FPS;
		spos = _spos;
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
		
		final Fighter me = 
				(Fighter)(
						new ShooterFactories.TestMyFighterFactory(
								keyboard,
								width,height,FPS,_container
								)
						.CreateObject()
						);
		me.SetCollisionable(true);

		final CollisionContainer<Rect,Circle> myBulletWithEnemy =
				new CollisionContainer<Rect,Circle>(
						);
		final CollisionContainer<Rect,Circle> enemyBulletWithMe =
				new CollisionContainer<Rect,Circle>();

		for(Bullet j : me.GetBulletSet()) {
			myBulletWithEnemy.AddListA(j);
		}
		enemyBulletWithMe.AddListB(me);
		
		for(int i = 0 ; i < 3;i++) {
			final Fighter enemy =
					(Fighter)(
							new ShooterFactories.AutoFighterFactory(
									3,
									new Vector2(10,0),
									Color.white,
									12,
									FPS,
									_container,
									width,height,
									120,
									3,
									80,
									100,
									10000
									)
							.CreateObject()
							);
			enemy.SetCollisionable(true);
			enemy.GetBounds().y = 30 + i * 80;
			enemy.GetBounds().x = i * 80 + 30;
			final ScreenShaker shaker =
					(ScreenShaker)(
							new ShooterFactories.DestroyScreenShakerFactory(
									enemy,
									100,100,FPS,
									spos,
									_container
									)
							.CreateObject()
							);
			myBulletWithEnemy.AddListB(enemy);
			

			for(Bullet j : enemy.GetBulletSet()) {
				enemyBulletWithMe.AddListA(j);
			}
		}
		
		
		try {
			_container.AddChild(myBulletWithEnemy);
			_container.AddChild(enemyBulletWithMe);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		

		
		
	}

}
