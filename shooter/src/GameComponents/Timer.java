package GameComponents;

import java.util.*;

//!notify after the specified time
/*!
 * \author n.ryouta
 */
public class Timer extends ComponentContainer{
	//!handler of timer
	/*!
	 * \author n.ryouta
	 */
	public interface Listener {
		//!be notified the event that the timer reaches time provided by this bridge
		/*!
		 * \param _sener the object calls this method
		 */
		void OnTimeReach(Timer _sender);
		
		//!get the time in frame that event handling
		/*!
		 * \returns the time in frame that event handling
		 */
		long GetTimeInFrame();
	}
	private List<Listener> listeners = 
			new ArrayList<Listener>();
	private List<Listener> append = 
			new ArrayList<Listener>();
	private List<Listener> erase = 
			new ArrayList<Listener>();
	private boolean lock = false;
	private long time = 0;
	//!add the timer handler 
	/*!
	 * \param _listener the listener will receive the event
	 * \warning if this timer is locking listener, the listener will be added after unlocking
	 */
	public void AddTimerListener(Listener _listener)throws IllegalArgumentException {
		if(!(_listener!=null))throw new IllegalArgumentException("Timer.AddTimerListener _listener is null");
		if(!lock) {
			listeners.add(_listener);
		}else {
			append.add(_listener);
		}
	}
	//!remove the timer handler
	/*!
	 * \param _listener the listener that you want to remove
	 * \returns if the listener is in this timer, true. otherwise, false.
	 */
	public boolean RemoveTimerListener(Listener _listener) {
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
	//!clocks timer and notifies event
	/*!
	 * \param _parent the parent component that contains this
	 */
	public void BeginUpdate(Component _parent) throws Exception {
		super.BeginUpdate(_parent);
		
		lock = true;
		append.clear();
		erase.clear();
		for(Listener i : listeners) {
			if(i.GetTimeInFrame() == time) {
				i.OnTimeReach(this);
			}
		}
		listeners.removeAll(erase);
		listeners.addAll(append);
		lock = false;
		
		time++;
	}
	
	//!reset this timer clock
	/*!
	 * reset this timer clock and re-notify all listeners
	 */
	public void ResetTimer() {
		time = 0;
	}
	
		
}
