package GameScenes;

import java.awt.Graphics;
import java.util.*;
import GameComponents.*;

//!universal scene object
/*!
 * \author n.ryouta
 */
public class Scene {
	//!the handler of scene event
	/*!
	 * \author n.ryouta
	 */
	public interface Listener {
		//!begins updating this scene
		/*!
		 * \param _sender the object calls this method
		 */
		void BeginUpdate(Scene _sender);

		//!ends updating this scene
		/*!
		 * \param _sender the object calls this method
		 */
		void EndUpdate(Scene _sender);

		//!begins drawing this scene
		/*!
		 * \param _sender the object calls this method
		 * \param _g the GDI object
		 */
		void BeginDraw(Scene _sender,Graphics _g);
		//!ends drawing this scene
		/*!
		 * \param _sender the object calls this method
		 * \param _g the GDI object
		 */
		void EndDraw(Scene _sender,Graphics _g);
	}
	private List<Listener> listeners =
			new ArrayList<Listener>();
	
	//!add handler of the scene
	/*!
	 * \param _listener the handler object that called from this
	 * \exception IllegalArgumentException _listener is null
	 */
	public void AddSceneListener(Listener _listener)throws IllegalArgumentException {
		if(!(_listener!=null))throw new IllegalArgumentException("Scene.AddSceneListener _listener is null");
		listeners.add(_listener);
	}
	
	//!notify this scene begins updating
	/*!
	 */
	protected void NotifyBeginUpdate(){
		for(Listener i : listeners){
			i.BeginUpdate(this);
		}
	}
	
	//!notify this scene ends updating
	/*!
	 */
	protected void NotifyEndUpdate() {
		for(Listener i : listeners) {
			i.EndUpdate(this);
		}
	}
	
	//!notify begins drawing
	/*!
	 * \param _g the GDI object
	 */
	protected void NotifyBeginDraw(Graphics _g) {
		for(Listener i : listeners) {
			i.BeginDraw(this, _g);
		}
	}
	
	//!notify ends drawing
	/*!
	 * \param _g the GDI
	 */
	protected void NotifyEndDraw(Graphics _g) {
		for(Listener i : listeners) {
			i.EndDraw(this, _g);
		}
	}
	
	//!visitor object 
	/*!
	 * \author n.ryouta
	 */
	public interface Visitor {
		//!visit to myself
		/*!
		 * \param _this myself
		 */
		void Visit(Scene _this);
		
		//!visit my member of ComponentContianer
		/*!
		 * \param _container the ComponentContainer that contains all components in scene 
		 */
		void Visit(ComponentContainer _container);
	}
	
	//!accept visitor extension
	/*!
	 * \param _visitor the visitor object
	 * \exception IllegalArgumentException _visitor is null
	 */
	public void Accept(Visitor _visitor)throws IllegalArgumentException {
		if(!(_visitor!=null))throw new IllegalArgumentException("Scene.Accept _visitor is null");
		_visitor.Visit(this);
		_visitor.Visit(container);
	}
	
	private boolean lock = false;
	private boolean reserved = false;
	private ComponentContainer container = 
			new ComponentContainer();
	
	//!get parent ComponentContainer that is running in top level
	/*!
	 * \returns the component container of root
	 */
	public ComponentContainer GetComponentContainer(){
		return container;
	}
	
	//!remove all components from component container
	/*!
	 * discard root component container and make new component container
	 * \warning if this Scene is locking, this method reserve to reset component container. 
	 */
	public void ResetComponentContainer() {
		if(!lock) {
			container = new ComponentContainer();
		}else {
			reserved = true;
		}
	}
	
	//!be notified that this scene begins playing 
	/*!
	 * be notified this scene begins playing
	 * \see SceneContainer
	 */
	public void PlayThisScene(SceneContainer _sender) {}
	
	//!be noified that this scene ends playing
	/*!
	 * be notifid this scene ends playing
	 * \see SceneContainer
	 */
	public void StopThisScene(SceneContainer _sender) {}
	
	//!update this scene
	/*!
	 * 
	 */
	public void Update() throws Exception {
		NotifyBeginUpdate();
		InternalUpdate();
		NotifyEndUpdate();
	}
	//!draw this scene
	/*!
	 */
	public void Draw(Graphics _g) throws Exception{
		NotifyBeginDraw(_g);
		InternalDraw(_g);
		NotifyEndDraw(_g);
	}
	//!internal update while the event such as begin and end
	/*!
	 * \exception Exception if this scene is locking the component container
	 * \warning this method provides the value of null as argument to ComponentContainer.BeginUpdate, ComponentContianer.EndUpdate
	 * \warning this method locks the ComponentContainer while ComponentContainer.BeginUpdate and ComponentContainer.EndUpdate
	 */
	protected void InternalUpdate() throws Exception{
		if(lock)throw new Exception("Scene.InternalUpdate unsafe operation : locking container");
		
		lock = true;
		reserved = false;
		container.BeginUpdate(null);
		container.EndUpdate(null);
		if(reserved) {
			container = new ComponentContainer();
		}
		lock = false;
		
		
	}
	//!internal draw while the event such as begin and end
	/*!
	 * \exception Exception if this scene is locking the component container
	 * \warning this method provides the value of null as argument to ComponentContainer.BeginDraw, ComponentContianer.EndDraw
	 * \warning this method locks the ComponentContainer while ComponentContainer.BeginDraw and ComponentContainer.EndDraw
	 */
	protected void InternalDraw(Graphics _g) throws Exception{
		if(lock)throw new Exception("Scene.InternalDraw unsafe operation : locking container");
		lock = true;
		reserved = false;
		container.BeginDraw(null, _g);
		container.EndDraw(null, _g);
		if(reserved) {
			container = new ComponentContainer();
		}
		lock = false;
	}
}
