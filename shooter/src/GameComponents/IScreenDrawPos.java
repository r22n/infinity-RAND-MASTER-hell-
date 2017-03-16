package GameComponents;

//!bridge of the screen image drawing offset
/*!
	this interface is bridge of the screen position for manipulating it
	/author n.ryouta
*/
public interface IScreenDrawPos {
	//!set x location of screen
	/*!
		\param _x x location of screen
	*/
	void SetX(double _x);
	//! set y location of screen
	/*!
		\param _y location of screen
	*/
	void SetY(double _y);
	
	//!get x location of screen
	/*!
		\returns x location of screen
	*/
	double GetX();
	//!get y location of screen
	/*!
		\returns y location of screen
	*/
	double GetY();
}
