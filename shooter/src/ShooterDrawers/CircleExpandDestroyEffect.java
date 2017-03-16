package ShooterDrawers;

import java.awt.Color;
import GameComponents.*;
import java.awt.Graphics;
import java.util.*;
import GameComponents.Rect;
import ShooterComponents.GameCharacter;
import ShooterComponents.GameCharacterDrawer;

//!draws expending circle that is not filled
/*!
 * \author n.ryouta
 */
public class CircleExpandDestroyEffect implements GameCharacterDrawer, IEffect {
	private List<Listener<Object,Object>> listeners = 
			new ArrayList<Listener<Object,Object>>();
	private List<Listener<Object,Object>> erase = 
			new ArrayList<Listener<Object,Object>>();
	private List<Listener<Object,Object>> append =
			new ArrayList<Listener<Object,Object>>();
	private boolean lock = false;
	
	//!add handler that receives the event this effect ends playing
	/*!
	 * \param _listener the handler receives the event that this ends to play effect	
	 * \exception IllegalArgumentException _listener is null
	 */
	public void AddEffectEndListener(Listener<Object,Object> _listener)throws IllegalArgumentException {
		if(!(_listener!=null))throw new IllegalArgumentException("CircleExpandDestroyEffect.AddEffectEndListener _listener is null");
		if(!lock) {
			listeners.add(_listener);
		}else {
			append.add(_listener);
		}
	}

	//!remove handler from this
	/*!
	 * \param _listener the handler that you want to remove 	
	 * \returns if the _listener was registered in this, true. otherwise false.
	 */
	public boolean RemoveEffectEndListener(Listener<Object,Object> _listener) {
		if(_listener==null)return false;
		if(!lock) {
			return listeners.remove(_listener);
		}else {
			boolean result = listeners.contains(_listener);
			if(result) {
				erase.add(_listener);
			}
			return result;
		}
	}
	//!notify this effect ends to play
	/*!
	 * 
	 */
	protected void NofityEffectEnd() {
		for(Listener<Object,Object> i : listeners)
			i.OnListen(this,null);
	}
				
			
	
	
	private double r0;
	private double dr;
	private double r;
	private Color color;
	private int life,time;
	private boolean running  = false;
	private double rx,ry;
	//!initialize this object
	/*!
	 * \param _r0 the initial radius of expanding animation
	 * \param _expandSpeed expanding speed in px per sec
	 * \param _color the color of this
	 * \param _life the life of animation in frame
	 * \param _FPS the frame rate of game
	 * \param _rx relative x position
	 * \param _ry relative y position
	 * \exception IllegalArgumentException _r0 is smaller equal than 0
	 * \exception IllegalArgumentException _expandSpeed is smaller equal than 0
	 * \exception IllegalArgumentException _color is null
	 * \exception IllegalArgumentException _life is smaller equal than 0
	 * \exception IllegalArgumentException _FPS is smaller equal than 0  
	 */
	public CircleExpandDestroyEffect(
			double _r0,
			double _expandSpeed,
			Color _color,
			int _life,
			double _FPS,
			double _rx,double _ry
			)throws IllegalArgumentException {
		if(!(_r0 > 0))throw new IllegalArgumentException("CircleExpandDestroyEffect _r0 must be greater equal than 0");
		if(!(_expandSpeed > 0))throw new IllegalArgumentException("CircleExpandDestroyEffect _expandSpeed must be greater than 0");
		if(!(_color!=null))throw new IllegalArgumentException("CircleExpandDestroyEffect _color is null");
		if(!(_life > 0))throw new IllegalArgumentException("CircleExpandDestroy _life must be greater than 0");
		if(!(_FPS > 0))throw new IllegalArgumentException("CircleExpandDestroy _FPS  must be greater than 0");
		
		r0 = _r0;
		dr = _expandSpeed / _FPS;
		color = _color;
		life = _life;
		rx = _rx;
		ry = _ry;
	}
	
		
	
	@Override
	//!runs animation of circle expanding
	/*!
	 * runs animation that expands single circle and actuate it in center position of _sender
	 * \param _sender the target object
	 * \param _g GDI
	 */
	public void BeginDraw(GameCharacter _sender, Graphics _g) {
		// TODO Auto-generated method stub
		if(!running)return;
		if(time >= life) {
			EndPlay();
		}
		Rect bounds = _sender.GetBounds();
		double cx = bounds.x+bounds.w/2.0;
		double cy = bounds.y+bounds.h/2.0;
		
		
		_g.setColor(
				new Color(
						color.getRed(),
						color.getGreen(),
						color.getBlue(),
						(int)(255.0*((double)life-(double)time)/(double)life)
						)
				);
		_g.drawOval(
				(int)(cx-r/2.0),
				(int)(cy-r/2.0),
				(int)r,(int)r
				);
		
		
		r += dr;
		time++;
	}

	@Override
	//!empty
	/*!
	 */
	public void EndDraw(GameCharacter _sender, Graphics _g) {
		// TODO Auto-generated method stub

	}


	@Override
	//!play this effect
	/*!
	 * 
	 */
	public void BeginPlay() {
		// TODO Auto-generated method stub
		if(!running) {
			running = true;
			time = 0;
			r = r0;
		}			
	}


	@Override
	//!force this effect exit
	/*!
	 * 
	 */
	public void EndPlay() {
		// TODO Auto-generated method stub
		if(running) {
			running = false;
			NofityEffectEnd();
		}
	}

}
