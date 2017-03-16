package AwtInputBridges;
import java.awt.event.MouseEvent;

import GameComponents.*;

//!simple adapter from MouseListener to MouseBridge.Listener
/*!
 * this object adapts the notifications from MouseListener to MouseBridge.Listener
 * \author n.ryouta
 */
public class AwtMouseBridge 
	extends MouseBridge
	implements java.awt.event.MouseListener ,
	java.awt.event.MouseMotionListener {
	

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	//!put the mouse button press information to mouse button, status map
	/*!
	 * this method puts the button press information from awt
	 * \param e event argument from awt
	 */
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		SetMouseButton(e.getButton(),true);
		try {
			NotifyBeginMouseButtonDown();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}

	@Override
	//!put the mouse button release information to mouse button, status map
	/*!
	 * this method puts the button release information from awt
	 * \param e event argument from awt
	 */
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

		SetMouseButton(e.getButton(),false);
		try {
			NotifyBeginMouseButtonDown();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	@Override
	//!set the mouse location of this object
	/*!
	 * \param e event argument from awt
	 */
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub

		SetXLocation(e.getX());
		SetYLocation(e.getY());
	}

	@Override
	//!set the mouse location of this object
	/*!
	 * \param e event argument from awt
	 */
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		SetXLocation(e.getX());
		SetYLocation(e.getY());
	}
	
}
