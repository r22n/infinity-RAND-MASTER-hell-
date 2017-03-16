package GameComponents;
import java.util.*;
import java.util.Map.Entry;
/*template graphics interface*/
import java.awt.Graphics;

//!universal type Component for ComponentContainer
/*!
 * all game objects should be extended this object
 * \author n.ryouta
 */
public class Component {
	//!listener of component
	/*!
	 * \author n.ryouta
	 */
	public interface Listener {
		//!be notified the Component invalidated by SetValid(false)
		/*!
		 * \param _sender the Component object calls this method
		 */
		public void OnInvalidated(Component _sender);
		
		//!be notified the Component validated by SetValid(true)
		/*!
		 * \param _sender the Compoennt object calls this method
		 */
		public void OnValidated(Component _sender);
		
		//!be notified the Component invisibled by SetVisible(false)
		/*!
		 * \param _sender the Component object calls this method
		 */
		public void OnInVisibled(Component _sender);

		//!be notified the Component visibled by SetVisible(true)
		/*!
		 * \param _sender the Component object calls this method
		 */
		public void OnVisibled(Component _sender);
		
		//!be notified the Component becomes being able to click
		/*!
		 * \param _sender the Component object calls this method
		 */
		public void OnClickEnabled(Component _sender);
		
		//!be notified the Component becomes not being able to click
		/*!
		 * \param _sender the Component object calls this method
		 */
		public void OnClickDisabled(Component _sender);
		
		//!be notified the Component becomes being able to catch key events
		/*!
		 * \param _sender the Component object calls this method
		 */
		public void OnKeyEnabled(Component _sender);
		//!be notified the Component becomes being not able to catch key events
		/*!
		 * \param _sender the Component object calls this method
		 */
		public void OnKeyDisabled(Component _sender);

		//!be notified the Component BeginUpdate
		/*!
		 * \param _sender the Component object calls this method
		 */
		public void BeginUpdate(Component _sender);
		
		//!be notified the Component EndUpdate
		/*!
		 * \param _sender the Component object calls this method
		 */
		public void EndUpdate(Component _sender);
		
		//!be notified the Component BeginDraw
		/*!
		 * \param _sender the Component object calls this method
		 * \param _g the GDI provided by import command
		 */
		public void BeginDraw(Component _sender,Graphics _g);
		
		//!be notified the Component BeginDraw
		/*!
		 * \param _sender the Component object calls this method
		 * \param _g the GDI provided by import command
		 */
		public void EndDraw(Component _sender,Graphics _g);

		//!be notified the Component begins clicking with mouse
		/*!
		 * \param _sender the Component object calls this method
		 * \param _x the x location of mouse cursor
		 * \param _y the y location of mouse cursor
		 * \param _mouseButtons the status of mouse buttons map
		 */
		public void BeginClick(Component _sender,double _x,double _y,Map<Integer, Boolean> _mouseButtons);
		

		//!be notified the Component ends clicking with mouse
		/*!
		 * \param _sender the Component object calls this method
		 * \param _x the x location of mouse cursor
		 * \param _y the y location of mouse cursor
		 * \param _mouseButtons the status of mouse buttons map
		 */
		public void EndClick(Component _sender,double _x,double _y,Map<Integer,Boolean> _mouseButtons);
		

		//!be notified the Component begins keydown
		/*!
		 * \param _sender the Component object calls this method
		 * \param _vkCodes the status of keyboard map
		 */
		public void BeginKeyDown(Component _sender,Map<Integer,Boolean> _vkCodes);
		//!be notified the Component ends keydown
		/*!
		 * in conclusion, key up
		 * \param _sender the Component object calls this method
		 * \param _vkCodes the status of keyboard map
		 */
		public void EndKeyDown(Component _sender,Map<Integer,Boolean> _vkCodes);

		//!be notified the new child Component added into the Component
		/*!
		 * \param _sender the Component object calls this method
		 * \param _child the new child Component 
		 */
		public void OnAddChild(Component _sender,Component _child);
		
