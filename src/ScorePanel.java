import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;


public class ScorePanel extends JPanel {

	private JLabel scoreLabel;
	private JLabel livesLabel;
	
	public ScorePanel()
	{
		
		this.setBackground(Color.GRAY);
		this.setLayout(new BorderLayout());
		
		scoreLabel = new JLabel("Score:");
		livesLabel = new JLabel("Lives:" );	//Hard coded for now
		
		
		//Setup empty borders to simulate padding
		scoreLabel.setBorder(new EmptyBorder(10, 25, 10, 25));
		livesLabel.setBorder(new EmptyBorder(10, 25, 10, 25));
		
		
		this.add(scoreLabel,BorderLayout.WEST);
		this.add(livesLabel,BorderLayout.EAST);
		
		this.setVisible(true);
	}

	public void setScoreLabel(int score)
	{
		scoreLabel.setText("Score: " + score);
	}
	
	public JLabel getScoreLabel()
	{
		return scoreLabel;
	}
	
	
	
	public void setLivesLabel(String pLivesLabelText)
	{
		livesLabel.setText(pLivesLabelText);
	}
	
	public JLabel getLivesLabel()
	{
		return livesLabel;
	}
		
	
}
