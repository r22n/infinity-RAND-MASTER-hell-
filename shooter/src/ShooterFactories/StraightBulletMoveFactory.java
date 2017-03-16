package ShooterFactories;

import GameComponents.*;
import ShooterComponents.GameCharacterUpdater;
import ShooterUpdater.BulletMove;

//!straight bullet move strategy factory
/*!
 * \author n.ryouta
 */
public class StraightBulletMoveFactory implements Factory<BulletMove>{
	private double width,height;
	private double FPS;
	
	//!initializer
	/*!
	 * \param _width width width of screen
	 * \param _height height of screen
	 */
	public StraightBulletMoveFactory(
			double _width,double _height,
			double _FPS
			)throws IllegalArgumentException {
		if(!(_width>0))throw new IllegalArgumentException("StraightBulletMoveFactory _width must be greater than 0");
		if(!(_height>0))throw new IllegalArgumentException("StraightBulletMoveFactory _height must be greater than 0");
		if(!(_FPS>0))throw new IllegalArgumentException("StraightBulletMoveFactory _FPS must be greater than 0");
		width = _width;
		height = _height;
		FPS=  _FPS;
	}
	@Override
	//!create object
	/*!
	 * \returns BulletMove object
	 */
	public BulletMove CreateObject() {
		// TODO Auto-generated method stub
		return new ShooterUpdater.BulletMove(
				new Rect(0,0,width,height),0,0,FPS
				);
	}

}
