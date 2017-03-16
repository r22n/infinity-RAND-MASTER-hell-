package ShooterFactories;

import java.awt.Color;

import GameComponents.Factory;
import ShooterComponents.GameCharacterDrawer;

//!makes bullet drawer of BulletA
/*!
 * \author n.ryouta
 */
public class BulletADrawerFactory implements Factory<GameCharacterDrawer> {
	private boolean fill;
	private double size;
	private double rx,ry;
	private Color color;
	//!initialize
	/*!
	 * \param _fill if you want to fill, true.
	 * \param _size Bullet size in the screen 
	 * \param _rx relative x position  
	 * \param _ry relative y position
	 * \param _color color of BulletA 
	 * \exception IllegalArgumentException _size is smaller equal than 0
	 * \exception _color is null
	 */
	public BulletADrawerFactory(
			boolean _fill,
			double _size,
			double _rx,double _ry,
			Color _color)throws IllegalArgumentException {
		if(!(_size>0))throw new IllegalArgumentException("BulletADrawerFactory _size must be greater than 0");
		if(!(_color!=null))throw new IllegalArgumentException("BulletADrawerFactory _color is null");
		fill = _fill;
		size = _size;
		rx = _rx;
		ry = _ry;
		color = _color;
	}
	
	@Override
	//!create BulletA with parameters at the initializer
	/*!
	 * \returns BulletA object
	 */
	public GameCharacterDrawer CreateObject() {
		// TODO Auto-generated method stub
		return new ShooterDrawers.BulletA(fill, size, rx, ry, color);
	}

}
