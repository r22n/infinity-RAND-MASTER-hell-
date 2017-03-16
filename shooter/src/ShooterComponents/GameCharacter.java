package ShooterComponents;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import GameComponents.Component;
import GameComponents.ComponentContainer;

//!the basic game character that exposes in the screen and updates strategy
/*!
 * \author n.ryouta
 */
public class GameCharacter extends ComponentContainer{
	//!handler of GameCharacter
	/*!
	 * \author n.ryouta
	 */
	public interface Listener {
		//!be notified the GameCharacter object removes drawer
		/*!
		 * \param _sender the object calls this method
		 * \param _drawer the drawer object
		 */
		void OnRemoveGameCharacterDrawer(GameCharacter _sender,GameCharacterDrawer _drawer);
		
		//!be notified the GameCharacter object removes updater
		/*!
		 * \param _sender the object calls this method
		 * \param _updater the updater object
		 */
		void OnRemoveGameCharacterUpdater(GameCharacter _sender,GameCharacterUpdater _updater);
		
		//!be notified the GameCharacter adds drawer
		/*!
		 * \param _sender the object calls this method
		 * \param _drawer the drawer object that was added into _sender 
		 */
		void OnAddGameCharacterDrawer(GameCharacter _sender,GameCharacterDrawer _drawer);
		
		//!be notified the GameCharacter adds updater
		/*!
		 * \param _sender the object calls this method
		 * \param _updater the updater object that was added into _sender
		 */
		void OnAddGameCharacterUpdater(GameCharacter _sender,GameCharacterUpdater _updater);
	}
	private List<Listener> listeners =
			new ArrayList<Listener>();
	//!add handler
	/*!
	 * \param _listener the handler object
	 * \exception IllegalArgumentException _listener is null
	 * \warning if this GameCharacter is notifying to listeners, you must not call this method
	 */
	public void AddGameCharacterListener(Listener _listener)throws IllegalArgumentException {
		if(!(_listener!=null))throw new IllegalArgumentException("GameCharacter.AddGameCharacterListener _listener is null");
		listeners.add(_listener);
	}
	//!remove handler
	/*!
	 * \param _listener that you want to remove from this
	 * \returns if the _listener was contained by this, true. otherwise, false.
	 * \warning if this GameCharacter is notifying to listeners, you must not call this method
	 */
	public boolean RemoveGameCharacterListener(Listener _listener) {	
		if(_listener==null)return false;
		return listeners.remove(_listener);
	}
	//!notify the event that the GameCharacter adds drawer
	/*!
	 * \param _drawer the drawer object that will be added
	 */
	protected void NotifyAddGameCharacterDrawer(GameCharacterDrawer _drawer) {
		for(Listener i : listeners) {
			i.OnAddGameCharacterDrawer(this, _drawer);
		}
	}
	
	//!notify the event that the GameCharacter adds updater
	/*!
	 * \param _updater the updater object that will be added
	 */
	protected void NotifyAddGameCharacterUpdater(GameCharacterUpdater _updater) {
		for(Listener i : listeners ) {
			i.OnAddGameCharacterUpdater(this, _updater);
		}
	}
	//!notify the event that the GameCharacter removes drawer
	/*!
	 * \param _drawer the drawer object that will be removed
	 */
	protected void NotifyRemoveGameCharacterDrawer(GameCharacterDrawer _drawer) {
		for(Listener i : listeners) {
			i.OnRemoveGameCharacterDrawer(this, _drawer);
		}
	}
	//!notify the event that the GameCharacter removes updater
	/*!
	 * \param _updater the updater object that will be removed
	 */
	protected void NotifyRemoveGameCharacterUpdater(GameCharacterUpdater _updater) {
		for(Listener i : listeners ) {
			i.OnRemoveGameCharacterUpdater(this, _updater);
		}
	}
	
	private List<GameCharacterUpdater> updaters = new ArrayList<GameCharacterUpdater>();
	private List<GameCharacterDrawer> drawers = new ArrayList<GameCharacterDrawer>();
	
