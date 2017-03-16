package ShooterComponents;

import GameComponents.*;
import java.util.*;

//!bullet character
/*!
 * \author n.ryouta
 */
public class Bullet extends LifeCharacter{
	//!handler of bullet
	/*!
	 * \author n.ryouta
	 */
	public interface Listener {
		//!be notified the Bullet shot
		/*!
		 * \param _sender the object calls this method
		 * \param _e the fighter shoots it
		 */
		void BeginShot(Bullet _sender,Fighter _e) ;
		
		//!be notified the Bullet terminates shot
		/*!
		 * \param _sender the object calls this method
		 */
		void EndShot(Bullet _sender);
		
		//!be notified begining collision 
		/*!
		 * \param _sender the object calls this method
		 * \param _e the collision object
		 */
		void BeginCollision(Bullet _sender,CollisionContainer<Rect,Circle> _container,Fighter _e);
		//!be notified ending collision
		/*!
		 * \param _sender the object calls this method
		 */
		void EndCollision(Bullet _sender,Fighter _e);
	}
	private List<Listener> listeners =
			new ArrayList<Listener>();
	private List<Listener> append = new ArrayList<Listener>();
	private List<Listener> erase = new ArrayList<Listener>();
	private boolean lock = false;
	
	//!add listener about bullet
	/*!
	 * \param _listener the handler
	 * \exception IllegalArgumentException _listener is null
	 * \warning the listener will be added after unlocking, if this Bullet is locking listeners
	 */
	public void AddBulletListener(Listener _listener)throws IllegalArgumentException {
		if(!(_listener!=null))throw new IllegalArgumentException("Bullet.AddBulletListener _listener is null");
		if(!lock) {
			listeners.add(_listener);
		}else {
			append.add(_listener);
		}
	}
	//!remove listener bullet
	/*!
	 * \warning the listener will be removed after unlocking, if this Bullet is locking listeners
	 *
	 */
	public boolean RemoveBulletListener(Listener _listener) {
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
	//!notify this begins shot
	/*!
	 * \param _e the fighter shot this
	 */
	protected void NotifyBeginShot(Fighter _e){
		lock = true;
		append.clear();
		erase.clear();
		for(Listener i : listeners) {
			i.BeginShot(this, _e);
		}
		lock = false;
		listeners.removeAll(erase);
		listeners.addAll(append);
	}
	

	//!notify this ends shot
	/*!
	 * \param _e the fighter shot this
	 */
	protected void NotifyEndShot() {
		lock = true;
		append.clear();
		erase.clear();
		for(Listener i : listeners) {
			i.EndShot(this);
		}
		lock = false;
		listeners.removeAll(erase);
		listeners.addAll(append);
	}
	//!notify this begins collision with other object
	/*!
	 * \param _container the CollisionContainer manages this
	 * \param _e the collision object 
	 */
	protected void NotifyBeginCollision(CollisionContainer<Rect,Circle> _container,Fighter _e) {
		lock = true;
		append.clear();
		erase.clear();
		for(Listener i : listeners) {
			i.BeginCollision(this, _container, _e);
		}
		lock = false;
		listeners.removeAll(erase);
		listeners.addAll(append);
	}

	//!notify this ends collision with other object
	/*!
	 * \param _container the CollisionContainer manages this
	 */
	protected void NotifyEndCollision(Fighter _e) {
		lock = true;
		append.clear();
		erase.clear();
		for(Listener i : listeners ) {
			i.EndCollision(this, _e);
		}
		lock = false;
		listeners.removeAll(erase);
		listeners.addAll(append);
	}
	
	private Fighter owner;
	//!initialize 
	/*!
	 * sets visibility hidden and invalidating, and sets collision area as component bounds
	 * \see Component.GetBound()
	 */
	public Bullet() throws Exception {
		SetCollisionAreaT(GetBounds());
		SetVisible(false);
		SetValid(false);
	}
	//!get owner as the fighter
	/*!
	 * \returns the fighter shot myself
	 */
	public Fighter GetOwner(){return owner;}
	
	//!shot this bullet with relative position 
	/*!
	 * \param _sender the fighter shot me
	 * \param _rx relative x location
	 * \param _ry relative y location
	 */
	public void BeginShot(Fighter _sender,double _rx,double _ry)throws Exception {
		if(!(_sender!=null))throw new IllegalArgumentException("Bullet.Shot _sender is null");
		SetVisible(true);
		SetValid(true);
		SetCollisionable(true);
		Rect bounds = GetBounds();
		bounds.x = _sender.GetBounds().x + _rx;
		bounds.y = _sender.GetBounds().y+_ry;
		owner = _sender;
		NotifyBeginShot(_sender);
	}
	//!force this bullet terminate
	/*!
	 * 
	 */
	public void EndShot() throws Exception {
		SetVisible(false);
		SetValid(false);
		SetCollisionable(false);
		owner = null;
		NotifyEndShot();
	}
	
	//!notify the event that this begins collision with fighter
	/*!
	 * \param _container the CollisionContainer manages this
	 * \param _e the collision object
	 */
	protected void BeginCollisionWithFighter(CollisionContainer<Rect,Circle> _container,Fighter _e) {
		NotifyBeginCollision(_container,_e);
	}
	//!notify the event that this ends collision with fighter
	/*!
	 * \param _e the collision object
	 */
	protected void EndCollisionWithFighter(Fighter _e){
		NotifyEndCollision(_e);
	}
	//!dispatch the event into begin collision with Fighter
	/*!
	 * if the collision object of _e can be casted to Fighter, this method dispatches into BeginCollisionWithFighter 
	 * \param _sender the object calls this method
	 * \param _e the collision object
	 * \see BeginCollisionWithFighter
	 */
	protected void BeginCollision(CollisionContainer<Rect,Circle> _sender,Collisionable<Rect,Circle> _e){
		if(_e instanceof Fighter) {
			BeginCollisionWithFighter(_sender,(Fighter)_e);
		}
	}

	//!dispatch the event into end collision with Fighter
	/*!
	 * if the collision object of _e can be casted to Fighter, this method dispatches into EndCollisionWithFighter 
	 * \param _sender the object calls this method
	 * \see EndCollisionWithFighter
	 */
	protected void EndCollision(Collisionable<Rect,Circle> _e){
		if(_e instanceof Fighter) {
			EndCollisionWithFighter((Fighter)_e);
		}
	}
		
	private ShooterUpdater.BulletMove move;
	//!set the strategy that moves bullet
	/*!
	 * \param _move the strategy of bullet
	 */
	public void SetBulletMove(ShooterUpdater.BulletMove _move) {
		if(move!=null) {
			RemoveGameCharacterUpdater(move);
		}
		move = _move;
		AddGameCharacterUpdater(move);
	}
	//!get the strategy that moves this bullet
	/*!
	 * \returns the strategy that moves this bullet
	 */
	public ShooterUpdater.BulletMove GetBulletMove(){return move;}
}
