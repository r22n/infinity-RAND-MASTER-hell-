package ShooterScenes;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import GameComponents.Component;
import GameComponents.IObserver;
import GameComponents.Label;
import GameComponents.Line;
import GameComponents.Rect;
import GameComponents.Timer;
import GameComponents.Vector2;
import GameScenes.Scene;
import GameScenes.SceneContainer;
import ShooterComponents.GameCharacter;
import ShooterDrawers.ComponentBound;

//!menu scene that display title
/*!
 * \author n.ryouta
 */
public class MenuScene extends Scene {
	private double width,height,FPS;
	private IObserver<KeyListener> keyObserver; 
	private Scene fight;
	private SceneContainer sceneContainer;
	
	//!initialize
	/*!
	 * \param _width width of screen
	 * \param _height height of screen
	 * \param _FPS frame rate
	 * \param _keyObserver keyboard observer 
	 * \param _sceneContainer SceneContainer that manage this
	 * \exception IllegalArgumentException _width is smaller equal than 0
	 * \exception IllegalArgumentException _height is smaller equal than 0
	 * \exception IllegalArgumentException _FPS is smaller equal than 0
	 * \exception IllegalArgumentException _keyObserver is null
	 * \exception IllegalArgumentException _sceneContainer is null
	 */
	public MenuScene(
			double _width,
			double _height,
			double _FPS,
			IObserver<KeyListener> _keyObserver,
			SceneContainer _sceneContainer
			)throws IllegalArgumentException {
		if(!(_width>0))throw new IllegalArgumentException("MenuScene _width must be greater than 0");
		if(!(_height>0))throw new IllegalArgumentException("MenuScene _height must be greater than 0");
		if(!(_FPS>0))throw new IllegalArgumentException("MenuScene _FPS must be greater than 0");
		if(!(_keyObserver!=null))throw new IllegalArgumentException("MenuScene _keyObserver is null");
		if(!(_sceneContainer!=null))throw new IllegalArgumentException("MenuScene _sceneContainer is null");
		
		width = _width;
		height = _height;
		FPS = _FPS;
		keyObserver = _keyObserver;
		sceneContainer = _sceneContainer;
	}
	//!set next scene
	/*!
	 * \param _next the next scene was jumped into when the keyboard pressed
	 */
	public void SetNextScene(Scene _next) {
		fight = _next;
	}
	//!initialize all components
	/*!
	 * \param _sender no use
	 */
	public void PlayThisScene(SceneContainer _sender) {
		curY = height/3.0;
		curX = width/2.0-100; 
		deltaY = 30;
		deltaX = 0;
		InitializeComponents();
	}
	//!initialize all components
	/*!
	 */
	protected void InitializeComponents() {
		ResetComponentContainer();
		try {
			CreateTitleLabel("infinity");
			CreateTitleLabel("RANDom-MASTER");
			CreateTitleLabel("-hell-");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		double size = 10;
		double x = 10;
		double y = 40;
		double s = 1;
		double innerAccInSec = 5e-1;
		double outerAccInSec = 3e-1;
		
				
		CreateBox(new Rect(x,y,size,size));
		CreateBox(new Rect(x+size,y+size,size,size));
		CreateLine(
				new Vector2(x+size/2,y+size),
				new Vector2(x+size/2,height*s),
				innerAccInSec
				);
		CreateLine(
				new Vector2(x+size/2+size,y+size+size),
				new Vector2(x+size/2+size,height*s+size),
				outerAccInSec
				);
		CreateLine(
				new Vector2(x+size,y+size/2),
				new Vector2(width*s,y+size/2),
				innerAccInSec
				);
		CreateLine(
				new Vector2(x+size+size,y+size/2+size),
				new Vector2(width*s+size,y+size/2+size),
				outerAccInSec
				);

		CreateBox(new Rect(width-(x+size*2),height-(y+size*2),size,size));
		CreateBox(new Rect(width-(x+size),height-(y+size),size,size));
		CreateLine(
				new Vector2(width-(x+size/2),height-(y+size)),
				new Vector2(width-(x+size/2),height-(height*s)),
				innerAccInSec
				);
		CreateLine(
				new Vector2(width-(x+size/2+size),height-(y+size+size)),
				new Vector2(width-(x+size/2+size),height-(height*s+size)),
				outerAccInSec
				);
		CreateLine(
				new Vector2(width-(x+size),height-(y+size/2)),
				new Vector2(width-(width*s),height-(y+size/2)),
				innerAccInSec
				);
		CreateLine(
				new Vector2(width-(x+size+size),height-(y+size/2+size)),
				new Vector2(width-(width*s+size),height-(y+size/2+size)),
				outerAccInSec
				);
		
		
		
		CreatePressAnyKey();
		CreateNextSceneKey();
	}
	
	private double curY;
	private double curX;
	private double deltaY;
	private double deltaX;
	//!create title label
	/*!
	 * \returns title
	 */
	protected Label CreateTitleLabel(String _text) throws Exception {
		if(!(_text!=null))throw new IllegalArgumentException("MenuScene.CreateTitleLabel _text is null");
		Label result = new Label();
		result.SetColor(Color.white);
		result.SetFont("arial",Font.BOLD,30);
		result.SetText(_text);
		result.GetBounds().x = curX;
		result.GetBounds().y = curY;
		
		curY += deltaY;
		curX += deltaX;
		GetComponentContainer().AddChild(result);
		return result;
	}
	//!create box that was used in the design
	/*!
	 * \param _r rectangle that is design
	 */
	protected void CreateBox(Rect _r)throws IllegalArgumentException {
		if(!(_r!=null))throw new IllegalArgumentException("MenuScene.CreateBox _r is null");
		GameCharacter box = new GameCharacter();
		box.AddGameCharacterDrawer(
				new ShooterDrawers.ComponentBound(false, Color.white)
				);
		box.GetBounds().Copy(_r);
		
		try {
			GetComponentContainer().AddChild(box);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//!create moving line animation
	/*!
	 * \param _x0 first point of segment
	 * \param _x1 second point of segment
	 * \param _accPerSec acceleration of line animation
	 * \exception IllegalArgumentException _x0 is null
	 * \exception IllegalArgumentException _x1 is null
	 */
	protected void CreateLine(Vector2 _x0,Vector2 _x1,double _accPerSec)throws IllegalArgumentException {
		if(!(_x0!=null))throw new IllegalArgumentException("MenuScene.CreateLine _x0 is null");
		if(!(_x1!=null))throw new IllegalArgumentException("MenuScene.CreateLine _x1 is null");
		Line l = new Line();
		l.SetColor(Color.white);
		l.SetX0(_x0);
		l.SetX1(_x0);
		
		double dx = _x1.x-_x0.x;
		double dy = _x1.y-_x0.y;
		
		try {
			l.AddChild(
					new Component() {
						private double s = 0;
						private double ds = 0;
						private double dds = _accPerSec;
						public void BeginUpdate(Component _sender) {
							ds+=dds/FPS/FPS;s+=ds;
							s = Math.min(1, s);
							
							Vector2 x1 = l.GetX1();
							x1.x = dx*s + l.GetX0().x;
							x1.y = dy*s + l.GetX0().y;
							
							if(s >= 1) {
								try {
									SetValid(false);
								} catch (Exception e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
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
			GetComponentContainer().AddChild(l);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
		
	//!create press any key label
	/*!
	 */
	protected void CreatePressAnyKey() {
		final Label text = new Label();
		text.SetColor(Color.white);
		text.SetText("press any key");
		text.SetFont("arial", Font.PLAIN, 12);
		text.GetBounds().x = width/2.0-40;
		text.GetBounds().y = height/4.0*3.0;

		try {
			text.SetVisible(false);
			GetComponentContainer().AddChild(text);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Timer timer = new Timer();
		timer.AddTimerListener(
				new Timer.Listener() {
					public void OnTimeReach(Timer _sender) {
						try {
							text.SetVisible(true);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					public long GetTimeInFrame() {
						return (long)(2.0*FPS);
					}
				}
				);
		try {
			GetComponentContainer().AddChild(timer);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//!create next scene key
	/*!
	 */
	protected void CreateNextSceneKey() {
		Timer timer = new Timer();
		timer.AddTimerListener(
				new Timer.Listener() {
					public void OnTimeReach(Timer _sender) {
						keyObserver.AddListener(
								new KeyListener() {

									@Override
									public void keyPressed(KeyEvent e) {
										// TODO Auto-generated method stub
										if(fight!=null) {
											keyObserver.RemoveListener(this);
											sceneContainer.ReserveScene(fight);
										}
									}

									@Override
									public void keyReleased(KeyEvent e) {
										// TODO Auto-generated method stub
										
									}

									@Override
									public void keyTyped(KeyEvent e) {
										// TODO Auto-generated method stub
										
									}
								}
								);
					}
					public long GetTimeInFrame() {
						return (long)(3*FPS);
					}
				}
				);
		
		try {
			GetComponentContainer().AddChild(timer);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
