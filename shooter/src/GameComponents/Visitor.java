package GameComponents;

//!generic visitor of T
/*!
 * \author n.ryouta
 */
public interface Visitor<T> {
	//!visit member 
	/*!
	 * \param _this the member is visited by this
	 */
	void Visit(T _this);
}
