package ShooterComponents;

import java.util.ArrayList;
import java.util.List;

//!life character that can die with life
/*!
 * \author n.ryouta
 */
public class LifeCharacter extends HitableGameCharacter {
	//!handler about LifeCharacter
	/*!
	 * \author n.ryouta
	 */
	public interface Listener {
		//!be notified the LifeCharacter increases life
		/*!
		 * \param _sender the object calls this method
		 * \param _life the life that was increased
		 */
		void OnIncreaseLife(LifeCharacter _sender,double _life);
		//!be notified the LifeCharacter decreases life
		/*!
		 * \param _sender the object calls this method
		 * \param _life the life that was decreased
		 */
		void OnDecreaseLife(LifeCharacter _sender,double _life);
		//!be notified the LifeCharacter overshoots life
		/*!
		 * \param _sender the object calls this method
		 */
		void OnOverMaxLife(LifeCharacter _sender);
		//!be notified the LifeCharacter undershoots life
		/*!
		 * \param _sender the object calls this method
		 */
		void OnUnderMinLife(LifeCharacter _sender);
	}
	private List<Listener> listeners = 
			new ArrayList<Listener>();
	//!add handler into LifeCharacter
	/*!
	 * \param _listener the handler of this
	 * \exception IllegalArgumentException _listener is null
	 * \warning if this method is notifying some events, you must not call this method
	 */
	public void AddLifeCharacterListener(Listener _listener)throws IllegalArgumentException {
		if(!(_listener!=null))throw new IllegalArgumentException("LifeCharacter.AddLifeCharacterListener _listener is null");
		listeners.add(_listener);
	}
	//!remove handler from LifeCharacter
	/*!
	 * \param _listener the handler that you want to remove from this
	 * \returns if this has the _listener, true. otherwise, false.
	 * \warning if this method is notifying some events, you must not call this method
	 */
	public boolean RemoveLifeCharacterListener(Listener _listener) {
		if(_listener==null)return false;
		return listeners.remove(_listener);
	}
	//!notify this LifeCharacter increases life
	/*!
	 * \param _increase the life increased
	 */
	protected void NotifyIncreaseLife(double _increase) {
		for(Listener i : listeners) {
			i.OnIncreaseLife(this,_increase);
		}
	}
	//!notify this LifeCharacter decreases life
	/*!
	 * \param _increase the life increased
	 */
	protected void NotifyDecreaseLife(double _decrease) {
		for(Listener i : listeners ) {
			i.OnDecreaseLife(this, _decrease);
		}
	}
	//!notify this LifeCharacter overshoots life
	/*!
	 */
	protected void NotifyOverMaxLife() {
		for(Listener i : listeners) {
			i.OnOverMaxLife(this);
		}
	}
	//!notify this LifeCharacter undershoots life
	/*!
	 */
	protected void NotifyUnderMinLife() {
		for(Listener i : listeners) {
			i.OnUnderMinLife(this);
		}
	}
	
	private double life = 100;
	private double minValue = 0;
	private double maxValue = 100;
	
	//!get the life of LifeCharacter
	/*!
	 * \returns life
	 */
	public double GetLife(){return life;}
	//!set the life of LifeCharacter
	/*!
	 * \param _life the life that you want to set into this
	 * \exception IllegalArgumentException if _life is not in the range of [min,max]
	 * \see GetMinLife
	 * \see GetMaxLife
	 */
	protected void SetLife(double _life)throws IllegalArgumentException {
		if(!(GetMinLife() <= _life && _life <= GetMaxLife()))throw new IllegalArgumentException("LifeCharacter.SetLife _life must be in [minValue,maxValue]");
		life = _life;
	}
	//!get minimum life
	/*!
	 * \returns minimum life
	 */
	public double GetMinLife(){return minValue;}
	//!get maximum life
	/*!
	 * \returns maximum life
	 */
	public double GetMaxLife(){return maxValue;}
	
	//!set minimum life
	/*!
	 * \param _value the minimum life that you want to set 
	 * \exception IllegalArgumentException if the _value is greater equal than max
	 * \see GetMaxLife
	 */
	public void SetMinLife(double _value) throws IllegalArgumentException {
		if(!(_value < GetMaxLife()))throw new IllegalArgumentException("LifeCharacter.SetMinLife _value must be smaller than maxValue");
		minValue = _value;
	}
	//!set maximum life
	/*!
	 * \param _value the maximum life that you want to set
	 * \exception IllegalArgumentException if the _value is smaller equal than min
	 * \see GetMinLife
	 */
	public void SetMaxLife(double _value) throws IllegalArgumentException {
		if(!(_value > GetMinLife()))throw new IllegalArgumentException("LifeCharacter.SetMaxLife _value must be greater than minValue");
		maxValue = _value;
	}
	
	//!increase life
	/*!
	 * \param _increase the life that you want to increase
	 * \exception IllegalArgumentException if the _increase is smaller equal than 0
	 */
	public void IncreaseLife(double _increase)throws IllegalArgumentException {
		if(!(_increase >0))throw new IllegalArgumentException("LifeCharacter.IncreaseLife _increase must be greater than 0");
		life += _increase;
		NotifyIncreaseLife(_increase);
		if(GetLife() > GetMaxLife()) {
			SetLife(GetMaxLife());
			NotifyOverMaxLife();
		}
	}
	
	//!decrease life
	/*!
	 * \param _decrease the life that you want to decrease
	 * \exception IllegalArgumentException if the _decrease is smaller equal than 0
	 */
	public void DecreaseLife(double _decrease)throws IllegalArgumentException {
		if(!(_decrease >0))throw new IllegalArgumentException("LifeCharacter.IncreaseLife _decrease must be greater than 0");
		life -= _decrease;
		NotifyDecreaseLife(_decrease);
		if(GetLife() < GetMinLife()) {
			SetLife(GetMinLife());
			NotifyUnderMinLife();
		}	
	}
	
	
}
