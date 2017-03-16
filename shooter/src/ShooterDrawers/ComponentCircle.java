package ShooterDrawers;

import java.awt.Color;
import java.awt.Graphics;

import GameComponents.*;
import ShooterComponents.*;

//!draws single circle at the GameCharacter position in the screen
/*!
 * \author n.ryouta
 */
public class ComponentCircle implements GameCharacterDrawer,IEffect{
	private Color color;
	private double radius;
	private double rx,ry;
	private boolean running = false;
	
	//!initialize circle info
	/*!
	 * \param _color the color of circle
	 * \param _radius the radius of circel
	 * \param _rx relative x position
	 * \param _ry relative y position
	 * \exception IllegalArgumentException _color is null
	 * \exception IllegalArgumentException _radius is smaller equal than 0
	 */
	public ComponentCircle(
			Color _color,
			double _radius,
			double _rx,double _ry
			)throws IllegalArgumentException {
		if(!(_color!=null))throw new IllegalArgumentException("ComponentCircle _color is null");
		if(!(_radius > 0))throw new IllegalArgumentException("ComponentCircle _radius must be greater than 0");
		color = _color;
		radius = _radius;
		rx = _rx;
		ry = _ry;
	}
	
	@Override
	//!draw the single circle in the screen
	/*!
	 * draws the single cirlce at the relative + center position of _sender bounds
	 * \param _sender target GameCharacter that is drawn the circle
	 * \param _g GDI
	 */
	public void BeginDraw(GameCharacter _sender, Graphics _g) {
		// TODO Auto-generated method stub
		if(!running)return;
		if(!(_sender!=null)) {
			System.out.println("ComponentCircle.BeginDraw _sender is null");
			return ;
		}
		Rect bounds = _sender.GetBounds();
		double cx = bounds.x+bounds.w/2.0+rx;
		double cy = bounds.y+bounds.h/2.0+ry;
		
		_g.setColor(color);
		_g.drawOval(
				(int)(cx-radius),
				(int)(cy-radius),
				(int)(2.0*radius),
				(int)(2.0*radius)
				);
	}

	@Override
	//!empty
	/*!
	 */
	public void EndDraw(GameCharacter _sender, Graphics _g) {
		// TODO Auto-generated method stub
		
	}

	@Override
	//!turn the visibility true
	/*!
	 */
	public void BeginPlay() {
		// TODO Auto-generated method stub
		if(running)return;
		running  = true;
	}

	@Override
	//!turn the visibility false
	/*!
	 */
	public void EndPlay() {
		// TODO Auto-generated method stub
		if(!running)return;
		running = false;
	}

}
