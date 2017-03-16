package ShooterComponents;

import java.awt.Graphics;

//!render strategy of GameCharacter
/*!
 * \author n.ryouta
 */
public interface GameCharacterDrawer {
	//!begin draw
	/*!
	 * \param _sender the GameCharacter wants to draw
	 * \param _g the GDI object
	 */
	void BeginDraw(GameCharacter _sender,Graphics _g);
	
	//!end draw
	/*!
	 * \param _sender the GameCharacter wants to draw
	 * \param _g the GDI object
	 */
	void EndDraw(GameCharacter _sender,Graphics _g);
}
