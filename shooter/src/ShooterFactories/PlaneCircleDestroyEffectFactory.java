package ShooterFactories;

import java.awt.Color;

import GameComponents.*;
import ShooterDrawers.CircleExpandDestroyEffect;

//!circle destroy effect factory
/*!
 * when the GameCharacter destroyed, expanding circle exposes in its position.
 * this function will be used in LifeCharacter. so this factory is useful.
 * \author n.ryouta
 */
public class PlaneCircleDestroyEffectFactory implements Factory<CircleExpandDestroyEffect>{
	
	private double r0;
	private double expandSpeed;
	private Color color;
	private double rx,ry;
	private int life;
	private double FPS;
	
	//!initialize
	/*!
	 * \param _r0 the initial circle radius
	 * \param _expandSpeed expand speed in px per sec
	 * \param _color color of destroy effect
	 * \param _life the life that destroy effect exposing in frame
	 *  \param _FPS frame rate
	 *  \param _rx relative x position
	 *  \param _ry relative y position
	 *  \exception IllegalArgumentException _r0 is smaller equal than 0
	 *  \exception IllegalArgumentException _expandSpeed is smaller equal than 0
	 *  \exception IllegalArgumentException _color is null
	 *  \exception IllegalArgumentException _life is smaller equal than 0
	 *  \exception IllegalArgumentException _FPS is smaller equal than 0
	 */
	public PlaneCircleDestroyEffectFactory(
			double _r0,
			double _expandSpeed,
			Color _color,
			int _life,
			double _FPS,
			double _rx,double _ry
			)throws IllegalArgumentException {
		if(!(_r0 > 0))throw new IllegalArgumentException("PlaneCircleDestroyEffectFactory _r0 must be greater equal than 0");
		if(!(_expandSpeed > 0))throw new IllegalArgumentException("PlaneCircleDestroyEffectFactory _expandSpeed must be greater than 0");
		if(!(_color!=null))throw new IllegalArgumentException("PlaneCircleDestroyEffectFactory _color is null");
		if(!(_life > 0))throw new IllegalArgumentException("PlaneCircleDestroyEffectFactory _life must be greater than 0");
		if(!(_FPS > 0))throw new IllegalArgumentException("PlaneCircleDestroyEffectFactory _FPS  must be greater than 0");
		
		r0 = _r0;
		expandSpeed = _expandSpeed;
		FPS= _FPS;
		color = _color;
		life = _life;
		rx = _rx;
		ry = _ry;
	}
	
	@Override
	//!create object
	/*!
	 * \returns CircleExpendDestroyEffect object
	 */
	public CircleExpandDestroyEffect CreateObject() {
		// TODO Auto-generated method stub
		return new ShooterDrawers.CircleExpandDestroyEffect(
				r0, expandSpeed, color, life, FPS, rx, ry
				);
	}

}
