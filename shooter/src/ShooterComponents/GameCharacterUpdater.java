package ShooterComponents;

//!update strategy of GameCharacter
/*!
 * \author n.ryouta
 */
public interface GameCharacterUpdater {
	//!begin update
	/*!
	 * \param _sender the GameCharacter wants to update
	 */
	void BeginUpdate(GameCharacter _sender);
	//!end update
	/*!
	 * \param _sender the GameCharacter wants to update
	 */
	void EndUpdate(GameCharacter _sender);
}
