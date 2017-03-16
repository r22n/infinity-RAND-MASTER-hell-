package Tests;

import GameScenes.*;
import ShooterComponents.Bullet;
import ShooterComponents.Fighter;
import ShooterComponents.GameCharacter;
import ShooterComponents.GameCharacterDrawer;
import ShooterComponents.GameCharacterUpdater;
import ShooterComponents.LifeCharacter;
import GameComponents.*;

import java.awt.Color;

import AwtInputBridges.*;
import ShooterFactories.*;
import ShooterUpdater.BulletMove;

public class MyFighterFactoryTest implements Scene.Visitor{
	
	private AwtKeyBridge keyboard = new AwtKeyBridge();
	private double width,height,FPS;
	
	public MyFighterFactoryTest(java.awt.Component _frame,double _width,double _height,double _FPS) throws IllegalArgumentException {
		if(!(_frame!=null))throw new IllegalArgumentException("MyFighterFactoryTest _frame is null");
		if(!(_width>0))throw new IllegalArgumentException("MyfighterFactoryTest _width must be greater than 0");
		if(!(_height>0))throw new IllegalArgumentException("MyfighterFactoryTest _height must be greater than 0");
		if(!(_FPS>0))throw new IllegalArgumentException("MyfighterFactoryTest _FPS must be greater than 0");
		
		width = _width;
		height = _height;
		FPS = _FPS;
		
		_frame.addKeyListener(keyboard);
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
		Fighter f = (Fighter)(
				new ShooterFactories.TestMyFighterFactory(
				keyboard, width, height, FPS, _container
				).CreateObject()
				);
		ShooterDrawers.MyFighterBase base = 
				new ShooterDrawers.MyFighterBase(
						50,10,FPS,Color.red,200
						);
		f.AddGameCharacterDrawer(base);
		base.SetSpeed(
				f.GetBounds().x+f.GetBounds().w/2.0,
				f.GetBounds().y+f.GetBounds().h/2.0,
				0,0,
				FPS
				);
		base.BeginPlay();
				

		RandomBoundFactory backGround = 
				new RandomBoundFactory(
						Color.white,
						width,height,
						width*0.3,width*0.7,
						_container
						);
		for(int i = 0 ; i  < 90;i++) {
			GameCharacter g = (GameCharacter)backGround.CreateObject();
			g.AddGameCharacterUpdater(
					new ShooterUpdater.BulletMove(
							new Rect(0,0,width,height),
							0,100*Math.random()+50,
							FPS
							)
					);
		}
		
		CollisionContainer<Rect,Circle> collisions = 
				new CollisionContainer<Rect,Circle>();
		try {
			_container.AddChild(collisions);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		for(Component i : f) {
			if(!(i instanceof Collisionable))continue;
			collisions.AddListA((Collisionable<Rect,Circle>)i);
		}
		
		final Fighter targetTest = 
				new Fighter();
		try {
			_container.AddChild(targetTest);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		collisions.AddListB(targetTest);
		/*test*/ {
			Rect bounds = targetTest.GetBounds();
			bounds.x = width/2.0 - bounds.w/2.0;
			bounds.y = 50;
			
			ShooterDrawers.ComponentBound body;
			targetTest.AddGameCharacterDrawer(
					body = new ShooterDrawers.ComponentBound(false, Color.white)
					);
			final ShooterDrawers.CircleExpandDestroyEffect destroy =
					new ShooterDrawers.CircleExpandDestroyEffect(
							10,100,Color.white,(int)FPS,FPS,0,0
							);
			targetTest.AddGameCharacterDrawer(destroy);
			targetTest.AddLifeCharacterListener(
					new LifeCharacter.Listener() {
						
						@Override
						public void OnUnderMinLife(LifeCharacter _sender) {
							// TODO Auto-generated method stub
							destroy.BeginPlay();
							body.EndPlay();
							collisions.RemoveFromB(targetTest);
						}
						
						@Override
						public void OnOverMaxLife(LifeCharacter _sender) {
							// TODO Auto-generated method stub
							
						}
						
						@Override
						public void OnIncreaseLife(LifeCharacter _sender, double _life) {
							// TODO Auto-generated method stub
							
						}
						
						@Override
						public void OnDecreaseLife(LifeCharacter _sender, double _life) {
							// TODO Auto-generated method stub
							
						}
					});
		}
		
		
		_container.ApplyPriority();				
	}

}
