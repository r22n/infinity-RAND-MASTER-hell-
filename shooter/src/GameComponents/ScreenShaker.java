package GameComponents;

import GameComponents.*;

//!shakes screen such as destroy effect etc
/*!
 * \author n.ryouta
 */
public class ScreenShaker extends ComponentContainer{
	private double x,y,dx,dy;
	private double k,eta,m;
	private double FPS;
	private double ts;
	private IScreenDrawPos pos;
	private double dt;
	
	//!initialize with parameters for spring and dumping vibration
	/*!
	 * \param _k sprint stiffness
	 * \param _eta dumping parameter that times with speeds
	 * \param _m mass of pendulum
	 * \param _FPS frame rate of game
	 * \param _thresholdSpeed the threshold of speed
	 * \param _pos the screen position
	 * \exception IllegalArgumentException _k is smaller equal than 0
	 * \exception IllegalArgumentException _eta is smaller equal than 0
	 * \exception IllegalArgumentException _m is smaller equal than 0
	 * \exception IllegalArgumentException _FPS is smaller equal than 0
	 * \exception IllegalArgumentException _threshould is smaller equal than 0 
	 * \exception IllegalArgumentException _pos is null
	 */
	public ScreenShaker(
			double _k,
			double _eta,
			double _m,
			double _FPS,
			double _thresholdSpeed,
			IScreenDrawPos _pos
			)throws IllegalArgumentException {
		if(!(_k>0))throw new IllegalArgumentException("ScreenShaker _k must be greater than 0");
		if(!(_eta>0))throw new IllegalArgumentException("ScreenShaker _eta must be greater than 0");
		if(!(_m>0))throw new IllegalArgumentException("ScreenShaker _m must be greater than 0");
		if(!(_FPS>0))throw new IllegalArgumentException("ScreenShaker _FPS must be greater than 0");
		if(!(_thresholdSpeed>0))throw new IllegalArgumentException("ScreenShaker _threashouldSpeed must be greater than 0");
		if(!(_pos!=null))throw new IllegalArgumentException("ScreenShaker _pos is null");

		x = y = dx = dy = 0;
		k = _k;
		eta = _eta;
		m = _m;
		FPS = _FPS;
		ts = _thresholdSpeed;
		pos = _pos;
		dt = 1.0/_FPS;
	}
	//!begins shaking with speed that indicates pixel per sec
	/*!
	 * \param _sx x speed in per sec
	 * \param _sy y speed in per sec
	 */
	public void BeginShake(double _sx,double _sy) {
		dx = _sx / FPS;
		dy = _sy / FPS;
	}
	//!force shaking ends
	/*!
	 */
	public void EndShake(){
		dx = dy = 0;
		x = y = 0;
	}
	//!shaking the position bridge such as screen that provided by initializer
	/*!
	 * 
	 */
	public void BeginUpdate(Component _parent) {
		dx += - k / m * x * dt*dt - eta / m * dx * dt;
		dy += - k / m * y * dt*dt - eta / m * dy * dt;
		x += dx;
		y += dy;
		
		pos.SetX(x);
		pos.SetY(y);
		
		double vx = dx/dt;
		double vy = dy/dt;
		double dts = ts/FPS;
		if(vx*vx + vy*vy <= ts*ts &&
			x*x+y*y <= dts) {
			EndShake();
		}
	}
	
}
