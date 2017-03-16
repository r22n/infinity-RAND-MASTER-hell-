package ShooterFactories;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.util.Map;

import GameComponents.*;
import ShooterComponents.*;
import ShooterDrawers.ParticleDestroyEffect;
import ShooterUpdater.MyFighterMove;


//!my fighter factory
/*!
 * \author n.ryouta
 */
public class TestMyFighterFactory implements Factory<Component>{
	private ComponentContainer container;
	private double width,height,FPS;
	private KeyBridge keyboard;
	
	//!initializer
	/*!
	 * \param _keyboard keyboard bridge
	 *\param _width width of screen
	 *\param _height height of screen
	 *\param _FPS frame rate
	 *\param _container the container will be stored result
	 *\exception IllegalArgumentException _container is null
	 *\exception IllegalArgumentException _width is smaller equal than 0
	 *\exception IllegalArgumentException _height is smaller equal than 0
	 *\exception IllegalArgumentException _keyboard is null
	 */
	public TestMyFighterFactory(
			KeyBridge _keyboard,
			double _width,double _height,
			double _FPS,ComponentContainer _container)throws IllegalArgumentException {
		if(!(_container!=null))throw new IllegalArgumentException("TestMyFighterFactory _container is null");
		if(!(_FPS > 0))throw new IllegalArgumentException("TestMyFighterFactory _FPS must be greater than 0");
		if(!(_width > 0))throw new IllegalArgumentException("TestMyFighterFactory _width must be greater than 0");
		if(!(_height > 0))throw new IllegalArgumentException("TestMyFighterFactory _height must be greater than 0");
		if(!(_keyboard!=null))throw new IllegalArgumentException("TestMyFighterFactory _keyboard is null");
		
		container = _container;
		width = _width;
		height = _height;
		FPS = _FPS;
		keyboard = _keyboard;
	}
	
