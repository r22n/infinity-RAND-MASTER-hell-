package GameComponents;

//!circle definition as the geometric
/*!
 * \author n.ryouta
 */
public class Circle {
	//!open member that is center x location of this circle
	/*!
	 * this member is open for typically circle
	 */
	public double x;
	//!open member that is center y location of this circle
	/*!
	 * this member is open for typically circle
	 */
	public double y;
	
	//!open member that is radius of this circle
	/*!
	 * this circle definition allows negative radius
	 */
	public double radius;
	
	//!initializer with center location and radius
	/*!
	 * \param _x x center location 
	 * \param _y y center location
	 * \param _radius radius of this circle that was allowed negative
	 */
	public Circle(double _x,double _y,double _radius) {
		x = _x;
		y = _y;
		radius = _radius;
	}
	//!copy all values from the other circle object
	/*!
	 * copy all values including location and radius
	 * \param _c the other circle object
	 * \exception IllegalArgumentException _c is null
	 */
	public void Copy(Circle _c)  throws IllegalArgumentException{
		if(!(_c!=null))throw new IllegalArgumentException("Circle.Copy _c is null");
		x = _c.x;
		y = _c.y;
		radius = _c.radius;
	}
	//!check intersect with other circle object geometrically
	/*!
	 * this method returns the status that this circle intersects with other
	 * \param _c checking other circle
	 * \exception IllegalArgumentException _c is null  
	 */
	public boolean Intersect(Circle _c) throws IllegalArgumentException {
		if(!(_c!=null))throw new IllegalArgumentException("Circle.Intersect _c is null");
		double dx = x-_c.x;
		double dy = y-_c.y;
		double dr =(radius + _c.radius);
		return (dx*dx + dy*dy) <= dr*dr;
	}
}
