package GameComponents;

//!generic observer interface
/*!
	you can use some observers in same way
	\author n.ryouta
*/
public interface IObserver<T> {
	//!add listener into this observer
	/*!
		\param _listener the listener that will receive the notifications from this
	*/
	void AddListener(T _listener);

	//!remove listener from this observer
	/*!
		\param _listener the listener object that you want to remove from. this observer
		\returns if this observer has the specified listener object, true. otherwise, false.
	*/
	boolean RemoveListener(T _listener);
}