	@Override
	//!create my Fighter
	/*!
	 * \returns my Fighter that can actuate keyboard
	 */
	public Component CreateObject() {
		// TODO Auto-generated method stub

		final Timer timer =
				new Timer();
		try {
			container.AddChild(timer);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		final Fighter result = new Fighter();
		
		/*life*/ {
			result.SetMaxLife(1);
			result.SetMinLife(0);
			result.IncreaseLife(1);
		}
			
		
		final Rect bounds = result.GetBounds();
		bounds.w = 3;
		bounds.h = 3;
		bounds.x = width / 2;
		bounds.y = height - bounds.h - 80;
		
		final ShooterDrawers.FighterA body = 
				new ShooterDrawers.FighterA(false, Color.white, 10, 3);
		result.AddGameCharacterDrawer(body);
		
		final ShooterDrawers.ComponentBound hit =
				new ShooterDrawers.ComponentBound(false, Color.red);
		result.AddGameCharacterDrawer(hit);
		
		/*jet*/ 
		final ShooterDrawers.ParticleJetEffect jet = 
				new ShooterDrawers.ParticleJetEffect(
						6,1,10,
						Math.PI/2.0,Math.PI*0.1,
						300,3,Color.red,
						100,FPS,
						3,10
						); 
		result.AddGameCharacterDrawer(jet);
		

		timer.AddTimerListener(
				new Timer.Listener() {
					public void OnTimeReach(Timer _sender) {
						jet.BeginPlay();
					}
					public long GetTimeInFrame() {
						// TODO Auto-generated method stub
						return (long)(3*FPS);
					}
				}
				);
		
		/*uav*/ {
			final ShooterDrawers.MyFighterUAV uav = 
					new ShooterDrawers.MyFighterUAV(
							keyboard,
							4,
							3,
							0,
							Math.PI*5.0,
							FPS,
							8,
							0,
							-9,
							KeyEvent.VK_Z,
							Color.white
							);
			result.AddGameCharacterDrawer(uav);
			result.AddLifeCharacterListener(
					new LifeCharacter.Listener() {
						public void OnUnderMinLife(LifeCharacter _sender) {
							uav.EndPlay();
						}
						public void OnOverMaxLife(LifeCharacter _sender) {}
						public void OnIncreaseLife(LifeCharacter _sender, double _life) {}
						public void OnDecreaseLife(LifeCharacter _sender, double _life) {}
					}
					);
		}
		/*circle1*/ {
			final ShooterDrawers.ComponentCircle circle = 
					new ShooterDrawers.ComponentCircle(
							new Color(255,255,255,100),
							bounds.w*8.0,
							0, 0
							);
			result.AddGameCharacterDrawer(circle);

			result.AddLifeCharacterListener(
					new LifeCharacter.Listener() {
						public void OnUnderMinLife(LifeCharacter _sender) {
							circle.EndPlay();
						}
						public void OnOverMaxLife(LifeCharacter _sender) {}
						public void OnIncreaseLife(LifeCharacter _sender, double _life) {}
						public void OnDecreaseLife(LifeCharacter _sender, double _life) {}
					}
					);
			timer.AddTimerListener(
					new Timer.Listener() {
						public void OnTimeReach(Timer _sender) {
							// TODO Auto-generated method stub
							circle.BeginPlay();
						}
						public long GetTimeInFrame() {
							return (long)(2.5*FPS);
						}
					}
					);
		}
		
		/*move*/ {
			double moveSpeed = 100;
			final ShooterUpdater.MyFighterMove move = 
					new ShooterUpdater.MyFighterMove(keyboard);
			move.RegisterKeyConfig(KeyEvent.VK_UP, new Vector2(0, -moveSpeed),FPS);
			move.RegisterKeyConfig(KeyEvent.VK_DOWN, new Vector2(0, moveSpeed),FPS);
			move.RegisterKeyConfig(KeyEvent.VK_LEFT, new Vector2(-moveSpeed,0 ),FPS);
			move.RegisterKeyConfig(KeyEvent.VK_RIGHT, new Vector2(moveSpeed,0),FPS);
			move.RegisterSpeedUp(KeyEvent.VK_Z, 0.6);
			result.AddGameCharacterUpdater(move);
			timer.AddTimerListener(
					new Timer.Listener() {
						public void OnTimeReach(Timer _sender) {
							// TODO Auto-generated method stub
							move.BeginPlay();
						}
						public long GetTimeInFrame() {
							// TODO Auto-generated method stub
							return (long)(3*FPS);
						}
					}
					);
			
			
			result.AddGameCharacterUpdater(new ShooterUpdater.RangeToBound(10, 40, width-20, height-50));
					
		}
		/*shot*/ {
			final ShooterFactories.NWwayShotVisitor nWayShot = 
					new ShooterFactories.NWwayShotVisitor(
							3,500,FPS,
							-Math.PI/2.0,20,
							-Math.PI*0.3
							);
			try {
				result.RegisterBullet(
						new ShooterFactories.PlaneBulletFactory(
								8,
								null,
								new ShooterFactories.StraightBulletMoveFactory(width, height,FPS),
								new ShooterFactories.PlaneCircleDestroyEffectFactory(
										1,200,Color.white,(int)(FPS/2),FPS,
										0,0
										)
								),
						100,
						null
						);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			final ShooterUpdater.MyFighterShotUpdater shotUpdater = 
					new ShooterUpdater.MyFighterShotUpdater(
							result,
							5,
							nWayShot,
							KeyEvent.VK_X,
							keyboard,
							3,-25
							);
					
			result.AddGameCharacterUpdater(shotUpdater);
			result.AddLifeCharacterListener(
					new LifeCharacter.Listener() {
						public void OnUnderMinLife(LifeCharacter _sender) {
							shotUpdater.EndPlay();
						}
						public void OnOverMaxLife(LifeCharacter _sender) {}
						public void OnIncreaseLife(LifeCharacter _sender, double _life) {}
						public void OnDecreaseLife(LifeCharacter _sender, double _life) {}
					}
					);
			timer.AddTimerListener(
					new Timer.Listener() {
						public void OnTimeReach(Timer _sender) {
							// TODO Auto-generated method stub
							shotUpdater.BeginPlay();
						}
						public long GetTimeInFrame() {
							// TODO Auto-generated method stub
							return (long)(3*FPS);
						}
					}
					);
			
			keyboard.AddKeyBridgeListener(
					new KeyBridge.Listener() {
						private boolean shot = false;
						private double lastDistAngle;
						public void EndKeyDown(KeyBridge _sender, Map<Integer, Boolean> _keyboard) {
							if(shot && !_sender.GetKeyDown(KeyEvent.VK_Z)) {
								nWayShot.SetDistAngle(lastDistAngle);
								shot = false;
							}
						}
						public void BeginKeyDown(KeyBridge _sender, Map<Integer, Boolean> _keyboard) {
							if(!shot && _sender.GetKeyDown(KeyEvent.VK_Z)) {
								lastDistAngle = nWayShot.GetDistAngle();
								nWayShot.SetDistAngle(0);
								shot = true;
							}
						}
					});
		}
		/*dead*/ {
			final ShooterDrawers.CircleExpandDestroyEffect circleDestroy =
					new ShooterDrawers.CircleExpandDestroyEffect(
							10,200,
							Color.white,
							(int)FPS,FPS,
							0,0
							);
			result.AddGameCharacterDrawer(circleDestroy);
			final ShooterDrawers.ParticleDestroyEffect particleDestroy =
					new ShooterDrawers.ParticleDestroyEffect(
							20,
							Color.white,
							10,20,
							0,0,10,
							100,200,
							FPS,
							(int)FPS
							);
			result.AddGameCharacterDrawer(particleDestroy);
			
			
			result.AddLifeCharacterListener(
					new LifeCharacter.Listener() {
						public void OnUnderMinLife(LifeCharacter _sender) {
							circleDestroy.BeginPlay();
							particleDestroy.SetSpeed(
									result.GetBounds().x+result.GetBounds().w/2.0,
									result.GetBounds().y+result.GetBounds().h/2.0,
									0,0,
									FPS
									);
							particleDestroy.BeginPlay();
							
							body.EndPlay();
							jet.EndPlay();
							hit.EndPlay();
							result.SetCollisionable(false);
							try {
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						public void OnOverMaxLife(LifeCharacter _sender) {}
						public void OnIncreaseLife(LifeCharacter _sender, double _life) {}
						public void OnDecreaseLife(LifeCharacter _sender, double _life) {}
					}
					);
			circleDestroy.AddEffectEndListener(new Listener<Object,Object>() {
				public void OnListen(Object _sender, Object _e) {
					try {
						result.SetValid(false);
						result.SetVisible(false);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
		}
		
		/*base*/ {
			final ShooterDrawers.MyFighterBase base = 
					new ShooterDrawers.MyFighterBase(
							bounds.w*3,
							50,
							FPS,
							Color.red,
							300
							);
			base.SetSpeed(bounds.x+bounds.w/2.0, bounds.y+bounds.h/2.0, 0, 0, FPS);
			result.AddGameCharacterDrawer(base);
			
			timer.AddTimerListener(
					new Timer.Listener() {
						public void OnTimeReach(Timer _sender) {
							base.BeginPlay();
						}
						public long GetTimeInFrame() {
							return (long)(2*FPS);
						}
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
