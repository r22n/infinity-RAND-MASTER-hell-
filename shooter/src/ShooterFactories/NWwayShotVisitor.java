package ShooterFactories;

import java.util.List;
import java.util.Queue;

import ShooterComponents.*;

//!makes the Fighter shot n-way
/*!
 * \author n.ryouta
 */
public class NWwayShotVisitor implements Fighter.ShotVisitor{
	private int n;
	private double speed;
	private double angle;
	private double width;
	private double distAngle;
	
	//!initialize
	/*!
	 * \param _n n-way shot
	 * \param _speed bullet speed in px per sec
	 * \param _FPS frame rate
	 * \param _angle shot angle
	 * \param _width width of shot nozzle
	 * \param _distAngle defusing angle of n-way
	 * \exception IllegalArgumentException _n is smaller equal than 0
	 * \exception IllegalArgumentException _speed is smaller equal than 0
	 * \exception IllegalArgumentException _FPS is smaller equal than 0
	 * \exception IllegalArgumentException _width is smaller equal than 0
	 */
	public NWwayShotVisitor(
			int _n,
			double _speed,double _FPS,
			double _angle,double _width,double _distAngle
			)throws IllegalArgumentException {
		if(!(_n > 0))throw new IllegalArgumentException("NWayShotVisitor _n must be greater than 0");
		if(!(_speed > 0))throw new IllegalArgumentException("NwayShotVisitor _speed must be greater than 0");
		if(!(_FPS > 0))throw new IllegalArgumentException("NWayShotVisitor _FPS must be greater than 0");
		if(!(_width > 0))throw new IllegalArgumentException("NWayShotVisitor _width must be greater than 0");
		n = _n;
		speed = _speed ;
		angle = _angle;
		distAngle = _distAngle;
		width = _width;
	}
	//!set n-way
	/*!
	 * \param _n n-way shot 
	 * \exception IllegalArgumentException _n is smaller equal than 0
	 */
	public void SetNWayShot(int _n) throws IllegalArgumentException {
		if(!(_n > 0))throw new IllegalArgumentException("NWayShotVisitor.SetNWayShot _n must be greater than 0");
		n = _n;
	}
	//!get n-way
	/*!
	 * \returns n-way
	 */
	public int GetNWayShot(){
		return n;
	}
	
	//!set defusing angle
	/*!
	 * \param _distAngle defusing angle
	 */
	public void SetDistAngle(double _distAngle){
		distAngle = _distAngle;
	}
	//!get defusing angle
	/*!
	 * \returns defusing angle
	 */
	public double GetDistAngle(){
		return distAngle;
	}
		
	@Override
	//!visit member and shot its
	/*!
	 * \param _this Fighter that wants shot
	 * \param _bullets Bullet pool 
	 * \param _rx relative x position
	 * \param _ry relative y position
	 * \param _dstShotBulletsResult the container that will be stored shot Bullets
	 */
	public void Visit(
			Fighter _this,
			Queue<Bullet> _bullets,
			double _rx, double _ry,
			List<Bullet> _dstShotBulletsResult) {
		// TODO Auto-generated method stub
		
		for(int i = 0 ; i < n && !_bullets.isEmpty();i++) {
			Bullet b = _bullets.poll();
			ShooterUpdater.BulletMove move = b.GetBulletMove();
			if(move==null)continue;
			double rrx = -width/2.0 +width/(double)n * i;
			double rry = 0;
			double sAngle = distAngle/(double)n * ((double)n/2.0-(double)i-0.5) + angle;
			move.SetSpeed(
					speed*Math.cos(sAngle),speed*Math.sin(sAngle)
					);
			try {
				b.BeginShot(_this, rrx+_rx, rry+_ry);
				_dstShotBulletsResult.add(b); 
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

}
