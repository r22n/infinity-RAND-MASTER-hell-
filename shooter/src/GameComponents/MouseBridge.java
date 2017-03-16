package GameComponents;

import java.util.*;

//!the bridge of mouse 
/*!
	\author n.ryouta
*/
public class MouseBridge extends ComponentContainer {
	//!the visitor object 
	public interface Visitor {
		//!visit to the myself
		/*!
			\param _this myself
		*/
		void Visit(MouseBridge _this);
		//!visit to the mouse location that is not referencing
		/*!
			\param _x the x location of mouse that is in MouseBridge
			\param _y the y location of mouse that is in MouseBridge
		*/
		void Visit(double _x,double _y);
		//!visit mouse button status 
		/*!
			\param _mouseButtons the mouse button status
		*/
		void Visit(HashMap<Integer,Boolean> _mouseButtons);
	}
	//!accept the visitor
	/*!
		\param _visitor the visitor object that executes extension
		\exception IllegalArgumentException _visitor is null
	*/
	public void Accept(Visitor _visitor)throws IllegalArgumentException {
		if(!(_visitor!=null))throw new IllegalArgumentException("MouseBridge.Accept _visitor is null");
		_visitor.Visit(this);
		_visitor.Visit(x,y);
		_visitor.Visit(mouseButtons);
	}
	//!the listener for MouseBridge
	/*!
		\author n.ryouta
	*/
	public interface Listener {
		//!be notified the MouseBridge begins mouse button down 
		/*!
			\param _sender the object calls this method
			\param _mkuseButton the status of mouse button
		*/
		void BeginMouseButtonDown(MouseBridge _sender,Map<Integer,Boolean> _mouseButton) ;

		//!be notified the MouseBridge ends mouse button down 
		/*!
			\param _sender the object calls this method
			\param _mkuseButton the status of mouse button
		*/
		void EndMouseBUttonDown(MouseBridge _sender,Map<Integer,Boolean> _mouseButton) ;
	} 
	private List<Listener> listeners = 
			new ArrayList<Listener>();
	//!add mouse bridge listener
	/*!
		\param _listener the listener that will receive some events
		\exception IllegalArgumentException _listener 
		\warning th MouseBridge would not lock myself about listeber. so, you must not add or remove listener
is null	*/
	public void AddMouseBridgeListener(Listener _listener)throws IllegalArgumentException {
		if(!(_listener!=null))throw new IllegalArgumentException("MouseBridge.AddMouseBridgeListener _listener is null");
		listeners.add(_listener);
	}
	//!remove mouse bridge listener
	/*!
		\param _listener the listener that will be removed from this
		\exception IllegalArgumentException _listener 
		\warning th MouseBridge would not lock myself about listeber. so, you must not add or remove listener
is null	*/
	public boolean RemoveMouseBridgeListener(Listener _listener) {
		if(_listener==null)return false;
		return listeners.remove(_listener);
	}
	//!notify the event that this object begins key downing
	/*!
		
	*/
	protected void NotifyBeginMouseButtonDown() throws Exception {
		BeginClick(this,x,y,mouseButtons);
		for(Listener i : listeners) {
			i.BeginMouseButtonDown(this, mouseButtons);
		}
	}
	//!notify the event that this oject ends key downing
	/*!
		
	*/
	protected void NotifyEndMouseButtonDown() throws Exception {
		EndClick(this,x,y,mouseButtons);
		for(Listener i : listeners) {
			i.EndMouseBUttonDown(this, mouseButtons);
		}
	}
	
	
	private double x,y;
	private HashMap<Integer, Boolean> mouseButtons = 
			new HashMap<Integer,Boolean>();
	
	//!set the location of this mouse x
	/*!
		updates status of mouse x location
	*/
	//!set the location of this mouse x
	/*!
		updates status of mouse x location
	*/
	protected void SetXLocation(double _x){x = _x;}
	//!set the location of this mouse y
	/*!
		updates status of mouse y location
	*/
	protected void SetYLocation(double _y){y = _y;}
	//!set the status of mouse button 
	/*!
		updates status of mouse
	*/
	protected void SetMouseButton(int _mouseButton,boolean _state) {
		mouseButtons.put(_mouseButton, _state);
	}
	//!get the x location of this
	/*!
		\returns the x location of this 
	*/
	public double GetXLocation(){return x;}

	//!get the y location of this
	/*!
		\returns the y location of this 
	*/
	public double GetYLocation(){return y;}

	// get the status of mouse button
	/*!
		\returns the status of mouse button
	*/
	public Map<Integer,Boolean> GetMouseButtons(){
		return (Map<Integer,Boolean>)mouseButtons.clone();
	}
		
}
