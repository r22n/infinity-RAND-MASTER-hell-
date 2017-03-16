package ShooterUpdater;

import ShooterComponents.Fighter;
import ShooterComponents.GameCharacter;
import ShooterComponents.GameCharacterUpdater;
import ShooterDrawers.IEffect;

//!auto shot for enemy
/*!
 * \author n.ryouta
 */
public class ConstantIntervalShooter implements GameCharacterUpdater,IEffect{
	private long interval;
	private long time;
	private Fighter.ShotVisitor shot;
	private double rx,ry;
	private boolean running = false;
	
	//!initialize
	/*!
	 * \param _interval the interval that is auto shot in frame
	 * \param _shot the shot visitor
	 * \param _rx relative x position of shot
	 * \param _ry relative y position of shot
	 * \exception IllegalArgumentException _interval is smaller equal than 0
	 * \exception IllegalArgumentException _shot is null
	 *  
	 */
	public ConstantIntervalShooter(
			long _interval,
			Fighter.ShotVisitor _shot,
			double _rx,double _ry
			)throws IllegalArgumentException {
		if(!(_interval > 0))throw new IllegalArgumentException("ConstantIntervalShooter _interval must be greater than 0");
		if(!(_shot!=null))throw new IllegalArgumentException("ConstantIntervalShooter _shot is null");
		interval = _interval;
		time = 0;
		shot = _shot;
		rx = _rx;
		ry = _ry;
	}
	@Override
	//!shot with constant interval
	/*!
	 * \param _sender must be casted into Fighter
	 */
	public void BeginUpdate(GameCharacter _sender) {
		// TODO Auto-generated method stub
		if(!running)return;
		if(!(_sender instanceof Fighter))return;
		Fighter me = (Fighter)_sender;
		
		if(time % interval == 0) {
			try {
				me.Shot(shot, rx, ry);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		time++;
	}

	@Override
	//!empty
	/*!
	 */
	public void EndUpdate(GameCharacter _sender) {
		// TODO Auto-generated method stub
		
	}
	@Override
	//!turn auto shot enable
	/*!
	 */
	public void BeginPlay() {
		// TODO Auto-generated method stub
		if(running)return;
		running = true;
	}
	@Override
	//!turn auto shot disable
	/*!
	 */
	public void EndPlay() {
		// TODO Auto-generated method stub
		if(!running)return;
		running = false;
	}

}
