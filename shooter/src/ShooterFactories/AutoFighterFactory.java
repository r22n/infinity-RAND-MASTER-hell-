package ShooterFactories;

import ShooterComponents.*;
import ShooterDrawers.CircleExpandDestroyEffect;
import ShooterDrawers.FighterA;
import ShooterUpdater.BulletMove;

import java.awt.Color;
import java.util.List;
import java.util.Queue;

import GameComponents.*;

//!auto move enemy factory
/*!
 * makes auto move and auto shot enemy
 * \author n.ryouta
 */
public class AutoFighterFactory implements Factory<Component>{
	private int design;
	private Vector2 speed;
	private Color color;
	private double size;
	private double FPS;
	private ComponentContainer container;
	private double width,height;
	private long interval;
	private int nWayShot;
	private double bulletSize;
	private double bulletSpeed;
	private double life;
	
	//!initialize
	/*!
	 * \param _design the number of edge 
	 * \param _speed the moving speed in px per sec
	 * \param _color the color of Fighter
	 * \param _size the size of fighter that indicates the radius of circumscribed circle
	 * \param _FPS frame rate
	 * \param _container the container will be stored the result
	 * \param _scrWidth width of screen
	 * \param _scrHeight height of screen
	 * \param _shotInterval interval of shot in frame
	 * \param _nWayShot n-way shot
	 * \param _bulletSize size of bullet 
	 * \param _bulletSpeed speed of bullet in px per sec
	 * \param _life the life of Bullet that damages to Fighter
	 * \exception IllegalArgumentException _design is smaller than 3
	 * \exception IllegalArgumentException _speed is null
	 * \exception IllegalArgumentException _color is null
	 * \exception IllegalArgumentException _size is smaller equal than 0
	 * \exception IllegalArgumentException _FPS is smaller equal than 0
	 * \exception IllegalArgumentException _container is null
	 * \exception IllegalArgumentException _scrWidth is smaller equal than 0
	 * \exception IllegalArgumentException _scrHeight is smaller equal than 0
	 * \exception IllegalArgumentException _shotInterval is smaller equal than 0
	 * \exception IllegalArgumentException _nWayShot is smaller equal than 0
	 * \exception IllegalArgumentException _bulletSize is smaller equal than 0
	 * \exception IllegalArgumentException _bulletSpeed is smaller equal than 0
	 * \exception IllegalArgumentException _life is smaller equal than 0
	 * 
	 */
	public AutoFighterFactory(
			int _design,
			Vector2 _speed,
			Color _color,
			double _size,
			double _FPS,
			ComponentContainer _container,
			double _scrWidth,double _scrHeight,
			long _shotInterval,
			int _nWayShot,
			double _bulletSize,
			double _bulletSpeed,
			double _life
			)throws IllegalArgumentException {
		if(!(_design >= 3))throw new IllegalArgumentException("AutoFighterFactory _design must be greater equal than 3");
		if(!(_speed!=null))throw new IllegalArgumentException("AutoFighterFactory _speed is null");
		if(!(_color!=null))throw new IllegalArgumentException("AutoFighterFactory _color is null");
		if(!(_size>0))throw new IllegalArgumentException("AutoFighterFactory _size must be greater than 0");
		if(!(_FPS > 0))throw new IllegalArgumentException("AutoFighterFactory _FPS must be greater than 0");
		if(!(_container!=null))throw new IllegalArgumentException("AutoFighterFactory _container is null");
		if(!(_scrWidth>0))throw new IllegalArgumentException("AutoFighterFactory _scrWidth must be greater than 0");
		if(!(_scrHeight>0))throw new IllegalArgumentException("AutoFighterFactory _scrHeight must be greater than 0");
		if(!(_shotInterval>0))throw new IllegalArgumentException("AutoFighterFactory _shotInterval must be greater than 0");
		if(!(_nWayShot>0))throw new IllegalArgumentException("AutoFighterFactory _nWayShot must be greater than 0");
		if(!(_bulletSize>0))throw new IllegalArgumentException("AutoFighterFactory _bulletSize must be greater than 0");
		if(!(_bulletSpeed>0))throw new IllegalArgumentException("AutoFighterFactory _bulletSpeed must be greater than 0");
		if(!(_life > 0))throw new IllegalArgumentException("AutoFighterFactory _life must be greater than 0");
		
		design = _design;
		speed = new Vector2(0,0);
		speed.Copy(_speed);
		color = _color;
		size = _size;
		FPS = _FPS;
		container = _container;
		width = _scrWidth;
		height = _scrHeight;
		
		interval = _shotInterval;
		nWayShot = _nWayShot;
		bulletSize = _bulletSize;
		bulletSpeed = _bulletSpeed;
		life = _life;
	}
	//!set enemy move speed
	/*!
	 * \param _speed enemy speed
	 */
	public void SetMoveSpeed(
			Vector2 _speed
			)throws IllegalArgumentException {
		if(!(_speed!=null))throw new IllegalArgumentException("AutoFighterFactory.SetMoveSpeed _speed is null");
		speed.Copy(_speed);
	}
	//!get enemy move speed
	/*!
	 * \returns speed
	 */
	public Vector2 GetMoveSpeed(){return speed;}
	
