package AwtInputBridges;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import GameComponents.*;

//!simple Adapter from KeyListener to KeyBridge.Listener
/*!
 * this object adapts notifications from KeyListener as KeyBridge.Listener
 * \author n.ryouta
 */
public class AwtKeyBridge 
	extends KeyBridge
	implements KeyListener{
	
	
	@Override
	//!put the key-press status to virtual-keycode, status map
	/*!
	 * calling this method from the awt, this puts the key-press status 
	 * the value of true related with arg0.getKeyCode() 
	 * \param arg0 parameter of KeyListener
	 */
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		SetKeyDown(arg0.getKeyCode(),true);
		try {
			NotifyBeginKeyDown();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	//!set the key-release information to virtual-keycode, status map
	/*!
	 * calling this method from the awt, this puts the key-press status 
	 * the value of false related with arg0.getKeyCode() 
	 * \param arg0 parameter of KeyListener
	 */
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		SetKeyDown(arg0.getKeyCode(),false);
		try {
			NotifyEndKeyDown();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	//!empty implementation
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
}