		//!be notified the child Component removed from the Component
		/*!
		 * \param _sender the Component object calls this method
		 * \param _child the child Component
		 */
		public void OnRemoveChild(Component _sender,Component _child);
	}
	private List<Listener> listeners = 
			new ArrayList<Listener>();
	private List<Listener> ladd = new ArrayList<Listener>();
	private List<Listener> lremove = new ArrayList<Listener>();
	boolean llock = false;
	
	//!add Component.Listener object
	/*!
	 * \param _listener the listener object
	 * \warning if this Component is locking, the _listener object will be added after unlocking
	 * \exception IllegalArgumentException _listener is null
	 */
	public void AddComponentListener(Listener _listener) throws IllegalArgumentException {
		if(!(_listener!=null))throw new IllegalArgumentException("Component.AddComponentListener _listener is null");
		if(!llock) {
			listeners.add(_listener);
		}else {
			ladd.add(_listener);
		}
	}
	
	//!remove Component.Listener object
	/*!
	 * \param _listener the _listener that you want remove from this
	 * \warning if this Component is locking, the _listener object will be removed after unlocking
	 * \returns if _listener contains in this Component
	 */
	public boolean RemoveComponentListener(Listener _listener) {
		if(_listener==null)return false;
		if(!llock) {
			return listeners.remove(_listener);
		}else {
			boolean result = listeners.contains(_listener);
			if(result) {
				lremove.add(_listener);
			}
			return result;
		}
	}
	//!get all listeners
	/*!
	 * \returns the iterable object that contains all listeners
	 */
	public Iterable<Listener> GetListeners(){return listeners;}
	
