import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JPanel;
import javax.swing.Timer;


public class GameManager extends JPanel {
	

	
	private ScorePanel scorePanel;
	private GamePanel gamePanel;
	private ControlPanel controlPanel;
	
	private int lives; 	//Lives remaining
	private int score;  //Score of the game
	
	
	public GameManager()
	{

		lives = 3;
		score = 0;
		
		
		
		//Applet Setup
		this.setSize(WIDTH, HEIGHT);
		this.setLayout(new BorderLayout());
		
		//Setup panels
		scorePanel = new ScorePanel(score,lives);
		gamePanel = new GamePanel();
		controlPanel = new ControlPanel();
		
		
		//Add panels to applet
		this.add(scorePanel,BorderLayout.NORTH);
		this.add(gamePanel, BorderLayout.CENTER);
		this.add(controlPanel, BorderLayout.SOUTH);

		
		//Add listeners
		controlPanel.startButton.addActionListener(new buttonListener());
		controlPanel.stopButton.addActionListener(new buttonListener());
				
		
	}
	
	
	//Draw the background, all of the falling objects in the list
	//and all of the bullets that are active in the list
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);		
	}
	
	
	//Because this is only one thread, I needed the timer to run faster
	//to check for collisions, but I didn't want the objects to move 
	//as fast a second time check was placed in the code
	private class TimerListener implements ActionListener
	{
	
		private int time;
		
		//The listener that triggered when the timer goes off
		public void actionPerformed(ActionEvent arg0) {
		
		
		
		}
	
		
		//Check each falling object against each live bullet
		private void checkCollisions()
		{
	
			

				
				
			
		}
		
		
		//Go through the removeList and remove each object from
		//the falling object array list and the bullet list respectively
		private void removeObjects()
		{
			
		}
		
		//Move all of the falling objects.  Called from the 
		//action listener
		private void moveFallingObjects()
		{
			
		}
		
		//Move the bullets
		private void moveBullets()
		{
			
			
		}
	}

		private class MyKeyListener implements KeyListener{
		@Override
		public void keyPressed(KeyEvent e) {
			
		}
	
	
		@Override
		public void keyReleased(KeyEvent arg0) {
			// TODO Auto-generated method stub
			
		}
	
	
		@Override
		public void keyTyped(KeyEvent arg0) {
			// TODO Auto-generated method stub
			
		}
	}
		
		private class buttonListener implements ActionListener{
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(e.getSource()==controlPanel.startButton)
				{
					
				}
				else if(e.getSource()==controlPanel.stopButton)
				{
					System.exit(0);
				}
			}
		}
		
		private void alert(String input)
		{
			System.out.println(input.toString());
		}
		
		


}
