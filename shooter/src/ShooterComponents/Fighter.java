package ShooterComponents;

import GameComponents.Circle;
import GameComponents.CollisionContainer;
import GameComponents.Collisionable;
import GameComponents.ComponentContainer;
import GameComponents.Rect;
import java.util.*;

//!the fighter character that is shooter to destroy other fighter 
/*!
 * \author n.ryouta
 */
public class Fighter extends LifeCharacter implements Bullet.Listener {
	//!the handler about Fighter
	/*!
	 * \author n.ryouta
	 */
	public interface Listener {
		//!be notified the Fighter begins shot
		/*!
		 * \param _sender the object calls this method
		 * \param _e the bullet was shot by sender
		 */
		void BeginShot(Fighter _sender,Bullet _e);
		//!be notified the Fighter failed to shot the bullet
		/*!
		 * \param _sender the object calls this method
		 */
		void FailedShot(Fighter _sender);
		
		//!be notified the Fighter collide with other object
		/*!
		 * \param _sender the object calls this method
		 * \param _container the CollisionContainer handles collision events
		 * \param _e the Bullet object collided with Fighter
		 */
		void BeginCollision(Fighter _sender,CollisionContainer<Rect,Circle> _contianer,Bullet _e);

		//!be notified the Fighter ends colliding with other object
		/*!
		 * \param _sender the object calls this method
		 * \param _e the Bullet object collided with Fighter
		 */
		void EndCollision(Fighter _sender,Bullet _e);

		//!be notified the Fighter collide with other object
		/*!
		 * \param _sender the object calls this method
		 * \param _container the CollisionContainer handles collision events
		 * \param _e the Fighter object collided with Fighter
		 */
		void BeginCollision(Fighter _sender,CollisionContainer<Rect,Circle> _contianer,Fighter _e);
		//!be notified the Fighter ends colliding with other object
		/*!
		 * \param _sender the object calls this method
		 * \param _e the Fighter object collided with Fighter
		 */
		void EndCollision(Fighter _sender,Fighter _e);
	}
	private List<Listener> listeners = 
			new ArrayList<Listener>();
	
	//!add handler of Fighter
	/*!
	 * \param _listener the handler
	 * \warning if this object is locking listeners, you must not call this method.
	 */
	public void AddFighterListener(Listener _listener)throws IllegalArgumentException {
		if(!(_listener!=null))throw new IllegalArgumentException("Fighter.AddFighterListener _listener is null");
		listeners.add(_listener);
	}
	//!remove handler from Fighter
	/*!
	 * \param _listener the handler that you want to remove from this
	 * \warning if this object is locking listeners, you must not call this method.
	 */
	public boolean RemoveFighterListener(Listener _listener){
		if(_listener==null)return false;
		return listeners.remove(_listener);
	}
	//!notify this object begins shot
	/*!
	 * \param _e Bullet object will be enabled
	 */
	protected void NotifyBeginShot(Bullet _e) {
		for(Listener i : listeners) {
			i.BeginShot(this, _e);
		}
	}
	
	//!notify this object failed to shot
	/*!
	 */
	protected void NotifyFailedShot() {
		for(Listener i : listeners) {
			i.FailedShot(this);
		}
	}
	
	//!notify this object begins collide with Bullet object
	/*!
	 * \param _container the CollisionContainer handles this and Bullet of _e
	 * \param _e the Bullet object colliding with me
	 */
	protected void NotifyBeginCollision(CollisionContainer<Rect,Circle> _container,Bullet _e) {
		for(Listener i : listeners) {
			i.BeginCollision(this,_container,_e);
		}
	}

	//!notify this object ends collide with Bullet object
	/*!
	 * \param _e the Bullet object colliding with me
	 */
	protected void NotifyEndCollision(Bullet _e) {
		for(Listener i : listeners) {
			i.EndCollision(this,_e);
		}
	}
	//!notify this object begins collide with Fighter object
	/*!
	 * \param _container the CollisionContainer handles this and Bullet of _e
	 * \param _e the Fighter object colliding with me
	 */
	protected void NotifyBeginCollision(CollisionContainer<Rect,Circle> _container,Fighter _e) {
		for(Listener i : listeners) {
			i.BeginCollision(this,_container,_e);
		}
	}

