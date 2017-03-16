package Tests;

import GameComponents.ComponentContainer;
import GameComponents.Component;

public class ComponentContainerAddTest implements ComponentContainer.Visitor{
	boolean result = false;
	public boolean GetResult(){return result;}
	public String toString(){return String.valueOf(result);}
	@Override
	public void Visit(ComponentContainer _this) {
		// TODO Auto-generated method stub
		Component test = new Component() ;
		try {
			_this.AddChild(test);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		result = _this.HasComponent(test);
	}
	
}
