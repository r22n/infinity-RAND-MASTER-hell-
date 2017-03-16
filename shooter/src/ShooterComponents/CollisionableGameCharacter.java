package ShooterComponents;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import GameComponents.CollisionContainer;
import GameComponents.Collisionable;
import GameComponents.Component;

//!the game character that can collide with other object
/*!
 * \author n.ryouta
 */
public class CollisionableGameCharacter<T,U> 
	extends GameCharacter
	implements Collisionable<T,U>{
	//!the listener object that receive events from CollisionableGameCharacter
	/*!
	 * \author n.ryouta
	 */
	public interface Listener<T,U> {
		//!be notified the CollisionableGameCharacter changes collision area of T
		/*!
		 * \param _sender the object calls this method
		 * \param _t new area of collision
		 */
		void OnSetAreaT(CollisionableGameCharacter<T,U> _sender,T _t);
		//!be notified the CollisionableGameCharacter changes collision area of U
		/*!
		 * \param _sender the object calls this method
		 * \param _u new area of collision
		 */
		void OnSetAreaU(CollisionableGameCharacter<T,U> _sender,U _u);
	}
	private List<Listener<T,U>> listeners = 
			new ArrayList<Listener<T,U>>();
	
	//!add handler of CollisionableGameCharacter
	/*!
	 * \param _listener the handler will be notified
	 * \exception IllegalArgumentException _listener is null 
	 * \warning you must not call this method while notifying from this object
	 * \see NotifySetAreaT
	 * \see NotifySetAreaU 
	 */
	public void AddCollisionableGameCharacterListener(Listener<T,U> _listener)throws IllegalArgumentException {
		if(!(_listener!=null))throw new IllegalArgumentException("CollisionableGameCharacter.AddGameCharacterListener _listener is null");
		listeners.add(_listener);
	}
	
	//!remove handler from this
	/*!
	 * \param _listener the handler will be removed
	 * \returns if this CollisionableGameCharacter has the listener, true. otherwise, false.
	 */
	public boolean RemoveCollisionableGameCharacterListener(Listener<T,U> _listener) {
		if(_listener==null)return false;
		return listeners.remove(_listener);
	}
	
	//!notify the event that this object sets new area for checking intersection with other 
	/*!
	 * \param _t new area
	 */
	protected void NotifySetAreaT(T _t){
		for(Listener<T,U> i : listeners){
			i.OnSetAreaT(this, _t);
		}
	}
	//!notify the event that this object sets new area for checking intersection with other 
	/*!
	 * \param _u new area
	 */
	protected void NotifySetAreaU(U _u) {
		for(Listener<T,U> i : listeners) {
			i.OnSetAreaU(this, _u);
		}
	}
		
				
	private boolean collisionable = false;
	private T t = null;
	private U u = null;
	private HashSet<Collisionable<T,U>> current = 
			new HashSet<Collisionable<T,U>>();
	private HashSet<Collisionable<T,U>> prev = 
			new HashSet<Collisionable<T,U>>();
	
	//!set the first type area
	/*!
	 * \param new area of T
	 */
	public void SetCollisionAreaT(T _t) {
		t = _t;
		NotifySetAreaT(_t);
	}
	//!set the first type area
	/*!
	 * \param new area of U
	 */
	public void SetCollisionAreaU(U _u){
		u = _u;
		NotifySetAreaU(_u);
	}
	
	@Override
	//!the template that checks intersection with other
	/*!
	 * you must implement this method for checking intersection
	 * \param _c other collision object
	 * \warning this method returns false
	 */
	public boolean Intersect(Collisionable<T, U> _c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	//!get the first type collision area
	/*!
	 * \returns first type collision area
	 */
	public T GetCollisionAreaT() {
		// TODO Auto-generated method stub
		return t;
	}

	@Override
	//!get the second type collision area
	/*!
	 * \returns second type collision area
	 */
	public U GetCollisionAreaU() {
		// TODO Auto-generated method stub
		return u;
	}
	
	@Override
	//!check collision with other and notify the event that is BeginCollision to this template
	/*!
	 * this method checks the collision object set of prev frame for notifying BeginCollision
	 * \param _sender the object calls this method
	 * \param _e the other object
	 */
	public void CollisionWith(CollisionContainer<T, U> _sender, Collisionable<T, U> _e) {
		// TODO Auto-generated method stub
		
		if(!current.contains(_e))current.add(_e);
		
		if(!prev.contains(_e)) {
			BeginCollision(_sender,_e);
		}
	}
	//!check end collision with prev frame collision set 
	/*!
	 * \param _parent the parent component that contains this
	 */
	public void EndUpdate(Component _parent) throws Exception{
		super.EndUpdate(_parent);
		for(Collisionable<T,U> i : prev) {
			if(!current.contains(i)) {
				EndCollision(i);
			}
		}
		prev.clear();
		prev.addAll(current);
		current.clear();
	}
	//!template method that handles this object begins collision
	/*!
	 * \param _sender the object calls this method
	 * \param _e the object collides with this
	 */
	protected void BeginCollision(CollisionContainer<T,U> _sender,Collisionable<T,U> _e){}
	
	//!template method that handles this object ends collision
	/*!
	 * \param _e the object ends collision with this
	 */
	protected void EndCollision(Collisionable<T,U> _e){}
	@Override
	//!get this object can collide with
	/*!
	 * \returns if this object can collide, true. otherwise, false.
	 */
	public boolean Collisionable() {
		// TODO Auto-generated method stub
		return collisionable;
	}
	//!set this object can collide with
	/*!
	 * \param _collisionable the flag that is this object can collide 
	 */
	public void SetCollisionable(boolean _collisionable) {
		collisionable = _collisionable;
	}
}
