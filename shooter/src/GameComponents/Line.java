package GameComponents;

import java.awt.Color;
import java.awt.Graphics;

//!the line for drawing the line segment in the screen
/*!
	\author n.ryouta
*/
public class Line extends ComponentContainer{
	private Vector2 x0 = new Vector2(0,0);
	private Vector2 x1 = new Vector2(0,0);
	private Color color;
	//!get the location of first point of segment
	/*!
		\returns the first location of point
	*/
	public Vector2 GetX0(){return x0;}
	//!set the first location of segment
	/*!
		\param _v the first point of segment that will be copied
		\exception IllegalArgumentException _v is null
	*/
	public void SetX0(Vector2 _v)throws IllegalArgumentException {
		if(!(_v!=null))throw new IllegalArgumentException("Line.SetX0 _v is null");
		x0.Copy(_v);
	}
	
	//!get the location of second point of segment
	/*!
		\returns the second location of point
	*/
	public Vector2 GetX1(){return x1;}

	//!set the second location of segment
	/*!
		\param _v the second point of segment that will be copied
		\exception IllegalArgumentException _v is null
	*/
	public void SetX1(Vector2 _v)throws IllegalArgumentException {
		if(!(_v!=null))throw new IllegalArgumentException("Line.SetX1 _v is null");
		x1.Copy(_v);
	}
	//!get the color of this line segment
	/*!
		\returns the color of this
	*/
	public Color GetColor(){return color;}
	//!set the color of this
	/*!
		\param _color the color of this
		\exception IllegalArgumentException _color is null
	*/
	public void SetColor(Color _color)throws IllegalArgumentException {
		if(!(_color!=null))throw new IllegalArgumentException("Line.SetColor _color is null");
		color = _color;
	}
	//!draw the segment with settings
	/*! 
		this method ignores complement GetBounds, and draw the line segment with first and second location in specified color
	*/
	public void BeginDraw(Component _parent,Graphics _g) {
		_g.setColor(color);
		_g.drawLine(
				(int)x0.x,(int)x0.y,
				(int)x1.x,(int)x1.y
				);
	}
	
}