	@Override
	//!create auto move and auto shot enemy
	/*!
	 * \returns Fighter object that was set up 
	 */
	public Component CreateObject() {
		// TODO Auto-generated method stub
		final Fighter result = 
				new Fighter();
		Rect bounds = result.GetBounds();
		bounds.w = size;
		bounds.h = size;
		/*life*/ {
			result.SetMaxLife(life);
			result.SetMinLife(0);
			result.IncreaseLife(life);
		}
		
		/*move*/ {
			result.AddGameCharacterUpdater(
					new ShooterUpdater.BulletMove(
							new Rect(0,0,width,height),
							speed.x,speed.y,FPS
							)
					);
		}
		
		/*bullet*/ try {
			result.RegisterBullet(
					new ShooterFactories.PlaneBulletFactory(
							bulletSize,
							null,
							new ShooterFactories.StraightBulletMoveFactory(
									width,height,FPS
									),
							new ShooterFactories.PlaneCircleDestroyEffectFactory(
									10,
									100,
									Color.white,
									(int)FPS,FPS,
									0,0
									)
							),
					200,
					null
					);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
						
						
		/*auto shot*/ {
			final ShooterUpdater.ConstantIntervalShooter autoShot =
					new ShooterUpdater.ConstantIntervalShooter(
							interval,
							new ShooterFactories.NWwayShotVisitor(
									nWayShot,bulletSpeed,
									FPS,
									Math.PI/2.0,
									result.GetBounds().w,
									Math.PI*0.3
									),
							result.GetBounds().w/2.0,
							result.GetBounds().h
							);
			autoShot.BeginPlay();
			result.AddGameCharacterUpdater(autoShot);
			result.AddLifeCharacterListener(
					new LifeCharacter.Listener() {
						public void OnUnderMinLife(LifeCharacter _sender) {
							autoShot.EndPlay();
						}
						public void OnOverMaxLife(LifeCharacter _sender) {}
						public void OnIncreaseLife(LifeCharacter _sender, double _life) {}
						public void OnDecreaseLife(LifeCharacter _sender, double _life) {}
					}
					);
		}
						
		/*body design*/ {
			final ShooterDrawers.FighterA body  =
					new ShooterDrawers.FighterA(
							false,
							color,
							size*0.5,
							design,
							Math.PI
							);
			result.AddGameCharacterDrawer(body);
			
			final ShooterDrawers.ComponentBound hit =
					new ShooterDrawers.ComponentBound(
							false,new Color(0,0,255,255)
							);
			result.AddGameCharacterDrawer(hit);
			
			result.AddLifeCharacterListener( 
					new LifeCharacter.Listener() {
						public void OnUnderMinLife(LifeCharacter _sender) {
							body.EndPlay();
							hit.EndPlay();
						}
						public void OnOverMaxLife(LifeCharacter _sender) {}
						public void OnIncreaseLife(LifeCharacter _sender, double _life) {}
						public void OnDecreaseLife(LifeCharacter _sender, double _life) {}
					}
					);
		}
		
		/*destroy effect*/ {
			final ShooterDrawers.CircleExpandDestroyEffect circleDestroyEffect = 
					new ShooterDrawers.CircleExpandDestroyEffect(
							10,
							100,
							color,
							(int)FPS,
							FPS,
							0,0
							);
			result.AddGameCharacterDrawer(
					circleDestroyEffect
					);
			final ShooterDrawers.ParticleDestroyEffect particleDestroyEffect =
					new ShooterDrawers.ParticleDestroyEffect(
							10,
							color,
							result.GetBounds().w*0.2,
							result.GetBounds().w*0.9,
							0,0,
							result.GetBounds().w*0.2,
							100,200,
							FPS,
							(int)FPS
							);
			result.AddGameCharacterDrawer(particleDestroyEffect);
			
			result.AddLifeCharacterListener(
					new LifeCharacter.Listener() {
						public void OnUnderMinLife(LifeCharacter _sender) {
							particleDestroyEffect.SetSpeed(
									result.GetBounds().x+result.GetBounds().w/2.0,
									result.GetBounds().y+result.GetBounds().h/2.0,
									speed.x,speed.y,
									FPS
									);
									
							circleDestroyEffect.BeginPlay();
							particleDestroyEffect.BeginPlay();
							result.SetCollisionable(false);
						}
						public void OnOverMaxLife(LifeCharacter _sender) {}
						public void OnIncreaseLife(LifeCharacter _sender, double _life) {}
						public void OnDecreaseLife(LifeCharacter _sender, double _life) {}
					}
					);
		}
		
		
		
		try {
			container.AddChild(result);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
}
