package GameComponents;

//!generic collision area type object bridge
/*!
 * the object implemented with this requires area intersection method 
 * \author n.ryouta
 */
public interface Collisionable<T,U> {
	//!check this collision area is intersect with other one
	/*!
	 * geometric collision area intersection check must be prepared by you.
	 * this method can use GetCollisionAreaT or GetCollisionAreaU for checking intersection.
	 * \param _c the other collisionable object
	 * \returns is intersect with _c
	 * \see GetCollisionAreaT
	 * \see GetCollisionAreaU
	 */
	boolean Intersect(Collisionable<T,U> _c);
	
	//!get the collision area that is first type of T
	/*!
	 * get the geometric collision area of T, you dont have to implement this,
	 * because this method was not called from Intersect method certainly
	 * \returns collision area of T 
	 */
	T GetCollisionAreaT();

	//!get the collision area that is second type of U
	/*!
	 * get the geometric collision area of U, you dont have to implement this,
	 * because this method was not called from Intersect method certainly
	 * \returns collision area of U 
	 */
	U GetCollisionAreaU();
	
	//!will be notified the event that this object collision with other from CollisionContainer
	/*!
	 * implementing this method as event handler, you can get the notification from CollisionContainer
	 * 
	 */
	void CollisionWith(CollisionContainer<T,U> _sender,Collisionable<T,U> _e);
	
	//!get the flag that this object gets notification of CollisionWith
	/*!
	 * 
	 */
	boolean Collisionable();
}