	//!notify the event that this component becomes invalidate to all listeners
	/*!
		\warning this method locks this Component for notifications to all listeners. so you can not use this method in other notification handlers of Component.
		\exception Exception when this Component locking
	*/
	protected void NotifyInvalidated()throws Exception{
		if(llock) throw new Exception("Component invalid operation that listeners is notifing");
		
		llock = true;
		ladd.clear();
		lremove.clear();
		for(Listener i : listeners)i.OnInvalidated(this);
		listeners.removeAll(lremove);
		listeners.addAll(ladd);
		llock = false;
	}
	//!notify the event that this component becomes validate to all listeners
	/*!
		\warning this method locks this Component for notifications to all listeners. so you can not use this method in other notification handlers of Component.
		\exception Exception when this Component locking
	*/
	protected void NotifyValidated()throws Exception{
		if(llock) throw new Exception("Component invalid operation that listeners is notifing");
		
		llock = true;
		ladd.clear();
		lremove.clear();
		for(Listener i : listeners)i.OnValidated(this);
		listeners.removeAll(lremove);
		listeners.addAll(ladd);
		llock = false;
	}
	//!notify the event that this component becomes invisible to all listeners
	/*!
		\warning this method locks this Component for notifications to all listeners. so you can not use this method in other notification handlers of Component.
		\exception Exception when this Component locking
	*/
	protected void NotifyInVisibled()throws Exception{
		if(llock) throw new Exception("Component invalid operation that listeners is notifing");
		
		llock = true;
		ladd.clear();
		lremove.clear();
		for(Listener i : listeners)i.OnInVisibled(this);
		listeners.removeAll(lremove);
		listeners.addAll(ladd);
		llock = false;
	}
	//!notify the event that this component becomes visible to all listeners
	/*!
		\warning this method locks this Component for notifications to all listeners. so you can not use this method in other notification handlers of Component.
		\exception Exception when this Component locking
	*/
	protected void NotifyVisibled()throws Exception{
		if(llock) throw new Exception("Component invalid operation that listeners is notifing");
		
		llock = true;
		ladd.clear();
		lremove.clear();
		for(Listener i : listeners)i.OnVisibled(this);
		listeners.removeAll(lremove);
		listeners.addAll(ladd);
		llock = false;
	}
	//!notify the event that this component becomes clickable to all listeners
	/*!
		\warning this method locks this Component for notifications to all listeners. so you can not use this method in other notification handlers of Component.
		\exception Exception when this Component locking
	*/
	protected void NotifyClickEnabled()throws Exception{
		if(llock) throw new Exception("Component invalid operation that listeners is notifing");
		
		llock = true;
		ladd.clear();
		lremove.clear();
		for(Listener i : listeners)i.OnClickEnabled(this);
		listeners.removeAll(lremove);
		listeners.addAll(ladd);
		llock = false;
	}
	//!notify the event that this Component becomes unclickabke to all listeners
	/*!
		\warning this method locks this Component for notifications to all listeners. so you can not use this method in other notification handlers of Component.
		\exception Exception when this Component locking
	*/
	protected void NotifyClickDisabled()throws Exception{
		if(llock) throw new Exception("Component invalid operation that listeners is notifing");
		
		llock = true;
		ladd.clear();
		lremove.clear();
		for(Listener i : listeners)i.OnClickDisabled(this);
		listeners.removeAll(lremove);
		listeners.addAll(ladd);
		llock = false;
	}
	//!notify the event that this component becomes keyboard event enabked  to all listeners
	/*!
		\warning this method locks this Component for notifications to all listeners. so you can not use this method in other notification handlers of Component.
		\exception Exception when this Component locking
	*/
	protected void NotifyKeyEnabled()throws Exception{
		if(llock) throw new Exception("Component invalid operation that listeners is notifing");
		
		llock = true;
		ladd.clear();
		lremove.clear();
		for(Listener i : listeners)i.OnKeyEnabled(this);
		listeners.removeAll(lremove);
		listeners.addAll(ladd);
		llock = false;
	}
	//!notify the event that this component becomes keyboard event disabled to all listeners
	/*!
		\warning this method locks this Component for notifications to all listeners. so you can not use this method in other notification handlers of Component.
		\exception Exception when this Component locking
	*/
	protected void NotifyKeyDisabled()throws Exception{
		if(llock) throw new Exception("Component invalid operation that listeners is notifing");
		
		llock = true;
		ladd.clear();
		lremove.clear();
		for(Listener i : listeners)i.OnKeyDisabled(this);
		listeners.removeAll(lremove);
		listeners.addAll(ladd);
		llock = false;
	}
	//!notify the event that this component begins updating to all listeners
	/*!
		\warning this method locks this Component for notifications to all listeners. so you can not use this method in other notification handlers of Component.
		\exception Exception when this Component locking
	*/
	protected void NotifyBeginUpdate() throws Exception{
		if(llock) throw new Exception("Component invalid operation that listeners is notifing");
		
		llock = true;
		ladd.clear();
		lremove.clear();
		for(Listener i : listeners)i.BeginUpdate(this);
		listeners.removeAll(lremove);
		listeners.addAll(ladd);
		llock = false;
	}
	//!notify the event that this component ends updating to all listeners
	/*!
		\warning this method locks this Component for notifications to all listeners. so you can not use this method in other notification handlers of Component.
		\exception Exception when this Component locking
	*/
	protected void NotifyEndUpdate() throws Exception{
		if(llock) throw new Exception("Component invalid operation that listeners is notifing");
		
		llock = true;
		ladd.clear();
		lremove.clear();
		for(Listener i : listeners)i.EndUpdate(this);
		listeners.removeAll(lremove);
		listeners.addAll(ladd);
		llock = false;
	}
	//!notify the event that this component begins drawing to all listeners
	/*!
		\warning this method locks this Component for notifications to all listeners. so you can not use this method in other notification handlers of Component.
		\exception Exception when this Component locking
		\param _g GDI object
	*/
	protected void NotifyBeginDraw(Graphics _g) throws Exception{
		if(llock) throw new Exception("Component invalid operation that listeners is notifing");
		
		llock = true;
		ladd.clear();
		lremove.clear();
		for(Listener i : listeners)i.BeginDraw(this,_g);
		listeners.removeAll(lremove);
		listeners.addAll(ladd);
		llock = false;
	}
	//!notify the event that this component ends drawing to all listeners
	/*!
		\warning this method locks this Component for notifications to all listeners. so you can not use this method in other notification handlers of Component.
		\exception Exception when this Component locking
		\param _g GDI object
	*/
	protected void NotifyEndDraw(Graphics _g) throws Exception{
		if(llock) throw new Exception("Component invalid operation that listeners is notifing");
		
		llock = true;
		ladd.clear();
		lremove.clear();
		for(Listener i : listeners)i.EndDraw(this, _g);
		listeners.removeAll(lremove);
		listeners.addAll(ladd);
		llock = false;
	}
	//!notify the event that this component begins clicking to all listeners
	/*!
		\warning this method locks this Component for notifications to all listeners. so you can not use this method in other notification handlers of Component.
		\exception Exception when this Component locking
		\param _x x location of cursor
		\param _y y location of cursor
		\param _mouseButton status of mouse buttons
	*/
	protected void NotifyBeginClick(double _x,double _y,Map<Integer,Boolean> _mouseButton)throws Exception{
		if(llock) throw new Exception("Component invalid operation that listeners is notifing");
		
		llock = true;
		ladd.clear();
		lremove.clear();
		for(Listener i : listeners)i.BeginClick(this,_x,_y,_mouseButton);
		listeners.removeAll(lremove);
		listeners.addAll(ladd);
		llock = false;
	}
	//!notify the event that this component ends clicking to all listeners
	/*!
		\warning this method locks this Component for notifications to all listeners. so you can not use this method in other notification handlers of Component.
		\exception Exception when this Component locking
		\param _x x location of cursor
		\param _y y location of cursor
		\param _mouseButton status of mouse buttons
	*/
	protected void NotifyEndClick(double _x,double _y,Map<Integer,Boolean> _mouseButton) throws Exception{
		if(llock) throw new Exception("Component invalid operation that listeners is notifing");
		
		llock = true;
		ladd.clear();
		lremove.clear();
		for(Listener i : listeners)i.EndClick(this,_x,_y, _mouseButton);
		listeners.removeAll(lremove);
		listeners.addAll(ladd);
		llock = false;
	}
	//!notify the event that this component begins keyboard down to all listeners
	/*!
		\warning this method locks this Component for notifications to all listeners. so you can not use this method in other notification handlers of Component.
		\exception Exception when this Component locking
		\param _vkCodes status of keyboard that related with virtual key code.
	*/
	protected void NotifyBeginKeyDown(Map<Integer,Boolean> _vkCode)throws Exception{
		if(llock) throw new Exception("Component invalid operation that listeners is notifing");
		
		llock = true;
		ladd.clear();
		lremove.clear();
		for(Listener i : listeners)i.BeginKeyDown(this, _vkCode);
		listeners.removeAll(lremove);
		listeners.addAll(ladd);
		llock = false;
	}
	//!notify the event that this component ends keyboard down to all listeners
	/*!
		\warning this method locks this Component for notifications to all listeners. so you can not use this method in other notification handlers of Component.
		\exception Exception when this Component locking
		\param _vkCodes status of keyboard that related with virtual key code.
	*/
	protected void NotifyEndKeyDown(Map<Integer,Boolean> _vkCode)throws Exception{
		if(llock) throw new Exception("Component invalid operation that listeners is notifing");
		
		llock = true;
		ladd.clear();
		lremove.clear();
		for(Listener i : listeners)i.EndKeyDown(this, _vkCode);
		listeners.removeAll(lremove);
		listeners.addAll(ladd);
		llock = false;
	}

