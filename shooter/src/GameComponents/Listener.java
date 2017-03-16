package GameComponents;

//!the generic listener type
/*!
	\author n.ryouta
*/
public interface Listener<T,U> {
	//!be notified some event was handled
	/*!
		\param _sender the object calls this method
		\param _e the event arguments
	*/
	void OnListen(T _sender,U _e);
}
