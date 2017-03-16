package ShooterUpdater;

import ShooterComponents.Fighter;
import ShooterComponents.GameCharacter;
import ShooterComponents.GameCharacterUpdater;
import ShooterDrawers.IEffect;

import java.util.Map;

import GameComponents.*;

//!makes my Fighter shot with keyboard input
/*!
 * \author n.ryouta
 */
public class MyFighterShotUpdater implements GameCharacterUpdater, KeyBridge.Listener, IEffect{
	private Fighter me;
	private int time;
	private int interval;
	private Fighter.ShotVisitor shot;
	private int shotKey;
	private KeyBridge keyboard;
	private double rx,ry;
	private boolean running = false;
	
	//!initializer
	/*!
	 * \param _me my Fighter
	 * \param _interval shot Bullet interval
	 * \param _shot shot visitor
	 * \param _shotKey virtual-keycode that makes my Fighter shot
	 * \param _rx relative x position of Bullet nozzle
	 * \param _ry relative y position of Bullet nozzle
	 * \exception IllegalArgumentException _me is null
	 * \exception IllegalArgumentException _interval is smaller equal than 0
	 * \exception IllegalArgumentException _shot is null
	 * \exception IllegalArgumentException _keyboard is null
	 */
	public MyFighterShotUpdater(
			Fighter _me,
			int _interval,
			Fighter.ShotVisitor _shot,
			int _shotKey,
			KeyBridge _keyboard,
			double _rx,double _ry
			) throws IllegalArgumentException {
		if(!(_me!=null))throw new IllegalArgumentException("MyFighterShotUpdater _me is null");
		if(!(_interval > 0))throw new IllegalArgumentException("MyFighterShotUpdater _interbal must be greater than 0");
		if(!(_shot!=null))throw new IllegalArgumentException("MyFIghterShotUpdater _shot is null");
		if(!(_keyboard!=null))throw new IllegalArgumentException("MyFighterShotUpdater _keyboard is null");
		me = _me;
		interval = _interval;
		shot = _shot;
		shotKey= _shotKey;
		time = 0;
		keyboard = _keyboard;
		rx = _rx;
		ry = _ry;
		
		keyboard.AddKeyBridgeListener(this);
	}
	
	
	
	@Override
	//!shot bullets with keyboard and shot visitor
	/*!
	 * \param _sender no use
	 */
	public void BeginUpdate(GameCharacter _sender) {
		// TODO Auto-generated method stub
		if(!running)return;
		if((time % interval == 0) &&
				keyboard.GetKeyDown(shotKey)) {
			try {
				me.Shot(shot, rx, ry);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	//!count up timer
	/*!
	 */
	public void EndUpdate(GameCharacter _sender) {
		// TODO Auto-generated method stub
		time++;
	}



	@Override
	public void BeginKeyDown(KeyBridge _sender, Map<Integer, Boolean> _keyboard) {
		// TODO Auto-generated method stub
	}



	@Override
	public void EndKeyDown(KeyBridge _sender, Map<Integer, Boolean> _keyboard) {
		// TODO Auto-generated method stub
	}



	@Override
	//!turn this effect enable
	/*!
	 */
	public void BeginPlay() {
		// TODO Auto-generated method stub
		if(running)return;
		running=true;
	}



	@Override
	
	//!turn this effect disable
	/*!
	 */
	public void EndPlay() {
		// TODO Auto-generated method stub
		if(!running)return;
		running=false;
	}


}