	//!notify the event that the other Component added into this Component as child to all listeners
	/*!
		\warning this method locks this Component for notifications to all listeners. so you can not use this method in other notification handlers of Component.
		\exception Exception when this Component locking
		\param _child the new child object
	*/
	protected void NotifyAddChild(Component _child)throws Exception{
		if(llock) throw new Exception("Component invalid operation that listeners is notifing");
		
		llock = true;
		ladd.clear();
		lremove.clear();
		for(Listener i : listeners)i.OnAddChild(this, _child);
		listeners.removeAll(lremove);
		listeners.addAll(ladd);
		llock = false;
	}
	//!notify the event that the other Component removed from this Component as child to all listeners
	/*!
		\warning this method locks this Component for notifications to all listeners. so you can not use this method in other notification handlers of Component.
		\exception Exception when this Component locking
		\param _child the child object
	*/
	protected void NotifyRemoveChild(Component _child)throws Exception{
		if(llock) throw new Exception("Component invalid operation that listeners is notifing");
		
		llock = true;
		ladd.clear();
		lremove.clear();
		for(Listener i : listeners)i.OnRemoveChild(this, _child);
		listeners.removeAll(lremove);
		listeners.addAll(ladd);
		llock = false;
	}
		
	
		
	
	
	private List<Component> childs = 
			new ArrayList<Component>();
	private Map<String,Component> dictionary = 
			new HashMap<String,Component>();
	
