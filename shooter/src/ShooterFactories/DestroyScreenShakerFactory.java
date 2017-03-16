package ShooterFactories;

import GameComponents.*;
import ShooterComponents.LifeCharacter;

//!the factory creates screen shaker
/*!
 * \author n.ryouta
 */
public class DestroyScreenShakerFactory implements Factory<Component>{
	private LifeCharacter destroy;
	private double sx,sy;
	private double FPS;
	private ComponentContainer container;
	private IScreenDrawPos spos;
	
	//!initialize
	/*!
	 * \param _destroyTarget when this character destroyed, shakes screen
	 * \param _sx initial speed x
	 * \param _sy initial speed y
	 * \param _spos screen position bridge
	 * \param _container the container that will be stored 
	 * \exception IllegalArgumentException _destroyTarget is null
	 * \exception IllegalArgumentException _container is null
	 * \exception IllegalArgumentException _spos is null_spos
	 * \exception IllegalArgumentException _FPS is smaller equal than 0
	 */
	public DestroyScreenShakerFactory(
			LifeCharacter _destroyTarget,
			double _sx,double _sy,double _FPS,
			IScreenDrawPos _spos,
			ComponentContainer _container
			)throws IllegalArgumentException {
		if(!(_destroyTarget!=null))throw new IllegalArgumentException("DestroyScreenShakerFactory _destroyTarget is null");
		if(!(_container!=null))throw new IllegalArgumentException("DestroyScreenShakerFactory _container is null");
		if(!(_spos!=null))throw new IllegalArgumentException("DestroyScreenShakerFactory _spos is null");
		if(!(_FPS > 0))throw new IllegalArgumentException("DestroyScreenShakerFactory _FPS must be greater than 0");
		destroy = _destroyTarget;
		sx = _sx;
		sy = _sy;
		FPS = _FPS;
		spos = _spos;
		container = _container;
	}
		

	@Override
	//!create ScreenShaker object
	/*!
	 * this method returns ScreenShaker object that shakes screen when the target in the initializer was destroyed
	 * \returns ScreenShaker object
	 */
	public Component CreateObject() {
		// TODO Auto-generated method stub
		final ScreenShaker result = 
				new GameComponents.ScreenShaker(
						5,
						0.2,
						0.1,
						FPS,
						10,
						spos
						);
						
		destroy.AddLifeCharacterListener(
				new LifeCharacter.Listener() {
					public void OnUnderMinLife(LifeCharacter _sender) {
						// TODO Auto-generated method stub
						result.BeginShake(sx, sy);
					}
					public void OnOverMaxLife(LifeCharacter _sender) {}
					public void OnIncreaseLife(LifeCharacter _sender, double _life) {}
					public void OnDecreaseLife(LifeCharacter _sender, double _life) {}
				}
				);
		try {
			container.AddChild(result);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

}
