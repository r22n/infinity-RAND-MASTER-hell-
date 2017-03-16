package ShooterUpdater;

import ShooterComponents.*;
import GameComponents.*;

//!force the GameCharacter range into bounds as the rectangle type
/*!
 * \author n.ryouta
 */
public class RangeToBound implements GameCharacterUpdater {
	private Rect screen;
	//!initializer
	/*!
	 * \param _x x location of bounds
	 * \param _y y location of bounds
	 * \param _w width of bounds
	 * \param _h height of bounds
	 * 
	 */
	public RangeToBound(double _x,double _y,double _w,double _h) {
		screen = new Rect();
		screen.x = _x;
		screen.y = _y;
		screen.w = _w;
		screen.h = _h;
	}
	@Override
	//!empty
	/*!
	 */
	public void BeginUpdate(GameCharacter _sender) {
		// TODO Auto-generated method stub
		
	}

	@Override
	//!force the GameCharacter range into
	/*!
	 * \param _sender the GameCharacter was forced 
	 */
	public void EndUpdate(GameCharacter _sender) {
		// TODO Auto-generated method stub
		if(_sender==null) {
			System.out.println("RangeToBound.EndUpdate _sender is null");
			return ;
		}
		Rect bounds = _sender.GetBounds();
		if(bounds.x <= screen.x) {
			bounds.x = screen.x;
		}else if(screen.x+screen.w < bounds.x + bounds.w) {
			bounds.x = screen.x + screen.w - bounds.w;
		}
		if(bounds.y <= screen.y) {
			bounds.y = screen.y;
		}else if(screen.y + screen.h < bounds.y + bounds.h) {
			bounds.y = screen.y+screen.h - bounds.h;
		}

	}
		
}
