package ShooterScenes;

import ShooterComponents.*;
import ShooterUpdater.BulletMove;
import ShooterUpdater.OutOfBoundCheck;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyListener;
import java.util.Map;

import GameComponents.*;
import GameScenes.Scene;
import GameScenes.SceneContainer;

//!fight scene 
/*!
 * \author n.ryouta
 */
public class FightScene extends Scene {
	
	private SceneContainer sceneContainer;
	private Scene mainScene;
	private double FPS;
	private double width;
	private double height;
	private IScreenDrawPos spos;
	private IObserver<KeyListener> keyboardObserver;
	
	//!get SceneContainer object
	/*!
	 * \returns the SceneContianer object that manage this
	 */
	public SceneContainer GetSceneContainer(){return sceneContainer;}
	//!get the MainScene
	/*!
	 * \returns main scene
	 */
	public Scene GetMainScene(){return mainScene;}
	
	//!get frame rate
	/*!
	 * \returns frame rate
	 */
	public double GetFPS(){return FPS;}
	
	//!get the width of screen
	/*!
	 * \returns width of screen
	 * 
	 */
	public double GetScreenWidth(){return width;}
	
	//!get the height of screen
	/*!
	 * \returns height of screen
	 */
	public double GetScreenHeight(){return height;}
	
	//!get screen position bridge
	/*!
	 * \returns screen position
	 */
	public IScreenDrawPos GetScreenPos(){return spos;}
	
	//!get keyboard observer that notify the event about keyboard wth java.awt
	/*!
	 * \returns keyboard observer
	 */
	public IObserver<KeyListener> GetKeyboardObserver(){return keyboardObserver;}
	
	//!initializer
	/*!
	 * \param _sceneContainer SceneContainer object that manage this
	 * \param _mainScene the main scene
	 * \param _FPS frame rate
	 * \param _width width of screen
	 * \param _height height of screen
	 * \param _spos screen position bridge
	 * \param _keyObserver keyboard observer
	 * \exception IllegalArgumentException _sceneContainer is null
	 * \exception IllegalArgumentException _mainScene is null
	 * \exception IllegalArgumentException _FPS is smaller equal than 0
	 * \exception IllegalArgumentException _width is smaller equal than 0
	 * \exception IllegalArgumentException _height is smaller equal than 0
	 * \exception IllegalArgumentException _spos is null
	 * \exception IllegalArgumentException _keyObserver is null
	 */
	public FightScene(
			SceneContainer _sceneContainer,
			Scene _mainScene,
			double _FPS,
			double _width,
			double _height,
			IScreenDrawPos _spos,
			IObserver<KeyListener> _keyObserver
			)throws IllegalArgumentException {
		if(!(_sceneContainer!=null))throw new IllegalArgumentException("FightScene _sceneContainer is null");
		if(!(_mainScene!=null))throw new IllegalArgumentException("FightScene _mainScene is null");
		if(!(_FPS > 0))throw new IllegalArgumentException("FightScene _FPS must be greater than 0");
		if(!(_width>0))throw new IllegalArgumentException("FightScene _width must be greater than 0");
		if(!(_height>0))throw new IllegalArgumentException("FightScene _height must be greater than 0");
		if(!(_spos!=null))throw new IllegalArgumentException("FightScene _spos is null");
		if(!(_keyObserver!=null))throw new IllegalArgumentException("FightScene _keyObserver is null");
		sceneContainer = _sceneContainer;
		mainScene = _mainScene;
		FPS = _FPS;
		width = _width;
		height = _height;
		spos = _spos;
		keyboardObserver  = _keyObserver;
		
	}
	
	//!initialize components
	/*!
	 * \param _sender no use
	 */
	public void PlayThisScene(SceneContainer _sender) {
		InitializeComponents();
	}
	
	
	private KeyBridge keyboard;
	
