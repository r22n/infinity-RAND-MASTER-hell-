package ShooterComponents;

import GameComponents.*;

//!manages collision of Fighter and Bullet
/*!
 * \author n.ryouta
 */
public class ShooterCollisionContainer extends ComponentContainer {
	private CollisionContainer<Rect,Circle> me_bullet =
			new CollisionContainer<Rect,Circle>();
	private CollisionContainer<Rect,Circle> mebullet_enemy =
			new CollisionContainer<Rect,Circle>();
	
	//!initialize this object
	/*!
	 * sets priority -1 for checking collision in first and adds facading CollisionContainer into this as child component
	 * 
	 */
	public ShooterCollisionContainer() {
		SetPriority(-1);
		try {
			AddChild(me_bullet);
			AddChild(mebullet_enemy);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//!add my fighter and manage collision with enemy
	/*!
	 * \param _me my Fighter
	 */
	public void AddMyFighter(Fighter _me) throws IllegalArgumentException {
		if(!(_me!=null))throw new IllegalArgumentException("ShooterCollisionContainer.AddMyFighter _me is null");
		me_bullet.AddListA(_me);
		for(Bullet i : _me.GetBulletSet()) {
			mebullet_enemy.AddListA(i);
		}
	}

	//!remove my fighter and manage collision with enemy
	/*!
	 * \param _me my Fighter
	 */
	public void RemoveMyFighter(Fighter _me)throws IllegalArgumentException {
		if(!(_me!=null))throw new IllegalArgumentException("ShooterCollisionContainer.RemoveMyFighter _me is null");
		me_bullet.RemoveFromA(_me);
		for(Bullet i : _me.GetBulletSet()) {
			mebullet_enemy.RemoveFromA(i);
		}
	}

	//!add enemy fighter and manage collision with enemy
	/*!
	 * \param _me enemy Fighter
	 */
	public void AddEnemyFighter(Fighter _e)throws IllegalArgumentException {
		if(!(_e!=null))throw new IllegalArgumentException("ShooterCollisionContainer.AddEnemyFighter _e is null");
		for(Bullet i : _e.GetBulletSet()) {
			me_bullet.AddListB(i);
		}
		mebullet_enemy.AddListB(_e);
	}
	//!remove enemy fighter and manage collision with enemy
	/*!
	 * \param _me enemy Fighter
	 */
	public void RemoveEnemyFighter(Fighter _e)throws IllegalArgumentException {
		if(!(_e!=null))throw new IllegalArgumentException("ShooterCollisionContainer.RemoveEnemyFighter _e is null");
		for(Bullet i : _e.GetBulletSet()) {
			me_bullet.RemoveFromB(i);
		}
		mebullet_enemy.RemoveFromB(_e);
	}
}
