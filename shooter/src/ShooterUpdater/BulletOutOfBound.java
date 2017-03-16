package ShooterUpdater;

import ShooterComponents.*;
import GameComponents.*;

//!checks that Bullet object moved out 
/*!
 * \author n.ryouta
 */
public class BulletOutOfBound implements GameCharacterUpdater{
	private Rect bound;
	
	//!initialize
	/*!
	 * \param _x bound of x
	 * \param _y bound of y
	 * \param _w bound of width
	 * \param _h bound of height
	 */
	public BulletOutOfBound(double _x,double _y,double _w,double _h) {
		bound = new Rect();
		bound.x = _x;
		bound.y = _y;
		bound.w = _w;
		bound.h = _h;
	}
	@Override
	//!check the Bullet moved out from bounds and notify EndShot
	/*!
	 * \param _sender must be casted to Bullet
	 */
	public void BeginUpdate(GameCharacter _sender) {
		// TODO Auto-generated method stub
		if(!(_sender instanceof Bullet)) {
			System.out.println("BulletOutofBound.BeginUpdate _sender is not Bullet");
			return ;
		}
		Bullet sender = (Bullet)_sender;
		if(!bound.Intersect(sender.GetBounds())) {
			try {
				sender.EndShot();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	//!empty
	/*!
	 */
	public void EndUpdate(GameCharacter _sender) {
		// TODO Auto-generated method stub
		
	}

}
