package ShooterUpdater;

import java.util.ArrayList;
import java.util.List;

import GameComponents.*;
import ShooterComponents.*;

//!bullet move strategy
/*!
 * \author n.ryouta
 */
public class BulletMove implements GameCharacterUpdater{
	
	//!handler of BulletMove
	/*!
	 * \author n.ryouta
	 */
	public interface Listener {
		
		//!be notified the GameCharacter move out from rectangle area
		/*!
		 * \param _sender the object this calls this method
		 * \param _e the GameCharacter moved out
		 */
		void OnOutOfBound(BulletMove _sender,GameCharacter _e);
	}
	private List<Listener> listeners =
			new ArrayList<Listener>();
	private List<Listener> erase =
			new ArrayList<Listener>();
	private List<Listener> append =
			new ArrayList<Listener>();
	private boolean lock = false;
	
	//!add handler
	/*!
	 * \param _listener handler
	 * \exception IllegalArgumentException _listener is null
	 */
	public void AddBulletMoveListener(Listener _listener)throws IllegalArgumentException {
		if(!(_listener!=null))throw new IllegalArgumentException("BulletMove.AddBulletMoveListener _listener is null");
		if(!lock) {
			listeners.add(_listener);
		}else{
			append.add(_listener);
		}
	}
	
	//!remove handler
	/*!
	 * \param _listener the handler that you want to remove
	 * \returns if the _listener is in this, true. otherwise, false.
	 */
	public boolean RemoveBulletMoveListener(Listener _listener) {
		if(_listener==null)return false;
		if(!lock) {
			return listeners.remove(_listener);
		}else {
			boolean result = listeners.contains(_listener);
			if(result) {
				erase.add(_listener);
			}
			return result;
		}
	}
	//!notify the GameCharacter moved out from bounds as rectangle area
	/*!
	 * \param _e the GameCharacter moved out
	 */
	protected void NotifyOutOfBound(GameCharacter _e)throws Exception {
		if(lock)throw new Exception("BulletMove.NotifyOutOfBound invalid operation");
		
		lock = true;
		erase.clear();
		append.clear();
		for(Listener i : listeners) {
			i.OnOutOfBound(this, _e);
		}
		listeners.removeAll(erase);
		listeners.addAll(append);
		lock = false;
	}
		
	
	private Rect range;
	private double dx,dy;
	private double FPS;
	//!initialize
	/*!
	 * \param _refRange reference of the bounds
	 * \param _vx speed x in px per sec
	 * \param _vy speed y in px per sec
	 * \param _FPS frame rate
	 * \exception IllegalArgumentException _refRange is null
	 * 
	 */
	public BulletMove(Rect _refRange,double _vx,double _vy,double _FPS)throws IllegalArgumentException {
		if(!(_refRange!=null))throw new IllegalArgumentException("BulletMove _refRange is null");
		range = _refRange;
		dx = _vx / _FPS;
		dy = _vy / _FPS;
		FPS = _FPS;
	}
	
	//!set the speed of bullet
	/*!
	 * \param _sx speed x in px per sec
	 * \param _sy speed y in px per sec
	 */
	public void SetSpeed(double _sx,double _sy) {
		dx = _sx / FPS;
		dy = _sy / FPS;
	}
	//!get speed x in px per sec
	/*!
	 * \returns speed x
	 */
	public double GetSpeedX(){return dx*FPS;}
	//!get speed y in px per sec
	/*!
	 * \returns speed y
	 */
	public double GetSpeedY(){return dy*FPS;}
	
	
	@Override
	//!make the _sender move to
	/*!
	 * if _sender can be casted to Bullet type and moved out, call Bullet.EndShot
	 * \param _sender the move target
	 */
	public void BeginUpdate(GameCharacter _sender) {
		// TODO Auto-generated method stub
		if(_sender==null) {
			System.out.println("BulletMove _sender is null");
			return ;
		}
		Rect bounds = _sender.GetBounds();
		bounds.x += dx;
		bounds.y += dy;
		
		
		if(!range.Intersect(bounds)) {
			try {
				NotifyOutOfBound(_sender);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			if(_sender instanceof Bullet) {
				Bullet b = (Bullet)_sender;
				try {
					b.EndShot();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	//!empty
	/*!
	 */
	public void EndUpdate(GameCharacter _sender) {
		// TODO Auto-generated method stub
		
	}

}
