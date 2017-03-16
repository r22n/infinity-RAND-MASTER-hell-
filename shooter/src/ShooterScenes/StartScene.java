package ShooterScenes;

import java.awt.Color;
import java.awt.Font;

import GameComponents.Component;
import GameComponents.Label;
import GameComponents.Timer;
import GameScenes.Scene;
import GameScenes.SceneContainer;
import ShooterComponents.GameCharacter;
import ShooterComponents.GameCharacterUpdater;

//!start scene that contains author name
/*!
 * \author n.ryouta
 */
public class StartScene extends Scene{
	private Scene menu;
	private SceneContainer sceneContainer;
	private double width,height;
	private double FPS;
	
	//!initializer
	/*!
	 * \param _menu the menu scene that is next
	 * \param _sceneContainer SceneContainer manages this and next
	 * \param _width width of screen
	 * \param _height height of screen
	 * \param _FPS frame rate
	 * \exception IllegalArgumentException _menu is null
	 * \exception IllegalArgumentException _sceneContainer is null
	 * \exception IllegalArgumentException _width is smaller equal than 0
	 * \exception IllegalArgumentException _height is smaller equal than 0
	 * \exception IllegalArgumentException _FPS is smaller equal than 0
	 */
	public StartScene(
			Scene _menu,
			SceneContainer _sceneContainer,
			double _width,
			double _height,
			double _FPS
			)throws IllegalArgumentException {
		if(!(_menu!=null))throw new IllegalArgumentException("StartScene _menu is null");
		if(!(_sceneContainer!=null))throw new IllegalArgumentException("StartScene _sceneContainer is null");
		if(!(_width>0))throw new IllegalArgumentException("StartScene _width must be greater than 0");
		if(!(_height>0))throw new IllegalArgumentException("StartScene _height must be greater than 0");
		if(!(_FPS>0))throw new IllegalArgumentException("StartScene _FPS must be greater than 0");
		menu = _menu;
		sceneContainer = _sceneContainer;
		width = _width;
		height = _height;
		FPS = _FPS;
		
		
	}
	
	//!initialize all components
	/*!
	 * \param _sender no use
	 */
	public void PlayThisScene(SceneContainer _sender) {
		InitializeComponents();
	}
	//!initialize all components
	/*!
	 */
	protected void InitializeComponents() {
		ResetComponentContainer();
		
		final ShooterFactories.RandomBoundFactory back =
				new ShooterFactories.RandomBoundFactory(
						new Color(255,255,255,10),
						width,height,
						10,
						80,
						GetComponentContainer()
						);
		
		for(int i = 0 ; i < 300;i++) {
			final GameCharacter bound = (GameCharacter)(back.CreateObject());
			bound.AddGameCharacterUpdater(
					new GameCharacterUpdater() {
						private double g = 50+Math.random()*300;
						private double dy = 0;
						public void BeginUpdate(GameCharacter _sender) {
							dy += g / FPS / FPS;
							bound.GetBounds().y += dy;
						}
						public void EndUpdate(GameCharacter _sender) {}
					}
					);
			
		}
		
		final Label author = new Label();
		author.SetColor(new Color(255,255,255,0));
		author.SetFont("arial", Font.PLAIN, 20);
		author.SetText("n.ryouta 2017");
		author.GetBounds().x = width/2.0-60;
		author.GetBounds().y = height/2.0;
		try {
			author.AddChild(
					new Component() {
						private int opacity = 0;
						public void BeginUpdate(Component _panret) {
							author.SetColor(new Color(255,255,255,opacity));
							opacity++;
							if(opacity==255) {
								try {
									SetValid(false);
								} catch (Exception e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						}
					});
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {
			GetComponentContainer().AddChild(author);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		final Timer timer = new Timer();
		timer.AddTimerListener(
				new Timer.Listener() {
					
					@Override
					public void OnTimeReach(Timer _sender) {
						// TODO Auto-generated method stub
						sceneContainer.ReserveScene(menu);
					}
					
					@Override
					public long GetTimeInFrame() {
						// TODO Auto-generated method stub
						return (long)(FPS*9.0);
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
