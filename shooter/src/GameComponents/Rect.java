package GameComponents;

//!geometric definition of Rectangle
/*!
 * \author n.ryouta
 */
public class Rect {
	//!x location of rectangle
	public double x;
	//!y location of rectangle
	public double y;
	//!width of rectangle
	public double w;
	//!height of rectangle
	public double h;
	
	//!initialize rectangle with size of 30
	/*!
	 * location of x,y = 0
	 * size of width,height  = 30
	 */
	public Rect() {
		x = y = 0;
		w = h = 30;
	}
	//!initialize with argument
	/*!
	 * \param _x x location
	 * \param _y y location
	 * \param _w width
	 * \param _h height
	 */
	public Rect(double _x,double _y,double _w,double _h) {
		x = _x;
		y = _y;
		w = _w;
		h = _h;
	}
	//!copy from other object
	/*!
	 * copy all members from other rectangle
	 * \param _r other rectangle
	 * \exception IllegalArgumentException _r is null
	 */
	public void Copy(Rect _r) throws IllegalArgumentException{
		if(!(_r!=null))throw new IllegalArgumentException("Rect.Copy _r is null");
		
		x = _r.x;
		y = _r.y;
		w = _r.w;
		h = _r.h;
	}
	//!check the rectangle has the intersection with point
	/*!
	 * \param _x x location of point
	 * \param _y y location of point
	 */
	public boolean Intersect(double _x,double _y) {
		return (x <= _x && _x <= x + w) &&
				(y <= _y && _y <= y + h);
	}
	//!check the rectangle has the intersection with other one
	/*!
	 * \param _r the other rectangle
	 * \exception IllegalArgumentException _r is null
	 */
	public boolean Intersect(Rect _r) {
		if(!(_r!=null))throw new IllegalArgumentException("Rect.Intersect _r is null");
		double _right = _r.x+_r.w;
		double _top = _r.y+_r.h;
		double right = x+w;
		double top = y+h;
		return (x <= _right && _r.x <= right) &&
				(y <= _top && _r.y <= top);
	}
		
}
