package GameComponents;

import java.awt.Graphics;

//!draws numbers such as score, level etc
/*!
 * \author n.ryouta
 */
public class ScoreBoard extends Label{
	private String format = "%d";
	private long score = 0;
	
	//!increase score 
	/*!
	 * \param _value scores will be added
	 * \exception IllegalArgumentException _value is smaller equal than 0
	 */
	public void IncreaseScore(long _value)throws IllegalArgumentException {
		if(!(_value > 0))throw new IllegalArgumentException("ScoreBoard.AddScore _value must be greater than 0");
		score += _value;
	}
	//!decrease score
	/*!
	 * \param _value scores will be subtracted
	 * \exception IllegalArgumentException _value is greater equal than 0
	 */
	public void DecreaseScore(long _value)throws IllegalArgumentException {
		if(!(_value > 0))throw new IllegalArgumentException("ScoreBoard.DecreaseScore _value must be greater than 0");
		score -= _value;
	}
	
	//!get the score
	/*!
	 * \returns score of this score board
	 */
	public long GetScore(){
		return score;	
	}
	//!set the format that labeling score
	/*!
	 * \param _format the format should include '%d'
	 * \exception IllegalArgumentException _format is null
	 * \warning _format string should include '\%d' character
	 */
	public void SetScoreFormat(String _format) throws IllegalArgumentException {
		if(!(_format!=null))throw new IllegalArgumentException("ScoreBoard.SetScoreFormat _format is null");
		format = _format;
	}
	//!get the format that labeling
	/*!
	 * \returns the format
	 */
	public String GetScoreFormat() {
		return format;
	}
	
	//!draw the score with configured format
	/*!
	 * \param _parent the parent Component that contains this
	 * \param _g the GDI object
	 */
	public void EndDraw(Component _parent,Graphics _g) throws Exception {
		SetText(String.format(format, score));
		super.EndDraw(_parent, _g);
	}
}
