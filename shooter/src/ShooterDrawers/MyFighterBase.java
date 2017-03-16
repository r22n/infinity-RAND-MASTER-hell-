package ShooterDrawers;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import ShooterComponents.*;

//!draws my container that hold in the sky
/*!
 * \author n.ryouta
 */
public class MyFighterBase implements GameCharacterDrawer,StraightMovingEffect {
	private boolean running = false;
	private double size;
	private double pargeSpeed;
	private Color color;
	private double FPS;
	private double purgeAcc;
	
	private class Box {
		public double x,y,w,h;
		public double dx,dy;
	}
	private List<Box> boxes = 
			new ArrayList<Box>();
	
	//!initializer 
	/*!
	 * \param _size the size of my container 
	 * \param _purgeSpeed initial speeds that is container purging 
	 * \param _FPS frame rate
	 * \param _color the color of container
	 * \param _purgeAcc acceleration in px per sec^2
	 * \exception IllegalArgumentException _size is smaller equal than 0
	 * \exception IllegalArgumentException _purgeSpeed is smaller equal than 0
	 * \exception IllegalArgumentException _FPS is smaller equal than 0
	 * \exception IllegalArgumentException _color is null
	 * \exception IllegalArgumentException _purgeAcc is smaller equal than 0
	 * 
	 * 
	 */
	public MyFighterBase(
			double _size,
			double _purgeSpeed,
			double _FPS,
			Color _color,
			double _purgeAcc
			)throws IllegalArgumentException {
		if(!(_size>0))throw new IllegalArgumentException("MyFighterBase _size must be greater than 0");
		if(!(_purgeSpeed>0))throw new IllegalArgumentException("MyFighterBase _pargeSpeed must be greater than 0");
		if(!(_FPS > 0))throw new IllegalArgumentException("MyFighterBase _FPS must be greater than 0");
		if(!(_color!=null))throw new IllegalArgumentException("MyFighterBase _color is null");
		if(!(_purgeAcc > 0))throw new IllegalArgumentException("MyFighterBase _pargeAcc must be greater than 0");
		
		size = _size;
		pargeSpeed = _purgeSpeed / _FPS;
		purgeAcc = _purgeAcc / _FPS / _FPS;
		color = _color;
		
		for(int i = 0 ; i < 4;i++) {
			Box b = new Box();
			b.w = size;
			b.h = size;
			boxes.add(b);
		}
		Box center = new Box();
		center.w = size*2;
		center.h = size*2;
		boxes.add(center);
	}
		
	
	@Override
	//!enable purging
	/*!
	 */
	public void BeginPlay() {
		// TODO Auto-generated method stub
		if(running)return;
		running = true;
	}

	@Override
	//!force purging disable
	/*!
	 */
	public void EndPlay() {
		// TODO Auto-generated method stub
		if(!running)return;
		running = false;
				
	}


	@Override
	//!draw my container with purging
	/*!
	 * \param _sender the object that is expected my fighter
	 * \param _g GDI
	 */
	public void BeginDraw(GameCharacter _sender, Graphics _g) {
		// TODO Auto-generated method stub
		if(running) {
			for(Box i : boxes) {
				i.dy += purgeAcc;
				i.x += i.dx;
				i.y += i.dy;
			}
		}
		
		_g.setColor(color);
		for(Box i : boxes) {
			_g.drawRect(
					(int)i.x,(int)i.y,
					(int)i.w,(int)i.h
					);
		}
	}


	@Override
	//!empty
	/*!
	 * 
	 */
	public void EndDraw(GameCharacter _sender, Graphics _g) {
		// TODO Auto-generated method stub
		
	}


	@Override
	//!set the initial position of container
	/*!
	 * \param _x x location of container
	 * \param _y y location of container
	 * \param _sx purge speed of x
	 * \param _sy purge speed of y
	 * \param _FPS frame rate
	 */
	public void SetSpeed(
			double _x, double _y,
			double _sx, double _sy,
			double _FPS) {
		// TODO Auto-generated method stub
		for(int i = 0 ; i < 2;i++) {
			for(int j = 0 ; j < 2;j++) {
				Box b = boxes.get(i*2+j);
				b.x = i * size + _x - size;
				b.y = j * size + _y - size;
				b.dx = ((i==0)?(-1.0):(1.0))*pargeSpeed + _sx / _FPS;
				b.dy = ((j==0)?(-1.0):(1.0))*pargeSpeed + _sy / _FPS;
			}
		}
		Box center = boxes.get(boxes.size()-1);
		center.x = _x - size;
		center.y = _y - size;
		center.dy = pargeSpeed + _sy / _FPS;
	}
}
