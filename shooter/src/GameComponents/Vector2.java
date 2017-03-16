package GameComponents;

//!geometric definition of 2 dimensional vector with real element
/*!
 * \author n.ryouta
 */
public class Vector2 {
	//!x element
	public double x;
	//!y element
	public double y;
	
	//!initialize vector 
	/*!
	 * \param _x the x element
	 * \param _y the y element
	 */
	public Vector2(double _x,double _y) {
		x = _x;
		y = _y;
	}
	//!copy all members from other object
	/*!
	 * \param _srcCopy the other vector object
	 * \exception IllegalArgumentException _srcCopy is null
	 */
	public void Copy(Vector2 _srcCopy)throws IllegalArgumentException {
		if(!(_srcCopy!=null))throw new IllegalArgumentException("Vector2.Copy _srcCopy is null");
		x = _srcCopy.x;
		y =_srcCopy.y;
	}
	//!yield the norlm that calculated with sqrt of dx*dx + dy*dy
	/*!
	 * \returns the norlm
	 */
	public double Norlm() {
		return Math.sqrt(x*x+y*y);
	}
}
