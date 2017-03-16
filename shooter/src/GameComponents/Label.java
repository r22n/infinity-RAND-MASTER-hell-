package GameComponents;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
	
//!the label component that draws text in the screen
/*!
	this label makes this font family the arial 10points plane and color white.
	\author n.ryouta
*/
public class Label extends ComponentContainer {
	private String text = "";
	private Font font = new java.awt.Font("airal", java.awt.Font.PLAIN, 10);
	private Color color = Color.white;
	//!set this text of label
	/*!
		\param _text the text will be set 
		\exception IllegalArgumentException _text is null
	*/
	public void SetText(String _text)throws IllegalArgumentException {
		if(!(_text!=null))throw new IllegalArgumentException("Label.SetText _text is null");
		text = _text;
	}
	//!get the text of label
	/*!
		\returns the text of this
	*/
	public String GetText(){return text;}
	//!set the font of this
	/*!
		\param _family the font family
		\param _style the font style flags that provided by the implemented font. default font is java.awt. Font.
		\param _size th size of font in points
		\exception IllegalArgumentException _family is null 
		\exception IllegalArgumentException _size smaller equal 0
	*/
	public void SetFont(String _family,int _style,int _size)throws IllegalArgumentException {
		if(!(_family!=null))throw new IllegalArgumentException("Label.SetFont _family is null");
		if(!(_size > 0))throw new IllegalArgumentException("Lablel.SetFont _size must be greater than 0");
		font = new java.awt.Font(_family,_style,_size);
	}
	//!get the font 
	/*!
		\returns the font object that implemented by developers. defaultlly, java.awt.Font.
	*/
	public Font GetFont(){return font;}
	//!set the color of font
	/*!
		\param _color the color of this
		\exception _color is null
	*/
	public void SetColor(Color _color)throws IllegalArgumentException {
		if(!(_color!=null))throw new IllegalArgumentException("Label.SetColor _color is null");
		color = _color;
	}
	//!get the color of this label
	/*!
		\returns the color of this label

	*/
	public Color GetColor(){return color;}
	//!draw this body with the specified setting in members
	/*!
		draw text at the location that provided by GetBounds
		\param _parent the parent component
		\param _g the GDI object
		\see Component.GetBounds()
	*/
	public void EndDraw(Component _parent,Graphics _g) throws Exception{
		super.EndDraw(_parent, _g);
		_g.setColor(color);
		_g.setFont(font);
		Rect bound = GetBounds();
		_g.drawString(
				text,
				(int)bound.x,(int)bound.y
				);
	}
}
