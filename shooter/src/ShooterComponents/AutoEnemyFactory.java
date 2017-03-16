package ShooterComponents;

import GameComponents.Component;
import GameComponents.ComponentContainer;
import GameComponents.Rect;
import GameComponents.Vector2;
import ShooterUpdater.OutOfBoundCheck;

import java.util.*;

//!auto straight move and auto n-way shot enemy factory
/*!
 * this component makes enemies randomly and runs its.
 * enemies run into target bounds from top of screen and shot n-way shot randomly.
 * \author n.ryouta
 */
public class AutoEnemyFactory extends ComponentContainer{
	//!handler that receive some events
	/*!
	 * \author n.ryouta
	 */
	public interface Listener {
		//!be notified AutoEnemyFactory creates enemy 
		/*!
		 * \param _sender the object calls this method
		 * \param _e the Fighter has been created 
		 */
		void OnCreateEnemy(AutoEnemyFactory _sender,Fighter _e);
	}
	private List<Listener> listeners =
			new ArrayList<Listener>();
	private List<Listener> append = 
			new ArrayList<Listener>();
	private List<Listener> erase = 
			new ArrayList<Listener>();
	boolean lock = false;
	
	//!add handler for the event that this factory creates enemy
	/*!
	 * \param _listener listener object
	 * \exception IllegalArgumentException _listener is null
	 * \warning if this factory is locking, this method reserve adding listener
	 */
	public void AddAutoEnemyFactoryListener(Listener _listener)throws IllegalArgumentException {
		if(!(_listener!=null))throw new IllegalArgumentException("AutoEnemyFactory.AddAutoEnemyFactoryListener _listener is null");
		if(!lock) {
			listeners.add(_listener);
		}else{
			append.add(_listener);
		}
	}
	//!remove handler 
	/*!
	 * \param _listener listener object
	 * \warning if this factory is locking, this method reserve removing listener
	 */
	public boolean RemoveAutoEnemyFactoryListener(Listener _listener) {
		if(_listener==null)return false;
		if(!lock) {
			return listeners.remove(_listener);
		}else{
			boolean result = listeners.contains(_listener);
			if(result) {
				erase.add(_listener);
			}
			return result;
		}
	}
	//!notify the event that this creates enemy
	/*!
	 * \param _e new enemy
	 */
	protected void NotifyCreateEnemy(Fighter _e) throws Exception {
		if(lock)throw new Exception("AutoEnemyFactory.NotifyCreateEnemy unsafe operation : locking listeners");
		
		lock = true;
		append.clear();
		erase.clear();
		for(Listener i : listeners) {
			i.OnCreateEnemy(this, _e);
		}
		listeners.removeAll(erase);
		listeners.addAll(append);
		lock = false;
	}
	private ShooterFactories.AutoFighterFactory factory ;
	private double percentage;
	private double FPS;
	private double width,height;
	private Rect targetBound;
	//!initialize factory
	/*!
	 * 
	 * \param _factory auto move fighter factory
	 * \param _percentageInSec the appear rate in sec
	 * \param _FPS frame rate of game
	 * \param _width screen width
	 * \param _height screen height
	 * \param _targetBound move target
	 */
	public AutoEnemyFactory(
			ShooterFactories.AutoFighterFactory _factory,
			double _percentageInSec,
			double _FPS,
			double _width,double _height,
			Rect _targetBound
			)throws IllegalArgumentException {
		if(!(_factory!=null))throw new IllegalArgumentException("AutoEnemyFactory _factory is null");
		if(!(0 < _percentageInSec && _percentageInSec <=1))throw new IllegalArgumentException("AutoEnemyFactory _percentageInSec must be in (0,1]");
		if(!(0 < _FPS))throw new IllegalArgumentException("AutoEnemyFactory _FPS must be greater than 0");
		if(!(0 < _width))throw new IllegalArgumentException("AutoEnemyFactory _width must be greater than 0");
		if(!(0 < _height))throw new IllegalArgumentException("AutoEnemyFactory _height must be greater than 0");
		if(!(_targetBound!=null))throw new IllegalArgumentException("AutoEnemyFactory _targetBound is null");
		
		
		factory = _factory;
		
		FPS = _FPS;
		percentage = _percentageInSec;
		width = _width;
		height = _height;
		targetBound = new Rect();
		targetBound.Copy(_targetBound);
	}
	//!set the appear rate
	/*!
	 * \param _percentage the appear rate in sec
	 */
	public void SetPercentageInSec(double _percentage) throws IllegalArgumentException {
		if(!(0 < _percentage && _percentage <= 1))throw new IllegalArgumentException("AutoEnemyFactory.SetPercentageInSec _percentage must be in (0,1]");
		percentage = _percentage;
	}
	//!get the appear rate
	/*!
	 * \returns appear rate of enemy
	 */
	public double GetPercentageInSec(){
		return percentage;
	}
	//!get enemy factory
	/*!
	 * \returns the factory creates straight move enemy
	 */
	public ShooterFactories.AutoFighterFactory GetFactory(){return factory;}
		
	
	//!make and run enemies
	/*!
	 * \param _parent the parent component
	 */
	public void BeginUpdate(Component _parent) throws Exception {
		super.BeginUpdate(_parent);
		double p = percentage / FPS;
		if(Math.random() <= p) {
			double buffer = Math.PI/4.0;
			double angle = - Math.random() * (Math.PI-buffer*2)  -buffer;
			double radius = Math.sqrt(width*width+height*height) * 0.8;
			double tx = Math.random()*targetBound.w + targetBound.x;
			double ty = Math.random()*targetBound.h + targetBound.y;
			double cx = radius * Math.cos(angle) + tx;
			double cy = radius * Math.sin(angle) + ty;
			double dx = tx-cx;
			double dy = ty-cy;
			double norlm = Math.sqrt(dx*dx + dy*dy);
			double nx = dx/norlm;
			double ny = dy/norlm;
			
			double speedNorlm = factory.GetMoveSpeed().Norlm();
			Vector2 speed = factory.GetMoveSpeed();
			speed.x = speedNorlm * nx;
			speed.y = speedNorlm * ny;
			
			Fighter e = (Fighter)factory.CreateObject();
			e.GetBounds().x = cx;
			e.GetBounds().y = cy;
			e.SetCollisionable(true);
			
			NotifyCreateEnemy(e);
		}
	}
}
