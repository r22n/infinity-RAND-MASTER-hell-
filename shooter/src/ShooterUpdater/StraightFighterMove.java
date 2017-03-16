package ShooterUpdater;

import GameComponents.*;
import ShooterComponents.*;

//!straight move updater for Fighter
/*!
 * makes the GameCharacter move to target
 * \author n.ryouta
 */
public class StraightFighterMove implements GameCharacterUpdater{
	private double tx,ty;
	private double speed;
	private double FPS;
	//!initializer
	/*!
	 * \param _tx target x location
	 * \param _ty target y location
	 * \param _speed speed in px per sec
	 * \param _FPS frame rate
	 */
	public StraightFighterMove(
			double _tx,double _ty,
			double _speed,
			double _FPS
			)throws IllegalArgumentException {
		if(!(_speed>0))throw new IllegalArgumentException("StraightFighterMove _speed must be greater than 0");
		if(!(_FPS>0))throw new IllegalArgumentException("StraightFighterMove _FPS must be greater than 0");
		tx = _tx;
		ty = _ty;
		speed = _speed;
		FPS = _FPS;
	}
	
	@Override
	//!force the GameCharacter move to
	/*!
	 * \param _sender the target object
	 */
	public void BeginUpdate(GameCharacter _sender) {
		// TODO Auto-generated method stub
		if(!(_sender!=null)) {
			System.out.println("StraightFighterMove.BeginUpdate _sender is null");
			return;
		}
		Rect bounds = _sender.GetBounds();
		double dx = tx-bounds.x;
		double dy = ty-bounds.y;
		double norlm = Math.sqrt(dx*dx+dy*dy);
		if(norlm<=0) return;
		double nx = dx/norlm;
		double ny = dy/norlm;
		bounds.x += nx*speed/FPS;
		bounds.y += ny*speed/FPS;
	}

	@Override
	//!empty
	/*!
	 */
	public void EndUpdate(GameCharacter _sender) {
		// TODO Auto-generated method stub
		
	}

}
