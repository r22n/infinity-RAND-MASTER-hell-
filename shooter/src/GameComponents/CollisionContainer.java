package GameComponents;

import java.util.*;

//!the container of Collisionable type manages notifications to Collision
/*!
 * this container divides collision group of A and B,
 * and check collision, notify.
 * the check will be running with each group.
 * so collision objects that is in same group will not be notified.
 * \author n.ryouta
 */
public class CollisionContainer<T,U> extends Component{
	private List<Collisionable<T,U>> alist = 
			new ArrayList<Collisionable<T,U>>();
	private List<Collisionable<T,U>> blist =
			new ArrayList<Collisionable<T,U>>();
	private boolean lock = false;
	private List<Collisionable<T,U>> 
			addA = new ArrayList<Collisionable<T,U>>(),
			removeA = new ArrayList<Collisionable<T,U>>(),
			addB = new ArrayList<Collisionable<T,U>>(),
			removeB = new ArrayList<Collisionable<T,U>>();
	
	//!add Collisionable object to group A
	/*!
	 * add Collisionable object to group A
	 * \param _collision the Collisionable object 
	 * \exception IllegalArgumentException _collision is null
	 * \warning the object will be added after unlocking when the CheckCollision method ends, if this locking.
	 */
	public void AddListA(Collisionable<T,U> _collision) throws IllegalArgumentException {
		if(!(_collision!=null))throw new IllegalArgumentException("CollisionContainer.AddListA _collision is null");
		if(!lock) {
			alist.add(_collision);
		}else {
			addA.add(_collision);
		}
	}		
	//!remove Collisionable object from group A
	/*!
	 * remove Collisionable object from group A
	 * \param _collision the Collisionable object 
	 * \warning the object will be removed after unlocking when the CheckCollision method ends, if this locking.
	 */	
	public boolean RemoveFromA(Collisionable<T,U> _collision) {
		if(_collision==null)return false;
		if(!lock) {
			return alist.remove(_collision);
		}else {
			boolean result = alist.contains(_collision);
			if(result) {
				removeA.add(_collision);
			}
			return result;
		}
	}
	//!add Collisionable object to group B
	/*!
	 * add Collisionable object to group B
	 * \param _collision the Collisionable object 
	 * \exception IllegalArgumentException _collision is null
	 * \warning the object will be added after unlocking when the CheckCollision method ends, if this locking.
	 */
	public void AddListB(Collisionable<T,U> _collision)throws IllegalArgumentException {
		if(!(_collision!=null))throw new IllegalArgumentException("CollisionContainer.AddListB _collision is null");
		if(!lock) {
			blist.add(_collision);
		}else {
			addB.add(_collision);
		}
	}
	//!remove Collisionable object from group B
		/*!
		 * remove Collisionable object from group B
		 * \param _collision the Collisionable object 
		 * \warning the object will be removed after unlocking when the CheckCollision method ends, if this locking.
		 */	
		
	public boolean RemoveFromB(Collisionable<T,U> _collision) {
		if(_collision==null)return false;
		if(!lock) {
			return blist.remove(_collision);
		}else {
			boolean result = blist.contains(_collision);
			if(result) {
				removeB.add(_collision);
			}
			return result;
		}
	}
	//!initialize this object for checking first priority to notify at first of loop
	/*!
	 * this method sets the priority -1 of this
	 */
	public CollisionContainer() {
		SetPriority(-1);
	}
	
	//!check and notify collision for Collisionable objects of both groups
	/*!
	 * \warning this method locks the container of both Collisionable groups
	 * \exception Exception this container being locked with CheckCollision
	 */
	public void CheckCollision()throws Exception {
		if(lock)throw new Exception("CollisionContainer.CheckCollision unsafe operation: this container is locking");
		lock = true;
		removeA.clear();
		addA.clear();
		removeB.clear();
		addB.clear();
		for(Collisionable<T,U> a : alist) {
			for(Collisionable<T,U> b : blist) {
				if(a.Collisionable() && b.Collisionable() && a.Intersect(b)) {
					a.CollisionWith(this, b);
					b.CollisionWith(this, a);
				}
			}
		}
		alist.removeAll(removeA);
		blist.removeAll(removeB);
		alist.addAll(addA);
		blist.addAll(addB);
		lock = false;

	}
	//!call CheckCollision method after super BeginUpdate with given arguments
	/*!
	 * \exception Exception see CheckCollision
	 * \see CheckCollision
	 * \param _parent the parent component
	 */
	public void BeginUpdate(Component _parent) throws Exception {
		super.BeginUpdate(_parent);
		CheckCollision();
	}
}
