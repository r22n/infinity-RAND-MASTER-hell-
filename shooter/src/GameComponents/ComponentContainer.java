package GameComponents;
import java.util.*;
import java.util.Map.Entry;
import java.awt.Graphics;

//!the container solution that can contain some Component objects
/*!
	container for components to conclude some functions as basic composite
	\author n.ryouta
*/
public class ComponentContainer 
	extends Component 
	implements Iterable<Component>{
	//!visitor that can extend the ComponentContainer as extension
	/*!
		\author n.ryouta
	*/
	public interface Visitor {
		//!visit into ComponentContainer
		/*!
			/param _this the target ComponentContainer
		*/
		void Visit(ComponentContainer _this);
	}
	//!accept visitor object for extension
	/*!
		\param _visitor th visitor object that extend this ComponentContainer
		\exceptio IllegalArgumentException the visitor is null
	*/
	public void Accept(Visitor _visitor) throws IllegalArgumentException {
		if(!(_visitor!=null))throw new IllegalArgumentException("ComponentContainer _visitor is null");
		_visitor.Visit(this);
	}
	
	
	private boolean lock = false;
	private List<Component> append = new ArrayList<Component>();
	private List<Component> erase = new ArrayList<Component>();

	//!remove component with key
	/*!
		this method removes component from super component with locking flag. 
		\exception Exception if this ComponentContainer locks child components
		\warning if you removed from this ComponentContainer with locking by this method, this method throws exception.
		\see Component.RemoveComponent
		\param _key the key related with the child component
		\returns if the specified child component is in this ComponentContainer, true. otherwise, false.
	*/
	public boolean RemoveComponent(String _key) throws Exception {		
		if(lock)throw new Exception("unsafe operation, locking components");
		return super.RemoveComponent(_key);
	}
	//!add child component into this ComponentContainer
	/*!
		if this ComponentContainer was not locked, the given child component will be added as soon.
		otherwise, the child component will be added after unlocking.
		\param _child the child component that you want to add

	*/
	public void AddChild(Component _child)throws Exception{
		if(!lock) {
			super.AddChild(_child);
		}else {
			append.add(_child);
		}
	}
	//!remove child component from this ComponentContainer
	/*!
		if this ComponentContainer was not locked, the given child component will be added as soon.
		otherwise, the child component will be added after unlocking.
		\param _child the child component that you want to remove from this
		\returns if the specified child component is in this ComponentContainer, true. otherwise, false.
	*/
	public boolean RemoveComponent(Component _child) throws Exception {
		if(!lock) {
			return super.RemoveComponent(_child);
		}else {
			boolean result = HasComponent(_child);
			if(result) {
				erase.add(_child);
			}
			return result;
		}
	}
	//!begins updating all child components 
	/*!
		\param _parent the parent component object that handles this
		\excetion Exception if this ComponentContainer locks child components, this method will throw the Exception as unsaf operation
	*/
	public void BeginUpdate(Component _parent) throws Exception {
		super.BeginUpdate(_parent);
		if(lock)throw new Exception("unsafe operation : locking components");
		
		lock = true;
		append.clear();
		erase.clear();
		for(Component c : this) {
			if(c.IsValid())c.BeginUpdate(this);
		}
		lock = false;
		for(Component i : erase)RemoveComponent(i);
		for(Component i : append)AddChild(i);
	}
	//!ends updating all child components 
	/*!
		\param _parent the parent component object that handles this
		\excetion Exception if this ComponentContainer locks child components, this method will throw the Exception as unsaf operation
	*/
	public void EndUpdate(Component _parent) throws Exception {
		super.EndUpdate(_parent);
		if(lock)throw new Exception("unsafe operation : locking components");
		lock = true;
		append.clear();
		erase.clear();
		for(Component c : this) {
			if(c.IsValid())c.EndUpdate(this);
		}
		lock = false;
		for(Component i : erase)RemoveComponent(i);
		for(Component i : append)AddChild(i);
	}
	//!begins drawing all child components 
	/*!
		\param _parent the parent component object that handles this
		\param _g GDI object that related with the screen that you want to draw in
		\excetion Exception if this ComponentContainer locks child components, this method will throw the Exception as unsaf operation
	*/
	public void BeginDraw(Component _parent,Graphics _g) throws Exception {
		super.BeginDraw(_parent, _g);
		if(lock)throw new Exception("unsafe operation : locking components");
		lock = true;
		append.clear();
		erase.clear();
		for(Component c : this) {
			if(c.IsVisible())c.BeginDraw(this,_g);
		}
		lock = false;
		for(Component i : erase)RemoveComponent(i);
		for(Component i : append)AddChild(i);
	}
	//!ends drawing all child components 
	/*!
		\param _parent the parent component object that handles this
	//!ends updating all child components 
	/*!
		\param _parent the parent component object that handles this
		\param _g GDI object that related with the screen that you want to draw in
		\excetion Exception if this ComponentContainer locks child components, this method will throw the Exception as unsaf operation
	*/
	public void EndDraw(Component _parent,Graphics _g) throws Exception {
		super.EndDraw(_parent, _g);
		if(lock)throw new Exception("unsafe operation : locking components");
		lock = true;
		append.clear();
		erase.clear();
		for(Component c : this) {
			if(c.IsVisible())c.EndDraw(this,_g);
		}
		lock = false;
		for(Component i : erase)RemoveComponent(i);
		for(Component i : append)AddChild(i);
	}
	//!begins clicking all child components 
	/*!
		\param _parent the parent component object that handles this
		\param _x x location of mouse cursor
		\param _y y location of mouse cursor
		\param _mouseButton status of mouse buttons
		\excetion Exception if this ComponentContainer locks child components, this method will throw the Exception as unsaf operation
	*/
	public void BeginClick(Component _parent,double _x,double _y,Map<Integer,Boolean> _mouseButton) throws Exception {
		super.BeginClick(_parent, _x, _y, _mouseButton);
		if(lock)throw new Exception("unsafe operation : locking components");
		lock = true;
		append.clear();
		erase.clear();
		for(Component c : this) {
			if(c.Clickable() && c.Intersect(_x, _y))c.BeginClick(this, _x,_y,_mouseButton);
		}
		lock = false;
		for(Component i : erase)RemoveComponent(i);
		for(Component i : append)AddChild(i);
	}
	//!ends clicking all child components 
	/*!
		\param _parent the parent component object that handles this
		\param _x x location of mouse cursor
		\param _y y location of mouse cursor
		\param _mouseButton status of mouse buttons
		\excetion Exception if this ComponentContainer locks child components, this method will throw the Exception as unsaf operation
	*/
	public void EndClick(Component _parent,double _x,double _y,Map<Integer,Boolean> _mouseButton) throws Exception {
		super.EndClick(_parent, _x, _y, _mouseButton);
		if(lock)throw new Exception("unsafe operation : locking components");
		lock = true;
		append.clear();
		erase.clear();
		for(Component c : this) {
			if(c.Clickable())c.EndClick(this, _x,_y,_mouseButton);
		}
		lock = false;
		for(Component i : erase)RemoveComponent(i);
		for(Component i : append)AddChild(i);
	}
	//!begins keyboard downing for all child components
	/*!
		\param _parent the parent component that handles all child components
		\param _vkCode status of keyboard pressing
		\exception Exception if this object plus locking, true. otherwise, false.
	*/
	public void BeginKeyDown(Component _parent,Map<Integer,Boolean> _vkCode) throws Exception {
		super.BeginKeyDown(_parent, _vkCode);
		if(lock)throw new Exception("unsafe operation : locking components");
		lock = true;
		append.clear();
		erase.clear();
		for(Component c : this) {
			if(c.IsKeyEnable())c.BeginKeyDown(this,_vkCode);
		}
		lock = false;
		for(Component i : erase)RemoveComponent(i);
		for(Component i : append)AddChild(i);
	}
	//!ends keyboard downing for all child components
	/*!
		\param _parent the parent component that handles all child components
		\param _vkCode status of keyboard pressing
		\exception Exception if this object plus locking, true. otherwise, false.
	*/
	public void EndKeyDown(Component _parent,Map<Integer,Boolean> _vkCode) throws Exception {
		super.EndKeyDown(_parent, _vkCode);
		if(lock)throw new Exception("unsafe operation : locking components");
		lock = true;
		append.clear();
		erase.clear();
		for(Component c : this) {
			if(c.IsKeyEnable())c.EndKeyDown(this,_vkCode);
		}
		lock = false;
		for(Component i : erase)RemoveComponent(i);
		for(Component i : append)AddChild(i);
	}
	
	@Override
	//!get iterator for all child components
	/*!
		\returns th iterator objects that accesses all child components  sequencilly
	*/
	public Iterator<Component> iterator() {
		// TODO Auto-generated method stub
		return GetChilds().iterator();
	}
	
}
