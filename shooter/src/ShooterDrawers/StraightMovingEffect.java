package ShooterDrawers;

//!bridge for the straight moving effect with initial position
/*!
 * \author n.ryouta
 */
public interface StraightMovingEffect extends IEffect{
	//!set speed and initial position
	/*!
	 * \param _x x position of effect
	 * \param _y y position of effect
	 * \param _sx x speed of effect in px per sec
	 * \param _sy y speed of effect in px per sec
	 * \param _FPS frame rate
	 */
	void SetSpeed(double _x,double _y,double _sx,double _sy,double _FPS);
}
