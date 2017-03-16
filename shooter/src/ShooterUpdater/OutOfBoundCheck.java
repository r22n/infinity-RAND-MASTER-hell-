package ShooterUpdater;

import GameComponents.Rect;
import ShooterComponents.*;
import java.util.*;

//!checking out of bound
/*!
 * \author n.ryouta
 */
public class OutOfBoundCheck implements GameCharacterUpdater {
	//!handler of bound
	/*!
	 * \author n.ryouta
	 */
	public interface Listener {
		//!be notified the GameCharacter is not being in bounds as the rectangle area every frame
		/*!
		 * \param _sender the object calls this method
		 * \param _e the object moving out
		 */
		void OnOutOfBound(OutOfBoundCheck _sender,GameCharacter _e);
		
		//!be notified the GameCharacter is being in bounds as the rectangle every frame
		/*!
		 * \param _sender the object calls this method
		 * \param _e the object moving in
		 */
		void OnInnerBound(OutOfBoundCheck _sender,GameCharacter _e);
	}
	private List<Listener> listeners = 
			new ArrayList<Listener>();
	private List<Listener> erase= 
			new ArrayList<Listener>();
	private List<Listener> append = 
			new ArrayList<Listener>();
	private boolean lock = false;
	
	//!add handler
	/*!
	 * \param _listener handler
	 * \exception IllegalArgumentException _listener is null
	 */
	public void AddOutOfBoundCheck(Listener _listener)throws IllegalArgumentException {
		if(!(_listener!=null))throw new IllegalArgumentException("OutOfBoundCheck.AddOutOfBoundCheckListener _listener is null");
		if(!lock) {
			listeners.add(_listener);
		}else {
			append.add(_listener);
		}
	}
	//!remove handler
	/*!
	 * \param _listener the handler that you want to remove
	 * \returns if the _listener is in this, true. otherwise false.
	 */
	public boolean RemoveOutOfBoundCheck(Listener _listener) {
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
	//!notify GameCharacter object is not being in out of bounds
	/*!
	 * \param _e target GameCharacter
	 */
	protected void NotifyOutOfBound(GameCharacter _e) throws Exception {
		if(lock)throw new Exception("OutOfBoundCheck.NotifyOutOfBound unsafe operation : listeners locking now");
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
	//!notify GameCharacter object is being in out of bounds
	/*!
	 * \param _e target GameCharacter
	 */
	protected void NotifyInnerBound(GameCharacter _e) throws Exception {
		if(lock)throw new Exception("OutOfBoundCheck.NotifyOutOfBound unsafe operation : listeners locking now");
		lock = true;
		erase.clear();
		append.clear();
		for(Listener i : listeners) {
			i.OnInnerBound(this, _e);
		}
		listeners.removeAll(erase);
		listeners.addAll(append);
		lock = false;
	}
	
	private Rect scr;
	//!initializer
	/*!
	 * \param _x x location of bounds
	 * \param _y y location of bounds
	 * \param _width width of bounds
	 * \param _height height of bounds
	 */
	public OutOfBoundCheck(
			double _x,double _y,double _width,double _height
			)throws IllegalArgumentException {
		if(!(_width > 0))throw new IllegalArgumentException("OutOfBoundCheck _width must be greater than 0");
		if(!(_height > 0))throw new IllegalArgumentException("OutOfBoundCheck _height must be greater than 0");
		scr = new Rect(_x,_y,_width,_height);
	}
	
	@Override
	//!check bounds and notify
	/*!
	 * \param _sender the GameCharacter that has bounds will be checked
	 */
	public void BeginUpdate(GameCharacter _sender) {
		// TODO Auto-generated method stub
		if(!(_sender!=null)) {
			System.out.println("OutOfBoundCheck.BeginUpdate _sender is null");
			return ;
		}
		if(!scr.Intersect(_sender.GetBounds())) {
			try {
				NotifyOutOfBound(_sender);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else {
			try {
				NotifyInnerBound(_sender);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
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
