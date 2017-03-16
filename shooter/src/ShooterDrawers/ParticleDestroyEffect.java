package ShooterDrawers;

import ShooterComponents.*;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

//!plays destroy effect that scatters some circles
/*!
 * scattered circles become invisible step by step
 * \author n.ryouta
 */
public class ParticleDestroyEffect implements GameCharacterDrawer, StraightMovingEffect{
	private boolean running = false;
	private int particleCount;
	private Color color;
	private double minSize;
	private double maxSize;
	private double dx,dy;
	private double rx,ry;
	private double r0;
	private double minSpeed;
	private double maxSpeed;
	private double cx,cy;
	private int life , time;
	
	private class Particle {
		public double x,y;
		public double size;
		public double dx,dy;
		public void Move(){
			x += dx;
			y += dy;
		}
	}
	private List<Particle> particles = 
			new ArrayList<Particle>();
	
	//!initializer
	/*!
	 * initializes some circles fully randomly
	 * \param _particleCount the number of circles
	 * \param _color basic color of circles
	 * \param _minSize the minimum radius of circle
	 * \param _maxSize the maximum radius of circle
	 * \param _rx relative x position that exposing the destroy effect
	 * \param _ry relative y position that exposing the destroy effect
	 * \param _r0 random initial position from the center position of game character
	 * \param _speedMin minimum moving speed in sec
	 * \param _speedMax maximum moving speed in sec
	 * \param _FPS frame rate
	 * \param _timeInFrame the life of this effect in frame
	 * \exception IllegalArgumentException _particleCount is smaller equal than 0
	 * \exception IllegalArgumentException _color is null
	 * \exception IllegalArgumentException _minSize is smaller equal than 0
	 * \exception IllegalArgumentException _maxSize is smaller equal than 0
	 * \exception IllegalArgumentException _maxSize is smaller equal than _minSize
	 * \exception IllegalArgumentException _speedMin is smaller equal than 0
	 * \exception IllegalArgumentException _speedMax is smaller equal than 0
	 * \exception IllegalArgumentException _speedMax is smaller equal than _speedMin
	 * \exception IllegalArgumentException _timeInFrame is smaller equal than 0 
	 *  
	 * 
	 */
	public ParticleDestroyEffect(
			int _particleCount,
			Color _color,
			double _minSize,double _maxSize,
			double _rx,double _ry,
			double _r0,
			double _speedMin,double _speedMax,
			double _FPS,
			int _timeInFrame
			)throws IllegalArgumentException {
		if(!(_particleCount>0))throw new IllegalArgumentException("ParticleDestoryEffect _particleCount must be greater than 0");
		if(!(_color!=null))throw new IllegalArgumentException("ParticleDestoryEffect _color is null");
		if(!(_minSize > 0))throw new IllegalArgumentException("ParticleDestoryEffect _minSize must be greater than 0");
		if(!(_maxSize > 0))throw new IllegalArgumentException("ParticleDestoryEffect _maxSize must be greater than 0");
		if(!(_maxSize > _minSize))throw new IllegalArgumentException("ParticleDestoryEffect _minSize must be greater than 0");
		
		if(!(_speedMin > 0))throw new IllegalArgumentException("ParticleDestoryEffect _speedMin must be greater than 0");
		if(!(_speedMax > 0))throw new IllegalArgumentException("ParticleDestoryEffect _speedMax must be greater than 0");
		if(!(_speedMax > _speedMin))throw new IllegalArgumentException("ParticleDestoryEffect _speedMin must be greater than 0");
		if(!(_timeInFrame > 0))throw new IllegalArgumentException("ParticleDestoryEffect _timeInFrame must be greater than 0");
		
		particleCount = _particleCount;
		color = _color;
		minSize = _minSize;
		maxSize = _maxSize;
		rx = _rx;
		ry = _ry;
		r0 = _r0;
		minSpeed = _speedMin / _FPS;
		maxSpeed = _speedMax / _FPS;
		life  = _timeInFrame;
		
		for(int i = 0 ; i < particleCount;i++) {
			Particle p = new Particle();
			particles.add(p);
			
		}
	}
	
	
	
	@Override
	//!draw this effect
	/*!
	 * \param _sender no use
	 * \param _g GDI
	 */
	public void BeginDraw(GameCharacter _sender, Graphics _g) {
		// TODO Auto-generated method stub
		if(!running)return ;
		if(time >= life) {
			EndPlay();
			return;
		}

		for(Particle i : particles) {
			i.Move();
		}
		_g.setColor(
				new Color(
						color.getRed(),
						color.getGreen(),
						color.getBlue(),
						(int)(255.0*((double)life-(double)time)/(double)life)
						)
				);
				
		for(Particle i : particles) {
			_g.drawOval(
					(int)(i.x - i.size/2.0),
					(int)(i.y - i.size/2.0),
					(int)i.size,
					(int)i.size
					);
		}
		time++;
		
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
	//!play this effect
	/*!
	 * initialize all circles that will be set randomly
	 * \warning before you call this method, you should call SetSpeed method
	 * \see SetSpeed
	 */
	public void BeginPlay() {
		// TODO Auto-generated method stub
		if(running) return;
		running = true;
		for(Particle p : particles) {
			double t = Math.PI * (-1.0 + 2.0*Math.random());
			p.x = rx + r0 * Math.cos(t) + cx;
			p.y = ry + r0 * Math.sin(t) + cy;
			double s =Math.random()*(maxSpeed-minSpeed)+minSpeed; 
			p.dx = s*Math.cos(t) + dx;
			p.dy = s*Math.sin(t) + dy;
			p.size = (maxSize-minSize)*Math.random()+minSize;
		}
		time = 0;
	}



	@Override
	//!force exit this effect
	/*!
	 */
	public void EndPlay() {
		// TODO Auto-generated method stub
		if(!running) return ;
		running = false;
	}



	@Override
	//!set speed and initial position
	/*!
	 * \param _x x location of destroy effect center
	 * \param _x y location of destroy effect center
	 * \param _sx speed x of effect moving
	 * \param _sy speed y of effect moving
	 * \param _FPS frame rate
	 * 
	 */
	public void SetSpeed(double _x,double _y,double _sx, double _sy, double _FPS) {
		// TODO Auto-generated method stub
		dx = _sx / _FPS;
		dy = _sy /_FPS;
		cx  = _x;
		cy = _y;
	}
}
