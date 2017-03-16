package ShooterDrawers;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import GameComponents.*;

import ShooterComponents.GameCharacter;
import ShooterComponents.GameCharacterDrawer;

//!draws jet effect of Fighter with many circles as particle
/*!
 * \author n.ryouta
 */
public class ParticleJetEffect implements GameCharacterDrawer,IEffect{
	private class Particle {
		public double dx,dy;
		public double x,y;
		public int time;
		public int life;
		public void Move(){
			x+=dx;
			y+=dy;
		}
		
	}
	private List<Particle> pwParticles = 
			new ArrayList<Particle>();
	private Queue<Particle> particles =
			new java.util.ArrayDeque<Particle>();
	
	private int maxParticle;
	private double FPS;
	private double rx,ry;
	private Color color;
	private double angle;
	private double distAngle;
	private double speed;
	private int interval;
	private double width;
	private int time;
	private double life;
	private double size;
	private boolean running = false;
	//!initialize
	/*!
	 * \param _size the size that is radius of circle
	 * \param _lifeInSec the life of circle in frame
	 * \param _width the width of nozzle that expose many circles
	 * \param _angle the angle of jet effecting
	 * \param _distAngle the angle that the jet effect defusing
	 * \param _speed the circle effecting speed in px per sec
	 * \param _interval the interval in sec that defuses circle
	 * \param _color the color of effect
	 * \param _maxParticle the maximumu count of circles that can be exposed
	 * \param _FPS frame rate
	 * \param _xFromBound the relative x position
	 * \param _yFromBound the relative y position
	 * \exception IllegalArgumentException _maxParticle is smaller equal than 0
	 * \exception IllegalArgumentException _FPS is smaller equal than 0
	 * \exception IllegalArgumentException _color is null
	 * \exception IllegalArgumentException _distAngle is smaller than 0
	 * \exception IllegalArgumentException _speed is smaller equal than 0
	 * \exception IllegalArgumentException _interval is smaller equal than 0
	 * \exception IllegalArgumentException _width is smaller equal than 0
	 * \exception IllegalArgumentException _lifeInSec is smaller equal than 0
	 * \exception IllegalArgumentException _size is smaller equal than 0
	 */
	public ParticleJetEffect(
			double _size,double _lifeInSec,
			double _width,double _angle,
			double _distAngle,double _speed,
			int _interval,Color _color,
			int _maxParticle,double _FPS,
			double _xFromBound,double _yFromBound)throws IllegalArgumentException {
		if(!(_maxParticle > 0))throw new IllegalArgumentException("ParticleJetEffect _maxParticle must be greater than 1");
		if(!(_FPS > 0))throw new IllegalArgumentException("ParticleJetEffect _FPS must be greater than 0");
		if(!(_color!=null))throw new IllegalArgumentException("ParticleJetEffect _color is null");
		if(!(_distAngle>=0))throw new IllegalArgumentException("ParticleJetEffect _distAngle must be greater euqlan than 0");
		if(!(_speed>0))throw new IllegalArgumentException("ParticleJetEffect _speed must be greater than 0");
		if(!(_interval > 0))throw new IllegalArgumentException("ParticleJetEffect _interval must be greater than 0");
		if(!(_width > 0))throw new IllegalArgumentException("ParticleJetEffect _width must be greater than 0");
		if(!(_lifeInSec > 0))throw new IllegalArgumentException("ParticleJetEffect _life must be greater tha 0");
		if(!(_size>0))throw new IllegalArgumentException("ParticleJetEffect _size must be greater than 0");
		
		
		
		maxParticle = _maxParticle;
		FPS = _FPS;
		rx = _xFromBound;
		ry = _yFromBound;
		color = _color;
		angle = _angle;
		distAngle = _distAngle;
		speed = _speed;
		interval = _interval;
		width = _width;
		time = 0;
		life = _lifeInSec;
		size = _size;
		for(int i = 0 ; i < maxParticle;i++) {
			Particle p = new Particle();
			particles.add(p);
		}	
	}
	
	@Override
	//!runs jet effect
	/*!
	 * \param _sender the object that defuses jet effect
	 * \param _g GDI
	 */
	public void BeginDraw(GameCharacter _sender, Graphics _g) {
		// TODO Auto-generated method stub
		if(!running)return;
		if(_sender==null) {
			System.out.println("ParticleJetEffect.BeginDraw _sender is null");
			return ;
		}
		
		Rect bounds = _sender.GetBounds();
		double zx = bounds.x + rx;
		double zy = bounds.y + ry;
		double cx = width * Math.random() - width/2.0;
		double shotAngle = distAngle * Math.random() - distAngle/2.0;
		double dx = (speed / FPS) * (Math.random()+0.1)*Math.cos(shotAngle+angle);
		double dy = (speed / FPS) * (Math.random()+0.1)*Math.sin(shotAngle+angle);
		int lifeInFrame = (int)(life * FPS * (Math.random()+0.1));
		if(time % interval == 0 && !particles.isEmpty()) {
			Particle p = particles.poll();
			p.dx = dx;
			p.dy = dy;
			p.life = lifeInFrame;
			p.time = 0;
			p.x = zx + cx;
			p.y = zy;
			
			pwParticles.add(p);
		}
		
		for(Particle i : pwParticles) {
			i.Move();
			_g.setColor(
					new Color(
							color.getRed(),color.getGreen(),color.getBlue(),
							(int)(255*((double)i.life-(double)i.time)/(double)i.life)
							)
					);
			
			_g.drawOval(
					(int)(i.x-size/2.0),
					(int)(i.y-size/2.0),
					(int)size,(int)size
					);
			i.time++;
		}
		
		
		time++;
	}

	private List<Particle> temp = 
			new ArrayList<Particle>();
	@Override
	//!kill and re-enqueue particle for reusing
	/*!
	 * \param _sender no use
	 * \param _g no use
	 */
	public void EndDraw(GameCharacter _sender, Graphics _g) {
		// TODO Auto-generated method stub
		if(!running)return;
		
		temp.clear();
		for(Particle i : pwParticles) {
			if(i.time > i.life) {
				temp.add(i);
			}
		}
		pwParticles.removeAll(temp);
		particles.addAll(temp);
	}

	@Override
	//!play jet effect
	/*!
	 */
	public void BeginPlay() {
		// TODO Auto-generated method stub
		if(running)return;
		running = true;
	}

	@Override
	//!force jet effect exit
	/*!
	 */
	public void EndPlay() {
		// TODO Auto-generated method stub
		if(!running)return;
		running = false;
	}

}
