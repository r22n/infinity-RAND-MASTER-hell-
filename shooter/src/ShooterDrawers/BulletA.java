package ShooterDrawers;

import java.awt.Color;
import java.awt.Graphics;

import GameComponents.Rect;
import ShooterComponents.*;

//!draws circle for Bullet
/*!
 * \author n.ryouta
 */
public class BulletA implements GameCharacterDrawer,IEffect{
	
	private double rx,ry;
	private double size;
	private Color color;
	private boolean fill;
	private boolean running = true;
	
	//!initialize this object
	/*!
	 * \param _fill if you fill it, true.
	 * \param _size size of bullet in the screen
	 * \param _rx relative x position
	 * \param _ry relative y position
	 * \param _color the color of Bullet 
	 * \exception IllegalArgumentException _color is null
	 * \exception IllegalArgumentException _size is smaller equal than 0
	 */
	public BulletA(boolean _fill,double _size,double _rx,double _ry,Color _color) throws IllegalArgumentException {
		if(!(_color!=null))throw new IllegalArgumentException("BulletA _color is null");
		if(!(_size > 0))throw new IllegalArgumentException("BulletA _size must be greater than 0");
		rx = _rx;
		ry = _ry;
		size = _size;
		color = _color;
		fill=  _fill;
	}
	
	
	@Override
	//!draw the bullet in the screen at center position of _sender's bounds
	/*!
	 * this method draws the circle as Bullet in the screen at center position of _sender'd bounds.
	 * \param _sender the Bullet object that was expected
	 * \paran _g GDI
	 */
	public void BeginDraw(GameCharacter _sender, Graphics _g) {
		// TODO Auto-generated method stub
		if(!running)return;
		
		if(_sender==null) {
			System.out.println("BulletA _sender is null");
			return ;
		}
		Rect bounds = _sender.GetBounds();
		double cx = bounds.x + bounds.w/2.0+rx;
		double cy = bounds.y + bounds.h/2.0+ry;
		
		_g.setColor(color);
		if(fill) {
			_g.fillOval(
					(int)(cx-size/2.0),(int)(cy-size/2.0),
					(int)size,(int)size
					);
		}else {
			_g.drawOval(
					(int)(cx-size/2.0),(int)(cy-size/2.0),
					(int)size,(int)size
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
	//!show this Bullet
	/*!
	 */
	public void BeginPlay() {
		// TODO Auto-generated method stub
		if(running)return;
		running = true;
		
	}


	@Override
	//!hide this Bullet
	/*!
	 */
	public void EndPlay() {
		// TODO Auto-generated method stub
		if(!running)return;
		running = false;
	}

}
