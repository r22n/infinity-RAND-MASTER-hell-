package ShooterDrawers;

import ShooterComponents.*;

import java.awt.Color;
import java.awt.Graphics;

import GameComponents.*;

//!draws UAV at around my Fighter
/*!
 * these UAV plays at around my Fighter and is actuated by keybord such as the action that includes open-close
 * \author n.ryouta
 */
public class MyFighterUAV implements GameCharacterDrawer ,IEffect{
	private KeyBridge keyboard;
	private double size;
	private int design;
	private double angle;
	private double speed;
	private double FPS;
	private double radius;
	private double rx,ry;
	private int centerVK;
	private Color color;
	private int[] tx;
	private int[]ty;
	private boolean running = true;
	
	private class UAV {
		public double angle;
	}
	private UAV left = new UAV();
	private UAV right = new UAV();
	
	//!initializer
	/*!
	 * \param _keyboard the keyboard bridge
	 * \param _size the size of UAV that indicates by the radius of circumscribed circle
	 * \param _design the number of edge of UAV
	 * \param _angle the angle of UAV
	 * \param _speed the speed of UAV moving
	 * \param _FPS frame rate
	 * \param _radius the orbit radius of UAV moving
	 * \param _rx relative x position
	 * \param _ry relative y position
	 * \param _centerVK the virtual-keycode that makes UAVs moving into center
	 * \param _color color of UAVs
	 * \exception IllegalArgumentException _keyboard is null
	 * \exception IllegalArgumentException _size is smaller equal than 0
	 * \exception IllegalArgumentException _design is smaller than 3
	 * \exception IllegalArgumentException _speed is smaller equal than 0
	 * \exception IllegalArgumentException _FPS is smaller equal than 0
	 * \exception IllegalArgumentException _radius is smaller equal than 0
	 * \exception IllegalArgumentException _color is null
	 *   
	 * 
	 * 
	 */
	public MyFighterUAV(
			KeyBridge _keyboard,
			double _size,
			int _design,
			double _angle,
			double _speed,
			double _FPS,
			double _radius,
			double _rx,double _ry,
			int _centerVK,
			Color _color
			)throws IllegalArgumentException {
		if(!(_keyboard!=null))throw new IllegalArgumentException("MyFighterUAV _keyboard is null");
		if(!(_size > 0))throw new IllegalArgumentException("MyFighterUAV _size must be greater than 0");
		if(!(_design >= 3))throw new IllegalArgumentException("MyFighterUAV _design must be greater equal than 3");
		if(!(_speed > 0))throw new IllegalArgumentException("MyFighterUAV _speed must be greater than 0");
		if(!(_FPS > 0))throw new IllegalArgumentException("MyFighterUAV _FPS must be greater than 0");
		if(!(_radius > 0))throw new IllegalArgumentException("MyFighter _radius must be greater than 0");
		if(!(_color!=null))throw new IllegalArgumentException("MyFighter _color is null");
		keyboard = _keyboard;
		size = _size;
		design = _design;
		angle = _angle;
		speed = _speed;
		FPS = _FPS;
		radius = _radius;
		rx = _rx;
		ry = _ry;
		centerVK = _centerVK;
		color = _color;
		left.angle = -Math.PI;
		right.angle = 0;
		tx = new int[design];
		ty = new int[design];
	}
	
	@Override
	//!draw UAV with animation by using keyboard pressing
	/*!
	 * \param _sender the object that excepted my Fighter
	 * \param _g GDI
	 */
	public void BeginDraw(GameCharacter _sender, Graphics _g) {
		// TODO Auto-generated method stub
		if(!(_sender!=null)) {
			System.out.println("MyFighterUAV.BeginDraw _sender is null");
			return ;
		}
		if(!running)return;
		Rect bounds = _sender.GetBounds();
		double cx = bounds.x+bounds.w/2.0+rx;
		double cy = bounds.y+bounds.h/2.0+ry;
		double dt = speed / FPS;
		
		if(keyboard.GetKeyDown(centerVK)) {
			left.angle = Math.min(left.angle+dt,-Math.PI*0.6);
			right.angle = Math.max(right.angle-dt,-Math.PI*0.4);
		}else {
			left.angle = Math.max(left.angle-dt,-Math.PI);
			right.angle = Math.min(right.angle+dt,0);
		}
		
		_g.setColor(color);
		double lx = radius*Math.cos(left.angle)+cx;
		double ly = radius*Math.sin(left.angle)+cy;
		for(int i = 0 ; i < design;i++) {
			double theta = Math.PI*2.0/(double)design * (double)i + angle+left.angle;
			tx[i] = (int)(size*Math.cos(theta) + lx);
			ty[i] = (int)(size*Math.sin(theta) + ly);
		}
		_g.drawPolygon(tx, ty, design);
		double rx = radius*Math.cos(right.angle)+cx;
		double ry = radius*Math.sin(right.angle)+cy;
		for(int i = 0 ; i < design;i++) {
			double theta = Math.PI*2.0/(double)design * (double)i + angle +right.angle;
			tx[i] = (int)(size*Math.cos(theta) + rx);
			ty[i] = (int)(size*Math.sin(theta) + ry);
		}
		_g.drawPolygon(tx, ty, design);
	}

	@Override
	//!empty
	/*!
	 */
	public void EndDraw(GameCharacter _sender, Graphics _g) {
		// TODO Auto-generated method stub
		
	}

	@Override
	//!turn the visibility true
	/*!
	 */
	public void BeginPlay() {
		// TODO Auto-generated method stub
		if(running)return;
		running =true;
	}

	@Override
	//!turn the visibility false
	/*!
	 */
	public void EndPlay() {
		// TODO Auto-generated method stub
		if(!running)return;
		running = false;
	}

}
