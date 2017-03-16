package ShooterDrawers;

import java.awt.Color;
import java.awt.Graphics;

import GameComponents.*;
import ShooterComponents.*;

//!draws rectangle at the GameCharacter
/*!
 * \author n.ryouta
 */
public class ComponentBound implements GameCharacterDrawer,IEffect {

	private Color color;
	private boolean fill;
	private boolean running = true;
	//!initialize this
	/*!
	 * \param _fill if you want to fill, true.
	 * \param _color the color of rectangle in the screen
	 * \exception IllegalArgumentException _color is null
	 */
	public ComponentBound(boolean _fill,Color _color)throws IllegalArgumentException {
		if(!(_color!=null))throw new IllegalArgumentException("ComponentBound _color is null");
		color = _color;
		fill = _fill;
	}
		
	
	@Override
	//!draw the rectangle at the _sender bounds in the screen
	/*!
	 * \param _sender the target object that has the bounds as rectangle
	 *\param _g GDI
	 */
	public void BeginDraw(GameCharacter _sender, Graphics _g) {
		// TODO Auto-generated method stub
		if(!running)return;
		if(_sender==null) {
			System.out.println("ComponentBound _sender is null");
			return ;
		}
		Rect bounds = _sender.GetBounds();
		_g.setColor(color);
		if(fill) {
			_g.fillRect(
					(int)bounds.x,(int)bounds.y,
					(int)bounds.w,(int)bounds.h
					);
		}else {
			_g.drawRect(
					(int)bounds.x,(int)bounds.y,
					(int)bounds.w,(int)bounds.h
					);
		}
	}

	@Override
	//!empty
	/*!
	 */
	public void EndDraw(GameCharacter _sender, Graphics _g) {
		// TODO Auto-generated method stub
		
	}


	@Override
	//!turn true the visibility of this 
	/*!
	 */
	public void BeginPlay() {
		// TODO Auto-generated method stub
		if(running)return;
		running = true;
		
	}


	@Override

	//!turn false the visibility of this 
	/*!
	 */
	public void EndPlay() {
		// TODO Auto-generated method stub
		if(!running)return;
		running = false;
	}

}
