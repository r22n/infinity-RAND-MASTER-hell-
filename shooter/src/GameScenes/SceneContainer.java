package GameScenes;

import java.awt.Graphics;

//!manage the scene such as changing scene
/*!
 * \author n.ryouta
 */
public class SceneContainer {
	private Scene current=null;
	private Scene next=null;
	private boolean lock = false;
	
	
	//!reserve next scene for changing to other 
	/*!
	 * \param _scene the next scene
	 * \exception IllegalArgumentException _scene is null
	 */
	public void ReserveScene(Scene _scene)throws IllegalArgumentException {
		if(!(_scene!=null))throw new IllegalArgumentException("SceneContainer.ReserveScene _scene is null");
		if(lock) {
			next = _scene;
		}else {
			current = _scene;
			current.PlayThisScene(this);
		}
		
	}
	//!update current scene
	/*!
	 * 
	 */
	public void Update() throws Exception {
		if(current!=null) {
			lock = true;
			current.Update();
			lock = false;
		}
		if(next!=null) {
			if(current!=null)current.StopThisScene(this);
			current = next;
			next = null;
			current.PlayThisScene(this);
		}
	}
	
	//!draw current scene
	/*!
	 */
	public void Draw(Graphics _g) throws Exception{
		if(current!=null){
			lock = true;
			current.Draw(_g);
			lock = false;
		}
		if(next!=null) {
			if(current!=null)current.StopThisScene(this);
			current = next;
			next = null;
			current.PlayThisScene(this);
		}
	}
}