	//!add game character drawer of this
	/*!
	 * \param _drawer the drawer object that will render this character into screen
	 * \exception IllegalArgumentException _drawer is null
	 * \warning if this GameCharacter is notifying to drawers, you must not call this method
	 */
	public void AddGameCharacterDrawer(GameCharacterDrawer _drawer)throws IllegalArgumentException {
		if(!(_drawer!=null))throw new IllegalArgumentException("GameCharacter.AddGameCharacterDrawer _drawer is null");
		drawers.add(_drawer);
		NotifyAddGameCharacterDrawer(_drawer);
	}
	//!remove game character drawer of this
	/*!
	 * \param _drawer the drawer object that you want to remove
	 * \exception IllegalArgumentException _drawer is null
	 * \warning if this GameCharacter is notifying to drawers, you must not call this method
	 */
	public boolean RemoveGameCharacterDrawer(GameCharacterDrawer _drawer) {
		if(_drawer==null)return false;
		boolean result = drawers.remove(_drawer);
		if(result) {
			NotifyRemoveGameCharacterDrawer(_drawer);
		}
		return result;
	}
	//!get all drawers
	/*!
	 * \returns the iterable object that contains all drawers
	 */
	public Iterable<GameCharacterDrawer> GetGameCharacterDrawers(){
		return drawers;
	}

	//!add game character updater of this
	/*!
	 * \param _updater the drawer object that will render this character into screen
	 * \exception IllegalArgumentException _updater is null
	 * \warning if this GameCharacter is notifying to updaters, you must not call this method
	 */
	public void AddGameCharacterUpdater(GameCharacterUpdater _updater)throws IllegalArgumentException {
		if(!(_updater!=null))throw new IllegalArgumentException("GameCharacter.AddGameCharacterUpdater _updater is null");
		updaters.add(_updater);
		NotifyAddGameCharacterUpdater(_updater);
	}

	//!remove game character updater of this
	/*!
	 * \param _updater the drawer object that you want to remove
	 * \exception IllegalArgumentException _updater is null
	 * \warning if this GameCharacter is notifying to updaters, you must not call this method
	 */
	public boolean RemoveGameCharacterUpdater(GameCharacterUpdater _updater) {
		if(_updater==null)return false;
		boolean result = updaters.remove(_updater);
		if(result) {
			NotifyRemoveGameCharacterUpdater(_updater);
		}
		return result;
	}
	//!get all updaters
	/*!
	 * \returns the iterable object that contains all updaters
	 */
	public Iterable<GameCharacterUpdater> GetGameCharacterUpdaters(){
		return updaters;
	}
	//!make all drawers begin draw 
	/*!
	 * \param _parent the parent Component that contains this
	 * \param _g the GDI object
	 */
	public void BeginDraw(Component _parent,Graphics _g) throws Exception {
		super.BeginDraw(_parent, _g);
		for(GameCharacterDrawer i : drawers) {
			i.BeginDraw(this, _g);
		}
	}
	//!make all drawers end draw 
	/*!
	 * \param _parent the parent Component that contains this
	 * \param _g the GDI object
	 */
	public void EndDraw(Component _parent,Graphics _g) throws Exception {
		super.EndDraw(_parent, _g);
		for(GameCharacterDrawer i : drawers) {
			i.EndDraw(this, _g);
		}
	}
	//!make all drawers begin update 
	/*!
	 * \param _parent the parent Component that contains this
	 */
	public void BeginUpdate(Component _parent) throws Exception{
		super.BeginUpdate(_parent);
		for(GameCharacterUpdater i : updaters ) {
			i.BeginUpdate(this);
		}
	}
	//!make all drawers end update 
	/*!
	 * \param _parent the parent Component that contains this
	 */
	public void EndUpdate(Component _parent) throws Exception {
		super.EndUpdate(_parent);
		for(GameCharacterUpdater i : updaters ) {
			i.EndUpdate(this);
		}
	}
}
