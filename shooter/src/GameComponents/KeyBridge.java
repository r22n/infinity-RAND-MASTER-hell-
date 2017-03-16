package GameComponents;
import java.util.*;

//!bridge adapter of keyboard that cab notify to listener
/*!
	\author n.ryouta
*/
public class KeyBridge extends ComponentContainer{
	//!visitor of KeyBridge
	/*!
		\author n.ryouta
	*/
	public interface Visitor {
		//!visit my self
		/*!
			execute extension for myself
			\param _this the object of myself
		*/
		void Visit(KeyBridge _this);
		//!visit into the member of keyboard status
		/*!
			\param _vkDown keyboard pressing status that related with virtual keycode
		*/
		void Visit(HashMap<Integer,Boolean> _vkDown);
	}
	//!accept visitor object
	/*!
		execute extension that provided by visitor
		\param _visitor the visitor will be accepted
		\exception IllegalArgumentException _visitor is null
	*/
	public void Accept(Visitor _visitor)throws IllegalArgumentException {
		if(!(_visitor!=null))throw new IllegalArgumentException("KeyBridge.Accept _visitor is null");
		_visitor.Visit(this);
		_visitor.Visit(vkDowns);
	}
	//!the listener will be notified events from KeyBridge
	/*!
		\author n.ryouta
	*/
	public interface Listener {
		//!the KeyBridge begins keyboard pressing
		/*!
			\param _sender the object calls this method
			\param _keybord the status of keyboard
		*/
		void BeginKeyDown(KeyBridge _sender,Map<Integer,Boolean> _keyboard);
		//!the KeyBridge ends key pressing
		/*!
			\param _sender the object calls this method
			\param _keybord the status of keyboard
			
		*/
		void EndKeyDown(KeyBridge _sender,Map<Integer,Boolean> _keyboard);
	}
	private List<Listener> listeners  =
			new ArrayList<Listener>();
	//!add KeyBridge listener 
	/*!
		add keyboard listener 
		\param _listener the listener object
		\warning this method would not lock myself. so, you must not re
move or add listener while the handling event such as KeyBridge listener.
		\exception IllegalArgumentException _listener is null
	*/
	public void AddKeyBridgeListener(Listener _listener)throws IllegalArgumentException {
		if(!(_listener!=null))throw new IllegalArgumentException("KeyBridge.AddKeyBridgeListener _listener is null");
		listeners.add(_listener);
	}
	//!remove KeyBridge listener from this
	/*!
		\param _listener the listener that you want to remove from this
		\warning this method would not lock myself. so, you must not re
move or add listener while the handling event such as KeyBridge listener.
		\exception IllegalArgumentException _liateber is null
		
	*/
	public boolean RemoveKeyBridgeListener(Listener _listener) {
		if(_listener==null)return false;
		return listeners.remove(_listener);
	}
	//!notify the keyboard begins key pressing 
	/*!
		notify the keyboard begins key pressing to all listebers
	*/
	protected void NotifyBeginKeyDown() throws Exception {
		BeginKeyDown(this, vkDowns);
		for(Listener i : listeners){
			i.BeginKeyDown(this, vkDowns);
		}
	}
	//!notify the keyboard ends key pressing 
	/*!
		notify the keyboard ends key pressing to all listebers
	*/
	protected void NotifyEndKeyDown() throws Exception {
		EndKeyDown(this,vkDowns);
		for(Listener i : listeners) {
			i.EndKeyDown(this, vkDowns);
		}
	}
		
	
	
	private HashMap<Integer, Boolean> vkDowns =
			new HashMap<Integer, Boolean>();
	//!set the keyboard status 
	/*!
		\param _vkCode the virtual keyboard
		\param _state keyboard status 
	*/
	protected void SetKeyDown(int _vkCode,boolean _state) {
		vkDowns.put(_vkCode, _state);
	}
	//!get the flag what the specified keyboard with virtual keycode is set
	/*!
		\param _vkCode the virtual keycode
		\returns if the specified key is downing, true. otherwise, false.
	*/
	public boolean GetKeyDown(int _vkCode) {
		if(!vkDowns.containsKey(_vkCode))return false;
		return vkDowns.get(_vkCode);
	}
	//!get the status of all keys
	/*!
		\returns the status of all keys
	*/
	public Map<Integer,Boolean> GetKeyState() {
		return (Map<Integer,Boolean>)vkDowns.clone();
	}
}
