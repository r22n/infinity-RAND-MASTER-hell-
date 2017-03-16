package ShooterDrawers;

import java.awt.Color;
import java.awt.Graphics;

import ShooterComponents.GameCharacter;
import ShooterComponents.GameCharacterDrawer;
import GameComponents.*;
import java.util.*;

//!draws the Fighter character in the screen
/*!
 * draws the geometric regular N square in the screen
 * \author n.ryouta
 */
public class FighterA implements GameCharacterDrawer,IEffect {
	private double size;
	private boolean fill;
	private Color color;
	private int edge;
	private double[] ex;
	private double[] ey;
	private boolean running = true;
	//!initializer
	/*!
	 * \param _fill if you want to fill, true.
	 * \param _color the color of this
	 * \param _size the circumscribed circle's radius
	 * \param _edge the number of edge
	 * \exception IllegalArgumentException _size is smaller equal than 0
	 * \exception IllegalArgumentException _color is null
	 * \exception IllegalArgumentException _edge is smaller than 3
	 * 
	 */
	public FighterA(boolean _fill,Color _color,double _size,int _edge)throws IllegalArgumentException {
		if(!(_size > 0))throw new IllegalArgumentException("FighterA _size must be greater than 0");
		if(!(_color!=null))throw new IllegalArgumentException("FighterA _color is null");
		if(!(_edge>=3))throw new IllegalArgumentException("FighterA _edge must be greater equal than 3");
		
		size = _size;
		color = _color;
		fill = _fill;
		edge = _edge;
		
		ex = new double[edge];
		ey = new double[edge];
		for(int i = 0 ; i < edge;i++) {
			double rx = size * Math.cos(2.0*Math.PI / edge * i - Math.PI/2.0);
			double ry = size * Math.sin(2.0*Math.PI/edge*i - Math.PI/2.0);
			ex[i] = rx;
			ey[i] = ry;
		}
	}
	

	//!initializer with angle
	/*!
	 * \param _fill if you want to fill, true.
	 * \param _color the color of this
	 * \param _size the circumscribed circle's radius
	 * \param _edge the number of edge
	 * \param _angle the angle of Fighter
	 * \exception IllegalArgumentException _size is smaller equal than 0
	 * \exception IllegalArgumentException _color is null
	 * \exception IllegalArgumentException _edge is smaller than 3
	 * 
	 */
	public FighterA(boolean _fill,Color _color,double _size,int _edge,double _angle)throws IllegalArgumentException {
		if(!(_size > 0))throw new IllegalArgumentException("FighterA _size must be greater than 0");
		if(!(_color!=null))throw new IllegalArgumentException("FighterA _color is null");
		if(!(_edge>=3))throw new IllegalArgumentException("FighterA _edge must be greater equal than 3");
		
		size = _size;
		color = _color;
		fill = _fill;
		edge = _edge;
		
		ex = new double[edge];
		ey = new double[edge];
		for(int i = 0 ; i < edge;i++) {
			double rx = size * Math.cos(2.0*Math.PI / edge * i - Math.PI/2.0 + _angle);
			double ry = size * Math.sin(2.0*Math.PI/edge*i - Math.PI/2.0 + _angle);
			ex[i] = rx;
			ey[i] = ry;
		}
	}
	
	private int[] tx = null;
	private int[] ty = null;
	@Override
	
	//!draws Fighter character in the screen 
	/*!
	 * draws Fighter character at the relative position + center of _sender bounds with the angle that provided by initializer
	 * \param _sender the character
	 * \param _g GDI
	 */
	public void BeginDraw(GameCharacter _sender, Graphics _g) {
		// TODO Auto-generated method stub
		if(!running)return ;
		if(_sender==null) {
			System.out.println("FighterA _sender is null");
			return;
		}
		if(tx==null)tx = new int[edge];
		if(ty==null)ty = new int[edge];
		
		Rect bound = _sender.GetBounds();
		double cx = bound.x + bound.w/2;
		double cy = bound.y + bound.h/2;
		
		_g.setColor(color);
		for(int i = 0 ; i < edge;i++) {
			tx[i] = (int)(ex[i] + cx);
			ty[i] = (int)(ey[i] + cy);
		}
		if(fill) {
			_g.fillPolygon(tx, ty, edge);
		}else {
			_g.drawPolygon(tx,ty,edge);
		}
	}

	@Override
	//!empty
	/*!
	 */
	public void EndDraw(GameCharacter _sender, Graphics _g) {
		// TODO Auto-generated method stub
		
	}
	//!turn the visibility true
	/*!
	 */
	public void BeginPlay() {
		if(running)return;
		running = true;
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