	//!notify this object ends collide with Fighter object
	/*!
	 * \param _e the Fighter object colliding with me
	 */
	protected void NotifyEndCollision(Fighter _e) {
		for(Listener i : listeners) {
			i.EndCollision(this,_e);
		}
	}
				
	//!the shot definition by visitor
	/*!
	 * the Fighter requires the method that is shot Bullets
	 * \author n.ryouta
	 */
	public interface ShotVisitor {
		//!visit the member of Fighter
		/*!
		 * \param _this the Fighter object
		 * \param _bullets the available Bullets list
		 * \param _rx the relative x position of bullet
		 * \param _ry the relative y position of bullet
		 * \param _dstShotBulletsResult the container should contains all shout bullets
		 * \warning you should implement this method you dequeue from _bullets and call BeginShot from Bullet and store _dstShotBulletsResult
		 */
		void Visit(Fighter _this,Queue<Bullet> _bullets,double _rx,double _ry,List<Bullet> _dstShotBulletsResult);
	}
	//!abstract factory of Bullet for Fighter
	/*!
	 * \author n.ryouta
	 */
	public interface BulletFactory {
		//!create bullet for Fighter
		/*!
		 * \param _this the Fighter object
		 */
		Bullet CreateBullet(Fighter _this);
	}
	private List<Bullet> bulletSet =
			new ArrayList<Bullet>();
	
	//!get all Bullets
	/*!
	 * \returns the iterable object that contains all Bullets 
	 */
	public Iterable<Bullet> GetBulletSet(){
		return bulletSet;
	}
	private Queue<Bullet> bullets = 
			new java.util.ArrayDeque<Bullet>();
	
	//!register bullets into this Fighter
	/*!
	 * this method makes _factory creates bullets that is number of _maxBullets.
	 * if _container is null, all created Bullets will stored into this Fighter as child components. 
	 * \param _factory the BulletFactory object for creating Bullets
	 * \param _maxBullet the count that is _factory creates
	 * \param _container ComponentContianer will be stored Bullets as child components. this is allowed null.
	 * \exception IllegalArgumentException _factory is null
	 * \exception IllegalArgumentException _maxBullet is smaller equal than 0
	 */
	public void RegisterBullet(BulletFactory _factory,int _maxBullet,ComponentContainer _container)throws Exception {
		if(!(_factory!=null))throw new IllegalArgumentException("Fighter.RegisterBullet _factory is null");
		if(!(_maxBullet > 0))throw new IllegalArgumentException("Figter.RegisterBullet _maxBullet must be greater than 0");
		for(int i = 0 ; i < _maxBullet;i++) {
			Bullet b = _factory.CreateBullet(this);
			if(!(b!=null))throw new Exception("Fighter.RegsterBullet failed to make bullet");
			bulletSet.add(b);
			bullets.add(b);
			if(_container!=null) {
				_container.AddChild(b);
			}else {
				AddChild(b);
			}
		}
	}
	
	private List<Bullet> shotTemp = new ArrayList<Bullet>();
	//!shot bullets
	/*!
	 * this method maeks _shotVisitor shot some Bullets.
	 * if this Fighter of Bullets queue is empty, this method notify FailedShot
	 * \param _shotVisitor the visitor object 
	 * \param _rx relative x position from Fighter object
	 * \param _ry relative y position from Fighter object
	 * \exception IllegalArgumentException _shotVisitor is null
	 * \see NotifyFailedShot
	 */
	public void Shot(
			ShotVisitor _shotVisitor,double _rx,double _ry) throws Exception {
		if(!(_shotVisitor!=null))throw new IllegalArgumentException("Fighter.Shot _shotVisitor is null");
		if(bullets.isEmpty()) {
			NotifyFailedShot();
			return ;
		}
		shotTemp.clear();
		_shotVisitor.Visit(this, bullets, _rx, _ry, shotTemp);
		
		
		for(Bullet b : shotTemp) {
			b.AddBulletListener(this);
			NotifyBeginShot(b);
		}
	}
	
