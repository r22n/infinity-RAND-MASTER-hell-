package GameComponents;

import java.awt.Graphics;
import java.awt.Point;

//!draws the progress bar in the screen
/*!
	\author n.ryouta
*/
public class ProgressBar extends ScoreBoard {	
	//!top border flag in logical or
	/*!
		\see SetBorderStyle
	*/
	public static final int BORDER_TOP = 		0x00000001;
	//!bottom border flag in logical or
	/*!
		\see SetBorderStyle
	*/
	public static final int BORDER_BOTTOM = 	0x00000002;
	//!left border flag in logical or
	/*!
		\see SetBorderStyle
	*/
	public static final int BORDER_LEFT =		0x00000004;
	//! right border flag in logical or
	/*!
		\see SetBorderStyle
	*/
	public static final int BORDER_RIGHT = 	0x00000008;
	
	private int border = BORDER_BOTTOM | BORDER_LEFT | BORDER_RIGHT;
	private double value=0;
	private double min=0,max=1;
	private double rx,ry;
	private double height=1;
	//!set the value of progress bar 
	/*!
		set the value is in range of min and max that provided by SetMinValue and SetMaxValue.
		\param _value the value of progress
		\exception IllegalArgumentException _ value is not in the range 
		\see SetMinValue
		\see SetMaxValue
	*/
	public void SetValue(double _value) throws IllegalArgumentException {
		if(!(min <= _value && _value <= max))throw new IllegalArgumentException("ProgressBar.SetValue _value must be in [min,max]");
		value = _value;
	}
	//!get the value of progress
	/*!
		get the value of this progress bar
		\returns the value of progress
	*/
	public double GetValue(){return value;}
	//!set minimum value of progress bar
	/*!
		the value was set will be used in SetValue method and Draw body.
		\param _minValue the minimum value
		\exception IllegalArgumentException _minValue is not smaller than max value that provided by GetMaxValue
		\see SetMaxValue
	*/
	public void SetMinValue(double _minValue)throws IllegalArgumentException {
		if(!(_minValue < max))throw new IllegalArgumentException("ProgressBar.SetMinValue _minValue must be smaller than max");
		min = _minValue;
	}
	//!get the minimum value of this progress bar
	/*!
		\returns minimum value
	*/
	public double GetMinValue(){return min;}

	//!set maximum value of progress bar
	/*!
		the value was set will be used in SetValue method and Draw body.
		\param _maxValue the maximum value
		\exception IllegalArgumentException _maxValue is not greater than min value that provided by SetMinValue
		\see SetMinValue
	*/
	public void SetMaxValue(double _maxValue)throws IllegalArgumentException {
		if(!(min < _maxValue))throw new IllegalArgumentException("ProgressBar.SetMaxValue _maxValue must be greater than min");
		max = _maxValue;
	}
	//!get the maximum value of this progress bar
	/*!
		\returns maximum value
	*/
	public double GetMaxValue(){return max;}
	//!get bit flags border visibillity
	/*!
		\returns border bit flags
	*/
	public int GetBorderStyle(){return border;}
	//!set border bit flags
	/*!
		\param _style bit flags that is border visibility
	*/
	public void SetBorderStyle(int _style) throws IllegalArgumentException {
		if(!(BORDER_TOP <= _style && _style <= BORDER_RIGHT)) throw new IllegalArgumentException("ProgressBar.SetBorderStyle __style must be in range of valid style");
		border = _style;
	}
	//!set the relative position from the bounds of component
	/*!
		\param _rx relative x 
		\param _ry relative y
	*/
	public void SetRelativePos(double _rx,double _ry) {
		rx = _rx;
		ry = _ry;
	}
	//!set the progress bar gauge height in percentage with height of component bounds 
	/*!
		\param _percentage the percentage of progress bar height in percentage
		\exception IllegalArgumentException _percentage is not in the range of [0,1]
	*/
	public void SetGaugeHeightInPercentage(double _percentage)throws IllegalArgumentException {
		if(!(0 <= _percentage && _percentage <= 1.0))throw new IllegalArgumentException("ProgressBr.SetGaugeHeightInPercentage _percentage must be in [0,1]");
		height = _percentage;
	}
	//!get the height of progress bar gauge in percentage with height of component bounds
	/*! 
		\returns the height of progress bar height in percentage
	*/
	public double GetGaugeHeightInPercentage(){return height;}
	//!draw the progress bar in screen
	/*!
		draw the progress bar with configured style that includes the color of super class 
		\param _parent the parent object that contains this
		\param _g the GDi object
	*/
	public void EndDraw(Component _parent,Graphics _g) throws Exception {
		super.EndDraw(_parent, _g);
		
		final Rect bounds = GetBounds();
		final int x = (int)(bounds.x + rx);
		final int y = (int)(bounds.y + ry);
		final int w = (int)(bounds.w);
		final int h = (int)(bounds.h);
		final int l = x;
		final int r = x+w;
		final int t = y;
		final int b = y+h;
		
		_g.setColor(GetColor());
		if((border & BORDER_TOP) > 0) {
			_g.drawLine(
					l,t,
					r,t
					);
		}

		if((border & BORDER_BOTTOM) > 0) {
			_g.drawLine(
					l,b,
					r,b
					);
		}
		if((border & BORDER_LEFT) > 0) {
			_g.drawLine(
					l,t,
					l,b
					);
		}
		if((border & BORDER_RIGHT) > 0) {
			_g.drawLine(
					r,t,
					r,b
					);
		}
		_g.fillRect(
				l,
				(int)(bounds.y+ry+bounds.h - bounds.h*height),
				(int)(bounds.w*(value-min)/(max-min)),
				(int)(bounds.h*height)
				);
	}
}
