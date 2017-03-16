package ShooterFactories;
import ShooterComponents.*;
import ShooterDrawers.BulletA;
import ShooterDrawers.CircleExpandDestroyEffect;
import ShooterUpdater.BulletMove;

import java.awt.Color;

import GameComponents.*;


//!factory for the plane Bullet
/*!
 * 
 * \author n.ryouta
 */
public class PlaneBulletFactory implements Fighter.BulletFactory {
	private double bodySize;
	private Factory<GameCharacterDrawer> drawerFactory;
	private Factory<BulletMove> moveFactory;
	private Factory<CircleExpandDestroyEffect> destroyEffectFactory;
	
	//!initializer
	/*!
	 * \param _sizeOfBulletBody bullet body size
	 * \param _drawerFactory body maker
	 * \param _moveFactory move strategy factory
	 * \exception IllegalArgumentException _sizeOfBulletBody is smaller equal than 0
	 * \exception IllegalArgumentException _moveFactory is null
	 */
	public PlaneBulletFactory(
			double _sizeOfBulletBody,
			Factory<GameCharacterDrawer> _drawerFactory,
			Factory<BulletMove> _moveFactory
			)throws IllegalArgumentException {
		if(!(_sizeOfBulletBody > 0))throw new IllegalArgumentException("StraightBulletFactory _collisionSize must be greater than 0");
		if(!(_moveFactory!=null))throw new IllegalArgumentException("StraightBulletFactory _moveFactory is null");
		bodySize = _sizeOfBulletBody;
		drawerFactory = _drawerFactory;
		moveFactory = _moveFactory;
	}
	//!initializer
	/*!
	 * \param _sizeOfBulletBody bullet body size
	 * \param _drawerFactory body maker
	 * \param _moveFactory move strategy factory
	 * \param _destroyEffectFactory destroy effect factory
	 * \exception IllegalArgumentException _sizeOfBulletBody is smaller equal than 0
	 * \exception IllegalArgumentException _moveFactory is null
	 * \exception IllegalArgumentException _destroyEffectFactory is null
	 */
	public PlaneBulletFactory(
			double _sizeOfBulletBody,
			Factory<GameCharacterDrawer> _drawerFactory,
			Factory<BulletMove> _moveFactory,
			Factory<CircleExpandDestroyEffect> _destroyEffectFactory
			)throws IllegalArgumentException {
		if(!(_sizeOfBulletBody > 0))throw new IllegalArgumentException("StraightBulletFactory _collisionSize must be greater than 0");
		if(!(_moveFactory!=null))throw new IllegalArgumentException("StraightBulletFactory _moveFactory is null");
		if(!(_destroyEffectFactory!=null))throw new IllegalArgumentException("StraightBulletFactory _destroyEffectFactory is null");
		bodySize = _sizeOfBulletBody;
		drawerFactory = _drawerFactory;
		moveFactory = _moveFactory;
		destroyEffectFactory = _destroyEffectFactory;
	}
	
	@Override
	//!create plane Bullet
	/*!
	 * \param _this the Fighter object will shot
	 */
	public Bullet CreateBullet(Fighter _this) {
		// TODO Auto-generated method stub
		try {
			Bullet result = new Bullet();
			result.GetBounds().w = bodySize*0.9;
			result.GetBounds().h = bodySize*0.9;
			if(drawerFactory!=null) {
				result.AddGameCharacterDrawer(drawerFactory.CreateObject());
			}else {
				final ShooterDrawers.BulletA body = 
						new ShooterDrawers.BulletA(
								false,
								bodySize,0,0,
								Color.white
								);
				result.AddGameCharacterDrawer(body);
				result.AddBulletListener(
						new Bullet.Listener() {
							public void EndShot(Bullet _sender) {}
							public void EndCollision(Bullet _sender, Fighter _e) {}
							public void BeginShot(Bullet _sender, Fighter _e) {
								body.BeginPlay();
							}
							public void BeginCollision(Bullet _sender, CollisionContainer<Rect, Circle> _container, Fighter _e) {
								body.EndPlay();
							}
						}
						);
				
			}
			result.SetBulletMove(moveFactory.CreateObject());
			
			
			
			if(destroyEffectFactory!=null) {
				CircleExpandDestroyEffect effect =
						destroyEffectFactory.CreateObject();
				result.AddGameCharacterDrawer(effect);
				result.AddBulletListener(
						new Bullet.Listener() {
							public void EndShot(Bullet _sender) {
								effect.EndPlay();
							}
							public void EndCollision(Bullet _sender, Fighter _e) {}
							public void BeginShot(Bullet _sender, Fighter _e) {}
							public void BeginCollision(Bullet _sender, CollisionContainer<Rect, Circle> _container, Fighter _e) {
								effect.BeginPlay();
							}
						}
						);
				effect.AddEffectEndListener(
						new Listener<Object,Object>() {
							public void OnListen(Object _sender, Object _e) {
								try {
									result.SetValid(false);
									result.SetVisible(false);
									result.EndShot();
								} catch (Exception e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						});
			}else {
				result.AddBulletListener(
						new Bullet.Listener() {
							public void EndShot(Bullet _sender) {}
							public void EndCollision(Bullet _sender, Fighter _e) {}
							public void BeginShot(Bullet _sender, Fighter _e) {
								
							}
							public void BeginCollision(Bullet _sender, CollisionContainer<Rect, Circle> _container, Fighter _e) {
								try {
									result.SetVisible(false);
									result.SetValid(false);
								} catch (Exception e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						}
						);
			}
			
			
			return result;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

}
