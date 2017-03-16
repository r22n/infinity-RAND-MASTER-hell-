package shooter;
import Tests.*;
import java.awt.*;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;

import GameComponents.IScreenDrawPos;
import GameScenes.Scene;
import GameScenes.SceneContainer;
import ShooterScenes.FightScene;
import ShooterScenes.MenuScene;
import ShooterScenes.StartScene;

//!the Main class that includes entry point
/*!
 * \author n.ryouta
 */
public class Main extends Frame implements Runnable,IScreenDrawPos,GameComponents.IObserver<KeyListener> {
	private static final long serialVersionUID = 1L;
	private final Double Width = 300.0;
	private final Double Height = 400.0;
	private final Double FPS = 60.0;
	private final Double IntervalMillis = 1.0 / FPS * 1000.0;
	private final String Title = "+inf RAND-MASTER";
	private final Image image = 
			new BufferedImage(
					Width.intValue(),
					Height.intValue(),
					BufferedImage.TYPE_3BYTE_BGR
					);
	private final Graphics graphics = image.getGraphics();
	private final Thread thread = new Thread(this);
	private boolean running = true;
	private SceneContainer scene = 
			new SceneContainer();
	private double screenX=0,screenY=0;
	
	//!initializer
	/*!
	 * forces frame constant size, 300x400 size, 60fps, title
	 */
	public Main() {
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent _e) {
				try {
					running = false;
					thread.join();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.exit(0);
			}
		});
		setResizable(false);
		setSize(Width.intValue(),Height.intValue());
		setTitle(Title);
		InitializeComponents();

		thread.start();
		setVisible(true);
	}
	//!initialize all scenes
	/*!
	 * make start scene, menu scene, fight scene
	 */
	protected void InitializeComponents() {
		MenuScene menu = new ShooterScenes.MenuScene(
				Width,Height,
				FPS,
				this,
				scene
				);
		StartScene start = new ShooterScenes.StartScene(
				menu,
				scene,
				Width,Height,FPS
				);
		FightScene fight = new ShooterScenes.FightScene(
				scene,
				menu,
				FPS,
				Width,Height,
				this,
				this
				);
		menu.SetNextScene(fight);

		scene.ReserveScene(start);
	}
	//!execute this app
	/*!
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Main();
	}
	//!repaint this object
	/*!
	 * \param _g GDI
	 */
	public void update(Graphics _g) {
		paint(_g);
	}
	//!paint current scene
	/*!
	 * \param _g GDI
	 */
	public void paint(Graphics _g) {
		graphics.setColor(Color.black);
		graphics.fillRect(0, 0, Width.intValue(), Height.intValue());
		
		try {
			scene.Update();
			scene.Draw(graphics);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		_g.drawImage(image, (int)screenX, (int)screenY, this);
	}
	//!empty
	/*!
	 */
	protected void Update() {}
	@Override
	//!run executes 60fps game
	/*!
	 */
	public void run() {
		// TODO Auto-generated method stub
		while(running) {
			long begin = System.currentTimeMillis();
			Update();
			repaint();
			double work = System.currentTimeMillis()-begin;
			try {
				Thread.sleep(Math.max(0, (long)(IntervalMillis-work)));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	@Override
	//!actuate x position of screen
	/*!
	 * \param _x screen x position
	 */
	public void SetX(double _x) {
		// TODO Auto-generated method stub
		screenX = _x;
	}
	@Override
	//!actuate y position of screen
	/*!
	 * \param _y screen x position
	 */
	public void SetY(double _y) {
		// TODO Auto-generated method stub
		screenY = _y;
	}
	@Override
	//!get x position of screen
	/*!
	 * \returns x position of screen
	 */
	public double GetX() {
		// TODO Auto-generated method stub
		return screenX;
	}
	@Override
	//!get y position of screen
	/*!
	 * \returns y position of screen
	 */
	public double GetY() {
		// TODO Auto-generated method stub
		return screenY;
	}
	
	@Override
	//!add key listener by redirecting to addKeyListener
	/*!
	 * \param _listener the handler that receives some events
	 */
	public void AddListener(KeyListener _listener) {
		// TODO Auto-generated method stub
		if(!(_listener!=null)) {
			System.out.println("Main.AddListener _listener is null");
			return ;
		}
		addKeyListener(_listener);
	}
	@Override
	//!remove key listener by redirecting to removeKeyListener
	/*!
	 * \return value of true
	 */
	public boolean RemoveListener(KeyListener _listener) {
		// TODO Auto-generated method stub
		removeKeyListener(_listener);
		return true;
	}

}
