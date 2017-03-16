package GameComponents;

//!generic abstract factory pattern object
/*!
	you can use some factories in same way
	\author n.ryouta
*/
public interface Factory<T> {
	//!create the object that prepared by user
	/*!
		\warning you must implement this
	*/
	T CreateObject();
}
