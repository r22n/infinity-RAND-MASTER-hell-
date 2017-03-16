package Tests;

import java.awt.Color;
import java.awt.Graphics;

import GameComponents.*;
import GameScenes.*;
import ShooterComponents.*;

public class TestScene implements Scene.Visitor {

	@Override
	public void Visit(ComponentContainer _this) {
		// TODO Auto-generated method stub
		final Fighter test = new Fighter();
		final Rect b = test.GetBounds();
		b.x = 100;
		b.y = 100;
		test.AddGameCharacterUpdater(new GameCharacterUpdater() {
			
			@Override
			public void BeginUpdate(GameCharacter _sender) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void EndUpdate(GameCharacter _sender) {
				// TODO Auto-generated method stub
				b.x += 1;
				
			}
		});
		test.AddGameCharacterDrawer(new GameCharacterDrawer(){
			@Override
			public void BeginDraw(GameCharacter _sender, Graphics _g) {
				// TODO Auto-generated method stub
				_g.setColor(Color.white);
				int x = (int)_sender.GetBounds().x;
				int y = (int)_sender.GetBounds().y;
				int width = (int)_sender.GetBounds().w;
				int height = (int)_sender.GetBounds().h;
				
				_g.drawRect(x, y, width, height);
			}

			@Override
			public void EndDraw(GameCharacter _sender, Graphics _g) {
				// TODO Auto-generated method stub
				
			}
		});
		
		try {
			_this.AddChild(test);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
	public void Visit(Scene _this) {
		// TODO Auto-generated method stub
		
	}

}
