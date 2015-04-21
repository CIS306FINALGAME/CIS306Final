import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;


public class GamePanel extends JPanel {
	
	private Paddle player;
	
	public GamePanel() 
	{
		//player = new Paddle();
			
		this.setBackground(Color.GRAY);
		this.setVisible(true);
		
	}
	
	
	//Draw the background, all of the falling objects in the list
		//and all of the bullets that are active in the list
		public void paintComponent(Graphics g)
		{
				player.draw(g);
			
		}
	

}