	//!get keyboard bridge
	/*!
	 * \returns keyboard object
	 */
	public KeyBridge GetKeyboard(){return keyboard;}
	//!create keyboard bridge
	/*!
	 * \returns keyboard bridge object
	 */
	protected KeyBridge CreateKeyBridge() {
		 AwtInputBridges.AwtKeyBridge result = new AwtInputBridges.AwtKeyBridge();
		try {
			GetComponentContainer().AddChild(result);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		keyboardObserver.AddListener(result);
		return result;
	}
	
	private Fighter me;
	//!get my Fighter
	/*!
	 * \returns my Fighter
	 */
	public Fighter GetMyFighter(){return me;}
	
	//!create my Fighter
	/*!
	 * \returns my Fighter
	 */
	protected Fighter CreateMyFighter(){
		final Fighter result =
				(Fighter)(
						new ShooterFactories.TestMyFighterFactory(
								keyboard,
								width,height,FPS,
								GetComponentContainer()
								)
						.CreateObject()
						);
		result.SetCollisionable(true);
		return result;
	}
	
	//!create background animation
	/*!
	 * 
	 */
	protected void CreateCloud() {
		final Integer count =  30;
		final ComponentContainer contianer = GetComponentContainer();
		final Rect bound = new Rect(0,0,width,height);
		final double minSize = 10;
		final double maxSize = 400;
		final double minSpeed = 5;
		final double maxSpeed = 50;
		
		for(int i =  0 ; i < count;i++) {
			GameCharacter cloud = 
					(GameCharacter)(
							new ShooterFactories.RandomBoundFactory(
									new Color(255,255,255,120),
									width,height,
									minSize,
									maxSize,
									contianer
									)
							.CreateObject()
							);
			cloud.SetPriority(-1);
			ShooterUpdater.BulletMove move = 
					new ShooterUpdater.BulletMove(
							bound,
							0,
							(maxSpeed-minSpeed)*Math.random()+minSpeed,
							FPS
							);
			cloud.AddGameCharacterUpdater(move);
			
			move.AddBulletMoveListener(
					new ShooterUpdater.BulletMove.Listener() {
						public void OnOutOfBound(BulletMove _sender, GameCharacter _e) {
							Rect r = _e.GetBounds();
							r.w = (maxSize-minSize)*Math.random()+minSize;
							r.h = (maxSize-minSize)*Math.random()+minSize;
							r.x = Math.random() * (width+r.w) - r.w/2.0;
							r.y = -r.h;
						}
					}
					);
			
		}
	}
	
	//!create get ready label
	/*!
	 */
	protected void CreateGetReadyLabel() {
		final Label ready = 
				new Label();
		ready.SetFont("arial", Font.BOLD, 20);
		ready.SetText("get ready?");
		ready.SetPriority(2);
		ready.SetColor(Color.white);
		Rect r = ready.GetBounds();
		r.x = 100;
		r.y = 200;
		
		Timer timer = new Timer();
		
		try {
			GetComponentContainer().AddChild(ready);
			GetComponentContainer().AddChild(timer);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		timer.AddTimerListener(
				new Timer.Listener() {

					@Override
					public void OnTimeReach(Timer _sender) {
						// TODO Auto-generated method stub
						try {
							ready.SetVisible(false);
							ready.SetValid(false);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}

					@Override
					public long GetTimeInFrame() {
						// TODO Auto-generated method stub
						return (long)(3*FPS);
					}
				}
				);
	}
	
	//!create go! label
	/*!
	 *
	 */
	protected void CreateGoLabel() {
		final Label go =
				new Label();
		go.SetFont("arial", Font.BOLD, 20);
		go.SetText("go!");
		go.SetPriority(2);
		go.SetColor(Color.white);
		Rect r = go.GetBounds();
		r.x = 135;
		r.y = 200;
		try {
			go.SetVisible(false);
			go.SetValid(false);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		Timer timer =
				new Timer();
		
		
		try {
			GetComponentContainer().AddChild(go);
			GetComponentContainer().AddChild(timer);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		timer.AddTimerListener(
				new Timer.Listener() {
					
					@Override
					public void OnTimeReach(Timer _sender) {
						// TODO Auto-generated method stub
						try {
							go.SetVisible(true);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					
					@Override
					public long GetTimeInFrame() {
						// TODO Auto-generated method stub
						return (long)(3*FPS);
					}
				}
				);
		timer.AddTimerListener(
				new Timer.Listener() {
					
					@Override
					public void OnTimeReach(Timer _sender) {
						// TODO Auto-generated method stub
						try {
							go.SetVisible(false);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					
					@Override
					public long GetTimeInFrame() {
						// TODO Auto-generated method stub
						return (long)(5*FPS);
					}
				}
				);
	}
	
	private ShooterCollisionContainer collisionContainer;
	//!get collision container for this scene
	/*!
	 * \returns collision container that manages all characters
	 */
	public ShooterCollisionContainer GetCollisionContainer(){return collisionContainer;}
	
	//!create collision container
	/*!
	 * \returns collision container that manages all characters
	 */
	protected ShooterCollisionContainer CreateCollisionContainer() {
		ShooterCollisionContainer result = new ShooterCollisionContainer();
		try {
			GetComponentContainer().AddChild(result);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	private ScoreBoard score = new ScoreBoard();
	//!get score board that display current points
	/*!
	 * \return score board
	 */
	public ScoreBoard GetScoreBoard(){return score;}
	
	//!create score board 
	/*!
	 * \returns score board
	 */
	protected ScoreBoard CreateScoreBoard() {
		ScoreBoard result = new ScoreBoard();
		result.SetFont("arial", Font.PLAIN, 12);
		result.SetColor(Color.white);
		result.SetPriority(4);
		result.SetScoreFormat("%d points");
		
		Rect r = result.GetBounds();
		r.x = 10;
		r.y = 40;
		
		try {
			GetComponentContainer().AddChild(result);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}
	
	private ScreenShaker shaker;
	//!get screen shaker
	/*!
	 * \returns screen shaker
	 */
	public ScreenShaker GetScreenShaker(){return shaker;}
	//!create screen shaker
	/*!
	 * \returns screen shaker
	 */
	protected ScreenShaker CreateScreenShaker() {
		final ScreenShaker result =
				new GameComponents.ScreenShaker(
						7,0.4,0.2,
						FPS,
						4,
						spos
						);
		try {
			GetComponentContainer().AddChild(result);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
		
	}
	
	private ProgressBar comboProgress;
	
	//!get progress bar that display combo
	/*!
	 * \returns progress bar
	 */
	public ProgressBar GetComboProgress(){return comboProgress;}
	
	//!create progress bar that display combo
	/*!
	 * \returns progress bar
	 */
	protected ProgressBar CreateComboProgress() {
		double comboGaugeDecreasePerFrame = 0.5e-2;
		
		
		final ProgressBar result =
				new ProgressBar();
		Rect r = result.GetBounds();
		r.x = 137;
		r.y = 60;
		r.h = 10;
		r.w = 120;
		result.SetPriority(4);
		result.SetColor(Color.white);
		result.SetFont("arial", Font.PLAIN, 12);
		result.SetScoreFormat("x %d");
		result.IncreaseScore(1);
		result.SetMinValue(0);
		result.SetMaxValue(1);
		result.SetRelativePos(-r.w*1.05, -r.h);
		result.SetGaugeHeightInPercentage(0.5);
		try {
			result.AddChild(
					new Component() {
						public void BeginUpdate(Component _parent) {
							result.SetValue(
									Math.max(
											result.GetValue()-comboGaugeDecreasePerFrame,
											result.GetMinValue()
											)
									);
							if(result.GetScore() >= 2) {
								if(result.GetValue()<= result.GetMinValue()) {
									result.DecreaseScore(1);
									result.SetValue(result.GetMaxValue());
								}
							}
						}
					}
					);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		try {
			GetComponentContainer().AddChild(result);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	private ScoreBoard levelBoard;
	
	//!get score board that display current level
	/*!
	 * \returns level board
	 */
	public ScoreBoard GetLevelBoard(){return levelBoard;}
	
	//!get score board that display current level
	/*!
	 * \returns level board
	 */
	protected ScoreBoard CreateLevelBoard() {
		final ScoreBoard result = 
				new ScoreBoard();
		result.SetColor(Color.white);
		result.SetScoreFormat("lv:%d");
		result.IncreaseScore(1);
		result.SetFont("arial", Font.PLAIN, 12);
		Rect r = result.GetBounds();
		r.x = 10;
		r.y = 80;
		
		
		try {
			GetComponentContainer().AddChild(result);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	private Label gameOverLabel;
	
	//!get game over label
	/*!
	 * \returns game over label
	 */
	public Label GetGameOverLabel(){return gameOverLabel;}
	
	//!create game over label
	/*!
	 * \returns game over label
	 */
	protected Label CreateGameOverLabel() {
		final Label result = new Label();
		result.SetColor(Color.white);
		result.SetText("game over");
		result.SetFont("arial", Font.BOLD, 20);
		Rect r = result.GetBounds();
		r.x = width/2.0-40;
		r.y = height/2.0;
		try {
			result.SetVisible(false);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		me.AddLifeCharacterListener(
				new LifeCharacter.Listener() {
					public void OnUnderMinLife(LifeCharacter _sender) {
						Timer t = new Timer();
						t.AddTimerListener(
								new Timer.Listener() {
									public void OnTimeReach(Timer _sender) {
										try {
											result.SetVisible(true);
										} catch (Exception e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
										
										Timer u = new Timer();
										u.AddTimerListener(
												new Timer.Listener() {
													public void OnTimeReach(Timer _sender) {
														sceneContainer.ReserveScene(mainScene);
													}
													public long GetTimeInFrame() {
														return (long)(FPS*5);
													}
												}
												);
										try {
											GetComponentContainer().AddChild(u);
										} catch (Exception e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
									}
									public long GetTimeInFrame() {
										return (long)(FPS*1.5);
									}
								}
								);
						try {
							GetComponentContainer().AddChild(t);
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
		
		try {
			GetComponentContainer().AddChild(result);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	
	//!make auto enemy factory with Timer
	/*!
	 * 
	 */
	protected void TEST() {	
		/*		
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
			double _percentageInSec,
			double _FPS,
			double _width,double _height,
			Rect _targetBound
		*/
		int designMax = 7;
		int designMin = 3;
		double speedMin = 50;
		double speedMax = 100;
		double sizeMin = 20;
		double sizeMax = 50;
		double shotIntervalInSecMin = 1;
		double shotIntervalInSecMax = 2;
		int nWayShotMin = 1;
		int nWayShotMax = 7;
		double bulletSizeMin = 8;
		double bulletSizeMax = 20;
		double bulletSpeedMin = 50;
		double bulletSpeedMax = 120;
		double lifeMin = 500;
		double lifeMax = 8000;
		
		//config
		int design = (int)((designMax-designMin)*Math.random()+designMin);
		double speed = (speedMax-speedMin)*Math.random()-speedMin;
		Color color = Color.white;
		double size = (sizeMax-sizeMin)*Math.random()+sizeMin;
		long shotInterVal = (long)(FPS*( (shotIntervalInSecMax-shotIntervalInSecMin)*Math.random()+shotIntervalInSecMin));
		int nWayShot = (int)((nWayShotMax-nWayShotMin)*Math.random()+nWayShotMin);
		double bulletSize = (bulletSizeMax-bulletSizeMin)*Math.random()+bulletSizeMin;
		double bulletSpeed = (bulletSpeedMax-bulletSpeedMin)*Math.random()+bulletSpeedMin;
		double life = (lifeMax-lifeMin)*Math.random()+lifeMin;
		double percentage = 30e-2;
		double moveTargetS = 0.3;
		long point = 3;
		Rect target = new Rect(
				width/2.0-width*moveTargetS/2.0,
				0,
				width*moveTargetS,
				height*moveTargetS
				);
		//config
		
		
		AutoEnemyFactory factory = 
				new AutoEnemyFactory(
						new ShooterFactories.AutoFighterFactory(
								design,
								new Vector2(speed,0),
								color,
								size,
								FPS,
								GetComponentContainer(),
								width,height,
								shotInterVal,
								nWayShot,
								bulletSize,
								bulletSpeed,
								life
								),
						percentage,
						FPS,
						width,height,
						target
						);
		factory.AddAutoEnemyFactoryListener(
				new AutoEnemyFactory.Listener() {
					public void OnCreateEnemy(AutoEnemyFactory _sender, Fighter _ee) {
						collisionContainer.AddEnemyFighter(_ee);
						_ee.AddLifeCharacterListener(
								new LifeCharacter.Listener() {
									public void OnUnderMinLife(LifeCharacter _sender) {
										score.IncreaseScore(point*comboProgress.GetScore());
										comboProgress.SetValue(comboProgress.GetMaxValue());
										comboProgress.IncreaseScore(1);
										
										Timer t = new Timer();
										t.AddTimerListener(
												new Timer.Listener() {
													public void OnTimeReach(Timer _sender) {
														collisionContainer.RemoveMyFighter(_ee);
														try {
															GetComponentContainer().RemoveComponent(_ee);
															GetComponentContainer().RemoveComponent(t);
														} catch (Exception e) {
															// TODO Auto-generated catch block
															e.printStackTrace();
														}
													}
													public long GetTimeInFrame() {
														return (long)(120*FPS);
													}
												}
												);
										try {
											GetComponentContainer().AddChild(t);
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
						double bufferZone = 200;
						ShooterUpdater.OutOfBoundCheck out =
								new ShooterUpdater.OutOfBoundCheck(-bufferZone,-bufferZone,width+bufferZone*2, height+bufferZone*2);
						_ee.AddGameCharacterUpdater(out);
						out.AddOutOfBoundCheck(
								new OutOfBoundCheck.Listener() {
									private int count = 0;
									private boolean out = false;
									public void OnOutOfBound(OutOfBoundCheck _sender, GameCharacter _e) {
										// TODO Auto-generated method stub
										if(!out) {
											out = true;
											count++;
											
											if(count>=2) {
												try {
													GetComponentContainer().RemoveComponent(_ee);
													collisionContainer.RemoveEnemyFighter(_ee);
												} catch (Exception e) {
													// TODO Auto-generated catch block
													e.printStackTrace();
												}
											}
										}
									}
									@Override
									public void OnInnerBound(OutOfBoundCheck _sender, GameCharacter _e) {
										// TODO Auto-generated method stub
										if(out) {
											out = false;
										}
									}
								}
								);
					}
				}
				);
		try {
			GetComponentContainer().AddChild(factory);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//!level up process with Timer
	/*!
	 */
	protected void LevelUpProc() {
		double lvUpInSec = 50;
		
		final Label lv = new Label();
		lv.SetColor(Color.white);
		lv.SetText("level up...");
		lv.SetFont("arial", Font.PLAIN, 20);
		Rect r = lv.GetBounds();
		r.x = width/2.0 - 40;
		r.y = height/2.0;
		try {
			lv.SetVisible(false);
			GetComponentContainer().AddChild(lv);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		final Timer timer = new Timer();
		timer.AddTimerListener(
				new Timer.Listener() {
					public void OnTimeReach(Timer _sender) {
						levelBoard.IncreaseScore(1);
						TEST();
						
						try {
							lv.SetVisible(true);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						Timer t = new Timer();
						t.AddTimerListener(new Timer.Listener() {
							public void OnTimeReach(Timer _sender) {
								try {
									lv.SetVisible(false);
									GetComponentContainer().RemoveComponent(t);
								} catch (Exception e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
							public long GetTimeInFrame() {
								// TODO Auto-generated method stub
								return (long)(FPS*1.5);
							}
						});
						try {
							GetComponentContainer().AddChild(t);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						
						timer.ResetTimer();
					}
					@Override
					public long GetTimeInFrame() {
						// TODO Auto-generated method stub
						return (long)(FPS*lvUpInSec);
					}
				}
				);
		
		try {
			GetComponentContainer().AddChild(timer);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			GetComponentContainer().AddChild(
					new Component() {
						private boolean dead = false;
						public void BeginUpdate(Component _parent) {
							if(!me.Collisionable() && !dead) {
								dead = true;
								try {
									GetComponentContainer().RemoveComponent(lv);
									GetComponentContainer().RemoveComponent(timer);
								} catch (Exception e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						}
					}
					);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//!initialize all components
	/*!
	 * initialize all components randomly
	 */
	protected void InitializeComponents() {
		ResetComponentContainer();
		
		keyboard = CreateKeyBridge();
		me = CreateMyFighter();
		CreateCloud();
		CreateGetReadyLabel();
		CreateGoLabel();
		collisionContainer = CreateCollisionContainer();
		collisionContainer.AddMyFighter(me);
		score = CreateScoreBoard();
		shaker = CreateScreenShaker();
		comboProgress = CreateComboProgress();
		levelBoard = CreateLevelBoard();
		gameOverLabel = CreateGameOverLabel();
		
		TEST();
		LevelUpProc();
		
		GetComponentContainer().ApplyPriority();
	}
}