	//!add child Component into this Component with relational key as string
	/*!
		\exception IllegalArgumentException given child component as argument is null
		\exception IllegalArgumentException given key string will be related with child component is null
		\exception IllegalArgumentException given key has been held by this component
		\param _child the new child object
		\param _key the key as string will be related with child
	*/
	public void AddChild(Component _child,String _key) throws Exception {
		if(!(_child!=null))throw new IllegalArgumentException("Component.AddChild _child is null");
		if(!(_key!=null&!_key.isEmpty()))throw new IllegalArgumentException("Component.AddChild _key is null or empty");
		if(!(!dictionary.containsKey(_key)))throw new IllegalArgumentException("Component.AddChid _key is already added");

		dictionary.put(_key, _child);
		AddChild(_child);
	}
	//!get child component object by key
	/*!
		this method returns the component object that related with key by AddChild
		\param _key the key given in AddChild
		\returns if there is component related with the key, thats component. otherwise, null.
	*/
	public Component GetComponent(String _key) {
		if(_key==null) return null;
		if(_key.isEmpty()) return null;
		return dictionary.get(_key);
	}
	//!remove component from this component with key
	/*!
		removes the component with key
		\param _key the key given in AddChild 
		\returns if this method cloud remove the component, true. otherwise, false.
	*/
	public boolean RemoveComponent(String _key) throws Exception {
		return (dictionary.remove(_key)!=null);
	}
	//!add child component  without key
	/*!
		add child component without key, the object that was added
	*/
	public void AddChild(Component _child)throws Exception{ 
		if(!(_child!=null))throw new IllegalArgumentException("Component.AddChild _child");
		childs.add(_child);
		NotifyAddChild(_child);
	}
	//!check this component has the specified the child component
	/*!
		\param _child the child component what you want to check being this.
		\returns if the specified child component was in this, true. otherwise, false.
	*/
	public boolean HasComponent(Component _child) {
		if(_child==null)return false;
		return childs.indexOf(_child) != -1;
	}
	//!remove child component from this component
	/*!
		/param _child the child component what you want to try removing
		/returns if the specified child component was in this, true. otherwise, false.
	*/
	public boolean RemoveComponent(Component _child) throws Exception {
		if(_child==null)return false;
		boolean result = childs.remove(_child);
		if(result)NotifyRemoveChild(_child);
		if(result) {
			String key = null;
			for(Entry<String,Component> c : dictionary.entrySet()) {
				if(c.getValue()==_child) {
					key = c.getKey();
					break;
				}
			}
			if(key!=null) {
				RemoveComponent(key);
			}
		}
		return result;
	}
	//!get all child components
	/*!
		/returns the iterable object that contains all child componentals
	*/
	public Iterable<Component> GetChilds(){return childs;}
	
	
	private boolean valid, visible, clickable, keyenable;
	private int priority;
	
