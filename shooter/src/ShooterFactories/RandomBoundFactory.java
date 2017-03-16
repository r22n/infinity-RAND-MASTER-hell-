package ShooterFactories;

import java.awt.Color;

import GameComponents.*;
import ShooterComponents.GameCharacter;

//!makes rectangle randomly
/*!
 * \author n.ryouta
 */
public class RandomBoundFactory implements Factory<Component> {
	private double sizeMin;
	private double sizeMax;
	private ComponentContainer container;
	private double width,height;
	private Color baseColor;
	//!initialize
	/*!
	 * \param _baseColor the color thats opacity will be inversed
	 * \param _scrWidth width of screen
	 * \param _scrHeight height of screen
	 * \param _sizeMin random rectangle minimum size
	 * \param _sizeMax random rectangle maximum size
	 * \exception IllegalArgumentException _sizeMin is smaller equal than 0
	 * \exception IllegalArgumentException _sizeMax is smaller equal than 0
	 * \exception IllegalArgumentException _sizeMax is smaller equal than _sizeMin
	 * \exception IllegalArgumentException _container is null 
	 * \exception IllegalArgumentException _scrWidth is smaller equal than 0
	 * \exception IllegalArgumentException _scrHeight is smaller equal than 0
	 * \exception IllegalArgumentException _baseColor is null 
	 * 
	 */
	public RandomBoundFactory(
			Color _baseColor,
			double _scrWidth,double _scrHeight,
			double _sizeMin,double _sizeMax,
			ComponentContainer _container)throws IllegalArgumentException {
		if(!(_sizeMin > 0))throw new IllegalArgumentException("RandomBoundFactory _sizeMin must be greater than 0");
		if(!(_sizeMax > 0))throw new IllegalArgumentException("RandomBoundFactory _sizeMax must be greater than 0");
		if(!(_sizeMax > _sizeMin))throw new IllegalArgumentException("RandomBoundFactory _sizeMax must be greater than _sizeMin");
		if(!(_container!=null))throw new IllegalArgumentException("RandomBoundFactory _container is null");
		if(!(_scrWidth >0))throw new IllegalArgumentException("RandomBoundFactory _srcWidth must be greater than 0");
		if(!(_scrHeight >0))throw new IllegalArgumentException("RandomBoundFactory _srcHeight must be greater than 0");
		if(!(_baseColor!=null))throw new IllegalArgumentException("RandomBoundFactory _baseColor is null");
		
		sizeMin = _sizeMin;
		sizeMax = _sizeMax;
		container = _container;
		width = _scrWidth;
		height = _scrHeight;
		baseColor = _baseColor;
	}
		
		
	
	@Override
	//!create object
	/*!
	 * \returns GameCharacter that was set up to draw rectangle at my bounds
	 */
	public Component CreateObject() {
		// TODO Auto-generated method stub
		double x = (width+sizeMin)*Math.random() - sizeMin/2.0;
		double y = (height+sizeMin)*Math.random() - sizeMin/2.0;
		double w = (sizeMax-sizeMin)*Math.random()+sizeMin;
		double h = (sizeMax-sizeMin)*Math.random()+sizeMin;
		double red = baseColor.getRed();
		double green = baseColor.getGreen();
		double blue = baseColor.getBlue();
		double opacity = baseColor.getAlpha();
		
		GameCharacter result = new GameCharacter();
		Rect b = result.GetBounds();
		b.x = x;
		b.y = y;
		b.w = w;
		b.h = h;
		
		result.AddGameCharacterDrawer(
				new ShooterDrawers.ComponentBound(
						false, 
						new Color((int)red,(int)green,(int)blue,(int)((255-opacity)*Math.random()))
						)
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
