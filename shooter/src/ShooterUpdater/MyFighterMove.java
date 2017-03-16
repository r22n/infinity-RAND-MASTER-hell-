package ShooterUpdater;
import GameComponents.*;
import ShooterComponents.*;
import ShooterDrawers.IEffect;

import java.util.*;

//!my fighter move updater with keyboard
/*!
 * \author n.ryouta
 */
public class MyFighterMove implements GameCharacterUpdater,IEffect {
	private boolean running = false;
	private KeyBridge keyboard;
	private Map<Integer,Vector2> moves = new HashMap<Integer,Vector2>();
	private Map<Integer,Double> speedup = new HashMap<Integer,Double>();
	//!initialize
	/*!
	 * \param _keyboard the keyboard object
	 * \exception IllegalArgumentException _keyboard is null
	 */
	public MyFighterMove(KeyBridge _keyboard) throws IllegalArgumentException {
		if(!(_keyboard!=null))throw new IllegalArgumentException("MyFighterMove _keyboard null");
		keyboard = _keyboard;
	}
	//!register moving keyboard configuration
	/*!
	 * \param _vk virtual-keycode
	 * \param _speed move speed
	 * \param _FPS frame rate
	 * \exception IllegalArgumentException _speed is null
	 * \exception IllegalArgumentException _FPS is smaller equal than 0
	 */
	public void RegisterKeyConfig(int _vk,Vector2 _speed,double _FPS) throws IllegalArgumentException {
		if(!(_speed!=null))throw new IllegalArgumentException("MyFighterMove.RegisterKeyConfig _speed is null");
		if(!(_FPS>0))throw new IllegalArgumentException("MyFighter.RegisterKeyConfig _FPS must be greater than 0");
		Vector2 m = new Vector2(_speed.x / _FPS,_speed.y / _FPS);
		moves.put(_vk,m);
	}
	//!register speed up rate
	/*!
	 * \param _vk virtual-keycode
	 * \param _speedup rate of move speed
	 * \exception IllegalArgumentException _speedup is smaller equal than 0
	 */
	public void RegisterSpeedUp(int _vk,double _speedup)throws IllegalArgumentException {
		if(!(_speedup > 0))throw new IllegalArgumentException("MyFighterMove.RegisterSpeedUp _speedup must be greater than 0");
		speedup.put(_vk, _speedup);
	}
	
	@Override
	//!move with keyboard config
	/*!
	 * \param _sender the GameCharacter that was moved
	 */
	public void BeginUpdate(GameCharacter _sender) {
		// TODO Auto-generated method stub
		if(!running)return;
		if(_sender==null) {
			System.out.println("MyFighterMove _sender is null");
			return ;
		}
		Rect bounds = _sender.GetBounds();
		
		double k = 1;
		for(Map.Entry<Integer,Double> i : speedup.entrySet()) {
			if(keyboard.GetKeyDown(i.getKey())) {
				k = i.getValue();
			}
		}
		
		for(Map.Entry<Integer,Vector2> i : moves.entrySet()) {
			if(keyboard.GetKeyDown(i.getKey())) {
				
				Vector2 ds = i.getValue();
				bounds.x += k*ds.x;
				bounds.y += k*ds.y;
			}
		}
		
	}

	@Override
	public void EndUpdate(GameCharacter _sender) {
		// TODO Auto-generated method stub
	}
	@Override
	public void BeginPlay() {
		// TODO Auto-generated method stub
		if(running)return;
		running = true;
		
	}
	@Override
	public void EndPlay() {
		// TODO Auto-generated method stub
		if(!running)return;
		running = false;
	}

}