	private Rect bounds = new Rect();
	
	//!initialize this component
	/*! 
		initialize this component with priority of zero and all flags are true
	*/
	public Component(){
		valid = visible = clickable = keyenable = true;
		priority = 0;
	}
	//!get this component's geometric bounds as rectangle type
	/*!
		this method returns built in bounds of rectangle that this component exposed. if you want to actuate same way with other component, you should implement the geometric expression with this method.
		\returns rectangle that this component's bounds exposed.
	*/
	public Rect GetBounds(){return bounds;}
	public void SetBounds(Rect _srcCopy)throws IllegalArgumentException {
		if(!(_srcCopy!=null))throw new IllegalArgumentException("Component.SetBounds _srcCopy is null");
		
		bounds.Copy(_srcCopy);
	}
	//!get this component should be updated
	/*!
		\returns the flag that the ComponentContainer calls the BeginUpdate.
	*/
	public boolean IsValid(){return valid;}
	//!set the flag that this component should be updated
	/*!
		\param _valid the flag that this component should be updated
		\warning this method notify the event that this component was invalidated or validated to all listeners.
	*/
	public void SetValid(boolean _valid) throws Exception {
		valid = _valid;
		if(valid) {
			NotifyValidated();
		}else {
			NotifyInvalidated();
		}
	}
	//!get the flag that this component should be drawn
	/*!
		\returns th flags that this component should be drawn
	*/
	public boolean IsVisible(){return visible;}
	//!set the flag that this component should be drawn
	/*! 
		\param _visible the flag that this component should be drawn
		\warning this method notify that this component becomes visible or invisible to all listeners.
	*/
	public void SetVisible(boolean _visible) throws Exception {
		visible = _visible;
		if(visible) {
			NotifyVisibled();
		}else {
			NotifyInVisibled();
		}
	}
	//! get the flag that this component should be clicked
	/*!
		\returns the flag that this component should be clicked
	*/
	public boolean Clickable() {return clickable;}
	//!set the flag that this component should be clicked
	/*!
		\param _clickable the flag that this component should be clicked
		\warning this method notify that this component becomes clickable or unclickable to all listeners.
	*/
	public void SetClickable(boolean _clickable) throws Exception {
		clickable = _clickable;
		if(clickable) {
			NotifyClickEnabled();
		}else {
			NotifyClickDisabled();
		}
	}
	//!get the flag that this component should receive events that keyboard downing or releasing.
	/*!
		\returns this component should receive events that keyboard downing or releasing.
	*/
	public boolean IsKeyEnable(){return keyenable;}
	//!set th flag that this component should receive events that keyboard downing or releasing
	/*!
		\param _keyenable the flag that this component should receive events that keyboard downing or releasing
	*/
	public void SetKeyEnable(boolean _keyenable) throws Exception {
		keyenable = _keyenable;
		if(keyenable) {
			NotifyKeyEnabled();
		}else {
			NotifyKeyDisabled();
		}
	}
	//!get this component's priority that drawing in screen 
	/*!
		\returns the priority that all components should be drawn in greater oder. so the component has the greatest priority in all components.
	*/
	public int GetPriority() {return priority;} 
	//!set this component's priority that drawing in screen
	/*!
		\param _priority the priority of this
	*/
	public void SetPriority(int _priority) {
		priority = _priority;
	}
	//!sorts all child components for drawing in screen
	/*!
		this method sorts all child component in this container for drawing in screen in greater order from top of screen.
	*/
	public void ApplyPriority() {
		childs.sort(new Comparator<Component>(){
			@Override
			public int compare(Component o1, Component o2) {
				// TODO Auto-generated method stub
				return (int)(o1.GetPriority() - o2.GetPriority());
			}
		});
	}
	//!begins updating this component 
	/*!
		notify the event that this component begins updating to all listeners.
		\warning if you implemented this method, please call super.BeginUpdate for calling listeners.
	//!begins drawing this component
	/*!
		notify the event that this component begims drawing to all listeners.
		\warning if you implemented this method, please call super.BeginUpdate for calling listeners.
		\param _parent the parent component that handling this component.
	*/
	public void BeginUpdate(Component _parent) throws Exception {
		NotifyBeginUpdate();
	}
	//!ends updating this component
	/*!
		notify the event that this component ends updating to all listeners.
		\warning if you implemented this method, please call super.EndUpdate for calling listeners.
		\param _parent the parent component that handling this component.
	*/
	public void EndUpdate(Component _parent) throws Exception {
		NotifyEndUpdate();
	} 
	//!begins drawing this component
	/*!
		notify the event that this component begins drawing to all listeners.
		\warning if you implemented this method, please call super.BeginDraw for calling listeners.	
		\param _parent the parent component that handling this component.
		\param _g GDI object that you want to draw in 
	*/
	public void BeginDraw(Component _parent,Graphics _g) throws Exception {
		NotifyBeginDraw(_g);
	}
	//!ends drawing this component
	/*!
		notify the event that this component ends drawing to all listeners.
		\warning if you implemented this method, please call super.EndDraw for calling listeners.
		\param _parent the parent component that handling this component.
		\param _g GDI object that you want to draw in
	*/
	public void EndDraw(Component _parent,Graphics _g) throws Exception {
		NotifyEndDraw(_g);
	}
	//!begins clicking this component
	/*!
		\param _parent the parent component that handling this
		\param _x x location of mouse cursor
		\param _y y location of mouse cursor
		\param _mouseButton status of mouse buttons
	*/
	public void BeginClick(Component _parent,double _x,double _y,Map<Integer,Boolean> _mouseButton) throws Exception {
		NotifyBeginClick(_x,_y,_mouseButton);
	}
	//!ends clicking this component
	/*!
		\param _parent the parent component that handling this
		\param _x x location of mouse cursor
		\param _y y location of mouse cursor
		\param _mouseButton status of mouse buttons
	*/
	public void EndClick(Component _parent,double _x,double _y,Map<Integer,Boolean> _mouseButton) throws Exception {
		NotifyEndClick(_x,_y,_mouseButton);
	}
	//!begins keyboard downing this component
	/*!
		\param _parent the parent component that handling this
		\param _vkCode status of keyboard pressing that related with virtual keycode
	*/
	public void BeginKeyDown(Component _parent,Map<Integer,Boolean> _vkCode) throws Exception {
		NotifyBeginKeyDown(_vkCode);
	}
	//!ends keyboard downing this component
	/*!
		\param _parent the parent component that handling this
		\param _vkCode status of keyboard pressing that related with virtual keycode
	*/
	public void EndKeyDown(Component _parent,Map<Integer,Boolean> _vkCode) throws Exception {
		NotifyEndKeyDown(_vkCode);
	}
	
	//!check this component intersect with mouse cursor
	/*!
		this method checks this component bounds as rectangle with mouse cursor location .
		\param _x x location of mouse cursor
		\param _y y location of mouse cursor
	*/
	public boolean Intersect(double _x,double _y) {
		return bounds.Intersect(_x, _y);
	}
	//!accept visitor object for some extensions
	/*!
		\param _visitor the visitor object for extending this component
		\exception IllegalArgumentException _viaitor is null
	*/
	public void Accept(Visitor<Component> _visitor) throws IllegalArgumentException{
		if(!(_visitor!=null))throw new IllegalArgumentException("Component.Accept _visitor is null");
		_visitor.Visit(this);
	}
}
