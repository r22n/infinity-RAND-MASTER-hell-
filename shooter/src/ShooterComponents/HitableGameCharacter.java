package ShooterComponents;
import GameComponents.*;


//!the GameCharacter that was tuned into Collisionable of Rectangle
/*!
 * \author n.ryouta
 */
public class HitableGameCharacter extends CollisionableGameCharacter<Rect,Circle>{
	//!set bounds as rectangle
	/*!
	 * this method make this bounds set argument, if this has area object.
	 * circle area will be set the center location as argument x,y.
	 * \param _rc the new rectangle
	 */
	public void SetBounds(Rect _rc)throws IllegalArgumentException {
		super.SetBounds(_rc);
		Circle c = GetCollisionAreaU();
		Rect r = GetCollisionAreaT();
		if(c!=null){ 
			c.x = _rc.x;
			c.y = _rc.y;
		}
		if(r!=null) {
			r.Copy(_rc);
		}
	}
	
	//!check intersection with other
	/*!
	 * check intersection with other by using circle area.
	 * if this object and other have not circle, these will use rectangle.
	 * otherwise, returns false.
	 * \param _e the other object
	 */
	public boolean Intersect(Collisionable<Rect,Circle> _e) {
		if(_e==null) {
			System.out.println("HitableGameCharacter.Intersect _e is null");
			return false;
		}
		Circle _c = _e.GetCollisionAreaU();
		Rect _r = _e.GetCollisionAreaT();
		Circle c = GetCollisionAreaU();
		Rect r = GetCollisionAreaT();
		
		if(_c!=null&&c!=null) {
			return c.Intersect(_c);
		}else if(_r!=null && r!=null) {
			return r.Intersect(_r);
		}else {
			return false;
		}
	}
}