	//!initialize this object
	/*!
	 * initialize first collision area as Component Bounds
	 * \see Component.GetBounds
	 */
	public Fighter() {
		SetCollisionAreaT(GetBounds());
	}
	//!dispatch the event into Fighter or Bullet
	/*!
	 * this method dispatches the event into Bullet or Fighter by using type subclass cast
	 * \param _sender the CollisionContainer contains Bullet and Fighter
	 * \param _e the collisionable object
	 * \see BeginCollisionWithFighter
	 * \see BeginCollisionWithBullet
	 */
	protected void BeginCollision(CollisionContainer<Rect,Circle> _sender,Collisionable<Rect,Circle> _e){
		if(_e instanceof Bullet) {
			BeginCollisionWithBullet(_sender,(Bullet)_e);
		}else if(_e instanceof Fighter) {
			BeginCollisionWithFighter(_sender,(Fighter)_e);
			
		}
	}
	//!dispatch the event into Fighter or Bullet
	/*!
	 * \param _e Collisionable object
	 * \see EndCollisionWithFighter
	 * \see EndCollisionWithBullet
	 */
	protected void EndCollision(Collisionable<Rect,Circle> _e){
		if(_e instanceof Bullet) {
			EndCollisionWithBullet((Bullet)_e);
		}else if(_e instanceof Fighter) {
			EndCollisionWithFighter((Fighter)_e);
		}
	}
	
	//!the template method that this object begins collision with fighter
	/*!
	 * \param _container the ComponentContianer object contains this 
	 * \param _e Fighter object 
	 */
	protected void BeginCollisionWithFighter(CollisionContainer<Rect,Circle> _container,Fighter _e) {
		DecreaseLife(_e.GetLife());
		NotifyBeginCollision(_container,_e);
	}
	//!the template method that this object ends collision with fighter
	/*!
	 * \param _container the ComponentContianer object contains this 
	 * \param _e Fighter object 
	 */
	protected void EndCollisionWithFighter(Fighter _e) {
		NotifyEndCollision(_e);
	}

	//!the template method that this object begins collision with bullet
	/*!
	 * \param _container the ComponentContianer object contains this 
	 * \param _e Fighter object 
	 */
	protected void BeginCollisionWithBullet(CollisionContainer<Rect,Circle> _container,Bullet _e) {
		DecreaseLife(_e.GetLife());
		NotifyBeginCollision(_container,_e);
	}
	//!the template method that this object ends collision with bullet
	/*!
	 * \param _container the ComponentContianer object contains this 
	 * \param _e Fighter object 
	 */
	protected void EndCollisionWithBullet(Bullet _e) {
		NotifyEndCollision(_e);
	}
	
	@Override
	//!empty implementation
	/*!
	 */
	public void BeginShot(Bullet _sender, Fighter _e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	//!be notified the Bullet object ends shot 
	/*!
	 * this method re-enqueue the Bullet of _sender into Bullet queue of this
	 * \param _sender the object calls this method and will be enqueued into this 
	 */
	public void EndShot(Bullet _sender) {
		// TODO Auto-generated method stub
		if(_sender.RemoveBulletListener(this)) {
			bullets.add(_sender);
		}
	}
	@Override
	//!empty implementation
	/*!
	 */
	public void BeginCollision(Bullet _sender, CollisionContainer<Rect, Circle> _container, Fighter _e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	//!empty implementation
	/*!
	 */
	public void EndCollision(Bullet _sender, Fighter _e) {
		// TODO Auto-generated method stub
		
	}
}
